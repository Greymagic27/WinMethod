package io.github.greymagic27.win_method.platform;

import io.github.greymagic27.win_method.BaseTsd.DWORD_PTR;
import io.github.greymagic27.win_method.IntSafe.DWORD;
import io.github.greymagic27.win_method.Memory;
import io.github.greymagic27.win_method.Pointer;
import io.github.greymagic27.win_method.WinDef.UINT;
import io.github.greymagic27.win_method.WinDef.WORD;
import io.github.greymagic27.win_method.WinNT.LPSTR;
import io.github.greymagic27.win_method.types.MmSysCom;
import io.github.greymagic27.win_method.types.MmeApi;
import java.lang.foreign.ValueLayout;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.github.greymagic27.win_method.types.MmeApi.WAVE_FORMAT_PCM;
import static io.github.greymagic27.win_method.types.MmeApi.WHDR_DONE;
import static io.github.greymagic27.win_method.types.MmeApi.WHDR_PREPARED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

class WinMMTest {

    private static final WinMM winmm = WinMM.INSTANCE;
    private MmeApi.WAVEFORMATEX format;
    private WinMM.WAVEOUT device;

    private static void waitUntilDone(MmeApi.@NonNull WAVEHDR header) {
        long deadline = System.nanoTime() + 3_000_000_000L;
        while ((header.dwFlags.intValue() & WHDR_DONE) == 0 && System.nanoTime() < deadline) {
            Thread.onSpinWait();
        }
    }

    private static MmeApi.@NonNull WAVEFORMATEX pcmFormat(int tag) {
        MmeApi.WAVEFORMATEX wfx = new MmeApi.WAVEFORMATEX();
        wfx.wFormatTag = new WORD((short) tag);
        wfx.nChannels = new WORD((short) 2);
        wfx.nSamplesPerSec = new DWORD(44100);
        wfx.wBitsPerSample = new WORD((short) 16);
        wfx.nBlockAlign = new WORD((short) (2 * 16 / 8));
        wfx.nAvgBytesPerSec = new DWORD(44100 * 2 * 16 / 8);
        wfx.cbSize = new WORD((short) 0);
        return wfx;
    }

    private static MmeApi.@NonNull WAVEHDR newHeader(@NonNull Memory data, int length) {
        MmeApi.WAVEHDR header = new MmeApi.WAVEHDR();
        header.lpData = new LPSTR(data.segment);
        header.dwBufferLength = new DWORD(length);
        header.dwFlags = new DWORD(0);
        header.dwLoops = new DWORD(0);
        return header;
    }

    @BeforeEach
    void setUp() {
        format = pcmFormat(WAVE_FORMAT_PCM);
        try (Memory out = new Memory(ValueLayout.ADDRESS.byteSize())) {
            WinMM.MMRESULT result = winmm.waveOutOpen(new WinMM.WAVEOUT(out), new UINT(-1), format, new DWORD_PTR(0), new DWORD_PTR(0), new DWORD(0));
            assumeTrue(result.intValue() != 6, "No audio device available");
            assertEquals(0, result.intValue(), "waveOutOpen should succeed");
            device = new WinMM.WAVEOUT(new Pointer(out.segment.get(ValueLayout.ADDRESS, 0)));
        }
    }

    @AfterEach
    void tearDown() {
        if (device != null) {
            winmm.waveOutReset(device);
            winmm.waveOutClose(device);
            device = null;
        }
    }

    @Test
    void testWaveOutOpen() {
        assertNotNull(device);
        assertNotEquals(0, device.segment.address());
        MmeApi.WAVEFORMATEX bad = pcmFormat(0x7FFF);
        try (Memory out = new Memory(ValueLayout.ADDRESS.byteSize())) {
            WinMM.MMRESULT result = winmm.waveOutOpen(new WinMM.WAVEOUT(out), new UINT(-1), bad, new DWORD_PTR(0), new DWORD_PTR(0), new DWORD(0));
            assertNotEquals(0, result.intValue());
        }
    }

    @Test
    void testWaveOutClose() {
        assertEquals(0, winmm.waveOutClose(device).intValue());
        device = null;
        assertNotEquals(0, winmm.waveOutClose(new WinMM.WAVEOUT(new Pointer(null))).intValue());
    }

    @Test
    void testWaveOutPrepareHeader() {
        try (Memory data = new Memory(4096)) {
            MmeApi.WAVEHDR header = newHeader(data, 4096);
            assertEquals(0, winmm.waveOutPrepareHeader(device, header, new UINT(header.size())).intValue());
            assertTrue((header.dwFlags.intValue() & WHDR_PREPARED) != 0);
            assertEquals(0, winmm.waveOutUnprepareHeader(device, header, new UINT(header.size())).intValue());
            assertEquals(0, header.dwFlags.intValue() & WHDR_PREPARED);
        }
    }

    @Test
    void testWaveOutUnprepareHeader() {
        try (Memory data = new Memory(4096)) {
            MmeApi.WAVEHDR header = newHeader(data, 4096);
            assertEquals(0, winmm.waveOutPrepareHeader(device, header, new UINT(header.size())).intValue());
            assertEquals(0, winmm.waveOutUnprepareHeader(device, header, new UINT(header.size())).intValue());
            assertEquals(0, header.dwFlags.intValue() & WHDR_PREPARED);
        }
    }

    @Test
    void testWaveOutWrite() {
        int length = format.nAvgBytesPerSec.intValue() / 10;
        try (Memory data = new Memory(length)) {
            MmeApi.WAVEHDR header = newHeader(data, length);
            assertEquals(0, winmm.waveOutPrepareHeader(device, header, new UINT(header.size())).intValue());
            try {
                assertEquals(0, winmm.waveOutWrite(device, header, new UINT(header.size())).intValue());
                waitUntilDone(header);
                assertTrue((header.dwFlags.intValue() & WHDR_DONE) != 0);
            } finally {
                winmm.waveOutReset(device);
                assertEquals(0, winmm.waveOutUnprepareHeader(device, header, new UINT(header.size())).intValue());
            }
        }
    }

    @Test
    void testWaveOutWriteUnprepared() {
        try (Memory data = new Memory(1024)) {
            MmeApi.WAVEHDR header = newHeader(data, 1024);
            assertNotEquals(0, winmm.waveOutWrite(device, header, new UINT(header.size())).intValue());
        }
    }

    @Test
    void testWaveOutReset() {
        int length = format.nAvgBytesPerSec.intValue() * 5;
        try (Memory data = new Memory(length)) {
            MmeApi.WAVEHDR header = newHeader(data, length);
            assertEquals(0, winmm.waveOutPrepareHeader(device, header, new UINT(header.size())).intValue());
            assertEquals(0, winmm.waveOutWrite(device, header, new UINT(header.size())).intValue());
            assertEquals(0, winmm.waveOutReset(device).intValue());
            assertNotEquals(0, winmm.waveOutReset(new WinMM.WAVEOUT(new Pointer(null))).intValue());
            assertTrue((header.dwFlags.intValue() & WHDR_DONE) != 0);
            assertEquals(0, winmm.waveOutUnprepareHeader(device, header, new UINT(header.size())).intValue());
        }
    }

    @Test
    void testWaveOutGetPosition() {
        MmSysCom.MMTIME time = new MmSysCom.MMTIME();
        time.wType = new UINT(2);
        WinMM.MMRESULT result = winmm.waveOutGetPosition(device, time, new UINT(time.size()));
        assertEquals(0, result.intValue());
        assertEquals(2, time.wType.intValue());
        assertEquals(0, time.u.sample.intValue());
    }

    @Test
    void testWaveOutGetPositionAdvances() {
        int length = format.nAvgBytesPerSec.intValue() / 5;
        try (Memory data = new Memory(length)) {
            MmeApi.WAVEHDR header = newHeader(data, length);
            assertEquals(0, winmm.waveOutPrepareHeader(device, header, new UINT(header.size())).intValue());
            try {
                assertEquals(0, winmm.waveOutWrite(device, header, new UINT(header.size())).intValue());
                waitUntilDone(header);
                MmSysCom.MMTIME time = new MmSysCom.MMTIME();
                time.wType = new UINT(2);
                assertEquals(0, winmm.waveOutGetPosition(device, time, new UINT(time.size())).intValue());
                assertTrue(time.u.sample.intValue() > 0);
            } finally {
                winmm.waveOutReset(device);
                winmm.waveOutUnprepareHeader(device, header, new UINT(header.size()));
            }
        }
    }

    @Test
    void testWaveOutGetPositionInvalidHandle() {
        MmSysCom.MMTIME time = new MmSysCom.MMTIME();
        time.wType = new UINT(2);
        assertNotEquals(0, winmm.waveOutGetPosition(new WinMM.WAVEOUT(new Pointer(null)), time, new UINT(time.size())).intValue());
    }
}
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
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.github.greymagic27.win_method.types.MmeApi.WAVE_FORMAT_PCM;
import static io.github.greymagic27.win_method.types.MmeApi.WAVE_MAPPER;
import static io.github.greymagic27.win_method.types.MmeApi.WHDR_DONE;
import static io.github.greymagic27.win_method.types.MmeApi.WHDR_PREPARED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class WinMMTest {

    private static final WinMM winmm = WinMM.INSTANCE;
    private WinMM.WAVEOUT device;
    private int length;

    @SuppressWarnings("BusyWait")
    private static void waitUntilDone(MmeApi.@NonNull WAVEHDR header) {
        long deadline = System.nanoTime() + 3_000_000_000L;
        try {
            do {
                header.read();
                if ((header.dwFlags.intValue() & WHDR_DONE) != 0) return;
                Thread.sleep(5);
            } while (System.nanoTime() < deadline);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            fail("Interrupted while waiting for WHDR_DONE");
        }
        fail("Timed out waiting for WHDR_DONE");
    }

    private static MmeApi.@NonNull WAVEFORMATEX pcmFormat(int tag) {
        MmeApi.WAVEFORMATEX wfx = new MmeApi.WAVEFORMATEX();
        wfx.wFormatTag = new WORD(tag);
        wfx.nChannels = new WORD(2);
        wfx.nSamplesPerSec = new DWORD(44100);
        wfx.wBitsPerSample = new WORD(16);
        wfx.nBlockAlign = new WORD((2 * 16 / 8));
        wfx.nAvgBytesPerSec = new DWORD(44100 * 2 * 16 / 8);
        wfx.cbSize = new WORD(0);
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
        MmeApi.WAVEFORMATEX format = pcmFormat(WAVE_FORMAT_PCM);
        length = format.nAvgBytesPerSec.intValue();
        try (Memory out = new Memory(ValueLayout.ADDRESS.byteSize())) {
            WinMM.MMRESULT result = winmm.waveOutOpen(new WinMM.WAVEOUT(out), WAVE_MAPPER, format, new DWORD_PTR(0), new DWORD_PTR(0), new DWORD(0));
            assertEquals(0, result.intValue());
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
    }

    @Test
    void testWaveOutOpenInvalidFormat() {
        MmeApi.WAVEFORMATEX bad = pcmFormat(0x7FFF);
        try (Memory out = new Memory(ValueLayout.ADDRESS.byteSize())) {
            WinMM.MMRESULT result = winmm.waveOutOpen(new WinMM.WAVEOUT(out), WAVE_MAPPER, bad, new DWORD_PTR(0), new DWORD_PTR(0), new DWORD(0));
            assertNotEquals(0, result.intValue());
        }
    }

    @Test
    void testWaveOutClose() {
        assertEquals(0, winmm.waveOutClose(device).intValue());
        device = null;
        assertNotEquals(0, winmm.waveOutClose(new WinMM.WAVEOUT(new Pointer(MemorySegment.NULL))).intValue());
    }

    @Test
    void testWaveOutCloseWhilePlaying() {
        try (Memory data = new Memory(length)) {
            MmeApi.WAVEHDR header = newHeader(data, length);
            assertEquals(0, winmm.waveOutPrepareHeader(device, header, new UINT(header.size())).intValue());
            assertEquals(0, winmm.waveOutWrite(device, header, new UINT(header.size())).intValue());
            assertEquals(33, winmm.waveOutClose(device).intValue());
            winmm.waveOutReset(device);
            header.read();
            winmm.waveOutUnprepareHeader(device, header, new UINT(header.size()));
        }
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
        try (Memory data = new Memory(length)) {
            MmeApi.WAVEHDR header = newHeader(data, length);
            assertEquals(0, winmm.waveOutPrepareHeader(device, header, new UINT(header.size())).intValue());
            try {
                assertEquals(0, winmm.waveOutWrite(device, header, new UINT(header.size())).intValue());
                waitUntilDone(header);
                assertTrue((header.dwFlags.intValue() & WHDR_DONE) != 0);
            } finally {
                winmm.waveOutReset(device);
                header.read();
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
        try (Memory data = new Memory(length)) {
            MmeApi.WAVEHDR header = newHeader(data, length);
            assertEquals(0, winmm.waveOutPrepareHeader(device, header, new UINT(header.size())).intValue());
            assertEquals(0, winmm.waveOutWrite(device, header, new UINT(header.size())).intValue());
            assertEquals(0, winmm.waveOutReset(device).intValue());
            header.read();
            assertTrue((header.dwFlags.intValue() & WHDR_DONE) != 0);
            assertEquals(0, winmm.waveOutUnprepareHeader(device, header, new UINT(header.size())).intValue());
        }
    }

    @Test
    void testWaveOutResetInvalidHandle() {
        assertNotEquals(0, winmm.waveOutReset(new WinMM.WAVEOUT(new Pointer(MemorySegment.NULL))).intValue());
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
                header.read();
                winmm.waveOutUnprepareHeader(device, header, new UINT(header.size()));
            }
        }
    }

    @Test
    void testWaveOutGetPositionInvalidHandle() {
        MmSysCom.MMTIME time = new MmSysCom.MMTIME();
        time.wType = new UINT(2);
        assertNotEquals(0, winmm.waveOutGetPosition(new WinMM.WAVEOUT(new Pointer(MemorySegment.NULL)), time, new UINT(time.size())).intValue());
    }

    @Test
    void testWaveOutPrepareHeaderInvalidHandle() {
        try (Memory data = new Memory(4096)) {
            MmeApi.WAVEHDR header = newHeader(data, 4096);
            assertNotEquals(0, winmm.waveOutPrepareHeader(new WinMM.WAVEOUT(new Pointer(MemorySegment.NULL)), header, new UINT(header.size())).intValue());
        }
    }
}
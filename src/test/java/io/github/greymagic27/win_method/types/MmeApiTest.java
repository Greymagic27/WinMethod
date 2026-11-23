package io.github.greymagic27.win_method.types;

import io.github.greymagic27.win_method.BaseTsd.DWORD_PTR;
import io.github.greymagic27.win_method.IntSafe.DWORD;
import io.github.greymagic27.win_method.Memory;
import io.github.greymagic27.win_method.WinDef.WORD;
import io.github.greymagic27.win_method.WinNT.LPSTR;
import org.junit.jupiter.api.Test;

import static io.github.greymagic27.win_method.types.MmeApi.WAVE_FORMAT_PCM;
import static io.github.greymagic27.win_method.types.MmeApi.WAVE_MAPPER;
import static io.github.greymagic27.win_method.types.MmeApi.WHDR_DONE;
import static io.github.greymagic27.win_method.types.MmeApi.WHDR_PREPARED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MmeApiTest {

    @Test
    void testValues() {
        assertEquals(1, WAVE_FORMAT_PCM);
        assertEquals(0x00000001, WHDR_DONE);
        assertEquals(0x00000002, WHDR_PREPARED);
        assertEquals(-1, WAVE_MAPPER.intValue());
    }

    @Test
    void testWaveFormatEx() {
        MmeApi.WAVEFORMATEX wfx = new MmeApi.WAVEFORMATEX();
        wfx.wFormatTag = new WORD(WAVE_FORMAT_PCM);
        wfx.nChannels = new WORD(2);
        wfx.nSamplesPerSec = new DWORD(44100);
        wfx.nAvgBytesPerSec = new DWORD(176400);
        wfx.nBlockAlign = new WORD(4);
        wfx.wBitsPerSample = new WORD(16);
        wfx.cbSize = new WORD(0);
        assertEquals(WAVE_FORMAT_PCM, wfx.wFormatTag.shortValue());
        assertEquals(2, wfx.nChannels.shortValue());
        assertEquals(44100, wfx.nSamplesPerSec.intValue());
        assertEquals(wfx.nSamplesPerSec.intValue() * wfx.nBlockAlign.shortValue(), wfx.nAvgBytesPerSec.intValue());
        assertEquals(16, wfx.wBitsPerSample.shortValue());
        assertEquals(0, wfx.cbSize.shortValue());
    }

    @Test
    void testWaveHdr() {
        MmeApi.WAVEHDR header = new MmeApi.WAVEHDR();
        try (Memory data = new Memory(4096)) {
            header.lpData = new LPSTR(data.segment);
            header.dwBufferLength = new DWORD(4096);
            header.dwBytesRecorded = new DWORD(1024);
            header.dwUser = new DWORD_PTR(42);
            header.dwFlags = new DWORD(WHDR_PREPARED);
            header.dwLoops = new DWORD(3);
            header.lpNext = null;
            header.reserved = new DWORD_PTR(7);
            assertEquals(data.segment.address(), header.lpData.segment.address());
            assertEquals(4096, header.dwBufferLength.intValue());
            assertEquals(1024, header.dwBytesRecorded.intValue());
            assertEquals(42, header.dwUser.longValue());
            assertTrue((header.dwFlags.intValue() & WHDR_PREPARED) != 0);
            assertEquals(0, header.dwFlags.intValue() & WHDR_DONE);
            header.dwFlags = new DWORD(header.dwFlags.intValue() | WHDR_DONE);
            assertEquals(WHDR_PREPARED | WHDR_DONE, header.dwFlags.intValue());
            assertEquals(3, header.dwLoops.intValue());
            assertNull(header.lpNext);
            assertEquals(7, header.reserved.longValue());
        }
    }
}
package io.github.greymagic27.win_method.types;

import io.github.greymagic27.win_method.IntSafe.DWORD;
import io.github.greymagic27.win_method.WinDef.BYTE;
import io.github.greymagic27.win_method.WinDef.UINT;
import org.junit.jupiter.api.Test;

import static io.github.greymagic27.win_method.types.MmSysCom.TIME_SAMPLES;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MmSysComTest {

    @Test
    void testValues() {
        assertEquals(0x0002, TIME_SAMPLES);
    }

    @Test
    void testMmTime() {
        MmSysCom.MMTIME time = new MmSysCom.MMTIME();
        time.wType = new UINT(TIME_SAMPLES);
        assertEquals(TIME_SAMPLES, time.wType.intValue());
        assertNotNull(time.u);
    }

    @Test
    void testMmTimeUnion() {
        MmSysCom.MMTIME.U u = new MmSysCom.MMTIME.U();
        assertEquals(8, u.size());
        u.ms = new DWORD(1000);
        u.sample = new DWORD(44100);
        u.cb = new DWORD(4096);
        u.ticks = new DWORD(480);
        assertEquals(1000, u.ms.intValue());
        assertEquals(44100, u.sample.intValue());
        assertEquals(4096, u.cb.intValue());
        assertEquals(480, u.ticks.intValue());
    }

    @Test
    void testMmTimeSmpte() {
        MmSysCom.MMTIME.SMPTE smpte = new MmSysCom.MMTIME.SMPTE();
        assertEquals(8, smpte.size());
        smpte.hour = new BYTE((byte) 1);
        smpte.min = new BYTE((byte) 2);
        smpte.sec = new BYTE((byte) 3);
        smpte.frame = new BYTE((byte) 4);
        smpte.fps = new BYTE((byte) 30);
        smpte.dummy = new BYTE((byte) 0);
        assertEquals(1, smpte.hour.byteValue());
        assertEquals(2, smpte.min.byteValue());
        assertEquals(3, smpte.sec.byteValue());
        assertEquals(4, smpte.frame.byteValue());
        assertEquals(30, smpte.fps.byteValue());
        assertEquals(0, smpte.dummy.byteValue());
        assertNotNull(smpte.pad);
        assertEquals(2, smpte.pad.length);
    }

    @Test
    void testMmTimeMidi() {
        MmSysCom.MMTIME.MIDI midi = new MmSysCom.MMTIME.MIDI();
        assertEquals(4, midi.size());
        midi.songptrpos = new DWORD(960);
        assertEquals(960, midi.songptrpos.intValue());
    }
}
package io.github.greymagic27.win_method.types;

import io.github.greymagic27.win_method.IntSafe.DWORD;
import io.github.greymagic27.win_method.Structure;
import io.github.greymagic27.win_method.Union;
import io.github.greymagic27.win_method.WinDef.BYTE;
import io.github.greymagic27.win_method.WinDef.UINT;

/// Values defined in mmsyscom.h
public interface MmSysCom {

    /// Number of waveform-audio samples
    int TIME_SAMPLES = 0x0002;

    /// The MMTIME structure contains timing information for different types of multimedia data
    @Structure.AutoFieldOrder
    class MMTIME extends Structure {
        /// Time format. It can be one of [the following formats](https://learn.microsoft.com/en-us/previous-versions//dd757347(v=vs.85))
        public UINT wType;
        /// Time value in the format specified by {@link #wType}
        public U u;

        /// Union containing the time value in one of several supported formats
        @SuppressWarnings("unused")
        @AutoFieldOrder
        public static class U extends Union {
            /// Time in milliseconds
            public DWORD ms;
            /// Time in samples
            public DWORD sample;
            /// Time in bytes
            public DWORD cb;
            /// Time in MIDI ticks
            public DWORD ticks;
            /// Time in SMPTE format
            public SMPTE smpte;
            /// Time in MIDI song pointer position
            public MIDI midi;
        }

        /// Represents time in SMPTE format
        @Structure.AutoFieldOrder
        public static class SMPTE extends Structure {
            /// Hours
            public BYTE hour;
            /// Minutes
            public BYTE min;
            /// Seconds
            public BYTE sec;
            /// Frames
            public BYTE frame;
            /// Frames per second
            public BYTE fps;
            /// Unused
            public BYTE dummy;
            /// Reserved padding
            @Structure.ArrayLength(2)
            public BYTE[] pad;
        }

        /// Represents time as a MIDI song pointer position
        @Structure.AutoFieldOrder
        public static class MIDI extends Structure {
            /// MIDI song pointer position
            public DWORD songptrpos;
        }
    }
}

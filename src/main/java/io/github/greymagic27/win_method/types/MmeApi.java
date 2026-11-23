package io.github.greymagic27.win_method.types;

import io.github.greymagic27.win_method.BaseTsd.DWORD_PTR;
import io.github.greymagic27.win_method.IntSafe.DWORD;
import io.github.greymagic27.win_method.Pointer;
import io.github.greymagic27.win_method.Structure;
import io.github.greymagic27.win_method.WinDef.UINT;
import io.github.greymagic27.win_method.WinDef.WORD;
import io.github.greymagic27.win_method.WinNT.LPSTR;
import io.github.greymagic27.win_method.platform.WinMM;

/// Values defined in mmeapi.h
public interface MmeApi {

    /// Waveform-audio data is PCM
    int WAVE_FORMAT_PCM = 1;
    /// Set by the device driver to indicate that it is finished with the buffer and is returning it to the application
    int WHDR_DONE = 0x00000001;
    /// Set by Windows to indicate that the buffer has been prepared with the waveInPrepareHeader or {@link io.github.greymagic27.win_method.platform.WinMM#waveOutPrepareHeader(WinMM.WAVEOUT, WAVEHDR, UINT)} function
    int WHDR_PREPARED = 0x00000002;
    /// The function selects a waveform-audio output device capable of playing the given format
    int WAVE_MAPPER = new UINT(-1).intValue();

    /// The WAVEFORMATEX structure defines the format of waveform-audio data. Only format information common to all waveform-audio data formats is included in this structure
    @Structure.AutoFieldOrder
    @Structure.Pack(1)
    class WAVEFORMATEX extends Structure {
        /// Waveform-audio format type
        public WORD wFormatTag;
        /// Number of channels in the waveform-audio data
        public WORD nChannels;
        /// Sample rate, in samples per second (hertz). If wFormatTag is WAVE_FORMAT_PCM, then common values for nSamplesPerSec are 8.0 kHz, 11.025 kHz, 22.05 kHz, and 44.1 kHz
        public DWORD nSamplesPerSec;
        /// Required average data-transfer rate, in bytes per second, for the format tag. If wFormatTag is WAVE_FORMAT_PCM, nAvgBytesPerSec should be equal to the product of nSamplesPerSec and nBlockAlign
        public DWORD nAvgBytesPerSec;
        /// Block alignment, in bytes. The block alignment is the minimum atomic unit of data for the wFormatTag format type. If wFormatTag is WAVE_FORMAT_PCM or WAVE_FORMAT_EXTENSIBLE, nBlockAlign must be equal to the product of nChannels and wBitsPerSample divided by 8 (bits per byte)
        public WORD nBlockAlign;
        /// Bits per sample for the wFormatTag format type. If wFormatTag is WAVE_FORMAT_PCM, then wBitsPerSample should be equal to 8 or 16. For non-PCM formats, this member must be set according to the manufacturer's specification of the format tag
        public WORD wBitsPerSample;
        /// Size, in bytes, of extra format information appended to the end of the WAVEFORMATEX structure. This information can be used by non-PCM formats to store extra attributes for the wFormatTag. If no extra information is required by the wFormatTag, this member must be set to 0
        public WORD cbSize;
    }

    /// The WAVEHDR structure defines the header used to identify a waveform-audio buffer
    @Structure.AutoFieldOrder
    class WAVEHDR extends Structure {
        /// {@link io.github.greymagic27.win_method.Pointer} to the waveform buffer
        public LPSTR lpData;
        /// Length, in bytes, of the buffer
        public DWORD dwBufferLength;
        /// When the header is used in input, specifies how much data in stored in the buffer
        public DWORD dwBytesRecorded;
        /// User data
        public DWORD_PTR dwUser;
        /// A bitwise OR of zero or more flags. [The following flags](https://learn.microsoft.com/en-us/windows/win32/api/mmeapi/ns-mmeapi-wavehdr) are defined
        public DWORD dwFlags;
        /// Number of times to pla the loop. This member is only used with output buffers
        public DWORD dwLoops;
        /// Reserved
        public Pointer lpNext;
        /// Reserved
        public DWORD_PTR reserved;
    }
}

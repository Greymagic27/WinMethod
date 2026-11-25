package io.github.greymagic27.win_method.platform;

import io.github.greymagic27.win_method.BaseTsd.DWORD_PTR;
import io.github.greymagic27.win_method.IntSafe.DWORD;
import io.github.greymagic27.win_method.Library;
import io.github.greymagic27.win_method.Pointer;
import io.github.greymagic27.win_method.WinDef.UINT;
import io.github.greymagic27.win_method.WinNT.HANDLE;
import io.github.greymagic27.win_method.types.MmSysCom;
import io.github.greymagic27.win_method.types.MmeApi;
import org.jspecify.annotations.NonNull;

/// Interface for WinMM.dll
public interface WinMM extends Library {

    WinMM INSTANCE = Library.load(WinMM.class);

    /// The waveOutOpen function opens the given waveform-audio output device for playback
    ///
    /// @param phwo       Pointer to a buffer that receives a handle identifying the open waveform-audio output device
    /// @param uDeviceID  Identifier of the waveform-audio output device to open. It can be either a device identifier or a handle of an open waveform-audio input device. You can also use {@link MmeApi#WAVE_MAPPER} instead of a device identifier
    /// @param pwfx       Pointer to a {@link io.github.greymagic27.win_method.types.MmeApi.WAVEFORMATEX} structure that identifies the format of the waveform-audio data to be sent to the device. You can free this structure immediately after passing it to waveOutOpen
    /// @param dwCallback Specifies the callback mechanism. The value must be [one of the following](https://learn.microsoft.com/en-us/windows/win32/api/mmeapi/nf-mmeapi-waveoutopen)
    /// @param dwInstance User-instance data passed to the callback mechanism. This parameter is not used with the window callback mechanism
    /// @param fdwOpen    Flags for opening the device. The [following values](https://learn.microsoft.com/en-us/windows/win32/api/mmeapi/nf-mmeapi-waveoutopen) are defined
    /// @return Returns {@code MMSYSERR_NOERROR} if successful or an error otherwise. Possible error values [include the following](https://learn.microsoft.com/en-us/windows/win32/api/mmeapi/nf-mmeapi-waveoutopen)
    MMRESULT waveOutOpen(WAVEOUT phwo, UINT uDeviceID, MmeApi.WAVEFORMATEX pwfx, DWORD_PTR dwCallback, DWORD_PTR dwInstance, DWORD fdwOpen);

    /// The waveOutPrepareHeader function prepares a waveform-audio data block for playback
    ///
    /// @param hwo  Handle to the waveform-audio output device
    /// @param pwh  Pointer to a {@link io.github.greymagic27.win_method.types.MmeApi.WAVEHDR} structure that identifies the data block to be prepared
    /// @param cbwh Size, in bytes, of the {@link io.github.greymagic27.win_method.types.MmeApi.WAVEHDR} structure
    /// @return Returns {@code MMSYSERR_NOERROR} if successful or an error otherwise. Possible error values [include the following](https://learn.microsoft.com/en-us/windows/win32/api/mmeapi/nf-mmeapi-waveoutprepareheader)
    MMRESULT waveOutPrepareHeader(WAVEOUT hwo, MmeApi.WAVEHDR pwh, UINT cbwh);

    /// The waveOutUnprepareHeader function cleans up the preparation performed by the {@link #waveOutPrepareHeader(WAVEOUT, MmeApi.WAVEHDR, UINT)} function. This function must be called after the device driver is finished with a data block. You must call this function before freeing the buffer
    ///
    /// @param hwo  Handle to the waveform-audio output device
    /// @param pwh  Pointer to a {@link io.github.greymagic27.win_method.types.MmeApi.WAVEHDR} structure identifying the data block to be cleaned up
    /// @param cbwh Size, in bytes, of the {@link io.github.greymagic27.win_method.types.MmeApi.WAVEHDR} structure
    /// @return Returns {@code MMSYSERR_NOERROR} if successful or an error otherwise. Possible error values [include the following](https://learn.microsoft.com/en-us/windows/win32/api/mmeapi/nf-mmeapi-waveoutunprepareheader)
    MMRESULT waveOutUnprepareHeader(WAVEOUT hwo, MmeApi.WAVEHDR pwh, UINT cbwh);

    /// The waveOutWrite function sends a data block to the given waveform-audio output device
    ///
    /// @param hwo  Handle to the waveform-audio output device
    /// @param pwh  Pointer to a {@link io.github.greymagic27.win_method.types.MmeApi.WAVEHDR} structure containing information about the data block
    /// @param cbwh Size, in bytes, of the {@link io.github.greymagic27.win_method.types.MmeApi.WAVEHDR} structure
    /// @return Returns {@code MMSYSERR_NOERROR} if successful or an error otherwise. Possible error values [include the following](https://learn.microsoft.com/en-us/windows/win32/api/mmeapi/nf-mmeapi-waveoutwrite)
    MMRESULT waveOutWrite(WAVEOUT hwo, MmeApi.WAVEHDR pwh, UINT cbwh);

    ///  The waveOutReset function stops playback on the given waveform-audio output device and resets the current position to zero. All pending playback buffers are marked as done ({@link MmeApi#WHDR_DONE}) and returned to the application
    ///
    /// @param hwo Handle to the waveform-audio output device
    /// @return Returns {@code MMSYSERR_NOERROR} if successful or an error otherwise. Possible error values [include the following](https://learn.microsoft.com/en-us/windows/win32/api/mmeapi/nf-mmeapi-waveoutreset)
    MMRESULT waveOutReset(WAVEOUT hwo);

    /// The waveOutClose function closes the given waveform-audio output device
    ///
    /// @param hwo Handle to the waveform-audio output device. If the function succeeds, the handle is no longer valid after this call
    /// @return Returns {@code MMSYSERR_NOERROR} if successful or an error otherwise. Possible error values [include the following](https://learn.microsoft.com/en-us/windows/win32/api/mmeapi/nf-mmeapi-waveoutclose)
    MMRESULT waveOutClose(WAVEOUT hwo);

    /// The waveOutGetPosition function retrieves the current playback position of the given waveform-audio output device
    ///
    /// @param hwo   Handle to the waveform-audio output device
    /// @param pmmt  Pointer to an {@link io.github.greymagic27.win_method.types.MmSysCom.MMTIME} structure
    /// @param cbmmt Size, in bytes, of the {@link io.github.greymagic27.win_method.types.MmSysCom.MMTIME} structure
    /// @return Returns {@code MMSYSERR_NOERROR} if successful or an error otherwise. Possible error values [include the following](https://learn.microsoft.com/en-us/windows/win32/api/mmeapi/nf-mmeapi-waveoutgetposition)
    MMRESULT waveOutGetPosition(WAVEOUT hwo, MmSysCom.MMTIME pmmt, UINT cbmmt);

    class MMRESULT extends UINT {
        public MMRESULT(int value) {
            super(value);
        }
    }

    class WAVEOUT extends HANDLE {
        public WAVEOUT(@NonNull Pointer pointer) {
            super(pointer);
        }
    }
}

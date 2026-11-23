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

    MMRESULT waveOutOpen(WAVEOUT phwo, UINT uDeviceID, MmeApi.WAVEFORMATEX pwfx, DWORD_PTR dwCallback, DWORD_PTR dwInstance, DWORD fdwOpen);

    MMRESULT waveOutPrepareHeader(WAVEOUT hwo, MmeApi.WAVEHDR pwh, UINT cbwh);

    MMRESULT waveOutUnprepareHeader(WAVEOUT hwo, MmeApi.WAVEHDR pwh, UINT cbwh);

    MMRESULT waveOutWrite(WAVEOUT hwo, MmeApi.WAVEHDR pwh, UINT cbwh);

    MMRESULT waveOutReset(WAVEOUT hwo);

    MMRESULT waveOutClose(WAVEOUT hwo);

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

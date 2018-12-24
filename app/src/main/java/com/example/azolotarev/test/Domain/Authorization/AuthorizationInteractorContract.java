package com.example.azolotarev.test.Domain.Authorization;

import android.support.annotation.NonNull;
import com.example.azolotarev.test.BaseCallback;
import com.example.azolotarev.test.UI.ProgressContract;

public interface AuthorizationInteractorContract {

    interface getSuccessCallback extends BaseCallback.BaseErrorCallback{
        void onSuccess(boolean success);
    }
    void logIn(@NonNull final getSuccessCallback callback, @NonNull String login, @NonNull String password, @NonNull boolean firstLoad);
    void setProgressListener(@NonNull ProgressContract listener);


}

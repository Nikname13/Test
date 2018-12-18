package com.example.azolotarev.test.Domain.Authorization;

import android.support.annotation.NonNull;
import com.example.azolotarev.test.Service.ProgressContract;

public interface AuthorizationInteractorContract {

    void logIn(@NonNull String login,@NonNull String password);
    void setProgressListener(@NonNull ProgressContract listener);
    boolean getSuccess();

}

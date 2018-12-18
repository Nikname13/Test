package com.example.azolotarev.test.Repository;

import android.support.annotation.NonNull;

public interface RepositoryContract {

    boolean isAuth(@NonNull String login,@NonNull String password);
}

package com.example.azolotarev.test.UI;

import android.os.Parcelable;
import android.support.annotation.NonNull;

public interface BasePresenter {
    void start();
    void bindView(@NonNull BaseView view);
    void unbindView();
}

package com.example.azolotarev.test.UI.Main.EmployeePage.LargeImage;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import com.example.azolotarev.test.UI.BasePresenter;
import com.example.azolotarev.test.UI.BaseView;
import com.example.azolotarev.test.UI.ProgressContract;

public interface LargeImageContract {
    interface View extends BaseView<Presenter>, ProgressContract {
        void setAvatar(@NonNull Bitmap avatar);
    }

    interface Presenter extends BasePresenter,ProgressContract{
        void start(String id);
    }
}

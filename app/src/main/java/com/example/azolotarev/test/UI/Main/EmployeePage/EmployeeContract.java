package com.example.azolotarev.test.UI.Main.EmployeePage;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import com.example.azolotarev.test.UI.BasePresenter;
import com.example.azolotarev.test.UI.BaseView;
import com.example.azolotarev.test.UI.ProgressContract;

public interface EmployeeContract {
    interface View extends BaseView<Presenter>,ProgressContract {
        void setTitle(@NonNull String title);
        void setName(@NonNull String name);
        void setPhone(@NonNull String phone);
        void setEmail(@NonNull String email);
        void setAvatarView(@NonNull Bitmap avatarView);
        void hideTitle();
        void hideName();
        void hidePhone();
        void hideEmail();
        void callNumber(@NonNull String number);
        void sendEmail(@NonNull String email);
        void showLargeImage(@NonNull String id);
        void showError(@NonNull String errorMessage);
        void showProgressImage();
        void hideProgressImage();
    }

    interface Presenter extends BasePresenter,ProgressContract {
        void callNumber(@NonNull String number);
        void sendEmail(@NonNull String email);
        void showLargeImage();
        void loadPhoto(int width, int height);
        void setPhotoId(@NonNull String id);
        void setItemId(@NonNull String id);
        void errorIntent();
    }
}

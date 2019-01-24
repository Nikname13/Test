package com.example.azolotarev.test.UI.Main.EmployeePage;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import com.example.azolotarev.test.Model.EmployeeModel;
import com.example.azolotarev.test.UI.BasePresenter;
import com.example.azolotarev.test.UI.BaseView;
import com.example.azolotarev.test.UI.ProgressContract;

public interface EmployeeContract {
    interface View extends BaseView<Presenter>, ProgressContract {
        void setTitle(String title);
        void setName(String name);
        void setPhone(String phone);
        void setEmail(String email);
        void setAvatar(@NonNull Bitmap avatar);
        void hideTitle();
        void hideName();
        void hidePhone();
        void hideEmail();
        void callNumber(String number);
        void sendEmail(String email);

    }

    interface Presenter extends BasePresenter,ProgressContract{
        void start(String id);
        void callNumber(String number);
        void sendEmail(String email);

    }
}

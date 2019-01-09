package com.example.azolotarev.test.UI.Main.EmployeePage;

import android.graphics.Bitmap;
import com.example.azolotarev.test.Model.EmployeeModel;
import com.example.azolotarev.test.UI.BasePresenter;
import com.example.azolotarev.test.UI.BaseView;
import com.example.azolotarev.test.UI.ProgressContract;

public interface EmployeePageContract {
    interface View extends BaseView<Presenter>, ProgressContract {
        void setTitle(String title);
        void setName(String name);
        void setPhone(String phone);
        void setEmail(String email);
        void setAvatar(Bitmap avatar);
        void hideTitle();
        void hideName();
        void hidePhone();
        void hideEmail();
        EmployeeModel getEmployee();
    }

    interface Presenter extends BasePresenter,ProgressContract{}
}

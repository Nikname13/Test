package com.example.azolotarev.test.UI.Authorization;

import android.support.annotation.NonNull;
import com.example.azolotarev.test.Service.ProgressContract;
import com.example.azolotarev.test.UI.BasePresenter;
import com.example.azolotarev.test.UI.BaseView;

public interface AuthorizationContract {

    interface View extends BaseView<Presenter>,ProgressContract {

        void showConnectionError();
        void showSuccessError(String errorMessage);
        void showDepartmentsList();
    }

    interface Presenter extends BasePresenter,ProgressContract{
        void showConnectionError();
        void logIn(@NonNull String login,@NonNull String password);
    }
}

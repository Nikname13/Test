package com.example.azolotarev.test.UI.Authorization;

import android.support.annotation.NonNull;
import com.example.azolotarev.test.UI.ProgressContract;
import com.example.azolotarev.test.UI.BasePresenter;
import com.example.azolotarev.test.UI.BaseView;

public interface AuthorizationContract {

    interface View extends BaseView<Presenter>,ProgressContract {
        void showConnectionError(String errorMessage);
        void showSuccessError(String errorMessage);
        void showDepartmentsList();
    }

    interface Presenter extends BasePresenter,ProgressContract{
        void logIn(@NonNull String login,@NonNull String password);
        void destroy();
    }
}

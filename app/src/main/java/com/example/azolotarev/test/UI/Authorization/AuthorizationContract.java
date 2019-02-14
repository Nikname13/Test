package com.example.azolotarev.test.UI.Authorization;

import android.support.annotation.NonNull;
import com.example.azolotarev.test.UI.ProgressContract;
import com.example.azolotarev.test.UI.BasePresenter;
import com.example.azolotarev.test.UI.BaseView;

public interface AuthorizationContract {

    interface View extends BaseView<Presenter>,ProgressContract {
        void showError(String errorMessage);
        void showDepartmentsList();
        void showAuthField();
    }

    interface Presenter extends BasePresenter,ProgressContract{
        void logIn(@NonNull String login,@NonNull String password);
        void destroy();
    }
}

package com.example.azolotarev.test.UI.Start;

import android.support.annotation.NonNull;
import com.example.azolotarev.test.Service.ProgressContract;
import com.example.azolotarev.test.UI.Authorization.AuthorizationContract;
import com.example.azolotarev.test.UI.BasePresenter;
import com.example.azolotarev.test.UI.BaseView;

public interface StartContract {

    interface View extends BaseView<Presenter>,ProgressContract {
        void showConnectionError();
        void showSuccessError(String errorMessage);
        void showDepartmentsList();
        void showAuthorization();
    }

    interface Presenter extends BasePresenter,ProgressContract{
        void showConnectionError();
    }
}

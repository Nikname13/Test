package com.example.azolotarev.test.UI.Authorization;

import com.example.azolotarev.test.UI.BasePresenter;
import com.example.azolotarev.test.UI.BaseView;

public interface AuthorizationContract {

    interface View extends BaseView<Presenter>{
        void showProgress();
        void hideProgress();
        void showConnectionError();
        void showSuccessError(String errorMessage);
        void showDepartmentsList();
    }

    interface Presenter extends BasePresenter {
        void showProgress();
        void hideProgress();
        void showConnectionError();
        void showSuccessError(String errorMessage);
        void showDepartmentsList();

        void logIn(String login, String password);
    }
}

package com.example.azolotarev.test.presentation.Authorization;

import com.example.azolotarev.test.presentation.BasePresenter;
import com.example.azolotarev.test.presentation.BaseView;

public interface AuthorizationContract {

    interface View extends BaseView<Presenter>{
        void showProgress();
        void hideProgress();
        void showSuccess();
        void showDepartmentsList();
    }

    interface Presenter extends BasePresenter {
        void logIn(String login, String password);
    }
}

package com.example.azolotarev.test.UI.Start;

import com.example.azolotarev.test.UI.ProgressContract;
import com.example.azolotarev.test.UI.BasePresenter;
import com.example.azolotarev.test.UI.BaseView;

public interface StartContract {

    interface View extends BaseView<Presenter>,ProgressContract {
        void showConnectionError(String errorMessage);
        void showSuccessError(String errorMessage);
        void showDepartmentsList();
        void showAuthorization();
    }

    interface Presenter extends BasePresenter,ProgressContract{
        void showConnectionError();
    }
}

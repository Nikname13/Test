package com.example.azolotarev.test.UI.DepartmentsList;

import com.example.azolotarev.test.Service.ProgressContract;
import com.example.azolotarev.test.UI.BasePresenter;
import com.example.azolotarev.test.UI.BaseView;
import com.example.azolotarev.test.UI.Start.StartContract;

public interface DepartmentListContract {

    interface View extends BaseView<Presenter>,ProgressContract {
        void showConnectionError();
        void showSuccessError(String errorMessage);
        void showAuthorization();
    }

    interface Presenter extends BasePresenter,ProgressContract{
        void showConnectionError();
    }
}

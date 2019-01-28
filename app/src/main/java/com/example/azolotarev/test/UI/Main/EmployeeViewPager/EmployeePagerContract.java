package com.example.azolotarev.test.UI.Main.EmployeeViewPager;

import android.support.annotation.NonNull;
import com.example.azolotarev.test.Model.MapModel;
import com.example.azolotarev.test.UI.BasePresenter;
import com.example.azolotarev.test.UI.BaseView;
import com.example.azolotarev.test.UI.ProgressContract;

import java.util.List;

public interface EmployeePagerContract {
    interface View extends BaseView<Presenter>, ProgressContract {
        void initViewPager(@NonNull List<MapModel> mapModelList, int startPosition);
        void showAuthorization();
    }
    interface Presenter extends BasePresenter,ProgressContract {
        void start(@NonNull String positionInTree,@NonNull String id, @NonNull String filterString);
        void logOut();
    }
}

package com.example.azolotarev.test.UI.Main.ChildrenElements;

import android.support.annotation.NonNull;
import com.example.azolotarev.test.Model.BaseModel;
import com.example.azolotarev.test.UI.BasePresenter;
import com.example.azolotarev.test.UI.BaseView;
import com.example.azolotarev.test.UI.Main.ItemClickListener;
import com.example.azolotarev.test.UI.ProgressContract;

import java.util.List;

public interface ChildrenContract {

    interface View extends BaseView<Presenter>,ItemClickListener,ProgressContract {
         List<BaseModel> getListModel();
         int getViewType();
        void showListModel(@NonNull List<BaseModel> departmentList, @NonNull int viewType);
        void showChildrenList(@NonNull List<BaseModel> departmentList, @NonNull int containerId, @NonNull int viewType);
        void showChildrenDetail(@NonNull BaseModel model);
    }
    interface Presenter extends BasePresenter,ProgressContract {
        void openDetail(@NonNull BaseModel selectedDepartment, @NonNull int containerId);
    }
}

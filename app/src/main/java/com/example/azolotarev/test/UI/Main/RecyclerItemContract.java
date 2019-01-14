package com.example.azolotarev.test.UI.Main;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import com.example.azolotarev.test.Model.BaseModel;
import com.example.azolotarev.test.Model.DepartmentModel;

public interface RecyclerItemContract {
        void scrollToPosition(@NonNull int position);
        void onClickItem(@NonNull BaseModel model, @NonNull int containerId);
        void removeFragment(@NonNull BaseModel model);
}


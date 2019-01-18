package com.example.azolotarev.test.UI.Main;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import com.example.azolotarev.test.Model.BaseModel;
import com.example.azolotarev.test.Model.DepartmentModel;
import com.example.azolotarev.test.Model.RecyclerModel;

public interface RecyclerItemContract {

    interface itemInPositionCallback{
        void onItem(@NonNull RecyclerModel model);
    }
    void itemInPosition(@NonNull final itemInPositionCallback callback,int position);
    void scrollToPosition(int position);
    void onClickItem(@NonNull RecyclerModel model);
    interface scroll{
            void scrollTo(int position);
    }
}


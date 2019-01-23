package com.example.azolotarev.test.UI.Main;

import android.support.annotation.NonNull;
import com.example.azolotarev.test.Model.MapModel;

public interface RecyclerItemContract {

    interface itemInPositionCallback{
        void onItem(@NonNull MapModel model);
    }
    void itemInPosition(@NonNull final itemInPositionCallback callback,int position);
    void scrollToPosition(int position);
    void onClickItem(@NonNull MapModel model);
    interface scroll{
            void scrollTo(int position);
    }
}


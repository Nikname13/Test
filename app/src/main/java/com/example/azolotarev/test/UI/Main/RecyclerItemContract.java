package com.example.azolotarev.test.UI.Main;

import android.support.annotation.NonNull;
import com.example.azolotarev.test.Model.MapModel;

import java.util.List;

public interface RecyclerItemContract {

    interface ItemInPositionCallback {
        void onItem(@NonNull MapModel model);
    }
    interface RecyclerFilterCallback{
        void onResult(List<Integer> filteredList);
    }
    void onFilter(@NonNull String filterString, @NonNull final RecyclerFilterCallback callback);
    void itemInPosition(@NonNull final ItemInPositionCallback callback, int position);
    void scrollToPosition(int position);
    void onClickItem(@NonNull MapModel model);
    interface scroll{
            void scrollTo(int position);
    }
}


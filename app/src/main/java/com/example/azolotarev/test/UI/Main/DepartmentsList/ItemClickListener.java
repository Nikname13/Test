package com.example.azolotarev.test.UI.Main.DepartmentsList;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import com.example.azolotarev.test.Model.DepartmentModel;

public interface ItemClickListener {
    void onClickItem(@NonNull DepartmentModel department,@NonNull RecyclerView container);
}

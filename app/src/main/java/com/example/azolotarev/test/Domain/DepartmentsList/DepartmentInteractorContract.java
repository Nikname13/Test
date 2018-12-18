package com.example.azolotarev.test.Domain.DepartmentsList;

import android.support.annotation.NonNull;
import com.example.azolotarev.test.Service.ProgressContract;

public interface DepartmentInteractorContract {

    void setProgressListener(@NonNull ProgressContract listener);
}

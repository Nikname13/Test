package com.example.azolotarev.test.Repository;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ListView;
import com.example.azolotarev.test.Model.DepartmentModel;
import com.example.azolotarev.test.Model.EmployeeModel;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JParser implements JParserContract {

    @Override
    public void getDepartments(@NonNull ParsDepartmentsCallback callback, String jsonString) {
        Log.e("TAG", "jparser getDepartments");
        if(jsonString==null)
        Log.e("TAG", "jparser getDepartments jsonString = null");
        try {
            JSONObject json=new JSONObject(jsonString);
            if(json.length()!=0)
            callback.onDepartmentsLoaded(parsJson(json));
            else callback.errorSuccess("Ошибка данных либо пустой список");
        } catch (JSONException e) {
            Log.e("TAG",e.getMessage());
            e.printStackTrace();
            callback.errorSuccess(e.getMessage());
        }
    }

    @Override
    public void getSuccess(@NonNull ParsSuccessCallback callback, String jsonString) {
        try {
            JSONObject json=new JSONObject(jsonString);
            if(!json.getString("Message").equals("null")) callback.errorSuccess(json.getString("Message"));
            callback.onSuccess(json.getBoolean("Success"));
        } catch (JSONException e) {
            Log.e("TAG",e.getMessage());
            callback.onSuccess(false);
            callback.errorSuccess(e.getMessage());
        }
    }

    private List<DepartmentModel> parsJson(JSONObject json) {
        List<DepartmentModel> list=new ArrayList<>();
        for(int i=0;i<25;i++){
            DepartmentModel departmentModel=new DepartmentModel(i,"Отдел "+i);
            List<DepartmentModel> underDepartment=new ArrayList<>();
            List<EmployeeModel> employees=new ArrayList<>();
            for(int y=0;y<10;y++){
               underDepartment.add(new DepartmentModel(y,"Подотдел "+i));
            }
            for(int x=0;x<15;x++){
                employees.add(new EmployeeModel(x,"Сотрудник "+x));
            }
            //if(i%2==0)
                departmentModel.setDepartmentsList(underDepartment);
            //else departmentModel.setEmploeeList(employees);
            list.add(new DepartmentModel(i,"Отдел "+i));
        }
        return list;
    }
}

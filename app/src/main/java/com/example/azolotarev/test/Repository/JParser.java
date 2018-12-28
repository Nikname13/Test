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
        Log.e("TAG", "jparser getListModel");
        if(jsonString==null)
        Log.e("TAG", "jparser getListModel jsonString = null");
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
        for(int i=0;i<100;i++){
            DepartmentModel departmentModel=new DepartmentModel(i,"Отдел "+i);
            List<DepartmentModel> underDepartment=new ArrayList<>();
            List<EmployeeModel> employees=new ArrayList<>();
            for(int y=0;y<=i;y++){
                DepartmentModel childrenDepartment=new DepartmentModel(y,"Подотдел "+y);
               List<DepartmentModel> underunderDepartment=new ArrayList<>();
               List<EmployeeModel> underEmployee=new ArrayList<>();
               for(int z=0;z<=y;z++){
                   underunderDepartment.add(new DepartmentModel(z,"Подотдельный отдел"+z));
               }
                for(int w=0;w<15;w++){
                    underEmployee.add(new EmployeeModel(w,"Сотрудник "+w));
                }
                if(y%2==0)
               childrenDepartment.setDepartmentsList(underunderDepartment);
               else
                   childrenDepartment.setEmploeeList(underEmployee);
                underDepartment.add(childrenDepartment);
            }
            for(int x=0;x<15;x++){
                employees.add(new EmployeeModel(x,"Сотрудник "+x));
            }
            if(i%2==0)
                departmentModel.setDepartmentsList(underDepartment);
            else departmentModel.setEmploeeList(employees);
            list.add(departmentModel);
        }
        return list;
    }
}

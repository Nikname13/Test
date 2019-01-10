package com.example.azolotarev.test.Repository;

import android.support.annotation.NonNull;
import android.util.Log;
import com.example.azolotarev.test.Model.DepartmentModel;
import com.example.azolotarev.test.Model.EmployeeModel;
import org.json.JSONArray;
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
            if(json.length()!=0) {
                List<DepartmentModel> list= getDepartment(json).getDepartmentsList();
               if(list!=null) callback.onDepartmentsLoaded(list);
                else callback.notAvailable("Лист = null");
            }
            else callback.notAvailable("Ошибка данных либо пустой список");
        } catch (JSONException e) {
            Log.e("TAG",e.getMessage());
            e.printStackTrace();
            callback.notAvailable(e.getMessage());
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

    private DepartmentModel getDepartment(JSONObject json) {
        DepartmentModel departmentModel=new DepartmentModel();
        try {
            departmentModel.setId(Integer.valueOf(json.getString("ID")));
            departmentModel.setName(json.getString("Name"));
            if(!json.isNull("Departments")){
                List<DepartmentModel> listDepartments=new ArrayList<>();
                JSONArray jsonArray=json.getJSONArray("Departments");
                for(int i=0;i<jsonArray.length();i++){
                    Log.e("TAG",jsonArray.getJSONObject(i).getString("ID"));
                   listDepartments.add(getDepartment(jsonArray.getJSONObject(i)));
                }
                departmentModel.setDepartmentsList(listDepartments);
                  return departmentModel;
            }
            if(!json.isNull("Employees")){
                List<EmployeeModel> listEmployee=new ArrayList<>();
                    JSONArray jsonArray=json.getJSONArray("Employees");
                    for(int i=0;i<jsonArray.length();i++){
                        Log.e("TAG",jsonArray.getJSONObject(i).getString("ID"));
                        listEmployee.add(getEmployee(jsonArray.getJSONObject(i)));
                    }
                    departmentModel.setEmployeeList(listEmployee);
                    return departmentModel;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    private EmployeeModel getEmployee(JSONObject json){
        EmployeeModel employeeModel=new EmployeeModel();
        try {
            employeeModel.setId(Integer.valueOf(json.getString("ID")));
            employeeModel.setName(json.getString("Name"));
            employeeModel.setTitle(json.getString("Title"));
            employeeModel.setPhone(json.getString("Phone"));
            employeeModel.setEmail(json.getString("Email"));
            return employeeModel;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}

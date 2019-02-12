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
    public void parsDepartments(@NonNull String jsonString, @NonNull ParsDepartmentsCallback callback) {
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
    public void parsSuccess(@NonNull String jsonString, @NonNull ParsSuccessCallback callback) {
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
        DepartmentModel rootDepartment=new DepartmentModel();
        try {
            rootDepartment.setId(json.getString("ID"));
            if(!json.isNull("Name")) rootDepartment.setName(json.getString("Name"));
            if(!json.isNull("Departments")){
                List<DepartmentModel> listDepartments=new ArrayList<>();
                JSONArray jsonArray=json.getJSONArray("Departments");
                for(int i=0;i<jsonArray.length();i++){
                  //  Log.e("TAG",jsonArray.getJSONObject(i).getString("ID"));
                    DepartmentModel childrenDepartment=getDepartment(jsonArray.getJSONObject(i));
                    if(childrenDepartment!=null) {
                        childrenDepartment.setParent(rootDepartment);
                        listDepartments.add(childrenDepartment);
                    }
                }
                rootDepartment.setDepartmentsList(listDepartments);
                return rootDepartment;
            }
            if(!json.isNull("Employees")){
                List<EmployeeModel> listEmployee=new ArrayList<>();
                    JSONArray jsonArray=json.getJSONArray("Employees");
                    for(int i=0;i<jsonArray.length();i++){
                      //  Log.e("TAG",jsonArray.getJSONObject(i).getString("ID"));
                        EmployeeModel employeeModel=getEmployee(jsonArray.getJSONObject(i));
                        if(employeeModel!=null){
                            employeeModel.setParent(rootDepartment);
                            listEmployee.add(employeeModel);
                        }
                    }
                    rootDepartment.setEmployeeList(listEmployee);
                    return rootDepartment;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return rootDepartment;
    }

    private EmployeeModel getEmployee(JSONObject json){
        EmployeeModel employeeModel=new EmployeeModel();
        try {
            employeeModel.setId(json.getString("ID"));
           if(!json.isNull("Name")) employeeModel.setName(json.getString("Name"));
           if(!json.isNull("Title")) employeeModel.setTitle(json.getString("Title"));
           if(!json.isNull("Phone")) employeeModel.setPhone(json.getString("Phone"));
           if(!json.isNull("Email")) employeeModel.setEmail(json.getString("Email"));
            return employeeModel;
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("TAG","getEmployee error");
            return null;
        }
    }

    private List<DepartmentModel> getTestList() {
        List<DepartmentModel> list=new ArrayList<>();
        for(int i=0;i<100;i++){
            DepartmentModel departmentModel=new DepartmentModel(String.valueOf(i),"Отдел "+i);
            List<DepartmentModel> underDepartment=new ArrayList<>();
            List<EmployeeModel> employees=new ArrayList<>();
            for(int y=0;y<=i;y++){
                DepartmentModel childrenDepartment=new DepartmentModel(String.valueOf(y),"Подотдел "+y);
                List<DepartmentModel> underunderDepartment=new ArrayList<>();
                List<EmployeeModel> underEmployee=new ArrayList<>();
                for(int z=0;z<=y;z++){
                    DepartmentModel testdep2Lvl=new DepartmentModel(String.valueOf(z),"Подотдельный отдел"+z);
                    testdep2Lvl.setParent(childrenDepartment);
                    underunderDepartment.add(testdep2Lvl);
                }
                for(int w=0;w<15;w++){
                    EmployeeModel testEmp2Lvl=new EmployeeModel(String.valueOf(w),"Сотрудник "+w);
                    testEmp2Lvl.setParent(childrenDepartment);
                    underEmployee.add(testEmp2Lvl);
                }
                if(y%2==0)
                    childrenDepartment.setDepartmentsList(underunderDepartment);
                else
                    childrenDepartment.setEmployeeList(underEmployee);
                childrenDepartment.setParent(departmentModel);
                underDepartment.add(childrenDepartment);
            }
            for(int x=0;x<15;x++){
                EmployeeModel testEm1lvl=new EmployeeModel(String.valueOf(x),"Сотрудник "+x);
                testEm1lvl.setParent(departmentModel);
                employees.add(testEm1lvl);
            }
            if(i%2==0)
                departmentModel.setDepartmentsList(underDepartment);
            else departmentModel.setEmployeeList(employees);
            departmentModel.setParent(new DepartmentModel("0","parent"));
            list.add(departmentModel);
        }
        return list;
    }
}

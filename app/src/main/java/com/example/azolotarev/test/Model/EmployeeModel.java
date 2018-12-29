package com.example.azolotarev.test.Model;

import android.support.annotation.NonNull;

import java.io.Serializable;

public class EmployeeModel extends BaseModel implements Serializable {

    private String mTitle;
    private String mEmail;
    private String mPhone;

    public EmployeeModel(@NonNull int id,@NonNull String name) {
        super(id, name);
    }

    public EmployeeModel(@NonNull int id, @NonNull String name, String title, String email, String phone) {
        super(id, name);
        mTitle = title;
        mEmail = email;
        mPhone = phone;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }
}

package com.example.azolotarev.test.Service;

import java.util.ArrayList;
import java.util.List;

public class URLBuilder {
    private String mUrl;
    private List<String> mParams;
    private static final String URL="https://contact.taxsee.com/Contacts.svc/";

    public URLBuilder(){
        mUrl="";
        mParams=new ArrayList<>();
    }

    public URLBuilder(String url){
        this();
        mUrl=url;
    }

    public URLBuilder withParam(Object name, Object value) {
        if (name != null && value != null)
            mParams.add(name+"="+value);
        return this;
    }

    public String build(){
        return URL+mUrl+"?"+String.join("&",mParams);
    }
}

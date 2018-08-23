package com.zzeng.deptdemo.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.zzeng.deptdemo.Constant;
import com.zzeng.deptdemo.bean.DeptListBean;
import com.zzeng.deptdemo.util.GetJsonDataUtil;


public class DeptService extends IntentService {

    public DeptService() {
        super("DeptService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        try {
            String jsonData = new GetJsonDataUtil().getJson(this, "Dept.json");
            Gson gson = new Gson();
            Constant.deptListData = gson.fromJson(jsonData, DeptListBean.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.zzeng.deptdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.zzeng.deptdemo.activity.DeptActivity;
import com.zzeng.deptdemo.service.DeptService;

/**
 * 作者: zengxc
 * 描述: 组织架构通讯录实现
 * 时间: 2018/08/20 17:51
 */

public class MainActivity extends AppCompatActivity {

    private Button mBtnDept;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnDept = (Button) findViewById(R.id.btn_dept);
        mBtnDept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DeptActivity.class));
            }
        });
        startService(new Intent(MainActivity.this, DeptService.class));
    }

}

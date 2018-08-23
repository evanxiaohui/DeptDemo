package com.zzeng.deptdemo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.zzeng.deptdemo.R;
import com.zzeng.deptdemo.activity.fragment.DeptFragment;

/**
 * 作者: zengxc
 * 描述: 组织架构列表
 * 时间: 2018/08/20 17:57
 */

public class DeptActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dept);
        init();
        initDeptFragment(true);
    }

    private void init() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_back_action);

    }

    private void initDeptFragment( boolean isRoot) {
        Fragment mDeptFragment = new DeptFragment();
        FragmentManager manager = getSupportFragmentManager();
        Bundle bundle = new Bundle();
        bundle.putBoolean("isRoot", isRoot);
        mDeptFragment.setArguments(bundle);
        manager.beginTransaction().replace(R.id.dept_content, mDeptFragment).commit();
    }
}

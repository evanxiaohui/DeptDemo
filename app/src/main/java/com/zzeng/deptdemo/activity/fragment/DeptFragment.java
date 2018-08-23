package com.zzeng.deptdemo.activity.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zzeng.deptdemo.Constant;
import com.zzeng.deptdemo.R;
import com.zzeng.deptdemo.adapter.BaseRecycleAdapter;
import com.zzeng.deptdemo.adapter.BaseRecycleHolder;
import com.zzeng.deptdemo.bean.DeptListBean;

import java.util.ArrayList;
import java.util.List;


/**
 * 作者: zengxc
 * 描述: 展示部门  员工
 * 时间: 2018/08/20 18:03
 */

public class DeptFragment extends BaseFragment {

    private RecyclerView mRvDept;
    private RecyclerView mRvDeptUser;
    private Toolbar mToolbar;
    private LinearLayout mDeptNavLayout;
    private HorizontalScrollView mHsvNav;

    private View view;

    /**
     * 标志位，标志已经初始化完成
     */
    private boolean isPrepared;
    /**
     * 是否已被加载过一次，第二次就不再去请求数据了
     */
    private boolean mHasLoadedOnce;

    private BaseRecycleAdapter<DeptListBean.DataBean.DepartmentListBean> mDeptAdapter;
    private List<DeptListBean.DataBean.DepartmentListBean> mDeptList = new ArrayList<>();

    private BaseRecycleAdapter<String> mDeptUserAdapter;
    private List<String> mDeptUserList = new ArrayList<>();

    private List<String> mNavNameList = new ArrayList<>();

    private boolean isRoot;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_dept, container, false);
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        isPrepared = true;
        lazyLoad();
        setListeners();
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || mHasLoadedOnce) {
            return;
        }
        initData();
    }

    @Override
    protected void initViews() {

        mToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        mDeptNavLayout = (LinearLayout) getActivity().findViewById(R.id.ll_dept_nav);
        mHsvNav = (HorizontalScrollView) getActivity().findViewById(R.id.hsv_nav);
        mRvDept = (RecyclerView) view.findViewById(R.id.rv_dept);
        mRvDeptUser = (RecyclerView) view.findViewById(R.id.rv_dept_user);

        mDeptList = getArguments().getParcelableArrayList("deptList");
        mDeptUserList = getArguments().getStringArrayList("deptUserList");
        isRoot = getArguments().getBoolean("isRoot");

        mRvDept.setNestedScrollingEnabled(false);
        mRvDeptUser.setNestedScrollingEnabled(false);

        initDeptNameNav();
    }

    @Override
    protected void initData() {
        if (isRoot) {
            getDeptList();
        } else {
            mRvDept.setVisibility(mDeptList != null && mDeptList.size() > 0 ? View.VISIBLE : View.GONE);
            mRvDeptUser.setVisibility(mDeptUserList != null && mDeptUserList.size() > 0 ? View.VISIBLE : View.GONE);
        }
    }

    /**
     * 获取部门数据
     */
    private void getDeptList() {
        mDeptList = Constant.deptListData.getData().getDepartmentList();
        mToolbar.setTitle(Constant.deptListData.getData().getCname());
    }

    @Override
    protected void setListeners() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity().getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    getActivity().getSupportFragmentManager().popBackStack();
                } else {
                    getActivity().finish();
                }
            }
        });

        mDeptAdapter = new BaseRecycleAdapter<DeptListBean.DataBean.DepartmentListBean>(
                getActivity(), R.layout.item_dept, mDeptList) {
            @Override
            public void convert(BaseRecycleHolder helper, DeptListBean.DataBean.DepartmentListBean item, int position) {
                TextView mTvDeptName = helper.getView(R.id.tv_dept_name);
                mTvDeptName.setText(item.getDeptName() + "(" + item.getCreator() + ")");
            }
        };

        mDeptAdapter.setOnItemClickListener(new BaseRecycleAdapter.OnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                initDeptFragment(
                        (ArrayList<DeptListBean.DataBean.DepartmentListBean>) mDeptList
                                .get(position).getChildDepartment(),
                        (ArrayList<String>) mDeptList.get(position).getListUser(),
                        mDeptList.get(position).getDeptName());

            }
        });
        mRvDept.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRvDept.setAdapter(mDeptAdapter);

        mDeptUserAdapter = new BaseRecycleAdapter<String>(
                getActivity(), R.layout.item_dept_user, mDeptUserList) {
            @Override
            public void convert(BaseRecycleHolder helper, final String item, int position) {
                TextView mTvUserName = helper.getView(R.id.tv_user_name);
                ImageView mIvCall = helper.getView(R.id.iv_call);

                mTvUserName.setText(item);

                mIvCall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(), "拨打" + item + "电话", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };
        mRvDeptUser.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRvDeptUser.setAdapter(mDeptUserAdapter);

    }

    private void initDeptNameNavList() {
        if (mNavNameList == null) {
            mNavNameList = new ArrayList<>();
        }
        mNavNameList.clear();
        String cName = Constant.deptListData.getData().getCname();
        if (!TextUtils.isEmpty(cName)) mNavNameList.add(cName);
        for (int i = 0; i < getActivity().getSupportFragmentManager().getBackStackEntryCount(); i++) {
            mNavNameList.add(getActivity().getSupportFragmentManager().getBackStackEntryAt(i).getName());
        }
    }

    private void initDeptNameNav() {
        initDeptNameNavList();
        mDeptNavLayout.removeAllViews();
        for (int i = 0; i < mNavNameList.size(); i++) {
            final LinearLayout mNavView = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.item_dept_name_show, null);
            ImageView mIvNext = (ImageView) mNavView.findViewById(R.id.iv_next);
            TextView mTvNav = (TextView) mNavView.findViewById(R.id.tv_nav_name);

            mNavView.setTag(i);//设置下标为tag,方便后面点击事件处理数据
            mIvNext.setVisibility(i == 0 ? View.GONE : View.VISIBLE);
            mTvNav.setText(mNavNameList.get(i));

            if (mNavNameList.size() > 1) {//二级部门以上
                if (i == mNavNameList.size() - 1) {
                    mTvNav.setTextColor(Color.parseColor("#808080"));
                } else {
                    mTvNav.setTextColor(Color.parseColor("#E04E4E"));
                }
            } else {//一级部门
                mTvNav.setTextColor(Color.parseColor("#808080"));
            }

            mNavView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int currentIndex = 0;

                    if (mNavView.getTag() != null) {
                        currentIndex = (int) mNavView.getTag();
                    }

                    if (currentIndex == 0) {//点击回到一级部门,清除所有回退栈
                        if (mNavNameList.size() > 1) {
                            getActivity().getSupportFragmentManager().popBackStackImmediate(mNavNameList.get(1), FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        }
                    } else {
                        getActivity().getSupportFragmentManager().popBackStackImmediate(mNavNameList.get(currentIndex), 0);
                    }

                }
            });

            mDeptNavLayout.addView(mNavView);
            mHsvNav.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mHsvNav.fullScroll(HorizontalScrollView.FOCUS_RIGHT); //方法不能直接被调用,可能会不生效,需先加入消息对列等待调用
                }
            }, 100L);
        }

    }

    private void initDeptFragment(ArrayList<DeptListBean.DataBean.DepartmentListBean> mDeptList,
                                  ArrayList<String> mDeptUserList, String tag) {
        Fragment mDeptFragment = new DeptFragment();
        FragmentManager manager = getActivity().getSupportFragmentManager();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("deptList", mDeptList);
        bundle.putStringArrayList("deptUserList", mDeptUserList);
        mDeptFragment.setArguments(bundle);
        manager.beginTransaction()
                .replace(R.id.dept_content, mDeptFragment)
                .addToBackStack(tag)
                .commit();
    }

}

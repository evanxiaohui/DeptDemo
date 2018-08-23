package com.zzeng.deptdemo.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;


public class DeptListBean {

    private DataBean data;
    private String responseCode;
    private boolean isSuccess;
    private String responseMsg;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getResponseCode() {
        return responseCode == null ? "" : responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getResponseMsg() {
        return responseMsg == null ? "" : responseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }

    public static class DataBean {

        private int id;
        private String cname;
        private List<DepartmentListBean> departmentList;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCname() {
            return cname == null ? "" : cname;
        }

        public void setCname(String cname) {
            this.cname = cname;
        }

        public List<DepartmentListBean> getDepartmentList() {
            if (departmentList == null) {
                return new ArrayList<>();
            }
            return departmentList;
        }

        public void setDepartmentList(List<DepartmentListBean> departmentList) {
            this.departmentList = departmentList;
        }

        public static class DepartmentListBean implements Parcelable {

            private int id;
            private String deptName;
            private String pid;
            private int cid;
            private String cname;
            private int creator;
            private List<String> listUser;
            private List<DepartmentListBean> childDepartment;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getDeptName() {
                return deptName == null ? "" : deptName;
            }

            public void setDeptName(String deptName) {
                this.deptName = deptName;
            }

            public String getPid() {
                return pid == null ? "" : pid;
            }

            public void setPid(String pid) {
                this.pid = pid;
            }

            public int getCid() {
                return cid;
            }

            public void setCid(int cid) {
                this.cid = cid;
            }

            public String getCname() {
                return cname == null ? "" : cname;
            }

            public void setCname(String cname) {
                this.cname = cname;
            }

            public int getCreator() {
                return creator;
            }

            public void setCreator(int creator) {
                this.creator = creator;
            }

            public List<String> getListUser() {
                if (listUser == null) {
                    return new ArrayList<>();
                }
                return listUser;
            }

            public void setListUser(List<String> listUser) {
                this.listUser = listUser;
            }

            public List<DepartmentListBean> getChildDepartment() {
                if (childDepartment == null) {
                    return new ArrayList<>();
                }
                return childDepartment;
            }

            public void setChildDepartment(List<DepartmentListBean> childDepartment) {
                this.childDepartment = childDepartment;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(this.id);
                dest.writeString(this.deptName);
                dest.writeString(this.pid);
                dest.writeInt(this.cid);
                dest.writeString(this.cname);
                dest.writeInt(this.creator);
                dest.writeStringList(this.listUser);
                dest.writeList(this.childDepartment);
            }

            public DepartmentListBean() {
            }

            protected DepartmentListBean(Parcel in) {
                this.id = in.readInt();
                this.deptName = in.readString();
                this.pid = in.readString();
                this.cid = in.readInt();
                this.cname = in.readString();
                this.creator = in.readInt();
                this.listUser = in.createStringArrayList();
                this.childDepartment = new ArrayList<DepartmentListBean>();
                in.readList(this.childDepartment, DepartmentListBean.class.getClassLoader());
            }

            public static final Parcelable.Creator<DepartmentListBean> CREATOR = new Parcelable.Creator<DepartmentListBean>() {
                @Override
                public DepartmentListBean createFromParcel(Parcel source) {
                    return new DepartmentListBean(source);
                }

                @Override
                public DepartmentListBean[] newArray(int size) {
                    return new DepartmentListBean[size];
                }
            };
        }
    }
}

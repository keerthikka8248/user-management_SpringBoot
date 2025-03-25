package com.example.user.Entity;

public class Classroom {
    private int cid;
    private String dept;
    private String sec;
    public Classroom(int cid, String dept, String sec) {
        this.cid = cid;
        this.dept = dept;
        this.sec = sec;
    }

    public int getCid() {
        return cid;
    }

    public String getDept() {
        return dept;
    }

    public String getSec() {
        return sec;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public void setSec(String sec) {
        this.sec = sec;
    }
}

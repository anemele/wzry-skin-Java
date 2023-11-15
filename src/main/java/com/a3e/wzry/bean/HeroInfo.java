package com.a3e.wzry.bean;

public class HeroInfo {
    private String title, cname;
    private int ename;
    public HeroInfo(String title, String cname, int ename) {
        this.title = title;
        this.cname = cname;
        this.ename = ename;
    }
    @Override
    public String toString() {
        return title + "_" + cname;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getCname() {
        return cname;
    }
    public void setCname(String cname) {
        this.cname = cname;
    }
    public int getEname() {
        return ename;
    }
    public void setEname(int ename) {
        this.ename = ename;
    }
}

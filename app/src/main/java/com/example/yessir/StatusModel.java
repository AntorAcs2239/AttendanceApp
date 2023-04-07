package com.example.yessir;

public class StatusModel {
    private String status,date;
    private long sid,cid,statusid;

    public StatusModel(String status, String date, long sid, long cid, long statusid) {
        this.status = status;
        this.date = date;
        this.sid = sid;
        this.cid = cid;
        this.statusid = statusid;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getSid() {
        return sid;
    }

    public void setSid(long sid) {
        this.sid = sid;
    }

    public long getCid() {
        return cid;
    }

    public void setCid(long cid) {
        this.cid = cid;
    }

    public long getStatusid() {
        return statusid;
    }

    public void setStatusid(long statusid) {
        this.statusid = statusid;
    }
}

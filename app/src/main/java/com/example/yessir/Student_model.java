package com.example.yessir;

public class Student_model {
    private String student_name;
    private String status;
    private String date;
    private long Sid;
    int roll;
    public Student_model(String student_name, long sid, int roll,String date) {
        this.student_name = student_name;
        Sid = sid;
        this.roll = roll;
        this.date=date;
        status="";
    }
    public Student_model(String student_name, long sid, int roll,String date,String status) {
        this.student_name = student_name;
        Sid = sid;
        this.roll = roll;
        this.date=date;
        this.status=status;
    }
    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getSid() {
        return Sid;
    }
    public void setSid(long sid) {
        Sid = sid;
    }

    public int getRoll() {
        return roll;
    }

    public void setRoll(int roll) {
        this.roll = roll;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}

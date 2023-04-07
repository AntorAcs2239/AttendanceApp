package com.example.yessir;

public class SearchonlyStudetnModel {
    private String student_name,student_roll,Date,status,Subject_name;

    public SearchonlyStudetnModel(String student_name, String student_roll, String date, String status, String subject_name) {
        this.student_name = student_name;
        this.student_roll = student_roll;
        Date = date;
        this.status = status;
        Subject_name = subject_name;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getStudent_roll() {
        return student_roll;
    }

    public void setStudent_roll(String student_roll) {
        this.student_roll = student_roll;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubject_name() {
        return Subject_name;
    }

    public void setSubject_name(String subject_name) {
        Subject_name = subject_name;
    }
}

package com.example.yessir;

public class Class_item {
    private String classname,subjectname;
    private long ID;

    public Class_item(String classname, String subjectname, long ID) {
        this.classname = classname;
        this.subjectname = subjectname;
        this.ID = ID;
    }
    public Class_item(String classname, String subjectname) {
        this.classname = classname;
        this.subjectname = subjectname;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getSubjectname() {
        return subjectname;
    }

    public void setSubjectname(String subjectname) {
        this.subjectname = subjectname;
    }
    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

}

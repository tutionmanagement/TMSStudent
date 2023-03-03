package com.example.tms;

import java.util.ArrayList;

public class StudentModel {
    private String tcName;
    private String fullName;
    private int standards;
    private ArrayList<String> subjects;
    private String email;

    public StudentModel(String tcName, String fullName, int standards, ArrayList<String> subjects, String email) {
        this.tcName = tcName;
        this.fullName = fullName;
        this.standards = standards;
        this.subjects = subjects;
        this.email = email;
    }

    public String getTcName() {
        return tcName;
    }

    public void setTcName(String tcName) {
        this.tcName = tcName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getStandards() {
        return standards;
    }

    public void setStandards(int standards) {
        this.standards = standards;
    }

    public ArrayList<String> getSubjects() {
        return subjects;
    }

    public void setSubjects(ArrayList<String> subjects) {
        this.subjects = subjects;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

package com.example.tms;

import java.math.BigInteger;
import java.util.ArrayList;

public class StudentModel {
    private String tcName,fullName,email,phoneNumber;
    private int standards;
    private ArrayList<String> subjects;

    public StudentModel(String tcName, String fullName, int standards, ArrayList<String> subjects, String email, String phoneNumber) {
        this.tcName = tcName;
        this.fullName = fullName;
        this.standards = standards;
        this.subjects = subjects;
        this.email = email;
        this.phoneNumber = phoneNumber;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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
}

package com.longwang.uhrm.Tool;

import java.util.Arrays;

public class convertdata {
    private int employeeId;
    private String employeeName;
    private String employeeSex;
    private String employeeBirthday;
    private String employeeAddress;
    private String employeePhone;
    private String[] changeInfoOriginal;
    private String[] changeInfoNow;
    private String[] selectedInfo;

    public String[] getChangeInfoOriginal() {
        return changeInfoOriginal;
    }

    public void setChangeInfoOriginal(String[] changeInfoOriginal) {
        this.changeInfoOriginal = changeInfoOriginal;
    }

    public String[] getChangeInfoNow() {
        return changeInfoNow;
    }

    public void setChangeInfoNow(String[] changeInfoNow) {
        this.changeInfoNow = changeInfoNow;
    }

    public String[] getSelectedInfo() {
        return selectedInfo;
    }

    public void setSelectedInfo(String[] selectedInfo) {
        this.selectedInfo = selectedInfo;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeSex() {
        return employeeSex;
    }

    public void setEmployeeSex(String employeeSex) {
        this.employeeSex = employeeSex;
    }

    public String getEmployeeBirthday() {
        return employeeBirthday;
    }

    public void setEmployeeBirthday(String employeeBirthday) {
        this.employeeBirthday = employeeBirthday;
    }

    public String getEmployeeAddress() {
        return employeeAddress;
    }

    public void setEmployeeAddress(String employeeAddress) {
        this.employeeAddress = employeeAddress;
    }

    public String getEmployeePhone() {
        return employeePhone;
    }

    public void setEmployeePhone(String employeePhone) {
        this.employeePhone = employeePhone;
    }


}

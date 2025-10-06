package com.rahul.spbcollegeduplicate.PrincipalModule.FireBaseExtraClasses;

public class RegisterPrincipalHelperClass {

    String fullName;
    String mobileNo;
    String eMailId;
    String addharNo;
    String panNo;
    String username;
    String password;

    public RegisterPrincipalHelperClass(String fullName, String mobileNo, String eMailId, String addharNo, String panNo, String username, String password) {
        this.fullName = fullName;
        this.mobileNo = mobileNo;
        this.eMailId = eMailId;
        this.addharNo = addharNo;
        this.panNo = panNo;
        this.username = username;
        this.password = password;
    }


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String geteMailId() {
        return eMailId;
    }

    public void seteMailId(String eMailId) {
        this.eMailId = eMailId;
    }

    public String getAddharNo() {
        return addharNo;
    }

    public void setAddharNo(String addharNo) {
        this.addharNo = addharNo;
    }

    public String getPanNo() {
        return panNo;
    }

    public void setPanNo(String panNo) {
        this.panNo = panNo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RegisterPrincipalHelperClass() {
    }


}

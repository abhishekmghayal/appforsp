package com.rahul.spbcollegeduplicate.PrincipalModule.FireBaseExtraClasses;

public class AddClerkProfileToDatabaseHelperClass {

    String clerkFullName;
    String clerkId;
    String clerkMobileNo;
    String clerkEMailId;
    String clerkAadharNo;
    String clerkPanNo;
    String clerkUsername;
    String clerkPassword;

    public AddClerkProfileToDatabaseHelperClass() {
    }

    public AddClerkProfileToDatabaseHelperClass(String clerkFullName, String clerkId, String clerkMobileNo, String clerkEMailId, String clerkAadharNo, String clerkPanNo, String clerkUsername, String clerkPassword) {
        this.clerkFullName = clerkFullName;
        this.clerkId = clerkId;
        this.clerkMobileNo = clerkMobileNo;
        this.clerkEMailId = clerkEMailId;
        this.clerkAadharNo = clerkAadharNo;
        this.clerkPanNo = clerkPanNo;
        this.clerkUsername = clerkUsername;
        this.clerkPassword = clerkPassword;
    }

    public String getClerkFullName() {
        return clerkFullName;
    }

    public void setClerkFullName(String clerkFullName) {
        this.clerkFullName = clerkFullName;
    }

    public String getClerkId() {
        return clerkId;
    }

    public void setClerkId(String clerkId) {
        this.clerkId = clerkId;
    }

    public String getClerkMobileNo() {
        return clerkMobileNo;
    }

    public void setClerkMobileNo(String clerkMobileNo) {
        this.clerkMobileNo = clerkMobileNo;
    }

    public String getClerkEMailId() {
        return clerkEMailId;
    }

    public void setClerkEMailId(String clerkEMailId) {
        this.clerkEMailId = clerkEMailId;
    }

    public String getClerkAadharNo() {
        return clerkAadharNo;
    }

    public void setClerkAadharNo(String clerkAadharNo) {
        this.clerkAadharNo = clerkAadharNo;
    }

    public String getClerkPanNo() {
        return clerkPanNo;
    }

    public void setClerkPanNo(String clerkPanNo) {
        this.clerkPanNo = clerkPanNo;
    }

    public String getClerkUsername() {
        return clerkUsername;
    }

    public void setClerkUsername(String clerkUsername) {
        this.clerkUsername = clerkUsername;
    }

    public String getClerkPassword() {
        return clerkPassword;
    }

    public void setClerkPassword(String clerkPassword) {
        this.clerkPassword = clerkPassword;
    }
}

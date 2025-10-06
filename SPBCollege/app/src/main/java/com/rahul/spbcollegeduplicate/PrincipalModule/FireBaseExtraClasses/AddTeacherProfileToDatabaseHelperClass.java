package com.rahul.spbcollegeduplicate.PrincipalModule.FireBaseExtraClasses;

public class AddTeacherProfileToDatabaseHelperClass {

    String teacherFullName;
    String teacherId;
    String teacherMobileNo;
    String teacherEMailId;
    String teacherAadharNo;
    String teacherPanNo;
    String teacherUsername;
    String teacherPassword;

    public AddTeacherProfileToDatabaseHelperClass() {
    }

    public AddTeacherProfileToDatabaseHelperClass(String teacherFullName, String teacherId, String teacherMobileNo, String teacherEMailId, String teacherAadharNo, String teacherPanNo, String teacherUsername, String teacherPassword) {
        this.teacherFullName = teacherFullName;
        this.teacherId = teacherId;
        this.teacherMobileNo = teacherMobileNo;
        this.teacherEMailId = teacherEMailId;
        this.teacherAadharNo = teacherAadharNo;
        this.teacherPanNo = teacherPanNo;
        this.teacherUsername = teacherUsername;
        this.teacherPassword = teacherPassword;
    }

    public String getteacherFullName() {
        return teacherFullName;
    }

    public void setteacherFullName(String teacherFullName) {
        this.teacherFullName = teacherFullName;
    }

    public String getteacherId() {
        return teacherId;
    }

    public void setteacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getteacherMobileNo() {
        return teacherMobileNo;
    }

    public void setteacherMobileNo(String teacherMobileNo) {
        this.teacherMobileNo = teacherMobileNo;
    }

    public String getteacherEMailId() {
        return teacherEMailId;
    }

    public void setteacherEMailId(String teacherEMailId) {
        this.teacherEMailId = teacherEMailId;
    }

    public String getteacherAadharNo() {
        return teacherAadharNo;
    }

    public void setteacherAadharNo(String teacherAadharNo) {
        this.teacherAadharNo = teacherAadharNo;
    }

    public String getteacherPanNo() {
        return teacherPanNo;
    }

    public void setteacherPanNo(String teacherPanNo) {
        this.teacherPanNo = teacherPanNo;
    }

    public String getteacherUsername() {
        return teacherUsername;
    }

    public void setteacherUsername(String teacherUsername) {
        this.teacherUsername = teacherUsername;
    }

    public String getteacherPassword() {
        return teacherPassword;
    }

    public void setteacherPassword(String teacherPassword) {
        this.teacherPassword = teacherPassword;
    }
}

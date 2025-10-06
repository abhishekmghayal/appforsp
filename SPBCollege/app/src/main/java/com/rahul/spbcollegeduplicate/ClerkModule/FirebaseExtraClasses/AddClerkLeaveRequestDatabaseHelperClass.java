package com.rahul.spbcollegeduplicate.ClerkModule.FirebaseExtraClasses;

public class AddClerkLeaveRequestDatabaseHelperClass {

    String staffName;
    String staffId;
    String staffDesignationOfLeave;
    String staffLeaveStartDate;
    String staffLeaveEndDate;
    String staffLeaveTitle;
    String staffLeaveDescription;

    public AddClerkLeaveRequestDatabaseHelperClass() {
    }

    public AddClerkLeaveRequestDatabaseHelperClass(String staffId, String staffName, String staffDesignationOfLeave, String staffLeaveStartDate, String staffLeaveEndDate, String staffLeaveTitle, String staffLeaveDescription) {
        this.staffName = staffName;
        this.staffId = staffId;
        this.staffDesignationOfLeave = staffDesignationOfLeave;
        this.staffLeaveStartDate = staffLeaveStartDate;
        this.staffLeaveEndDate = staffLeaveEndDate;
        this.staffLeaveTitle = staffLeaveTitle;
        this.staffLeaveDescription = staffLeaveDescription;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getStaffDesignationOfLeave() {
        return staffDesignationOfLeave;
    }

    public void setStaffDesignationOfLeave(String staffDesignationOfLeave) {
        this.staffDesignationOfLeave = staffDesignationOfLeave;
    }

    public String getStaffLeaveStartDate() {
        return staffLeaveStartDate;
    }

    public void setStaffLeaveStartDate(String staffLeaveStartDate) {
        this.staffLeaveStartDate = staffLeaveStartDate;
    }

    public String getStaffLeaveEndDate() {
        return staffLeaveEndDate;
    }

    public void setStaffLeaveEndDate(String staffLeaveEndDate) {
        this.staffLeaveEndDate = staffLeaveEndDate;
    }

    public String getStaffLeaveTitle() {
        return staffLeaveTitle;
    }

    public void setStaffLeaveTitle(String staffLeaveTitle) {
        this.staffLeaveTitle = staffLeaveTitle;
    }

    public String getStaffLeaveDescription() {
        return staffLeaveDescription;
    }

    public void setStaffLeaveDescription(String staffLeaveDescription) {
        this.staffLeaveDescription = staffLeaveDescription;
    }
}

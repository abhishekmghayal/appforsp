package com.rahul.spbcollegeduplicate.PrincipalModule.ExtraClasses;

public class LeaveRequestModelClass {
         private String staffId;
         private String staffName;
         private String staffLeaveFrom;
         private String staffLeaveTo;


    public LeaveRequestModelClass() {
    }

    public LeaveRequestModelClass(String staffId, String staffName, String staffLeaveFrom, String staffLeaveTo) {
        this.staffId = staffId;
        this.staffName = staffName;
        this.staffLeaveFrom = staffLeaveFrom;
        this.staffLeaveTo = staffLeaveTo;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getStaffLeaveFrom() {
        return staffLeaveFrom;
    }

    public void setStaffLeaveFrom(String staffLeaveFrom) {
        this.staffLeaveFrom = staffLeaveFrom;
    }

    public String getStaffLeaveTo() {
        return staffLeaveTo;
    }

    public void setStaffLeaveTo(String staffLeaveTo) {
        this.staffLeaveTo = staffLeaveTo;
    }
}

package com.rahul.spbcollegeduplicate.ClerkModule.FirebaseExtraClasses;

public class AddStudentIcardDatabaseHelperClass {

    String studentFullName;
    String studentDOB;
    String studentAcademicYear;
    String studentGeneralRegisterNumber;
    String studentStandard;
    String studentAddress;
    String imageUrl; // Add this field for storing the image URL

    public AddStudentIcardDatabaseHelperClass() {
    }

    public AddStudentIcardDatabaseHelperClass(String studentFullName, String studentDOB, String studentAcademicYear, String studentGeneralRegisterNumber, String studentStandard, String studentAddress, String imageUrl) {
        this.studentFullName = studentFullName;
        this.studentDOB = studentDOB;
        this.studentAcademicYear = studentAcademicYear;
        this.studentGeneralRegisterNumber = studentGeneralRegisterNumber;
        this.studentStandard = studentStandard;
        this.studentAddress = studentAddress;
        this.imageUrl = imageUrl;
    }

    public String getStudentFullName() {
        return studentFullName;
    }

    public void setStudentFullName(String studentFullName) {
        this.studentFullName = studentFullName;
    }

    public String getStudentDOB() {
        return studentDOB;
    }

    public void setStudentDOB(String studentDOB) {
        this.studentDOB = studentDOB;
    }

    public String getStudentAcademicYear() {
        return studentAcademicYear;
    }

    public void setStudentAcademicYear(String studentAcademicYear) {
        this.studentAcademicYear = studentAcademicYear;
    }

    public String getStudentGeneralRegisterNumber() {
        return studentGeneralRegisterNumber;
    }

    public void setStudentGeneralRegisterNumber(String studentGeneralRegisterNumber) {
        this.studentGeneralRegisterNumber = studentGeneralRegisterNumber;
    }

    public String getStudentStandard() {
        return studentStandard;
    }

    public void setStudentStandard(String studentStandard) {
        this.studentStandard = studentStandard;
    }

    public String getStudentAddress() {
        return studentAddress;
    }

    public void setStudentAddress(String studentAddress) {
        this.studentAddress = studentAddress;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

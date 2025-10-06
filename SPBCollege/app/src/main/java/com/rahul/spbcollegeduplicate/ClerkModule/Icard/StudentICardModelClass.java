package com.rahul.spbcollegeduplicate.ClerkModule.Icard;

public class StudentICardModelClass {

    private String studentFullName;
    private String studentGeneralRegisterNumber;
    private String studentStandard;
    private String studentAcademicYear;
    private String imageUrl; // Added imageUrl field

    public StudentICardModelClass() {
        // Default constructor required for Firebase
    }

    public StudentICardModelClass(String studentFullName, String studentGeneralRegisterNumber, String studentStandard, String studentAcademicYear, String imageUrl) {
        this.studentFullName = studentFullName;
        this.studentGeneralRegisterNumber = studentGeneralRegisterNumber;
        this.studentStandard = studentStandard;
        this.studentAcademicYear = studentAcademicYear;
        this.imageUrl = imageUrl;
    }

    public String getStudentFullName() {
        return studentFullName;
    }

    public void setStudentFullName(String studentFullName) {
        this.studentFullName = studentFullName;
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

    public String getStudentAcademicYear() {
        return studentAcademicYear;
    }

    public void setStudentAcademicYear(String studentAcademicYear) {
        this.studentAcademicYear = studentAcademicYear;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

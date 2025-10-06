package com.rahul.spbcollegeduplicate.ClerkModule.FirebaseExtraClasses;

public class AddBonafideCertificateDatabaseHelperClass {
    String StudentName;
    String StudentDOB;
    String StudentDOBInLetter;
    String StudentLearningYear;
    String StudentLearningStandard;
    String StudentCaste;
    String StudentBirthPlace;

    public AddBonafideCertificateDatabaseHelperClass(String studentName) {
        StudentName = studentName;
    }

    public AddBonafideCertificateDatabaseHelperClass(String studentName, String studentDOB, String studentDOBInLetter, String studentLearningYear, String studentLearningStandard, String studentCaste, String studentBirthPlace) {
        StudentName = studentName;
        StudentDOB = studentDOB;
        StudentDOBInLetter = studentDOBInLetter;
        StudentLearningYear = studentLearningYear;
        StudentLearningStandard = studentLearningStandard;
        StudentCaste = studentCaste;
        StudentBirthPlace = studentBirthPlace;
    }

    public String getStudentName() {
        return StudentName;
    }

    public void setStudentName(String studentName) {
        StudentName = studentName;
    }

    public String getStudentDOB() {
        return StudentDOB;
    }

    public void setStudentDOB(String studentDOB) {
        StudentDOB = studentDOB;
    }

    public String getStudentDOBInLetter() {
        return StudentDOBInLetter;
    }

    public void setStudentDOBInLetter(String studentDOBInLetter) {
        StudentDOBInLetter = studentDOBInLetter;
    }

    public String getStudentLearningYear() {
        return StudentLearningYear;
    }

    public void setStudentLearningYear(String studentLearningYear) {
        StudentLearningYear = studentLearningYear;
    }

    public String getStudentLearningStandard() {
        return StudentLearningStandard;
    }

    public void setStudentLearningStandard(String studentLearningStandard) {
        StudentLearningStandard = studentLearningStandard;
    }

    public String getStudentCaste() {
        return StudentCaste;
    }

    public void setStudentCaste(String studentCaste) {
        StudentCaste = studentCaste;
    }

    public String getStudentBirthPlace() {
        return StudentBirthPlace;
    }

    public void setStudentBirthPlace(String studentBirthPlace) {
        StudentBirthPlace = studentBirthPlace;
    }
}

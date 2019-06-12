package com.example.user.talleristamod.PackageProfiles.ProfileEstudiante;

public class ObjectEstudiante {

    private String studentId;
    private String studentName;
    private String studentPass;

    public ObjectEstudiante() {}

    public ObjectEstudiante(String studentId, String studentName, String studentPass) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentPass = studentPass;
    }

    //Get and Set methods

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentPass() {
        return studentPass;
    }

    public void setStudentPass(String studentPass) {
        this.studentPass = studentPass;
    }
}

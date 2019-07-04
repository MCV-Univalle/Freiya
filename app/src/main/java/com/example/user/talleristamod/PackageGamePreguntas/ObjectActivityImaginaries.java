package com.example.user.talleristamod.PackageGamePreguntas;

public class ObjectActivityImaginaries {

    private String id ;
    private String nombre ;
    private String pregunta;
    private String joinCode;
    private String stateA;
    private boolean copyA;

    public ObjectActivityImaginaries() {
    }

    public ObjectActivityImaginaries(String id, String nombre, String pregunta, String joinCode, String stateA, boolean copyA) {
        this.id = id;
        this.nombre = nombre;
        this.pregunta = pregunta;
        this.joinCode = joinCode;
        this.stateA = stateA;
        this.copyA = copyA;
    }

    public ObjectActivityImaginaries(String nombre, String pregunta, String joinCode, String stateA) {
        this.nombre = nombre;
        this.pregunta = pregunta;
        this.joinCode = joinCode;
        this.stateA = stateA;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public String getJoinCode() {
        return joinCode;
    }

    public void setJoinCode(String state) {
        this.joinCode = state;
    }

    public String getStateA() {
        return stateA;
    }

    public void setStateA(String stateA) {
        this.stateA = stateA;
    }

    public boolean isCopyA() {
        return copyA;
    }

    public void setCopyA(boolean copyA) {
        this.copyA = copyA;
    }


}

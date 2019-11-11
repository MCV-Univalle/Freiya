package com.example.user.talleristamod.PackageGameCuatro;

import java.util.ArrayList;
import java.util.List;

public class ObjectActivityInterPlay
{

    private String id ;
    private String nombre ;
    private String joinCode;
    private String stateA, creator;
    private String copyA;
    private int temporizador;
    private List<String> idQuestions;

    public ObjectActivityInterPlay(String id, String nombre, List<String> idQuestions, String joinCode, String stateA,String creator, String copyA) {
        this.id = id;
        this.nombre = nombre;
        this.idQuestions = idQuestions;
        this.joinCode = joinCode;
        this.stateA = stateA;
        this.copyA = copyA;
        this.creator = creator;
    }

    public ObjectActivityInterPlay(String nombre, List<String> idQuestions, String joinCode, String stateA) {
        this.nombre = nombre;
        this.idQuestions = idQuestions;
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

    public String isCopyA() {
        return copyA;
    }

    public void setCopyA(String copyA) {
        this.copyA = copyA;
    }

    public int getTemporizador() {
        return temporizador;
    }

    public void setTemporizador(int temporizador) {
        this.temporizador = temporizador;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

}

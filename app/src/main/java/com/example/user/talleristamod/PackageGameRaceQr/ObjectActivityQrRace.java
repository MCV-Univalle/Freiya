package com.example.user.talleristamod.PackageGameRaceQr;

import java.util.List;

public class ObjectActivityQrRace {

    /*
       Clase Modelo: ObjectActivityQrRace
       Representa el objeto que se traerá de base de datos firebase.

     */

    public String id ;
    public String nombre ;
    public List<String> idQuestions;
    private String joinCode;
    public String stateA;

    public ObjectActivityQrRace() {
    }

    public ObjectActivityQrRace(String id, String nombre, List<String> idQuestions, String joinCode) {
        this.id = id;
        this.nombre = nombre;
        this.idQuestions = idQuestions;
        this.joinCode = joinCode;
    }

    public ObjectActivityQrRace(String nombre, List<String> idQuestions, String stateA, String joinCode) {
        this.nombre = nombre;
        this.idQuestions = idQuestions;
        this.stateA = stateA;
        this.joinCode = joinCode;
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

    public List<String> getIdQuestions() {
        return idQuestions;
    }

    public void setIdQuestions(List<String> idQuestions) {
        this.idQuestions = idQuestions;
    }

    public String getStateA() {
        return stateA;
    }

    public void setStateA(String stateA) {
        this.stateA = stateA;
    }

    public String getJoinCode() {
        return joinCode;
    }

    public void setJoinCode(String joinCode) {
        this.joinCode = joinCode;
    }
}

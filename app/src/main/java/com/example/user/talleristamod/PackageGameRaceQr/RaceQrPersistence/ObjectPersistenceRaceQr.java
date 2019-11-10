package com.example.user.talleristamod.PackageGameRaceQr.RaceQrPersistence;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class ObjectPersistenceRaceQr {
    String nombre, fecha;
    List<ObjectLeaderBoardRaceQr> objectPersistenceRaceQrs;
    List<String> idQuestions;

    public ObjectPersistenceRaceQr() {
    }

    public ObjectPersistenceRaceQr(String nombre, String fecha, List<ObjectLeaderBoardRaceQr> objectPersistenceRaceQrs, List<String> idQuestions) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.objectPersistenceRaceQrs = objectPersistenceRaceQrs;
        this.idQuestions = idQuestions;
    }

    public List<String> getIdQuestions() {
        return idQuestions;
    }

    public void setIdQuestions(List<String> idQuestions) {
        this.idQuestions = idQuestions;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public List<ObjectLeaderBoardRaceQr> getObjectPersistenceRaceQrs() {
        return objectPersistenceRaceQrs;
    }

    public void setObjectPersistenceRaceQrs(ArrayList<ObjectLeaderBoardRaceQr> objectPersistenceRaceQrs) {
        this.objectPersistenceRaceQrs = objectPersistenceRaceQrs;
    }

}

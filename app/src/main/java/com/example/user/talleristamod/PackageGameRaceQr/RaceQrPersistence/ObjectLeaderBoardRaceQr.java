package com.example.user.talleristamod.PackageGameRaceQr.RaceQrPersistence;

import java.util.ArrayList;
import java.util.List;

public class ObjectLeaderBoardRaceQr {

    public String name ;
    public List<Integer> answIncorrect;

    public ObjectLeaderBoardRaceQr() {
    }

    public ObjectLeaderBoardRaceQr(String name, List<Integer> answIncorrect) {
        this.name = name;
        this.answIncorrect = answIncorrect;
    }

    public List<Integer> getAnswIncorrect() {
        return answIncorrect;
    }

    public void setAnswIncorrect(List<Integer> answIncorrect) {
        this.answIncorrect = answIncorrect;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}

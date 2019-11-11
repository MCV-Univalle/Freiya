package com.example.user.talleristamod.PackageGameCuatro;

import java.util.List;

public class ObjectQuestionInterPlay
{

    public String pregunta;
    public String id;

    public ObjectQuestionInterPlay() {}

    public ObjectQuestionInterPlay(String pregunta) {
        this.pregunta = pregunta;
    }

    public ObjectQuestionInterPlay(String pregunta, String id) {
        this.pregunta = pregunta;
        this.id = id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }


}




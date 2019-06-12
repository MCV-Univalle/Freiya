package com.example.user.talleristamod.PackageGameRaceQr.Questions;

import java.util.List;

public class ObjectQuestion {


    public String id;
    public String pregunta;
    public List<String> respuestas;


    public ObjectQuestion() {}

    public ObjectQuestion(String pregunta, List<String> respuestas) {
        this.pregunta = pregunta;
        this.respuestas = respuestas;
    }

    public ObjectQuestion(String pregunta, List<String> respuestas, String id) {
        this.pregunta = pregunta;
        this.respuestas = respuestas;
        this.id = id;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public List<String> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(List<String> respuestas) {
        this.respuestas = respuestas;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}

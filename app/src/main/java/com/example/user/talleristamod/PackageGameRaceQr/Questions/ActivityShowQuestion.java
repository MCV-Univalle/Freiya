package com.example.user.talleristamod.PackageGameRaceQr.Questions;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.example.user.talleristamod.GlobalVariables.GlobalVariables;
import com.example.user.talleristamod.PackageGameRaceQr.ActivityFollowCodeQr;
import com.example.user.talleristamod.PackageGameRaceQr.DatabaseRaceQr;
import com.example.user.talleristamod.R;
import com.example.user.talleristamod.PackageProfiles.Login.TalleristaPrincipalMenu;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ActivityShowQuestion extends AppCompatActivity implements View.OnClickListener {

    TextView pregunta, puntos;
    Button bSiguiente;
    RadioGroup respuestas;
    RadioButton rbrespuesta1;
    RadioButton rbrespuesta2;
    RadioButton rbrespuesta3;
    RadioButton rbrespuesta4;
    DatabaseRaceQr dataBaseSets;
    ArrayList<String> idQuestion;
    ArrayList<Integer> indicePreguntas;
    ArrayList<ObjectQuestion> questions;
    int codigoQr;
    String respuestaCorrecta;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_question);

        pregunta = findViewById(R.id.textViewPregunta);
        rbrespuesta1 = findViewById(R.id.rbRespuestaUno);
        rbrespuesta2 = findViewById(R.id.rbRespuestaDos);
        rbrespuesta3 = findViewById(R.id.rbRespuestaTres);
        rbrespuesta4 = findViewById(R.id.rbRespuestaCuatro);
        puntos = findViewById(R.id.textViewPuntos);
        bSiguiente = (Button) findViewById(R.id.buttonSiguiente);
        respuestas = (RadioGroup) findViewById(R.id.radioGroupRespuestas);
        dataBaseSets = new DatabaseRaceQr(this);
        questions = new ArrayList<ObjectQuestion>();
        idQuestion = new ArrayList<String>();
        indicePreguntas = new ArrayList<Integer>();
        indicesPreguntasAleatorios();
        bSiguiente.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);
        //agregas un mensaje en el ProgressDialog
        progressDialog.setMessage("cargando");
        //muestras el ProgressDialog
        progressDialog.show();

        Bundle parametros = this.getIntent().getExtras();
        idQuestion = parametros.getStringArrayList("idQuestion");
        codigoQr = Integer.parseInt(parametros.getString("codigoQr"));


        final DatabaseReference databaseQuestion = FirebaseDatabase.getInstance().getReference("Question");
        databaseQuestion.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataBaseSets.cargarPreguntasActividad(dataSnapshot, idQuestion, questions);
                progressDialog.dismiss();
                cargarPregunta(codigoQr, GlobalVariables.POINTS_EARNED, questions);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonSiguiente:
             comprobarRespuesta(respuestas);


        }
    }

    public void comprobarRespuesta(RadioGroup respuestas)
    {
        int radioButtonId = respuestas.getCheckedRadioButtonId();
        RadioButton rb = (RadioButton) findViewById(radioButtonId);
        String texto = rb.getText().toString();
        respuestaCorrecta = questions.get(GlobalVariables.POINTS_EARNED).respuestas.get(0);

        if ((respuestaCorrecta == texto))
        {    GlobalVariables.POINTS_EARNED = GlobalVariables.POINTS_EARNED + 1;
            if ( GlobalVariables.POINTS_EARNED == questions.size())
            {   GlobalVariables.POINTS_EARNED++;
                ventanaEmergenteFinal();
            }
            else {
                ventanaEmergente("Respuesta Correcta!", ("Ve a scannear el Codigo Qr" + GlobalVariables.QRVISIT.get(GlobalVariables.POINTS_EARNED)));
            }
        } else {
            ventanaEmergente("Respuesta Incorrecta", ("Vuelve a scannear el Codigo Qr" + GlobalVariables.QRVISIT.get(GlobalVariables.POINTS_EARNED)));

        }
    }

    public void ventanaEmergente(String title, String message)
    {

        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setIcon(R.drawable.robotcontinuar)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        Intent intent = new Intent(getApplicationContext(), ActivityFollowCodeQr.class);
                        intent.putStringArrayListExtra("idQuestion", idQuestion);
                        intent.putExtra("activityClassId", 1);
                        startActivity(intent);
                        finish();
                    }
                }).show();
    }

    public void ventanaEmergenteFinal()
    {

        FirebaseUser user =  FirebaseAuth.getInstance().getCurrentUser();
        String userUid = user.getUid();
        String userName = user.getDisplayName();
        GlobalVariables.POINTS_EARNED = 0;

        DatabaseReference databaseReferenceRace = FirebaseDatabase.getInstance().getReference("Activity/ActivityQrRace/"+GlobalVariables.ID_ACTIVITY+"/LeaderBoard");
        databaseReferenceRace.push().setValue(userName);


        DatabaseRaceQr dataBaseSets = new DatabaseRaceQr(this);
        dataBaseSets.oila();

        new AlertDialog.Builder(this)
                .setTitle("Respuesta Correcta!")
                .setMessage("Felicitaciones, Llegaste al final!")
                .setIcon(R.drawable.robotcontinuar)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        Intent intent = new Intent(getApplicationContext(), TalleristaPrincipalMenu.class);
                        startActivity(intent);
                        finish();
                    }
                }).show();
    }


    public void indicesPreguntasAleatorios() {
        // Genera 5 numeros entre 1 y 4
        for (int i = 1; i <= 4; i++)
        {
            int numero = (int) (Math.random() * 4 + 1);
            if (this.indicePreguntas.contains(numero)) {
                i--;
            } else {
                this.indicePreguntas.add(numero);
            }
        }
    }

    public void cargarPregunta(int codigoQr, int puntosObtenidos, ArrayList<ObjectQuestion> questions)
    {
        if((codigoQr) == GlobalVariables.QRVISIT.get(GlobalVariables.POINTS_EARNED))
        {
            pregunta.setText(questions.get(GlobalVariables.POINTS_EARNED).getPregunta());
            rbrespuesta1.setText(questions.get(GlobalVariables.POINTS_EARNED).respuestas.get(indicePreguntas.get(0)-1));
            rbrespuesta2.setText(questions.get(GlobalVariables.POINTS_EARNED).respuestas.get(indicePreguntas.get(1)-1));
            rbrespuesta3.setText(questions.get(GlobalVariables.POINTS_EARNED).respuestas.get(indicePreguntas.get(2)-1));
            rbrespuesta4.setText(questions.get(GlobalVariables.POINTS_EARNED).respuestas.get(indicePreguntas.get(3)-1));
            puntos.setText(puntosObtenidos + "");
        }
        else
        {
            ventanaEmergente("Error!", ("Qr incorrecto, ve al codigo Qr:" + GlobalVariables.QRVISIT.get(GlobalVariables.POINTS_EARNED)));

        }

    }



}

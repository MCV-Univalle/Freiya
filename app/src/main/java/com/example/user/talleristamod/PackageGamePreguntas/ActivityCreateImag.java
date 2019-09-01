package com.example.user.talleristamod.PackageGamePreguntas;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.user.talleristamod.PackageProfiles.ActivityActivitiesFreiya;
import com.example.user.talleristamod.R;
import com.google.firebase.auth.FirebaseAuth;

public class ActivityCreateImag extends AppCompatActivity implements View.OnClickListener{

    private Button buttonCreateImg;
    private EditText editTextImgQuestion, editTextNameImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_imag);

        InitialConfig();
    }


    private void InitialConfig() {
        editTextNameImg = (EditText) findViewById(R.id.editTextNameImg);
        editTextImgQuestion = (EditText) findViewById(R.id.editTextQuestionImg);
        buttonCreateImg = (Button) findViewById(R.id.buttonCreateImg);
        buttonCreateImg.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonCreateImg:
                final ObjectActivityImaginaries objectActivityImaginaries = new ObjectActivityImaginaries();
                objectActivityImaginaries.setNombre(editTextNameImg.getText().toString());
                objectActivityImaginaries.setPregunta(editTextImgQuestion.getText().toString());
                objectActivityImaginaries.setJoinCode("none");
                objectActivityImaginaries.setStateA("Disable");
                objectActivityImaginaries.setCopyA("false");
                objectActivityImaginaries.setCreator(FirebaseAuth.getInstance().getCurrentUser().getUid());
                objectActivityImaginaries.setTemporizador(5);


                new AlertDialog.Builder(this)
                        .setTitle("Crearas la Pregunta " + objectActivityImaginaries.getNombre())
                        .setMessage(objectActivityImaginaries.getPregunta())
                        .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                DatabaseImaginaries dataBaseSets = new DatabaseImaginaries(getApplicationContext());
                                dataBaseSets.createImaginares(objectActivityImaginaries);

                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).show();


                break;

        }
    }


}

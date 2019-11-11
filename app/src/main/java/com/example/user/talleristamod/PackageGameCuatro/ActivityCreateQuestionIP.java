package com.example.user.talleristamod.PackageGameCuatro;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.user.talleristamod.PackageGameRaceQr.DatabaseRaceQr;
import com.example.user.talleristamod.R;

import java.util.ArrayList;
import java.util.List;

public class ActivityCreateQuestionIP extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextQuestion;
    private Button buttonAddQuestionIp;
    private String question;
    private DatabaseGameCuatro databaseGameCuatro; // private TextView editTextTitle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_question_ip);

        editTextQuestion = (EditText) findViewById(R.id.editTextQuestionIP);
        buttonAddQuestionIp = (Button) findViewById(R.id.buttonaddQuestionInterPlay);

        buttonAddQuestionIp.setOnClickListener(this);
        databaseGameCuatro = new DatabaseGameCuatro(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            //Botones TalleristaActivityLab -----------------------------------------
            case R.id.buttonaddQuestionInterPlay:

                question = editTextQuestion.getText().toString();

                new AlertDialog.Builder(this)
                        .setTitle("Crear Pregunta")
                        .setMessage("Crearas la pregunta "+ question)
                        .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                databaseGameCuatro.AddQuestionGameFour(question);
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

package com.example.user.talleristamod.PackageGameRaceQr.Questions;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.talleristamod.PackageGameRaceQr.ActivityCreateRace;
import com.example.user.talleristamod.PackageGameRaceQr.DatabaseRaceQr;
import com.example.user.talleristamod.PackageProfiles.ProfileTallerista.ActivityProfileTallerista;
import com.example.user.talleristamod.R;

import java.util.ArrayList;
import java.util.List;

public class ActivityCreateQuestion extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextQuestion, editTextCorrectAnsw, editTextOption1, editTextOption2, editTextOption3;
    private Button buttonAddQuestion;
    private List<String> respuestas;
    private String question;
    private DatabaseRaceQr dataBaseSets;
    private ConstraintLayout layoutHomeT, layoutAtrasT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_question);

        InitialConfig();
    }

    private void InitialConfig() {
        layoutHomeT = findViewById(R.id.layoutHomeT);
        layoutAtrasT = findViewById(R.id.layoutAtrasT);

        editTextQuestion = (EditText) findViewById(R.id.editTextQuestionName);
        editTextCorrectAnsw = (EditText) findViewById(R.id.editTextCorrectAnsw);
        editTextOption1 = (EditText) findViewById(R.id.editTextWrongAnsw1);
        editTextOption2 = (EditText) findViewById(R.id.editTextWrongAnsw2);
        editTextOption3 = (EditText) findViewById(R.id.editTextWrongAnsw3);
        buttonAddQuestion = (Button) findViewById(R.id.buttonAddQuestion);

        buttonAddQuestion.setOnClickListener(this);
        dataBaseSets = new DatabaseRaceQr(this);
        layoutHomeT.setOnClickListener(this);
        layoutAtrasT.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.layoutHomeT:
                Intent intentC = new Intent(this, ActivityCreateRace.class);
                startActivity(intentC);
                finish();
                break;
            case R.id.layoutAtrasT:
                Intent intent = new Intent(this, ActivityCreateRace.class);
                startActivity(intent);
                finish();
                break;
            //Botones TalleristaActivityLab -----------------------------------------
            case R.id.buttonAddQuestion:

                question = editTextQuestion.getText().toString();
                respuestas = new ArrayList<String>();
                respuestas.add(editTextCorrectAnsw.getText().toString());
                respuestas.add(editTextOption1.getText().toString());
                respuestas.add(editTextOption2.getText().toString());
                respuestas.add(editTextOption3.getText().toString());

                new AlertDialog.Builder(this)
                        .setTitle("Crear Pregunta")
                        .setMessage("Crearas la pregunta "+ question)
                        .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dataBaseSets = new DatabaseRaceQr(getApplicationContext());
                                dataBaseSets.AddQuestionLab(question, respuestas);
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

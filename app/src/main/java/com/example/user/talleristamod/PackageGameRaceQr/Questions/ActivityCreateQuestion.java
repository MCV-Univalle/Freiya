package com.example.user.talleristamod.PackageGameRaceQr.Questions;

import android.content.DialogInterface;
import android.graphics.Typeface;
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

public class ActivityCreateQuestion extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextQuestion, editTextCorrectAnsw, editTextOption1, editTextOption2, editTextOption3;
    private Button buttonAddQuestion;
    private List<String> respuestas;
    private String question;
    private DatabaseRaceQr dataBaseSets;
    private TextView editTextTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_question);

        InitialConfig();
    }

    private void InitialConfig() {
        editTextTitle = (TextView) findViewById(R.id.textViewTitleQuestion);
        editTextQuestion = (EditText) findViewById(R.id.editTextQuestionName);
        editTextCorrectAnsw = (EditText) findViewById(R.id.editTextCorrectAnsw);
        editTextOption1 = (EditText) findViewById(R.id.editTextWrongAnsw1);
        editTextOption2 = (EditText) findViewById(R.id.editTextWrongAnsw2);
        editTextOption3 = (EditText) findViewById(R.id.editTextWrongAnsw3);
        buttonAddQuestion = (Button) findViewById(R.id.buttonAddQuestion);


        //Cambiar fuente
        Typeface face=Typeface.createFromAsset(getAssets(),"fonts/adventpro-light.ttf");
        editTextTitle.setTypeface(face);

        buttonAddQuestion.setOnClickListener(this);
        dataBaseSets = new DatabaseRaceQr(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
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

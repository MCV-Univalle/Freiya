package com.example.user.talleristamod.PackageProfiles;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.user.talleristamod.GlobalVariables.GlobalVariables;
import com.example.user.talleristamod.PackageGameChallenge.DatabaseChallenge;
import com.example.user.talleristamod.PackageGameChallenge.TalleristaProfile.ActivityCreateChallenge;
import com.example.user.talleristamod.PackageGameChallenge.ActivitySelectChallenge;
import com.example.user.talleristamod.PackageGameCuatro.ActivityCreateInterPlay;
import com.example.user.talleristamod.PackageGamePreguntas.ActivityCreateImag;
import com.example.user.talleristamod.PackageGamePreguntas.ActivitySelectImg;
import com.example.user.talleristamod.PackageGamePreguntas.DatabaseImaginaries;
import com.example.user.talleristamod.PackageGameRaceQr.ActivitySelectRaceQr;
import com.example.user.talleristamod.PackageGameRaceQr.DatabaseRaceQr;
import com.example.user.talleristamod.R;

import static com.example.user.talleristamod.GlobalVariables.GlobalVariables.USER_TYPE;

public class ActivityActivitiesFreiya extends AppCompatActivity implements View.OnClickListener {
    Button bCreateRace, bCreateReto, bCreateImg;
    LinearLayout linearLayoutBack, linearLayoutProfile;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities_freiya);

        bCreateImg = findViewById(R.id.buttonCreateImg);
        bCreateRace = findViewById(R.id.buttonCreateRace);
        bCreateReto = findViewById(R.id.buttonCreateRetos);
        linearLayoutBack = findViewById(R.id.layoutAtrasT);
        linearLayoutProfile = findViewById(R.id.layoutProfileT);
        textButtons ();

        bCreateImg.setOnClickListener(this);
        linearLayoutProfile.setOnClickListener(this);
        linearLayoutBack.setOnClickListener(this);
        bCreateRace.setOnClickListener(this);
        bCreateReto.setOnClickListener(this);

    }

    public void textButtons (){

        if(USER_TYPE.equals("Estudiante"))
        {
            bCreateImg.setText("Unirse");
            bCreateReto.setText("Unirse");
            bCreateRace.setText("Unirse");
        }

        else if(USER_TYPE.equals("Tallerista"))
        {

            if(GlobalVariables.ACTIVITY_FUNCTION.equals("Create")){
                bCreateImg.setText("Crear");
                bCreateReto.setText("Crear");
                bCreateRace.setText("Crear");
            }
            if(GlobalVariables.ACTIVITY_FUNCTION.equals("Activate")){
                bCreateImg.setText("Activar");
                bCreateReto.setText("Activar");
                bCreateRace.setText("Activar");
            }

            if(GlobalVariables.ACTIVITY_FUNCTION.equals("Persistence")){
                bCreateImg.setText("Ver");
                bCreateReto.setText("Ver");
                bCreateRace.setText("Ver");
            }
        }
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId()) {
            case R.id.buttonCreateRace:

                if (USER_TYPE.equals("Tallerista")) {

                    if (GlobalVariables.ACTIVITY_FUNCTION.equals("Create")) {
                        Intent intent = new Intent(this, ActivityDescriptionActivities.class);
                        startActivity(intent);
                    }
                    if (GlobalVariables.ACTIVITY_FUNCTION.equals("Activate")) {
                        Intent intentR = new Intent(this, ActivitySelectRaceQr.class);
                        startActivity(intentR);
                    }

                    if (GlobalVariables.ACTIVITY_FUNCTION.equals("Persistence")) {
                        Intent intentR = new Intent(this, ActivitySelectRaceQr.class);
                        startActivity(intentR);
                    }
                }

                if (USER_TYPE.equals("Estudiante")) {
                    goRaceQr();
                }

                break;

            case R.id.buttonCreateRetos:

                if (USER_TYPE.equals("Tallerista")) {
                    if (GlobalVariables.ACTIVITY_FUNCTION == "Create") {
                        Intent intentR = new Intent(this, ActivityCreateChallenge.class);
                        startActivity(intentR);
                    }
                    if (GlobalVariables.ACTIVITY_FUNCTION == "Activate") {
                        Intent intentR = new Intent(this, ActivitySelectChallenge.class);
                        startActivity(intentR);
                    }

                }

                if (USER_TYPE.equals("Estudiante")) {
                    goChallenge();

                }
                break;

            case R.id.buttonCreateImg:

                if (USER_TYPE.equals("Tallerista")) {
                    if (GlobalVariables.ACTIVITY_FUNCTION == "Create") {
                        Intent intentR = new Intent(this, ActivityCreateImag.class);
                        startActivity(intentR);
                    }
                    if (GlobalVariables.ACTIVITY_FUNCTION == "Activate") {
                        Intent intentR = new Intent(this, ActivitySelectImg.class);
                        startActivity(intentR);
                    }
                }

                if (USER_TYPE.equals("Estudiante")) {
                    goQuestion();
                }
                break;
            case R.id.layoutAtrasT:
                finish();
                break;
            case R.id.layoutProfileT:
                finish();
                break;

            /*case R.id.buttonCreateCuatro:

                        Intent intent = new Intent(this, ActivityCreateInterPlay.class);
                        startActivity(intent);*/


                }
        }


    public void goQuestion() {
        final DatabaseImaginaries dataBaseSetsImg = new DatabaseImaginaries(this);

        final EditText input = new EditText(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        new AlertDialog.Builder(this)
                .setTitle("Unete a la Actividad ")
                .setMessage("Digita el codigo de la Actividad")
                .setView(input)
                .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dataBaseSetsImg.validateCode(input.getText().toString().toUpperCase());
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).show();
    }

    public void goRaceQr(){
        final DatabaseRaceQr databaseRaceQr = new DatabaseRaceQr(this);

        final EditText input = new EditText(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        new AlertDialog.Builder(this)
                .setTitle("Unete a la Actividad ")
                .setMessage("Digita el codigo de la Actividad")
                .setView(input)
                .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        databaseRaceQr.validateCode(input.getText().toString().toUpperCase());
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).show();
    }

    public void goChallenge(){
        final DatabaseChallenge databaseChallenge = new DatabaseChallenge(this);

        final EditText input = new EditText(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        new AlertDialog.Builder(this)
                .setTitle("Unete a la Actividad ")
                .setMessage("Digita el codigo de la Actividad")
                .setView(input)
                .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        databaseChallenge.validateCode(input.getText().toString().toUpperCase());
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).show();
    }


}

package com.example.user.talleristamod.PackageProfiles.ProfileTallerista;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageButton;

import com.example.user.talleristamod.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ActivitySelectBadge extends AppCompatActivity implements View.OnClickListener {

    public String idStudent, nameStudent;
    public ImageButton GiveMedalButton1, GiveMedalButton2,GiveMedalButton3, GiveMedalButton4, GiveMedalButton5,GiveMedalButton6,GiveMedalButton7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_badge);

        Bundle parametros = this.getIntent().getExtras();
        if (parametros != null) {
            idStudent = parametros.getString("idStudent");
            nameStudent = parametros.getString("nameStudent");

        }

        InitialConfig();

    }

    private void InitialConfig() {
        GiveMedalButton1 = findViewById(R.id.buttonBadge1);
        GiveMedalButton2 = findViewById(R.id.buttonBadge2);
        GiveMedalButton3 = findViewById(R.id.buttonBadge3);
        GiveMedalButton4 = findViewById(R.id.buttonBadge4);
        GiveMedalButton5 = findViewById(R.id.buttonBadge5);
        GiveMedalButton6 = findViewById(R.id.buttonBadge6);
        GiveMedalButton7 = findViewById(R.id.buttonBadge7);

        GiveMedalButton1.setOnClickListener(this);
        GiveMedalButton2.setOnClickListener(this);
        GiveMedalButton3.setOnClickListener(this);
        GiveMedalButton4.setOnClickListener(this);
        GiveMedalButton5.setOnClickListener(this);
        GiveMedalButton6.setOnClickListener(this);
        GiveMedalButton7.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.buttonBadge1:
                confirmBadge(nameStudent, "001");
                break;
            case R.id.buttonBadge2:
                confirmBadge(nameStudent, "002");
                break;
            case R.id.buttonBadge3:
                confirmBadge(nameStudent, "003");
                break;
            case R.id.buttonBadge4:
                confirmBadge(nameStudent, "004");
                break;
            case R.id.buttonBadge5:
                confirmBadge(nameStudent, "005");
                break;
            case R.id.buttonBadge6:
                confirmBadge(nameStudent, "006");
                break;
            case R.id.buttonBadge7:
                confirmBadge(nameStudent, "007");
                break;



        }
    }

    public void confirmBadge(String studentName , final String badgeName){
        new AlertDialog.Builder(this)
                .setTitle("Entregar Medalla ")
                .setMessage("¿Estas seguro de entregar la medalla "+badgeName+"al estudiante" +studentName+"?")
                .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseReference databaseEstudianteRegister = FirebaseDatabase.getInstance().getReference("Estudiante/"+idStudent+"/Badges");
                        databaseEstudianteRegister.push().setValue(badgeName);
                    }


                }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).show();

    }
}

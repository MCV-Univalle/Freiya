package com.example.user.talleristamod.PackageProfiles.ProfileTallerista;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.user.talleristamod.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ActivitySelectBadge extends AppCompatActivity {

    public String idStudent;
    public Button GiveMedalButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_badge);

        Bundle parametros = this.getIntent().getExtras();
        if (parametros != null) {
            idStudent = parametros.getString("idStudent");

        }


        GiveMedalButton = findViewById(R.id.ButtonBadge);
        GiveMedalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseEstudianteRegister = FirebaseDatabase.getInstance().getReference("Estudiante/"+idStudent+"/Badges");
                databaseEstudianteRegister.push().setValue("001");

            }
        });

    }
}

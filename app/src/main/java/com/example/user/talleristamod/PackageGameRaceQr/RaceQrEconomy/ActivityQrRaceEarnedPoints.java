package com.example.user.talleristamod.PackageGameRaceQr.RaceQrEconomy;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.user.talleristamod.PackageGameRaceQr.DatabaseRaceQr;
import com.example.user.talleristamod.PackageProfiles.Login.TalleristaPrincipalMenu;
import com.example.user.talleristamod.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ActivityQrRaceEarnedPoints extends AppCompatActivity {

    int recivedPoints = 20;
    DatabaseRaceQr databaseRaceQr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_race_earned_points);

        TextView textViewPoints = findViewById(R.id.TextViewQrPointsEarned);
        Button buttonBack = findViewById(R.id.buttonQrPointsBack);
        textViewPoints.setText(""+recivedPoints);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TalleristaPrincipalMenu.class);
                startActivity(intent);
                finish();
            }
        });

        final String user =  FirebaseAuth.getInstance().getCurrentUser().getUid();
        final DatabaseReference databaseEstudiantePoints = FirebaseDatabase.getInstance().getReference("Estudiante/"+user+"/Puntaje");


        databaseEstudiantePoints.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = (String) dataSnapshot.getValue();
                Integer totalPoints = Integer.parseInt(value);
                totalPoints += recivedPoints;
                databaseEstudiantePoints.setValue(totalPoints.toString());
                databaseEstudiantePoints.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}

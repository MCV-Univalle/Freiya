package com.example.user.talleristamod.PackageGameRaceQr;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;


import com.example.user.talleristamod.PackageProfiles.ActivityActivitiesFreiya;
import com.example.user.talleristamod.QrRaceHolder;
import com.example.user.talleristamod.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class ActivitySelectRaceQr extends AppCompatActivity {
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_game);

        recyclerView = findViewById(R.id.recycler);

        //Cambiar fuente
        Typeface face=Typeface.createFromAsset(getAssets(),"fonts/adventpro-light.ttf");


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference referencia = database.getReference("Activity/ActivityQrRace");


        AdaptadorFirebaseQrRace adaptadorFirebaseQrRace = new AdaptadorFirebaseQrRace(ObjectActivityQrRace.class,R.layout.adapter_recycler_view
                , QrRaceHolder.class,referencia,this);

        recyclerView.setAdapter(adaptadorFirebaseQrRace);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1, LinearLayoutManager.VERTICAL,false));

    }



    }


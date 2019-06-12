package com.example.user.talleristamod.PackageGameChallenge;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.user.talleristamod.QrRaceHolder;
import com.example.user.talleristamod.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ActivitySelectChallenge extends AppCompatActivity {

    RecyclerView recyclerViewChallenges;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_challenge);

        recyclerViewChallenges = findViewById(R.id.recyclerChallenges);
        title = (TextView) findViewById(R.id.textViewTitleChallenges);
        //Cambiar fuente
        Typeface face=Typeface.createFromAsset(getAssets(),"fonts/adventpro-light.ttf");
        title.setTypeface(face);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference referencia = database.getReference("Activity/ActivityChallenge");


        AdaptadorFirebaseChallenges adaptadorFirebase = new AdaptadorFirebaseChallenges(ObjectActivityChallenge.class,R.layout.adapter_recycler_view
                , QrRaceHolder.class,referencia,this);

        recyclerViewChallenges.setAdapter(adaptadorFirebase);
        recyclerViewChallenges.setLayoutManager(new GridLayoutManager(this,1, LinearLayoutManager.VERTICAL,false));
    }
}

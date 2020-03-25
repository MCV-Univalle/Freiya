package com.example.user.talleristamod.PackageGameRaceQr;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


import com.example.user.talleristamod.GlobalVariables.GlobalVariables;
import com.example.user.talleristamod.PackageGameRaceQr.RaceQrPersistence.AdaptadorFirebasePersistenceQrRace;
import com.example.user.talleristamod.PackageGameRaceQr.RaceQrPersistence.ObjectPersistenceRaceQr;
import com.example.user.talleristamod.PackageProfiles.ProfileTallerista.ActivityProfileTallerista;
import com.example.user.talleristamod.QrRaceHolder;
import com.example.user.talleristamod.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class ActivitySelectRaceQr extends AppCompatActivity implements View.OnClickListener{
    RecyclerView recyclerView;
    TextView textViewSelectRace;
    TabLayout tabLayoutQr;
    String filter1, filter2;
    private ConstraintLayout linearLayoutBack, linearLayoutHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_game);

        linearLayoutBack = findViewById(R.id.layoutAtrasT);
        linearLayoutHome = findViewById(R.id.layoutHomeT);

        linearLayoutBack.setOnClickListener(this);
        linearLayoutHome.setOnClickListener(this);

        textViewSelectRace = findViewById(R.id.textViewSelecRace);
        recyclerView = findViewById(R.id.recyclerQrSelect);
        tabLayoutQr = findViewById(R.id.tabLayoutQrActivities);
        filter1 =  "creator";
        filter2 =  FirebaseAuth.getInstance().getCurrentUser().getUid();
        SetAdapterFire(filter1, filter2);


        tabLayoutQr.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        filter1 =  "creator";
                        filter2 =  FirebaseAuth.getInstance().getCurrentUser().getUid();
                        SetAdapterFire(filter1, filter2);
                        break;
                    case 1:
                        filter1 =  "copy";
                        filter2 =  "false";
                        SetAdapterFire(filter1, filter2);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });




    }

    public void SetAdapterFire (String filter1, String filter2) {


        if (GlobalVariables.ACTIVITY_FUNCTION.equals("Activate")) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference referencia = database.getReference("Activity/ActivityQrRace");

        AdaptadorFirebaseQrRace adaptadorFirebaseQrRace = new AdaptadorFirebaseQrRace(ObjectActivityQrRace.class, R.layout.adapter_recycler_view
                , QrRaceHolder.class, referencia, this, filter1, filter2);

        recyclerView.setAdapter(adaptadorFirebaseQrRace);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1, LinearLayoutManager.VERTICAL, false));
    } else
        if (GlobalVariables.ACTIVITY_FUNCTION.equals("Persistence")){

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("ActivityPersistence/ActivityQrRace/");
            AdaptadorFirebasePersistenceQrRace adaptadorFirebaseQrRace = new AdaptadorFirebasePersistenceQrRace(ObjectPersistenceRaceQr.class, R.layout.adapter_recycler_view
                    , QrRaceHolder.class, reference, this);

            recyclerView.setAdapter(adaptadorFirebaseQrRace);
            recyclerView.setLayoutManager(new GridLayoutManager(this, 1, LinearLayoutManager.VERTICAL, false));

        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layoutAtrasT:
                finishActivity();
                break;

            case R.id.layoutHomeT:
                finishActivity();
                break;
        }
    }

    private void finishActivity(){
        Intent intent = new Intent(getApplicationContext(), ActivityProfileTallerista.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed (){
        finishActivity();
    }
}


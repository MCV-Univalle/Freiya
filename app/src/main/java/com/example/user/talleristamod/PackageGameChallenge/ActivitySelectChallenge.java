package com.example.user.talleristamod.PackageGameChallenge;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.user.talleristamod.PackageProfiles.ActivityActivitiesFreiya;
import com.example.user.talleristamod.PackageProfiles.ProfileEstudiante.ActivityPrincipalProfile;
import com.example.user.talleristamod.PackageProfiles.ProfileEstudiante.ActivityProfileEstudiante;
import com.example.user.talleristamod.PackageProfiles.ProfileTallerista.ActivityProfileTallerista;
import com.example.user.talleristamod.QrRaceHolder;
import com.example.user.talleristamod.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.user.talleristamod.GlobalVariables.GlobalVariables.USER_TYPE;

public class ActivitySelectChallenge extends AppCompatActivity implements View.OnClickListener{

    RecyclerView recyclerViewChallenges;
    TabLayout tabLayoutChll;
    String filter1, filter2;
    private ConstraintLayout linearLayoutBack, linearLayoutHome;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_challenge);

        linearLayoutBack = findViewById(R.id.layoutAtrasT);
        linearLayoutHome = findViewById(R.id.layoutHomeT);

        linearLayoutBack.setOnClickListener(this);
        linearLayoutHome.setOnClickListener(this);

        recyclerViewChallenges = findViewById(R.id.recyclerChallenges);
        tabLayoutChll = findViewById(R.id.tabLayoutChllActivities);
        filter1 =  "creator";
        filter2 =  FirebaseAuth.getInstance().getCurrentUser().getUid();
        SetAdapterFire(filter1, filter2);


        tabLayoutChll.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        filter1 =  "creator";
                        filter2 =  FirebaseAuth.getInstance().getCurrentUser().getUid();
                        SetAdapterFire(filter1, filter2);
                        break;
                    case 1:
                        filter1 =  "copyA";
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

    public void SetAdapterFire (String filter1, String filter2){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference referencia = database.getReference("Activity/ActivityChallenge");


        AdaptadorFirebaseChallenges adaptadorFirebase = new AdaptadorFirebaseChallenges(ObjectActivityChallenge.class,R.layout.adapter_recycler_view
                , QrRaceHolder.class,referencia,this, filter1, filter2);

        recyclerViewChallenges.setAdapter(adaptadorFirebase);
        recyclerViewChallenges.setLayoutManager(new GridLayoutManager(this,1, LinearLayoutManager.VERTICAL,false));
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

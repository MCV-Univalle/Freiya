package com.example.user.talleristamod.PackageProfiles.ProfileTallerista;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.LinearLayout;

import com.example.user.talleristamod.GlobalVariables.GlobalVariables;
import com.example.user.talleristamod.PackageProfiles.ActivityActivitiesFreiya;
import com.example.user.talleristamod.PackageProfiles.Login.TalleristaPrincipalMenu;
import com.example.user.talleristamod.R;

public class ActivityProfileTallerista extends AppCompatActivity implements View.OnClickListener {
    CardView cardViewSettings, cardViewActivities, cardViewActivateActivities, cardViewPersistenceActivities;
    LinearLayout layoutBackTallerista;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_tallerista);

        layoutBackTallerista = findViewById(R.id.layoutBackTallerista);
        cardViewSettings = findViewById(R.id.cdProfile);
        cardViewActivities = findViewById(R.id.cdActivities);
        cardViewActivateActivities = findViewById(R.id.cdAtivateActivities);
        cardViewPersistenceActivities = findViewById(R.id.cdPersistenceActivities);

        layoutBackTallerista.setOnClickListener(this);
        cardViewSettings.setOnClickListener(this);
        cardViewActivities.setOnClickListener(this);
        cardViewActivateActivities.setOnClickListener(this);
        cardViewPersistenceActivities.setOnClickListener(this);

    }

    @Override
    public void onClick(View view)
    {
       switch (view.getId())
       {
           case R.id.cdProfile:
               Intent intentA = new Intent(this, ActivityGiveBadges.class);
               startActivity(intentA);
               GlobalVariables.ACTIVITY_FUNCTION = "Badges";
               finish();
               break;

           case R.id.cdActivities:
               Intent intent = new Intent(this, ActivityActivitiesFreiya.class);
               startActivity(intent);
               GlobalVariables.ACTIVITY_FUNCTION = "Create";
               finish();
               break;

           case R.id.cdAtivateActivities:

               Intent intentC = new Intent(this, ActivityActivitiesFreiya.class);
               startActivity(intentC);
               GlobalVariables.ACTIVITY_FUNCTION = "Activate";
               finish();
               break;

           case R.id.cdPersistenceActivities:

               Intent intentB = new Intent(this, ActivityActivitiesFreiya.class);
               startActivity(intentB);
               GlobalVariables.ACTIVITY_FUNCTION = "Persistence";
               finish();
               break;

           case R.id.layoutBackTallerista:

               Intent intentD = new Intent(this, TalleristaPrincipalMenu.class);
               startActivity(intentD);
               GlobalVariables.ACTIVITY_FUNCTION = "";
               finish();
               break;
       }
    }

    @Override
    public void onBackPressed () {
        Intent intentD = new Intent(this, TalleristaPrincipalMenu.class);
        startActivity(intentD);
        finish();
    }


}

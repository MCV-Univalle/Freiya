package com.example.user.talleristamod.PackageProfiles.ProfileTallerista;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;

import com.example.user.talleristamod.GlobalVariables.GlobalVariables;
import com.example.user.talleristamod.PackageProfiles.ActivityActivitiesFreiya;
import com.example.user.talleristamod.PackageProfiles.Login.TalleristaPrincipalMenu;
import com.example.user.talleristamod.R;

public class ActivityProfileTallerista extends AppCompatActivity implements View.OnClickListener {
    CardView cardViewSettings, cardViewActivities, cardViewActivateActivities;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_tallerista);


        cardViewSettings = findViewById(R.id.cdSettings);
        cardViewActivities = findViewById(R.id.cdActivities);
        cardViewActivateActivities = findViewById(R.id.cdAtivateActivities);

        cardViewSettings.setOnClickListener(this);
        cardViewActivities.setOnClickListener(this);
        cardViewActivateActivities.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
       switch (view.getId())
       {
           case R.id.cdSettings:
               Intent intentA = new Intent(this, ActivitySettingsTallerista.class);
               startActivity(intentA);
               GlobalVariables.ACTIVITY_FUNCTION = "Settings";
               break;

           case R.id.cdActivities:
               Intent intent = new Intent(this, ActivityActivitiesFreiya.class);
               startActivity(intent);
               GlobalVariables.ACTIVITY_FUNCTION = "Create";
               break;

           case R.id.cdAtivateActivities:

               Intent intentC = new Intent(this, ActivityActivitiesFreiya.class);
               startActivity(intentC);
               GlobalVariables.ACTIVITY_FUNCTION = "Activate";
               break;
       }
    }


}

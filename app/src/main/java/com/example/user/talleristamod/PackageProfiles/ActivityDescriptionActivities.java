package com.example.user.talleristamod.PackageProfiles;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.user.talleristamod.PackageGameRaceQr.ActivityCreateRace;
import com.example.user.talleristamod.R;

public class ActivityDescriptionActivities extends AppCompatActivity implements View.OnClickListener{
  TabLayout tablayout;
  TextView tvDescripcioion, tvElementos;
  Button bCrearRace;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_activities);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        tablayout = findViewById(R.id.tabLayout);
        tvDescripcioion = findViewById(R.id.textViewDescripcion);
        tvElementos = findViewById(R.id.textViewElementos);
        bCrearRace = findViewById(R.id.buttonCreateRace);
        bCrearRace.setOnClickListener(this);

        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        tvDescripcioion.setVisibility(View.VISIBLE);
                        tvElementos.setVisibility(View.INVISIBLE);
                        break;
                    case 1:
                        tvDescripcioion.setVisibility(View.INVISIBLE);
                        tvElementos.setVisibility(View.VISIBLE);
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

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.buttonCreateRace:
                Intent intent = new Intent(this, ActivityCreateRace.class);
                startActivity(intent);

        }

    }
}
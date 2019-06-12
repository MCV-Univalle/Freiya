package com.example.user.talleristamod.PackageProfiles.ProfileEstudiante;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.user.talleristamod.PackageProfiles.ProfileEstudiante.ActivityProfileEstudiante;
import com.example.user.talleristamod.R;

public class Activity_Profille extends AppCompatActivity implements View.OnClickListener {
Button buttonContinuar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__profille);
        buttonContinuar = findViewById(R.id.buttonContinuarProfile);
        buttonContinuar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.buttonContinuarProfile:
                Intent intentA = new Intent(this, ActivityProfileEstudiante.class);
                startActivity(intentA);
                break;
        }

    }
}

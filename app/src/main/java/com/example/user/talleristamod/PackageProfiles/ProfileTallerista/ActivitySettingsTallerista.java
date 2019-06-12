package com.example.user.talleristamod.PackageProfiles.ProfileTallerista;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.example.user.talleristamod.PackageGameRaceQr.BarcodeCaptureActivity;
import com.example.user.talleristamod.R;

public class ActivitySettingsTallerista extends AppCompatActivity implements View.OnClickListener {

    CardView cvLeerQr,cvGenerateQr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_tallerista);
        cvLeerQr = (CardView)findViewById(R.id.cdLeerQr);
        cvGenerateQr = (CardView)findViewById(R.id.cvGenerateQr);

        cvLeerQr.setOnClickListener(this);
        cvGenerateQr.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cdLeerQr:
                Intent intent1 = new Intent(view.getContext(), BarcodeCaptureActivity.class);
                view.getContext().startActivity(intent1);
                break;

            case R.id.cvGenerateQr:
                Intent intent2 = new Intent(view.getContext(), ActivityCreateQr.class);
                view.getContext().startActivity(intent2);
                break;
        }
    }
}

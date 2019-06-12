package com.example.user.talleristamod.PackageGameChallenge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.talleristamod.GlobalVariables.GlobalVariables;
import com.example.user.talleristamod.R;

public class ActivityReceiveChallenge extends AppCompatActivity implements View.OnClickListener {

    String name;
    String description;
    TextView areaDescripcion;
    Button buttonAceptarReto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_challenge);

        Bundle parametros = this.getIntent().getExtras();
        if (parametros != null)
        {
            name = parametros.getString("nameChallenge");
            description = parametros.getString("descriptionChallenge");
        }

        areaDescripcion = findViewById(R.id.tvDescriptionChallenge);
        buttonAceptarReto = findViewById(R.id.buttonAceptarReto);
        buttonAceptarReto.setOnClickListener(this);
        areaDescripcion.setText(description);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonAceptarReto:
                Intent intent1 = new Intent(this, ActivityUploadResources.class);
                startActivity(intent1);
                break;
        }
    }

}
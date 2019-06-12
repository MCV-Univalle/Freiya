package com.example.user.talleristamod.PackageGamePreguntas;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.user.talleristamod.R;

public class ActivityReceptorImaginaries extends AppCompatActivity {

    TextView textViewSelectedStudent;
    DatabaseImaginaries dataBaseSets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receptor_imaginaries);


        dataBaseSets = new DatabaseImaginaries(this);
        textViewSelectedStudent = findViewById(R.id.textViewSelectedStudent);
        dataBaseSets.receptorSignal(textViewSelectedStudent);
    }
}

package com.example.user.talleristamod.PackageGamePreguntas;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.user.talleristamod.GlobalVariables.GlobalVariables;
import com.example.user.talleristamod.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ActivityReceptorImaginaries extends AppCompatActivity {

    private TextView textViewSelectedStudent;
    private DatabaseImaginaries dataBaseSets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receptor_imaginaries);


        dataBaseSets = new DatabaseImaginaries(this);
        textViewSelectedStudent = findViewById(R.id.textViewSelectedStudent);
        dataBaseSets.receptorSignal(textViewSelectedStudent);
        dataBaseSets.signalFinishActivity();
    }

}

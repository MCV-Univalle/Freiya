package com.example.user.talleristamod.PackageGamePreguntas;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.user.talleristamod.QrRaceHolder;
import com.example.user.talleristamod.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ActivitySelectImg extends AppCompatActivity {
    RecyclerView recyclerViewImg;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_img);

        recyclerViewImg = findViewById(R.id.recyclerImg);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference referencia = database.getReference("Activity/ActivityImaginaries");

        AdapterFirebaseImaginaries adaptadorFirebase = new AdapterFirebaseImaginaries(ObjectActivityImaginaries.class, R.layout.adapter_recycler_view
                , QrRaceHolder.class,referencia,this);

        recyclerViewImg.setAdapter(adaptadorFirebase);
        recyclerViewImg.setLayoutManager(new GridLayoutManager(this,1, LinearLayoutManager.VERTICAL,false));
    }
}

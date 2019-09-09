package com.example.user.talleristamod.PackageGameRaceQr.RaceQrPersistence;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.user.talleristamod.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ActivityDetailsPersistenceRace extends AppCompatActivity {
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_persistence_race);

        Bundle parametros = this.getIntent().getExtras();
        ArrayList<String> arrayQuestions = parametros.getStringArrayList("questions");
        String date = parametros.getString("date");
        String name = parametros.getString("name");
        String key = parametros.getString("key");

        recyclerView = findViewById(R.id.recyclerViewQuestions);

        Toast.makeText(getApplicationContext(), arrayQuestions.size()+"",Toast.LENGTH_SHORT).show();

        RecyclerView.Adapter adapter = new AdaptadorPersistenceQuestionsQrRace (arrayQuestions);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        }




}

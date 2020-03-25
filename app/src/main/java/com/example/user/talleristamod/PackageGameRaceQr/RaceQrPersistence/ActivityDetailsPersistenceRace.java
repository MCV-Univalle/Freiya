package com.example.user.talleristamod.PackageGameRaceQr.RaceQrPersistence;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.talleristamod.PackageProfiles.ProfileTallerista.ActivityProfileTallerista;
import com.example.user.talleristamod.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ActivityDetailsPersistenceRace extends AppCompatActivity implements View.OnClickListener {
    RecyclerView recyclerView;
    TabLayout tableLayout;
    TextView nameRace, dateCreation;
    private ConstraintLayout linearLayoutBack, linearLayoutHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_persistence_race);

        linearLayoutBack = findViewById(R.id.layoutAtrasT);
        linearLayoutHome = findViewById(R.id.layoutHomeT);

        linearLayoutBack.setOnClickListener(this);
        linearLayoutHome.setOnClickListener(this);

        Bundle parametros = this.getIntent().getExtras();
        final ArrayList<String> arrayQuestions = parametros.getStringArrayList("questions");
        final ArrayList<String> arrayLeaderBoard = parametros.getStringArrayList("leaderboard");
        String date = parametros.getString("date");
        String name = parametros.getString("name");

        recyclerView = findViewById(R.id.recyclerViewQuestions);
        tableLayout = findViewById(R.id.tabPersistence);
        nameRace = findViewById(R.id.textViewNamePersistenceRace);
        dateCreation = findViewById(R.id.textViewDatePersistenceRace);


        nameRace.setText(name);
        dateCreation.setText(date);
        RecyclerView.Adapter adapter = new AdaptadorPersistenceQuestionsQrRace (arrayQuestions);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);

        tableLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        RecyclerView.Adapter adapter = new AdaptadorPersistenceQuestionsQrRace (arrayQuestions);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        recyclerView.setAdapter(adapter);
                        break;
                    case 1:
                        RecyclerView.Adapter adapter2 = new AdaptadorPersistenceQuestionsQrRace (arrayLeaderBoard);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        recyclerView.setAdapter(adapter2);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layoutAtrasT:
                finishActivity();
                break;

            case R.id.layoutHomeT:
                finishActivity();
                break;

        }
    }

    private void finishActivity(){
        Intent intent = new Intent(getApplicationContext(), ActivityProfileTallerista.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed (){
        finishActivity();
    }
}

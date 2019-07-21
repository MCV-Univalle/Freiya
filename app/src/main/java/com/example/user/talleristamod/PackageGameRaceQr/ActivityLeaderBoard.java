package com.example.user.talleristamod.PackageGameRaceQr;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.talleristamod.GlobalVariables.GlobalVariables;
import com.example.user.talleristamod.PackageProfiles.DatabaseProfiles;
import com.example.user.talleristamod.PackageProfiles.ProfileTallerista.ActivityProfileTallerista;
import com.example.user.talleristamod.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ActivityLeaderBoard extends AppCompatActivity implements View.OnClickListener {

    ListView listViewLeaderBoard, listViewPlayers;
    DatabaseProfiles dataBaseSets;
    TextView textViewJoinCode, tvTitleParticipantes,tvTitleLeader;
    Button buttonFinishQr;
    TabLayout tablayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);

        textViewJoinCode = (TextView) findViewById(R.id.textViewJoinCodeQr);
        tvTitleParticipantes = (TextView) findViewById(R.id.textViewPlayers);
        tvTitleLeader = (TextView) findViewById(R.id.textViewPlayers2);
        textViewJoinCode.setText(GlobalVariables.JOIN_CODE);
        tablayout = findViewById(R.id.tabLayout);
        listViewLeaderBoard = (ListView) findViewById(R.id.listViewBoardRace);
        listViewPlayers = (ListView) findViewById(R.id.listViewPlayersRace);
        buttonFinishQr = (Button) findViewById(R.id.buttonFinishQr);
        buttonFinishQr.setOnClickListener(this);

        dataBaseSets = new DatabaseProfiles(this);
        tablayout = findViewById(R.id.tabLayoutLeader);
        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        tvTitleParticipantes.setVisibility(View.VISIBLE);
                        tvTitleLeader.setVisibility(View.INVISIBLE);
                        listViewPlayers.setVisibility(View.VISIBLE);
                        listViewLeaderBoard.setVisibility(View.INVISIBLE);
                        break;
                    case 1:
                        tvTitleLeader.setVisibility(View.VISIBLE);
                        tvTitleParticipantes.setVisibility(View.INVISIBLE);
                        listViewLeaderBoard.setVisibility(View.VISIBLE);
                        listViewPlayers.setVisibility(View.INVISIBLE);
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


        dataBaseSets.listLeaderBoard(listViewLeaderBoard);
        dataBaseSets.listPlayers(listViewPlayers);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.buttonFinishQr:
                finishChallenge();
                break;

        }
    }

    public void onBackPressed (){
        finishChallenge();
    }

    public void finishChallenge(){
        new AlertDialog.Builder(this)
                .setTitle("Finalizar Actividad ")
                .setMessage("¿Estas seguro que deseas finalizar la actividad?")
                .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Toast.makeText(getApplicationContext(), "Actividad Finalizada ", Toast.LENGTH_LONG).show();

                        DatabaseReference databaseStateAQr = FirebaseDatabase.getInstance().getReference("Activity/ActivityQrRace/"+GlobalVariables.ID_ACTIVITY);

                        if (GlobalVariables.IS_COPY.equals("true")) {
                            databaseStateAQr.removeValue();
                        } else databaseStateAQr.child("stateA").setValue("Disable");

                        Intent intent = new Intent(getApplicationContext(), ActivityProfileTallerista.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).show();
    }
}
package com.example.user.talleristamod.PackageGameRaceQr;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.talleristamod.GlobalVariables.GlobalVariables;
import com.example.user.talleristamod.PackageGameRaceQr.RaceQrPersistence.ObjectLeaderBoardRaceQr;
import com.example.user.talleristamod.PackageGameRaceQr.RaceQrPersistence.ObjectPersistenceRaceQr;
import com.example.user.talleristamod.PackageProfiles.DatabaseProfiles;
import com.example.user.talleristamod.PackageProfiles.ProfileEstudiante.ActivityPrincipalProfile;
import com.example.user.talleristamod.PackageProfiles.ProfileTallerista.ActivityProfileTallerista;
import com.example.user.talleristamod.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ActivityLeaderBoard extends AppCompatActivity implements View.OnClickListener {

    ListView listViewLeaderBoard, listViewPlayers;
    DatabaseProfiles dataBaseSets;
    TextView textViewJoinCode, tvTitleParticipantes,tvTitleLeader;
    Button buttonFinishQr, buttonGivePoints;
    TabLayout tablayout;
    private ConstraintLayout linearLayoutBack, linearLayoutHome;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);

        textViewJoinCode = findViewById(R.id.textViewJoinCodeQr);
        tvTitleParticipantes = findViewById(R.id.textViewPlayers);
        tvTitleLeader = findViewById(R.id.textViewPlayers2);
        textViewJoinCode.setText(GlobalVariables.JOIN_CODE);
        tablayout = findViewById(R.id.tabLayout);
        listViewLeaderBoard = findViewById(R.id.listViewBoardRace);
        listViewPlayers = findViewById(R.id.listViewPlayersRace);
        buttonFinishQr = findViewById(R.id.buttonFinishQr);
        buttonGivePoints = findViewById(R.id.buttonGivePointsQr);
        linearLayoutBack = findViewById(R.id.layoutAtrasT);
        linearLayoutHome = findViewById(R.id.layoutHomeT);

        linearLayoutBack.setOnClickListener(this);
        linearLayoutHome.setOnClickListener(this);

        buttonGivePoints.setOnClickListener(this);
        buttonFinishQr.setOnClickListener(this);

        dataBaseSets = new DatabaseProfiles(this, "ActivityQrRace");
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
            case R.id.buttonGivePointsQr:
                dataBaseSets.givePointsList();
                break;
            case R.id.layoutAtrasT:
                finishChallenge();
                break;

            case R.id.layoutHomeT:
                finishChallenge();
                break;

        }
    }


    @Override
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

                        final DatabaseReference databaseStateAQr = FirebaseDatabase.getInstance().getReference("Activity/ActivityQrRace/"+GlobalVariables.ID_ACTIVITY);
                        databaseStateAQr.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String name = (String) dataSnapshot.child("nombre").getValue();
                                List<String> listquestions = (ArrayList<String>) dataSnapshot.child("idQuestions").getValue();
                                List<ObjectLeaderBoardRaceQr> listObjectLeaderBoard = new ArrayList<>();

                                DataSnapshot dataSnapshot1 = dataSnapshot.child("LeaderBoard");
                                for (DataSnapshot objectQuestionsSnapShot : dataSnapshot1.getChildren())
                                {
                                    ObjectLeaderBoardRaceQr objectLeaderBoardRaceQr = objectQuestionsSnapShot.getValue(ObjectLeaderBoardRaceQr.class);
                                    listObjectLeaderBoard.add(objectLeaderBoardRaceQr);
                                }

                                DatabaseReference database = FirebaseDatabase.getInstance().getReference("ActivityPersistence/ActivityQrRace/");
                                String key = database.push().getKey();
                                ObjectPersistenceRaceQr objectPersistenceRaceQr = new ObjectPersistenceRaceQr(name,"hoy", listObjectLeaderBoard, listquestions);
                                database.child(key).setValue(objectPersistenceRaceQr);

                                if (GlobalVariables.IS_COPY.equals("true")) {
                                    databaseStateAQr.removeValue();
                                } else databaseStateAQr.child("stateA").setValue("Disable");

                                Intent intent = new Intent(getApplicationContext(), ActivityProfileTallerista.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);

                                databaseStateAQr.removeEventListener(this);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                    finish();

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

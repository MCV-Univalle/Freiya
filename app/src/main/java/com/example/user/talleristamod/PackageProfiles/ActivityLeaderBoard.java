package com.example.user.talleristamod.PackageProfiles;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.user.talleristamod.GlobalVariables.GlobalVariables;
import com.example.user.talleristamod.PackageProfiles.ProfileTallerista.ActivityProfileTallerista;
import com.example.user.talleristamod.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ActivityLeaderBoard extends AppCompatActivity implements View.OnClickListener {

    ListView listViewLeaderBoard, listViewPlayers;
    DatabaseProfiles dataBaseSets;
    TextView textViewJoinCode;
    Button buttonFinishQr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);

        textViewJoinCode = (TextView) findViewById(R.id.textViewJoinCodeQr);
        textViewJoinCode.setText(GlobalVariables.JOIN_CODE);

        listViewLeaderBoard = (ListView) findViewById(R.id.listViewBoardRace);
        listViewPlayers = (ListView) findViewById(R.id.listViewPlayersRace);
        buttonFinishQr = (Button) findViewById(R.id.buttonFinishQr);
        buttonFinishQr.setOnClickListener(this);

        dataBaseSets = new DatabaseProfiles(this);


        dataBaseSets.listLeaderBoard(listViewLeaderBoard);
        dataBaseSets.listPlayers(listViewPlayers);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.buttonFinishQr:

                DatabaseReference databaseStateAQr = FirebaseDatabase.getInstance().getReference("Activity/ActivityQrRace/"+GlobalVariables.ID_ACTIVITY+"/stateA");
                databaseStateAQr.setValue("Disable");

                Intent intent = new Intent(this, ActivityProfileTallerista.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;

        }
    }
}

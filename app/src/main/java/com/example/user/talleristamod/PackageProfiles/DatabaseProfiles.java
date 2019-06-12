package com.example.user.talleristamod.PackageProfiles;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.user.talleristamod.GlobalVariables.GlobalVariables;
import com.example.user.talleristamod.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DatabaseProfiles {

    private Context context;


    public DatabaseProfiles(Context context) {
        this.context = context;
    }


    public void listLeaderBoard(final ListView listViewActivity) {
        DatabaseReference databaseQuestion;
        databaseQuestion = FirebaseDatabase.getInstance().getReference("Activity/ActivityQrRace/" + GlobalVariables.ID_ACTIVITY + "/LeaderBoard");

        databaseQuestion.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> objectActivitys = new ArrayList<>();
                objectActivitys.clear();
                for (DataSnapshot objectQuestionsSnapShot : dataSnapshot.getChildren()) {
                    String estudianteId = (String) objectQuestionsSnapShot.getValue();
                    objectActivitys.add(estudianteId);
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, R.layout.adapter_list_view_rows, R.id.checkListViewAdapter, objectActivitys);
                listViewActivity.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }


    public void listPlayers(final ListView listViewActivity) {
        DatabaseReference databaseQuestion;
        databaseQuestion = FirebaseDatabase.getInstance().getReference("Activity/ActivityQrRace/"+GlobalVariables.ID_ACTIVITY+"/Participantes");

        databaseQuestion.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> objectActivitys = new ArrayList<>();
                objectActivitys.clear();
                for (DataSnapshot objectQuestionsSnapShot : dataSnapshot.getChildren()) {
                    //ObjectActivityQrRace activity = objectQuestionsSnapShot.getValue(ObjectActivityQrRace.class);
                    String estudianteId = (String) objectQuestionsSnapShot.getValue();

                    objectActivitys.add(estudianteId);
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, R.layout.adapter_list_view_rows, R.id.checkListViewAdapter, objectActivitys);
                listViewActivity.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

}

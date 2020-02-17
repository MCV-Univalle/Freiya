package com.example.user.talleristamod.PackageProfiles;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.user.talleristamod.GlobalVariables.GlobalVariables;
import com.example.user.talleristamod.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DatabaseProfiles {

    private Context context;
    private Integer intPoints;
    private String activity;

    public DatabaseProfiles(Context context, String activity) {
        this.context = context;
        this.activity = activity;
    }
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
                    String estudianteId = (String) objectQuestionsSnapShot.child("name").getValue();
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

    public void listPlayersGivePoints (final Integer recivedPoints) {
        DatabaseReference databaseQuestion;
        databaseQuestion = FirebaseDatabase.getInstance().getReference("Activity/"+activity+"/"+GlobalVariables.ID_ACTIVITY+"/Participantes");

        databaseQuestion.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> objectActivitys = new ArrayList<>();
                objectActivitys.clear();
                for (DataSnapshot objectQuestionsSnapShot : dataSnapshot.getChildren()) {
                    singlePlayerGivePoints(objectQuestionsSnapShot.getKey(), recivedPoints);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    public void singlePlayerGivePoints (String studentKey, final Integer recivedPoints){
        final DatabaseReference databaseEstudiantePoints = FirebaseDatabase.getInstance().getReference("Estudiante/"+studentKey+"/Puntaje");

        databaseEstudiantePoints.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = (String) dataSnapshot.getValue();
                if (value != null){

                } else value = "0";

                Integer totalPoints = Integer.parseInt(value);
                totalPoints += recivedPoints;
                databaseEstudiantePoints.setValue(totalPoints.toString());
                databaseEstudiantePoints.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void givePointsList(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Elige los puntos a asignar");
        String[] points = {"100" , "200", "300"};
        builder.setSingleChoiceItems(points, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:
                        intPoints = 100;
                        break;
                    case 1:
                        intPoints = 200;
                        break;
                    case 2:
                        intPoints = 300;
                        break;
                }}});
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (intPoints!= null){
                    listPlayersGivePoints(intPoints);
                    Toast.makeText(context, "Se entregaron "+ intPoints +" a todos los participantes", Toast.LENGTH_SHORT).show();
                } else Toast.makeText(context, "Selecciona un puntaje", Toast.LENGTH_SHORT).show();
            }});
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }});
        builder.show();
    }

}

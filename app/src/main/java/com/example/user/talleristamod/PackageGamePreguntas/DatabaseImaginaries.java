package com.example.user.talleristamod.PackageGamePreguntas;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.os.Handler;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.talleristamod.GlobalVariables.GlobalVariables;
import com.example.user.talleristamod.PackageProfiles.ActivityActivitiesFreiya;
import com.example.user.talleristamod.PackageProfiles.ProfileTallerista.ActivityProfileTallerista;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;

public class DatabaseImaginaries {
    private Context context;


    public DatabaseImaginaries(Context context) {
        this.context = context;
    }


    public void signalFinishActivity (){

        final DatabaseReference databaseChosen = FirebaseDatabase.getInstance().getReference("Activity/ActivityImaginaries/");

        databaseChosen.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList <String> listQuestion = new ArrayList<>();
                String stateActivity = "";

                for (DataSnapshot objectQuestionsSnapShot : dataSnapshot.getChildren()) {
                    String question = objectQuestionsSnapShot.getKey();
                    listQuestion.add(question);

                    if (question.equals(GlobalVariables.ID_ACTIVITY)) {
                        stateActivity = (String) dataSnapshot.child(GlobalVariables.ID_ACTIVITY).child("stateA").getValue();
                        break;
                    }
                }

                if (!listQuestion.contains(GlobalVariables.ID_ACTIVITY)) {
                    Toast.makeText(context, "Actividad Deshabilitada", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, ActivityActivitiesFreiya.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
                    context.startActivity(intent);
                    databaseChosen.removeEventListener(this);

                } else if (stateActivity.equals("Disable")){
                        Intent intent = new Intent(context, ActivityActivitiesFreiya.class);
                        Toast.makeText(context, "Actividad Deshabilitada", Toast.LENGTH_SHORT).show();
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
                        context.startActivity(intent);
                        databaseChosen.removeEventListener(this);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    public void createImaginaresCopy(ObjectActivityImaginaries objectActivityImaginaries) {
        objectActivityImaginaries.setCopyA("true");
        objectActivityImaginaries.setCreator("Copy");

        SecureRandom random = new SecureRandom();
        String randomCode = new BigInteger(20, random).toString(32).toUpperCase();
        GlobalVariables.JOIN_CODE = randomCode;

        DatabaseReference databaseCreateImg = FirebaseDatabase.getInstance().getReference("Activity/ActivityImaginaries");
        String id = databaseCreateImg.push().getKey();
        databaseCreateImg.child(id).setValue(objectActivityImaginaries);
        databaseCreateImg.child(id).child("joinCode").setValue(randomCode);

        GlobalVariables.ID_ACTIVITY = id;
        Intent intent = new Intent(context, ActivityShowImaginarie.class);
        context.startActivity(intent);
    }



    public void createImaginares(ObjectActivityImaginaries objectActivityImaginaries) {

        DatabaseReference databaseCreateImg = FirebaseDatabase.getInstance().getReference("Activity/ActivityImaginaries");
        String id = databaseCreateImg.push().getKey();
        databaseCreateImg.child(id).setValue(objectActivityImaginaries);

        GlobalVariables.ID_ACTIVITY = id;
        Intent intent = new Intent(context, ActivityProfileTallerista.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public void showActivityImaginaries(final String selectedActivity){

        final DatabaseReference databaseReferenceRace = FirebaseDatabase.getInstance().getReference("Activity/ActivityImaginaries");

        databaseReferenceRace.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot objectQuestionsSnapShot : dataSnapshot.getChildren())
                {
                    ObjectActivityImaginaries img = objectQuestionsSnapShot.getValue(ObjectActivityImaginaries.class);
                    img.setId(objectQuestionsSnapShot.getKey());
                    String idImg = img.getId();


                    if (img.getNombre().equals(selectedActivity))
                    {
                        SecureRandom random = new SecureRandom();
                        String randomCode = new BigInteger(20, random).toString(32).toUpperCase();
                        GlobalVariables.ID_ACTIVITY = idImg;
                        GlobalVariables.JOIN_CODE = randomCode;

                        DatabaseReference databaseQuestion = FirebaseDatabase.getInstance().getReference("Activity/ActivityImaginaries/"+GlobalVariables.ID_ACTIVITY);
                        databaseQuestion.child("joinCode").setValue(randomCode);
                        databaseQuestion.child("stateA").setValue("Enabled");


                        Intent intent = new Intent(context, ActivityShowImaginarie.class);
                        context.startActivity(intent);
                        databaseReferenceRace.removeEventListener(this);
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }



    public void joinActivityImaginaries(){
        FirebaseUser user =  FirebaseAuth.getInstance().getCurrentUser();
        final String userUid = user.getUid();
        final String userName = user.getDisplayName();


        DatabaseReference databaseReferenceRace = FirebaseDatabase.getInstance().getReference("Activity/ActivityImaginaries/"+GlobalVariables.ID_ACTIVITY+"/Participantes");
        databaseReferenceRace.child(userUid).setValue(userName);

        Intent intent = new Intent(context, ActivityReceptorImaginaries.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public void sendSignal(){

        final DatabaseReference databaseQuestion;
        databaseQuestion = FirebaseDatabase.getInstance().getReference("Activity/ActivityImaginaries/"+GlobalVariables.ID_ACTIVITY+"/Participantes");

        databaseQuestion.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> listIdParticipants = new ArrayList<>();

                listIdParticipants.clear();

                for (DataSnapshot objectQuestionsSnapShot : dataSnapshot.getChildren()) {
                    String participantId = objectQuestionsSnapShot.getKey();
                    listIdParticipants.add(participantId);
                }

                if(listIdParticipants.isEmpty()){
                    Toast.makeText(context, "No se han unido estudiantes", Toast.LENGTH_SHORT).show();

                } else{

                    int numero = (int) (Math.random() * listIdParticipants.size());

                    DatabaseReference databaseCreateImg = FirebaseDatabase.getInstance().getReference("Activity/ActivityImaginaries/"+GlobalVariables.ID_ACTIVITY);
                    databaseCreateImg.child("temporizador").setValue(4);
                    databaseCreateImg.child("elegido").setValue("");
                    databaseCreateImg.child("elegido").setValue(listIdParticipants.get(numero));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void sendSignalPlay(){

        final DatabaseReference databaseQuestion;
        databaseQuestion = FirebaseDatabase.getInstance().getReference("Activity/ActivityImaginaries/"+GlobalVariables.ID_ACTIVITY+"/Participantes");

        databaseQuestion.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> listIdParticipants = new ArrayList<>();
                listIdParticipants.clear();

                for (DataSnapshot objectQuestionsSnapShot : dataSnapshot.getChildren()) {
                    String participantId = (String) objectQuestionsSnapShot.getKey();
                    listIdParticipants.add(participantId);
                }

                if(listIdParticipants.isEmpty()){
                    Toast.makeText(context, "No se han unido estudiantes", Toast.LENGTH_SHORT).show();
                } else{
                    int roundsNumber = (int)(Math.random() * 4)+1;
                    int numberParticipants = listIdParticipants.size();
                    DatabaseReference databaseCreateImg = FirebaseDatabase.getInstance().getReference("Activity/ActivityImaginaries/"+GlobalVariables.ID_ACTIVITY);

                    for (int a = roundsNumber; a >= 1; a--){
                        for (int i = 0; i < numberParticipants ; i++ ) {
                            if (a == 1) {
                                int selected = (int) (Math.random() * listIdParticipants.size());
                                databaseCreateImg.child("temporizador").setValue(5);
                                databaseCreateImg.child("elegido").setValue("");
                                databaseCreateImg.child("elegido").setValue(listIdParticipants.get(selected));

                            } else try {
                                Thread.sleep(a*1000);
                                databaseCreateImg.child("temporizador").setValue(a);
                                databaseCreateImg.child("elegido").setValue("");
                                databaseCreateImg.child("elegido").setValue(listIdParticipants.get(i));
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                    }



                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }



    public void selectIndividualStudent(String idStudent){

        DatabaseReference databaseCreateImg = FirebaseDatabase.getInstance().getReference("Activity/ActivityImaginaries/"+ GlobalVariables.ID_ACTIVITY);
        databaseCreateImg.child("elegido").setValue("");
        databaseCreateImg.child("elegido").setValue(idStudent);

    }

    public void validateCode(final String selectedActivityCode ){
        DatabaseReference databaseReferenceRace = FirebaseDatabase.getInstance().getReference("Activity/ActivityImaginaries");
        //
        databaseReferenceRace.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList <String> listQuestions = new ArrayList<>();
                for (DataSnapshot objectQuestionsSnapShot : dataSnapshot.getChildren())
                {
                    ObjectActivityImaginaries img = objectQuestionsSnapShot.getValue(ObjectActivityImaginaries.class);
                    img.setId(objectQuestionsSnapShot.getKey());
                    listQuestions.add(img.getJoinCode());

                    if (img.getJoinCode().equals(selectedActivityCode))
                    {
                        GlobalVariables.ID_ACTIVITY = img.getId();
                        joinActivityImaginaries();
                        break;
                    }
                }
                if (!listQuestions.contains(selectedActivityCode)) Toast.makeText(context, "Error al digitar el codigo de ingreso", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void obtainListStudents(final RecyclerView recyclerViewStudents){
        DatabaseReference databaseQuestion;
        databaseQuestion = FirebaseDatabase.getInstance().getReference("Activity/ActivityImaginaries/"+GlobalVariables.ID_ACTIVITY+"/Participantes");

        databaseQuestion.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ArrayList<AdapterStudentsClass> arrayStudentsClass = new ArrayList<AdapterStudentsClass>();
                arrayStudentsClass.clear();


                for (DataSnapshot objectQuestionsSnapShot : dataSnapshot.getChildren()) {
                    String participantName = (String) objectQuestionsSnapShot.getValue();
                    String participantId = (String) objectQuestionsSnapShot.getKey();

                    AdapterStudentsClass adapterStudentsClass = new AdapterStudentsClass(participantName, participantId);
                    arrayStudentsClass.add(adapterStudentsClass);

                    AdapterStudents adapterStudents = new AdapterStudents(arrayStudentsClass);
                    recyclerViewStudents.setAdapter(adapterStudents);                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }
//-----------------------------------------------------------------------------------------------------------
}

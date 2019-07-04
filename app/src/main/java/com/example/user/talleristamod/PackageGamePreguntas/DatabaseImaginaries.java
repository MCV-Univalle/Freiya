package com.example.user.talleristamod.PackageGamePreguntas;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
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

        DatabaseReference databaseChosen = FirebaseDatabase.getInstance().getReference("Activity/ActivityImaginaries/"+GlobalVariables.ID_ACTIVITY+"/stateA");

        databaseChosen.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String stateActivity = (String) dataSnapshot.getValue();

                if (stateActivity.equals("Disable")){
                    Intent intent = new Intent(context, ActivityActivitiesFreiya.class);
                    Toast.makeText(context, "Actividad Deshabilitada", Toast.LENGTH_SHORT).show();
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void createImaginaresCopy(ObjectActivityImaginaries objectActivityImaginaries) {
        objectActivityImaginaries.setCopyA(true);

        SecureRandom random = new SecureRandom();
        String randomCode = new BigInteger(30, random).toString(32).toUpperCase();
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

        DatabaseReference databaseReferenceRace = FirebaseDatabase.getInstance().getReference("Activity/ActivityImaginaries");

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
                        String randomCode = new BigInteger(30, random).toString(32).toUpperCase();
                        GlobalVariables.ID_ACTIVITY = idImg;
                        GlobalVariables.JOIN_CODE = randomCode;

                        DatabaseReference databaseQuestion = FirebaseDatabase.getInstance().getReference("Activity/ActivityImaginaries/"+GlobalVariables.ID_ACTIVITY);
                        databaseQuestion.child("joinCode").setValue(randomCode);
                        databaseQuestion.child("stateA").setValue("Enabled");


                        Intent intent = new Intent(context, ActivityShowImaginarie.class);
                        context.startActivity(intent);
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

    public void sendSignal(final TextView textSelectedStudent){

        DatabaseReference databaseQuestion;
        databaseQuestion = FirebaseDatabase.getInstance().getReference("Activity/ActivityImaginaries/"+GlobalVariables.ID_ACTIVITY+"/Participantes");

        databaseQuestion.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> listNameParticipants = new ArrayList<>();
                ArrayList<String> listIdParticipants = new ArrayList<>();
                listNameParticipants.clear();
                listIdParticipants.clear();

                for (DataSnapshot objectQuestionsSnapShot : dataSnapshot.getChildren()) {
                    String participantName = (String) objectQuestionsSnapShot.getValue();
                    String participantId = (String) objectQuestionsSnapShot.getKey();
                    listNameParticipants.add(participantName);
                    listIdParticipants.add(participantId);
                }

                if(listIdParticipants.isEmpty()){
                    Toast.makeText(context, "No se han unido estudiantes", Toast.LENGTH_SHORT).show();

                } else{

                int numero = (int) (Math.random() * listNameParticipants.size()) + 1;
                textSelectedStudent.setText(listNameParticipants.get(numero-1));

                DatabaseReference databaseCreateImg = FirebaseDatabase.getInstance().getReference("Activity/ActivityImaginaries/"+GlobalVariables.ID_ACTIVITY);
                databaseCreateImg.child("elegido").setValue("");
                databaseCreateImg.child("elegido").setValue(listIdParticipants.get(numero-1));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void receptorSignal(final TextView textViewSelectedStudent){

        FirebaseUser user =  FirebaseAuth.getInstance().getCurrentUser();
        final String userUid = user.getUid();

        DatabaseReference databaseChosen;
        databaseChosen = FirebaseDatabase.getInstance().getReference("Activity/ActivityImaginaries/"+ GlobalVariables.ID_ACTIVITY+"/elegido");

        databaseChosen.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String idChosen = (String) dataSnapshot.getValue();

                Toast.makeText(context,userUid+" = "+ idChosen, Toast.LENGTH_SHORT).show();

                if (userUid.equals(idChosen)){
                    textViewSelectedStudent.setText("Fuiste Elegido");

                    Vibrator vibrator = (Vibrator) context.getSystemService(context.VIBRATOR_SERVICE);
                    vibrator.vibrate(5000);

                    Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
                    final MediaPlayer mp = MediaPlayer.create(context, notification);


                    mp.start();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mp.stop();
                        }
                    },7000);

                } else textViewSelectedStudent.setText("Aun no");
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

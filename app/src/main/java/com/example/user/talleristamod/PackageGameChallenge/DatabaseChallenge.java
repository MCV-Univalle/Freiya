package com.example.user.talleristamod.PackageGameChallenge;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.user.talleristamod.GlobalVariables.GlobalVariables;
import com.example.user.talleristamod.PackageGameChallenge.TalleristaProfile.ActivityShowActivateChallenge;
import com.example.user.talleristamod.PackageGamePreguntas.ActivityReceptorImaginaries;
import com.example.user.talleristamod.PackageGamePreguntas.ActivityShowImaginarie;
import com.example.user.talleristamod.PackageGamePreguntas.ObjectActivityImaginaries;
import com.example.user.talleristamod.PackageProfiles.ActivityActivitiesFreiya;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;

public class DatabaseChallenge {
    private Context context;

    public DatabaseChallenge(Context context) {
        this.context = context;
    }


    public void signalFinishActivity (){

        final DatabaseReference databaseChosen = FirebaseDatabase.getInstance().getReference("Activity/ActivityChallenge/");

        databaseChosen.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList <String> listChallenge = new ArrayList<>();
                String stateActivity = "";

                for (DataSnapshot objectQuestionsSnapShot : dataSnapshot.getChildren()) {
                    String challenge = objectQuestionsSnapShot.getKey();
                    listChallenge.add(challenge);

                    if (challenge.equals(GlobalVariables.ID_ACTIVITY)) {
                        stateActivity = (String) dataSnapshot.child(GlobalVariables.ID_ACTIVITY).child("stateA").getValue();
                        break;
                    }
                }

                if (!listChallenge.contains(GlobalVariables.ID_ACTIVITY)) {
                    Toast.makeText(context, "Actividad Deshabilitada", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, ActivityActivitiesFreiya.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
                    context.startActivity(intent);
                }
                if (stateActivity.equals("Disable")) {
                    Intent intent = new Intent(context, ActivityActivitiesFreiya.class);
                    Toast.makeText(context, "Actividad Deshabilitada", Toast.LENGTH_SHORT).show();
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
                    context.startActivity(intent);


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void createChallengeCopy(ObjectActivityChallenge objectActivityImaginaries) {

        SecureRandom random = new SecureRandom();
        String randomCode = new BigInteger(20, random).toString(32).toUpperCase();
        GlobalVariables.JOIN_CODE = randomCode;

        DatabaseReference databaseCreateImg = FirebaseDatabase.getInstance().getReference("Activity/ActivityChallenge");
        String id = databaseCreateImg.push().getKey();
        databaseCreateImg.child(id).setValue(objectActivityImaginaries);
        databaseCreateImg.child(id).child("Copy").setValue(true);
        databaseCreateImg.child(id).child("joinCode").setValue(randomCode);

        GlobalVariables.ID_ACTIVITY = id;
        Intent intent = new Intent(context, ActivityShowActivateChallenge.class);
        context.startActivity(intent);
    }


    public void createChallenge(ObjectActivityChallenge objectActivityChallenge)
    {

        DatabaseReference databaseCreateChallenge = FirebaseDatabase.getInstance().getReference("Activity/ActivityChallenge");
        String id = databaseCreateChallenge.push().getKey();
        databaseCreateChallenge.child(id).setValue(objectActivityChallenge);

    }



    public  void obtainActivatedChallenge(final String selectedActivity){
        final DatabaseReference databaseReferenceRace = FirebaseDatabase.getInstance().getReference("Activity/ActivityChallenge");
        databaseReferenceRace.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            for (DataSnapshot objectQuestionsSnapShot : dataSnapshot.getChildren())
            {
                ObjectActivityChallenge challenge = objectQuestionsSnapShot.getValue(ObjectActivityChallenge.class);
                challenge.setChallengeId(objectQuestionsSnapShot.getKey());

                if (challenge.getChallengeName().equals(selectedActivity))
                {
                    SecureRandom random = new SecureRandom();
                    String randomCode = new BigInteger(20, random).toString(32).toUpperCase();

                    GlobalVariables.JOIN_CODE = randomCode;
                    GlobalVariables.ID_ACTIVITY = challenge.getChallengeId();

                    DatabaseReference databaseQuestion = FirebaseDatabase.getInstance().getReference("Activity/ActivityChallenge/"+GlobalVariables.ID_ACTIVITY+"/joinCode");
                    databaseQuestion.setValue(randomCode);

                    DatabaseReference databaseStateAImg = FirebaseDatabase.getInstance().getReference("Activity/ActivityChallenge/"+GlobalVariables.ID_ACTIVITY+"/stateA");
                    databaseStateAImg.setValue("Enabled");

                    //-----------------------------------------------------------------------

                    Intent intent = new Intent(context, ActivityShowActivateChallenge.class);
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

    public void validateCode(final String selectedActivityCode){
        DatabaseReference databaseReferenceRace = FirebaseDatabase.getInstance().getReference("Activity/ActivityChallenge");


        databaseReferenceRace.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList <String> listChallenge = new ArrayList<>();
                for (DataSnapshot objectQuestionsSnapShot : dataSnapshot.getChildren())
                {
                    ObjectActivityChallenge challenge = objectQuestionsSnapShot.getValue(ObjectActivityChallenge.class);
                    challenge.setChallengeId(objectQuestionsSnapShot.getKey());
                    listChallenge.add(challenge.getJoinCode());

                    if (challenge.getJoinCode().equals(selectedActivityCode)){
                        GlobalVariables.ID_ACTIVITY = challenge.getChallengeId();

                        joinActivityChallenge(challenge.getChallengeName(), challenge.getChallengeDescription());
                        break;
                    }
                }
                if (!listChallenge.contains(selectedActivityCode)) Toast.makeText(context, "Error al digitar el codigo de ingreso", Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void joinActivityChallenge(String challengeName, String challengeDescription){
        FirebaseUser user =  FirebaseAuth.getInstance().getCurrentUser();
        final String userUid = user.getUid();
        final String userName = user.getDisplayName();


        DatabaseReference databaseReferenceRace = FirebaseDatabase.getInstance().getReference("Activity/ActivityChallenge/"+GlobalVariables.ID_ACTIVITY+"/Participantes");
        databaseReferenceRace.child(userUid).setValue(userName);

        Intent intent = new Intent(context, ActivityReceiveChallenge.class);
        intent.putExtra("nameChallenge", challengeName);
        intent.putExtra("descriptionChallenge", challengeDescription);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}

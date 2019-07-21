package com.example.user.talleristamod.PackageGameChallenge;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.user.talleristamod.GlobalVariables.GlobalVariables;
import com.example.user.talleristamod.PackageGameChallenge.TalleristaProfile.ActivityShowActivateChallenge;
import com.example.user.talleristamod.PackageGamePreguntas.ActivityShowImaginarie;
import com.example.user.talleristamod.PackageGamePreguntas.ObjectActivityImaginaries;
import com.example.user.talleristamod.PackageProfiles.ActivityActivitiesFreiya;
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


        DatabaseReference databaseChosen = FirebaseDatabase.getInstance().getReference("Activity/ActivityChallenge/");


        databaseChosen.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList <String> listChallenge = new ArrayList<>();
                String stateActivity = "";

                for (DataSnapshot objectQuestionsSnapShot : dataSnapshot.getChildren()) {
                    String challenge = objectQuestionsSnapShot.getKey();
                    listChallenge.add(challenge);
                    //Toast.makeText(context, challenge, Toast.LENGTH_SHORT).show();

                    if (challenge.equals(GlobalVariables.SELECTED_CHALLENGE)) {
                        stateActivity = (String) dataSnapshot.child(GlobalVariables.SELECTED_CHALLENGE).child("stateA").getValue();
                        break;
                    }
                }
                if (!listChallenge.contains(GlobalVariables.SELECTED_CHALLENGE)) {
                    Toast.makeText(context, "Actividad Deshabilitada", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, ActivityActivitiesFreiya.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
                    context.startActivity(intent); }

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
        String randomCode = new BigInteger(30, random).toString(32).toUpperCase();
        GlobalVariables.JOIN_CODE = randomCode;

        DatabaseReference databaseCreateImg = FirebaseDatabase.getInstance().getReference("Activity/ActivityChallenge");
        String id = databaseCreateImg.push().getKey();
        databaseCreateImg.child(id).setValue(objectActivityImaginaries);
        databaseCreateImg.child(id).child("Copy").setValue(true);
        databaseCreateImg.child(id).child("joinCode").setValue(randomCode);

        GlobalVariables.SELECTED_CHALLENGE = id;
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
                    String randomCode = new BigInteger(30, random).toString(32).toUpperCase();

                    GlobalVariables.JOIN_CODE = randomCode;
                    GlobalVariables.SELECTED_CHALLENGE = challenge.getChallengeId();

                    DatabaseReference databaseQuestion = FirebaseDatabase.getInstance().getReference("Activity/ActivityChallenge/"+GlobalVariables.SELECTED_CHALLENGE+"/joinCode");
                    databaseQuestion.setValue(randomCode);

                    DatabaseReference databaseStateAImg = FirebaseDatabase.getInstance().getReference("Activity/ActivityChallenge/"+GlobalVariables.SELECTED_CHALLENGE+"/stateA");
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
                        GlobalVariables.SELECTED_CHALLENGE = challenge.getChallengeId();

                        Intent intent = new Intent(context, ActivityReceiveChallenge.class);
                        intent.putExtra("nameChallenge", challenge.getChallengeName());
                        intent.putExtra("descriptionChallenge", challenge.getChallengeDescription());
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
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

}

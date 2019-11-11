package com.example.user.talleristamod.PackageGameCuatro;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.example.user.talleristamod.GlobalVariables.GlobalVariables;
import com.example.user.talleristamod.PackageGameRaceQr.ActivityLeaderBoard;
import com.example.user.talleristamod.PackageGameRaceQr.ObjectActivityQrRace;
import com.example.user.talleristamod.PackageGameRaceQr.Questions.ObjectQuestion;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class DatabaseGameCuatro {
    private Context context;

    public DatabaseGameCuatro(Context context) {
        this.context = context;
    }



    public void AddQuestionGameFour (String Pregunta) {

        DatabaseReference databaseQuestion = FirebaseDatabase.getInstance().getReference("QuestionGameCuatro");
        String id = databaseQuestion.push().getKey();
        ObjectQuestionInterPlay objectQuestion = new ObjectQuestionInterPlay(Pregunta);
        databaseQuestion.child(id).setValue(objectQuestion);
    }



    public void createGameFour(ObjectQuestionInterPlay objectActivityFour) {
        SecureRandom random = new SecureRandom();
        String randomCode = new BigInteger(20, random).toString(32).toUpperCase();
        GlobalVariables.JOIN_CODE = randomCode;

        DatabaseReference databaseCreateImg = FirebaseDatabase.getInstance().getReference("Activity/ActivityFour");
        String id = databaseCreateImg.push().getKey();
        databaseCreateImg.child(id).setValue(objectActivityFour);
        databaseCreateImg.child(id).child("joinCode").setValue(randomCode);
        //databaseCreateImg.child(id).child("copy").setValue("false");

        GlobalVariables.ID_ACTIVITY = id;
        Intent intent = new Intent(context, ActivityLeaderBoard.class);
        context.startActivity(intent);
    }

    public void CreateGameInterPlay(final ArrayList<String> selected, final String raceName)
    {

        DatabaseReference databaseQuestion = FirebaseDatabase.getInstance().getReference("QuestionGameCuatro");

        final List<ObjectQuestionInterPlay> objectQuestions = new ArrayList<>();
        final List<String> idQuestions = new ArrayList<>();
        idQuestions.clear();
        objectQuestions.clear();

        databaseQuestion.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
                        ObjectQuestionInterPlay question = issue.getValue(ObjectQuestionInterPlay.class);
                        question.setId(issue.getKey());

                        if (selected.contains(question.pregunta)) {
                            objectQuestions.add(question);
                            idQuestions.add(question.getId());
                        }
                    }
                }

                SecureRandom random = new SecureRandom();
                String randomCode = new BigInteger(20, random).toString(32).toUpperCase();
                GlobalVariables.JOIN_CODE = randomCode;

                DatabaseReference databaseCreateImg = FirebaseDatabase.getInstance().getReference("Activity/ActivityFour");
                String id = databaseCreateImg.push().getKey();
                ObjectActivityInterPlay objectActivityQrRace = new ObjectActivityInterPlay(id, raceName, idQuestions, randomCode, "Disable" , FirebaseAuth.getInstance().getCurrentUser().getUid(), "false");
                databaseCreateImg.child(id).setValue(objectActivityQrRace);
                databaseCreateImg.child(id).child("joinCode").setValue(randomCode);
                //databaseCreateImg.child(id).child("copy").setValue("false");

                GlobalVariables.ID_ACTIVITY = id;



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }












}

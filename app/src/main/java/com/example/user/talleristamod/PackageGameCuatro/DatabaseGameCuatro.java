package com.example.user.talleristamod.PackageGameCuatro;

import android.content.Context;
import android.content.Intent;

import com.example.user.talleristamod.GlobalVariables.GlobalVariables;
import com.example.user.talleristamod.PackageGameRaceQr.ActivityLeaderBoard;
import com.example.user.talleristamod.PackageGameRaceQr.ObjectActivityQrRace;
import com.example.user.talleristamod.PackageGameRaceQr.Questions.ObjectQuestion;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;

public class DatabaseGameCuatro {
    private Context context;

    public DatabaseGameCuatro(Context context) {
        this.context = context;
    }



    public void AddQuestionGameFour (String Pregunta) {

        DatabaseReference databaseQuestion = FirebaseDatabase.getInstance().getReference("QuestionGameCuatro");
        String id = databaseQuestion.push().getKey();
        databaseQuestion.child(id).setValue(Pregunta);
    }
// crea un objeto de actividad cuatro con lo que necesites para la actividad y simplemente cambias @Object por el objeto cre creaste y listo se crea la actividad
    public void createGameFour(Object objectActivityFour) {
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
}

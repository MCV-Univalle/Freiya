package com.example.user.talleristamod.PackageProfiles.Login;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DatabaseLogin {

    private Context context;

    public DatabaseLogin(Context context) {
        this.context = context;
    }

    public void combrobarUserBd (){

        final FirebaseUser user =  FirebaseAuth.getInstance().getCurrentUser();
        final String userUid = user.getUid();
        DatabaseReference databaseEstudiante = FirebaseDatabase.getInstance().getReference("Estudiante");

        databaseEstudiante.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean exist = false;
                for (DataSnapshot objectQuestionsSnapShot : dataSnapshot.getChildren()) {
                    String userTaken = objectQuestionsSnapShot.getKey();
                    if(userTaken.equals(userUid))exist = true;
                }
                if (!exist){
                    DatabaseReference databaseEstudianteRegister = FirebaseDatabase.getInstance().getReference("Estudiante/"+userUid);
                    databaseEstudianteRegister.child("Nombre").setValue(user.getDisplayName());
                    databaseEstudianteRegister.child("Puntaje").setValue("0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}

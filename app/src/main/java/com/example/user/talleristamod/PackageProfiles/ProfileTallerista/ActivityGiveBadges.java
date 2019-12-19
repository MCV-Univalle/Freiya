package com.example.user.talleristamod.PackageProfiles.ProfileTallerista;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.example.user.talleristamod.GlobalVariables.GlobalVariables;
import com.example.user.talleristamod.PackageGamePreguntas.AdapterStudents;
import com.example.user.talleristamod.PackageGamePreguntas.AdapterStudentsClass;
import com.example.user.talleristamod.PackageGamePreguntas.DatabaseImaginaries;
import com.example.user.talleristamod.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ActivityGiveBadges extends AppCompatActivity {

    DatabaseImaginaries dataBaseSets;
    RecyclerView recyclerViewStudents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_badges);
        showStudents();

    }

    public void showStudents(){
        recyclerViewStudents = (RecyclerView) findViewById(R.id.reciverViewBadges);
        recyclerViewStudents.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        obtainTotalStudentsList(recyclerViewStudents);
    }



    public void obtainTotalStudentsList(final RecyclerView recyclerViewStudents){
        DatabaseReference databaseQuestion;
        databaseQuestion = FirebaseDatabase.getInstance().getReference("Estudiante");

        databaseQuestion.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ArrayList<AdapterStudentsClass> arrayStudentsClass = new ArrayList<>();


                for (DataSnapshot objectQuestionsSnapShot : dataSnapshot.getChildren()) {
                    String participantName = (String) objectQuestionsSnapShot.child("Nombre").getValue();
                    String participantId = (String) objectQuestionsSnapShot.getKey();

                    AdapterStudentsClass adapterStudentsClass = new AdapterStudentsClass(participantName, participantId);
                    arrayStudentsClass.add(adapterStudentsClass);

                    AdapterStudents adapterStudents = new AdapterStudents(arrayStudentsClass);
                    recyclerViewStudents.setAdapter(adapterStudents);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

}

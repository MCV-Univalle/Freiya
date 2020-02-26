package com.example.user.talleristamod.PackageProfiles.ProfileTallerista;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import com.example.user.talleristamod.PackageGamePreguntas.AdapterStudents;
import com.example.user.talleristamod.PackageGamePreguntas.AdapterStudentsClass;
import com.example.user.talleristamod.PackageProfiles.ProfileEstudiante.ActivityPrincipalProfile;
import com.example.user.talleristamod.PackageProfiles.ProfileEstudiante.ActivityProfileEstudiante;
import com.example.user.talleristamod.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.user.talleristamod.GlobalVariables.GlobalVariables.USER_TYPE;

public class ActivityGiveBadges extends AppCompatActivity implements View.OnClickListener {

    public RecyclerView recyclerViewStudents;
    private ConstraintLayout linearLayoutBack, linearLayoutHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_badges);
        showStudents();

    }

    public void showStudents(){
        linearLayoutBack = findViewById(R.id.layoutAtrasT);
        linearLayoutHome = findViewById(R.id.layoutHomeT);

        linearLayoutBack.setOnClickListener(this);
        linearLayoutHome.setOnClickListener(this);


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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layoutAtrasT:
                finishActivity();
                break;

            case R.id.layoutHomeT:
                finishActivity();
                break;
        }


        }

    private void finishActivity(){

        Intent intent = new Intent(getApplicationContext(), ActivityProfileTallerista.class);
        startActivity(intent);
        finish();

    }

    @Override
    public void onBackPressed (){
        finishActivity();
    }

    }


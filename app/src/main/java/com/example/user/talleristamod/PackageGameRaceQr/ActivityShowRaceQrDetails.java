package com.example.user.talleristamod.PackageGameRaceQr;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.user.talleristamod.PackageGameRaceQr.Questions.ObjectQuestion;
import com.example.user.talleristamod.PackageProfiles.ProfileEstudiante.ActivityPrincipalProfile;
import com.example.user.talleristamod.PackageProfiles.ProfileEstudiante.ActivityProfileEstudiante;
import com.example.user.talleristamod.PackageProfiles.ProfileTallerista.ActivityProfileTallerista;
import com.example.user.talleristamod.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.user.talleristamod.GlobalVariables.GlobalVariables.USER_TYPE;

public class ActivityShowRaceQrDetails extends AppCompatActivity implements View.OnClickListener{

    DatabaseRaceQr dataBaseSets;
    ArrayList<ObjectQuestion> questions = new ArrayList<>();
    ArrayList<String> idQuestion = new ArrayList<>();
    ListView listQuestions;
    ArrayList<String> questionsQ = new ArrayList<>();
    String nameQrRace;
    TextView textViewNameRace, textViewTitle;
    ConstraintLayout linearLayoutBack, linearLayoutHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_race_qr_details);

        textViewNameRace = findViewById(R.id.textViewDetailsNameRace);
        textViewTitle = findViewById(R.id.textView15);
        listQuestions = findViewById(R.id.listViewRaceQrDetails);
        listQuestions.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        linearLayoutBack = findViewById(R.id.layoutAtrasT);
        linearLayoutHome = findViewById(R.id.layoutHomeT);


        linearLayoutBack.setOnClickListener(this);
        linearLayoutHome.setOnClickListener(this);

        dataBaseSets = new DatabaseRaceQr(this);
        Bundle parametros = this.getIntent().getExtras();
        idQuestion = parametros.getStringArrayList("idQuestion");
        nameQrRace = parametros.getString("nameRace");

        textViewNameRace.setText(nameQrRace);
        Typeface face = Typeface.createFromAsset(getAssets(), "font/adventpro_light.ttf");
        textViewTitle.setTypeface(face);

        final DatabaseReference databaseQuestion = FirebaseDatabase.getInstance().getReference("Question");
        databaseQuestion.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (int i = 0; i < idQuestion.size(); i++)
                {
                    for (DataSnapshot objectQuestionsSnapShot : dataSnapshot.getChildren())
                    {
                        ObjectQuestion question = objectQuestionsSnapShot.getValue(ObjectQuestion.class);
                        if (objectQuestionsSnapShot.getKey().equalsIgnoreCase(idQuestion.get(i)))
                        {
                            questionsQ.add(question.getPregunta());
                            break;
                        }
                    }
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter <>(ActivityShowRaceQrDetails.this , R.layout.text_view_show_questions, R.id.adapterShowQrQuestions, questionsQ);
                listQuestions.setAdapter(arrayAdapter);
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
                Intent intent = new Intent(getApplicationContext(), ActivityProfileTallerista.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    private void finishActivity(){
        Intent intent = new Intent(getApplicationContext(), ActivitySelectRaceQr.class);
        startActivity(intent);
        finish();

    }

    @Override
    public void onBackPressed (){
        finishActivity();
    }
}

package com.example.user.talleristamod.PackageGameRaceQr;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.talleristamod.GlobalVariables.GlobalVariables;
import com.example.user.talleristamod.PackageGameRaceQr.Questions.ObjectQuestion;
import com.example.user.talleristamod.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ActivityShowRaceQrDetails extends AppCompatActivity {

    DatabaseRaceQr dataBaseSets;
    ArrayList<ObjectQuestion> questions = new ArrayList<>();
    ArrayList<String> idQuestion = new ArrayList<>();
    ListView listQuestions;
    ArrayList<String> questionsQ = new ArrayList<>();
    String nameQrRace;
    TextView textViewNameRace, textViewTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_race_qr_details);

        textViewNameRace = findViewById(R.id.textViewDetailsNameRace);
        textViewTitle = findViewById(R.id.textView15);
        listQuestions = findViewById(R.id.listViewRaceQrDetails);
        listQuestions.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);

        dataBaseSets = new DatabaseRaceQr(this);
        Bundle parametros = this.getIntent().getExtras();
        idQuestion = parametros.getStringArrayList("idQuestion");
        nameQrRace = parametros.getString("nameRace");

        textViewNameRace.setText(nameQrRace);
        Typeface face = Typeface.createFromAsset(getAssets(),"fonts/adventpro-light.ttf");
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

}

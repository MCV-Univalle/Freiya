package com.example.user.talleristamod.PackageGameRaceQr.RaceQrPersistence;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.user.talleristamod.PackageGameRaceQr.ActivityShowRaceQrDetails;
import com.example.user.talleristamod.PackageGameRaceQr.Questions.ObjectQuestion;
import com.example.user.talleristamod.QrRaceHolder;
import com.example.user.talleristamod.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

public class AdaptadorFirebasePersistenceQrRace extends FirebaseRecyclerAdapter<ObjectPersistenceRaceQr, QrRaceHolder> {

    Context context;
    ArrayList<String> questionsQ = new ArrayList<>();

    public AdaptadorFirebasePersistenceQrRace(Class<ObjectPersistenceRaceQr> modelClass, int modelLayout, Class<QrRaceHolder> viewHolderClass, Query ref, Context c) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        context = c;
    }

    @Override
    protected void populateViewHolder(QrRaceHolder viewHolder, final ObjectPersistenceRaceQr model, final int position) {

        viewHolder.nombre.setText(model.getNombre());


        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ArrayList<String> leaderBoard = new ArrayList<>();
                if (model.getObjectPersistenceRaceQrs().isEmpty()){

                } else {

                for (int i = 0; i < model.getObjectPersistenceRaceQrs().size(); i++)
                    {
                        leaderBoard.add(model.getObjectPersistenceRaceQrs().get(i).getName());
                    }
                }

                final DatabaseReference databaseQuestion = FirebaseDatabase.getInstance().getReference("Question");
                databaseQuestion.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    List<String> questionsList = model.getIdQuestions();

                        for (int i = 0; i < questionsList.size(); i++)
                        {
                            for (DataSnapshot objectQuestionsSnapShot : dataSnapshot.getChildren())
                            {
                                ObjectQuestion question = objectQuestionsSnapShot.getValue(ObjectQuestion.class);
                                if (objectQuestionsSnapShot.getKey().equalsIgnoreCase(questionsList.get(i)))
                                {
                                    questionsQ.add(question.getPregunta());
                                    break;
                                }
                            }
                        }

                        Intent intent = new Intent(context, ActivityDetailsPersistenceRace.class);
                        intent.putStringArrayListExtra("questions", questionsQ);
                        intent.putStringArrayListExtra("leaderboard", leaderBoard);
                        intent.putExtra("name",model.getNombre());
                        intent.putExtra("date",model.getFecha());
                        intent.putExtra("key",getRef(position).getKey());
                        context.startActivity(intent);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        });


    }

}

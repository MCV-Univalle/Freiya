package com.example.user.talleristamod.PackageGameChallenge.TalleristaProfile;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.user.talleristamod.GlobalVariables.GlobalVariables;
import com.example.user.talleristamod.PackageGameChallenge.ObjectResourceChallenge;
import com.example.user.talleristamod.PackageGameChallenge.TalleristaProfile.AdaptadorFirebaseChallengesResources;
import com.example.user.talleristamod.PackageProfiles.ProfileTallerista.ActivityProfileTallerista;
import com.example.user.talleristamod.QrRaceHolder;
import com.example.user.talleristamod.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ActivityShowActivateChallenge extends AppCompatActivity implements View.OnClickListener{

    RecyclerView recyclerViewChallenges;
    TextView title, textViewJoinCodeChallenge;
    Button buttonFinishChallenge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_activate_challenge);

        recyclerViewChallenges = findViewById(R.id.recyclerActivateChallenge);
        textViewJoinCodeChallenge = (TextView) findViewById(R.id.textViewJoinCodeChallenge);
        textViewJoinCodeChallenge.setText(GlobalVariables.JOIN_CODE);

        buttonFinishChallenge = (Button) findViewById(R.id.buttonFinishChallenge);
        buttonFinishChallenge.setOnClickListener(this);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference referencia = database.getReference("Activity/ActivityChallenge/"+GlobalVariables.SELECTED_CHALLENGE+"/Resources");

        AdaptadorFirebaseChallengesResources adaptadorFirebase = new AdaptadorFirebaseChallengesResources(ObjectResourceChallenge.class,R.layout.adapter_recycler_view
                , QrRaceHolder.class,referencia,this);

        recyclerViewChallenges.setAdapter(adaptadorFirebase);
        recyclerViewChallenges.setLayoutManager(new GridLayoutManager(this,1, LinearLayoutManager.VERTICAL,false));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {

            case R.id.buttonFinishChallenge:
                DatabaseReference databaseStateAImg = FirebaseDatabase.getInstance().getReference("Activity/ActivityChallenge/"+GlobalVariables.SELECTED_CHALLENGE);
               // databaseStateAImg.setValue("Disable");

                if (GlobalVariables.IS_COPY.equals("true")) {
                    databaseStateAImg.removeValue();
                } else databaseStateAImg.child("stateA").setValue("Disable");

                Intent intent = new Intent(this, ActivityProfileTallerista.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;

        }
    }
}

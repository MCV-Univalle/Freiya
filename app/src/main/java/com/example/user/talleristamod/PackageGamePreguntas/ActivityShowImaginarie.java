package com.example.user.talleristamod.PackageGamePreguntas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.talleristamod.GlobalVariables.GlobalVariables;
import com.example.user.talleristamod.PackageProfiles.ProfileTallerista.ActivityProfileTallerista;
import com.example.user.talleristamod.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ActivityShowImaginarie extends AppCompatActivity implements View.OnClickListener{

    Button buttonSendSignal, buttonFinishImg;
    TextView textSelectedStudent, textCodeA;
    DatabaseImaginaries dataBaseSets;
    RecyclerView recyclerViewStudents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_imaginarie);

        textSelectedStudent = (TextView) findViewById(R.id.textViewStudentName);
        textCodeA = (TextView) findViewById(R.id.textViewACodeImg);
        textCodeA.setText(GlobalVariables.JOIN_CODE);

        buttonSendSignal = (Button) findViewById(R.id.buttonSendSignal);
        buttonFinishImg = (Button) findViewById(R.id.buttonFinishImg);

        buttonSendSignal.setOnClickListener(this);
        buttonFinishImg.setOnClickListener(this);
        dataBaseSets = new DatabaseImaginaries(this);
        showStudents();

        Toast.makeText(this, "id = "+GlobalVariables.ID_ACTIVITY, Toast.LENGTH_LONG).show();
    }

    public void showStudents(){
        recyclerViewStudents = (RecyclerView) findViewById(R.id.recyclerViewImaginarieStudents);
        recyclerViewStudents.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        dataBaseSets.obtainListStudents(recyclerViewStudents);
    }





    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.buttonSendSignal:
                dataBaseSets.sendSignal(textSelectedStudent);
                break;
            case R.id.buttonFinishImg:

                DatabaseReference databaseStateAImg = FirebaseDatabase.getInstance().getReference("Activity/ActivityImaginaries/"+GlobalVariables.ID_ACTIVITY);

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

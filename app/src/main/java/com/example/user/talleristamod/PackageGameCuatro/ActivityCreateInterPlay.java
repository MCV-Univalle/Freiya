package com.example.user.talleristamod.PackageGameCuatro;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.talleristamod.PackageGameRaceQr.ActivityCreateRace;
import com.example.user.talleristamod.PackageGameRaceQr.DatabaseRaceQr;
import com.example.user.talleristamod.PackageGameRaceQr.Questions.ActivityCreateQuestion;
import com.example.user.talleristamod.PackageGameRaceQr.Questions.ObjectQuestion;
import com.example.user.talleristamod.PackageProfiles.ProfileTallerista.ActivityProfileTallerista;
import com.example.user.talleristamod.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ActivityCreateInterPlay extends AppCompatActivity implements View.OnClickListener
{

    private Button buttonAddQuestionIP, buttonCreateActivityIP;
    private DatabaseReference databaseQuestion;
    private ListView listViewQuestionsIP;
    private EditText editTextIPName;
    private TextView textViewTitle, textViewSteps;
    private ArrayList<String> selected;
    TabLayout tabLayoutStepIP;
    DatabaseGameCuatro databaseGameCuatro;
    DatabaseRaceQr dataBaseSets;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_inter_play);
        InitialConfig();
        databaseQuestion = FirebaseDatabase.getInstance().getReference("QuestionGameCuatro");
    }

    private void InitialConfig()
    {
        buttonAddQuestionIP = (Button) findViewById(R.id.buttonAddQuestionIP);
        buttonCreateActivityIP = (Button) findViewById(R.id.buttonCreateActivityIP);
        listViewQuestionsIP = (ListView) findViewById(R.id.listViewPreguntasInterPlay);
        editTextIPName = (EditText) findViewById(R.id.editTextActivityIpName);
        textViewTitle = (TextView) findViewById(R.id.textViewIP);
        tabLayoutStepIP = (TabLayout) findViewById(R.id.tabCreateInterPlay);


        textViewSteps = (TextView) findViewById(R.id.textViewIP);
        //Cambiar fuente
        Typeface face=Typeface.createFromAsset(getAssets(),"fonts/adventpro-light.ttf");
        textViewTitle.setTypeface(face);

        listViewQuestionsIP.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);

        buttonAddQuestionIP.setOnClickListener(this);
        buttonCreateActivityIP.setOnClickListener(this);


        tabLayoutStepIP.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        textViewSteps.setText("Selecciona las preguntas o ");
                        buttonAddQuestionIP.setVisibility(View.VISIBLE);
                        buttonAddQuestionIP.setEnabled(true);
                        listViewQuestionsIP.setVisibility(View.VISIBLE);
                        listViewQuestionsIP.setEnabled(true);
                        editTextIPName.setVisibility(View.INVISIBLE);
                        editTextIPName.setEnabled(false);
                        buttonCreateActivityIP.setText("Siguiente");
                        break;
                    case 1:
                        textViewSteps.setText("Ingresa el nombre de la Actividad");
                        buttonAddQuestionIP.setVisibility(View.INVISIBLE);
                        buttonAddQuestionIP.setEnabled(false);
                        listViewQuestionsIP.setVisibility(View.INVISIBLE);
                        listViewQuestionsIP.setEnabled(false);
                        editTextIPName.setVisibility(View.VISIBLE);
                        editTextIPName.setEnabled(true);
                        buttonCreateActivityIP.setText("Siguiente");
                        break;
                    case 2:
                        textViewSteps.setText("Los codigos Qr estaran en tu galeria");
                        buttonAddQuestionIP.setVisibility(View.INVISIBLE);
                        buttonAddQuestionIP.setEnabled(false);
                        listViewQuestionsIP.setVisibility(View.INVISIBLE);
                        listViewQuestionsIP.setEnabled(false);
                        editTextIPName.setVisibility(View.INVISIBLE);
                        editTextIPName.setEnabled(false);
                        buttonCreateActivityIP.setText("Crear Carrera");
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseQuestion.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> objectQuestions = new ArrayList<>();
                objectQuestions.clear();
                for (DataSnapshot objectQuestionsSnapShot : dataSnapshot.getChildren())
                {
                    ObjectQuestionInterPlay question = objectQuestionsSnapShot.getValue(ObjectQuestionInterPlay.class);

                    objectQuestions.add(question.pregunta);

                }

                ArrayAdapter<String> arrayAdapter;
                arrayAdapter = new ArrayAdapter<>(ActivityCreateInterPlay.this, R.layout.adapter_list_view_rows, R.id.checkListViewAdapter, objectQuestions);

                listViewQuestionsIP.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    public void messageConfirmCreateInterPlay()
    {
        final String name = editTextIPName.getText().toString();
        new AlertDialog.Builder(this)
                .setTitle("Crear Actividad")
                .setMessage("Crearas la actividad " + name)
                .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        databaseGameCuatro.CreateGameInterPlay(selected, name);
                        Intent intent = new Intent(getApplicationContext(), ActivityProfileTallerista.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).show();
    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.buttonAddQuestionIP:
                Intent intent = new Intent(this, ActivityCreateQuestionIP.class);
                startActivity(intent);
                break;

            case R.id.buttonCreateActivityIP:

                if (tabLayoutStepIP.getSelectedTabPosition() != 1)
                {
                    tabLayoutStepIP.getTabAt(tabLayoutStepIP.getSelectedTabPosition() + 1).select();
                } else
                    {
                    createActivityInterplay();
                }

                break;
        }
    }

    public void createActivityInterplay()
    {
        databaseGameCuatro = new DatabaseGameCuatro(getApplicationContext());
        dataBaseSets = new DatabaseRaceQr(getApplicationContext());
        selected = dataBaseSets.SelectedItems(listViewQuestionsIP);

        if(!selected.isEmpty()) {
            if(!editTextIPName.getText().toString().equals("")){
                if(editTextIPName.getText().toString().length()<=18){
                    messageConfirmCreateInterPlay();
                } else{
                    tabLayoutStepIP.getTabAt(1).select();
                    Toast.makeText(this,"El nombre debe tener menos de 18 caracteres",Toast.LENGTH_SHORT).show();

                }
            } else{
                tabLayoutStepIP.getTabAt(1).select();
                Toast.makeText(this,"Debes nombrar la actividad",Toast.LENGTH_SHORT).show();
            }
        }
        else  {

            tabLayoutStepIP.getTabAt(0).select();
            Toast.makeText(this,"No hay preguntas seleccionadas",Toast.LENGTH_SHORT).show();
        }

    }





}

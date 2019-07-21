package com.example.user.talleristamod.PackageGameRaceQr;

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

public class ActivityCreateRace extends AppCompatActivity implements View.OnClickListener{

    private Button buttonAddQuestion, buttonCreateActivity;
    private DatabaseReference databaseQuestion;
    private ListView listViewQuestionsLab;
    private EditText editTextRaceName;
    private TextView textViewTitle, textViewSteps;
    private ArrayList<String> selected;
    TabLayout tabLayoutStepsRace;
    DatabaseRaceQr dataBaseSets;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_race);
        InitialConfig();
        databaseQuestion = FirebaseDatabase.getInstance().getReference("Question");

    }

    private void InitialConfig() {
        buttonAddQuestion = (Button) findViewById(R.id.buttonAddQuestionLab);
        buttonCreateActivity = (Button) findViewById(R.id.buttonCreateActivityLab);
        listViewQuestionsLab = (ListView) findViewById(R.id.listViewPreguntasRace);
        editTextRaceName = (EditText) findViewById(R.id.editTextActivityRaceName);
        textViewTitle = (TextView) findViewById(R.id.textViewTitle);
        tabLayoutStepsRace = (TabLayout) findViewById(R.id.tabCreateRace);
        

        textViewSteps = (TextView) findViewById(R.id.textViewRaceQ);
        //Cambiar fuente
        Typeface face=Typeface.createFromAsset(getAssets(),"fonts/adventpro-light.ttf");
        textViewTitle.setTypeface(face);

        listViewQuestionsLab.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);

        buttonAddQuestion.setOnClickListener(this);
        buttonCreateActivity.setOnClickListener(this);


        tabLayoutStepsRace.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        textViewSteps.setText("Selecciona las preguntas o ");
                        buttonAddQuestion.setVisibility(View.VISIBLE);
                        buttonAddQuestion.setEnabled(true);
                        listViewQuestionsLab.setVisibility(View.VISIBLE);
                        listViewQuestionsLab.setEnabled(true);
                        editTextRaceName.setVisibility(View.INVISIBLE);
                        editTextRaceName.setEnabled(false);
                        buttonCreateActivity.setText("Siguiente");
                        break;
                    case 1:
                        textViewSteps.setText("Ingresa el nombre de la carrera");
                        buttonAddQuestion.setVisibility(View.INVISIBLE);
                        buttonAddQuestion.setEnabled(false);
                        listViewQuestionsLab.setVisibility(View.INVISIBLE);
                        listViewQuestionsLab.setEnabled(false);
                        editTextRaceName.setVisibility(View.VISIBLE);
                        editTextRaceName.setEnabled(true);
                        buttonCreateActivity.setText("Siguiente");
                        break;
                    case 2:
                        textViewSteps.setText("Los codigos Qr estaran en tu galeria");
                        buttonAddQuestion.setVisibility(View.INVISIBLE);
                        buttonAddQuestion.setEnabled(false);
                        listViewQuestionsLab.setVisibility(View.INVISIBLE);
                        listViewQuestionsLab.setEnabled(false);
                        editTextRaceName.setVisibility(View.INVISIBLE);
                        editTextRaceName.setEnabled(false);
                        buttonCreateActivity.setText("Crear Carrera");
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
                ArrayList <String> objectQuestions = new ArrayList<>();
                objectQuestions.clear();
                for(DataSnapshot objectQuestionsSnapShot : dataSnapshot.getChildren()){
                    ObjectQuestion question = objectQuestionsSnapShot.getValue(ObjectQuestion.class);

                    objectQuestions.add(question.pregunta);

                }

                ArrayAdapter <String> arrayAdapter = new ArrayAdapter <>(ActivityCreateRace.this , R.layout.adapter_list_view_rows, R.id.checkListViewAdapter, objectQuestions);

                listViewQuestionsLab.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            //Botones TalleristaActivityLab -----------------------------------------
            case R.id.buttonAddQuestionLab:
                Intent intent = new Intent(this, ActivityCreateQuestion.class);
                startActivity(intent);
                break;

            case R.id.buttonCreateActivityLab:


                //tabLayoutStepsRace.getSelectedTabPosition();
                if(tabLayoutStepsRace.getSelectedTabPosition() != 2) {
                    tabLayoutStepsRace.getTabAt(tabLayoutStepsRace.getSelectedTabPosition() + 1).select();
                } else {
                    createRace();
                }

                break;

        }
    }

   public void messageConfirmCreateCreate()
   {
       final String raceName = editTextRaceName.getText().toString();
       new AlertDialog.Builder(this)
               .setTitle("Crear Actividad")
               .setMessage("Crearas la actividad " + raceName)
               .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       dataBaseSets.ObtainRace(selected, raceName);
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


   public void createRace()
   {
       dataBaseSets = new DatabaseRaceQr(getApplicationContext());
       selected = dataBaseSets.SelectedItems(listViewQuestionsLab);

       if(!selected.isEmpty()) {
            if(!editTextRaceName.getText().toString().equals("")){
                if(editTextRaceName.getText().toString().length()<=18){
                messageConfirmCreateCreate();
                } else{
                    tabLayoutStepsRace.getTabAt(1).select();
                    Toast.makeText(this,"El nombre debe tener menos de 18 caracteres",Toast.LENGTH_SHORT).show();

                }
            } else{
                tabLayoutStepsRace.getTabAt(1).select();
                Toast.makeText(this,"Debes nombrar la carrera",Toast.LENGTH_SHORT).show();
            }
       }
       else  {

           tabLayoutStepsRace.getTabAt(0).select();
           Toast.makeText(this,"No hay preguntas seleccionadas",Toast.LENGTH_SHORT).show();
       }



   }
}

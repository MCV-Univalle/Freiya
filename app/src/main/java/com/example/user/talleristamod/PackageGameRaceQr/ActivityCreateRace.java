package com.example.user.talleristamod.PackageGameRaceQr;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

    private Button buttonAddQuestion, buttonNextCreateQr, buttonBackCreateQr;
    private DatabaseReference databaseQuestion;
    private ListView listViewQuestionsLab;
    private EditText editTextRaceName;
    private TextView textViewCreateQ, textViewSteps;
    private ArrayList<String> selected;
    private DatabaseRaceQr dataBaseSets;
    private Integer countStep = 0;

    private ConstraintLayout layoutHomeT, layoutAtrasT;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_race);
        InitialConfig();
        databaseQuestion = FirebaseDatabase.getInstance().getReference("Question");

    }

    private void InitialConfig() {
        layoutHomeT = findViewById(R.id.layoutHomeT);
        layoutAtrasT = findViewById(R.id.layoutAtrasT);
        buttonAddQuestion = findViewById(R.id.buttonAddQuestionLab);
        buttonNextCreateQr = findViewById(R.id.buttonNextCreateQr);
        buttonBackCreateQr = findViewById(R.id.buttonBackCreateQr);
        listViewQuestionsLab = findViewById(R.id.listViewPreguntasRace);
        editTextRaceName = findViewById(R.id.editTextActivityRaceName);
        textViewCreateQ = findViewById(R.id.textViewCreateQ);
        textViewSteps = findViewById(R.id.textViewRaceQ);

        listViewQuestionsLab.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);

        layoutHomeT.setOnClickListener(this);
        layoutAtrasT.setOnClickListener(this);
        buttonAddQuestion.setOnClickListener(this);
        buttonNextCreateQr.setOnClickListener(this);
        buttonBackCreateQr.setOnClickListener(this);
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
    public void onClick(View v)
    {
        switch (v.getId())
        {
            //Botones TalleristaActivityLab -----------------------------------------
            case R.id.layoutHomeT:
                Intent intentC = new Intent(this, ActivityProfileTallerista.class);
                startActivity(intentC);
                finish();
                break;
            case R.id.layoutAtrasT:
                Intent intent = new Intent(this, ActivityProfileTallerista.class);
                startActivity(intent);
                finish();
                break;
            case R.id.buttonAddQuestionLab:
                Intent intentB = new Intent(this, ActivityCreateQuestion.class);
                startActivity(intentB);
                break;
            case R.id.buttonNextCreateQr:
                if (countStep == 0)
                {
                    buttonBackCreateQr.setVisibility(View.VISIBLE);
                    buttonBackCreateQr.setEnabled(true);
                    textViewSteps.setText("Selecciona las preguntas que quieres en tu carrera Qr ");
                    buttonAddQuestion.setVisibility(View.VISIBLE);
                    buttonAddQuestion.setEnabled(true);
                    listViewQuestionsLab.setVisibility(View.VISIBLE);
                    listViewQuestionsLab.setEnabled(true);
                    editTextRaceName.setVisibility(View.INVISIBLE);
                    editTextRaceName.setEnabled(false);
                    textViewCreateQ.setVisibility(View.VISIBLE);
                    buttonNextCreateQr.setText("Crear Carrera");
                    countStep++;
                } else if (countStep == 1) createRace();
                break;
            case R.id.buttonBackCreateQr:
                textViewSteps.setText("Ingresa el nombre de la carrera");
                buttonAddQuestion.setVisibility(View.INVISIBLE);
                buttonAddQuestion.setEnabled(false);
                listViewQuestionsLab.setVisibility(View.INVISIBLE);
                listViewQuestionsLab.setEnabled(false);
                editTextRaceName.setVisibility(View.VISIBLE);
                editTextRaceName.setEnabled(true);
                buttonNextCreateQr.setText("Siguiente");
                buttonBackCreateQr.setVisibility(View.INVISIBLE);
                buttonBackCreateQr.setEnabled(false);
                textViewCreateQ.setVisibility(View.INVISIBLE);
                countStep--;
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
                       finish();
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
                    Toast.makeText(this,"El nombre debe tener menos de 18 caracteres",Toast.LENGTH_SHORT).show();

                }
            } else{
                Toast.makeText(this,"Debes nombrar la carrera",Toast.LENGTH_SHORT).show();
            }
       }
       else  {
           Toast.makeText(this,"No hay preguntas seleccionadas",Toast.LENGTH_SHORT).show();
       }



   }
}

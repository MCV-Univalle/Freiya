package com.example.user.talleristamod.PackageGameRaceQr;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.SparseBooleanArray;
import android.widget.ListView;
import android.widget.Toast;

import com.example.user.talleristamod.GlobalVariables.GlobalVariables;
import com.example.user.talleristamod.PackageGameRaceQr.Questions.ObjectQuestion;
import com.example.user.talleristamod.PackageProfiles.ActivityActivitiesFreiya;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class DatabaseRaceQr {
    private Context context;


    public DatabaseRaceQr(Context context) {
        this.context = context;
    }

    public void signalFinishActivity (){

        DatabaseReference databaseChosen = FirebaseDatabase.getInstance().getReference("Activity/ActivityQrRace/"+GlobalVariables.ID_ACTIVITY+"/stateA");

        databaseChosen.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String stateActivity = (String) dataSnapshot.getValue();

                if (stateActivity.equals("Disable")){
                    Intent intent = new Intent(context, ActivityActivitiesFreiya.class);
                    Toast.makeText(context, "Actividad Deshabilitada", Toast.LENGTH_SHORT).show();
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void createQrRaceCopy(ObjectActivityQrRace objectActivityQrRace) {
        objectActivityQrRace.setCreator("Copy");
        SecureRandom random = new SecureRandom();
        String randomCode = new BigInteger(20, random).toString(32).toUpperCase();
        GlobalVariables.JOIN_CODE = randomCode;

        DatabaseReference databaseCreateImg = FirebaseDatabase.getInstance().getReference("Activity/ActivityQrRace");
        String id = databaseCreateImg.push().getKey();
        databaseCreateImg.child(id).setValue(objectActivityQrRace);
        databaseCreateImg.child(id).child("copy").setValue("true");
        databaseCreateImg.child(id).child("joinCode").setValue(randomCode);

        GlobalVariables.ID_ACTIVITY = id;
        Intent intent = new Intent(context, ActivityLeaderBoard.class);
        context.startActivity(intent);
    }

    public void ObtainRace(final ArrayList<String> selected, final String raceName) {

        DatabaseReference databaseQuestion = FirebaseDatabase.getInstance().getReference("Question");

        final List<ObjectQuestion> objectQuestions = new ArrayList<>();
        final List<String> idQuestions = new ArrayList<>();
        idQuestions.clear();
        objectQuestions.clear();

        databaseQuestion.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
                        ObjectQuestion question = issue.getValue(ObjectQuestion.class);
                        question.setId(issue.getKey());

                        if (selected.contains(question.pregunta)) {
                            objectQuestions.add(question);
                            idQuestions.add(question.getId());
                        }
                    }
                }

                DatabaseReference databaseQuestion = FirebaseDatabase.getInstance().getReference("Activity/ActivityQrRace");
                String id = databaseQuestion.push().getKey();
                GlobalVariables.ID_ACTIVITY = id;

                ObjectActivityQrRace objectActivityQrRace = new ObjectActivityQrRace(raceName, idQuestions, "Disable", "dasfasfasfasg1565" , FirebaseAuth.getInstance().getCurrentUser().getUid(), "false");
                databaseQuestion.child(id).setValue(objectActivityQrRace);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public ArrayList<String> SelectedItems(ListView listView) {

        ArrayList<String> selected = new ArrayList<>();
        //Obtengo los elementos seleccionados de mi lista
        SparseBooleanArray seleccionados = listView.getCheckedItemPositions();

        if(seleccionados == null || seleccionados.size()==0){

        }else{
            for (int i=0; i<seleccionados.size(); i++) {

                if (seleccionados.valueAt(i)) {
                    selected.add(listView.getItemAtPosition(seleccionados.keyAt(i)).toString());
                }
            }
        }

        return selected;
    }

    public void obtenerIdPreguntasActividadSeleccionada(final  ArrayList<String> idQuestion)
    {
        FirebaseUser user =  FirebaseAuth.getInstance().getCurrentUser();
        final String userUid = user.getUid();
        final String userName = user.getDisplayName();

        DatabaseReference databaseReferenceRace = FirebaseDatabase.getInstance().getReference("Activity/ActivityQrRace/"+GlobalVariables.ID_ACTIVITY+"/Participantes");
        databaseReferenceRace.child(userUid).setValue(userName);

        cargarArrayQrVisitar(idQuestion);
        Intent intent = new Intent(context, ActivityFollowCodeQr.class);
        intent.putStringArrayListExtra("idQuestion", idQuestion);
        intent.putExtra("activityClassId", 1);
        context.startActivity(intent);


    }

    public void obtenerIdQrRace(final String selectedActivity)
    {
        final DatabaseReference databaseReferenceRace = FirebaseDatabase.getInstance().getReference("Activity/ActivityQrRace");

        databaseReferenceRace.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot objectQuestionsSnapShot : dataSnapshot.getChildren())
                {
                    ObjectActivityQrRace race = objectQuestionsSnapShot.getValue(ObjectActivityQrRace.class);
                    race.setId(objectQuestionsSnapShot.getKey());
                    String idRace = race.getId();

                    if (race.getNombre().equals(selectedActivity))
                    {
                        SecureRandom random = new SecureRandom();
                        String randomCode = new BigInteger(20, random).toString(32).toUpperCase();
                        GlobalVariables.ID_ACTIVITY = idRace;
                        GlobalVariables.JOIN_CODE = randomCode;

                        DatabaseReference databaseQuestion = FirebaseDatabase.getInstance().getReference("Activity/ActivityQrRace/"+GlobalVariables.ID_ACTIVITY+"/joinCode");
                        databaseQuestion.setValue(randomCode);

                        DatabaseReference databaseStateAImg = FirebaseDatabase.getInstance().getReference("Activity/ActivityQrRace/"+GlobalVariables.ID_ACTIVITY+"/stateA");
                        databaseStateAImg.setValue("Enabled");

                        Intent intent2 = new Intent(context, ActivityLeaderBoard.class);
                        context.startActivity(intent2);
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void cargarArrayQrVisitar(ArrayList<String> questions)
    {
        GlobalVariables.QRVISIT = new ArrayList<Integer>();

        for (int i=0; i < questions.size(); i++)
        {
            int numero = (int) ((Math.random() *  questions.size()) + 1);
            while(GlobalVariables.QRVISIT .contains(numero)== true)
            {
                numero = (int) ((Math.random() *  questions.size()) + 1);
            }

            GlobalVariables.QRVISIT .add(numero);

        }

    }

    public void AddQuestionLab(String Pregunta, List<String> respuestas) {

        DatabaseReference databaseQuestion = FirebaseDatabase.getInstance().getReference("Question");
        String id = databaseQuestion.push().getKey();
        ObjectQuestion objectQuestion = new ObjectQuestion(Pregunta, respuestas);
        databaseQuestion.child(id).setValue(objectQuestion);
    }

    public void cargarPreguntasActividad(DataSnapshot dataSnapshot, ArrayList<String> idPreguntasActividad, ArrayList<ObjectQuestion> questions) {

        for (int i = 0; i < idPreguntasActividad.size(); i++)
        {
            for (DataSnapshot objectQuestionsSnapShot : dataSnapshot.getChildren())
            {
                ObjectQuestion question = objectQuestionsSnapShot.getValue(ObjectQuestion.class);
                if (objectQuestionsSnapShot.getKey().equalsIgnoreCase(idPreguntasActividad.get(i)))
                {
                    questions.add(question);
                    break;
                }
            }
        }
    }


    public void oila (){

        FirebaseUser user =  FirebaseAuth.getInstance().getCurrentUser();
        final String userUid = user.getUid();

        DatabaseReference databaseEstudiante = FirebaseDatabase.getInstance().getReference("Estudiante/"+userUid);
        databaseEstudiante.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = (String) dataSnapshot.child("Puntaje").getValue();
                Integer points = Integer.parseInt(value);
                points += GlobalVariables.POINTS_EARNED;
                DatabaseReference databaseEstudiantePoints = FirebaseDatabase.getInstance().getReference("Estudiante/"+userUid);
                databaseEstudiantePoints.child("Puntaje").setValue(points.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void validateCode(final String selectedActivityCode){
        DatabaseReference databaseReferenceRace = FirebaseDatabase.getInstance().getReference("Activity/ActivityQrRace");
        databaseReferenceRace.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList <String> listRaceQr = new ArrayList<>();
                for (DataSnapshot objectQuestionsSnapShot : dataSnapshot.getChildren())
                {
                    ArrayList<String> idQuestion = new ArrayList<String>();
                    ObjectActivityQrRace race = objectQuestionsSnapShot.getValue(ObjectActivityQrRace.class);
                    race.setId(objectQuestionsSnapShot.getKey());
                    listRaceQr.add(race.getJoinCode());

                    if (race.getJoinCode().equals(selectedActivityCode))
                    {
                        for (int i=0; i< race.getIdQuestions().size(); i++)
                        {
                            idQuestion.add(race.getIdQuestions().get(i));
                        }

                        GlobalVariables.ID_ACTIVITY = race.getId();
                        obtenerIdPreguntasActividadSeleccionada(idQuestion);
                        break;

                    }
                }

                if (!listRaceQr.contains(selectedActivityCode)) Toast.makeText(context, "Error al digitar el codigo de ingreso", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}

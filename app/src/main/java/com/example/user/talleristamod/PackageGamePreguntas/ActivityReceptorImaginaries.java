package com.example.user.talleristamod.PackageGamePreguntas;

import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.talleristamod.GlobalVariables.GlobalVariables;
import com.example.user.talleristamod.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.user.talleristamod.R.*;

public class ActivityReceptorImaginaries extends AppCompatActivity {

    public TextView textViewSelectedStudent;
    public DatabaseImaginaries dataBaseSets;
    public ValueEventListener valueEventListenerReceptor;
    public String userUid;
    public Integer temp;
    public MediaPlayer mediaPlayer;
    public Vibrator vibrator;
    DatabaseReference databaseChosen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_receptor_imaginaries);


        dataBaseSets = new DatabaseImaginaries(this);
        textViewSelectedStudent = findViewById(R.id.textViewSelectedStudent);
        receptorSignal();
        dataBaseSets.signalFinishActivity();
    }

    public void receptorSignal (){
        userUid =  FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseChosen = FirebaseDatabase.getInstance().getReference("Activity/ActivityImaginaries/"+GlobalVariables.ID_ACTIVITY);;

        valueEventListenerReceptor = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                vibrator = (Vibrator) getSystemService(getApplicationContext().VIBRATOR_SERVICE);
                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.selectedsound);


                if (vibrator.hasVibrator()) {
                    temp = 0;
                    if (dataSnapshot.child("temporizador").exists()) {
                        temp = Integer.parseInt(dataSnapshot.child("temporizador").getValue().toString());
                        String idChosen = (String) dataSnapshot.child("elegido").getValue();

                        if (userUid.equals(idChosen)) {
                            textViewSelectedStudent.setText("Fuiste Elegido");
                            vibrator.vibrate(temp*1000);
                            mediaPlayer.start();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mediaPlayer.stop();
                                }
                            }, temp*1000);

                        } else

                            textViewSelectedStudent.setText("Manten en celular en tu mano sin salir de la aplicacion");
                    }
                } else
                    Toast.makeText(getApplicationContext(), "este dispositivo no puede vibrar", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }};

        databaseChosen.addValueEventListener(valueEventListenerReceptor);
}

    @Override
    protected void onStop() {
        super.onStop();
        databaseChosen.removeEventListener(valueEventListenerReceptor);
    }
}


package com.example.user.talleristamod.PackageGameChallenge.TalleristaProfile;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.talleristamod.PackageGameChallenge.DatabaseChallenge;
import com.example.user.talleristamod.PackageGameChallenge.ObjectActivityChallenge;
import com.example.user.talleristamod.PackageProfiles.ActivityActivitiesFreiya;
import com.example.user.talleristamod.PackageProfiles.ProfileTallerista.ActivityProfileTallerista;
import com.example.user.talleristamod.R;
import com.google.firebase.auth.FirebaseAuth;

public class ActivityCreateChallenge extends AppCompatActivity implements View.OnClickListener
{

    private EditText editTextChallengeName, editTextChallengeDescription;
    private Button buttonCreateChallenge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_challenge);

        InitialConfig();
    }

    private void InitialConfig() {
        editTextChallengeName = (EditText) findViewById(R.id.editTextChallengeName);
        editTextChallengeDescription = (EditText) findViewById(R.id.editTextChallengeDescription);
        buttonCreateChallenge = (Button) findViewById(R.id.buttonCreateChallenge);
        buttonCreateChallenge.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
                case R.id.buttonCreateChallenge:

                    if (validations()) {
                        final String challengeName = editTextChallengeName.getText().toString();
                        final String challengeDescription = editTextChallengeDescription.getText().toString();


                    new AlertDialog.Builder(this)
                            .setTitle("Crearas el desafio " + challengeName)
                            .setMessage(challengeDescription)
                            .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ObjectActivityChallenge objectActivityChallenge = new ObjectActivityChallenge(challengeName, challengeDescription, "none", "Disable", FirebaseAuth.getInstance().getCurrentUser().getUid(), "false");
                                    DatabaseChallenge dataBaseSets = new DatabaseChallenge(getApplicationContext());
                                    dataBaseSets.createChallenge(objectActivityChallenge);

                                    Toast.makeText(getApplicationContext(), "Has creado el desafio "+challengeName, Toast.LENGTH_LONG).show();

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

                    break;
            }

    }

    private boolean validations() {

        if(editTextChallengeName.getText() != null && editTextChallengeDescription.getText() != null ){
            return true;
        } else {
            Toast.makeText(this, "Debes dar un nombre y una descripcion al reto", Toast.LENGTH_LONG).show();
            return false;
        }
    }



}

package com.example.user.talleristamod.PackageProfiles.Login;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.user.talleristamod.GlobalVariables.GlobalVariables;
import com.example.user.talleristamod.PackageProfiles.ProfileEstudiante.ActivityProfileEstudiante;
import com.example.user.talleristamod.PackageProfiles.ProfileTallerista.ActivityProfileTallerista;
import com.example.user.talleristamod.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.example.user.talleristamod.GlobalVariables.GlobalVariables.USER_TYPE;

public class TalleristaPrincipalMenu extends AppCompatActivity implements View.OnClickListener {

    private Button buttonActivitiesMenu, buto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tallerista_principal_menu);
        verifyPermission();
        checkingLogin();
        InitialConfig();
    }


    private void InitialConfig() {
        buttonActivitiesMenu = (Button) findViewById(R.id.bTalleristas);
        buto = (Button) findViewById(R.id.bEstudiante);
        buttonActivitiesMenu.setOnClickListener(this);
        buto.setOnClickListener(this);

    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {
            //Botones TalleristaPrincipalMenu ---------------------------------------
            case R.id.bTalleristas:
                Intent intent = new Intent(v.getContext(), ActivityProfileTallerista.class);
                USER_TYPE = "Tallerista";
                v.getContext().startActivity(intent);
                break;

            case R.id.bEstudiante:
                Intent intent1 = new Intent(v.getContext(), ActivityProfileEstudiante.class);
                USER_TYPE = "Estudiante";
                GlobalVariables.ACTIVITY_FUNCTION = "";
                v.getContext().startActivity(intent1);
                break;
        }
    }


    private void verifyPermission()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            int permsRequestCode = 100;
            String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA};

            if (ActivityCompat.checkSelfPermission(TalleristaPrincipalMenu.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(TalleristaPrincipalMenu.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            {
            }
            else
                {
                    requestPermissions(perms, permsRequestCode);
                }

        }
    }

    private void checkingLogin() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {

            DatabaseLogin dataBaseSets = new DatabaseLogin(this);
            dataBaseSets.combrobarUserBd();

        } else {
            goLoginScreen();
        }
    }

    //recordar añadir el boton de cerrar sesion --------------------------------------------------------------------
    private void goLoginScreen()
    {
        Intent intent = new Intent(this, ActivityLogin.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }


}

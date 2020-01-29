package com.example.user.talleristamod.PackageProfiles.ProfileTallerista;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.user.talleristamod.R;

import java.util.ArrayList;

public class ActivityGivePoints extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_points);

    }

    public void givePoints (ArrayList<String> studentList){}
    public void givePoints (String studentName){}
}

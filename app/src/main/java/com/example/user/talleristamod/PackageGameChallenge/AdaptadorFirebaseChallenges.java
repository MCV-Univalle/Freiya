package com.example.user.talleristamod.PackageGameChallenge;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.example.user.talleristamod.GlobalVariables.GlobalVariables;
import com.example.user.talleristamod.QrRaceHolder;
import com.example.user.talleristamod.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;

public class AdaptadorFirebaseChallenges extends FirebaseRecyclerAdapter<ObjectActivityChallenge, QrRaceHolder> {

    Context context;
    DatabaseChallenge dataBaseSets;
    String selectedChallenge;


    public AdaptadorFirebaseChallenges(Class<ObjectActivityChallenge> modelClass, int modelLayout, Class<QrRaceHolder> viewHolderClass, DatabaseReference ref, Context c) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        context = c;
    }


    @Override
    protected void populateViewHolder(QrRaceHolder viewHolder, final ObjectActivityChallenge model, int position) {
        dataBaseSets = new DatabaseChallenge(context);



        if (model.getStateA().equals("Enabled")) {

            viewHolder.unirse.setText("EN USO");
            //viewHolder.cardView.setCardBackgroundColor(Color.parseColor("#A4FF5722"));
            viewHolder.cardView.setVisibility(View.INVISIBLE);
        }

        if (model.getStateA().equals("Disable")) {

            viewHolder.unirse.setText("LIBRE");
            //viewHolder.cardView.setCardBackgroundColor(Color.parseColor("#a4c639"));

        }

        viewHolder.nombre.setText(model.getChallengeName());
        viewHolder.cardView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                selectedChallenge = model.getChallengeName();

                if( GlobalVariables.ACTIVITY_FUNCTION == "Activate"){

                    if (model.getStateA().equals("Enabled")){
                        GlobalVariables.IS_COPY = "true";
                        dataBaseSets.createChallengeCopy(model);
                    }
                    else{
                        GlobalVariables.IS_COPY = "false";
                        dataBaseSets.obtainActivatedChallenge(selectedChallenge);
                    }
                }

    }
});
    }
}
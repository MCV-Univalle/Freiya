package com.example.user.talleristamod.PackageGameChallenge;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.Toast;

import com.example.user.talleristamod.GlobalVariables.GlobalVariables;
import com.example.user.talleristamod.QrRaceHolder;
import com.example.user.talleristamod.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;

public class AdaptadorFirebaseChallenges extends FirebaseRecyclerAdapter<ObjectActivityChallenge, QrRaceHolder> {

    Context context;
    DatabaseChallenge dataBaseSets;
    String selectedChallenge;


    public AdaptadorFirebaseChallenges(Class<ObjectActivityChallenge> modelClass, int modelLayout, Class<QrRaceHolder> viewHolderClass, DatabaseReference ref, Context c, String Filter1,String Filter2) {
        super(modelClass, modelLayout, viewHolderClass, ref.orderByChild(Filter1).equalTo(Filter2));
        context = c;
    }


    @Override
    protected void populateViewHolder(QrRaceHolder viewHolder, final ObjectActivityChallenge model, int position) {
        dataBaseSets = new DatabaseChallenge(context);

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
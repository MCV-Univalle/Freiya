package com.example.user.talleristamod.PackageGameChallenge.TalleristaProfile;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.user.talleristamod.GlobalVariables.GlobalVariables;
import com.example.user.talleristamod.PackageGameChallenge.DatabaseChallenge;
import com.example.user.talleristamod.PackageGameChallenge.ObjectResourceChallenge;
import com.example.user.talleristamod.QrRaceHolder;
import com.example.user.talleristamod.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;

public class AdaptadorFirebaseChallengesResources extends FirebaseRecyclerAdapter<ObjectResourceChallenge, QrRaceHolder> {
    Context context;
    DatabaseChallenge dataBaseSets;
    String selectedChallenge;


    public AdaptadorFirebaseChallengesResources(Class<ObjectResourceChallenge> modelClass, int modelLayout, Class<QrRaceHolder> viewHolderClass, DatabaseReference ref, Context c) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        context = c;
    }


    @Override
    protected void populateViewHolder(QrRaceHolder viewHolder, final ObjectResourceChallenge model, int position) {
        dataBaseSets = new DatabaseChallenge(context);

        viewHolder.imagen.setImageResource(R.drawable.bombilla);
        viewHolder.nombre.setText("");
        viewHolder.unirse.setText(model.getResourceNameStudent());
        viewHolder.cardView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ActivityShowResourses.class);
                GlobalVariables.IDRESOURCE = model.resourceId;
                context.startActivity(intent);


            }
        });
    }
}

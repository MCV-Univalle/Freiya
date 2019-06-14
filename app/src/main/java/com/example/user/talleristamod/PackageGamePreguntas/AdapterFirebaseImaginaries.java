package com.example.user.talleristamod.PackageGamePreguntas;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.example.user.talleristamod.GlobalVariables.GlobalVariables;
import com.example.user.talleristamod.QrRaceHolder;
import com.example.user.talleristamod.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;

public class AdapterFirebaseImaginaries extends FirebaseRecyclerAdapter<ObjectActivityImaginaries, QrRaceHolder> {
    Context context;
    DatabaseImaginaries dataBaseSets;
    String selectedActivity, selectedActivityId;


    public AdapterFirebaseImaginaries(Class<ObjectActivityImaginaries> modelClass, int modelLayout, Class<QrRaceHolder> viewHolderClass, DatabaseReference ref, Context c) {



        super(modelClass, modelLayout, viewHolderClass, ref);
        context = c;
    }


    @Override
    protected void populateViewHolder(QrRaceHolder viewHolder, final ObjectActivityImaginaries model, int position) {

        dataBaseSets = new DatabaseImaginaries(context);

        if (model.getStateA().equals("Enabled")) {
            viewHolder.imagen.setImageResource(R.drawable.warning);
            viewHolder.unirse.setText("EN USO");
            viewHolder.cardView.setCardBackgroundColor(Color.parseColor("#A4FF5722"));
        }

        if (model.getStateA().equals("Disable")) {
            viewHolder.imagen.setImageResource(R.drawable.good);
            viewHolder.unirse.setText("LIBRE");
            viewHolder.cardView.setCardBackgroundColor(Color.parseColor("#a4c639"));
        }

        viewHolder.nombre.setText(model.getNombre());
        viewHolder.cardView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {

                selectedActivity = model.getNombre();
                selectedActivityId = model.getId();

                if( GlobalVariables.ACTIVITY_FUNCTION == "Activate"){

                    if (model.getStateA().equals("Enabled")){
                        GlobalVariables.IS_COPY = "true";
                        dataBaseSets.createImaginaresCopy(model);
                    }
                    else{
                        GlobalVariables.IS_COPY = "false";
                        dataBaseSets.showActivityImaginaries(selectedActivity);
                    }
                }

            }
        });
    }
}

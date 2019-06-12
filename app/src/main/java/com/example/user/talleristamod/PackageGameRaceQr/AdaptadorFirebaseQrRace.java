package com.example.user.talleristamod.PackageGameRaceQr;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import com.example.user.talleristamod.GlobalVariables.GlobalVariables;
import com.example.user.talleristamod.PackageProfiles.ActivityLeaderBoard;
import com.example.user.talleristamod.QrRaceHolder;
import com.example.user.talleristamod.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;

/*
    Representa el punto de unión entre firebase y el elemento lista.
    Extiende de FirebaseRecyclerAdapter y como elementos necesarios se pasa la clase del objeto se se podrá en los ítems
    y la clase que representa el holder
 */


public class AdaptadorFirebaseQrRace extends FirebaseRecyclerAdapter<ObjectActivityQrRace, QrRaceHolder>
{
    Context context;
    DatabaseRaceQr dataBaseSets;
    String selectedActivity;


    public AdaptadorFirebaseQrRace(Class<ObjectActivityQrRace> modelClass, int modelLayout, Class<QrRaceHolder> viewHolderClass, DatabaseReference ref, Context c) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        context = c;
    }

    @Override
    protected void populateViewHolder(final QrRaceHolder viewHolder, final ObjectActivityQrRace model, int position) {
        dataBaseSets = new DatabaseRaceQr(context);

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

                if( GlobalVariables.ACTIVITY_FUNCTION == "Activate")
                {
                    dataBaseSets.obtenerIdQrRace(selectedActivity);
                }



            }
        });
    }
}



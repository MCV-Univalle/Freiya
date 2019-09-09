package com.example.user.talleristamod.PackageGameRaceQr;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.example.user.talleristamod.GlobalVariables.GlobalVariables;
import com.example.user.talleristamod.QrRaceHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.logging.Filter;

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


    public AdaptadorFirebaseQrRace(Class<ObjectActivityQrRace> modelClass, int modelLayout, Class<QrRaceHolder> viewHolderClass, DatabaseReference ref, Context c, String Filter1,String Filter2) {
        super(modelClass, modelLayout, viewHolderClass, ref.orderByChild(Filter1).equalTo(Filter2));
        context = c;
    }

    @Override
    protected void populateViewHolder(final QrRaceHolder viewHolder, final ObjectActivityQrRace model, final int position) {
        dataBaseSets = new DatabaseRaceQr(context);

        //getRef(position).getKey();

        viewHolder.nombre.setText(model.getNombre());
        viewHolder.cardView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                selectedActivity = model.getNombre();

                if(GlobalVariables.ACTIVITY_FUNCTION == "Activate"){

                    if (model.getStateA().equals("Enabled")){
                        GlobalVariables.IS_COPY = "true";
                        dataBaseSets.createQrRaceCopy(model);

                    }
                    else{
                        GlobalVariables.IS_COPY = "false";
                        dataBaseSets.obtenerIdQrRace(selectedActivity);
                    }
                }


            }
        });

        viewHolder.imagenDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedActivity = model.getNombre();
                dataBaseSets.obtainDetailsQrRace(selectedActivity);
            }
        });
    }

}



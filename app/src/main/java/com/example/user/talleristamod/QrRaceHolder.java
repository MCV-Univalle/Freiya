package com.example.user.talleristamod;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class QrRaceHolder extends RecyclerView.ViewHolder {

/*
   Clase: QrRaceHolder
   Representa el elemento que se renderizará en el elemento de lista.
   Extentiende de RecyclerView.ViewHolder y como mínimo en constructor tendrá como parámetro el layout que se utilizará.
   Adicionalmente se pasará como parámetro cualquier cosa que sea necesaria.
   En este constructor se inicializarán todos los elementos del layout
 */

    public CircleImageView imagen;
    public CardView cardView;
    public TextView nombre, unirse;
    public CircleImageView imagenDetails;


    public QrRaceHolder(View itemView) {
        super(itemView);
        cardView = itemView.findViewById(R.id.cardViewHolder);
        imagen = itemView.findViewById(R.id.imagenHolder);
        nombre = itemView.findViewById(R.id.nombreHolder);
        unirse = itemView.findViewById(R.id.unirseHolder);
        imagenDetails = itemView.findViewById(R.id.imagenDetailsHolder);
    }
}


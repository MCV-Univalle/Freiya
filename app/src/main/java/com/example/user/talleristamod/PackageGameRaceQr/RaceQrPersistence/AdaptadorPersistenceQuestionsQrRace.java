package com.example.user.talleristamod.PackageGameRaceQr.RaceQrPersistence;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.talleristamod.R;

import java.util.ArrayList;

public class AdaptadorPersistenceQuestionsQrRace extends RecyclerView.Adapter<AdaptadorPersistenceQuestionsQrRace.HolderLeadeboardRaceQrPersistence> {

    ArrayList<String> questions;

    public AdaptadorPersistenceQuestionsQrRace(ArrayList<String> questions) {
        this.questions = questions;
    }

    @NonNull
    @Override
    public HolderLeadeboardRaceQrPersistence onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_race_questions_answers, parent, false);

        return new HolderLeadeboardRaceQrPersistence(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorPersistenceQuestionsQrRace.HolderLeadeboardRaceQrPersistence holder, int position) {
        holder.question.setText(questions.get(position));
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public class HolderLeadeboardRaceQrPersistence extends RecyclerView.ViewHolder {
        public TextView question, numberFails;
        public CardView cardViewHolder;

        public HolderLeadeboardRaceQrPersistence(View itemView) {
            super(itemView);
            cardViewHolder = itemView.findViewById(R.id.cardViewQestionsAnswHolder);
            question = itemView.findViewById(R.id.tvQuestionPersistence);
            numberFails = itemView.findViewById(R.id.textViewNumberErrors);

        }
    }
}

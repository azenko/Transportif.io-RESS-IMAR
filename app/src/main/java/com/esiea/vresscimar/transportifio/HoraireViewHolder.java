package com.esiea.vresscimar.transportifio;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by vress on 14/12/2016.
 */

public class HoraireViewHolder extends RecyclerView.ViewHolder{

    private TextView textTimeStation;
    private TextView textTrainCode;

    //itemView est la vue correspondante à 1 cellule
    public HoraireViewHolder(View itemView) {
        super(itemView);

        //c'est ici que l'on fait nos findView

        textTimeStation = (TextView) itemView.findViewById(R.id.timeStation);
        textTrainCode = (TextView) itemView.findViewById(R.id.trainCode);
    }

    //puis ajouter une fonction pour remplir la cellule en fonction d'un MyObject
    public void bind(HoraireAPI monHoraire){
        textTimeStation.setText(monHoraire.getTimeAndStation());
        textTrainCode.setText(monHoraire.getTrainCode());
    }
}
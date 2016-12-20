package com.esiea.vresscimar.transportifio;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by vress on 14/12/2016.
 */

public class HoraireAdapter extends RecyclerView.Adapter<HoraireViewHolder> {

    ArrayList<HoraireAPI> list;

    //ajouter un constructeur prenant en entrée une liste
    public HoraireAdapter(ArrayList<HoraireAPI> list) {
        this.list = list;
    }

    //cette fonction permet de créer les viewHolder
    //et par la même indiquer la vue à inflater (à partir des layout xml)
    @Override
    public HoraireViewHolder onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_horaire,viewGroup,false);
        return new HoraireViewHolder(view);
    }

    //c'est ici que nous allons remplir notre cellule avec le texte/image de chaque MyObjects
    @Override
    public void onBindViewHolder(HoraireViewHolder myViewHolder, int position) {
        HoraireAPI myObject = list.get(position);
        myViewHolder.bind(myObject);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
package com.argueta.proyectogym.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.argueta.proyectogym.Models.Entreno;
import com.argueta.proyectogym.R;

import java.util.ArrayList;

public class AdapterRutinasCompletas extends RecyclerView.Adapter<AdapterRutinasCompletas.MyViewHolder> {

    Context context;

    ArrayList<Entreno> list;

    public AdapterRutinasCompletas(Context context, ArrayList<Entreno> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Entreno entreno=list.get(position);
        holder.tvtiempo.setText(entreno.getTiempo());
        holder.tvfecha.setText(entreno.getFecha());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tvtiempo, tvfecha;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvtiempo=itemView.findViewById(R.id.tvtiempo);
            tvfecha=itemView.findViewById(R.id.tvfecha);

        }
    }

}

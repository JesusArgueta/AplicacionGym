package com.argueta.proyectogym.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.argueta.proyectogym.Fragments.EjerciciosRutinaFragment;
import com.argueta.proyectogym.Fragments.RutinasFragment;
import com.argueta.proyectogym.Models.Rutina;
import com.argueta.proyectogym.R;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterRutina extends FirebaseRecyclerAdapter<Rutina,AdapterRutina.myviewholder> {

    public AdapterRutina(@NonNull FirebaseRecyclerOptions<Rutina> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull Rutina model) {

        holder.nombre.setText(model.getNombre());
        holder.descripcion.setText(model.getRepeticiones());
        Glide.with(holder.img1.getContext()).load(model.getPic()).into(holder.img1);

        holder.img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity=(AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new EjerciciosRutinaFragment(model.getNombre(),model.getDescripcion(),model.getPic())).addToBackStack(null).commit();
            }
        });

    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerowdesign,parent,false);
        return new myviewholder(view);
    }

    public class myviewholder extends RecyclerView.ViewHolder{

        CircleImageView img1;
        TextView nombre;
        TextView descripcion;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            img1=(CircleImageView)itemView.findViewById(R.id.img1);
            nombre=itemView.findViewById(R.id.name);
            descripcion=itemView.findViewById(R.id.description);
        }
    }

}

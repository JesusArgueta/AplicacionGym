package com.argueta.proyectogym.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.argueta.proyectogym.Login;
import com.argueta.proyectogym.MainActivity;
import com.argueta.proyectogym.Models.ProgresoUser;
import com.argueta.proyectogym.Models.Users;
import com.argueta.proyectogym.Models.VistaAdministrador;
import com.argueta.proyectogym.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;

public class AdapaterUser extends RecyclerView.Adapter<AdapaterUser.MyViewHolder> {
    Context context;
    ArrayList<Users> list;

    public AdapaterUser(Context context, ArrayList<Users> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.itemuser,parent,false);
        return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Users user=list.get(position);
        holder.nombre.setText(user.getUserName());
        holder.contraseña.setText(user.getPassword());
        holder.email.setText(user.getMail());
        holder.papelera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // creating a variable for our Database
                // Reference for Firebase.
                DatabaseReference dbref= FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUserId());
                // we are use add listerner
                // for event listener method
                dbref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        // remove the value at reference
                        dataSnapshot.getRef().removeValue();
                        list.clear();
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(holder.itemView.getContext(),ProgresoUser.class);
                it.putExtra("itemDetail", (Serializable) user);
                holder.itemView.getContext().startActivity(it);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class  MyViewHolder extends RecyclerView.ViewHolder{

        TextView nombre,contraseña,email;
        ImageView papelera;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nombre=itemView.findViewById(R.id.nombre);
            contraseña=itemView.findViewById(R.id.contraseña);
            email=itemView.findViewById(R.id.email);
            papelera=itemView.findViewById(R.id.papelera);

        }
    }

}

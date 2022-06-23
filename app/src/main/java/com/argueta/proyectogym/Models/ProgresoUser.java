package com.argueta.proyectogym.Models;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.argueta.proyectogym.ProgresoUserOne;
import com.argueta.proyectogym.R;
import com.argueta.proyectogym.RutinaUser;

import java.io.Serializable;

public class ProgresoUser extends AppCompatActivity {

    private Users users;
    TextView nombre;
    ImageView rutina,progresos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progreso_user);

        nombre=findViewById(R.id.nombre);
        rutina=findViewById(R.id.rutinasAcabadas);
        progresos=findViewById(R.id.progresos);
        users= (Users) getIntent().getExtras().getSerializable("itemDetail");
        nombre.setText(users.getUserName().toString());

        rutina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(ProgresoUser.this, RutinaUser.class);
                it.putExtra("itemDetail", users.getUserId().toString());
                startActivity(it);
            }
        });

        progresos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(ProgresoUser.this, ProgresoUserOne.class);
                it.putExtra("itemDetail", users.getUserId().toString());
                startActivity(it);
            }
        });


    }
}
package com.argueta.proyectogym;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.argueta.proyectogym.Models.DatosImc;
import com.argueta.proyectogym.Models.Entreno;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Progreso extends AppCompatActivity {

    DatabaseReference databaseReference;
    List<DatosImc> list;
    FirebaseAuth mAuth;

    BarChart barChart;
    PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progreso);

        mAuth= FirebaseAuth.getInstance();
        String uid=mAuth.getUid();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("IMC");
        list=new ArrayList<>();

        barChart=findViewById(R.id.bar_chart);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    DatosImc imc=dataSnapshot.getValue(DatosImc.class);
                    list.add(imc);
                }

                listar();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void listar() {
        ArrayList<BarEntry> barEntries=new ArrayList<>();
        float cont=0f;
        for(DatosImc aux:list){
            cont++;
            BarEntry barEntry=new BarEntry(cont, Float.parseFloat(aux.getImc()));
            barEntries.add(barEntry);

        }

        BarDataSet barDataSet=new BarDataSet(barEntries, "IMC");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        // setting text color.
        barDataSet.setValueTextColor(Color.BLACK);
        barChart.setData(new BarData(barDataSet));
        barChart.animateY(5000);

        // setting text size
        barDataSet.setValueTextSize(25f);
        barChart.getDescription().setEnabled(false);

    }
}
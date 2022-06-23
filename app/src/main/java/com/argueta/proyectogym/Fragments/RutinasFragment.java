package com.argueta.proyectogym.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import com.argueta.proyectogym.Adapters.AdapterRutina;
import com.argueta.proyectogym.Models.ChronometerHelper;
import com.argueta.proyectogym.Models.Entreno;
import com.argueta.proyectogym.Models.Rutina;
import com.argueta.proyectogym.R;
import com.argueta.proyectogym.Register;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class RutinasFragment extends Fragment {



    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private FirebaseAuth mAuth;
    private String mParam1;
    private String mParam2;
    RecyclerView recview;
    AdapterRutina adapter;
    private String id;

    FirebaseDatabase database;

    public Chronometer chronometer;
    private long pauseOffset = 0;
    private boolean running = false;

    private Button start;
    private Button reset;
    private Button stop;

    ChronometerHelper chronometerHelper = new ChronometerHelper();

    public RutinasFragment() {

    }

    public RutinasFragment(String id) {
        this.id=id;
    }

    public static RutinasFragment newInstance(String param1, String param2) {
        RutinasFragment fragment = new RutinasFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database=FirebaseDatabase.getInstance();
        mAuth=FirebaseAuth.getInstance();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_rutinas, container, false);
        recview=(RecyclerView)view.findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<Rutina> options =
                new FirebaseRecyclerOptions.Builder<Rutina>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child(id), Rutina.class)
                        .build();

        adapter=new AdapterRutina(options);
        recview.setAdapter(adapter);

        /////////Cronometro///////////////

        chronometer=(Chronometer) view.findViewById(R.id.chronometer);

        if(running){
            chronometer.setBase(chronometerHelper.getStartTime());
            chronometer.start();
        }else if (chronometerHelper.getStartTime() != null){
            chronometer.setBase(chronometerHelper.getStartTime());
        }
        /*if (savedInstanceState != null) {
            // Restore last state for checked position.
            long checked = savedInstanceState.getLong("crono", 0);
            chronometer.setBase(checked);
        }*/

        /*chronometer.setBase(SystemClock.elapsedRealtime());
            //Para Resetear el tiempo a los 10 seg
        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if ((SystemClock.elapsedRealtime() - chronometer.getBase()) >= 10000) {
                    chronometer.setBase(SystemClock.elapsedRealtime());
                }
            }
        });*/

        start=(Button) view.findViewById(R.id.btniniciar);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!running) {
                    chronometerHelper.setStartTime(SystemClock.elapsedRealtime() - pauseOffset);
                    chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
                    chronometer.start();
                    running = true;
                    Toast.makeText(getContext(), "Comienza la rutina", Toast.LENGTH_SHORT).show();
                }
            }
        });

        stop=(Button) view.findViewById(R.id.btnpausar);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (running) {
                    chronometer.stop();
                    pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
                    chronometerHelper.setStartTime(pauseOffset);
                    running = false;
                    Toast.makeText(getContext(), "Rutina pausada", Toast.LENGTH_SHORT).show();
                }
            }
        });

        reset=(Button) view.findViewById(R.id.btnfinalizar);{
            reset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    TimeZone myTimeZone = TimeZone.getTimeZone("Europe/Madrid");
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    simpleDateFormat.setTimeZone(myTimeZone);
                    String dateTime = simpleDateFormat.format(new Date());
                    Entreno entreno=new Entreno(chronometer.getText().toString(),dateTime);
                    database.getReference().child("Users").child(mAuth.getUid()).child("Entrenamiento").child("Entrenamiento "+dateTime).setValue(entreno);
                    chronometer.setBase(SystemClock.elapsedRealtime());
                    chronometer.stop();
                    pauseOffset = 0;
                    chronometerHelper.setStartTime(pauseOffset);
                    running = false;
                    Toast.makeText(getContext(), "Rutina finalizada", Toast.LENGTH_SHORT).show();
                }
            });
        }

        return view;

    }

    /*@Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("crono", chronometer.getBase());
    }*/

    @Override
    public void onStart(){
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop(){
        super.onStop();
        adapter.stopListening();
    }

}
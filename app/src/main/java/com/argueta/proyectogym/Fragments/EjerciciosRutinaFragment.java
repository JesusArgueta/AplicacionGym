package com.argueta.proyectogym.Fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.argueta.proyectogym.R;
import com.bumptech.glide.Glide;


public class EjerciciosRutinaFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String nombre;
    String descripcion;
    String foto;
    public EjerciciosRutinaFragment() {
        // Required empty public constructor
    }

    public EjerciciosRutinaFragment(String nombre, String descripcion, String foto) {
        this.nombre=nombre;
        this.descripcion=descripcion;
        this.foto=foto;
    }

    public static EjerciciosRutinaFragment newInstance(String param1, String param2) {
        EjerciciosRutinaFragment fragment = new EjerciciosRutinaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_ejercicios_rutina, container, false);

        ImageView imageholder=view.findViewById(R.id.imagegholder);
        TextView nameholder=view.findViewById(R.id.nameholder);
        TextView description=view.findViewById(R.id.descriptionholder);

        nameholder.setText(nombre);
        description.setText(descripcion);
        Glide.with(getContext()).load(foto).into(imageholder);


        return view;
    }

    public void onBackPressed(){
        AppCompatActivity activity=(AppCompatActivity)getContext();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new RutinasFragment()).addToBackStack(null).commit();

    }

}
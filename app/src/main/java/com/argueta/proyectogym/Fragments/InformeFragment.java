package com.argueta.proyectogym.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.argueta.proyectogym.Progreso;
import com.argueta.proyectogym.R;
import com.argueta.proyectogym.RutinasCompletas;


public class InformeFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ImageView rutinasAcabadas;
    ImageView progreso;

    public InformeFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static InformeFragment newInstance(String param1, String param2) {
        InformeFragment fragment = new InformeFragment();
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
        View view= inflater.inflate(R.layout.fragment_informe, container, false);

        rutinasAcabadas=view.findViewById(R.id.rutinasAcabadas);
        rutinasAcabadas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RutinasCompletas.class);
                getActivity().startActivity(intent);
            }
        });

        progreso=view.findViewById(R.id.progresos);
        progreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Progreso.class);
                getActivity().startActivity(intent);
            }
        });

        return view;
    }
}
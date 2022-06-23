package com.argueta.proyectogym.Fragments;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.argueta.proyectogym.Models.DatosImc;
import com.argueta.proyectogym.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


public class YoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EditText lblWeightValue = null;
    private EditText lblHeightValue = null;


    private EditText lblIMCValue = null;
    private EditText lblDescription = null;

    private Button calcular;
    private Button borrar;
    private Button guardar;

    FirebaseDatabase database;
    private FirebaseAuth mAuth;

    public YoFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static YoFragment newInstance(String param1, String param2) {
        YoFragment fragment = new YoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database= FirebaseDatabase.getInstance();
        mAuth= FirebaseAuth.getInstance();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_yo, container, false);

        lblWeightValue = view.findViewById(R.id.lblWeightValue);
        lblHeightValue = view.findViewById(R.id.lblHeightValue);


        lblIMCValue = view.findViewById(R.id.lblIMCValue);
        lblDescription = view.findViewById(R.id.lblDescriptionValue);

        calcular=view.findViewById(R.id.btnCalculate);
        calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate(v);
            }
        });

        borrar=view.findViewById(R.id.btnClearData);
        borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear(v);
            }
        });

        guardar=view.findViewById(R.id.btnSave);
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarIMC(v);
            }
        });

        return view;
    }

    private void guardarIMC(View v) {
        AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
        StringBuilder text = new StringBuilder();

        if(lblWeightValue.getText().toString().isEmpty() && lblHeightValue.getText().toString().isEmpty()){

            text.append(getString(R.string.Empty_fields));
            alert.setMessage(text);
            alert.setPositiveButton("close", null);

            alert.show();

        }else {
            calculate(v);
            String valor = lblIMCValue.getText().toString();
            String descripcion=lblDescription.getText().toString();

            TimeZone myTimeZone = TimeZone.getTimeZone("Europe/Madrid");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            simpleDateFormat.setTimeZone(myTimeZone);
            String dateTime = simpleDateFormat.format(new Date());
            String dateTimeLong = new Date().getTime()+"";

            DatosImc datosImc=new DatosImc(valor,dateTimeLong);
            database.getReference().child("Users").child(mAuth.getUid()).child("IMC").child("IMC "+dateTime).setValue(datosImc);
            Toast.makeText(getContext(), "DATOS GUARDADOS", Toast.LENGTH_SHORT).show();
            clear(v);

        }

    }

    //Method to make the BMI, with its respective exception
    public void calculate(View view){

        AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
        StringBuilder text = new StringBuilder();

        if(lblWeightValue.getText().toString().isEmpty() && lblHeightValue.getText().toString().isEmpty()){

            text.append(getString(R.string.Empty_fields));
            alert.setMessage(text);
            alert.setPositiveButton("close", null);

            alert.show();

        }else{

            double weight = Double.parseDouble(lblWeightValue.getText().toString());
            double height= Double.parseDouble(lblHeightValue.getText().toString());

            if(weight <= 0  || height <= 0){
                text.append(getString(R.string.zero_values));
                alert.setMessage(text);
                alert.setPositiveButton("cerrar", null);

                alert.show();
            }else{
                double resultBMI = weight/(height*height);
                lblIMCValue.setText(String.format("%.2f",resultBMI));
                description(resultBMI);
            }

        }

    }

    //Method that describes the result obtained from the bmi calculation
    public  void description(double resultBMI){
        if(resultBMI > 0 && resultBMI <18.5)
            lblDescription.setText(getString(R.string.under_weight));
        else
        if(resultBMI>= 18.5 && resultBMI <= 24.9)
            lblDescription.setText(getString(R.string.normal_weight));
        else
        if(resultBMI>= 25 && resultBMI <= 29.9)
            lblDescription.setText(getString(R.string.overweight));
        else
        if(resultBMI>= 30 && resultBMI <= 34.9)
            lblDescription.setText(getString(R.string.type_1_obesity));
        else
        if(resultBMI>= 35 && resultBMI <= 39.9)
            lblDescription.setText(getString(R.string.type_2_obesity));
        else
        if(resultBMI>= 40)
            lblDescription.setText(getString(R.string.type_3_obesity));
    }

    //Method to clean the text fields
    public  void clear(View v){
        lblWeightValue.setText("");
        lblHeightValue.setText("");
        lblIMCValue.setText("");
        lblDescription.setText("");
    }

}
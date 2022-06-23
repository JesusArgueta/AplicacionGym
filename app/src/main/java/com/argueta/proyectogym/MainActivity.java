package com.argueta.proyectogym;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.argueta.proyectogym.Fragments.EjerciciosFragment;
import com.argueta.proyectogym.Fragments.EjerciciosRutinaFragment;
import com.argueta.proyectogym.Fragments.ElegirRutinaFragment;
import com.argueta.proyectogym.Fragments.InformeFragment;
import com.argueta.proyectogym.Fragments.RutinasFragment;
import com.argueta.proyectogym.Fragments.YoFragment;
import com.argueta.proyectogym.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new ElegirRutinaFragment());



        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()){
                case R.id.rutina:
                    replaceFragment(new ElegirRutinaFragment());
                    break;
                case R.id.ejercicios:
                    replaceFragment(new EjerciciosFragment());
                    break;
                case R.id.informe:
                    replaceFragment(new InformeFragment());
                    break;
                case R.id.yo:
                    replaceFragment(new YoFragment());
                    break;
            }

            return true;
        });
    }

    private void replaceFragment(Fragment fragment){

        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();

    }

}
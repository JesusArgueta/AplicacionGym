package com.argueta.proyectogym.Fragments;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.argueta.proyectogym.Adapters.AdapterEjercicios;
import com.argueta.proyectogym.Adapters.AdapterRutina;
import com.argueta.proyectogym.Models.Rutina;
import com.argueta.proyectogym.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EjerciciosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EjerciciosFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView recviewejer;
    AdapterEjercicios adapter;
    FirebaseDatabase database;
    private FirebaseAuth mAuth;

    private MenuItem menuItem;
    private SearchView searchView;

    Toolbar toolbar;

    public static EjerciciosFragment newInstance(String param1, String param2) {
        EjerciciosFragment fragment = new EjerciciosFragment();
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
        mAuth= FirebaseAuth.getInstance();

        setHasOptionsMenu(true);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        inflater.inflate(R.menu.menu_item,menu);

        menuItem=menu.findItem(R.id.searchId);
        searchView= (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setIconified(true);

        SearchManager searchManager= (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mysearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                mysearch(query);
                return true;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    private void mysearch(String query) {
        FirebaseRecyclerOptions<Rutina> options =
                new FirebaseRecyclerOptions.Builder<Rutina>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Ejercicios").orderByChild("nombre").
                                startAt(query).endAt(query + "\uf8ff"), Rutina.class)
                        .build();

        adapter=new AdapterEjercicios(options);
        adapter.startListening();
        recviewejer.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_ejercicios, container, false);

        toolbar=view.findViewById(R.id.toolbar);
        AppCompatActivity activity=(AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setTitle("");

        recviewejer=(RecyclerView) view.findViewById(R.id.recviewejer);
        recviewejer.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<Rutina> options =
                new FirebaseRecyclerOptions.Builder<Rutina>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Ejercicios"), Rutina.class)
                        .build();

        adapter=new AdapterEjercicios(options);
        recviewejer.setAdapter(adapter);

        return view;
    }

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
package com.argueta.proyectogym;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.argueta.proyectogym.Fragments.RutinasFragment;
import com.argueta.proyectogym.Models.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    ProgressDialog progressDialog;

    EditText userTxt,emailTxt,passwordTxt;
    Button registrar;
    TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userTxt=findViewById(R.id.inputUser);
        emailTxt=findViewById(R.id.inputEmail);
        passwordTxt=findViewById(R.id.inputPassword);

        registrar=findViewById(R.id.btnRegistrar);
        login=findViewById(R.id.gotoLogin);

        mAuth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();

        //getSupportActionBar().hide();

        progressDialog=new ProgressDialog(Register.this);
        progressDialog.setTitle("Creando cuenta");
        progressDialog.setMessage("Estamos creando tú cuenta");

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!userTxt.getText().toString().isEmpty() && !emailTxt.getText().toString().isEmpty() && !passwordTxt.getText().toString().isEmpty()){
                    progressDialog.show();
                    mAuth.createUserWithEmailAndPassword(emailTxt.getText().toString(),passwordTxt.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressDialog.dismiss();
                                    if(task.isSuccessful()){
                                        Users user=new Users(userTxt.getText().toString(),emailTxt.getText().toString(),passwordTxt.getText().toString(),mAuth.getUid().toString());
                                        String id=task.getResult().getUser().getUid();

                                        database.getReference().child("Users").child(id).setValue(user);
                                        Toast.makeText(Register.this, "Usuario registrado", Toast.LENGTH_SHORT).show();

                                    }else{
                                        Toast.makeText(Register.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }else{
                    Toast.makeText(Register.this, "Rellena los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login=new Intent(Register.this,Login.class);
                startActivity(login);
            }
        });

    }
}
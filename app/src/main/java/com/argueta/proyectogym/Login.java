package com.argueta.proyectogym;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.argueta.proyectogym.Models.VistaAdministrador;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {

    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;

    TextView registro, resetearContraseña;
    EditText emailedt,passwordedt;
    Button enter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //getSupportActionBar().hide();

        mAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();

        progressDialog=new ProgressDialog(Login.this);
        progressDialog.setTitle("Login");
        progressDialog.setMessage("Por favor espere\n,Validacción en progreso");

        registro = findViewById(R.id.gotoRegister);
        emailedt=findViewById(R.id.inputEmail);
        passwordedt=findViewById(R.id.inputPassword);
        enter=findViewById(R.id.btnLogin);
        resetearContraseña=findViewById(R.id.forgotPassword);

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registro=new Intent(Login.this, Register.class);
                startActivity(registro);
            }
        });

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(emailedt.getText().toString().equals("root") && passwordedt.getText().toString().equals("root")){
                    Intent intent=new Intent(Login.this, VistaAdministrador.class);
                    startActivity(intent);
                }else if(!emailedt.getText().toString().isEmpty() && !passwordedt.getText().toString().isEmpty()){
                    progressDialog.show();
                    mAuth.signInWithEmailAndPassword(emailedt.getText().toString(),passwordedt.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressDialog.dismiss();
                                    if(task.isSuccessful()){
                                        Intent intent=new Intent(Login.this,MainActivity.class);
                                        startActivity(intent);
                                    }else{
                                        Toast.makeText(Login.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }else{

                    Toast.makeText(Login.this, "Rellene los campos", Toast.LENGTH_SHORT).show();

                }
            }
        });

        resetearContraseña.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resetearContraseña=new Intent(Login.this,ResetPassword.class);
                startActivity(resetearContraseña);
            }
        });

    }
}
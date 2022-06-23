package com.argueta.proyectogym;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity {

    Button resetarbtn;
    EditText emailedt;
    TextView logintv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        emailedt=findViewById(R.id.inputEmail);
        resetarbtn=findViewById(R.id.btnResetear);
        logintv=findViewById(R.id.gotoLogin);

        logintv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login=new Intent(ResetPassword.this,Login.class);
                startActivity(login);
            }
        });

        resetarbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                String correo = emailedt.getText().toString();

                if (!emailedt.getText().toString().isEmpty()) {
                    auth.sendPasswordResetEmail(correo)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(ResetPassword.this, "Comprueba tú Email", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(ResetPassword.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }else{
                    Toast.makeText(ResetPassword.this, "Rellene los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
package com.adrian.voceless.Authentication;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.adrian.voceless.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Registro extends AppCompatActivity {
    Button explicito, signUp;
    EditText emailText, passwordText;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        mAuth = FirebaseAuth.getInstance();


        explicito = findViewById(R.id.btn_inicioses);
        signUp = findViewById(R.id.btn_rest);
        emailText = findViewById(R.id.editxt_email);
        passwordText = findViewById(R.id.edittxt_pass);

        explicito.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                _metodoExplicito();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _metodoRegistro();
            }
        });
    }
    private void _metodoExplicito(){
        Intent intent = new Intent(Registro.this, Login.class);
        startActivity(intent);
        finish();
    }

    private void _metodoRegistro(){
        new AlertDialog.Builder(this)
                .setTitle("Alerta")
                .setMessage("EJE,PLO")
                .setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();

        String email =  emailText.getText().toString();
        String password = passwordText.getText().toString();

        if (password.length() > 6){
            if (password.equals(email.substring(0, password.length()))){
                Toast.makeText(this, String.valueOf("Aviso: La contraseña y el correo deben ser distintos"), Toast.LENGTH_SHORT).show();
            }
            else
            {
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    updateUI(null);
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener(){
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Registro.this, "Error inesperado", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }else{
            Toast.makeText(this, String.valueOf("La contraseña debe ser mayor a 6 carácteres"), Toast.LENGTH_SHORT).show();
        }
    }

    private void updateUI(FirebaseUser user) {
        if (user != null){
            Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Error al registrar", Toast.LENGTH_SHORT).show();
        }
    }
}
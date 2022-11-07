package com.adrian.voceless.Authentication;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.adrian.voceless.Intents.MainActivity;
import com.adrian.voceless.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    Button explicito, log, rest;
    EditText emailText, passwordText;
    CheckBox check;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        explicito = findViewById(R.id.btn_inicioses);
        log = findViewById(R.id.btn_rest);
        rest = findViewById(R.id.btn_restaurar);
        emailText = findViewById(R.id.editxt_email);
        check = findViewById(R.id.rememberBox);
        passwordText = findViewById(R.id.edittxt_pass);

        explicito.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                _metodoExplicito();
            }
        });
     
        log.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                _logearse();
            }
        });

        rest.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                _restaurar();
            }
        });

    }

    private void _restaurar() {
        Intent intent = new Intent(Login.this, Restaurar.class);
        startActivity(intent);
        finish();
    }

    private void _logearse() {
        String email =  emailText.getText().toString();
        String password = passwordText.getText().toString();

        if (email.length() > 0 && password.length() > 0){
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                updateUI(null);
                            }
                        }
                    });
        }else{
            Toast.makeText(Login.this, "Favor de introducir los datos para iniciar sesión", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateUI(FirebaseUser user) {
        if (user != null){
            Toast.makeText(this, "Acceso otorgado", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
            finish();
        }else {
            Toast.makeText(this, "Usuario no encontrado", Toast.LENGTH_SHORT).show();
        }
    }

    private void _metodoExplicito(){
        Intent intent = new Intent(Login.this, Registro.class);
        startActivity(intent);
        finish();
    }
}
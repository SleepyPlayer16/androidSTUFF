package com.adrian.voceless.Authentication;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.adrian.voceless.BottomNavigation.Home;
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

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        explicito = findViewById(R.id.btn_inicioses);
        log = findViewById(R.id.btn_rest);
        rest = findViewById(R.id.btn_restaurar);
        emailText = findViewById(R.id.editxt_email);
        passwordText = findViewById(R.id.edittxt_pass);
        check = findViewById(R.id.rememberBox);
        mAuth = FirebaseAuth.getInstance();

        SharedPreferences sharedPref = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        Boolean guardarPass = sharedPref.getBoolean("uCheck",false);
        String uEmail = sharedPref.getString("uEmail",  "");
        String uPass = sharedPref.getString("uPass",  "");



        if (guardarPass){
            emailText.setText(uEmail);
            passwordText.setText(uPass);
            check.setChecked(guardarPass);
        }else{
            check.setChecked(guardarPass);
        }


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
        Boolean guardarPass = check.isChecked();

        if (email.length() > 0 && password.length() > 0){
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                loginUI(user, guardarPass, password, email);
                            } else {
                                // If sign in fails, display a message to the user.
                                loginUI(null, guardarPass, password, email);

                                Log.w(TAG, "signInWithEmail:failure", task.getException());
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
            goToHome();
        }else {
            Toast.makeText(this, "Usuario no encontrado", Toast.LENGTH_SHORT).show();
        }
    }

    private void loginUI(FirebaseUser user, Boolean guardarPass, String pass, String email) {
        if (user != null){
            if (guardarPass){
                SharedPreferences sharedPref = this.getSharedPreferences(
                        "userInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("uPass", pass);
                editor.putString("uEmail", email);
                editor.putBoolean("uCheck", guardarPass);
                editor.apply();
            }else{
                SharedPreferences sharedPref = this.getSharedPreferences(
                        "userInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("uPass", "");
                editor.putString("uEmail", "");
                editor.putBoolean("uCheck", guardarPass);
                editor.apply();
            }

            goToHome();
        }
    }
    private void _metodoExplicito(){
        Intent intent = new Intent(Login.this, Registro.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            goToHome();
        }
    }

    private void goToHome() {
        Intent intent = new Intent(Login.this, Home.class);
        startActivity(intent);
        finish();
    }
}
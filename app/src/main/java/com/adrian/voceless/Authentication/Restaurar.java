package com.adrian.voceless.Authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.adrian.voceless.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Restaurar extends AppCompatActivity {
    Button rest;
    EditText emailText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurar);

        rest = findViewById(R.id.btn_rest);
        emailText = findViewById(R.id.editxt_email);

        rest.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                _restaurar();
            }
        });
    }

    private void _restaurar() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String emailAddress = emailText.getText().toString();

        auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    }
                });
    }
}
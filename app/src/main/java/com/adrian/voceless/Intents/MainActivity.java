 package com.adrian.voceless.Intents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.adrian.voceless.R;

public class MainActivity extends AppCompatActivity {
    Button explicito, implicito;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent mensaje = getIntent();

        String nombre = mensaje.getStringExtra("nombreDeUsuario");
        int edad = mensaje.getIntExtra("edadUsuario", 0);

        if (edad >= 18){
            Toast.makeText(this, String.valueOf(edad) + String.valueOf(" " + nombre + " Mayor de edad"), Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, String.valueOf(edad) + String.valueOf(" " + nombre + " Menor de edad"), Toast.LENGTH_SHORT).show();
        }


        explicito = findViewById(R.id.btn_exp);
        implicito = findViewById(R.id.btn_imp);

        explicito.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                _metodoExplicito();
            }
        });
    }

    private void _metodoExplicito(){
        Intent intent = new Intent(MainActivity.this, SignUp.class);
        startActivity(intent);
        finish();
    }
}
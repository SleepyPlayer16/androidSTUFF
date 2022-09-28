package com.adrian.voceless.Intents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.adrian.voceless.R;

public class SignUp extends AppCompatActivity {
    Button explicito, implicito;
    EditText textoNombre ,textoEdad, textoMensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Log.d("cicloDeVida","OnCreate()");
        explicito = findViewById(R.id.btn_exp);
        implicito = findViewById(R.id.btn_imp);

        textoNombre = findViewById(R.id.txt_nombre);
        textoEdad = findViewById(R.id.txt_edad);
        textoMensaje = findViewById(R.id.txt_mensaje);

        explicito.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                _metodoExplicito();
            }
        });

        implicito.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                _metodoImplicito();
            }
        });

    }

    private void _metodoExplicito(){
        /*Intent intent = new Intent(SignUp.this, MainActivity.class);
        intent.putExtra("nombreDeUsuario",
                textoNombre.getText().toString());
        intent.putExtra("edadUsuario",
                Integer.parseInt( textoEdad.getText().toString()));
        startActivity(intent);*/

        //In

        String mensaje = textoMensaje.getText().toString();
        Uri uri = Uri.parse("geo:0,0?q=" + mensaje);

        Intent intent2 = new Intent();
        intent2.setAction(Intent.ACTION_VIEW);
        intent2.setData(uri);
        intent2.setPackage("com.google.android.apps.maps");
        startActivity(intent2);
    }

    private void _metodoImplicito(){
        String mensaje = textoMensaje.getText().toString();
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, mensaje);
        intent.setType("text/plain");
        startActivity(intent);
    }
}
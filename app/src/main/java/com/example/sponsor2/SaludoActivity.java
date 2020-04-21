package com.example.sponsor2;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class SaludoActivity extends AppCompatActivity implements View.OnClickListener {

private Button btnContinuar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saludo);

        btnContinuar= (Button) findViewById(R.id.botonContinuar);


        btnContinuar.setOnClickListener(this);

    }


    public void Continuar(){
        Intent intencion1 = new Intent(getApplication(),codigoregistroActivity.class);
        startActivity(intencion1);
    }


    @Override
    public void onClick(View v) {
        Continuar();
    }
}
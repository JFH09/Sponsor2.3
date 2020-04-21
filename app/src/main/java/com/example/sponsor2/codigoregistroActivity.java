package com.example.sponsor2;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class codigoregistroActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText TextCodigoRegistro;
    private Button btnContinuar;
    private DatabaseReference BDUsuarios;
    private String identificacion;

@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codigoregistro);

        BDUsuarios= FirebaseDatabase.getInstance().getReference("Usuarios");
        Bundle bundle=this.getIntent().getExtras();
        identificacion= bundle.getString("identificacion1");
        TextCodigoRegistro = findViewById(R.id.TxtCodigoRegistro);
        btnContinuar = (Button) findViewById(R.id.botonContinuar);


        btnContinuar.setOnClickListener(this);
    }

    public void continuar(){
        String codigoRegistro = TextCodigoRegistro.getText().toString().trim();

        if(!TextUtils.isEmpty(codigoRegistro)){
            Map<String, Object> personaMap = new HashMap<>();
            personaMap.put("codigoRegistro",codigoRegistro);
            BDUsuarios.child("Informacion").child(identificacion).updateChildren(personaMap);

            Toast.makeText(this,"CODIGO DE REGISTRO INGRESADO CORRECTAMENTE  ", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,"Falta algun Dato / Error ", Toast.LENGTH_LONG).show();
        }



        Intent intento1 = new Intent(getApplication(), eligetumateriaActivity.class);
        startActivity(intento1);
    }

    @Override
    public void onClick(View v) {
        continuar();
    }
}

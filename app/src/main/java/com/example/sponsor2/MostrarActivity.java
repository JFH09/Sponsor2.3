package com.example.sponsor2;


import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptIntrinsicYuvToRGB;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Collection;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class MostrarActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText TextoMostrar;
    private DatabaseReference BDUsuarios;
    private Button btnMostrar;
    private String texto;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar);

        BDUsuarios= FirebaseDatabase.getInstance().getReference();
        TextoMostrar= (EditText) findViewById(R.id.txtMostrar2);
        btnMostrar = (Button ) findViewById(R.id.botonMostrar);

        btnMostrar.setOnClickListener(this);


    }

    public void Mostrar(){
        BDUsuarios.child("Usuario").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    //TextoMostrar=snapshot.getValue();
                    Log.e("Datos "," "+snapshot.getValue());
                    texto= (String) snapshot.getValue();
                    Toast.makeText(MostrarActivity.this,"Salio.."+texto,Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        Mostrar();
    }
}

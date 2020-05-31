package com.example.sponsor2;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView rVerMensaje;
    private EditText txtNombre;
    private EditText txtMensaje;
    private ImageButton btnEnviar;
    private DateFormatSymbols db;
    private List<MensajeVO> listaMensajes;
    private AdaptadorRVMensajes RAdaptadorMensaje;
    //private DateFormatSymbols FirebaseFirestore;
    private DatabaseReference BDUsuario;


    String usuario, correo;

    Bundle tomarUsuario;
    Bundle tomarCorreo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        tomarUsuario = getIntent().getExtras();
        usuario = tomarUsuario.getString("usuario");


        tomarCorreo = getIntent().getExtras();
        correo = tomarCorreo.getString("correo");


        Toast.makeText(ChatActivity.this, "Usuario = "+usuario, Toast.LENGTH_SHORT ).show();
    }
}

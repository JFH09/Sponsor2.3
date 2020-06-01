package com.example.sponsor2;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
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
    private EditText TxtCreador;
    private EditText TxtMensaje;
    private ImageButton BotonEnviar;
    private DateFormatSymbols db;
    List<MensajeVO> listaMensajes= new ArrayList<>();
    AdaptadorRVMensajes RAdaptadorMensaje;
    //private DateFormatSymbols FirebaseFirestore;
    private DatabaseReference BDChats;
    String[] Tutoria = new String[100];
    int cont=0;
    String usuario, correo;
    String nomUsuario, nomCorreo;
    Bundle tomarUsuario;
    Bundle tomarCorreo;
    Bundle tomarUsuarioDeNuevo;


    RecyclerView rvUsuarios;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);



    }



}

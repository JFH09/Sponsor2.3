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
    private String  usuario;
    //private DateFormatSymbols db;
    private List<MensajeVO> listaMensajes;
    private AdaptadorRVMensajes RAdaptadorMensaje;
    //private DateFormatSymbols FirebaseFirestore;
    private DatabaseReference BDUsuario;



    private void  setComponents(){

        Bundle recibeUser = this.getIntent().getExtras();

        if(recibeUser!=null){
            String usuario = recibeUser.getString("usuario");
            Toast.makeText(this, " Nombre" + usuario,Toast.LENGTH_LONG).show();

/*
            String id = BDUsuario.push().getKey();

            Intent intencion4 = new  Intent();
            intencion4.putExtra("Nombre",usuario);
            //intencion4.putExtra("Mensaje",Mensaje1);
            AdaptadorRVMensajes adrp = new AdaptadorRVMensajes(usuario,Mensaje1);
            BDUsuario.child("Chat").child(id).setValue(adrp);
            startActivity(intencion4);
*/

        }

        BDUsuario= FirebaseDatabase.getInstance().getReference("Chat");
        FirebaseFirestore db = FirebaseFirestore.getInstance();


        rVerMensaje = (RecyclerView) findViewById(R.id.rvMensajes);
        txtNombre = (EditText) findViewById(R.id.TxtNombre);
        txtMensaje = (EditText) findViewById(R.id.TxtMensaje);
        btnEnviar = (ImageButton) findViewById(R.id.botonEnviar);

        txtNombre.setText(usuario);
        listaMensajes = new ArrayList<>();
        RAdaptadorMensaje = new AdaptadorRVMensajes(listaMensajes);

        rVerMensaje.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rVerMensaje.setAdapter(RAdaptadorMensaje);
        rVerMensaje.setHasFixedSize(true);

        /*Map<String, Object> Enviarmensaje = new HashMap<>();
        Enviarmensaje.put("Transmisor:",txtNombre);
        Enviarmensaje.put("mensaje",txtMensaje.getText().toString().trim());*/

        db.collection("Chat")

                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        for (DocumentChange mDocumentChange : queryDocumentSnapshots.getDocumentChanges()){
                            if(mDocumentChange.getType() == DocumentChange.Type.ADDED){
                                listaMensajes.add(mDocumentChange.getDocument().toObject(MensajeVO.class));
                                RAdaptadorMensaje.notifyDataSetChanged();
                                rVerMensaje.smoothScrollToPosition(listaMensajes.size());
                            }
                        }
                    }
                });
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtMensaje.length() == 0)
                    return;
                MensajeVO RMensajeVO = new MensajeVO();
                RMensajeVO.setMensaje(txtMensaje.getText().toString());
                RMensajeVO.setNombre(usuario);

                FirebaseFirestore.getInstance().collection("Chat").add(RMensajeVO);
                txtMensaje.setText("");
            }
        });

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);




        setComponents();
    }
}

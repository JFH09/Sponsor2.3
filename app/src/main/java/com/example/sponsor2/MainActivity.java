package com.example.sponsor2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText TextEmail;
    private EditText TextPassword;
    private Button btnRegistrar;
    private Button btnEntrar;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference BDUsuario;
    private  Collection informeRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        BDUsuario= FirebaseDatabase.getInstance().getReference("Usuarios");
        TextEmail = (EditText) findViewById(R.id.TxtEmail);
        TextPassword = (EditText) findViewById(R.id.TxtPassword);

        btnRegistrar = (Button) findViewById(R.id.botonRegistrar);
        btnEntrar = (Button) findViewById(R.id.botonEntrar);
        progressDialog = new ProgressDialog(this);




        btnRegistrar.setOnClickListener(this);
        btnEntrar.setOnClickListener(this);
    }

    public void IngresoUsuario(){
        final String email = TextEmail.getText().toString().trim();
        String password  = TextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Se debe ingresar un email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Falta ingresar la contraseña",Toast.LENGTH_LONG).show();
            return;
        }


        progressDialog.setMessage("Iniciando Sesión en linea...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if(task.isSuccessful()){
                            int pos= email.indexOf("@");
                            String user = email.substring(0,pos);

                            Toast.makeText(MainActivity.this, "Bienvenido  "+user, Toast.LENGTH_LONG).show();


                            Intent intent1= new Intent(getApplication(),eligetumateriaActivity.class);
                            startActivity(intent1);
    /*
                            BDUsuario.child("Informacion").addValueEventListener(new ValueEventListener() {

                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                for (final DataSnapshot snapshot : dataSnapshot.getChildren()){

                                        BDUsuario.child("Informacion").child(snapshot.getKey()).addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                Usuarios usuarios = snapshot.getValue(Usuarios.class);
                                                String ide = usuarios.getEmail();
                                                Toast.makeText(MainActivity.this, "AQUI ENTRO!! "+ide, Toast.LENGTH_LONG).show();
                                                Log.e("email: ",ide);


                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });

                                    */

/*
                                        if (dataSnapshot.exists()) {
                                            String codigoRegistro = dataSnapshot.child("codigoRegistro").getValue().toString();
                                            Toast.makeText(MainActivity.this, "AQUI ENTRO!!!!!!!!! ", Toast.LENGTH_LONG).show();
                                            if (codigoRegistro == "No Registrado") {

                                                Intent intencion = new Intent(getApplication(), SaludoActivity.class);
                                                startActivity(intencion);

                                                Toast.makeText(MainActivity.this, "No se Encuentra Codigo de Registro ", Toast.LENGTH_LONG).show();
                                            } else {
                                                Intent intencion2 = new Intent(getApplication(), eligetumateriaActivity.class);
                                                startActivity(intencion2);
                                                Toast.makeText(MainActivity.this, "Se ha Iniciado Sesión el usuario con el email: " + TextEmail.getText(), Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Toast.makeText(MainActivity.this,"No se pudo realizar la accion... ",Toast.LENGTH_LONG).show();
                                }
                            });

                                    */
                        }else{

                            Toast.makeText(MainActivity.this,"No se pudo Iniciar Sesion... ",Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });


    }

    public void AccionRegistrar(){
        Intent intencion2 = new Intent(getApplication(),Registro_Activity.class);
        startActivity(intencion2);
    }

    public void onClick(View view) {
        switch(view.getId()){
            case R.id.botonEntrar:
                IngresoUsuario();
                break;

            case R.id.botonRegistrar:
                AccionRegistrar();
                break;
        }




    }

}

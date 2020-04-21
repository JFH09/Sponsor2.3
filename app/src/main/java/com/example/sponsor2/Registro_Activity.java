package com.example.sponsor2;

import android.app.ProgressDialog;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthActionCodeException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registro_Activity extends AppCompatActivity implements View.OnClickListener{
    private  EditText TextEmail;
    private  EditText TextPassword;
    private EditText TextNombre;
    private EditText TextNumero;
    private Button btnRegistrar;
    private  Button btnAtras;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference BDUsuarios;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_);
            BDUsuarios= FirebaseDatabase.getInstance().getReference("Usuarios");

            firebaseAuth = FirebaseAuth.getInstance();
            TextEmail = (EditText) findViewById(R.id.TxtEmail);
            TextPassword = (EditText) findViewById(R.id.TxtPassword);
            TextNombre = (EditText) findViewById(R.id.TxtNombreComp);
            TextNumero = (EditText) findViewById(R.id.TxtNumeroCelular);


            btnRegistrar = (Button) findViewById(R.id.botonRegistrar);
            progressDialog = new ProgressDialog(this);
            btnAtras = (Button) findViewById(R.id.botonAtras);

            btnRegistrar.setOnClickListener(this);
            btnAtras.setOnClickListener(this);
        }

        private void registrarUsuario(){
            String email = TextEmail.getText().toString().trim();
            String password  = TextPassword.getText().toString().trim();
            String nombre = TextNombre.getText().toString().trim();
            String numero = TextNumero.getText().toString().trim();


            if(TextUtils.isEmpty(email)){
                Toast.makeText(this,"Se debe ingresar un email",Toast.LENGTH_LONG).show();
                return;
            }

            if(TextUtils.isEmpty(password)){
                Toast.makeText(this,"Falta ingresar la contraseña",Toast.LENGTH_LONG).show();
                return;
            }

            if(TextUtils.isEmpty(nombre)){
                Toast.makeText(this,"Falta ingresar el nombre",Toast.LENGTH_LONG).show();
                return;
            }

            if(TextUtils.isEmpty(numero)){
                Toast.makeText(this,"Falta ingresar el numero ",Toast.LENGTH_LONG).show();
                return;
            }



            if(numero.length()<10||numero.length()>10){
                Toast.makeText(this,"Dato No valido",Toast.LENGTH_LONG).show();
                return;
            }

            if(!TextUtils.isEmpty(email)||!TextUtils.isEmpty(password)||!TextUtils.isEmpty(nombre)||!TextUtils.isEmpty(numero)){
                String id = BDUsuarios.push().getKey();
                Intent intencionId = new  Intent(this,codigoregistroActivity.class);
                intencionId.putExtra("identificacion1",id);
                Usuarios usuario = new Usuarios(email,password,nombre,numero,"No Registrado");
                BDUsuarios.child("Informacion").child(id).setValue(usuario);
                startActivity(intencionId);
            }else{
                Toast.makeText(this,"Falta algun Dato / Error ", Toast.LENGTH_LONG).show();
            }


            progressDialog.setMessage("Realizando registro en linea...");
            progressDialog.show();

            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            //checking if success
                            if(task.isSuccessful()){

                                Toast.makeText(Registro_Activity.this,"Se ha registrado el usuario con el email: "+ TextEmail.getText(),Toast.LENGTH_LONG).show();


                                Intent intent2 = new Intent(getApplicationContext(),SaludoActivity.class);
                                startActivity(intent2);

                            }else{

                                if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                    Toast.makeText(Registro_Activity.this,"El Usuario ya existe ",Toast.LENGTH_LONG).show();
                                    Intent intent3 = new Intent(getApplicationContext(),SaludoActivity.class);
                                    startActivity(intent3);
                                }else{
                                    Toast.makeText(Registro_Activity.this,"No se pudo registrar el usuario ",Toast.LENGTH_LONG).show();
                                }

                            }
                            progressDialog.dismiss();
                        }
                    });


        }

    public void Devolver(){
        Intent intencionAtras = new Intent(getApplication(),MainActivity.class);
        startActivity(intencionAtras);
    }

    @Override
    public void onClick(View view) {

        switch(view.getId()){
            case R.id.botonRegistrar:
                registrarUsuario();
                break;

            case R.id.botonAtras:
                Devolver();
                break;
        }


    }

}

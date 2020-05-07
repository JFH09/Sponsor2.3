package com.example.sponsor2;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
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

import java.util.Calendar;

public class Registro_Activity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "Registro_Activity";
    private  EditText TextEmail;
    private  EditText TextPassword;
    private EditText TextNombre;
    private EditText TextNumero;
    private EditText TextCodigoRegistro;
    private EditText TextApellido;
    private EditText TextIdentificacion;
    private EditText TextColegio;
    private Button btnRegistrar;
    private  Button btnAtras;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference BDUsuarios;
    private TextView BtnTextFecha;
    private DatePickerDialog.OnDateSetListener  DarFecha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_);
            BDUsuarios= FirebaseDatabase.getInstance().getReference("Usuarios");

            firebaseAuth = FirebaseAuth.getInstance();
            TextEmail = (EditText) findViewById(R.id.TxtEmail);
            TextPassword = (EditText) findViewById(R.id.TxtPassword);
            TextNombre = (EditText) findViewById(R.id.TxtNombre);
            TextNumero = (EditText) findViewById(R.id.TxtNumeroCelular);
            TextApellido = (EditText) findViewById(R.id.TxtApellido);
            TextCodigoRegistro = (EditText) findViewById(R.id.TxtCodigoRegistro);
            TextIdentificacion = (EditText) findViewById(R.id.TxtIdentificacion);
            TextColegio = (EditText) findViewById(R.id.TxtColegio);

            BtnTextFecha = (TextView) findViewById(R.id.TxtVFecha);


            btnRegistrar = (Button) findViewById(R.id.botonRegistrar);
            progressDialog = new ProgressDialog(this);
            btnAtras = (Button) findViewById(R.id.botonAtras);



            btnRegistrar.setOnClickListener(this);
            btnAtras.setOnClickListener(this);

            BtnTextFecha.setOnClickListener(new  View.OnClickListener(){

                public void onClick(View view){
                    Calendar cal= Calendar.getInstance();
                    int año = cal.get(Calendar.YEAR);
                    int mes = cal.get(Calendar.MONTH);
                    int dia = cal.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog dialog = new DatePickerDialog(
                            Registro_Activity.this,
                            android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                            DarFecha,
                            año, mes, dia);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();

                }
            });

            DarFecha = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    BtnTextFecha.setText( i + "/" + i1 +"/"+ i2);
                }
            };
        }

        private void registrarUsuario(){
            String email = TextEmail.getText().toString().trim();
            String password  = TextPassword.getText().toString().trim();
            String nombre = TextNombre.getText().toString().trim();
            String numero = TextNumero.getText().toString().trim();
            String codigoRegistro = TextCodigoRegistro.getText().toString().trim();
            String fecha = BtnTextFecha.getText().toString().trim();
            String apellido = TextApellido.getText().toString().trim();
            String identificacion = TextIdentificacion.getText().toString().trim();
            String colegio = TextColegio.getText().toString().trim();
            boolean verificacion= false;

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

            if(TextUtils.isEmpty(apellido)) {
                Toast.makeText(this, "Falta un Apellido ", Toast.LENGTH_LONG).show();
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

            if(TextUtils.isEmpty(fecha)){
                Toast.makeText(this, "Falta Digitar Fecha ",Toast.LENGTH_LONG).show();
            }


            if(TextUtils.isEmpty(numero)){
                Toast.makeText(this,"Falta ingresar el numero ",Toast.LENGTH_LONG).show();
                return;
            }

            if(TextUtils.isEmpty(codigoRegistro)){

                Toast.makeText(this,"Codigo De Registro No valido",Toast.LENGTH_LONG).show();

            }

            if(TextUtils.isEmpty(colegio)){

                Toast.makeText(this,"Codigo De Registro No valido",Toast.LENGTH_LONG).show();

            }



            if(codigoRegistro.equals("estudiante")){
                verificacion=true;
            }

            if(verificacion) {
                Toast.makeText(this,"Bienvenido "+ nombre,Toast.LENGTH_LONG).show();
                if(!TextUtils.isEmpty(email) || !TextUtils.isEmpty(password) || !TextUtils.isEmpty(nombre) || !TextUtils.isEmpty(apellido) || !TextUtils.isEmpty(numero) || !TextUtils.isEmpty(fecha) || !TextUtils.isEmpty(colegio) ){
                    String id = BDUsuarios.push().getKey();
                    Intent intencionId = new  Intent(this,eligetumateriaActivity.class);
                    intencionId.putExtra("identificacion1",id);
                    Usuarios usuario = new Usuarios(email,password,nombre,apellido,numero,fecha,codigoRegistro,identificacion,colegio,"NULL");
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
                                if (task.isSuccessful()) {

                                    Toast.makeText(Registro_Activity.this, "Se ha registrado el usuario con el email: " + TextEmail.getText(), Toast.LENGTH_LONG).show();


                                    Intent intent2 = new Intent(getApplicationContext(), eligetumateriaActivity.class);
                                    startActivity(intent2);

                                } else {

                                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                        Toast.makeText(Registro_Activity.this, "El Usuario ya existe ", Toast.LENGTH_LONG).show();
                                        Intent intent3 = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent3);
                                    } else {
                                        Toast.makeText(Registro_Activity.this, "No se pudo registrar el usuario ", Toast.LENGTH_LONG).show();

                                    }

                                }
                                progressDialog.dismiss();
                            }
                        });

            }else{
                Toast.makeText(this,"No se puede Realizar El Registro... Se enviara un correo a "+email,Toast.LENGTH_LONG).show();
                Intent intentoNoRegistro = new Intent(getApplication(),SaludoActivity.class);
                startActivity(intentoNoRegistro);
            }
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

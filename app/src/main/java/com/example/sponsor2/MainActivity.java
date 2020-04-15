package com.example.sponsor2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText TextEmail;
    private EditText TextPassword;
    private Button btnRegistrar;
    private Button btnEntrar;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
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
                            Toast.makeText(MainActivity.this,"Se ha Iniciado Sesión el usuario con el email: "+ TextEmail.getText(),Toast.LENGTH_LONG).show();
                            Intent intencion = new Intent(getApplication(),BienvenidoActivity.class);
                            intencion.putExtra(BienvenidoActivity.user, user);
                            startActivity(intencion);

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

package com.example.sponsor2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sponsor2.fragments.InicioFragment;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

//implements GoogleApiClient.OnConnectionFailedListener
public class MainActivity extends AppCompatActivity implements View.OnClickListener,GoogleApiClient.OnConnectionFailedListener  {

    private EditText TextEmail;
    private EditText TextPassword;
    private Button btnRegistrar;
    private Button btnEntrar;
    private Button btnTutor;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference BDUsuario;
    private  Collection informeRef;
    private Button InicioGoogle;
    private ImageView btnImagen;
    private TextView nombreTextView;
    private TextView emailTextView;
    private GoogleApiClient googleApiClient;
    private SignInButton signInButton;
    public static final int SIGN_IN_CODE = 777;
    private FirebaseAuth mAuth;
    private CallbackManager callbackManager;
    boolean VerAdmin = false;
    private ImageButton btnImageAdmin;


    private FirebaseAuth.AuthStateListener firebaseAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        callbackManager = CallbackManager.Factory.create();



        //Google
        GoogleSignInOptions gso= new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient= new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mAuth = FirebaseAuth.getInstance();

        ////Google fin

        firebaseAuth = FirebaseAuth.getInstance();
        BDUsuario= FirebaseDatabase.getInstance().getReference("Usuarios");
        TextEmail = (EditText) findViewById(R.id.TxtEmail);
        TextPassword = (EditText) findViewById(R.id.TxtPassword);

        btnRegistrar = (Button) findViewById(R.id.botonRegistrar);
        btnEntrar = (Button) findViewById(R.id.botonEntrar);
        btnTutor = (Button) findViewById(R.id.botonTutor);
        btnImagen = (ImageView) findViewById(R.id.imageButton);
        btnImageAdmin =(ImageButton) findViewById(R.id.imgbtnAdmin);
        progressDialog = new ProgressDialog(this);




        InicioGoogle =  findViewById(R.id.botonIniciarGoogle);


        InicioGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, SIGN_IN_CODE);
            }
        });
        btnRegistrar.setOnClickListener(this);
        btnEntrar.setOnClickListener(this);
        //InicioGoogle.setOnClickListener(this);
        btnTutor.setOnClickListener(this);
        btnImageAdmin.setOnClickListener(this);

    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if(requestCode==SIGN_IN_CODE){

            GoogleSignInResult result=Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if(result.isSuccess()){
            goMainScreen();
        }else{
            Toast.makeText(MainActivity.this, "Ups, Ocurrio un Error !", Toast.LENGTH_LONG).show();

        }
    }

    private void goMainScreen() {
        Intent intent=new Intent(this, IniciarGoogleActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }




    public void IngresoUsuario(){
        final String email = TextEmail.getText().toString().trim();
        final String password  = TextPassword.getText().toString().trim();

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

                            //Toast.makeText(MainActivity.this, "Bienvenido  "+user, Toast.LENGTH_LONG).show();


                            if(user.equals("admin09")){
                                Toast.makeText(MainActivity.this,"Es Admin!",Toast.LENGTH_LONG).show();
                                Intent intentAdmin = new Intent(getApplication(), AdminActivity.class);
                                startActivity(intentAdmin);

                            }else {
                                Toast.makeText(MainActivity.this, "Bienvenido  "+user, Toast.LENGTH_LONG).show();
                                Intent intent1 = new Intent(getApplication(), eligetumateriaActivity.class);//chatActivity
                               // Bundle envioUser = new Bundle();
                                //envioUser.putString("usuario", user);
                                intent1.putExtra("usuario",user);
                                intent1.putExtra("correo",email);
                                startActivity(intent1);
                            }
                        }else{

                            Toast.makeText(MainActivity.this,"No se pudo Iniciar Sesion ",Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });


    }









    public void AccionRegistrar(){
        Intent intencion2 = new Intent(getApplication(),SaludoActivity.class);
        startActivity(intencion2);
    }

    public  void AccionTutor(){
        Intent intentoTutor = new Intent(getApplication(),SaludotutorActivity.class);
        startActivity(intentoTutor);
    }

    public void onClick(View view) {
        switch(view.getId()){
            case R.id.botonEntrar:
                IngresoUsuario();
                break;

            case R.id.botonRegistrar:
                AccionRegistrar();
                break;

            case R.id.botonTutor:
                AccionTutor();
                break;



        }




    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}

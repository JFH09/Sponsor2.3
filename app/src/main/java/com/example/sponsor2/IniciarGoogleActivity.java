package com.example.sponsor2;


import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResolvingResultCallbacks;
import com.google.android.gms.common.api.ResultCallbacks;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Arrays;

public class IniciarGoogleActivity extends AppCompatActivity  implements View.OnClickListener,GoogleApiClient.OnConnectionFailedListener{


    public static final String Usuario = "names";
    TextView txtUser;
    private Button btnSalir;
    private GoogleApiClient googleApiClient;
    private int SIGN_IN_CODE=777;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_google);

        txtUser = (TextView) findViewById(R.id.ShowU);
        String usuario = getIntent().getStringExtra("names");




        // txtUser.setText("Bienvenido " + usuario + "!");
        btnSalir=(Button) findViewById(R.id.Salirbtn);
        btnSalir.setOnClickListener(this);

        GoogleSignInOptions gso= new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient= new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        //FirebaseAuth.getInstance().signOut();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String name = user.getDisplayName();
            String email = user.getEmail();
            String uid = user.getUid();
            Uri photoUrl = user.getPhotoUrl();
            if(name==null){
                txtUser.setText( "Bienvenido "+usuario + "!");
            }else{
                txtUser.setText( "Bienvenido "+name + "!");
            }


        }
    }

    public void onClick(View view) {
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallbacks<Status>() {
            @Override

            public void onSuccess(@NonNull Status status) {
                if(status.isSuccess()){
                    LoginManager.getInstance().logOut();
                    Intent intencion = new Intent(getApplication(), MainActivity.class);
                    startActivity(intencion);
                    Toast.makeText(getApplicationContext(), "Se ha cerrado sesión", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "No se ha podido cerrar sesión", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Status status) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        OptionalPendingResult<GoogleSignInResult> opr=Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if(opr.isDone()){
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        }else{
            opr.setResultCallback(new ResultCallbacks<GoogleSignInResult>() {

                @Override
                public void onSuccess(@NonNull GoogleSignInResult googleSignInResult) {
                    handleSignInResult(googleSignInResult);
                }

                @Override
                public void onFailure(@NonNull Status status) {

                }
            });
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if(result.isSuccess()){
            GoogleSignInAccount account =result.getSignInAccount();
            txtUser.setText("Bienvenido "+"/n"+account.getDisplayName()+" !");
        }else{

        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }



}

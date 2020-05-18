package com.example.sponsor2;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.GoogleApiAvailabilityCache;

public class CuentaGoogleActivity extends AppCompatActivity implements  GoogleApiClient.OnConnectionFailedListener {

    private ImageView imagenView;
    private TextView txtNombreUsuGoogle;
    private TextView txtEmailUsuGoogle;
    private TextView txtIdUsuGoogle;
    private Button btnCerrarSesion;
    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incio_google);

        imagenView = (ImageView) findViewById(R.id.imagenView);
        txtEmailUsuGoogle = (TextView) findViewById(R.id.txtEmailGoogle);
        txtNombreUsuGoogle = (TextView) findViewById(R.id.txtNombreGoogle);
        txtIdUsuGoogle = (TextView) findViewById(R.id.idtxt);
        btnCerrarSesion= (Button) findViewById(R.id.botonSalir);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

         googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();



    }

    @Override
    protected void onStart() {
        super.onStart();
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient);

        if(opr.isDone()){
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        }else{
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    private void handleSignInResult(GoogleSignInResult result){
        if(result.isSuccess()){
            GoogleSignInAccount account = result.getSignInAccount();

            txtNombreUsuGoogle.setText(account.getDisplayName());
            txtEmailUsuGoogle.setText(account.getEmail());
            txtIdUsuGoogle.setText(account.getId());


            Glide.with(this).load(account.getPhotoUrl()).into(imagenView);

        }else{
            devueltaMain();
        }
    }

    public void devueltaMain(){

        Intent NoSePudo = new Intent(this,IniciarGoogleActivity.class);
        NoSePudo.addFlags(NoSePudo.FLAG_ACTIVITY_CLEAR_TOP | NoSePudo.FLAG_ACTIVITY_CLEAR_TASK | NoSePudo.FLAG_ACTIVITY_NEW_TASK);
        startActivity(NoSePudo);
    }

    public void CerrarSesion(View view){
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if(status.isSuccess()){
                    devueltaMain();
                }else{
                    Toast.makeText(getApplicationContext(), "No se pudo Cerrar Sesion",Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}

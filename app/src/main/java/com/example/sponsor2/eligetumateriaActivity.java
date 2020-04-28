package com.example.sponsor2;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class eligetumateriaActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnmatematicas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eligetumateria);

        btnmatematicas = findViewById(R.id.botonMatematicas);



        btnmatematicas.setOnClickListener(this);
    }

    public void matematicas(){
        Intent matematicas = new Intent(getApplication(),MatematicasActivity.class);
        startActivity(matematicas);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.botonMatematicas:
                matematicas();
                break;

        }
    }
}

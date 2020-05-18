package com.example.sponsor2;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.common.io.Closer;

import java.util.Calendar;

public class EmergenteAgregarActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView BtnTextFecha;
    private Button BtnAgregar;
    private String TomarFecha="NO HAY";
    private DatePickerDialog.OnDateSetListener  DarFecha;

    private static final String EscribirFecha = "fecha";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergente_agregar);

        BtnTextFecha = (TextView) findViewById(R.id.TxtVFecha);
        BtnTextFecha.setTextColor(Color.WHITE);

/*

        BtnTextFecha = (TextView) findViewById(R.id.TxtVFecha);
        String EscribirFecha = getIntent().getStringExtra("fecha");
        BtnTextFecha.setText("Fecha " + EscribirFecha);

        BtnAgregar = (Button) findViewById(R.id.btnAgregar);
*/

/*
        BtnTextFecha.setOnClickListener(new  View.OnClickListener(){

            public void onClick(View view){
                Calendar cal= Calendar.getInstance();
                int año = cal.get(Calendar.YEAR);
                int mes = cal.get(Calendar.MONTH);
                int dia = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        EmergenteAgregarActivity.this,
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
                TomarFecha = (i + "/" + i1 +"/"+ i2);
            }


        };

        */
    }



    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btnAgregar:
                Intent intentoVuelta = new Intent(getApplication(),MatematicasActivity.class);
                startActivity(intentoVuelta);
                break;

        }

    }

}

package com.example.sponsor2;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Crud1Activity extends AppCompatActivity {

    EditText etUsuario, etContrasena, etTelefono, etEmail;
    Button btnConsultar, btnConsultarUsuario, btnAgregar, btnEditar, btnEliminar, btnMatematicas;
    RecyclerView rvUsuarios;
    private Dialog ventanaEmergente, ventanaEmergenteEditar;

    DatabaseReference databaseReference,BDTutorias;

    List<Tutorias> listaTutorias= new ArrayList<>();

    AdaptadorTutorias adaptador;

    String nomUsuario, nomCorreo;
    Bundle tomarUsuario;
    Bundle tomarCorreo;
    Bundle tomarUsuarioDeNuevo;
    private TextView BtnHoraTutoria,BtnTextFecha,BtnTextEmergHora;
    DatePickerDialog.OnDateSetListener  DarFecha;
    private String TomarFecha="NO HAY",FechaTutoria, CambiarHora2="No Hay Hora",TomarHora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud1);

        BtnHoraTutoria = (TextView) findViewById(R.id.TxtHoraTutoria);
        BtnTextFecha = (TextView) findViewById(R.id.TxtVFecha);
        BtnTextFecha.setTextColor(Color.WHITE);


        ventanaEmergente = new Dialog(this);

        ventanaEmergenteEditar = new Dialog(this);

        etUsuario = findViewById(R.id.etUsuario);
        etContrasena = findViewById(R.id.etContrasena);
        etTelefono = findViewById(R.id.etTelefono);
        etEmail = findViewById(R.id.etEmail);
        btnConsultar = findViewById(R.id.btnConsultar);
        btnConsultarUsuario = findViewById(R.id.btnConsultarUsuario);
        btnMatematicas = findViewById(R.id.btnMatematicas);
        btnEditar = findViewById(R.id.btnEditar);
        btnEliminar = findViewById(R.id.btnEliminar);

        btnAgregar = findViewById(R.id.btnAgregar);
        tomarUsuario = getIntent().getExtras();
        nomUsuario = tomarUsuario.getString("usuario");

        tomarCorreo = getIntent().getExtras();
        nomCorreo = tomarCorreo.getString("correo");
        Toast.makeText(this,"Usuario en = "+nomUsuario,Toast.LENGTH_SHORT).show();
        tomarUsuarioDeNuevo = getIntent().getExtras();
        if(nomCorreo==null){

            nomUsuario = tomarUsuarioDeNuevo.getString("TutoriaPara");
            Toast.makeText(this,"Refrescando nomUsuaruo"+nomUsuario,Toast.LENGTH_LONG).show();
        }


        rvUsuarios = findViewById(R.id.rvUsuarios);
        rvUsuarios.setLayoutManager(new GridLayoutManager(this, 1));


        BDTutorias= FirebaseDatabase.getInstance().getReference("Tutorias");
        databaseReference = FirebaseDatabase.getInstance().getReference("Tutorias");

        etContrasena.setVisibility(View.INVISIBLE);
        etTelefono.setVisibility(View.INVISIBLE);
        etEmail.setVisibility(View.INVISIBLE);

        etUsuario.setText(nomUsuario);
        obtenerUsuarios();


        BtnHoraTutoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendario = Calendar.getInstance();
                int hora = calendario.get(Calendar.HOUR_OF_DAY);
                int minutos = calendario.get(Calendar.MINUTE);


                TimePickerDialog timePickerDialog = new TimePickerDialog(Crud1Activity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int horadelDia, int minutodelDia) {
                        BtnHoraTutoria.setText(horadelDia + " : " + minutodelDia);
                        TomarHora = (horadelDia + " : " + minutodelDia);
                        Toast.makeText(Crud1Activity.this,"Hora : "+BtnHoraTutoria.getText().toString().trim(),Toast.LENGTH_LONG).show();
                    }
                }, hora, minutos, false);
                timePickerDialog.show();

            }
        });

        BtnTextFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Calendar cal= Calendar.getInstance();
                int año = cal.get(Calendar.YEAR);
                int mes = cal.get(Calendar.MONTH);
                int dia = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        Crud1Activity.this,
                        android.R.style.Theme_Material_Dialog_MinWidth,
                        DarFecha,
                        año, mes, dia);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.DKGRAY));
                dialog.show();


            }
        });
        DarFecha = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                BtnTextFecha.setText( i + "/" + i1 +"/"+ i2);
                TomarFecha = (i + "/" + i1 +"/"+ i2);
                FechaTutoria = (i + "-"+i1+"-"+i2);
                Toast.makeText(Crud1Activity.this,"Fecha : "+TomarFecha,Toast.LENGTH_LONG).show();
            }
        };

        btnMatematicas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obtenerTutoriasPorMaterias();
            }
        });

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AgregarFechaHoraTutoria(nomCorreo);
            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               editarHora();
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminarUsuario();
            }
        });

        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obtenerUsuarios();
            }
        });

        btnConsultarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obtenerUsuario();
            }
        });

    }

    public void AgregarFechaHoraTutoria(String correo){


        String horaT = BtnHoraTutoria.getText().toString().trim();
        String fechaT = FechaTutoria;
        String idUsuario = "usu1";
        String idUsu = correo;

        Toast.makeText(this, "Correo = "+ idUsuario,Toast.LENGTH_SHORT).show();
        if(!TextUtils.isEmpty(horaT) || !TextUtils.isEmpty(fechaT) ){

            Toast.makeText(this, "Nueva Tutoria el dia :  " + fechaT + "Con Hora "  + horaT,Toast.LENGTH_LONG ).show();





            String id = BDTutorias.push().getKey();

            Intent intencionId = new  Intent(this,Crud1Activity.class);
            intencionId.putExtra("TutoriaPara",nomUsuario);
            Tutorias tutorias = new Tutorias(fechaT,horaT,nomUsuario);
            BDTutorias.child("Tutorias"+nomUsuario).child(nomUsuario+"Matematicas").child("D"+fechaT+"H:"+horaT).setValue(tutorias); //Aqui que las subCarpetas sean por dias u horas
            //.child(dia+hora)

            BDTutorias.child("ListadoTutorias").child("Matematicas").child("D"+fechaT+"H:"+horaT+"Tutor="+nomUsuario).setValue(tutorias);
            startActivity(intencionId);


        }else{
            Toast.makeText(this,"Falta algun Dato / Error ", Toast.LENGTH_LONG).show();
        }





        //   progressDialog.setMessage("Realizando registro en linea...");
        // progressDialog.show();
    }

    public void obtenerTutoriasPorMaterias(){

        //colocar un filtro por materia
        Toast.makeText(Crud1Activity.this,"---Matematicas--- ",Toast.LENGTH_SHORT).show();
        listaTutorias.clear();
        databaseReference.child("ListadoTutorias").child("Matematicas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot objeto : dataSnapshot.getChildren()) {
                    listaTutorias.add(objeto.getValue(Tutorias.class));
                }

                adaptador = new AdaptadorTutorias(Crud1Activity.this, listaTutorias);
                rvUsuarios.setAdapter(adaptador);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        limpiarCampos();
    }

    public void obtenerUsuario() {
        listaTutorias.clear();
        String usuario = etUsuario.getText().toString();

        Query query = databaseReference.child("Tutorias"+usuario).child(usuario+"Matematicas").orderByChild("usuario").equalTo(usuario);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot objeto : dataSnapshot.getChildren()) {
                    listaTutorias.add(objeto.getValue(Tutorias.class));
                }

                adaptador = new AdaptadorTutorias(Crud1Activity.this, listaTutorias);
                rvUsuarios.setAdapter(adaptador);

                limpiarCampos();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void obtenerUsuarios() {
        listaTutorias.clear();
        databaseReference.child("ListadoTutorias").child("Matematicas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot objeto : dataSnapshot.getChildren()) {
                    listaTutorias.add(objeto.getValue(Tutorias.class));
                }

                adaptador = new AdaptadorTutorias(Crud1Activity.this, listaTutorias);
                rvUsuarios.setAdapter(adaptador);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        limpiarCampos();
    }

    public void  editarHora(){



        final Button BtnAgregarEmerge;
        ImageButton BtnCerrar;
        String CambiarHora;

        listaTutorias.clear();
        String hora =TomarHora;

        String fecha = FechaTutoria;


        Query query = databaseReference.child("Tutorias"+nomUsuario).child(nomUsuario+"Matematicas").orderByChild("hora").equalTo(hora);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot objeto : dataSnapshot.getChildren()) {
                    objeto.getRef().removeValue();
                }
                Toast.makeText(Crud1Activity.this, "Se elimino la tutoria", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        limpiarCampos();

        ventanaEmergente.setContentView(R.layout.activity_emergente_agregar);

        BtnTextFecha = (TextView) ventanaEmergente.findViewById(R.id.TxtVFecha);
        BtnTextFecha.setTextColor(Color.WHITE);
        BtnTextEmergHora= (TextView) ventanaEmergente.findViewById(R.id.TxtHoraTutoria);

        BtnAgregarEmerge = ventanaEmergente.findViewById(R.id.botonAgregar);


        BtnCerrar = (ImageButton) ventanaEmergente.findViewById(R.id.btnCerrar);

        BtnAgregarEmerge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String correo = nomCorreo;
                Toast.makeText(Crud1Activity.this,"Agregando...",Toast.LENGTH_LONG).show();
                ModificarUsuario(TomarFecha,CambiarHora2);
                BtnAgregarEmerge.setText("Agregado");
                ventanaEmergente.dismiss();


            }



        });



        BtnTextFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Calendar cal= Calendar.getInstance();
                int año = cal.get(Calendar.YEAR);
                int mes = cal.get(Calendar.MONTH);
                int dia = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        Crud1Activity.this,
                        android.R.style.Theme_Material_Dialog_MinWidth,
                        DarFecha,
                        año, mes, dia);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.DKGRAY));
                dialog.show();


            }
        });
        DarFecha = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                BtnTextFecha.setText( i + "/" + i1 +"/"+ i2);
                TomarFecha = (i + "/" + i1 +"/"+ i2);
                FechaTutoria = (i + "-"+i1+"-"+i2);
                Toast.makeText(Crud1Activity.this,"Fecha : "+TomarFecha,Toast.LENGTH_LONG).show();
            }
        };



        BtnTextEmergHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendario = Calendar.getInstance();
                int hora = calendario.get(Calendar.HOUR_OF_DAY);
                int minutos = calendario.get(Calendar.MINUTE);


                TimePickerDialog timePickerDialog = new TimePickerDialog(Crud1Activity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int horadelDia, int minutodelDia) {
                        BtnTextEmergHora.setText(horadelDia + " : " + minutodelDia);
                        TomarHora= (horadelDia + " : " + minutodelDia);
                        CambiarHora2 = BtnHoraTutoria.getText().toString().trim();
                        Toast.makeText(Crud1Activity.this,"Hora : "+BtnHoraTutoria.getText().toString().trim(),Toast.LENGTH_LONG).show();
                    }
                }, hora, minutos, false);
                timePickerDialog.show();

            }
        });

        BtnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ventanaEmergente.dismiss();

            }
        });







        ventanaEmergente.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ventanaEmergente.show();



    }

    private void ModificarUsuario(String tomarFecha, String cambiarHora2) {

        String nuevaFecha = tomarFecha;
        String nuevaHora = cambiarHora2;

        String horaT = BtnHoraTutoria.getText().toString().trim();
        String fechaT = FechaTutoria;
        String idUsuario = nomUsuario;

        Toast.makeText(this, "Correo = "+ idUsuario,Toast.LENGTH_SHORT).show();
        if(!TextUtils.isEmpty(horaT) || !TextUtils.isEmpty(fechaT) ){

            Toast.makeText(this, "Tutoria Modificada para el dia :  " + fechaT + "Con Hora "  + horaT,Toast.LENGTH_LONG ).show();


            String id = BDTutorias.push().getKey();

            Intent intencionId = new  Intent(this,Crud1Activity.class);
            intencionId.putExtra("TutoriaPara",nomUsuario);
            Tutorias tutorias = new Tutorias(fechaT,horaT,nomUsuario);
            BDTutorias.child("Tutorias"+nomUsuario).child(nomUsuario+"Matematicas").child("D"+fechaT+"H:"+horaT).setValue(tutorias); //Aqui que las subCarpetas sean por dias u horas
            //.child(dia+hora)

            BDTutorias.child("ListadoTutorias").child("Matematicas").child("D"+fechaT+"H:"+horaT+"Tutor="+nomUsuario).setValue(tutorias);
            startActivity(intencionId);


        }else{
            Toast.makeText(this,"Falta algun Dato / Error ", Toast.LENGTH_LONG).show();
        }

    }


    public void eliminarUsuario() {
        listaTutorias.clear();
        Toast.makeText(this,"TomarHora es = " + TomarHora,Toast.LENGTH_SHORT).show();
        String hora = TomarHora;

        Query query = BDTutorias.child("Tutorias"+nomUsuario).child(nomUsuario+"Matematicas").orderByChild("hora").equalTo(hora);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot objeto : dataSnapshot.getChildren()) {
                    objeto.getRef().removeValue();
                }
                Toast.makeText(Crud1Activity.this, "Se elimino la tutoria", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        limpiarCampos();
    }



    public void limpiarCampos() {
        etUsuario.setText("");
        BtnHoraTutoria.setText("");
        BtnTextFecha.setText("");
        etEmail.setText("");
    }

}

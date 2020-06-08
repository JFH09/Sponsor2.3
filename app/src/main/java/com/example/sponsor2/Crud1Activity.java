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
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ScrollView;
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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Crud1Activity extends AppCompatActivity{

    private String indicadorMateria;

    EditText etUsuario, etContrasena, etTelefono, etEmail;
    TextView TxtBusquedaUsuario;
    Button btnConsultar, btnConsultarUsuario, btnAgregar, btnEditar, btnEliminar, btnMateria;
    RecyclerView rvUsuarios;
    HorizontalScrollView scrollView;
    private   Dialog ventanaEmergente;

    DatabaseReference databaseReference,BDTutorias;

    List<Tutorias> listaTutorias= new ArrayList<>();

    AdaptadorTutorias adaptador;

    String nomUsuario, nomCorreo;
    Bundle tomarUsuario;
    Bundle tomarCorreo;
    Bundle tomarUsuarioDeNuevo;
    Bundle tomarMateria;
    private TextView BtnHoraTutoria,BtnTextFecha,BtnTextEmergHora,BtnTextEmergeEditarHora, BtnTextEmergeEditarFecha,BtnTextEmergeAgregarFecha, BtnTextEmergeAgregarHora;
    DatePickerDialog.OnDateSetListener  DarFecha1,DarFecha2;
    private String TomarFecha="NO HAY",FechaTutoria, CambiarHora2="No Hay Hora",TomarHora,FechaTutoriaAEditar,HoraTutoriaAEditar,TomarHoraEditar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud1);

        BtnHoraTutoria = (TextView) findViewById(R.id.TxtHoraTutoria);
        BtnTextFecha = (TextView) findViewById(R.id.TxtVFecha);
       // BtnTextFecha.setTextColor(Color.WHITE);

        ventanaEmergente = new Dialog(this);


        scrollView = (HorizontalScrollView)findViewById(R.id.scrollView2);

        etUsuario = findViewById(R.id.etUsuario);
        etContrasena = findViewById(R.id.etContrasena);
        etTelefono = findViewById(R.id.etTelefono);
        etEmail = findViewById(R.id.etEmail);
        btnConsultar = findViewById(R.id.btnConsultar);
        btnConsultarUsuario = findViewById(R.id.btnConsultarUsuario);
        btnMateria = findViewById(R.id.btnMateria);
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

        tomarMateria = getIntent().getExtras();
        indicadorMateria = tomarMateria.getString("indiMateria");
        Toast.makeText(this, "LA MATERIA ES : "+indicadorMateria,Toast.LENGTH_LONG).show();

        btnMateria.setText(indicadorMateria);

        rvUsuarios = findViewById(R.id.rvUsuarios);
        rvUsuarios.setLayoutManager(new GridLayoutManager(this, 1));


        BDTutorias= FirebaseDatabase.getInstance().getReference("Tutorias");
        databaseReference = FirebaseDatabase.getInstance().getReference("Tutorias");

        etContrasena.setVisibility(View.INVISIBLE);
        etTelefono.setVisibility(View.INVISIBLE);
        etEmail.setVisibility(View.INVISIBLE);
        etUsuario.setVisibility(View.INVISIBLE);
        BtnHoraTutoria.setVisibility(View.INVISIBLE);
        BtnTextFecha.setVisibility(View.INVISIBLE);

        etUsuario.setText(nomUsuario);

       // colorear();
        if(indicadorMateria.equals("Matematicas")) {
            scrollView.setBackgroundResource(R.drawable.botonrematematicas);

        }

        if(indicadorMateria.equals("Biologia")){
            scrollView.setBackgroundResource(R.drawable.botonrebiologia);

        }
        if(indicadorMateria.equals("Español")){
            scrollView.setBackgroundResource(R.drawable.botonespanol);

        }
        if(indicadorMateria.equals("Sociales")){
            scrollView.setBackgroundResource(R.drawable.botonresociales);

        }
        if(indicadorMateria.equals("Fisica")){
            scrollView.setBackgroundResource(R.drawable.botonrefisica);

        }
        if(indicadorMateria.equals("Quimica")){

            scrollView.setBackgroundResource(R.drawable.botonreedufisica);
        }
        if(indicadorMateria.equals("Ingles")){
            scrollView.setBackgroundResource(R.drawable.botonreingles);

        }
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
                        DarFecha1,
                        año, mes, dia);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.DKGRAY));
                dialog.show();


            }
        });
        DarFecha1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                BtnTextFecha.setText( i + "/" + i1 +"/"+ i2);
                TomarFecha = (i + "/" + i1 +"/"+ i2);
                FechaTutoria = (i + "-"+i1+"-"+i2);
                Toast.makeText(Crud1Activity.this,"Fecha : "+TomarFecha,Toast.LENGTH_LONG).show();
            }
        };

        btnMateria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obtenerTutoriasPorMaterias();
            }
        });

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listaTutorias.clear();
                AgregarTutoriaEmergente();

            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listaTutorias.clear();
               editarHora();
               obtenerUsuarios();
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listaTutorias.clear();
                EliminarTutoria();
            }
        });



        btnConsultarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listaTutorias.clear();
                BusquedaPorUsuario();
            }
        });

    }

    public void AgregarTutoriaEmergente(){

        final Button BtnEditarEmerge,BtnAgregarEmerge;
        ImageButton BtnCerrar;
        listaTutorias.clear();
        ventanaEmergente.setContentView(R.layout.activity_emergente_agregar);

        BtnTextFecha = (TextView) ventanaEmergente.findViewById(R.id.TxtVFecha);
        BtnTextFecha.setTextColor(Color.WHITE);
        BtnTextEmergHora= (TextView) ventanaEmergente.findViewById(R.id.TxtHoraTutoria);

        BtnTextEmergeEditarHora = (TextView) ventanaEmergente.findViewById(R.id.TxtHoraEditar);

        BtnTextEmergeEditarFecha= (TextView) ventanaEmergente.findViewById(R.id.TxtFechaEditar);

        BtnTextEmergeAgregarFecha = (TextView) ventanaEmergente.findViewById(R.id.TxtVFecha2);
        BtnTextEmergeAgregarHora  =(TextView ) ventanaEmergente.findViewById(R.id.TxtHoraTutoria2);

        TxtBusquedaUsuario = (TextView) ventanaEmergente.findViewById(R.id.etUsuario);

        BtnEditarEmerge = ventanaEmergente.findViewById(R.id.botonEditar);
        BtnAgregarEmerge = ventanaEmergente.findViewById(R.id.botonAgregar);


        TxtBusquedaUsuario.setVisibility(View.INVISIBLE);
        BtnTextEmergeEditarHora.setVisibility(View.INVISIBLE);
        BtnTextEmergeEditarFecha.setVisibility(View.INVISIBLE);
        BtnTextFecha.setVisibility(View.INVISIBLE);
        BtnTextEmergHora.setVisibility(View.INVISIBLE);
        BtnEditarEmerge.setVisibility(View.INVISIBLE);


        BtnCerrar = (ImageButton) ventanaEmergente.findViewById(R.id.btnCerrar);

        BtnAgregarEmerge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listaTutorias.clear();
                String correo = nomCorreo;
                Toast.makeText(Crud1Activity.this,"Agregando...",Toast.LENGTH_LONG).show();
                AgregarFechaHoraTutoria(FechaTutoria,TomarHora);
                BtnAgregarEmerge.setText("Agregado");
                ventanaEmergente.dismiss();


            }



        });



        BtnTextEmergeAgregarFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Calendar cal= Calendar.getInstance();
                int año = cal.get(Calendar.YEAR);
                int mes = cal.get(Calendar.MONTH);
                int dia = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        Crud1Activity.this,
                        android.R.style.Theme_Material_Dialog_MinWidth,
                        DarFecha1,
                        año, mes, dia);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.DKGRAY));
                dialog.show();


            }
        });
        DarFecha1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                BtnTextEmergeAgregarFecha.setText( i + "/" + i1 +"/"+ i2);
                TomarFecha = (i + "/" + i1 +"/"+ i2);
                FechaTutoria = (i + "-"+i1+"-"+i2);
                Toast.makeText(Crud1Activity.this,"Fecha : "+TomarFecha,Toast.LENGTH_LONG).show();
            }
        };



        BtnTextEmergeAgregarHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendario = Calendar.getInstance();
                int hora = calendario.get(Calendar.HOUR_OF_DAY);
                int minutos = calendario.get(Calendar.MINUTE);


                TimePickerDialog timePickerDialog = new TimePickerDialog(Crud1Activity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int horadelDia, int minutodelDia) {
                        BtnTextEmergeAgregarHora.setText(horadelDia + " : " + minutodelDia);
                        TomarHora= (horadelDia + " : " + minutodelDia);
                        CambiarHora2 = BtnTextEmergeAgregarHora.getText().toString().trim();
                        Toast.makeText(Crud1Activity.this,"Hora : "+BtnTextEmergeAgregarHora.getText().toString().trim(),Toast.LENGTH_LONG).show();

                    }
                }, hora, minutos, false);
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.DKGRAY));
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
    public void AgregarFechaHoraTutoria(String TomarFecha,String TomarHora){


        String horaT =TomarHora;
        String fechaT = TomarFecha;
        //String idUsuario = "usu1";
        //String idUsu = correo;
        listaTutorias.clear();
        //Toast.makeText(this, "Correo = "+ idUsuario,Toast.LENGTH_SHORT).show();
        if(!TextUtils.isEmpty(horaT) || !TextUtils.isEmpty(fechaT) ){

            Toast.makeText(this, "Nueva Tutoria el dia :  " + fechaT + "Con Hora "  + horaT,Toast.LENGTH_LONG ).show();





            String id = BDTutorias.push().getKey();


            Tutorias tutorias = new Tutorias(fechaT,horaT,nomUsuario,indicadorMateria);
            BDTutorias.child("Tutorias"+nomUsuario).child(nomUsuario+indicadorMateria).child("D"+fechaT+"H:"+horaT).setValue(tutorias); //Aqui que las subCarpetas sean por dias u horas


            BDTutorias.child("ListadoTutorias").child(indicadorMateria).child("D"+fechaT+"H:"+horaT+"Tutor="+nomUsuario).setValue(tutorias);

            BDTutorias.child("Tutorias"+nomUsuario).child("ListadoTutorias").child("D"+fechaT+"H:"+horaT).setValue(tutorias);


        }else{
            Toast.makeText(this,"Falta algun Dato / Error ", Toast.LENGTH_LONG).show();
        }





    }



    public void obtenerTutoriasPorMaterias(){


        Toast.makeText(Crud1Activity.this,indicadorMateria,Toast.LENGTH_SHORT).show();
        listaTutorias.clear();
        databaseReference.child("ListadoTutorias").child(indicadorMateria).addValueEventListener(new ValueEventListener() {
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

    public void BusquedaPorUsuario(){
        listaTutorias.clear();

        final Button BtnEditarEmerge,BtnAgregarEmerge;
        ImageButton BtnCerrar;
        final String BuscarUsuario;

        ventanaEmergente.setContentView(R.layout.activity_emergente_agregar);

        BtnTextFecha = (TextView) ventanaEmergente.findViewById(R.id.TxtVFecha);
        BtnTextFecha.setTextColor(Color.WHITE);
        BtnTextEmergHora= (TextView) ventanaEmergente.findViewById(R.id.TxtHoraTutoria);

        BtnTextEmergeEditarHora = (TextView) ventanaEmergente.findViewById(R.id.TxtHoraEditar);

        BtnTextEmergeEditarFecha= (TextView) ventanaEmergente.findViewById(R.id.TxtFechaEditar);

        BtnTextEmergeAgregarFecha = (TextView) ventanaEmergente.findViewById(R.id.TxtVFecha2);
        BtnTextEmergeAgregarHora  =(TextView ) ventanaEmergente.findViewById(R.id.TxtHoraTutoria2);



        BtnEditarEmerge = ventanaEmergente.findViewById(R.id.botonEditar);
        BtnAgregarEmerge = ventanaEmergente.findViewById(R.id.botonAgregar);



        BtnTextEmergeEditarHora.setVisibility(View.INVISIBLE);
        BtnTextEmergeEditarFecha.setVisibility(View.INVISIBLE);
        BtnTextFecha.setVisibility(View.INVISIBLE);
        BtnTextEmergHora.setVisibility(View.INVISIBLE);
        BtnEditarEmerge.setVisibility(View.INVISIBLE);
        BtnTextEmergeAgregarHora.setVisibility(View.INVISIBLE);
        BtnTextEmergeAgregarFecha.setVisibility(View.INVISIBLE);

        BtnAgregarEmerge.setText("Buscar Tutorias Por el Tutor");

        TxtBusquedaUsuario = (TextView) ventanaEmergente.findViewById(R.id.etUsuario);
        BuscarUsuario = TxtBusquedaUsuario.getText().toString().trim();

        BtnCerrar = (ImageButton) ventanaEmergente.findViewById(R.id.btnCerrar);

        BtnAgregarEmerge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String correo = nomCorreo;
                Toast.makeText(Crud1Activity.this,"Buscando...",Toast.LENGTH_LONG).show();
                obtenerUsuario(BuscarUsuario);
                BtnAgregarEmerge.setText("Agregado");
                ventanaEmergente.dismiss();


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

    public void obtenerUsuario(String BuscarUsuario) {
        listaTutorias.clear();
        String usuario = TxtBusquedaUsuario.getText().toString().trim();

        Query query = databaseReference.child("Tutorias"+usuario).child(usuario+indicadorMateria).orderByChild("usuario").equalTo(usuario);
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
        databaseReference.child("ListadoTutorias").child(indicadorMateria).addValueEventListener(new ValueEventListener() {
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

        listaTutorias.clear();

        final Button BtnEditarEmerge,BtnAgregarEmerge;
        ImageButton BtnCerrar;
        String CambiarHora;



        ventanaEmergente.setContentView(R.layout.activity_emergente_agregar);

        BtnTextFecha = (TextView) ventanaEmergente.findViewById(R.id.TxtVFecha);
        BtnTextFecha.setTextColor(Color.WHITE);
        BtnTextEmergHora= (TextView) ventanaEmergente.findViewById(R.id.TxtHoraTutoria);

        BtnTextEmergeEditarHora = (TextView) ventanaEmergente.findViewById(R.id.TxtHoraEditar);

        BtnTextEmergeEditarFecha= (TextView) ventanaEmergente.findViewById(R.id.TxtFechaEditar);


        BtnTextEmergeAgregarFecha = (TextView) ventanaEmergente.findViewById(R.id.TxtVFecha2);
        BtnTextEmergeAgregarHora  =(TextView ) ventanaEmergente.findViewById(R.id.TxtHoraTutoria2);

        TxtBusquedaUsuario = (TextView) ventanaEmergente.findViewById(R.id.etUsuario);

        BtnEditarEmerge = ventanaEmergente.findViewById(R.id.botonEditar);
        BtnAgregarEmerge = ventanaEmergente.findViewById(R.id.botonAgregar);



        BtnCerrar = (ImageButton) ventanaEmergente.findViewById(R.id.btnCerrar);

        TxtBusquedaUsuario.setVisibility(View.INVISIBLE);
        BtnTextEmergeAgregarFecha.setVisibility(View.INVISIBLE);
        BtnTextEmergeAgregarHora.setVisibility(View.INVISIBLE);
        BtnAgregarEmerge.setVisibility(View.INVISIBLE);


        BtnEditarEmerge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listaTutorias.clear();
                String correo = nomCorreo;
                Toast.makeText(Crud1Activity.this,"Editando...",Toast.LENGTH_LONG).show();
                EliminarHoraAnterior(FechaTutoriaAEditar,HoraTutoriaAEditar);
                BtnEditarEmerge.setText("Editando...");
                listaTutorias.clear();
                ModificarUsuario(FechaTutoria,TomarHora,FechaTutoriaAEditar,HoraTutoriaAEditar);
                listaTutorias.clear();
                ventanaEmergente.dismiss();
                obtenerTutoriasPorMaterias();

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
                        DarFecha1,
                        año, mes, dia);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.DKGRAY));
                dialog.show();


            }
        });
        DarFecha1 = new DatePickerDialog.OnDateSetListener() {
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
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.DKGRAY));
                timePickerDialog.show();

            }
        });

        BtnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ventanaEmergente.dismiss();

            }
        });


        BtnTextEmergeEditarFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Calendar cal= Calendar.getInstance();
                int año = cal.get(Calendar.YEAR);
                int mes = cal.get(Calendar.MONTH);
                int dia = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        Crud1Activity.this,
                        android.R.style.Theme_Material_Dialog_MinWidth,
                        DarFecha2,
                        año, mes, dia);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.DKGRAY));
                dialog.show();


            }
        });
        DarFecha2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                BtnTextEmergeEditarFecha.setText( i + "/" + i1 +"/"+ i2);
                //TomarFecha = (i + "/" + i1 +"/"+ i2);
                FechaTutoria = (i + "-"+i1+"-"+i2);
                FechaTutoriaAEditar=(i + "-"+i1+"-"+i2);
                Toast.makeText(Crud1Activity.this,"Fecha : "+FechaTutoria,Toast.LENGTH_LONG).show();
            }
        };



        BtnTextEmergeEditarHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendario = Calendar.getInstance();
                int hora = calendario.get(Calendar.HOUR_OF_DAY);
                int minutos = calendario.get(Calendar.MINUTE);


                TimePickerDialog timePickerDialog = new TimePickerDialog(Crud1Activity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int horadelDia, int minutodelDia) {
                        BtnTextEmergeEditarHora.setText(horadelDia + " : " + minutodelDia);
                        //TomarHora= (horadelDia + " : " + minutodelDia);
                        TomarHoraEditar = (horadelDia + " : " + minutodelDia);
                        HoraTutoriaAEditar=  BtnTextEmergeEditarHora.getText().toString().trim();


                        Toast.makeText(Crud1Activity.this,"Hora : "+ BtnTextEmergeEditarHora.getText().toString().trim(),Toast.LENGTH_LONG).show();

                    }
                }, hora, minutos, false);
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.DKGRAY));
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

    private void EliminarTutoria(){

             listaTutorias.clear();
            final Button BtnEditarEmerge,BtnAgregarEmerge;
            ImageButton BtnCerrar;

            ventanaEmergente.setContentView(R.layout.activity_emergente_agregar);

            BtnTextFecha = (TextView) ventanaEmergente.findViewById(R.id.TxtVFecha);
            BtnTextFecha.setTextColor(Color.WHITE);
            BtnTextEmergHora= (TextView) ventanaEmergente.findViewById(R.id.TxtHoraTutoria);

            BtnTextEmergeEditarHora = (TextView) ventanaEmergente.findViewById(R.id.TxtHoraEditar);

            BtnTextEmergeEditarFecha= (TextView) ventanaEmergente.findViewById(R.id.TxtFechaEditar);

            BtnTextEmergeAgregarFecha = (TextView) ventanaEmergente.findViewById(R.id.TxtVFecha2);
            BtnTextEmergeAgregarHora  =(TextView ) ventanaEmergente.findViewById(R.id.TxtHoraTutoria2);

            TxtBusquedaUsuario = (TextView) ventanaEmergente.findViewById(R.id.etUsuario);

            BtnEditarEmerge = ventanaEmergente.findViewById(R.id.botonEditar);
            BtnAgregarEmerge = ventanaEmergente.findViewById(R.id.botonAgregar);

            BtnAgregarEmerge.setText("-Eliminar Tutoria-");

            TxtBusquedaUsuario.setVisibility(View.INVISIBLE);
            BtnTextEmergeEditarHora.setVisibility(View.INVISIBLE);
            BtnTextEmergeEditarHora.setVisibility(View.INVISIBLE);
            BtnTextEmergeEditarFecha.setVisibility(View.INVISIBLE);
            BtnTextFecha.setVisibility(View.INVISIBLE);
            BtnTextEmergHora.setVisibility(View.INVISIBLE);
            BtnEditarEmerge.setVisibility(View.INVISIBLE);


            BtnCerrar = (ImageButton) ventanaEmergente.findViewById(R.id.btnCerrar);

            BtnAgregarEmerge.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String correo = nomCorreo;
                    Toast.makeText(Crud1Activity.this,"Agregando...",Toast.LENGTH_LONG).show();
                    BtnAgregarEmerge.setText("Eliminando");
                    eliminarTutoria(FechaTutoria,TomarHora);
                    BtnAgregarEmerge.setText("Eliminado");
                    ventanaEmergente.dismiss();


                }



            });



            BtnTextEmergeAgregarFecha.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    Calendar cal= Calendar.getInstance();
                    int año = cal.get(Calendar.YEAR);
                    int mes = cal.get(Calendar.MONTH);
                    int dia = cal.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog dialog = new DatePickerDialog(
                            Crud1Activity.this,
                            android.R.style.Theme_Material_Dialog_MinWidth,
                            DarFecha1,
                            año, mes, dia);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.DKGRAY));
                    dialog.show();


                }
            });
            DarFecha1 = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    BtnTextEmergeAgregarFecha.setText( i + "/" + i1 +"/"+ i2);
                    TomarFecha = (i + "/" + i1 +"/"+ i2);
                    FechaTutoria = (i + "-"+i1+"-"+i2);
                    Toast.makeText(Crud1Activity.this,"Fecha : "+TomarFecha,Toast.LENGTH_LONG).show();
                }
            };



            BtnTextEmergeAgregarHora.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Calendar calendario = Calendar.getInstance();
                    int hora = calendario.get(Calendar.HOUR_OF_DAY);
                    int minutos = calendario.get(Calendar.MINUTE);


                    TimePickerDialog timePickerDialog = new TimePickerDialog(Crud1Activity.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int horadelDia, int minutodelDia) {
                            BtnTextEmergeAgregarHora.setText(horadelDia + " : " + minutodelDia);
                            TomarHora= (horadelDia + " : " + minutodelDia);
                            CambiarHora2 = BtnTextEmergeAgregarHora.getText().toString().trim();
                            Toast.makeText(Crud1Activity.this,"Hora : "+BtnTextEmergeAgregarHora.getText().toString().trim(),Toast.LENGTH_LONG).show();

                        }
                    }, hora, minutos, false);
                    timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.DKGRAY));
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


    private void EliminarHoraAnterior(String TomarFechaAEditar, String TomarHoraAEditar){
        listaTutorias.clear();
        String hora =TomarHoraAEditar;

        String fecha = TomarFechaAEditar;


        Toast.makeText(this,"F:"+TomarFechaAEditar +"H: "+TomarHoraAEditar,Toast.LENGTH_LONG).show();

        Query query = databaseReference.child("Tutorias"+nomUsuario).child(nomUsuario+indicadorMateria).orderByChild("hora").equalTo(hora);
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

        Query query2 = BDTutorias.child("ListadoTutorias").child(indicadorMateria).orderByChild("hora").equalTo(hora);
        query2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot objeto2 : dataSnapshot.getChildren()) {
                    objeto2.getRef().removeValue();
                }
                Toast.makeText(Crud1Activity.this, "Se elimino en listado tutorias", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        limpiarCampos();
    }

    private void ModificarUsuario(String FechaTutoria, String TomarHora, String FechaTutoriaAEditar, String HoraTutoriaAEditar) {

        String nuevaFecha = FechaTutoriaAEditar;
        String nuevaHora = HoraTutoriaAEditar;

        String horaT =  TomarHora;
        String fechaT = FechaTutoria;
        String idUsuario = nomUsuario;
        listaTutorias.clear();

       // Toast.makeText(this, "Correo = "+ idUsuario,Toast.LENGTH_SHORT).show();
        if(!TextUtils.isEmpty(horaT) || !TextUtils.isEmpty(fechaT) ){

            Toast.makeText(this, "Tutoria Modificada para el dia :  " + fechaT + "Con Hora "  + horaT,Toast.LENGTH_LONG ).show();


            String id = BDTutorias.push().getKey();

          //  Intent intencionId = new  Intent(this,Crud1Activity.class);
          //  intencionId.putExtra("TutoriaPara",nomUsuario);
            Tutorias tutorias = new Tutorias(fechaT,horaT,nomUsuario,indicadorMateria);
            BDTutorias.child("Tutorias"+nomUsuario).child(nomUsuario+indicadorMateria).child("D"+fechaT+"H:"+horaT).setValue(tutorias); //Aqui que las subCarpetas sean por dias u horas
            //.child(dia+hora)

            BDTutorias.child("ListadoTutorias").child(indicadorMateria).child("D"+fechaT+"H:"+horaT+"Tutor="+nomUsuario).setValue(tutorias);
            //startActivity(intencionId);


        }else{
            Toast.makeText(this,"Falta algun Dato / Error ", Toast.LENGTH_LONG).show();
        }

        limpiarCampos();

    }


    public void eliminarTutoria(String FechaTutoria, String TomarHora) {
        listaTutorias.clear();
        Toast.makeText(this,"TomarHora es = " + TomarHora,Toast.LENGTH_SHORT).show();
        String hora = TomarHora;

        Query query = BDTutorias.child("Tutorias"+nomUsuario).child(nomUsuario+indicadorMateria).orderByChild("hora").equalTo(hora);
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


        Query query2 = BDTutorias.child("ListadoTutorias").child(indicadorMateria).orderByChild("hora").equalTo(hora);
        query2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot objeto2 : dataSnapshot.getChildren()) {
                    objeto2.getRef().removeValue();
                }
                Toast.makeText(Crud1Activity.this, "Se elimino en listado tutorias", Toast.LENGTH_SHORT).show();
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


    public void colorear(){
        if(indicadorMateria.equals("Matematicas")) {
            scrollView.setBackgroundResource(R.drawable.botonrematematicas);

        }

        if(indicadorMateria.equals("Biologia")){
            scrollView.setBackgroundResource(R.drawable.botonrebiologia);

        }
        if(indicadorMateria.equals("Español")){
            scrollView.setBackgroundResource(R.drawable.botonespanol);

        }
        if(indicadorMateria.equals("Sociales")){
            scrollView.setBackgroundResource(R.drawable.botonresociales);

        }
        if(indicadorMateria.equals("Fisica")){
            scrollView.setBackgroundResource(R.drawable.botonrefisica);

        }
        if(indicadorMateria.equals("Quimica")){

            scrollView.setBackgroundResource(R.drawable.botonreedufisica);
        }
        if(indicadorMateria.equals("Ingles")){
            scrollView.setBackgroundResource(R.drawable.botonreingles);

        }

    }
}

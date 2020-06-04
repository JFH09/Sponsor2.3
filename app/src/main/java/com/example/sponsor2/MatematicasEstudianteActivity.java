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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MatematicasEstudianteActivity extends AppCompatActivity {

    EditText etUsuario, etContrasena, etTelefono, etEmail;
    TextView TxtBusquedaUsuario,BtnHoraTutoria,BtnTextFecha,BtnTextEmergHora,BtnTextEmergeEditarHora,BtnTextEmergeEditarFecha,BtnTextEmergeAgregarFecha,BtnTextEmergeAgregarHora;
    Button btnConsultar, btnConsultarUsuario, btnAgregar, btnEditar, btnEliminar, btnMatematicas;
    Button BtnMatematicas, NuevaTutoria, consultarPorTutor;

    String usuario, correo, FechaTutoria,TomarHora,CambiarHora2,TomarFecha,indicadorMateria = "Matematicas";
    boolean fechaEncontrada = false,horaEncontrada = false;
    boolean confirmFecha, confirmHora;
    boolean[] EncontroTutoria = new boolean[2];
    String nomUsuario, nomCorreo;
    Bundle tomarUsuario;
    Bundle tomarCorreo;
    Bundle tomarUsuarioDeNuevo;

    DatabaseReference databaseReference,BDTutorias,BDHorariosTutorias,BDChats;

    private   Dialog ventanaEmergente;
    DatePickerDialog.OnDateSetListener  DarFecha1,DarFecha2;

    List<Tutorias> listaTutorias= new ArrayList<>();

    AdaptadorTutorias adaptador;

    RecyclerView rvUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matematicas_estudiante);

        ventanaEmergente = new Dialog(this);




        tomarUsuario = getIntent().getExtras();
        nomUsuario = tomarUsuario.getString("usuario");

        Toast.makeText(MatematicasEstudianteActivity.this, "Usuario = "+nomUsuario, Toast.LENGTH_SHORT ).show();

        tomarCorreo = getIntent().getExtras();
        nomCorreo = tomarCorreo.getString("correo");

        tomarUsuarioDeNuevo = getIntent().getExtras();
        if(nomCorreo==null){

            nomUsuario = tomarUsuarioDeNuevo.getString("TutoriaPara");
            Toast.makeText(this,"Refrescando nomUsuaruo"+nomUsuario,Toast.LENGTH_LONG).show();
        }

        usuario= nomUsuario;
        rvUsuarios = findViewById(R.id.rvUsuarios);
        rvUsuarios.setLayoutManager(new GridLayoutManager(this, 1));


        BDTutorias= FirebaseDatabase.getInstance().getReference("Tutorias");
        BDHorariosTutorias= FirebaseDatabase.getInstance().getReference("HorariosTutorias");
        BDChats= FirebaseDatabase.getInstance().getReference("Chats");
        databaseReference = FirebaseDatabase.getInstance().getReference("Tutorias");

        etUsuario = findViewById(R.id.etUsuario);
        etContrasena = findViewById(R.id.etContrasena);
        etTelefono = findViewById(R.id.etTelefono);
        etEmail = findViewById(R.id.etEmail);
        btnConsultar = findViewById(R.id.btnConsultar);
        btnConsultarUsuario = findViewById(R.id.btnConsultarUsuario);
        btnMatematicas = findViewById(R.id.btnMatematicas);
        btnEditar = findViewById(R.id.btnEditar);
        btnEliminar = findViewById(R.id.btnEliminar);

        BtnMatematicas = findViewById(R.id.btnMatematicas);
        NuevaTutoria = findViewById(R.id.btnElegirTutoria);
        consultarPorTutor = findViewById(R.id.btnConsultaPorTutor);



        etContrasena.setVisibility(View.INVISIBLE);
        etTelefono.setVisibility(View.INVISIBLE);
        etEmail.setVisibility(View.INVISIBLE);
        etUsuario.setVisibility(View.INVISIBLE);


        ObtenerTutoriasMateria();


        BtnMatematicas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ObtenerTutoriasMateria();
            }
        });

        NuevaTutoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AgregarAHorario();
            }
        });

    }


    public void ObtenerTutoriasMateria(){

        listaTutorias.clear();
        databaseReference.child("ListadoTutorias").child("Matematicas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot objeto : dataSnapshot.getChildren()) {
                    listaTutorias.add(objeto.getValue(Tutorias.class));
                }

                adaptador = new AdaptadorTutorias(MatematicasEstudianteActivity.this, listaTutorias);
                rvUsuarios.setAdapter(adaptador);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //limpiarCampos();

    }

    public void AgregarAHorario(){

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
                String correo = nomCorreo;
                Toast.makeText(MatematicasEstudianteActivity.this,"Agregando...",Toast.LENGTH_LONG).show();
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
                        MatematicasEstudianteActivity.this,
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
                Toast.makeText(MatematicasEstudianteActivity.this,"Fecha : "+TomarFecha,Toast.LENGTH_LONG).show();
            }
        };



        BtnTextEmergeAgregarHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendario = Calendar.getInstance();
                int hora = calendario.get(Calendar.HOUR_OF_DAY);
                int minutos = calendario.get(Calendar.MINUTE);


                TimePickerDialog timePickerDialog = new TimePickerDialog(MatematicasEstudianteActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int horadelDia, int minutodelDia) {
                        BtnTextEmergeAgregarHora.setText(horadelDia + " : " + minutodelDia);
                        TomarHora= (horadelDia + " : " + minutodelDia);
                        CambiarHora2 = BtnTextEmergeAgregarHora.getText().toString().trim();
                        Toast.makeText(MatematicasEstudianteActivity.this,"Hora : "+BtnTextEmergeAgregarHora.getText().toString().trim(),Toast.LENGTH_LONG).show();

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


        final String horaT =TomarHora;
        String fechaT = TomarFecha;

        //String idUsuario = "usu1";
        //String idUsu = correo;


        //Toast.makeText(this, "Correo = "+ idUsuario,Toast.LENGTH_SHORT).show();
        if(!TextUtils.isEmpty(horaT) || !TextUtils.isEmpty(fechaT) ){

            Toast.makeText(this, "Se Agrego la Tutoria del dia :  " + fechaT + "Con Hora "  + horaT,Toast.LENGTH_LONG ).show();


            Query query = BDTutorias.child("ListadoTutorias").child(indicadorMateria).orderByChild("fecha").equalTo(fechaT);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                DataSnapshot Traer;
               // int pos= email.indexOf("@");
                //String user = email.substring(0,pos);
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot objeto : dataSnapshot.getChildren()) {

                        String tr1 = objeto.getValue().toString();
                        int posI= tr1.indexOf("usuario");
                        int posF = tr1.indexOf("}");
                        String creador = tr1.substring(posI,posF);


                        Toast.makeText(MatematicasEstudianteActivity.this,"Se encontro el usuario"+creador,Toast.LENGTH_LONG).show();

                        if(objeto.exists()){
                            fechaEncontrada= true;
                           // Toast.makeText(MatematicasEstudianteActivity.this,"Se encontro la fecha",Toast.LENGTH_LONG).show();
                            FuncionFechaEncontrada(fechaEncontrada,creador);
                        }
                    }


                }



                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            Query query2 = BDTutorias.child("ListadoTutorias").child(indicadorMateria).orderByChild("hora").equalTo(horaT);
            query2.addListenerForSingleValueEvent(new ValueEventListener() {
                DataSnapshot Traer;
                // int pos= email.indexOf("@");
                //String user = email.substring(0,pos);
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot objeto : dataSnapshot.getChildren()) {

                        String tr1 = objeto.getValue().toString();
                        int posI= tr1.indexOf("usuario");
                        int posF = tr1.indexOf("}");
                        String creador = tr1.substring(posI,posF);


                        Toast.makeText(MatematicasEstudianteActivity.this,"Se encontro el usuario"+creador,Toast.LENGTH_LONG).show();


                        if(objeto.exists()){
                            horaEncontrada= true;
                            //Toast.makeText(MatematicasEstudianteActivity.this,"Se encontro la hora",Toast.LENGTH_LONG).show();
                            FuncionHoraEncontrada(horaEncontrada,creador);
                        }
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });




        }else{
            Toast.makeText(this,"Falta algun Dato / Error ", Toast.LENGTH_LONG).show();
        }





        //   progressDialog.setMessage("Realizando registro en linea...");
        // progressDialog.show();
    }

    public void FuncionFechaEncontrada(Boolean fechaEncontrada, String creador){
        Toast.makeText(MatematicasEstudianteActivity.this,"Fecha en ="+fechaEncontrada,Toast.LENGTH_LONG).show();
        confirmFecha = fechaEncontrada;
        EncontroTutoria[0] = confirmFecha;

    }

    public void FuncionHoraEncontrada(Boolean horaEncontrada, String creador){
        Toast.makeText(MatematicasEstudianteActivity.this,"Hora en ="+horaEncontrada,Toast.LENGTH_LONG).show();
        confirmHora = horaEncontrada;
        EncontroTutoria[1] = confirmHora;

        pruebatutoria(creador);
    }

    public void pruebatutoria(String creador){

        boolean horaTutoria = EncontroTutoria[0];
        boolean fechaTutoria= EncontroTutoria[1];

        String dia = new Date().toString();


        Toast.makeText(MatematicasEstudianteActivity.this,"Ver"+horaTutoria+fechaTutoria,Toast.LENGTH_LONG).show();
        String tomandoCreador = creador+"}";
        //Toast.makeText(MatematicasEstudianteActivity.this,"Creador=")
        //int posI= tomandoCreador.indexOf(8);
        int posF = tomandoCreador.indexOf("}");
        String creadortutoria = tomandoCreador.substring(8,posF);


        Toast.makeText(MatematicasEstudianteActivity.this,"Su Tutor Va a ser = "+creadortutoria,Toast.LENGTH_LONG).show();


        if(horaTutoria==true && fechaTutoria==true){
            Toast.makeText(MatematicasEstudianteActivity.this,"Agregando Tutoria A tu Horario",Toast.LENGTH_LONG).show();
            Intent intencionId = new  Intent(this,MatematicasEstudianteActivity.class);
            intencionId.putExtra("TutoriaPara",nomUsuario);
            Tutorias tutorias = new Tutorias(FechaTutoria,TomarHora,creadortutoria,indicadorMateria);
            BDHorariosTutorias.child("Horario"+nomUsuario).child(nomUsuario+"Matematicas").child("D"+FechaTutoria+"H:"+TomarHora).setValue(tutorias); //Aqui que las subCarpetas sean por dias u horas
            //.child(dia+hora)
            //child("D"+FechaTutoria+"H:"+TomarHora+"Tutor="+nomUsuario)
            MensajeVO mensajeUno = new MensajeVO(nomUsuario,"Conectado - "+nomUsuario);
            BDChats.child("Materias"+nomUsuario).child(indicadorMateria+nomUsuario).child(creadortutoria+nomUsuario).child("Mensajes").child("MensajeDe"+nomUsuario+dia).setValue(mensajeUno);
            startActivity(intencionId);



        }else{
            Toast.makeText(MatematicasEstudianteActivity.this,"Ocurrio un Error intena de nuevo",Toast.LENGTH_LONG).show();
        }





    }




}

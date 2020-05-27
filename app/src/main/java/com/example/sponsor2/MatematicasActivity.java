package com.example.sponsor2;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.app.ProgressDialog;

import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.OnClickAction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sponsor2.fragments.InicioFragment;
import com.getbase.floatingactionbutton.FloatingActionButton;

import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthActionCodeException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Nullable;

public class MatematicasActivity extends AppCompatActivity{

    private String indicadorMateria = "Matematicas";

    private static final String usuario = "usuario";


    private DatabaseReference BDTutorias,BDHorarios;

    FloatingActionMenu actionMenu;
    FloatingActionButton fabAgregar, fabEditar, fabEliminar;
    private Dialog ventanaEmergente, ventanaEmergenteEditar;

    DatePickerDialog.OnDateSetListener  DarFecha;
    private String FechaTutoria;
    private String auxFecha;
    private TextView BtnTextFecha;
    private TextView BtnHoraTutoria;
    private String TomarFecha="NO HAY";
    private ProgressDialog progressDialog;
    private String horas,fecha;

    private String nomUsuario;
    private String nomCorreo;
    private String CorreoUsuario;

    Bundle tomarUsuario;
    Bundle tomarCorreo;

    //EstaClase
   // DatabaseReference databaseReference;

    //List<Tutorias> listaTutorias= new ArrayList<>();

    //AdaptadorTutorias adaptador;

    //RecyclerView rvUsuarios;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matematicas);

     //   rvUsuarios = findViewById(R.id.rvHorario);
       // rvUsuarios.setLayoutManager(new GridLayoutManager(this, 1));

        //databaseReference = FirebaseDatabase.getInstance().getReference("Tutorias");

       // RAdaptadorHorariosTutorias = new AdaptadorRVHorarioTutorias(ObtenerHorarios());
       // recyclerViewHorario.setAdapter(RAdaptadorHorariosTutorias);


        BDTutorias= FirebaseDatabase.getInstance().getReference("Tutorias");
        BDHorarios= FirebaseDatabase.getInstance().getReference("Horarios");
        //obtenerUsuarios();
        tomarUsuario = getIntent().getExtras();
        nomUsuario = tomarUsuario.getString("usuario");

        tomarCorreo = getIntent().getExtras();
        nomCorreo = tomarCorreo.getString("correo");
        CorreoUsuario=nomCorreo;


        Toast.makeText(this,"Correo!!!"+nomCorreo,Toast.LENGTH_LONG).show();




        ventanaEmergente = new Dialog(this);

        ventanaEmergenteEditar = new Dialog(this);



        actionMenu = (FloatingActionMenu) findViewById(R.id.grupoFab);

        actionMenu.setClosedOnTouchOutside(true);






    }
 /*
    public void obtenerUsuarios() {
        listaTutorias.clear();
        databaseReference.child("Tutorias"+nomUsuario).child(nomUsuario+"Matematicas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot objeto : dataSnapshot.getChildren()) {
                    listaTutorias.add(objeto.getValue(Tutorias.class));
                }

                adaptador = new AdaptadorTutorias(this, listaTutorias);
                rvUsuarios.setAdapter(adaptador);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

       // limpiarCampos();
    }
   */
 /*
    public void ObtenerDatosTutorias(){
        horariosTutorias.clear();
        String horariosUsuario = nomUsuario;
        //String fechaT = BtnTextFecha.getText().toString().trim();
        //String horaT = BtnHoraTutoria.getText().toString().trim();

        Query query = BDTutorias.child("Tutorias").child("Tutorias"+horariosUsuario);  //.equalTo(horariosUsuario);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot objeto : dataSnapshot.getChildren()){
                    horariosTutorias.add(objeto.getValue(HorarioTutoriaVO.class));
                }

                RAdaptadorHorariosTutorias = new AdaptadorRVHorarioTutorias(MatematicasActivity.this, horariosTutorias);
                recyclerViewHorario.setAdapter(RAdaptadorHorariosTutorias);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
*/


    public void BtnAgregar(View view){
        //Toast.makeText(this,"Agregar una hora de clase ",Toast.LENGTH_SHORT).show();
        //startActivity(new Intent (MatematicasActivity.this, EmergenteAgregarActivity.class));

         final Button BtnAgregarEmerge;
         ImageButton BtnCerrar;


        ventanaEmergente.setContentView(R.layout.activity_emergente_agregar);

        BtnTextFecha = (TextView) ventanaEmergente.findViewById(R.id.TxtVFecha);
        BtnTextFecha.setTextColor(Color.WHITE);


        BtnAgregarEmerge = ventanaEmergente.findViewById(R.id.botonAgregar);


        BtnCerrar = (ImageButton) ventanaEmergente.findViewById(R.id.btnCerrar);

        BtnAgregarEmerge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String correo = nomCorreo;
                Toast.makeText(MatematicasActivity.this,"Agregando...",Toast.LENGTH_LONG).show();
                AgregarFechaHoraTutoria(correo);
                BtnAgregarEmerge.setText("Agregado");



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
                        MatematicasActivity.this,
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
                Toast.makeText(MatematicasActivity.this,"Fecha : "+TomarFecha,Toast.LENGTH_LONG).show();
            }
        };



       BtnHoraTutoria.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               final Calendar calendario = Calendar.getInstance();
               int hora = calendario.get(Calendar.HOUR_OF_DAY);
               int minutos = calendario.get(Calendar.MINUTE);


               TimePickerDialog timePickerDialog = new TimePickerDialog(MatematicasActivity.this, new TimePickerDialog.OnTimeSetListener() {
                   @Override
                   public void onTimeSet(TimePicker view, int horadelDia, int minutodelDia) {
                       BtnHoraTutoria.setText(horadelDia + " : " + minutodelDia);
                       horas = BtnHoraTutoria.getText().toString().trim();
                       Toast.makeText(MatematicasActivity.this,"Hora : "+BtnHoraTutoria.getText().toString().trim(),Toast.LENGTH_LONG).show();
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


/*
    public List<HorarioTutoriaVO> ObtenerHorarios(){
        final List<HorarioTutoriaVO> horario = new ArrayList<>();

        BDTutorias.child("Tutorias").child("Tutorias"+nomUsuario).child(nomUsuario).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                horario.clear();

                for(DataSnapshot objSnaptshot : dataSnapshot.getChildren()){
                    HorarioTutoriaVO horariosFirebase = objSnaptshot.getValue(HorarioTutoriaVO.class);
                    String f = horariosFirebase.getFecha();
                    String h = horariosFirebase.getHorario();
                    String c = horariosFirebase.getCreador();
                    horario.add(new HorarioTutoriaVO(f,h,c));



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        String fechaT = BtnTextFecha.getText().toString().trim();
        String horaT = BtnHoraTutoria.getText().toString().trim();

        horario.add(new HorarioTutoriaVO(fechaT,horaT,nomUsuario));
        return horario;
    }
*/
    /////////
    public void AgregarFechaHoraTutoria(String correo){


            String horaT = BtnHoraTutoria.getText().toString().trim();
            String fechaT = FechaTutoria;
            String idUsuario = "usu1";
            String idUsu = correo;

            Toast.makeText(this, "Correo = "+ idUsuario,Toast.LENGTH_SHORT).show();
            if(!TextUtils.isEmpty(horaT) || !TextUtils.isEmpty(fechaT) ){

                Toast.makeText(this, "Nueva Tutoria el dia :  " + fechaT + "Con Hora "  + horaT,Toast.LENGTH_LONG ).show();





                String id = BDTutorias.push().getKey();

                Intent intencionId = new  Intent(this,eligetumateriaActivity.class);
                intencionId.putExtra("TutoriaPara",nomUsuario);
                Tutorias tutorias = new Tutorias(fechaT,horaT,nomUsuario);
                BDTutorias.child("Tutorias"+nomUsuario).child(nomUsuario+indicadorMateria).child("D"+fechaT+"H:"+horaT).setValue(tutorias); //Aqui que las subCarpetas sean por dias u horas
                                           //.child(dia+hora)

                BDTutorias.child("ListadoTutorias").child(indicadorMateria).child("D"+fechaT+"H:"+horaT+"Tutor="+nomUsuario).setValue(tutorias);
                startActivity(intencionId);


            }else{
                Toast.makeText(this,"Falta algun Dato / Error ", Toast.LENGTH_LONG).show();
            }





     //   progressDialog.setMessage("Realizando registro en linea...");
       // progressDialog.show();
    }



    public void BtnEditar(View view){

        final Button BtnEditarEmerge;
        ImageButton BtnCerrar;


        ventanaEmergenteEditar.setContentView(R.layout.activity_emergente_editar);

        BtnTextFecha = (TextView) ventanaEmergenteEditar.findViewById(R.id.TxtVFecha);
        BtnTextFecha.setTextColor(Color.WHITE);


        BtnEditarEmerge = ventanaEmergenteEditar.findViewById(R.id.botonEditar);


        BtnCerrar = (ImageButton) ventanaEmergenteEditar.findViewById(R.id.btnCerrar);

        BtnEditarEmerge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String correo = nomCorreo;
                Toast.makeText(MatematicasActivity.this,"Cambiando...",Toast.LENGTH_LONG).show();
                AgregarFechaHoraTutoria(correo);
                BtnEditarEmerge.setText("Editado");

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
                        MatematicasActivity.this,
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
                FechaTutoria= (i+"-"+i1+"-"+"i2");
                Toast.makeText(MatematicasActivity.this,"Fecha : "+TomarFecha,Toast.LENGTH_LONG).show();
            }
        };

        BtnHoraTutoria = (TextView) ventanaEmergente.findViewById(R.id.TxtHoraTutoria);

        BtnHoraTutoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendario = Calendar.getInstance();
                int hora = calendario.get(Calendar.HOUR_OF_DAY);
                int minutos = calendario.get(Calendar.MINUTE);


                TimePickerDialog timePickerDialog = new TimePickerDialog(MatematicasActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int horadelDia, int minutodelDia) {
                        BtnHoraTutoria.setText(horadelDia + " : " + minutodelDia);
                        horas = BtnHoraTutoria.getText().toString().trim();
                        Toast.makeText(MatematicasActivity.this,"Hora : "+BtnHoraTutoria.getText().toString().trim(),Toast.LENGTH_LONG).show();
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
    public void EditarFechaHoraTutoria(String correo){


        String horaT = BtnHoraTutoria.getText().toString().trim();
        String fechaT = BtnTextFecha.getText().toString().trim();
        String idUsuario = "usu1";
        String idUsu = correo;

        Toast.makeText(this, "Correo = "+ idUsuario,Toast.LENGTH_SHORT).show();
        if(!TextUtils.isEmpty(horaT) || !TextUtils.isEmpty(fechaT) ){

            Toast.makeText(this, "Nueva Tutoria el dia :  " + fechaT + "Con Hora "  + horaT,Toast.LENGTH_LONG ).show();
            String id = BDTutorias.push().getKey();

            Intent intencionId = new  Intent(this,MatematicasActivity.class);
            intencionId.putExtra("TutoriaPara",nomUsuario);
            Tutorias tutorias = new Tutorias(fechaT,horaT,nomUsuario);
            BDTutorias.child("Tutorias"+nomUsuario).child(nomUsuario).setValue(tutorias); //Aqui que las subCarpetas sean por dias u horas
            startActivity(intencionId);                             //.child(dia+hora)





        }else{
            Toast.makeText(this,"Falta algun Dato / Error ", Toast.LENGTH_LONG).show();
        }


        //   progressDialog.setMessage("Realizando registro en linea...");
        // progressDialog.show();
    }

    public void BtnEliminar(View view){
        Toast.makeText(MatematicasActivity.this,"Eliminar ",Toast.LENGTH_SHORT).show();
       // ObtenerDatosTutorias();
    }





}

package com.example.sponsor2;


import android.app.DatePickerDialog;
import android.graphics.Bitmap;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class RegistrotutorActivity<ImagenView> extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "RegistrotutorActivity";
    private  EditText TextEmail;
    private  EditText TextPassword;
    private EditText TextNombre;
    private EditText TextNumero;
    private EditText TextCodigoRegistro;
    private EditText TextApellido;
    private EditText TextIdentificacion;
    private EditText TextUniversidad;
    private Button btnRegistrar;
    private  Button btnAtras;
    private Button btnSubir;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference BDUsuarios,BDFotos;
    private StorageReference ReferenciaStorage;
    private TextView BtnTextFecha;
    private DatePickerDialog.OnDateSetListener  DarFecha;
    private StorageReference ReferenciaStorageArchivo;
    private Button btnAdjuntarArchivo;
    private Button btnAdjuntarCarnet;
    private Button btnAdjuntarDocIdentif, btnAdjuntarActaGrado;
    private ProgressDialog RProgressDialog,RProgressDialog2;
    private ImagenView imagenView;
    private String downloadUrl;
    private final int PICK_PHOTO = 1;
    private  ImagenView imagenProducto;
    public String registro,carnet,identif,acta,Imagen3;
    public String N;
    public Uri F;
    private String TomarFecha="NO HAY";
    private Boolean AdjuntarArchivo=false,AdjuntarCarnet=false,AdjuntarDocIdentif=false,AdjuntarActaGrado=false;
    private String nombreArchivo;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrotutor);
        BDUsuarios= FirebaseDatabase.getInstance().getReference("Usuarios");
        BDFotos = FirebaseDatabase.getInstance().getReference().child("Documentos");
        firebaseAuth = FirebaseAuth.getInstance();
        ReferenciaStorage = FirebaseStorage.getInstance().getReference();


        TextEmail = (EditText) findViewById(R.id.TxtEmail);
        TextPassword = (EditText) findViewById(R.id.TxtPassword);
        TextNombre = (EditText) findViewById(R.id.TxtNombre);
        TextNumero = (EditText) findViewById(R.id.TxtNumeroCelular);
        TextApellido = (EditText) findViewById(R.id.TxtApellido);
        TextCodigoRegistro = (EditText) findViewById(R.id.TxtCodigoRegistro);
        TextIdentificacion = (EditText) findViewById(R.id.TxtIdentificacion);
        TextUniversidad = (EditText) findViewById(R.id.TxtUniversidad);


        BtnTextFecha = (TextView) findViewById(R.id.TxtVFecha);

       // btnSubir= (Button) findViewById(R.id.botonSubir);
        btnRegistrar = (Button) findViewById(R.id.botonRegistrar);
        progressDialog = new ProgressDialog(this);
        btnAtras = (Button) findViewById(R.id.botonAtras);


        btnAdjuntarArchivo = (Button) findViewById(R.id.botonArchivo);
        btnAdjuntarCarnet = (Button) findViewById(R.id.botonArchivoCarnetU);
        btnAdjuntarDocIdentif = (Button) findViewById(R.id.botonArchivoDocIdentif);
        btnAdjuntarActaGrado = (Button) findViewById(R.id.botonArchivoActaGrado);


        btnRegistrar.setOnClickListener(this);
        btnAtras.setOnClickListener(this);
        btnAdjuntarArchivo.setOnClickListener(this);
        btnAdjuntarCarnet.setOnClickListener(this);
        btnAdjuntarDocIdentif.setOnClickListener(this);
        btnAdjuntarActaGrado.setOnClickListener(this);


        RProgressDialog = new ProgressDialog(this);
        RProgressDialog2 = new ProgressDialog(this);
        progressDialog = new ProgressDialog(this);

        BtnTextFecha.setOnClickListener(new  View.OnClickListener(){

            public void onClick(View view){
                Calendar cal= Calendar.getInstance();
                int año = cal.get(Calendar.YEAR);
                int mes = cal.get(Calendar.MONTH);
                int dia = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        RegistrotutorActivity.this,
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




    }

        public void SeleccionarArchivo(){

            progressDialog.setMessage("Adjuntando Archivo");
            progressDialog.show();
            Intent intentArchivos2 = new Intent();
            intentArchivos2.setType("*/*");
            intentArchivos2.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intentArchivos2,"Seleccione una Documento"),PICK_PHOTO);
        }




    
        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            RProgressDialog.setMessage("Adjuntando Documento");
            RProgressDialog.show();


            String nombreCarpeta = TextEmail.toString().trim();
            N=nombreCarpeta.toString().trim();
            if(requestCode == PICK_PHOTO && resultCode == RESULT_OK && data != null && data.getData() !=null){

                Uri filePath = data.getData();
                F=filePath;

                try {
                    Bitmap bitmapImagen = MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
                    RProgressDialog2.setMessage("Cargando Documento");
                    RProgressDialog2.show();
                    Toast.makeText(RegistrotutorActivity.this, "Adjuntando Archivo ..." , Toast.LENGTH_LONG).show();
                    Carga(N, F);

                    RProgressDialog2.dismiss();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            RProgressDialog.dismiss();
            progressDialog.dismiss();

        }

     public void Carga(String N, Uri F){


        final String nombreCarpeta2 = TextEmail.getText().toString().trim();
        final Uri filePath = F;
        nombreArchivo=filePath.getLastPathSegment();
         if(filePath!=null) {
             //.getCurrentUser().getUid()
             final StorageReference fotoRef = ReferenciaStorage.child("Documentos").child(nombreCarpeta2).child(filePath.getLastPathSegment());
             fotoRef.putFile(filePath).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                 @Override
                 public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                     if (!task.isSuccessful()) {
                         throw new Exception();
                     }

                     return fotoRef.getDownloadUrl();

                 }


             }).addOnCompleteListener(new OnCompleteListener<Uri>() {


                 @Override
                 public void onComplete(@NonNull Task<Uri> task) {

                     if (task.isSuccessful()) {
                         Uri downloadLink = task.getResult();
                         //Map<String, Object> producto = new HashMap<>();
                         //Imagen3 = downloadLink.toString();
                         if (AdjuntarArchivo == true) {

                             btnAdjuntarArchivo.setText(nombreArchivo);
                             btnAdjuntarArchivo.setTextColor(Color.WHITE);
                             btnAdjuntarArchivo.setBackgroundColor(R.id.botonMatematicas);

                             registro = downloadLink.toString();
                             AdjuntarArchivo = false;

                         }
                         if (AdjuntarCarnet == true) {

                             btnAdjuntarCarnet.setText(nombreArchivo);
                             btnAdjuntarCarnet.setTextColor(Color.WHITE);
                             btnAdjuntarCarnet.setBackgroundColor(R.id.botonMatematicas);

                             carnet = downloadLink.toString();
                             AdjuntarCarnet = false;

                         }

                         if (AdjuntarDocIdentif == true) {
                             btnAdjuntarDocIdentif.setText(nombreArchivo);
                             btnAdjuntarDocIdentif.setTextColor(Color.WHITE);
                             btnAdjuntarDocIdentif.setBackgroundColor(R.id.botonMatematicas);

                             identif = downloadLink.toString();
                             AdjuntarDocIdentif = false;
                         }

                         if (AdjuntarActaGrado == true) {
                             btnAdjuntarActaGrado.setText(nombreArchivo);
                             btnAdjuntarActaGrado.setTextColor(Color.WHITE);
                             btnAdjuntarActaGrado.setBackgroundColor(R.id.botonMatematicas);

                             acta = downloadLink.toString();
                             AdjuntarActaGrado = false;
                         }

                     }

                 }
             });

         }
   /*
         if (AdjuntarArchivo == true) {
             btnAdjuntarArchivo.setText(nombreArchivo);
             btnAdjuntarArchivo.setTextColor(Color.WHITE);
             btnAdjuntarArchivo.setBackgroundColor(R.id.botonMatematicas);

             //registro = downloadLink.toString();
         }

         if (AdjuntarCarnet == true) {
             btnAdjuntarCarnet.setText(nombreArchivo);
             btnAdjuntarCarnet.setTextColor(Color.WHITE);
             btnAdjuntarCarnet.setBackgroundColor(R.id.botonMatematicas);

             //carnet = downloadLink.toString();

         }

         if (AdjuntarDocIdentif == true) {
             btnAdjuntarDocIdentif.setText(nombreArchivo);
             btnAdjuntarDocIdentif.setTextColor(Color.WHITE);
             btnAdjuntarDocIdentif.setBackgroundColor(R.id.botonMatematicas);

             //identif = downloadLink.toString();
         }

         if (AdjuntarActaGrado == true) {
             btnAdjuntarActaGrado.setText(nombreArchivo);
             btnAdjuntarActaGrado.setTextColor(Color.WHITE);
             btnAdjuntarActaGrado.setBackgroundColor(R.id.botonMatematicas);

             //acta = downloadLink.toString();
         }
     */




/*
         if (task.isSuccessful()) {
             Uri downloadLink = task.getResult();
             //Map<String, Object> producto = new HashMap<>();
             Imagen3 = downloadLink.toString();

             //producto.put("nombre:", nombreCarpeta2);
             //producto.put("imagen", downloadLink.toString());

         }

*/
        return;

     }

/*
    public void cargarProductoFirebase(final String nombreCarpeta, Uri filePath) {

        if(filePath!=null){
                                                                            //.getCurrentUser().getUid()
            final StorageReference fotoRef = ReferenciaStorage.child("Fotos").child(nombreCarpeta).child(filePath.getLastPathSegment());
            fotoRef.putFile(filePath).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if(!task.isSuccessful()){
                        throw new Exception();
                    }

                    return fotoRef.getDownloadUrl();
                }


            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful()){
                        Uri downloadLink = task.getResult();
                        Map<String, Object> producto = new HashMap<>();
                        Imagen3 = downloadLink.toString();
                       // registrarUsuario(Imagen3);
                        producto.put("nombre:", nombreCarpeta);
                        producto.put("imagen", downloadLink.toString());
                        BDUsuarios.child("Usuarios").child(nombreCarpeta).child("productos").push().updateChildren(producto).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                //firebaseAuth.getCurrentUser().getUid()
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                progressDialog.dismiss();

                                Toast.makeText(RegistrotutorActivity.this, "Se cargo el producto correctamente.", Toast.LENGTH_SHORT).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(RegistrotutorActivity.this, "Error al cargar el producto" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                }
            });



        }


    }*/
   /////////////////////////////////////////////////////////////////////////////////////////////////
    private void registrarUsuario( String Imagen /*String registro,String identif, String acta, String carnet*/){
        String email = TextEmail.getText().toString().trim();
        final String password  = TextPassword.getText().toString().trim();
        String nombre = TextNombre.getText().toString().trim();
        String numero = TextNumero.getText().toString().trim();
        String DocHistoriaAcademica =registro;
        String Docidentif = identif;
        String Docacta = acta;
        String Docarnet = carnet;
        String codigoRegistro = "NoActivado";
        String fecha = BtnTextFecha.getText().toString().trim();
        String apellido = TextApellido.getText().toString().trim();
        String identificacion = TextIdentificacion.getText().toString().trim();
        String universidad = TextUniversidad.getText().toString().trim();
        Uri filePath = F;
        String nombreCarpeta = N;
        boolean verificacion= false;
        boolean PassValida = true;
        final String nombreCarpeta2 = email+ password;


        //////

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Se debe ingresar un email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Falta ingresar la contraseña",Toast.LENGTH_LONG).show();
            return;
        }

        if(password.length()<8){
            Toast.makeText(this,"Debe ingresar 8 o mas Caracteres",Toast.LENGTH_LONG).show();
            PassValida = false;
            return;
        }

        if(TextUtils.isEmpty(nombre)){
            Toast.makeText(this,"Falta ingresar el nombre",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(apellido)) {
            Toast.makeText(this, "Falta un Apellido ", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(numero)){
            Toast.makeText(this,"Falta ingresar el numero ",Toast.LENGTH_LONG).show();
            return;
        }




        if(numero.length()<10||numero.length()>10){
            Toast.makeText(this,"Dato No valido",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(fecha)){

                Toast.makeText(this, "Falta Digitar Fecha ",Toast.LENGTH_LONG).show();
                return;
        }



        if(TextUtils.isEmpty(identificacion)){
            Toast.makeText(this, "Falta Una Identificacion", Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(DocHistoriaAcademica)){
            Toast.makeText(this, "Falta Adjuntar un Historia ", Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(Docidentif)){
            Toast.makeText(this, "Falta Adjuntar un identificacion ", Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(Docacta)){
            Toast.makeText(this, "Falta Adjuntar un identificacion ", Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(Docarnet)){
            Toast.makeText(this, "Falta Adjuntar un carnet de la U ", Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(universidad)){
            Toast.makeText(this, "Falta Ingresar La universidad ",Toast.LENGTH_LONG).show();
            return;
        }




        if(codigoRegistro.equals("NoActivado")){
            verificacion=true;
        }

        if(verificacion) {

            if(!TextUtils.isEmpty(email) || !TextUtils.isEmpty(password) || !TextUtils.isEmpty(nombre) || !TextUtils.isEmpty(apellido) ||
                    !TextUtils.isEmpty(numero) || !TextUtils.isEmpty(fecha) || !TextUtils.isEmpty(identificacion) ||
                    !TextUtils.isEmpty(universidad)||!TextUtils.isEmpty(DocHistoriaAcademica) ||!TextUtils.isEmpty(Docidentif)
                    ||!TextUtils.isEmpty(Docacta) ||!TextUtils.isEmpty(Docarnet) || PassValida == true){

                Toast.makeText(this, "Bienvenido " + nombre,Toast.LENGTH_LONG ).show();
                String id = BDUsuarios.push().getKey();

                Intent intencionId = new  Intent(this,eligetumateriaActivity.class);
                intencionId.putExtra("identificacion1",id);
                Usuarios usuario = new Usuarios(email,password,nombre,apellido,numero,fecha,codigoRegistro,identificacion,universidad,DocHistoriaAcademica,Docidentif,Docacta,Docarnet);
                BDUsuarios.child("Informacion").child("Tutores").child(id).setValue(usuario);
                startActivity(intencionId);





            }else{
                Toast.makeText(this,"Falta algun Dato / Error ", Toast.LENGTH_LONG).show();
            }


            progressDialog.setMessage("Realizando registro en linea...");
            progressDialog.show();


            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            //checking if success
                            if (task.isSuccessful()) {

                                Toast.makeText(RegistrotutorActivity.this, "Se ha registrado el usuario con el email: " + TextEmail.getText(), Toast.LENGTH_LONG).show();


                                Intent intent2 = new Intent(getApplicationContext(), eligetumateriaActivity.class);
                                startActivity(intent2);

                            } else {

                                if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                    Toast.makeText(RegistrotutorActivity.this, "El Usuario ya existe ", Toast.LENGTH_LONG).show();
                                    Intent intent3 = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent3);
                                } else {
                                    Toast.makeText(RegistrotutorActivity.this, "No se pudo registrar el usuario ", Toast.LENGTH_LONG).show();

                                }

                            }
                            progressDialog.dismiss();
                        }
                    });

        }else{
            Toast.makeText(this,"No se puede Realizar El Registro... Se enviara un correo a "+email,Toast.LENGTH_LONG).show();
            Intent intentoNoRegistro = new Intent(getApplication(),SaludoActivity.class);
            startActivity(intentoNoRegistro);
        }
    }




    public void Devolver(){
        Intent intencionAtras = new Intent(getApplication(),MainActivity.class);
        startActivity(intencionAtras);
    }



    @Override
    public void onClick(View view) {

        switch(view.getId()){
            case R.id.botonRegistrar:
                registrarUsuario(Imagen3 /*registro,identif,acta,carnet*/);
                break;

            case R.id.botonAtras:
                Devolver();
                break;

            case R.id.botonArchivo:
                AdjuntarArchivo = true;
                SeleccionarArchivo();

                break;

            case R.id.botonArchivoCarnetU:
                AdjuntarCarnet = true;
                SeleccionarArchivo();

                break;

            case R.id.botonArchivoDocIdentif:

                AdjuntarDocIdentif=true;
                SeleccionarArchivo();
                break;

            case R.id.botonArchivoActaGrado:

                AdjuntarActaGrado=true;
                SeleccionarArchivo();
                break;
        }


    }




}

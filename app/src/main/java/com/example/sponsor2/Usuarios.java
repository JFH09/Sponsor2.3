package com.example.sponsor2;

public class Usuarios {
    String email, password, nombre, apellido, numero, codigoRegistro, fecha, Imagen3;


    public Usuarios(String email, String password, String nombre, String  apellido, String numero,  String fecha, String codigoRegistro, String Imagen3){
        this.email=email;
        this.password=password;
        this.nombre=nombre;
        this.apellido=apellido;
        this.numero=numero;
        this.fecha=fecha;
        this.codigoRegistro=codigoRegistro;
        this.Imagen3 = Imagen3;


    }

    public String getImagen3() {
        return Imagen3;
    }

    public String getApellido() {
        return apellido;
    }

    public String getFecha() {
        return fecha;
    }

    public String getCodigoRegistro() {
        return codigoRegistro;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNumero() {
        return numero;
    }
}

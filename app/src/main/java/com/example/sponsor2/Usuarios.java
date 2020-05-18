package com.example.sponsor2;

public class Usuarios {
    String email, password, nombre, apellido, numero, codigoRegistro, fecha, identificacion, universidad, DocHistoriaAcademica,Docidentif,Docacta,Docarnet;


    public Usuarios(String email, String password, String nombre, String  apellido, String numero,  String fecha, String codigoRegistro, String identificacion, String universidad, String DocHistoriaAcademica, String Docidentif, String Docacta, String Docarnet){
        this.email=email;
        this.password=password;
        this.nombre=nombre;
        this.apellido=apellido;
        this.numero=numero;
        this.fecha=fecha;
        this.codigoRegistro=codigoRegistro;
        this.identificacion= identificacion;
        this.universidad=universidad;
        this.DocHistoriaAcademica = DocHistoriaAcademica;
        this.Docidentif = Docidentif;
        this.Docacta = Docacta;
        this.Docarnet = Docarnet;
    }

    public String getDocidentif() {
        return Docidentif;
    }

    public String getDocacta() {
        return Docacta;
    }

    public String getDocarnet() {
        return Docarnet;
    }

    public String getDocHistoriaAcademica() {
        return DocHistoriaAcademica;
    }

    public String getUniversidad() {
        return universidad;
    }

    public String getIdentificacion() {
        return identificacion;
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

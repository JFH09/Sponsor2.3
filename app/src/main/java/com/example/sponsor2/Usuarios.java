package com.example.sponsor2;

public class Usuarios {
    String email, password, nombre, numero, codigoRegistro;

    public Usuarios(String email, String password, String nombre, String numero, String codigoRegistro){
        this.email=email;
        this.password=password;
        this.nombre=nombre;
        this.numero=numero;
        this.codigoRegistro=codigoRegistro;
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

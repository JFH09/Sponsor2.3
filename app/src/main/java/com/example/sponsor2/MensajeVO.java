package com.example.sponsor2;

public class MensajeVO {

    String nombre;
    String mensaje;

    public MensajeVO(){

    }

    public MensajeVO(String nombre, String mensaje){
        this.nombre=nombre;
        this.mensaje=mensaje;
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre){
        this.nombre=nombre;
    }

    public String getMensaje(){
        return mensaje;
    }

    public void setMensaje(String mensaje){
        this.mensaje=mensaje;
    }
}

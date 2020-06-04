package com.example.sponsor2;

public class NombreChat {


    String nombreEstudiante;

    public NombreChat(){

    }

    public NombreChat(String nombreEstudiante){
        this.nombreEstudiante=nombreEstudiante;

    }

    public String getNombreEstudiante(){
        return nombreEstudiante;
    }

    public void setNombreEstudiante(String nombreEstudiante){
        this.nombreEstudiante=nombreEstudiante;
    }
}

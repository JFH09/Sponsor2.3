package com.example.sponsor2;

public class Tutorias {

    String fecha, hora, usuario, indicadorMateria;

    public Tutorias(){

    }

    public Tutorias(String fecha, String hora, String usuario, String indicadorMateria){
        this.fecha=fecha;
        this.hora=hora;
        this.usuario=usuario;
        this.indicadorMateria=indicadorMateria;
    }

    public String getIndicadorMateria() {
        return indicadorMateria;
    }

    public void setIndicadorMateria(String indicadorMateria) {
        this.indicadorMateria = indicadorMateria;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getFecha() {
        return fecha;
    }

    public String getHora() {
        return hora;
    }
}

package com.example.sponsor2;

public class HorarioTutoriaVO {

    private String fecha,  horario, creador;

    public HorarioTutoriaVO(String fecha, String horario, String creador){
        this.fecha = fecha;
        this.horario = horario;
        this.creador = creador;
    }

    public String getCreador() {
        return creador;
    }

    public void setCreador(String creador) {
        this.creador = creador;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }


}

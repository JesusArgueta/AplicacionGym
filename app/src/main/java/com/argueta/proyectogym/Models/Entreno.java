package com.argueta.proyectogym.Models;

public class Entreno {

    private String tiempo;
    private String fecha;

    public Entreno(){}

    public Entreno(String tiempo, String fecha) {
        this.tiempo = tiempo;
        this.fecha = fecha;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}

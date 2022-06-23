package com.argueta.proyectogym.Models;

public class DatosImc {

    private String imc;
    private String fecha;

    public DatosImc(){}

    public DatosImc(String imc, String fecha) {
        this.imc = imc;
        this.fecha = fecha;
    }

    public String getImc() {
        return imc;
    }

    public void setImc(String imc) {
        this.imc = imc;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}

package com.argueta.proyectogym.Models;

public class Rutina {

    private String nombre;
    private String descripcion;
    private String pic;
    private String repeticiones;

    public Rutina() {
    }

    public Rutina(String nombre, String descripcion, String pic,String repeticiones) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.pic = pic;
        this.repeticiones=repeticiones;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getRepeticiones() {
        return repeticiones;
    }

    public void setRepeticiones(String repeticiones) {
        this.repeticiones = repeticiones;
    }
}

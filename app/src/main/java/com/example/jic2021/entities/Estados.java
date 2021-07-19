package com.example.jic2021.entities;

import java.io.Serializable;
import java.util.Date;

public class Estados implements Serializable {
    private int id;
    private String identificador;
    private Date fecha;
    private String comentario;
    private String estado;
    private String fechaString;

    //Constructor all
    public Estados(int id, String identificador, Date fecha, String comentario, String estado, String fechaString) {
        this.id = id;
        this.identificador = identificador;
        this.fecha = fecha;
        this.comentario = comentario;
        this.estado = estado;
        this.fechaString = fechaString;
    }

    //Constructor vacio
    public Estados() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFechaString() {
        return fechaString;
    }

    public void setFechaString(String fechaString) {
        this.fechaString = fechaString;
    }
}

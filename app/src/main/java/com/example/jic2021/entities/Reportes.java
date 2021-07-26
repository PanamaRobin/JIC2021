package com.example.jic2021.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Reportes implements Serializable {
    private String identificador;
    private String descripcion;
    private Date fecha;
    private List<Coordenadas> ubicacion;
    private String imagen;
    private String estado;
    private String identificadorUsuario;
    private List<Estados> estados;
    private String fechaString;

    //Constructor all
    public Reportes(String identificador, String descripcion, Date fecha, List<Coordenadas> ubicacion, String imagen, String estado, String identificadorUsuario, List<Estados> estados, String fechaString) {
        this.identificador = identificador;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.ubicacion = ubicacion;
        this.imagen = imagen;
        this.estado = estado;
        this.identificadorUsuario = identificadorUsuario;
        this.estados = estados;
        this.fechaString = fechaString;
    }

    //Constructor vacio
    public Reportes() {
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public List<Coordenadas> getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(List<Coordenadas> ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getIdentificadorUsuario() {
        return identificadorUsuario;
    }

    public void setIdentificadorUsuario(String identificadorUsuario) {
        this.identificadorUsuario = identificadorUsuario;
    }

    public List<Estados> getEstados() {
        return estados;
    }

    public void setEstados(List<Estados> estados) {
        this.estados = estados;
    }

    public String getFechaString() {
        return fechaString;
    }

    public void setFechaString(String fechaString) {
        this.fechaString = fechaString;
    }
}

package com.example.jic2021.entities;

import java.io.Serializable;
import java.util.List;

public class Usuarios implements Serializable {
    private String identificador;
    private String nombre;
    private String contrasena;
    private String email;
    private String telefono;
    private boolean activo;
    private List<RolesAsignados> roles;

    //Constructor all
    public Usuarios(String identificador, String nombre, String contrasena, String email, String telefono, boolean activo, List<RolesAsignados> roles) {
        this.identificador = identificador;
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.email = email;
        this.telefono = telefono;
        this.activo = activo;
        this.roles = roles;
    }

    //Constructor vacio
    public Usuarios() {
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public List<RolesAsignados> getRoles() {
        return roles;
    }

    public void setRoles(List<RolesAsignados> roles) {
        this.roles = roles;
    }
}

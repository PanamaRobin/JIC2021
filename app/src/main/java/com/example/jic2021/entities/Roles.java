package com.example.jic2021.entities;

import java.io.Serializable;

public class Roles implements Serializable {
    private int idRole;
    private String nombre;

    //Constructor all
    public Roles(int idRole, String nombre) {
        this.idRole = idRole;
        this.nombre = nombre;
    }

    //Constructor vacio
    public Roles() {
    }

    public int getIdRole() {
        return idRole;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}

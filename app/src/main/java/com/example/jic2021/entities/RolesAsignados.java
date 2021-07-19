package com.example.jic2021.entities;

import java.io.Serializable;

public class RolesAsignados implements Serializable {
    private int idRole;
    private boolean activo;

    //Constructor all
    public RolesAsignados(int idRole, boolean activo) {
        this.idRole = idRole;
        this.activo = activo;
    }

    //Constructor vacio
    public RolesAsignados() {
    }

    public int getIdRole() {
        return idRole;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}

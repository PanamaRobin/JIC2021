package com.example.jic2021.entities;

import java.io.Serializable;

public class Coordenadas implements Serializable {
    private double latitud;
    private double longitud;

    //Constructor all
    public Coordenadas(double longitud) {
        this.longitud = longitud;
    }

    //Constructor vacia
    public Coordenadas() {
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }
}

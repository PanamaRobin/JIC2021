package com.example.jic2021.interfaces;

import com.example.jic2021.entities.Reportes;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * Interfaz de listado de reportes
 */
public interface IReportes {
    @GET("reportes/allByUser/{id}")
    Call<List<Reportes>> listaReportes(@Header("Authorization") String id);
}

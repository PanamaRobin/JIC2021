package com.example.jic2021.interfaces;

import com.example.jic2021.entities.Reportes;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Interfaz de listado de reportes
 */
public interface IReportes {
    @GET("resources/reportes/allByUser/{id}")
    Call<List<Reportes>> listaReport(@Header("Authorization") String auth,@Path("id") String id);
    @POST("resources/reportes/add")
    Call<Reportes> crearReport(@Header("Authorization") String auth, @Body Reportes reporte);
}

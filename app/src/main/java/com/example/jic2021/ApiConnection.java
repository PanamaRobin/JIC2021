package com.example.jic2021;

import com.example.jic2021.interfaces.IReportes;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiConnection {

    //Este metodo privado hace la conexion con el API
    private static Retrofit obt_retrofit()
    {
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/API-APP/")// -/apis //controller
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }

    //Este metodo retorna la conexion de la interfaz que se comunica con el API
    public static IReportes obtReportes()
    {
        final IReportes url = obt_retrofit().create(IReportes.class);
        return url;
    }
}

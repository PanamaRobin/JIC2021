package com.example.jic2021.interfaces;

import com.example.jic2021.entities.Usuarios;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface IUsuario {
    @POST("resources/usuario/addCaptador")
    Call<Usuarios> crearUsuario(@Body Usuarios user, @Header("Authorization")String auth);

    @POST("resources/usuario/checkUsuario")
    Call<Usuarios> checkUsuario(@Body Usuarios user, @Header("Authorization")String auth);
}

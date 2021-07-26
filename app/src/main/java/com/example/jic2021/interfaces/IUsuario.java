package com.example.jic2021.interfaces;

import com.example.jic2021.entities.Usuarios;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IUsuario {
    @POST("resources/usuario/addCaptador")
    Call<Usuarios> crearUsuario(@Body Usuarios user, @Header("Authorization")String auth);

    @POST("resources/usuario/checkUsuario")
    Call<Usuarios> checkUsuario(@Body Usuarios user, @Header("Authorization")String auth);

    @PUT("resources/usuario/update")
    Call<Usuarios> actualizarUsuario(@Body Usuarios user,@Header("Authorization")String auth);

    @GET("resources/usuario/search/{id}")
    Call<Usuarios> obtUsuario(@Path("id") String id, @Header("Authorization")String auth);
}

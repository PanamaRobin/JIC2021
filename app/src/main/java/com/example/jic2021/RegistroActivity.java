package com.example.jic2021;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jic2021.entities.Usuarios;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroActivity extends AppCompatActivity {
    Dialog dialog_exitoso;
    Button cancelar,registrar;
    EditText cedula, correo, telefono, pass, nombre;
    public String base = "user" + ":" + "admin";
    public final String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        nombre = findViewById(R.id.et_nombre);
        cedula = findViewById(R.id.et_regcedula);
        correo = findViewById(R.id.et_regcorreo);
        telefono = findViewById(R.id.et_regtelefono);
        pass = findViewById(R.id.et_regcontraseña);
        registrar=findViewById(R.id.regregistro);
        cancelar = findViewById(R.id.regcancelar);/**Se conecta la variable creada con el id del botón correspondiente*/
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistroActivity.this, MainActivity.class));
            }
        });
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add();
            }
        });
        //Inicializacion del dialog
        dialog_exitoso = new Dialog(this);
    }

    public void registro(){
        Usuarios user = new Usuarios();
        user.setIdentificador(AES.encrypt(cedula.getText().toString(),"64aes64"));
        user.setNombre(AES.encrypt(nombre.getText().toString(),"64aes64"));
        user.setEmail(AES.encrypt(correo.getText().toString(),"64aes64"));
        user.setTelefono(AES.encrypt(telefono.getText().toString(),"64aes64"));
        user.setContrasena(AES.encrypt(pass.getText().toString(),"64aes64"));
        Call<Usuarios> calluser = ApiConnection.conexionUsuario().crearUsuario(user, authHeader);
        calluser.enqueue(new Callback<Usuarios>() {
            @Override
            public void onResponse(Call<Usuarios> call, Response<Usuarios> response) {
                try{
                    if(response.isSuccessful()){
                        registroDialogo();
                    }
                }catch(NullPointerException e){

                }
            }

            @Override
            public void onFailure(Call<Usuarios> call, Throwable t) {

            }
        });

    }
    public void add(){
        if(validacion()){
            if(!validarEmail(correo.getText().toString())){
                Toast.makeText(this, "Correo incorrecto", Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(this, "Procesando...", Toast.LENGTH_LONG).show();
                registro();
            }
        }
    }
    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
    //Dialog nativo descartadoo
    /*private void registroDialogo(){
        new AlertDialog.Builder(this)
                .setTitle("Todo Listo!")
                .setMessage("Registro Exitoso!")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .show();
    }*/
    //Custom dialog
    private void registroDialogo(){
        dialog_exitoso.setContentView(R.layout.activity_exitoso);
        dialog_exitoso.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button registro_button = dialog_exitoso.findViewById(R.id.aceptar);

        registro_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_exitoso.dismiss();
                finish();
            }
        });

        dialog_exitoso.show();
    }
    public boolean validacion(){
        boolean retorno = true;
        String c1 = cedula.getText().toString();
        String c2 = nombre.getText().toString();
        String c3 = correo.getText().toString();
        String c4 = telefono.getText().toString();
        String c5 = pass.getText().toString();
        if(c1.isEmpty()){
            cedula.setError("Introduzca su cédula!");
            retorno=false;
        }
        if(c2.isEmpty()){
            nombre.setError("Introduzca su nombre!");
            retorno=false;
        }
        if(c3.isEmpty()){
            correo.setError("Introduzca su correo!");
            retorno=false;
        }
        if(c4.isEmpty()){
            telefono.setError("Introduzca su número telefónico!");
            retorno=false;
        }
        if(c5.isEmpty()){
            pass.setError("Introduzca su contraseña!");
            retorno=false;
        }
        return retorno;
    }
}

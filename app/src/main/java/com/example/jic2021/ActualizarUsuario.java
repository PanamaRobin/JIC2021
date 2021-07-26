package com.example.jic2021;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jic2021.entities.Usuarios;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActualizarUsuario extends AppCompatActivity {
    String idUsuario;
    Button cancelar,actualizar;
    EditText  correo, telefono, Npass, nombre;
    TextView cedula;

    Dialog dialog_exitoso;

    Usuarios usuarioAnterior= new Usuarios();
    Usuarios usuarioNuevo= new Usuarios();

    public String base = "user" + ":" + "admin";
    public final String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_usuario);

        nombre = findViewById(R.id.et_nombre);
        cedula = findViewById(R.id.et_regcedula);
        correo = findViewById(R.id.et_regcorreo);
        telefono = findViewById(R.id.et_regtelefono);
        Npass = findViewById(R.id.et_regcontraseña);
        actualizar=findViewById(R.id.actualizar);
        cancelar = findViewById(R.id.regcancelar);

        idUsuario = getIntent().getStringExtra("idUsuario");
        //Obtengo los datos anteriores del usuario
        usuarioAnterior=UsuarioAnterior(idUsuario);

        //Inicializacion del dialog
        dialog_exitoso = new Dialog(this);

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActualizarUsuario.this,SolicitudesActivity.class);
                intent.putExtra("idUsuario", idUsuario);
                startActivity(intent);
            }
        });
        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validacion(usuarioAnterior)){
                    if(!validarEmail(correo.getText().toString())){
                        Toast.makeText(ActualizarUsuario.this, "Correo incorrecto", Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(ActualizarUsuario.this, "Procesando...", Toast.LENGTH_LONG).show();
                        actualizar();
                        registroDialogo();
                    }
                }
            }
        });

    }
    //Custom dialog
    private void registroDialogo() {
        dialog_exitoso.setContentView(R.layout.activity_exitoso);
        dialog_exitoso.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button registro_button = dialog_exitoso.findViewById(R.id.aceptar);
        TextView texto = dialog_exitoso.findViewById(R.id.registro_text);

        texto.setText("Actualización Exitosa!");

        registro_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_exitoso.dismiss();
                finish();
            }
        });

        dialog_exitoso.show();
    }
    public Usuarios UsuarioAnterior(String id)
    {
        Call<Usuarios> anteriorusuario= ApiConnection.conexionUsuario().obtUsuario(id,authHeader);
        anteriorusuario.enqueue(new Callback<Usuarios>() {
            @Override
            public void onResponse(Call<Usuarios> call, Response<Usuarios> response) {
                try {
                    if(response.isSuccessful())
                    {
                        usuarioAnterior=response.body();
                        //Se lo asigno a los campos
                        nombre.setText(usuarioAnterior.getNombre());
                        cedula.setText(usuarioAnterior.getIdentificador());
                        correo.setText(usuarioAnterior.getEmail());
                        telefono.setText(usuarioAnterior.getTelefono());
                    }
                }catch (NullPointerException e){
                    Toast.makeText(ActualizarUsuario.this,"Usuario No existe",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Usuarios> call, Throwable t) {
                Toast.makeText(ActualizarUsuario.this,t.getLocalizedMessage(),Toast.LENGTH_LONG).show();
            }
        });

        return usuarioAnterior;
    }
    public void actualizar(){
        Usuarios user = new Usuarios();
        user.setIdentificador(AES.encrypt(cedula.getText().toString(),"64aes64"));
        user.setNombre(AES.encrypt(nombre.getText().toString(),"64aes64"));
        user.setEmail(AES.encrypt(correo.getText().toString(),"64aes64"));
        user.setTelefono(AES.encrypt(telefono.getText().toString(),"64aes64"));
        user.setActivo(true);
        if(Npass.getText().toString().isEmpty()){
            user.setContrasena(AES.encrypt(usuarioAnterior.getContrasena(),"64aes64"));
        }
        else{
            user.setContrasena(AES.encrypt(Npass.getText().toString(),"64aes64"));
        }
        Call<Usuarios> calluser = ApiConnection.conexionUsuario().actualizarUsuario(user, authHeader);
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
    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
    public boolean validacion(Usuarios usuarioAnterior){
        boolean retorno = false;
        if(usuarioAnterior.getEmail()!= correo.getText().toString() || usuarioAnterior.getNombre()!= nombre.getText().toString() ||
        usuarioAnterior.getTelefono()!= telefono.getText().toString())
        {
            retorno= true;
        }
        return retorno;
    }
    /*private void registroDialogo(){
        new AlertDialog.Builder(this)
                .setTitle("Todo Listo!")
                .setMessage("Usuario Actualizado!")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .show();
    }*/
}

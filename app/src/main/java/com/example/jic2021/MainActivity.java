package com.example.jic2021;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import com.example.jic2021.entities.Usuario;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
Button  salir, registrarse, olvidopass;
EditText logcorreo, logpass;
    public String base = "user" + ":" + "admin";
    public final String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logcorreo = findViewById(R.id.et_logcorreo);/**Se conecta la variable creada con el id del edittext correspondiente*/
        logpass = findViewById(R.id.et_logcontraseña);/**Se conecta la variable creada con el id del edittext correspondiente*/

        registrarse = findViewById(R.id.logregistro);/**Se conecta la variable creada con el id del botón correspondiente*/
        registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RegistroActivity.class));
            }
        });
    }
public void agregar(View v){
        if(validar()){
            Toast.makeText(this, "Procesando...", Toast.LENGTH_LONG).show();
            inicioUsuario();
        }
}
        /**public void Enviar (View v){
        if(logcorreo.getText().toString().isEmpty()){
            Toast.makeText(this, "Campo cédula vacío", Toast.LENGTH_LONG).show();
        }else{
            if(logpass.getText().toString().isEmpty()){
            Toast.makeText(this,"Campo contraseña vacío", Toast.LENGTH_LONG).show();
        }else{
          Toast.makeText(this, "Inicio de Sesion exitoso", Toast.LENGTH_LONG).show();
          inicioUsuario();
            }
        }
    }*/
    public void inicioUsuario(){
        Usuario user = new Usuario();
        user.setEmail(AES.encrypt(logcorreo.getText().toString(),"64aes64"));
        user.setContrasena(AES.encrypt(logpass.getText().toString(),"64aes64"));
        Call<Usuario> calluser = API.conexionUsuario().checkUsuario(user, authHeader);
        calluser.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                try{
                    if(response.isSuccessful()){
                        Usuario u= response.body();
                        if(u.isActivo()) {
                            startActivity(new Intent(MainActivity.this, SolicitudesActivity.class));
                        }
                    }
                }catch(NullPointerException e){

                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {

            }
        });
    }
    public boolean validar(){
        boolean retorno = true;
        String c1 = logcorreo.getText().toString();
        String c2 = logpass.getText().toString();
        if(c1.isEmpty()){
            logcorreo.setError("Introduzca su correo!");
            retorno=false;
        }
        if(c2.isEmpty()){
            logpass.setError("Introduzca su contraseña!");
            retorno=false;
        }
        return retorno;
    }
}

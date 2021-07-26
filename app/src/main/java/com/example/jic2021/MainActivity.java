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
import com.example.jic2021.entities.Usuarios;

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
    public void inicioUsuario(){
        Usuarios user = new Usuarios();
        user.setEmail(AES.encrypt(logcorreo.getText().toString(),"64aes64"));
        user.setContrasena(AES.encrypt(logpass.getText().toString(),"64aes64"));
        Call<Usuarios> calluser = ApiConnection.conexionUsuario().checkUsuario(user, authHeader);
        calluser.enqueue(new Callback<Usuarios>() {
            @Override
            public void onResponse(Call<Usuarios> call, Response<Usuarios> response) {
                Log.d("error","Entre");
                try{
                    if(response.isSuccessful()){
                        Log.d("usuario","Entre");
                        Usuarios u= response.body();
                        if(u.isActivo()) {
                            Intent intent = new Intent(MainActivity.this,SolicitudesActivity.class);
                            intent.putExtra("idUsuario", u.getIdentificador());
                            startActivity(intent);
                            //startActivity(new Intent(MainActivity.this, SolicitudesActivity.class));
                        }
                        else{
                            Toast.makeText(MainActivity.this, "Usuario no existe", Toast.LENGTH_LONG).show();
                        }
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Usuario no existe", Toast.LENGTH_LONG).show();
                    }
                }catch(NullPointerException e){
                    Toast.makeText(MainActivity.this,  e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Usuarios> call, Throwable t) {
                Toast.makeText(MainActivity.this,  t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
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

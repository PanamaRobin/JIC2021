package com.example.jic2021;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
Button  registrarse, olvidopass;
EditText user, pass;
ImageButton salir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user = findViewById(R.id.et_loguser);/**Se conecta la variable creada con el id del edittext correspondiente*/
        pass = findViewById(R.id.et_logcontraseña);/**Se conecta la variable creada con el id del edittext correspondiente*/

        registrarse = findViewById(R.id.logregistro);/**Se conecta la variable creada con el id del botón correspondiente*/
        registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RegistroActivity.class));
            }
        });

        salir = findViewById(R.id.salir);
        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogo();
            }
        });
    }
       private void mostrarDialogo(){
           new AlertDialog.Builder(this)
                   .setTitle("Salir")
                   .setMessage("¿Desea salir de la aplicación?")
                   .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                           finish();
                       }
                   })
                   .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                           Log.d("Mensaje", "Se canceló la acción");
                       }
                   })
                   .show();
        }

        public void Enviar (View v){
        if(user.getText().toString().isEmpty()){
            Toast.makeText(this, "Campo cédula vacío", Toast.LENGTH_LONG).show();
        }else{
            if(pass.getText().toString().isEmpty()){
            Toast.makeText(this,"Campo contraseña vacío", Toast.LENGTH_LONG).show();
        }else{
          Toast.makeText(this, "Inicio de Sesion exitoso", Toast.LENGTH_LONG).show();
            }
        }

        /**aquí se le asigna un OnClick, que es un evento que se dará cuando se pulse el botón. Lo que hace es que cuando el usuario aprete el botón
         * registrarse, cambie de la vista de iniciar sesión a la vista de
         * registrarse*/
    }
}
package com.example.jic2021;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
public class MainActivity extends AppCompatActivity {
Button iniciar, registrarse, olvidopass;
EditText user, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user=findViewById(R.id.et_loguser);/**Se conecta la variable creada con el id del edittext correspondiente*/
        pass=findViewById(R.id.et_logcontraseña);/**Se conecta la variable creada con el id del edittext correspondiente*/
        iniciar=findViewById(R.id.iniciar);/**Se conecta la variable creada con el id del botón correspondiente*/
        iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,RegistroActivity.class));
            }
        });/**aquí se le asigna un OnClick, que es un evento que se dará cuando se pulse el botón. Aquí,
         al apretar el botón de iniciar sesión, cambia de vista. La vista a la cual debe ir sería la de Inicio, que es la del listado de solicitudes.
         Pero cómo no se encuentra en mi rama, lo coloqué que vaya a la de registro
         únicamente para que vea el funcionamiento. Cuando se conecten las 4 ramas, entonces es cambia a:
         startActivity(new(Intent(MainActivity.this,SolicitudesActivity.class));*/
        registrarse=findViewById(R.id.logregistro);/**Se conecta la variable creada con el id del botón correspondiente*/
        registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent (MainActivity.this,RegistroActivity.class));
            }
        });
        /**aquí se le asigna un OnClick, que es un evento que se dará cuando se pulse el botón. Lo que hace es que cuando el usuario aprete el botón
         * registrarse, cambie de la vista de iniciar sesión a la vista de
         * registrarse*/
    }
}
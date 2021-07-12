package com.example.jic2021;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
public class RegistroActivity extends AppCompatActivity {
Button regregistro, cancelar;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_registro);

        regregistro=findViewById(R.id.regregistro);/**Se conecta la variable creada con el id del botón correspondiente*/
        regregistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistroActivity.this,MainActivity.class));
                Toast toast1 =
                        Toast.makeText(getApplicationContext(),
                                "Registro exitoso", Toast.LENGTH_SHORT);
                toast1.show();
            }
        });/**Aquí, cuando el usuario ha llenado los campos debidamente, apretará el botón de Registrarse. Una vez apretado, se envía un mensaje corto
     notificándole que se ha registrado exitosamente e inmediatamente lo lleva a la vista de Iniciar Sesión.*/
        cancelar=findViewById(R.id.regcancelar);/**Se conecta la variable creada con el id del botón correspondiente*/
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistroActivity.this,MainActivity.class));
            }
        });/**En caso tal de que el usuario no quiera crear una cuenta, puede darle al botón cancelar y lo llevará a la pantalla de Iniciar Sesión*/
}
}
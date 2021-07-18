package com.example.jic2021;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class RegistroActivity extends AppCompatActivity {
    Button regregistro, cancelar;
    EditText cedula, correo, telefono, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        cedula = findViewById(R.id.et_regcedula);
        correo = findViewById(R.id.et_regcorreo);
        telefono = findViewById(R.id.et_regtelefono);
        pass = findViewById(R.id.et_regcontraseña);
        cancelar = findViewById(R.id.regcancelar);/**Se conecta la variable creada con el id del botón correspondiente*/
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistroActivity.this, MainActivity.class));
            }
        });
    }

    public void Registrar(View v) {
        if (cedula.getText().toString().isEmpty()) {
            Toast.makeText(this, "Campo cédula vacío", Toast.LENGTH_LONG).show();
        } else {
            if (correo.getText().toString().isEmpty()) {
                Toast.makeText(this, "Campo correo vacío", Toast.LENGTH_LONG).show();
            } else {
                if (telefono.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Campo teléfono vacío", Toast.LENGTH_LONG).show();
                } else {
                    if (pass.getText().toString().isEmpty()) {
                        Toast.makeText(this, "Campo contraseña vacío", Toast.LENGTH_LONG).show();
                    } else {
                        if (!validarEmail(correo.getText().toString())) {
                            Toast.makeText(this, "Correo incorrecto", Toast.LENGTH_LONG).show();
                        } else {
                            startActivity(new Intent(RegistroActivity.this, ExitosoActivity.class));
                        }

                    }
                }
            }
        }
    }
    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
}

package com.example.jic2021;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
Button iniciar, registrarse, olvidopass;
    /**
     * Login, esta seccion le toca a Josias Aponte.
     * Por favor agregar comentarios al codigo para poder visualizarlo mejor cuando haya
     * que juntarlo.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iniciar=findViewById(R.id.iniciar);
        iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,RegistroActivity.class));
            }
        });
        registrarse=findViewById(R.id.logregistro);
        registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent (MainActivity.this,RegistroActivity.class));
            }
        });

    }
}
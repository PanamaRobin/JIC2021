package com.example.jic2021;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SolicitudesActivity extends AppCompatActivity {

    /**
     * Vista de Solicitudes, esta seccion le toca a Jorge Herrera.
     * Por favor agregar comentarios al codigo para poder visualizarlo mejor cuando haya
     * que juntarlo.
     */

    FloatingActionButton float1, float2, float3;
    Animation closeAnim, openAnim, fromBottom, toBottom;

    boolean isVisible = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitudes);

        //Floating action buttons
        float1 = (FloatingActionButton) findViewById(R.id.new_button);
        float2 = (FloatingActionButton) findViewById(R.id.profile_button);
        float3 = (FloatingActionButton) findViewById(R.id.camera_button);

        //Animations
        closeAnim = AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim);
        openAnim = AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim);
        fromBottom = AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim);
        toBottom = AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim);

        float1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateFab();
            }
        });
    }

    private void animateFab(){
        if(isVisible) {
            float1.startAnimation(openAnim);
            float2.startAnimation(fromBottom);
            float3.startAnimation(fromBottom);
            isVisible = false;
        }else{
            float1.startAnimation(closeAnim);
            float2.startAnimation(toBottom);
            float3.startAnimation(toBottom);
            isVisible = true;
        }

    }
}
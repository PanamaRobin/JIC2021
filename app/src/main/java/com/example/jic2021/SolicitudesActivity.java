package com.example.jic2021;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jic2021.entities.Reportes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SolicitudesActivity extends AppCompatActivity implements RecyclerAdapter.OnItemListener {

    /**
     * Vista de Solicitudes, esta seccion le toca a Jorge Herrera.
     * Por favor agregar comentarios al codigo para poder visualizarlo mejor cuando haya
     * que juntarlo.
     */

    //Dialog que despliega los datos de la solicitud
    Dialog solicitud_dialog;

    FloatingActionButton float1, float2, float3;
    Animation closeAnim, openAnim, fromBottom, toBottom;

    //Declaracion de RecyclerView de la vista
    RecyclerView recyclerView;

    //Declaracion del RecyclerAdapter
    RecyclerAdapter recyclerAdapter;

    //Para la autenticacion de los endpoints
    public String base = "user" + ":" + "admin";
    public final String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

    //Lista de reportes que se envia al adapter
    public List<Reportes> listaFinalReportes;

    boolean isVisible = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitudes);

        obtenerReportes(authHeader);

        //RecyclerView y RecyclerAdapter
        recyclerView = findViewById(R.id.solicitudesRecycler);

        //Se envia la lista de reportes al adapter
        recyclerAdapter = new RecyclerAdapter(listaFinalReportes);

        //Inicializacion del dialog
        solicitud_dialog = new Dialog(this);

        //Inicializacion de la vista del adapter dentro del recyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerAdapter);

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

    public void obtenerReportes(String authHeader) {
        Call<List<Reportes>> listR=ApiConnection.obtReportes().listaReportes(authHeader);
        listR.enqueue(new Callback<List<Reportes>>() {
            @Override
            public void onResponse(Call<List<Reportes>> call, Response<List<Reportes>> response) {
                try{

                    if(response.isSuccessful())
                    {
                        Log.d("a","entreeeeeeeeeeeee");

                        //Aqui se carga la lista de reportes
                        listaFinalReportes = response.body();
                    }
                }catch (NullPointerException e){
                    //TODO: Capturar excepciones
                }
            }

            @Override
            public void onFailure(Call<List<Reportes>> call, Throwable t) {
                Log.e("Falloooo", t.getLocalizedMessage());
            }
        });
    }

    //Animacion del boton flotante
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

    @Override
    public void OnItemClick(int position) {
        openSolicitudDialog();
    }

    private void openSolicitudDialog() {
        setContentView(R.layout.solicitud_dialog);
        solicitud_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //Declaracion de elementos dentro del dialog
        ImageView solicitud_image = solicitud_dialog.findViewById(R.id.solicitud_image);
        Button solicitud_button = solicitud_dialog.findViewById(R.id.aceptar_btn);
        TextView solicitud_title = solicitud_dialog.findViewById(R.id.solicitud_title);
        TextView descripcion_solicitud = solicitud_dialog.findViewById(R.id.descripcion_solicitud);
        TextView fecha_solicitud = solicitud_dialog.findViewById(R.id.fecha_solicitud);
        TextView estado_solicitud = solicitud_dialog.findViewById(R.id.estado_solicitud);

        //TODO: Rellenar las variables creadas con la API

        //Boton de aceptar cierra el dialog
        solicitud_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                solicitud_dialog.dismiss();
            }
        });

        //Despliegue del dialog
        solicitud_dialog.show();
    }
}
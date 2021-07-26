package com.example.jic2021;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jic2021.entities.Reportes;
import com.example.jic2021.entities.Usuarios;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.apache.commons.net.ftp.FTPClient;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//Se implementa la interfaz dentro del adapter
public class SolicitudesActivity extends AppCompatActivity implements RecyclerAdapter.OnItemListener {

    /**
     * Vista de Solicitudes, esta seccion le toca a Jorge Herrera.
     * Por favor agregar comentarios al codigo para poder visualizarlo mejor cuando haya
     * que juntarlo.
     */

    //Dialog que despliega los datos de la solicitud
    Dialog solicitud_dialog1;

    FloatingActionButton float1, float2, float3, float4;
    Animation closeAnim, openAnim, fromBottom, toBottom;

    //Declaracion de RecyclerView de la vista
    RecyclerView recyclerView;

    //Declaracion del RecyclerAdapter
    RecyclerAdapter recyclerAdapter;

    //Para la autenticacion de los endpoints
    public String base = "user" + ":" + "admin";
    public final String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

    //Lista de reportes que se envia al adapter
    public static List<Reportes> listaFinalReportes;

    boolean isVisible = true;

    private static final int REQUEST_PERMISSION_CAMERA=100;
    private static final int REQUEST_IMAGE_CAMERA=101;

    public ContentValues values;
    public Uri imageUri;
    public static Bitmap bitmap;

    public static InputStream input;

    String imageurl;
    String currentPhotoPath;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    public static String filenameimagen;

    public static String id, descripcion,fecha,estado;
    String idUsuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitudes);

        idUsuario = getIntent().getStringExtra("idUsuario");
        //Log.d("idUsuario",idUsuario);
        obtenerReportes();

        //RecyclerView y RecyclerAdapter
        recyclerView = findViewById(R.id.solicitudesRecycler);


        //Inicializacion del dialog
        solicitud_dialog1 = new Dialog(this);

        //Inicializacion de la vista del adapter dentro del recyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Floating action buttons
        float1 = (FloatingActionButton) findViewById(R.id.new_button);
        float2 = (FloatingActionButton) findViewById(R.id.profile_button);
        float3 = (FloatingActionButton) findViewById(R.id.camera_button);
        float4 = (FloatingActionButton) findViewById(R.id.logout_button);

        //Animations
        closeAnim = AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim);
        openAnim = AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim);
        fromBottom = AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim);
        toBottom = AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim);

        //Boton flotante con su animacion
        float1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateFab();
            }
        });
        //Para la Camara
        float3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(ActivityCompat.checkSelfPermission(SolicitudesActivity.this, Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED){
                        goTocamera();
                    }
                    else {
                        ActivityCompat.requestPermissions(SolicitudesActivity.this,new String[]{Manifest.permission.CAMERA},REQUEST_PERMISSION_CAMERA);
                    }
                }
                else{
                    goTocamera();
                }
            }
        });

        //Para el perfil de usuario
        float2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SolicitudesActivity.this,ActualizarUsuario.class);
                intent.putExtra("idUsuario", idUsuario);
                startActivity(intent);
            }
        });

        //Para cerrar sesion
        float4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SolicitudesActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        //permisos para el ftp
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }
    //Camara
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==REQUEST_PERMISSION_CAMERA){
            if(permissions.length >0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                goTocamera();
            }
            else{
                Toast.makeText(this,"Necesita Habilitar los Permisos",Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==REQUEST_IMAGE_CAMERA){
            if(resultCode== Activity.RESULT_OK){
                try {
                    bitmap=MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);

                    //Obtiene la ruta donde se encuentra guardada la imagen.
                    imageurl = getRealPathFromURI(imageUri);
                    Log.i("TAG","result>=" +bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //jose
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String imageFileName = "JPEG_" + timeStamp + "_";
                filenameimagen=imageFileName+".jpg";
                Log.i("TAG",filenameimagen);

                //Envio la imagen a la otra actividad
                try {
                    //Write file
                    String filename = filenameimagen;
                    FileOutputStream stream = this.openFileOutput(filename, Context.MODE_PRIVATE);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

                    //Cleanup
                    stream.close();
                    bitmap.recycle();

                    //Pop intent
                    Intent in1 = new Intent(this, NuevaSolicitudActivity.class);
                    in1.putExtra("imagen", filename);
                    in1.putExtra("idUsuario",idUsuario);
                    startActivity(in1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public String getRealPathFromURI(Uri contentUri) {
        //Para obtener la URL de donde se guarda la imagen
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
    private void goTocamera(){
        //busco en la galeria
        values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Photo taken on " + System.currentTimeMillis());
        imageUri = getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent cameraIntent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(cameraIntent,REQUEST_IMAGE_CAMERA);

    }
    //Metodo que se encarga de llamar a la API
    public void obtenerReportes() {
        //Call<List<Reportes>> listR=ApiConnection.obtReportes().listaReport(authHeader,idUsuario);
        Call<List<Reportes>> listR=ApiConnection.obtReportes().listaReport(authHeader,idUsuario);
        listR.enqueue(new Callback<List<Reportes>>() {
            @Override
            public void onResponse(Call<List<Reportes>> call, Response<List<Reportes>> response) {
                try{

                    if(response.isSuccessful())
                    {
                        //Aqui se carga la lista de reportes
                        listaFinalReportes = response.body();
                        if(listaFinalReportes.size()<1)
                        {
                            Toast.makeText(SolicitudesActivity.this,"No tiene ningun Reporte",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            //Se envia la lista de reportes al adapter y se carga la interfaz que fue implementada
                            recyclerAdapter = new RecyclerAdapter(listaFinalReportes,SolicitudesActivity.this);
                            recyclerView.setAdapter(recyclerAdapter);
                        }
                    }
                }catch (NullPointerException e){
                    Log.d("error",e.getLocalizedMessage());
                    //TODO: Capturar excepciones
                }
            }

            @Override
            public void onFailure(Call<List<Reportes>> call, Throwable t) {
                Log.e("Fallo", t.getLocalizedMessage());
            }
        });
    }

    //Animacion del boton flotante
    private void animateFab(){
        if(isVisible) {
            float1.startAnimation(openAnim);
            float2.startAnimation(fromBottom);
            float3.startAnimation(fromBottom);
            float4.startAnimation(fromBottom);
            isVisible = false;
        }else{
            float1.startAnimation(closeAnim);
            float2.startAnimation(toBottom);
            float3.startAnimation(toBottom);
            float4.startAnimation(toBottom);
            isVisible = true;
        }

    }

    //Metodo implementado desde la interfaz en el adapter
    @Override
    public void onItemClick(int position) {
        //Se cargan las variables que se mostraran dentro del dialog
        id = listaFinalReportes.get(position).getIdentificador();
        descripcion = listaFinalReportes.get(position).getDescripcion();
        fecha = listaFinalReportes.get(position).getFechaString();
        estado = listaFinalReportes.get(position).getEstado();

        //Mapeo el URL
        String str = listaFinalReportes.get(position).getImagen();
        String[] arrOfStr = str.split("ftp://192.168.0.13/");
        String b = TextUtils.join("", arrOfStr);

        Log.d("url imagen",str);
        Log.d("string imagen",b);

        InputStream im;
        im=obtImagen(str,b);

        //Log.d("ob",bitmap.toString());
        openSolicitudDialog(im);
    }
    public InputStream obtImagen(String urlImagen, String imagen){
        //Create FTPClient object
        String server = "192.168.0.13";
        String user = "admin";
        String pass = "josejose";


        URL url = null;
        try {
            String ftpUrl = "ftp://%s:%s@%s/%s";
            ftpUrl = String.format(ftpUrl, user, pass, server,imagen );

            url = new URL(ftpUrl);
            URLConnection urlConnection =url.openConnection();
            urlConnection.setDoInput(true);
            input = urlConnection.getInputStream();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return input;
    }
    public void openSolicitudDialog(InputStream im) {

        solicitud_dialog1.setContentView(R.layout.solicitud_dialog);
        solicitud_dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        /*AlertDialog.Builder builder= new AlertDialog.Builder(this);
        View dia= getLayoutInflater().inflate(R.layout.solicitud_dialog,null);
        builder.setView(dia);*/
        //dia.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Log.d("aa",id+" "+descripcion+" "+fecha+" "+estado);
        //Declaracion de elementos dentro del dialog
        ImageView solicitud_image = solicitud_dialog1.findViewById(R.id.solicitud_image);
        Button solicitud_button = solicitud_dialog1.findViewById(R.id.aceptar_btn);
        TextView solicitud_title = solicitud_dialog1.findViewById(R.id.solicitud_title);
        TextView descripcion_solicitud = solicitud_dialog1.findViewById(R.id.descripcion_solicitud);
        TextView fecha_solicitud = solicitud_dialog1.findViewById(R.id.fecha_solicitud);
        TextView estado_solicitud = solicitud_dialog1.findViewById(R.id.estado_solicitud);

        //Se cargan las variables dentro del dialog
        solicitud_title.setText(id);
        descripcion_solicitud.setText(descripcion);
        fecha_solicitud.setText(fecha);

        Bitmap otro;
        otro=BitmapFactory.decodeStream(im);

        solicitud_image.setImageBitmap(otro);

       switch(estado){
            case "Finalizado":
                estado_solicitud.setText(estado);
                estado_solicitud.setBackgroundResource(R.drawable.login_button);
                break;
            case "Pendiente":
                estado_solicitud.setText(estado);
                estado_solicitud.setBackgroundResource(R.drawable.yellow_rounded_button);
                break;
            case "Rechazado":
                estado_solicitud.setText(estado);
                estado_solicitud.setBackgroundResource(R.drawable.red_rounded_button);
                break;
            default:
                break;
        }

        //Boton de aceptar cierra el dialog
        solicitud_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                solicitud_dialog1.dismiss();
            }
        });
        //Este crea un botoncito feo a parte inferior izquierda pero funciona xd
        /*builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });*/
        //builder.create().show();
        //Despliegue del dialog
        solicitud_dialog1.show();
    }
}
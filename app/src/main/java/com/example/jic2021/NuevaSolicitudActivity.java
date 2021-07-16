package com.example.jic2021;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class NuevaSolicitudActivity extends AppCompatActivity {

    /**
     * Nueva Solicitud, esta seccio le toca a Jorge Morales.
     * Por favor agregar comentarios al codigo para poder visualizarlo mejor cuando haya
     * que juntarlo.
     * @param savedInstanceState
     */
    TextView tvBotoncambiar;
    TextView tvBotoncancelar;
    TextView tvBotonenviar;

    ImageView picture;

    private static final int REQUEST_PERMISSION_CAMERA=100;
    private static final int REQUEST_IMAGE_CAMERA=101;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_solicitud);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        picture = findViewById(R.id.picture);
        tvBotoncambiar = (TextView) findViewById(R.id.textViewcambiar);
        tvBotoncancelar = (TextView) findViewById(R.id.textViewcancelar);
        tvBotonenviar = (TextView) findViewById(R.id.textViewenviar);

        tvBotoncambiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(),"cambiar",Toast.LENGTH_SHORT).show();
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                     if(ActivityCompat.checkSelfPermission(NuevaSolicitudActivity.this, Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED){
                         goTocamera();
                     }
                     else {
                         ActivityCompat.requestPermissions(NuevaSolicitudActivity.this,new String[]{Manifest.permission.CAMERA},REQUEST_PERMISSION_CAMERA);
                     }
                }
                else{
                    goTocamera();
                }

            }
        });
        tvBotoncancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvBotonenviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==REQUEST_PERMISSION_CAMERA){
            if(permissions.length >0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                goTocamera();
            }
            else{
                Toast.makeText(this,"se necesita avilitar los permisos",Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==REQUEST_IMAGE_CAMERA){
            if(resultCode== Activity.RESULT_OK){
                Bitmap bitmap= (Bitmap) data.getExtras().get("data");
                picture.setImageBitmap(bitmap);
                Log.i("TAG","result>=" +bitmap);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void goTocamera(){
        Intent cameraIntent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(cameraIntent.resolveActivity(getPackageManager())!=null){
           startActivityForResult(cameraIntent,REQUEST_IMAGE_CAMERA);
        }
    }
}
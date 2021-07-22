package com.example.jic2021;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jic2021.entities.Coordenadas;
import com.example.jic2021.entities.Reportes;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.apache.commons.net.ftp.FTPClient;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.LongSummaryStatistics;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NuevaSolicitudActivity extends AppCompatActivity implements OnMapReadyCallback {

    /**
     * Nueva Solicitud, esta seccion le toca a Jorge Morales.
     * Por favor agregar comentarios al codigo para poder visualizarlo mejor cuando haya
     * que juntarlo.
     *
     * @param savedInstanceState
     */
    TextView tvBotoncambiar;
    TextView tvBotoncancelar;
    TextView tvBotonenviar;

    EditText descripcion;

    ImageView picture;

    private static final int REQUEST_PERMISSION_CAMERA = 100;
    private static final int REQUEST_IMAGE_CAMERA = 101;
    public ContentValues values;
    public Uri imageUri;
    public static Bitmap bitmap;
    String imageurl;
    String currentPhotoPath;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static String filenameimagen;

    private GoogleMap mMap;
    static double Latitud, Longitud;
    private LocationRequest locationRequest;
    public static final int REQUEST_CHECK_SETTING = 1001;

    //Para la autenticacion de los endpoints
    public String base = "user" + ":" + "admin";
    public final String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

    public  String idUsuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_solicitud);
        //recibo el idUsuario
        idUsuario=getIntent().getStringExtra("idUsuario");
        Log.d("id",idUsuario);


        //Mapa
        getLocalizacion();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        picture = findViewById(R.id.picture);
        tvBotoncambiar = (TextView) findViewById(R.id.textViewcambiar);
        tvBotoncancelar = (TextView) findViewById(R.id.textViewcancelar);
        tvBotonenviar = (TextView) findViewById(R.id.textViewenviar);
        descripcion= (EditText) findViewById(R.id.descripcion);

        //Recibo la imagen
        String filename = getIntent().getStringExtra("imagen");
        try {
            FileInputStream is = this.openFileInput(filename);
            bitmap = BitmapFactory.decodeStream(is);
            picture.setImageBitmap(bitmap);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        tvBotoncambiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ActivityCompat.checkSelfPermission(NuevaSolicitudActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        goTocamera();
                    } else {
                        ActivityCompat.requestPermissions(NuevaSolicitudActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_PERMISSION_CAMERA);
                    }
                } else {
                    goTocamera();
                }

            }
        });
        tvBotoncancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NuevaSolicitudActivity.this, SolicitudesActivity.class));
            }
        });
        //permisos para el ftp
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        tvBotonenviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(descripcion.getText().toString().isEmpty())
                {
                    Toast.makeText(NuevaSolicitudActivity.this, "Coloque una Descripción", Toast.LENGTH_LONG).show();
                }
                else
                {
                    try {
                        ftpUpload(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    //Mapa
    private void getLocalizacion() {
        int permiso = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        if (permiso == PackageManager.PERMISSION_DENIED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {

            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(getApplicationContext())
                .checkLocationSettings(builder.build());

        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull @org.jetbrains.annotations.NotNull Task<LocationSettingsResponse> task) {
                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    Toast.makeText(NuevaSolicitudActivity.this, "GPS Activado", Toast.LENGTH_LONG).show();
                } catch (ApiException e) {
                    switch (e.getStatusCode()) {
                        case LocationSettingsStatusCodes
                                .RESOLUTION_REQUIRED:

                            try {
                                ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                                resolvableApiException.startResolutionForResult(NuevaSolicitudActivity.this, REQUEST_CHECK_SETTING);
                            } catch (IntentSender.SendIntentException sendIntentException) {
                                sendIntentException.printStackTrace();
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            break;
                    }
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * <p>
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        /*// Add a marker in Sydney and move the camera.... para posicionarlo en una ubicacion especifica
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions()
                .position(sydney)
                .title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        mMap.setMyLocationEnabled(true);

        mMap.getUiSettings().setMyLocationButtonEnabled(false);

        LocationManager locationManager = (LocationManager) NuevaSolicitudActivity.this.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                LatLng miUbicacion = new LatLng(location.getLatitude(), location.getLongitude());
                Log.d("latitud y longitud", "Latitud" + location.getLatitude() + "Longitud" + location.getLongitude());
                Latitud=location.getLatitude();
                Longitud=location.getLongitude();
                mMap.addMarker(new MarkerOptions().position(miUbicacion).title("Ubicacion actual"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(miUbicacion));
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(miUbicacion)
                        .zoom(14)
                        .bearing(90)
                        .tilt(45)
                        .build();
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
    }

    //Camara
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION_CAMERA) {
            if (permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                goTocamera();
            } else {
                Toast.makeText(this, "Necesita Habilitar los Permisos", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //mapa
        if (requestCode == REQUEST_CHECK_SETTING) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                    Toast.makeText(this, "GPS Activado", Toast.LENGTH_LONG).show();
                    break;
                case Activity.RESULT_CANCELED:
                    Toast.makeText(this, "El GPS es requerido", Toast.LENGTH_LONG).show();
            }
        }
        //camara
        if (requestCode == REQUEST_IMAGE_CAMERA) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);

                    //bitmap= (Bitmap) data.getExtras().get("data");
                    picture.setImageBitmap(bitmap);
                    //Obtiene la ruta donde se encuentra guardada la imagen.
                    imageurl = getRealPathFromURI(imageUri);
                    Log.i("TAG", "result>=" + bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public String getRealPathFromURI(Uri contentUri) {
        //Para obtener la URL de donde se guarda la imagen
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    private void goTocamera() {
        //busco en la galeria
        values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Photo taken on " + System.currentTimeMillis());
        imageUri = getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(cameraIntent, REQUEST_IMAGE_CAMERA);

    }

    //FTP
    private void ftpUpload(Bitmap bitmap) throws IOException {
        //Nombte de la imagen
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        filenameimagen = imageFileName + ".jpg";
        Log.i("TAG", filenameimagen);

        //bitmap is converted to InputStream
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        //Para aumentar la escala
        //Bitmap imagen=Bitmap.createScaledBitmap(bitmap,3000,4000,true);
        Log.d("TAG-FTP", "Alto " + width + "ancho " + height);
        Log.d("TAG-FTPaa", filenameimagen);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        InputStream fis = new ByteArrayInputStream(baos.toByteArray());


        //Create FTPClient object
        String server = "192.168.0.13";
        String user = "admin";
        String pass = "josejose";

        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(server, 21);
            ftpClient.login(user, pass);

            //ftpClient.changeWorkingDirectory("F:\\ftp\\imagenes");
            Log.d("TAG-FTP", "Conectado");
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(ftpClient.BINARY_FILE_TYPE);
            ftpClient.storeFile(filenameimagen, fis);
            ftpClient.logout();
            ftpClient.disconnect();

            enviarReporte();
        } catch (SocketException e) {
            Log.d("TAG-FTP", "Fallo");
            e.printStackTrace();
        }
    }
    public void enviarReporte()
    {
        Log.d("aa","entre a enviar reporte mas idUser "+idUsuario);
        Log.d("aa","nombre de la imagen "+filenameimagen);
        //Adapto las coodenadas
        Coordenadas coordenadas= new Coordenadas();
        coordenadas.setLatitud(Latitud);
        coordenadas.setLongitud(Longitud);

        List<Coordenadas> listC = new ArrayList<>();
        listC.add(coordenadas);

        //Creo el reporte
        Reportes nuevo= new Reportes();
        nuevo.setIdentificadorUsuario(idUsuario);
        nuevo.setDescripcion(descripcion.getText().toString());
        nuevo.setImagen("ftp://192.168.0.13/"+filenameimagen);
        nuevo.setUbicacion(listC);
        Call<Reportes> nuevoReporte=ApiConnection.obtReportes().crearReport(authHeader,nuevo);
        nuevoReporte.enqueue(new Callback<Reportes>() {
            @Override
            public void onResponse(Call<Reportes> call, Response<Reportes> response) {
                if (response.isSuccessful())
                {
                    registroDialogo();
                }
                else{
                    Log.d("Error-EnviarReporte","error "+response.errorBody());
                    Toast.makeText(NuevaSolicitudActivity.this, response.errorBody().toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Reportes> call, Throwable t) {
                Toast.makeText(NuevaSolicitudActivity.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }
    public void registroDialogo(){
        new AlertDialog.Builder(this)
                .setTitle("Todo Listo!")
                .setMessage("El Reporte ha sido enviado con exito!")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(NuevaSolicitudActivity.this,SolicitudesActivity.class);
                        intent.putExtra("idUsuario", idUsuario);
                        startActivity(intent);
                    }
                })
                .show();
    }
}
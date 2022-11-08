package com.example.grabadora;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.io.InvalidObjectException;

public class MainActivity extends AppCompatActivity {

    private MediaRecorder grabacion;
    private String archivo_salida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, 1000);
        }
    }
    public void recorder (View view) {
        if(grabacion == null){
            archivo_salida = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Grabacion.mp3";
            grabacion = new MediaRecorder();
            grabacion.setAudioSource(MediaRecorder.AudioSource.MIC);
            grabacion.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            grabacion.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            grabacion.setOutputFile(archivo_salida);

            try {
                grabacion.prepare();
                grabacion.start();
            }catch (IOException e) {

            }
            Toast.makeText(getApplicationContext(), "Grabando...", Toast.LENGTH_SHORT).show();
        }else if (grabacion != null){
            grabacion.stop();
            grabacion.release();
            grabacion = null;
            Toast.makeText(getApplicationContext(), "Grabacion finalizada", Toast.LENGTH_SHORT).show();
        }

    }
    public void reproducir (View view) {

        MediaPlayer mediaPlayer = new MediaPlayer();

        try {
            mediaPlayer.setDataSource(archivo_salida);
            mediaPlayer.prepare();
        }catch (IOException e){

        }
        mediaPlayer.start();
        Toast.makeText(getApplicationContext(), "Reproduciendo Audio ", Toast.LENGTH_SHORT).show();
    }
    // metodo para mostrar y ocultar menu
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.overflow, menu);
        return true;
    }

    public boolean onOptionsItemSelected (MenuItem item){

        int id = item.getItemId();

        if(id == R.id.item_1){
            Toast.makeText(this, "Esta es la Opcion 1 ", Toast.LENGTH_SHORT).show();
        } else if(id == R.id.item_2){
            Toast.makeText(this, "Esta es la Opcion 2 ", Toast.LENGTH_SHORT).show();
        } else if(id == R.id.item_3){
            Toast.makeText(this, "Esta es la option 3 ", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.buscar){
            Toast.makeText(this, "Buscando", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.compartir){
            Toast.makeText(this, "Compartir", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);

    }
}
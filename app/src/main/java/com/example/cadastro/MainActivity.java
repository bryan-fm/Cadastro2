package com.example.cadastro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private EditText nome;
    private EditText audio;
    private EditText caminho;
    private AlunoDAO dao;
    private Aluno aluno = null;


    String AudioSavePathInDevice = null;
    MediaRecorder mediaRecorder ;
    Random random ;
    String RandomAudioFileName = "ABCDEFGHIJKLMNOP";
    public static final int RequestPermissionCode = 1;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button par = findViewById(R.id.btParar);
        par.setEnabled(false);

        nome = findViewById(R.id.editNome);
        audio = findViewById(R.id.editAud);
        caminho = findViewById(R.id.editCaminho);
        dao = new AlunoDAO(this);

        Intent it = getIntent();
        if(it.hasExtra("aluno")){
            aluno = (Aluno) it.getSerializableExtra("aluno");
            nome.setText(aluno.getNome().toString());
            audio.setText(aluno.getPathAud().toString());
            caminho.setText(aluno.getCaminho().toString());
        }

        ImageView im = findViewById(R.id.foto);
        File imgfile = new File(caminho.getText().toString());
        Bitmap mybitmap = BitmapFactory.decodeFile(imgfile.getAbsolutePath());
        im.setImageBitmap(mybitmap);
    }

    public void salvar (View view){

        if (aluno == null) {
            Aluno a = new Aluno();
            a.setNome(nome.getText().toString());
            a.setPathaud(audio.getText().toString());
            a.setCaminho(caminho.getText().toString());
            long id = dao.inserir(a);
            Toast.makeText(this, "Aluno inserido com id: " + id, Toast.LENGTH_SHORT).show();
        }
        else
        {
            aluno.setNome(nome.getText().toString());
            aluno.setPathaud(audio.getText().toString());
            aluno.setCaminho(caminho.getText().toString());
            dao.atualizar(aluno);
            Toast.makeText(this, "Aluno Atualizado", Toast.LENGTH_SHORT).show();
        }
    }

    public static final int MY_CAMERA_REQUEST_CODE = 100;

    public void checkPermission(View view)
    {

        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
        }
        else
        {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent,2);
        }

    }

    public void gravar(View view)
    {
        if(!isRecordAudioPermissionGranted())
        {
            Toast.makeText(getApplicationContext(), "Need to request permission", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(), "No need to request permission", Toast.LENGTH_SHORT).show();
        }

        if(checkPermission()) {

            AudioSavePathInDevice =
                    Environment.getExternalStorageDirectory().getAbsolutePath() + "/Demo/" +
                            System.currentTimeMillis() + "AudioRecording.3gp";

            MediaRecorderReady();

            try {
                mediaRecorder.prepare();
                mediaRecorder.start();
            } catch (IllegalStateException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            Button btaud = findViewById(R.id.btProc);
            btaud.setEnabled(false);

            Button btpara = findViewById(R.id.btParar);
            btpara.setEnabled(true);

            Toast.makeText(MainActivity.this, "Recording started",
                    Toast.LENGTH_LONG).show();
        }

    }

    public void parargrava(View view)
    {
        mediaRecorder.stop();
        Button btpara = findViewById(R.id.btParar);
        btpara.setEnabled(false);

        Button btaud = findViewById(R.id.btProc);
        btaud.setEnabled(true);


        Toast.makeText(MainActivity.this, "Recording Completed",
                Toast.LENGTH_LONG).show();

        EditText edtt = findViewById(R.id.editAud);
        edtt.setText(AudioSavePathInDevice.toString());
    }



    OutputStream outputStream;

        public boolean checkPermission() {
            int result = ContextCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int result1 = ContextCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.RECORD_AUDIO);
            return result == PackageManager.PERMISSION_GRANTED &&
            result1 == PackageManager.PERMISSION_GRANTED;
        }

    public void MediaRecorderReady(){
        mediaRecorder=new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(AudioSavePathInDevice);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);
        Bitmap bit = (Bitmap)data.getExtras().get("data");
        ImageView img = findViewById(R.id.foto);

        File filepath = Environment.getExternalStorageDirectory();
        File dir = new File(filepath.getAbsolutePath() + "/Demo");
        dir.mkdir();
        File file = new File(dir, System.currentTimeMillis()+".jpg");

        String caminho = file.toString();
        try
        {
           outputStream  = new FileOutputStream(file);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        bit.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        Toast.makeText(getApplicationContext(),"Imagem Salva" + caminho,Toast.LENGTH_SHORT).show();
        try {
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File imgfile = new File(caminho);
        Bitmap mybitmap = BitmapFactory.decodeFile(imgfile.getAbsolutePath());
        img.setImageBitmap(mybitmap);
        EditText tx = findViewById(R.id.editCaminho);
        tx.setText(file.toString());



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    private boolean isRecordAudioPermissionGranted()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) ==
                    PackageManager.PERMISSION_GRANTED) {
                // put your code for Version>=Marshmallow
                return true;
            } else {
                if (shouldShowRequestPermissionRationale(Manifest.permission.RECORD_AUDIO)) {
                    Toast.makeText(this,
                            "App required access to audio", Toast.LENGTH_SHORT).show();
                }
                requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO
                },325);
                return false;
            }

        } else {
            // put your code for Version < Marshmallow
            return true;
        }
    }

    public void ouvir(View view)
    {
        EditText edtaud = findViewById(R.id.editAud);
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(edtaud.getText().toString());
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mediaPlayer.start();
        Toast.makeText(MainActivity.this, "Recording Playing",
                Toast.LENGTH_LONG).show();

    }


}

package com.example.cadastro;

import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

public class MemoActivity extends AppCompatActivity {

    Context context = this;
    MediaPlayer mp;
    ImageView curView = null;
    private int countPair = 0;
    final int[] drawable = new int []{R.drawable.aviao, R.drawable.bola, R.drawable.casa, R.drawable.dado,
            R.drawable.elefante, R.drawable.faca, R.drawable.gato, R.drawable.hospital,R.drawable.igreja,
            R.drawable.janela, R.drawable.limao,R.drawable.maca,R.drawable.a, R.drawable.b, R.drawable.c,
            R.drawable.d, R.drawable.e, R.drawable.f, R.drawable.g, R.drawable.h,R.drawable.i,
            R.drawable.j, R.drawable.l,R.drawable.m};

    int[] pos = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23};
    int currentPos = -1;




    @Override
    protected void onCreate(Bundle savedInstance)
    {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_memo);

        GridView gridView = (GridView)findViewById(R.id.gridView);
        ImageAdapter imageAdapter = new ImageAdapter(this);
        gridView.setAdapter(imageAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(currentPos < 0)
                {
                    currentPos = position;
                    curView = (ImageView)view;
                    ((ImageView)view).setImageResource(drawable[pos[position]]);
                }

                else

                {
                    if(pos[currentPos] - pos[position] != 12)
                    {
                        mp = MediaPlayer.create(context, R.raw.wrong);
                        mp.start();
                        curView.setImageResource(R.drawable.cloud);
                        Toast.makeText(getApplicationContext(),"ERRADO",Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
                        mp = MediaPlayer.create(context, R.raw.correct);
                        mp.start();
                        ((ImageView)view).setImageResource(drawable[pos[position]]);
                        countPair++;


                        if(countPair == 12) {
                            Toast.makeText(getApplicationContext(), "VOCE GANHOU", Toast.LENGTH_SHORT).show();
                        }
                    }
                    currentPos = -1;
                }
            }
        });
    }
}

package com.example.cadastro;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class Escolherletra extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.escolher);
    }



    public void filtro(View view)
    {
        String exbt;
        Intent i = new Intent(Escolherletra.this,ListarActivity.class);
        Button b = (Button)view;
        exbt = b.getText().toString();
        startActivity(i);
        SharedPreferences settings = getSharedPreferences("Letra", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("nome", exbt.toString()+ "%");
        editor.apply();

        Toast toast = Toast.makeText(this, exbt,Toast.LENGTH_SHORT);
        //toast.show();
    }

    public void memo(View view)
    {
        Intent i = new Intent(Escolherletra.this, MemoActivity.class);
        startActivity(i);
    }


}

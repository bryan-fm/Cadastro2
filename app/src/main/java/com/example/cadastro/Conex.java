package com.example.cadastro;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Conex extends SQLiteOpenHelper {

    private static final String name = "Aluno.db";
    private static final int version = 1;


    public Conex(@Nullable Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("Create Table Aluno(id integer primary key autoincrement, " +
                "nome varchar(50), cpf varchar(50), caminho varchar(200))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

package com.example.cadastro;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class AlunoDAO extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    private Conex conexao;
    private SQLiteDatabase banco;

    public  AlunoDAO(Context context){

        conexao = new Conex(context);
        banco = conexao.getWritableDatabase();
    }

    public long inserir(Aluno aluno){
        ContentValues values = new ContentValues();
        values.put("nome", aluno.getNome());
        values.put("audio", aluno.getPathAud());
        values.put("caminho", aluno.getCaminho());

        return banco.insert("Aluno", null, values);
    }

    public List<Aluno> obterAll()
    {
        List<Aluno> alunos = new ArrayList<>();
        Cursor cursor = banco.query("Aluno", new String[]{"id", "nome", "audio", "caminho"}, null, null, null, null, null);

        while (cursor.moveToNext()) {
            Aluno a = new Aluno();
            a.setId(cursor.getInt(0));
            a.setNome(cursor.getString(1));
            a.setPathaud(cursor.getString(2));
            a.setCaminho(cursor.getString(3));
            alunos.add(a);
        }

        return alunos;
    }


    public void excluir(Aluno a){
        banco.delete("aluno", "id = ?", new String[]{a.getId().toString()});
    }

    public void atualizar(Aluno aluno){
        ContentValues values = new ContentValues();
        values.put("nome", aluno.getNome());
        values.put("audio", aluno.getPathAud());
        values.put("caminho", aluno.getCaminho());

        banco.update("aluno", values,"id = ?", new String[]{aluno.getId().toString()});
    }

}

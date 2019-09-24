package com.example.cadastro;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class AlunoDAO {

    private Conex conexao;
    private SQLiteDatabase banco;

    public  AlunoDAO(Context context){

        conexao = new Conex(context);
        banco = conexao.getWritableDatabase();
    }

    public long inserir(Aluno aluno){
        ContentValues values = new ContentValues();
        values.put("nome", aluno.getNome());
        values.put("cpf", aluno.getCpf());
        values.put("caminho", aluno.getCaminho());

        return banco.insert("Aluno", null, values);
    }

    public List<Aluno> obterAll()
    {
        List<Aluno> alunos = new ArrayList<>();
        Cursor cursor = banco.query("Aluno", new String[]{"id", "nome", "cpf", "caminho"}, null, null, null, null, null);

        while (cursor.moveToNext()) {
            Aluno a = new Aluno();
            a.setId(cursor.getInt(0));
            a.setNome(cursor.getString(1));
            a.setCpf(cursor.getString(2));
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
        values.put("cpf", aluno.getCpf());
        values.put("caminho", aluno.getCaminho());

        banco.update("aluno", values,"id = ?", new String[]{aluno.getId().toString()});
    }
}

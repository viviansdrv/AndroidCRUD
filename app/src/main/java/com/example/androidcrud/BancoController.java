package com.example.androidcrud;

import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;

public class BancoController {

    private CriaBanco banco;
    private SQLiteDatabase db;

    public BancoController(Context context) {
        banco = new CriaBanco(context);
    }

    public String insereDado(String titulo, String autor, String editora) {
        ContentValues valores = new ContentValues();
        valores.put(banco.TITULO, titulo);
        valores.put(banco.AUTOR, autor);
        valores.put(banco.EDITORA, editora);
        db = banco.getWritableDatabase();
        long resultado = db.insertOrThrow(banco.TABELA, null, valores);
        db.close();
        return resultado == -1 ? "Erro ao inserir registro" : "Registro inserido com sucesso";
    }

    public Cursor carregaDados() {
        db = banco.getReadableDatabase();
        String[] campos = {banco.ID, banco.TITULO};
        Cursor cursor = db.query(banco.TABELA, campos, null, null, null,
       null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor carregaDadoById(int id) {
        db = banco.getReadableDatabase();
        String[] campos = {banco.ID, banco.TITULO, banco.AUTOR, banco.EDITORA};
        String where = CriaBanco.ID + "=" + id;
        Cursor cursor = db.query(CriaBanco.TABELA, campos, where, null, null,
       null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public void alteraRegistro(int id, String titulo, String autor, String editora) {
        db = banco.getWritableDatabase();
        String where = CriaBanco.ID + "=" + id;
        ContentValues valores = new ContentValues();
        valores.put(banco.TITULO, titulo);
        valores.put(banco.AUTOR, autor);
        valores.put(banco.EDITORA, editora);
        db.update(CriaBanco.TABELA, valores, where, null);
    }

    public void deletaRegistro(int id) {
        db = banco.getReadableDatabase();
        String where = CriaBanco.ID + "=" + id;
        db.delete(CriaBanco.TABELA, where, null);
    }

}
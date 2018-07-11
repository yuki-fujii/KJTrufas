package com.br.kjtrufas.sql;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import entidades.Vendedor;

public class VendedorDAO
{
    private SQLiteDatabase conn;

    public VendedorDAO (SQLiteDatabase conn)
    {
        this.conn = conn;
    }

    private ContentValues preencherContentValues(Vendedor vendedor)
    {
        ContentValues values = new ContentValues();

        //values.put("ID_VENDEDOR",vendedor.getId());
        values.put("NOME",vendedor.getNome());
        values.put("LOGIN",vendedor.getLogin());
        values.put("SENHA",vendedor.getSenha());
        values.put("CONF_SENHA",vendedor.getConfSenha());

        return values;
    }

    public void upsert(Vendedor vendedor)
    {
        Log.i("AUX","Vendedor: "+vendedor.getLogin());

        Cursor cursor = conn.query("VENDEDOR",null,"LOGIN = ?",new String[]{vendedor.getLogin()},null,null,null);

        Log.i("AUX","QTDE: "+cursor.getCount());

        int qtdVendedor = cursor.getCount();

        if(qtdVendedor==0)
            conn.insertOrThrow("VENDEDOR", null, preencherContentValues(vendedor));
        else
            conn.update("VENDEDOR",preencherContentValues(vendedor),"LOGIN = ?", new String[]{vendedor.getLogin()});
    }

}

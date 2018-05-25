package com.br.kjtrufas.sql;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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

        values.put("ID_VENDEDOR",vendedor.getId());
        values.put("NOME",vendedor.getNome());
        values.put("LOGIN",vendedor.getLogin());
        values.put("SENHA",vendedor.getSenha());

        return values;
    }

    public void insert(Vendedor vendedor)
    {
        Cursor cursor = conn.query("VENDEDOR",null,null,null,null,null,null);

        int qtdVendedor = cursor.getCount();

        if(qtdVendedor==0)
            conn.insertOrThrow("VENDEDOR", null, preencherContentValues(vendedor));
        else
            conn.update("PACIENTE",preencherContentValues(vendedor),null,null);
    }

}

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

        if(!hasVendedor(vendedor))
            conn.insertOrThrow("VENDEDOR", null, preencherContentValues(vendedor));
        else
            conn.update("VENDEDOR",preencherContentValues(vendedor),"LOGIN = ?", new String[]{vendedor.getLogin()});
    }

    public boolean hasVendedor(Vendedor vendedor)
    {
        Cursor cursor = conn.query("VENDEDOR",null,"LOGIN = ?",new String[]{vendedor.getLogin()},null,null,null);

        if (cursor.getCount()==0)
            return false;
        else
            return true;
    }

    public void delete(String login, String senha)
    {
        conn.delete("VENDEDOR","LOGIN = ? AND SENHA = ?",new String[]{login,senha});
    }

    public Vendedor getVendedor(String login, String senha) {

        Vendedor retorno = null;

        Cursor cursor = conn.query("VENDEDOR", null, "LOGIN = ? AND SENHA = ?", new String[]{login, senha}, null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            retorno = new Vendedor();

            retorno.setId(cursor.getString(cursor.getColumnIndex("ID_VENDEDOR")));
            retorno.setNome(cursor.getString(cursor.getColumnIndex("NOME")));
            retorno.setLogin(cursor.getString(cursor.getColumnIndex("LOGIN")));
            retorno.setSenha(cursor.getString(cursor.getColumnIndex("SENHA")));
            retorno.setConfSenha(cursor.getString(cursor.getColumnIndex("CONF_SENHA")));

            Log.i("retorno", retorno.getNome());

        }

        return retorno;
    }

}

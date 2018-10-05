package com.br.kjtrufas.sql;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.br.kjtrufas.entidades.Vendedor;

public class VendedorDAO
{
    private static ContentValues preencherContentValues(Vendedor vendedor)
    {
        ContentValues values = new ContentValues();

        values.put("ID_VENDEDOR",vendedor.getId());
        values.put("NOME",vendedor.getNome());
        values.put("LOGIN",vendedor.getLogin());
        values.put("SENHA",vendedor.getSenha());
        values.put("CONF_SENHA",vendedor.getConfSenha());
        values.put("ID_MOBILE_COMANDA",vendedor.getIdMobileComanda());
        values.put("ID_MOBILE_VENDA",vendedor.getIdMobileVenda());

        return values;
    }

    public static void upsert(Vendedor vendedor, SQLiteDatabase conn)
    {
        Log.i("vendedor", vendedor.getNome());

        if(!hasVendedor(vendedor,conn))
            conn.insertOrThrow("VENDEDOR", null, preencherContentValues(vendedor));
        else
            conn.update("VENDEDOR",preencherContentValues(vendedor),"LOGIN = ?", new String[]{vendedor.getLogin()});
    }

    public static boolean hasVendedor(Vendedor vendedor, SQLiteDatabase conn)
    {
        Cursor cursor = conn.query("VENDEDOR",null,"LOGIN = ?",new String[]{vendedor.getLogin()},null,null,null);

        if (cursor.getCount()==0)
            return false;
        else
            return true;
    }

    public static void delete(String login, String senha, SQLiteDatabase conn)
    {
        conn.delete("VENDEDOR","LOGIN = ? AND SENHA = ?",new String[]{login,senha});
    }

    public static Vendedor getVendedor(SQLiteDatabase conn) {

        Vendedor retorno = null;

        Cursor cursor = conn.query("VENDEDOR", null, null, null, null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            retorno = new Vendedor();

            retorno.setId(cursor.getString(cursor.getColumnIndex("ID_VENDEDOR")));
            retorno.setNome(cursor.getString(cursor.getColumnIndex("NOME")));
            retorno.setLogin(cursor.getString(cursor.getColumnIndex("LOGIN")));
            retorno.setSenha(cursor.getString(cursor.getColumnIndex("SENHA")));
            retorno.setConfSenha(cursor.getString(cursor.getColumnIndex("CONF_SENHA")));
            retorno.setIdMobileComanda(cursor.getInt(cursor.getColumnIndex("ID_MOBILE_COMANDA")));
            retorno.setIdMobileVenda(cursor.getInt(cursor.getColumnIndex("ID_MOBILE_VENDA")));
        }

        return retorno;
    }


    public static int getIdComanda(SQLiteDatabase conn) {

        Integer retorno = null;

        Cursor cursor = conn.query("VENDEDOR", null, null, null, null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            retorno = cursor.getInt(cursor.getColumnIndex("ID_MOBILE_COMANDA"));
        }

        retorno++;

        Vendedor v = getVendedor(conn);
        v.setIdMobileComanda(retorno);
        VendedorDAO.upsert(v,conn);
        return retorno;
    }

    public static int getIdVenda(SQLiteDatabase conn) {

        Integer retorno = null;

        Cursor cursor = conn.query("VENDEDOR", null, null, null, null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            retorno = cursor.getInt(cursor.getColumnIndex("ID_MOBILE_VENDA"));
        }

        retorno++;

        Vendedor v = getVendedor(conn);
        v.setIdMobileVenda(retorno);
        VendedorDAO.upsert(v,conn);
        return retorno;
    }

}

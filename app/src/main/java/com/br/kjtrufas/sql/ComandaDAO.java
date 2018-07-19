package com.br.kjtrufas.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.br.kjtrufas.entidades.Comanda;

public class ComandaDAO {

    private static ContentValues preencherContentValues(Comanda comanda)
    {
        ContentValues values = new ContentValues();

        values.put("ID_VENDEDOR",comanda.getIdVendedor());
        values.put("NOME",comanda.getNome());

        return values;
    }

    public static void upsert(Comanda comanda,SQLiteDatabase conn)
    {
        Log.i("Inserindo",comanda.getNome());
        Log.i("CONN",String.valueOf(conn));

        if(!hasComanda(comanda, conn))
        {
            Log.i("OPE","INSERT");
            conn.insertOrThrow("COMANDA", null, preencherContentValues(comanda));
        }
        else
        {
            Log.i("OPE","UPDATE");
            conn.update("COMANDA",preencherContentValues(comanda),"NOME = ? AND ID_VENDEDOR = ?", new String[]{comanda.getNome(),comanda.getIdVendedor()});
        }
    }

    public static boolean hasComanda(Comanda comanda, SQLiteDatabase conn)
    {
        Cursor cursor = conn.query("COMANDA",null,"NOME = ? AND ID_VENDEDOR = ?",new String[]{comanda.getNome(),comanda.getIdVendedor()},null,null,null);

        if (cursor.getCount()==0)
            return false;
        else
            return true;
    }

    public static void delete(String nome, String idVendedor, SQLiteDatabase conn)
    {
        conn.delete("COMANDA","NOME = ? AND ID_VENDEDOR = ?",new String[]{nome,idVendedor});
    }

    public static ArrayAdapter<String> getComanda(Context context,SQLiteDatabase conn) {

        Log.i("ID Vendedor",VendedorDAO.getVendedor(conn).getId());
        ArrayAdapter<String> retorno =  new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1);

        Cursor cursor = conn.query("COMANDA", null, "ID_VENDEDOR = ?", new String[]{VendedorDAO.getVendedor(conn).getId()}, null, null, null);

        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();

            do {
                Log.i("Comanda",cursor.getString(cursor.getColumnIndex("NOME")));
                retorno.add(cursor.getString(cursor.getColumnIndex("NOME")));
            }while(cursor.moveToNext());
        }

        return retorno;
    }
}

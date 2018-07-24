package com.br.kjtrufas.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.br.kjtrufas.entidades.Sabor;

public class SaborDAO {

    private static ContentValues preencherContentValues(Sabor sabor)
    {
        ContentValues values = new ContentValues();

        values.put("NOME",sabor.getNome());
        values.put("ATIVO",sabor.getAtivo());

        return values;
    }

    public static void upsert(Sabor sabor,SQLiteDatabase conn)
    {
        if(!hasSabor(sabor, conn))
            conn.insertOrThrow("SABOR", null, preencherContentValues(sabor));
        else
            conn.update("SABOR",preencherContentValues(sabor),"NOME = ?", new String[]{sabor.getNome()});
    }

    public static boolean hasSabor(Sabor sabor, SQLiteDatabase conn)
    {
        Cursor cursor = conn.query("SABOR",null,"NOME = ?",new String[]{sabor.getNome()},null,null,null);

        if (cursor.getCount()==0)
            return false;
        else
            return true;
    }

    public static void delete(String nome, SQLiteDatabase conn)
    {
        conn.delete("SABOR","NOME = ?",new String[]{nome});
    }

    public static ArrayAdapter<Sabor> getSabor(Context context, SQLiteDatabase conn, int apenasAtivos) {

        ArrayAdapter<Sabor> retorno =  new ArrayAdapter<Sabor>(context,android.R.layout.simple_list_item_1);

        Cursor cursor = conn.query("SABOR", null, null, null, null, null, "Nome");

        if (cursor.getCount() > 0)
        {
            retorno.add(new Sabor("--Nenhum--",0));
            cursor.moveToFirst();

            do {
                Sabor aux = new Sabor();
                aux.setId(cursor.getString(cursor.getColumnIndex("ID_SABOR")));
                aux.setNome(cursor.getString(cursor.getColumnIndex("NOME")));
                aux.setAtivo(cursor.getInt(cursor.getColumnIndex("ATIVO")));

                if(apenasAtivos == 1 && aux.getAtivo()==1)
                    retorno.add(aux);
                if(apenasAtivos == 0)
                    retorno.add(aux);

            }while(cursor.moveToNext());
        }

        return retorno;
    }
}

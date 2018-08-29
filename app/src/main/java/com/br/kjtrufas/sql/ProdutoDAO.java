package com.br.kjtrufas.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.br.kjtrufas.entidades.Produto;

public class ProdutoDAO {

    private static ContentValues preencherContentValues(Produto produto)
    {
        ContentValues values = new ContentValues();

        values.put("NOME",produto.getNome());
        values.put("PRECO",produto.getPreco());
        values.put("ATIVO",produto.getAtivo());
        values.put("ID_PRODUTO",produto.getId());

        return values;
    }

    public static void upsert(Produto produto,SQLiteDatabase conn)
    {
        Log.i("Upsert Produto",produto.getNome());
        Log.i("CONN",String.valueOf(conn));

        if(!hasProduto(produto, conn))
        {
            Log.i("OPE","INSERT");
            conn.insertOrThrow("PRODUTO", null, preencherContentValues(produto));
        }
        else
        {
            Log.i("OPE","UPDATE");
            conn.update("PRODUTO",preencherContentValues(produto),"NOME = ?", new String[]{produto.getNome()});
        }
    }

    public static boolean hasProduto(Produto produto, SQLiteDatabase conn)
    {
        Cursor cursor = conn.query("PRODUTO",null,"NOME = ?",new String[]{produto.getNome()},null,null,null);

        if (cursor.getCount()==0)
            return false;
        else
            return true;
    }

    public static void delete(String nome, SQLiteDatabase conn)
    {
        conn.delete("PRODUTO","NOME = ?",new String[]{nome});
    }

    public static ArrayAdapter<Produto> getProduto(Context context, SQLiteDatabase conn,int apenasAtivos) {

        ArrayAdapter<Produto> retorno =  new ArrayAdapter<Produto>(context,android.R.layout.simple_list_item_1);

        Cursor cursor = conn.query("PRODUTO", null, null, null, null, null, "NOME");

        if (cursor.getCount() > 0)
        {
            retorno.add(new Produto("--Nenhum--",null,0));
            cursor.moveToFirst();

            do {
                Produto aux = new Produto();
                aux.setId(cursor.getString(cursor.getColumnIndex("ID_PRODUTO")));
                aux.setNome(cursor.getString(cursor.getColumnIndex("NOME")));
                aux.setPreco(cursor.getDouble(cursor.getColumnIndex("PRECO")));
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

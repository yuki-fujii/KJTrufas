package com.br.kjtrufas.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.br.kjtrufas.entidades.ProdutoDisponivel;

public class ProdutoDisponivelDAO {

    private static ContentValues preencherContentValues(ProdutoDisponivel produtoDisponivel)
    {
        ContentValues values = new ContentValues();

        values.put("ID_PRODUTO_DISPONIVEL",produtoDisponivel.getId());
        values.put("SABOR",produtoDisponivel.getSabor());
        values.put("PRODUTO",produtoDisponivel.getProduto());
        values.put("ID_VENDEDOR",produtoDisponivel.getIdVendedor());
        values.put("QUANTIDADE",produtoDisponivel.getQuantidade());

        return values;
    }

    public static void upsert(ProdutoDisponivel produtoDisponivel,SQLiteDatabase conn)
    {

        if(!hasProduto(produtoDisponivel, conn))
            conn.insertOrThrow("PRODUTO_DISPONIVEL", null, preencherContentValues(produtoDisponivel));
        else
            conn.update("PRODUTO_DISPONIVEL",preencherContentValues(produtoDisponivel),"ID_VENDEDOR = ? AND PRODUTO = ? AND SABOR = ?",
                    new String[]{produtoDisponivel.getIdVendedor(),produtoDisponivel.getProduto(),produtoDisponivel.getSabor()});
    }

    public static boolean hasProduto(ProdutoDisponivel produtoDisponivel, SQLiteDatabase conn)
    {
        Cursor cursor = conn.query("PRODUTO_DISPONIVEL",null,"ID_VENDEDOR = ? AND PRODUTO = ? AND SABOR = ?",
                new String[]{produtoDisponivel.getIdVendedor(),produtoDisponivel.getProduto(),produtoDisponivel.getSabor()},null,null,null);

        if (cursor.getCount()==0)
            return false;
        else
            return true;
    }

    public static void delete(String produto, String sabor, SQLiteDatabase conn)
    {
        conn.delete("PRODUTO_DISPONIVEL","ID_VENDEDOR = ? AND PRODUTO = ? AND SABOR = ?",new String[]{VendedorDAO.getVendedor(conn).getId(),produto,sabor});
    }

    public static ArrayAdapter<ProdutoDisponivel> getProdutoDisponivel(Context context, SQLiteDatabase conn) {

        ArrayAdapter<ProdutoDisponivel> retorno =  new ArrayAdapter<ProdutoDisponivel>(context,android.R.layout.simple_list_item_1);

        Cursor cursor = conn.query("PRODUTO_DISPONIVEL", null, "QUANTIDADE > ?", new String[]{"0"}, null, null, "SABOR, PRODUTO");

        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();

            do {
                ProdutoDisponivel aux = new ProdutoDisponivel();
                aux.setId(cursor.getString(cursor.getColumnIndex("ID_PRODUTO_DISPONIVEL")));
                aux.setProduto(cursor.getString(cursor.getColumnIndex("PRODUTO")));
                aux.setSabor(cursor.getString(cursor.getColumnIndex("SABOR")));
                aux.setQuantidade(cursor.getInt(cursor.getColumnIndex("QUANTIDADE")));
                aux.setIdVendedor(cursor.getString(cursor.getColumnIndex("ID_VENDEDOR")));

                retorno.add(aux);

            }while(cursor.moveToNext());
        }

        return retorno;
    }
}


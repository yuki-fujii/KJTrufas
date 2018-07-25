package com.br.kjtrufas.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.ArrayAdapter;
import com.br.kjtrufas.entidades.Venda;

public class VendaDAO {

    private static ContentValues preencherContentValues(Venda venda)
    {
        ContentValues values = new ContentValues();

        values.put("ID_COMANDA",Integer.valueOf(venda.getIdComanda()));
        values.put("PRODUTO",venda.getProduto());
        values.put("SABOR",venda.getSabor());
        values.put("ID_VENDEDOR",Integer.valueOf(venda.getIdVendedor()));
        values.put("QUANTIDADE",venda.getQuantidade());
        values.put("ACRESCIMO",venda.getAcrescimo());
        values.put("DESCONTO",venda.getDesconto());
        values.put("VALOR_TOTAL",venda.getValorTotal());
        values.put("DATA_VENDA",venda.getDataVenda());
        values.put("DESCRICAO",venda.getDescricao());
        values.put("PAGO",venda.getPago());

        return values;
    }

    public static void upsert(Venda venda,SQLiteDatabase conn)
    {
        if(!hasVenda(venda, conn))
            conn.insertOrThrow("VENDA", null, preencherContentValues(venda));
        else
            conn.update("VENDA",preencherContentValues(venda),"ID_VENDA = ?", new String[]{venda.getId()});
    }

    public static boolean hasVenda(Venda venda, SQLiteDatabase conn)
    {
        Cursor cursor = conn.query("VENDA",null,"ID_VENDA = ?",new String[]{venda.getId()},null,null,null);

        if (cursor.getCount()==0)
            return false;
        else
            return true;
    }

    public static void delete(String id, SQLiteDatabase conn)
    {
        conn.delete("VENDA","ID_VENDA = ?",new String[]{id});
    }

    public static ArrayAdapter<Venda> getVendas(Context context, String idComanda, SQLiteDatabase conn) {

        Cursor cursor;
        ArrayAdapter<Venda> retorno =  new ArrayAdapter<Venda>(context,android.R.layout.simple_list_item_1);

        if(idComanda!=null && !(idComanda.equals("")))
            cursor = conn.query("VENDA", null, "ID_COMANDA = ? AND ID_VENDEDOR = ? AND PAGO = ?", new String[]{idComanda, VendedorDAO.getVendedor(conn).getId(), "0"}, null, null, "DATA_VENDA  DESC");
        else
            cursor = conn.query("VENDA", null, "ID_VENDEDOR = ? AND PAGO = ?", new String[]{VendedorDAO.getVendedor(conn).getId(), "0"}, null, null, "DATA_VENDA  DESC");

        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();

            do {
                Venda aux = new Venda();
                aux.setId(String.valueOf(cursor.getInt(cursor.getColumnIndex("ID_VENDA"))));
                aux.setIdComanda(String.valueOf(cursor.getInt(cursor.getColumnIndex("ID_COMANDA"))));
                aux.setProduto(cursor.getString(cursor.getColumnIndex("PRODUTO")));
                aux.setSabor(cursor.getString(cursor.getColumnIndex("SABOR")));
                aux.setIdVendedor(String.valueOf(cursor.getInt(cursor.getColumnIndex("ID_VENDEDOR"))));
                aux.setQuantidade(cursor.getInt(cursor.getColumnIndex("QUANTIDADE")));
                aux.setAcrescimo(cursor.getDouble(cursor.getColumnIndex("ACRESCIMO")));
                aux.setDesconto(cursor.getDouble(cursor.getColumnIndex("DESCONTO")));
                aux.setValorTotal(cursor.getDouble(cursor.getColumnIndex("VALOR_TOTAL")));
                aux.setDataVenda(cursor.getString(cursor.getColumnIndex("DATA_VENDA")));
                aux.setDescricao(cursor.getString(cursor.getColumnIndex("DESCRICAO")));
                aux.setPago(cursor.getInt(cursor.getColumnIndex("PAGO")));

                retorno.add(aux);

            }while(cursor.moveToNext());
        }

        return retorno;
    }

}
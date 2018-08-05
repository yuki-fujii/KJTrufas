package com.br.kjtrufas.sql;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.br.kjtrufas.entidades.Venda;
import com.br.kjtrufas.entidades.Vendedor;

import java.util.ArrayList;

public class EnviarRegistroDAO
{
    public static  ArrayList<Venda> getVendas(SQLiteDatabase conn)
    {
        //if(existe produto para integrar?)
        //{
        Cursor cursor = conn.query("VENDA", null, "ID_VENDEDOR = ?", new String[]{VendedorDAO.getVendedor(conn).getId()}, null, null, null);

        ArrayList<Venda> retorno = new ArrayList<Venda>();

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
        //}
    }
}

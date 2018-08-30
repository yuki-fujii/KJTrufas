package com.br.kjtrufas.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.br.kjtrufas.entidades.Comanda;
import com.br.kjtrufas.entidades.Venda;
import com.br.kjtrufas.suporte.DadosVendas;

import java.text.NumberFormat;
import java.util.ArrayList;

public class VendaDAO {

    private static ContentValues preencherContentValues(Venda venda)
    {
        ContentValues values = new ContentValues();

        if(venda.getId()!=null)
        values.put("ID_VENDA",Integer.valueOf(venda.getId()));
        values.put("ID_COMANDA",Integer.valueOf(venda.getIdComanda()));
        values.put("PRODUTO",venda.getProduto());
        values.put("SABOR",venda.getSabor());
        values.put("ID_VENDEDOR",venda.getIdVendedor());
        values.put("QUANTIDADE",venda.getQuantidade());
        values.put("ACRESCIMO",venda.getAcrescimo());
        values.put("DESCONTO",venda.getDesconto());
        values.put("VALOR_TOTAL",venda.getValorTotal());
        values.put("DATA_VENDA",venda.getDataVenda());
        values.put("DESCRICAO",venda.getDescricao());
        values.put("PAGO",venda.getPago());
        values.put("INTEGRAR",venda.getIntegrar());
        values.put("ID_SALESFORCE",venda.getIdSalesforce());

        return values;
    }

    public static void upsert(Venda venda,SQLiteDatabase conn)
    {
        if(!hasVenda(venda, conn))
            conn.insertOrThrow("VENDA", null, preencherContentValues(venda));
        else
            conn.update("VENDA", preencherContentValues(venda), "ID_VENDA = ?", new String[]{venda.getId()});
    }

    public static boolean hasVenda(Venda venda, SQLiteDatabase conn)
    {
        if(venda.getId()==null)
            return false;

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

    public static ArrayList<Venda> getVendasNaoPagas(Comanda comanda, SQLiteDatabase conn)
    {
        Cursor cursor = conn.query("VENDA", null, "ID_COMANDA = ? AND ID_VENDEDOR = ? AND PAGO = ?", new String[]{comanda.getId(), comanda.getIdVendedor(), "0"}, null, null, null);

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
                aux.setIdVendedor(cursor.getString(cursor.getColumnIndex("ID_VENDEDOR")));
                aux.setQuantidade(cursor.getInt(cursor.getColumnIndex("QUANTIDADE")));
                aux.setAcrescimo(cursor.getDouble(cursor.getColumnIndex("ACRESCIMO")));
                aux.setDesconto(cursor.getDouble(cursor.getColumnIndex("DESCONTO")));
                aux.setValorTotal(cursor.getDouble(cursor.getColumnIndex("VALOR_TOTAL")));
                aux.setDataVenda(cursor.getString(cursor.getColumnIndex("DATA_VENDA")));
                aux.setDescricao(cursor.getString(cursor.getColumnIndex("DESCRICAO")));
                aux.setPago(cursor.getInt(cursor.getColumnIndex("PAGO")));
                aux.setIntegrar(cursor.getInt(cursor.getColumnIndex("INTEGRAR")));
                aux.setIdSalesforce(cursor.getString(cursor.getColumnIndex("ID_SALESFORCE")));

                retorno.add(aux);

            }while(cursor.moveToNext());
        }

        return retorno;
    }

    public static DadosVendas getVendasNaoPagas(Context context, String idComanda, SQLiteDatabase conn) {

        Cursor cursor;

        ArrayList<String> data = new ArrayList<String>();
        ArrayList<String> descricao = new ArrayList<String>();
        ArrayList<String> valor = new ArrayList<String>();

        cursor = conn.query("VENDA", null, "ID_COMANDA = ? AND ID_VENDEDOR = ? AND PAGO = ?", new String[]{idComanda, VendedorDAO.getVendedor(conn).getId(), "0"}, null, null, "DATA_VENDA  DESC");

        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();

            do {
                valor.add(String.valueOf(NumberFormat.getCurrencyInstance().format(cursor.getDouble(cursor.getColumnIndex("VALOR_TOTAL")))));
                data.add(cursor.getString(cursor.getColumnIndex("DATA_VENDA")));
                descricao.add(cursor.getString(cursor.getColumnIndex("DESCRICAO")));

            }while(cursor.moveToNext());
        }

        DadosVendas retorno =  new DadosVendas(data.size());
        retorno.setData(data.toArray(retorno.getData()));
        retorno.setDescricao(descricao.toArray(retorno.getDescricao()));
        retorno.setValor(valor.toArray(retorno.getValor()));

        return retorno;
    }

    public static ArrayList<Venda> getVendasParaIntegrar(SQLiteDatabase conn)
    {
        Cursor cursor = conn.query("VENDA", null, "INTEGRAR = ? AND ID_VENDEDOR = ?", new String[]{"1", VendedorDAO.getVendedor(conn).getId()}, null, null, null);

        ArrayList<Venda> retorno = new ArrayList<Venda>();

        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();

            do {
                Venda aux = new Venda();
                aux.setId(String.valueOf(cursor.getInt(cursor.getColumnIndex("ID_VENDA"))));
                aux.setIdVendedor(cursor.getString(cursor.getColumnIndex("ID_VENDEDOR")));
                aux.setIdComanda(String.valueOf(cursor.getInt(cursor.getColumnIndex("ID_COMANDA"))));
                aux.setProduto(cursor.getString(cursor.getColumnIndex("PRODUTO")));
                aux.setSabor(cursor.getString(cursor.getColumnIndex("SABOR")));
                aux.setQuantidade(cursor.getInt(cursor.getColumnIndex("QUANTIDADE")));
                aux.setAcrescimo(cursor.getDouble(cursor.getColumnIndex("ACRESCIMO")));
                aux.setDesconto(cursor.getDouble(cursor.getColumnIndex("DESCONTO")));
                aux.setValorTotal(cursor.getDouble(cursor.getColumnIndex("VALOR_TOTAL")));
                aux.setDataVenda(cursor.getString(cursor.getColumnIndex("DATA_VENDA")));
                aux.setDescricao(cursor.getString(cursor.getColumnIndex("DESCRICAO")));
                aux.setPago(cursor.getInt(cursor.getColumnIndex("PAGO")));
                aux.setIntegrar(cursor.getInt(cursor.getColumnIndex("INTEGRAR")));
                aux.setIdSalesforce(cursor.getString(cursor.getColumnIndex("ID_SALESFORCE")));

                retorno.add(aux);

            }while(cursor.moveToNext());
        }

        return retorno;
    }

}
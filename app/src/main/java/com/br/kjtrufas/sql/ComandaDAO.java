package com.br.kjtrufas.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.br.kjtrufas.entidades.Comanda;
import com.br.kjtrufas.entidades.Venda;
import com.br.kjtrufas.suporte.DadosComandas;
import com.br.kjtrufas.suporte.DadosVendas;

import java.text.NumberFormat;
import java.util.ArrayList;

public class ComandaDAO {

    private static ContentValues preencherContentValues(Comanda comanda)
    {
        ContentValues values = new ContentValues();

        if(comanda.getId()!=null)
        values.put("ID_COMANDA",comanda.getId());
        values.put("ID_VENDEDOR",comanda.getIdVendedor());
        values.put("NOME",comanda.getNome());
        values.put("A_RECEBER",comanda.getAReceber());
        values.put("A_RECEBER",comanda.getAReceber());
        values.put("INTEGRAR",comanda.getIntegrar());
        values.put("ID_SALESFORCE",comanda.getIdSalesforce());

        return values;
    }

    public static void upsert(Comanda comanda,SQLiteDatabase conn)
    {
        if(!hasComanda(comanda, conn)) {
            comanda.setId(VendedorDAO.getIdComanda(conn));
            conn.insertOrThrow("COMANDA", null, preencherContentValues(comanda));
        }
        else
            conn.update("COMANDA",preencherContentValues(comanda),"NOME = ? AND ID_VENDEDOR = ?", new String[]{comanda.getNome(),comanda.getIdVendedor()});

    }

    public static boolean hasComanda(Comanda comanda, SQLiteDatabase conn)
    {
        if(comanda.getId()==null)
            return false;

        Cursor cursor = conn.query("COMANDA",null,"ID_COMANDA = ?",new String[]{comanda.getId()+""},null,null,null);

        if (cursor.getCount()==0)
            return false;
        else
            return true;
    }

    /*public static boolean hasComanda(String nome, SQLiteDatabase conn)
    {
        Cursor cursor = conn.query("COMANDA",null,"NOME = ? AND ID_VENDEDOR = ?",new String[]{nome,VendedorDAO.getVendedor(conn).getId()},null,null,null);

        if (cursor.getCount()==0)
            return false;
        else
            return true;
    }*/

    public static void delete(String nome, String idVendedor, SQLiteDatabase conn)
    {
        conn.delete("COMANDA","NOME = ? AND ID_VENDEDOR = ?",new String[]{nome,idVendedor});
    }

    public static ArrayAdapter<String> getTodasComandas(Context context,SQLiteDatabase conn) {

        ArrayAdapter<String> retorno =  new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1);

        Cursor cursor = conn.query("COMANDA", null, "ID_VENDEDOR = ?", new String[]{VendedorDAO.getVendedor(conn).getId()}, null, null, null);

        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();

            do {
                retorno.add(cursor.getString(cursor.getColumnIndex("NOME")));
            }while(cursor.moveToNext());
        }

        return retorno;
    }

    public static Comanda getComanda(String nome, SQLiteDatabase conn)
    {
        Comanda retorno = null;

        Cursor cursor = conn.query("COMANDA",null,"NOME = ? AND ID_VENDEDOR = ?",new String[]{nome,VendedorDAO.getVendedor(conn).getId()},null,null,null);

        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();

                retorno = new Comanda();
                retorno.setId(cursor.getInt(cursor.getColumnIndex("ID_COMANDA")));
                retorno.setIdSalesforce(cursor.getString(cursor.getColumnIndex("ID_SALESFORCE")));
                retorno.setIdVendedor(cursor.getString(cursor.getColumnIndex("ID_VENDEDOR")));
                retorno.setNome(cursor.getString(cursor.getColumnIndex("NOME")));
                retorno.setAReceber(cursor.getDouble(cursor.getColumnIndex("A_RECEBER")));
        }

        return retorno;
    }

    public static DadosComandas getComandasAReceber(SQLiteDatabase conn)
    {
        ArrayList<String> nome = new ArrayList<String>();
        ArrayList<String> valor = new ArrayList<String>();

        Cursor cursor = conn.query("COMANDA", null, "A_RECEBER != ? AND ID_VENDEDOR = ?", new String[]{"0", VendedorDAO.getVendedor(conn).getId()}, null, null, "NOME");

        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();

            do {
                valor.add(String.valueOf(NumberFormat.getCurrencyInstance().format(cursor.getDouble(cursor.getColumnIndex("A_RECEBER")))));
                nome.add(cursor.getString(cursor.getColumnIndex("NOME")));

            }while(cursor.moveToNext());
        }

        DadosComandas retorno =  new DadosComandas(nome.size());
        retorno.setNome(nome.toArray(retorno.getNome()));
        retorno.setValor(valor.toArray(retorno.getValor()));

        return retorno;
    }

    public static ArrayList<Comanda> getComandasParaIntegrar(SQLiteDatabase conn)
    {
        Cursor cursor = conn.query("COMANDA", null, "INTEGRAR = ? AND ID_VENDEDOR = ?", new String[]{"1", VendedorDAO.getVendedor(conn).getId()}, null, null, null);
        ArrayList<Comanda> retorno = new ArrayList<Comanda>();

        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();

            do {
                Comanda aux = new Comanda();
                aux.setId(cursor.getInt(cursor.getColumnIndex("ID_COMANDA")));
                aux.setIdSalesforce(cursor.getString(cursor.getColumnIndex("ID_SALESFORCE")));
                aux.setIdVendedor(cursor.getString(cursor.getColumnIndex("ID_VENDEDOR")));
                aux.setNome(cursor.getString(cursor.getColumnIndex("NOME")));
                aux.setAReceber(cursor.getDouble(cursor.getColumnIndex("A_RECEBER")));
                aux.setIntegrar(cursor.getInt(cursor.getColumnIndex("INTEGRAR")));

                retorno.add(aux);

            }while(cursor.moveToNext());
        }

        return retorno;
    }
}

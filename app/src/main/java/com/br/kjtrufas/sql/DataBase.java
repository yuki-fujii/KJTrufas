package com.br.kjtrufas.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.Serializable;

public class DataBase extends SQLiteOpenHelper implements Serializable
{
    public DataBase (Context context)
    {
        super(context,"KJ Trufas",null,101);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        Log.i("DataBase","criou");
        db.execSQL(ScriptSQL.getVendedor());
        db.execSQL(ScriptSQL.getComanda());
        db.execSQL(ScriptSQL.getProduto());
        db.execSQL(ScriptSQL.getSabor());
        db.execSQL(ScriptSQL.getProdutoDisponivel());
        db.execSQL(ScriptSQL.getVendas());
        db.execSQL(ScriptSQL.getTokenSF());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1)
    {
        Log.i("DataBase","atualizou");
        db.execSQL("DROP TABLE IF EXISTS VENDEDOR");
        db.execSQL("DROP TABLE IF EXISTS COMANDA");
        db.execSQL("DROP TABLE IF EXISTS PRODUTO");
        db.execSQL("DROP TABLE IF EXISTS SABOR");
        db.execSQL("DROP TABLE IF EXISTS PRODUTO_DISPONIVEL");
        db.execSQL("DROP TABLE IF EXISTS VENDA");
        db.execSQL("DROP TABLE IF EXISTS TOKEN_SF");

        onCreate(db);

    }

    public static void onDeleteSQLite(SQLiteDatabase db)
    {
        db.execSQL(ScriptSQL.deleteVendedor());
        db.execSQL(ScriptSQL.deleteComanda());
        db.execSQL(ScriptSQL.deleteProduto());
        db.execSQL(ScriptSQL.deleteSabor());
        db.execSQL(ScriptSQL.deleteProdutoDisponivel());
        db.execSQL(ScriptSQL.deleteVendas());
        db.execSQL(ScriptSQL.deleteTokenSF());
    }


}

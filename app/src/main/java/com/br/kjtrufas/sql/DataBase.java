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
        super(context,"KJ Trufas",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(ScriptSQL.getVendedor());
        db.execSQL(ScriptSQL.getComanda());
        db.execSQL(ScriptSQL.getProduto());
        db.execSQL(ScriptSQL.getSabor());
        db.execSQL(ScriptSQL.getProdutoDisponivel());
        db.execSQL(ScriptSQL.getVendas());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {

    }


}

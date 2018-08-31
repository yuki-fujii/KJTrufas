package com.br.kjtrufas.salesforce;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.br.kjtrufas.sql.DataBase;
import com.br.kjtrufas.suporte.Util;

import java.util.Calendar;

public class SincronizarDados extends BroadcastReceiver
{
    Context ctx;
    private DataBase dataBase;
    private SQLiteDatabase conn;
    private Boolean hasConnection;

    @Override
    public void onReceive(Context context, Intent intent)
    {
        ctx = context;
        Calendar c = Calendar.getInstance();

        try
        {
            conexaoBD();
            if (hasConnection)
            {
                Log.i("Sincronizar", "" + c.getTime());
                Util.integrarVendas(conn);
            }
        }
        catch (Exception e)
        {
            Log.i("Erro",e.getMessage());
        }
    }

    private void conexaoBD()
    {
        try {

            dataBase = new DataBase(ctx);
            conn = dataBase.getWritableDatabase();

            hasConnection = true;
        } catch (Exception ex) {
            hasConnection = false;
        }
    }

}

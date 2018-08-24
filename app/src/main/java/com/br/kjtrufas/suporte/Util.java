package com.br.kjtrufas.suporte;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;

import com.br.kjtrufas.entidades.Comanda;
import com.br.kjtrufas.entidades.EntidadePost;
import com.br.kjtrufas.entidades.EnviarRegistro;
import com.br.kjtrufas.entidades.Venda;
import com.br.kjtrufas.salesforce.SalesforcePost;
import com.br.kjtrufas.sql.ComandaDAO;
import com.br.kjtrufas.sql.VendaDAO;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class Util {

    public static String getDataAtual()
    {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dataFormatada = new SimpleDateFormat("dd/MM/yyyy");
        String str = dataFormatada.format(calendar.getTime());

        return str;
    }

    public static int converterBoolean(Boolean b)
    {
        if(b)
            return 1;
        else
            return 0;
    }

    public static void integrarVendas (SQLiteDatabase conn)
    {
        EnviarRegistro enviarRegistro = new EnviarRegistro();

        enviarRegistro.setVendas(VendaDAO.getVendasParaIntegrar(conn));
        enviarRegistro.setComandas(ComandaDAO.getComandasParaIntegrar(conn));

        if((!enviarRegistro.getComandas().isEmpty()) || (!enviarRegistro.getVendas().isEmpty()))
        {
            Gson gson = new Gson();
            String json = "{\"registros\" : "+gson.toJson(enviarRegistro)+"}";

            Log.i("JSON Enviar",json);

            EntidadePost entidadePost = new EntidadePost();
            entidadePost.setConn(conn);
            entidadePost.setService("registroWS");
            entidadePost.setJson(json);

            new SalesforcePost().execute(entidadePost);
        }

    }
}

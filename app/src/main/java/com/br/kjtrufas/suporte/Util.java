package com.br.kjtrufas.suporte;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.br.kjtrufas.entidades.Comanda;
import com.br.kjtrufas.entidades.EntidadePost;
import com.br.kjtrufas.entidades.EntidadesEncapsuladas;
import com.br.kjtrufas.entidades.EnviarRegistro;
import com.br.kjtrufas.entidades.Venda;
import com.br.kjtrufas.salesforce.SalesforcePost;
import com.br.kjtrufas.sql.ComandaDAO;
import com.br.kjtrufas.sql.VendaDAO;
import com.br.kjtrufas.sql.VendedorDAO;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


public class Util {

    public static String getDataAtual()
    {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dataFormatada = new SimpleDateFormat("dd/MM/yyyy");
        String str = dataFormatada.format(calendar.getTime());

        return str;
    }

    public static String convertDataSF(String s)
    {
        String ret;

        String[] separated = s.split("-");
        ret = separated[2]+"/"+separated[1]+"/"+separated[0];

        return ret;
    }

    public static int converterBoolean(Boolean b)
    {
        if(b)
            return 1;
        else
            return 0;
    }

    public static Double duasCasas(Double d)
    {
        DecimalFormat df = (DecimalFormat) new DecimalFormat("#.00").getInstance(Locale.US);
        String dx = df.format(d);

        return Double.parseDouble(dx);

    }

    public static void integrarVendas (SQLiteDatabase conn, Context context)
    {
        EntidadesEncapsuladas entidadesEncapsuladas = new EntidadesEncapsuladas();

        entidadesEncapsuladas.getEnviarRegistro().setVendas(VendaDAO.getVendasParaIntegrar(conn));
        entidadesEncapsuladas.getEnviarRegistro().setComandas(ComandaDAO.getComandasParaIntegrar(conn));
        entidadesEncapsuladas.setVendedor(VendedorDAO.getVendedor(conn));

        if((!entidadesEncapsuladas.getEnviarRegistro().getComandas().isEmpty()) || (!entidadesEncapsuladas.getEnviarRegistro().getVendas().isEmpty()))
        {
            Log.i("toString",entidadesEncapsuladas.getEnviarRegistro().toString());
            Gson gson = new Gson();
            String json = "{\"registros\" : "+gson.toJson(entidadesEncapsuladas)+"}";

            Log.i("JSON Enviar",json);

            EntidadePost entidadePost = new EntidadePost();
            entidadePost.setConn(conn);
            entidadePost.setService("registroWS");
            entidadePost.setJson(json);
            entidadePost.setContext(context);

            new SalesforcePost().execute(entidadePost);
        }
        else {
            if(context!=null)
                Toast.makeText(context,"Todos dados encontram-se sincronizados!", Toast.LENGTH_LONG).show();
            Log.i("Resposta", "não existem dados para serem integrados");
        }

    }
}

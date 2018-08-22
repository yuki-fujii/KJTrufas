package com.br.kjtrufas.suporte;

import android.util.Log;
import android.view.View;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

    public void integrarVendas (View view)
    {

    }
}

package com.br.kjtrufas.salesforce;

import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.br.kjtrufas.sql.TokenDAO;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SalesforceGet extends AsyncTask<SQLiteDatabase,Void,String> {

    private final String url = "/services/apexrest/";
    private SQLiteDatabase conn;

    protected String doInBackground(SQLiteDatabase... arg0) {

        String retorno = "false";

        try {

            conn = arg0[0];

            HttpClient httpclient = new HttpClient();

            GetMethod get = new GetMethod(TokenDAO.getToken(conn).getInstance_url()+this.url+"teste");
            get.addRequestHeader("Authorization","OAuth "+TokenDAO.getToken(conn).getAccess_token());
            get.addRequestHeader("Content-Type","application/json");

            httpclient.executeMethod(get);

            InputStream inputStream = get.getResponseBodyAsStream();
            InputStreamReader r = new InputStreamReader(inputStream);
            BufferedReader br = new BufferedReader(r);

            String resposta = br.readLine();

            Log.i("GET TESTE", resposta);

            retorno = "true";

        } catch (Exception e) {
            e.printStackTrace();
        }

        return retorno;
    }

    @Override
    protected void onPostExecute(String result) {

        Log.i("Result",result);

        if(result.equals("true"))
        {
            Log.i("Resultado", "Get efetuado com sucesso!");
        }
        else
            Log.i("Resultado", "Não foi possível conectar-se ao servidor! Por favor, tente mais tarde.");
    }
}

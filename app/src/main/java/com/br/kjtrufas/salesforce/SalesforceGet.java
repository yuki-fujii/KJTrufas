package com.br.kjtrufas.salesforce;

import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.br.kjtrufas.sql.TokenDAO;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SalesforceGet extends AsyncTask<SQLiteDatabase,Void,String> {

    private final String url = "/services/apexrest/";
    private SQLiteDatabase conn;

    protected String doInBackground(SQLiteDatabase... arg0) {

        String retorno = "false";

        try {

            conn = arg0[0];

            URL url = new URL(TokenDAO.getToken(conn).getInstance_url()+this.url+"teste");

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Authorization","OAuth "+TokenDAO.getToken(conn).getAccess_token());
            urlConnection.setRequestProperty("Content-Type", "application/json");

            InputStream inputStream;
            // get stream
            if (urlConnection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                inputStream = urlConnection.getInputStream();
            } else {
                inputStream = urlConnection.getErrorStream();
            }
            // parse stream
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String temp, resposta = "";
            while ((temp = bufferedReader.readLine()) != null) {
                resposta += temp;
            }

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

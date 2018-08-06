package com.br.kjtrufas.salesforce;

import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.br.kjtrufas.entidades.Token;
import com.br.kjtrufas.sql.TokenDAO;
import com.google.gson.Gson;

import org.apache.commons.httpclient.NameValuePair;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SalesForceAuthentication extends AsyncTask<SQLiteDatabase,Void,String> {

    private final String url = "https://login.salesforce.com/services/oauth2/token";
    private String parametros;
    private SQLiteDatabase conn;
    private Token token;

    public void SalesForceAuthentication() throws UnsupportedEncodingException {
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new NameValuePair("grant_type", "password"));
        list.add(new NameValuePair("client_id", "3MVG9oNqAtcJCF.HqArfHIZsNmM20XtQAU7iwxZDRy8dbsptEVZvLMsHS8iEbcSTW0m08.YMI1LgCmcrMJAUU"));
        list.add(new NameValuePair("client_secret", "1898644700404922238"));
        list.add(new NameValuePair("username", "kj.trufas.v2@force.com"));
        list.add(new NameValuePair("password", "Hiroyuk1"));

        this.parametros = this.getQuery(list);

    }

    protected String doInBackground(SQLiteDatabase... arg0) {

        String retorno = "false";

        try {

            conn = arg0[0];

            SalesForceAuthentication();

            Log.i("Parametros",parametros);
            URL url = new URL(this.url);

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);

            OutputStream outputStream = new BufferedOutputStream(urlConnection.getOutputStream());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "utf-8"));
            writer.write(parametros);
            writer.flush();
            writer.close();
            outputStream.close();

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

            Log.i("Resposta AUTHEN",resposta);

            Gson gson = new Gson();
            token = gson.fromJson(resposta,Token.class);
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
            TokenDAO.upsert(token,conn);
            Log.i("Resultado", "Login efetuado com sucesso!");
        }
        else
            Log.i("Resultado", "Não foi possível conectar-se ao servidor! Por favor, tente mais tarde.");
    }

    private String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException
    {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (NameValuePair pair : params)
        {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(pair.getName());
            result.append("=");
            result.append(pair.getValue());
        }

        return result.toString();
    }

}

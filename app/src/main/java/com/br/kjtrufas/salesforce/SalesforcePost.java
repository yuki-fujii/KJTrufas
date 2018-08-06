package com.br.kjtrufas.salesforce;

import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.br.kjtrufas.entidades.EnviarRegistro;
import com.br.kjtrufas.sql.EnviarRegistroDAO;
import com.br.kjtrufas.sql.TokenDAO;
import com.google.gson.Gson;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


public class SalesforcePost  extends AsyncTask<SQLiteDatabase,Void,String>
{
    private SQLiteDatabase conn;
    private final String url = "/services/apexrest/";

    protected String doInBackground(SQLiteDatabase... arg0) {

        String retorno = "false";

        try {

            conn = arg0[0];
            EnviarRegistro enviarRegistro = new EnviarRegistro();
            enviarRegistro.setVendas(EnviarRegistroDAO.getVendas(conn));

            Gson gson = new Gson();
            String jsonEnviar = gson.toJson(enviarRegistro);
            jsonEnviar = "{\"jsonRecebido\" :"+jsonEnviar+"}";

            Log.i("JSON",jsonEnviar);

            URL url = new URL(TokenDAO.getToken(conn).getInstance_url()+this.url+"teste");

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Authorization","OAuth "+TokenDAO.getToken(conn).getAccess_token());
            urlConnection.setRequestProperty("Content-Type", "application/json");

            OutputStream outputStream = new BufferedOutputStream(urlConnection.getOutputStream());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "utf-8"));
            writer.write(jsonEnviar);
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

            Log.i("Resposta",resposta);

            retorno = "true";

        } catch (Exception e) {
            e.printStackTrace();
            retorno = "false";
        }

        return retorno;
    }

    @Override
    protected void onPostExecute(String result) {

        Log.i("Result",result);

        if(result.equals("true"))
            Log.i("Resultado", "Post realizado");
        else
            Log.i("Resultado", "Não foi possível conectar-se ao servidor! Por favor, tente mais tarde.");
    }

}
package com.br.kjtrufas.salesforce;

import android.os.AsyncTask;
import android.util.Log;

import com.br.kjtrufas.entidades.EntidadePost;
import com.br.kjtrufas.entidades.EnviarRegistro;
import com.br.kjtrufas.sql.EnviarRegistroDAO;
import com.br.kjtrufas.sql.TokenDAO;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


public class SalesforcePost  extends AsyncTask<EntidadePost,Void,String>
{
    private EntidadePost entidadePost;
    private final String url = "/services/apexrest/";
    private String resposta = "";

    protected String doInBackground(EntidadePost... arg0) {

        String retorno = "false";

        try {

            entidadePost = arg0[0];

            //"{\"jsonRecebido\" :"
            Log.i("JSON", entidadePost.getJson());

            URL url = new URL(TokenDAO.getToken(entidadePost.getConn()).getInstance_url()+this.url+entidadePost.getService());

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Authorization","OAuth "+TokenDAO.getToken(entidadePost.getConn()).getAccess_token());
            urlConnection.setRequestProperty("Content-Type", "application/json");

            OutputStream outputStream = new BufferedOutputStream(urlConnection.getOutputStream());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "utf-8"));
            writer.write(entidadePost.getJson());
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
            String temp;
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

        if(result.equals("true"))
        {
            Log.i("Resultado", "Post realizado");
            TratamentoRespostaPost.tratarResposta(entidadePost,resposta);
        }
        else
            Log.i("Resultado", "Não foi possível conectar-se ao servidor! Por favor, tente mais tarde.");
    }

}
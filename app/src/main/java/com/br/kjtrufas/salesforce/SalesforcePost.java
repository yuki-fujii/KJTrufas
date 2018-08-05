package com.br.kjtrufas.salesforce;

import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.br.kjtrufas.entidades.EnviarRegistro;
import com.br.kjtrufas.sql.EnviarRegistroDAO;
import com.br.kjtrufas.sql.TokenDAO;
import com.google.gson.Gson;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

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

            Log.i("JSON",jsonEnviar);

            HttpClient httpclient = new HttpClient();

            PostMethod post = new PostMethod(TokenDAO.getToken(conn).getInstance_url()+this.url+"testepost");
            post.addRequestHeader("Authorization","OAuth "+TokenDAO.getToken(conn).getAccess_token());
            post.addRequestHeader("Content-Type","application/json");
            //post.addParameters(this.params);

            httpclient.executeMethod(post);

            InputStream inputStream = post.getResponseBodyAsStream();
            InputStreamReader r = new InputStreamReader(inputStream);
            BufferedReader br = new BufferedReader(r);

            String resposta = br.readLine();

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
            Log.i("Resultado", "Login efetuado com sucesso!");
            Log.i("GET TOKEN",TokenDAO.getToken(conn).getAccess_token());

            new SalesforceGet().execute(conn);
        }
        else
            Log.i("Resultado", "Não foi possível conectar-se ao servidor! Por favor, tente mais tarde.");
    }

}
package com.br.kjtrufas.salesforce;

import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.br.kjtrufas.entidades.Token;
import com.br.kjtrufas.entidades.Vendedor;
import com.br.kjtrufas.sql.TokenDAO;
import com.google.gson.Gson;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SalesForceAuthentication extends AsyncTask<SQLiteDatabase,Void,String> {

    private final String url = "https://login.salesforce.com/services/oauth2/token";
    private NameValuePair[] params;
    private Token token;
    private SQLiteDatabase conn;

    public void SalesForceAuthentication() {
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new NameValuePair("grant_type", "password"));
        list.add(new NameValuePair("client_id", "3MVG9oNqAtcJCF.HqArfHIZsNmM20XtQAU7iwxZDRy8dbsptEVZvLMsHS8iEbcSTW0m08.YMI1LgCmcrMJAUU"));
        list.add(new NameValuePair("client_secret", "1898644700404922238"));
        list.add(new NameValuePair("username", "kj.trufas.v2@force.com"));
        list.add(new NameValuePair("password", "Hiroyuk1"));

        this.params = list.toArray(new NameValuePair[list.size()]);
    }

    protected String doInBackground(SQLiteDatabase... arg0) {

        String retorno = "false";

        try {

            conn = arg0[0];

            SalesForceAuthentication();

            HttpClient httpclient = new HttpClient();

            PostMethod post = new PostMethod(this.url);
            post.addParameters(this.params);

            httpclient.executeMethod(post);

            InputStream inputStream = post.getResponseBodyAsStream();
            InputStreamReader r = new InputStreamReader(inputStream);
            BufferedReader br = new BufferedReader(r);

            String resposta = br.readLine();

            Gson gson = new Gson();
            token = gson.fromJson(resposta, Token.class);

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
            TokenDAO.upsert(token,conn);
            Log.i("GET TOKEN",TokenDAO.getToken(conn).getAccess_token());

            new SalesforceGet().execute(conn);
        }
        else
            Log.i("Resultado", "Não foi possível conectar-se ao servidor! Por favor, tente mais tarde.");
    }

}

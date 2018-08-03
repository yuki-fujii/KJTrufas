package com.br.kjtrufas.salesforce;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.br.kjtrufas.entidades.Vendedor;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SalesForceAuthentication extends AsyncTask<Vendedor,Void,String> {

    private final String url = "https://test.salesforce.com/services/oauth2/token";
    private NameValuePair[] params;
    private String token;

    // Construtor
    public SalesForceAuthentication() {
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new NameValuePair("grant_type", "password"));
        list.add(new NameValuePair("client_id", "3MVG9Vik22TUgUpjNpPYPiWTt9zk8DGzmpD4kjcaJxk.EEP2QfaRS15Sx8c797HvFJHWGWZIbfszYarumH0NK"));
        list.add(new NameValuePair("client_secret", "3977891924462011028"));
        list.add(new NameValuePair("username", "alcino.fujii@sotreq.com.homolog"));
        list.add(new NameValuePair("password", "2sotreq123"));

        this.params = list.toArray(new NameValuePair[list.size()]);
    }

    protected String doInBackground(Vendedor... arg0) {

        String retorno = "false";

        try {

            SalesForceAuthentication salesForceAuthentication = new SalesForceAuthentication();

            HttpClient httpclient = new HttpClient();

            PostMethod post = new PostMethod(this.url);
            post.addParameters(this.params);
            Log.i("Post", post.getName());

            httpclient.executeMethod(post);

            InputStream inputStream = post.getResponseBodyAsStream();

            byte[] buffer = new byte[1024 * 1024 * 5];
            String path = null;
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                path = new String(buffer);
            }

            Log.i("JSON", path);
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
            Log.i("Resultado", "Login efetuado com sucesso!");
        else
            Log.i("Resultado", "Não foi possível conectar-se ao servidor! Por favor, tente mais tarde.");
    }

}

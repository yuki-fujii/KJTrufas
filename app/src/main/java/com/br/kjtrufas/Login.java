package com.br.kjtrufas;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import com.br.kjtrufas.entidades.EntidadePost;
import com.br.kjtrufas.entidades.EntidadesEncapsuladas;
import com.br.kjtrufas.entidades.Produto;
import com.br.kjtrufas.entidades.ProdutoDisponivel;
import com.br.kjtrufas.entidades.Sabor;
import com.br.kjtrufas.entidades.Vendedor;
import com.br.kjtrufas.salesforce.SalesForceAuthentication;
import com.br.kjtrufas.salesforce.SalesforcePost;
import com.br.kjtrufas.sql.DataBase;
import com.br.kjtrufas.sql.TokenDAO;
import com.br.kjtrufas.sql.VendedorDAO;
import com.google.gson.Gson;

public class Login extends AppCompatActivity {

    private DataBase dataBase;
    private SQLiteDatabase conn;

    private EditText editEmailLogin;
    private EditText editSenhaLogin;

    private static int SPLASH_TIME_OUT = 6000;
    private Context context = this;

    private Boolean autenticacao = false;
    private Boolean hasConnection;
    private String msgError;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Vendedor v = new Vendedor();
        v.setNome("teste");
        v.setLogin("teste@teste.com");
        v.setSenha("Senha");
        v.setConfSenha("Senha");
        v.setId("ID");

        Produto p = new Produto();
        p.setId("id");
        p.setNome("nome");
        p.setPreco(1.0);
        p.setAtivo(1);

        Produto p2 = new Produto();
        p2.setId("id2");
        p2.setNome("nome2");
        p2.setPreco(1.0);
        p2.setAtivo(1);

        Sabor s = new Sabor();
        s.setId("id");
        s.setNome("nome");
        s.setAtivo(1);

        Sabor s2 = new Sabor();
        s2.setId("id2");
        s2.setNome("nome2");
        s2.setAtivo(2);

        ProdutoDisponivel produtoDisponivel = new ProdutoDisponivel();
        produtoDisponivel.setId("id");
        produtoDisponivel.setIdVendedor("idVend");
        produtoDisponivel.setProduto("prod");
        produtoDisponivel.setSabor("sabor");
        produtoDisponivel.setQuantidade(1);

        ProdutoDisponivel produtoDisponivel2 = new ProdutoDisponivel();
        produtoDisponivel2.setId("id2");
        produtoDisponivel2.setIdVendedor("idVend2");
        produtoDisponivel2.setProduto("prod2");
        produtoDisponivel2.setSabor("sabor2");
        produtoDisponivel2.setQuantidade(1);

        EntidadesEncapsuladas entidadesEncapsuladas = new EntidadesEncapsuladas();
        entidadesEncapsuladas.setVendedor(v);
        entidadesEncapsuladas.getProdutos().add(p);
        entidadesEncapsuladas.getProdutos().add(p2);
        entidadesEncapsuladas.getSabores().add(s);
        entidadesEncapsuladas.getSabores().add(s2);
        entidadesEncapsuladas.getProdutosDisponiveis().add(produtoDisponivel);
        entidadesEncapsuladas.getProdutosDisponiveis().add(produtoDisponivel2);

        Gson g = new Gson();
        String json = g.toJson(entidadesEncapsuladas);

        Log.i("JSON",json);

        editEmailLogin = findViewById(R.id.editEmailLogin);
        editSenhaLogin = findViewById(R.id.editSenhaLogin);

        conexaoBD();

        if(VendedorDAO.getVendedor(conn)!=null)
        {
            Intent i = new Intent(Login.this, MainActivity.class);
            startActivity(i);
            finish();
        }

        if(hasConnection)
        {
            new SalesForceAuthentication().execute(conn);
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    autenticacao = true;
                }
            }, SPLASH_TIME_OUT);
        }

    }

    private void conexaoBD()
    {
        try {

            dataBase = new DataBase(this);
            conn = dataBase.getWritableDatabase();

            hasConnection = true;
        } catch (Exception ex) {
            hasConnection = false;
        }
    }

    public void realizarLogin (View view)
    {
        if(validar())
        {
            dialog = new ProgressDialog(context);
            dialog.setTitle("Efetuando o login");
            dialog.setMessage("Aguarde...");
            dialog.show();

            Vendedor aux = new Vendedor();
            aux.setLogin(editEmailLogin.getText().toString());
            aux.setSenha(editSenhaLogin.getText().toString());

            Gson gson = new Gson();
            String json = "{\"vendedor\" : "+gson.toJson(aux)+"}";

            EntidadePost entidadePost = new EntidadePost();
            entidadePost.setJson(json);
            entidadePost.setService("login");
            entidadePost.setConn(conn);

            new SalesforcePost().execute(entidadePost);

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {

                    dialog.dismiss();

                    if(VendedorDAO.getVendedor(conn)==null || VendedorDAO.getVendedor(conn).getId().equals(""))
                    {
                        android.app.AlertDialog.Builder dlg = new android.app.AlertDialog.Builder(context);
                        dlg.setMessage("Login ou Senha estão incorretos.");
                        dlg.setNeutralButton("OK", null);
                        dlg.show();
                    }
                    else
                    {
                        Log.i("Chamar","Main");
                        Intent i = new Intent(Login.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    }

                }
            }, SPLASH_TIME_OUT);

        }
        else
        {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setMessage(msgError);
            dlg.setNeutralButton("OK",null);
            dlg.show();
        }

    }

    private boolean validar()
    {

        if(!this.autenticacao)
        {
            msgError = "Aguarde por alguns segundos e tente novamente.";
            return false;
        }

        if(editEmailLogin.getText()==null || editEmailLogin.getText().toString().equals("")) {
            msgError = "Preencha o campo Email.";
            return false;
        }
        else
        {
            editEmailLogin.setText(editEmailLogin.getText().toString().toLowerCase());
        }

        if(editSenhaLogin.getText()==null || editSenhaLogin.getText().toString().equals("")) {
            msgError = "Preencha o campo Senha.";
            return false;
        }

        if(!hasConnection)
        {
            msgError = "Não foi possível conectar-se ao banco de dados! Tente mais tarde.";
            return false;
        }

        if(TokenDAO.getToken(conn).getAccess_token() == null || TokenDAO.getToken(conn).getAccess_token().equals(""))
        {
            msgError = "Não foi possível conectar-se ao servidor! Tente mais tarde.";
            return false;
        }

        return true;
    }
}

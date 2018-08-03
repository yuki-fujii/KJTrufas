package com.br.kjtrufas;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.br.kjtrufas.salesforce.SalesForceAuthentication;
import com.br.kjtrufas.sql.DataBase;
import com.br.kjtrufas.sql.VendedorDAO;

import com.br.kjtrufas.entidades.Vendedor;

public class MainActivity extends AppCompatActivity {

    private Button btnAdministrador;
    private Button btnRealizarVendas;

    private DataBase dataBase;
    private SQLiteDatabase conn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        btnAdministrador = (Button) findViewById(R.id.btnAdministrador);
        btnRealizarVendas = (Button) findViewById(R.id.btnRealizarVendas);

        Vendedor novo = new Vendedor();
        novo.setNome("Alcino Fujii");
        novo.setLogin("yuki.fujii@hotmail.com");
        novo.setSenha("Hiroyuk1");
        novo.setConfSenha("Hiroyuk1");

        if(conexaoBD())
        {
            VendedorDAO.upsert(novo,conn);
        }
    }

    public void chamarAdministrador (View view)
    {
        Intent it = new Intent(this, Administrador.class);
        startActivityForResult(it, 0);
    }

    public void chamarRealizarVendas (View view)
    {
        Intent it = new Intent(this, Vendas.class);
        startActivityForResult(it, 0);
    }

    public void chamarConsultarComanda (View view)
    {
        Intent it = new Intent(this, ConsultarComanda.class);
        startActivityForResult(it, 0);
    }

    private boolean conexaoBD()
    {
        try {

            dataBase = new DataBase(this);
            conn = dataBase.getWritableDatabase();

            return true;
        }
        catch (Exception ex)
        {
            return false;
        }

    }

    public void mostrarMeuNome (View view)
    {
        AlertDialog.Builder dlg = new AlertDialog.Builder(this);
        dlg.setMessage("Keila Maravilhosa!");
        dlg.setNeutralButton("OK",null);
        dlg.show();

        new SalesForceAuthentication().execute(VendedorDAO.getVendedor(conn));
    }
}

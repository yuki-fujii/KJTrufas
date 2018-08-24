package com.br.kjtrufas;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.br.kjtrufas.sql.DataBase;
import com.br.kjtrufas.suporte.Util;


public class MainActivity extends AppCompatActivity {

    private DataBase dataBase;
    private SQLiteDatabase conn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
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

    public void mostrarMeuNome (View view)
    {
        if(conexaoBD())
            Util.integrarVendas(conn);
        else
        {
            android.app.AlertDialog.Builder dlg = new android.app.AlertDialog.Builder(this);
            dlg.setMessage("Erro ao conectar-se com o banco de dados.");
            dlg.setNeutralButton("OK", null);
            dlg.show();
        }
    }
}

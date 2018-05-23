package com.br.kjtrufas;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.br.kjtrufas.sql.DataBase;

public class MainActivity extends AppCompatActivity {

    private Button btnAdministrador;

    private DataBase dataBase;
    private SQLiteDatabase conn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        // Example of a call to a native method

        btnAdministrador = (Button) findViewById(R.id.btnAdministrador);
    }

    public void chamarAdministrador (View view)
    {
        Intent it = new Intent(this, Administrador.class);
        startActivityForResult(it, 0);

        if(conexaoBD())
        {
            android.app.AlertDialog.Builder dlg = new android.app.AlertDialog.Builder(this);
            dlg.setMessage("Conectou com banco!");
            dlg.setNeutralButton("OK", null);
            dlg.show();
            
        }
        else
        {
            android.app.AlertDialog.Builder dlg = new android.app.AlertDialog.Builder(this);
            dlg.setMessage("Erro ao conectar com banco!");
            dlg.setNeutralButton("OK", null);
            dlg.show();
        }

        
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
}

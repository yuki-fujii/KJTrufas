package com.br.kjtrufas;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

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
        AlertDialog.Builder dlg = new AlertDialog.Builder(this);
        dlg.setMessage("Keila Maravilhosa!");
        dlg.setNeutralButton("OK",null);
        dlg.show();

    }
}

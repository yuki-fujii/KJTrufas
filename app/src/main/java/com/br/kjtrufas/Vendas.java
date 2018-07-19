package com.br.kjtrufas;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.br.kjtrufas.sql.ComandaDAO;
import com.br.kjtrufas.sql.DataBase;
import com.br.kjtrufas.sql.VendedorDAO;

import com.br.kjtrufas.entidades.Comanda;

public class Vendas extends AppCompatActivity {

    private DataBase dataBase;
    private SQLiteDatabase conn;

    private AutoCompleteTextView autoNome;
    private ArrayAdapter<String> adpTodasComandas;

    private String nomeComanda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendas);

        autoNome = (AutoCompleteTextView) findViewById(R.id.autoNome);

        if(this.conexaoBD())
        {
            Bundle bundle = getIntent().getExtras();

            if ((bundle != null) && (bundle.containsKey("NOME")))
                autoNome.setText((String) bundle.getSerializable("NOME"));

            Log.i("Total coman",String.valueOf(ComandaDAO.getComanda(this,conn).getCount()));
            this.adpTodasComandas = ComandaDAO.getComanda(this,conn);
            autoNome.setAdapter(adpTodasComandas);

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

    public void salvarVendido(View view)
    {
        Log.i("Novo vend",autoNome.getText().toString());
        Comanda newComanda = new Comanda(autoNome.getText().toString(), VendedorDAO.getVendedor(conn).getId());
        ComandaDAO.upsert(newComanda,conn);

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        atualizarTela();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        finish();
                        break;
                }
            }
        };

        AlertDialog.Builder dlg = new AlertDialog.Builder(this);
        dlg.setMessage("Realizar outra venda para esse cliente?").setPositiveButton("Sim", dialogClickListener).setNegativeButton("Não", dialogClickListener);
        dlg.show();

    }

    public void atualizarTela()
    {
        Intent it = new Intent(this, Vendas.class);
        it.putExtra("NOME",String.valueOf(this.autoNome.getText()));
        startActivityForResult(it, 0);
        finish();
    }
}

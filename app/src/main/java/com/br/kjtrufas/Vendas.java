package com.br.kjtrufas;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.AdapterView;
import android.widget.Toast;

import com.br.kjtrufas.sql.ComandaDAO;
import com.br.kjtrufas.sql.DataBase;
import com.br.kjtrufas.sql.VendedorDAO;

import entidades.Comanda;

public class Vendas extends AppCompatActivity {

    private DataBase dataBase;
    private SQLiteDatabase conn;

    AutoCompleteTextView autoNome;
    private ArrayAdapter<String> adpTodasComandas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendas);

        autoNome = (AutoCompleteTextView) findViewById(R.id.autoNome);

        if(this.conexaoBD()){
            this.adpTodasComandas = ComandaDAO.getComanda(this,conn);
            autoNome.setAdapter(adpTodasComandas);

            /*autoNome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    nomeSintoma = adpTodosSistomas.getItem(position);
                }
            });*/
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
        Comanda newComanda = new Comanda(autoNome.getText().toString(), VendedorDAO.getVendedor(conn).getId());
        ComandaDAO.upsert(newComanda,conn);
        finish();
    }
}

package com.br.kjtrufas;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.TextView;

import com.br.kjtrufas.entidades.Comanda;
import com.br.kjtrufas.entidades.Venda;
import com.br.kjtrufas.sql.ComandaDAO;
import com.br.kjtrufas.sql.DataBase;
import com.br.kjtrufas.sql.VendaDAO;
import com.br.kjtrufas.suporte.CustomAdapterVendas;
import com.br.kjtrufas.suporte.DadosComanda;

import java.text.NumberFormat;

public class ConsultarComanda extends AppCompatActivity {

    private DataBase dataBase;
    private SQLiteDatabase conn;

    private AutoCompleteTextView consultaAutoNome;
    private TextView txtConsultaAReceber;
    private ListView lstConsulta;

    private DadosComanda dadosComanda;
    private ArrayAdapter<String> adpTodasComandas;
    private Comanda comanda;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_comanda);

        consultaAutoNome = findViewById(R.id.consultaAutoNome);
        txtConsultaAReceber = findViewById(R.id.txtConsultaAReceber);
        lstConsulta = findViewById(R.id.lstConsulta);

        if(this.conexaoBD())
        {
            this.adpTodasComandas = ComandaDAO.getTodasComandas(this,conn);
            consultaAutoNome.setAdapter(adpTodasComandas);
            context = this;

            consultaAutoNome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    comanda = ComandaDAO.getComanda(adpTodasComandas.getItem(position),conn);
                    txtConsultaAReceber.setText(String.valueOf(NumberFormat.getCurrencyInstance().format(comanda.getAReceber())));

                    dadosComanda = VendaDAO.getVendas(context, comanda.getId(), conn);
                    CustomAdapterVendas customAdapterVendas = new CustomAdapterVendas(getApplicationContext(),dadosComanda);
                    lstConsulta.setAdapter(customAdapterVendas);
                }
            });
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

    public void salvarConsultarComanda (View view)
    {
        finish();
    }
}

package com.br.kjtrufas;


import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.br.kjtrufas.entidades.Comanda;
import com.br.kjtrufas.entidades.Venda;
import com.br.kjtrufas.sql.ComandaDAO;
import com.br.kjtrufas.sql.DataBase;
import com.br.kjtrufas.sql.VendaDAO;
import com.br.kjtrufas.suporte.CustomAdapterComandas;
import com.br.kjtrufas.suporte.CustomAdapterVendas;
import com.br.kjtrufas.suporte.DadosComandas;
import com.br.kjtrufas.suporte.DadosVendas;
import com.br.kjtrufas.suporte.Util;

import java.text.NumberFormat;
import java.util.ArrayList;


public class ConsultarComandaFrag extends Fragment {

    private DataBase dataBase;
    private SQLiteDatabase conn;

    private AutoCompleteTextView consultaAutoNome;
    private TextView txtConsultaAReceber;
    private ListView lstConsulta;
    private EditText editValorRecebido;

    private DadosVendas dadosVendas;
    private DadosComandas dadosComandas;
    private ArrayAdapter<String> adpTodasComandas;
    private Comanda comanda;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_consultar_comanda, null);
        super.onCreate(savedInstanceState);

        context = container.getContext();
        consultaAutoNome = view.findViewById(R.id.consultaAutoNome);
        txtConsultaAReceber = view.findViewById(R.id.txtConsultaAReceber);
        lstConsulta = view.findViewById(R.id.lstConsulta);
        editValorRecebido = view.findViewById(R.id.editValorRecebido);

        if(this.conexaoBD())
        {
            Bundle bundle = getActivity().getIntent().getExtras();

            this.adpTodasComandas = ComandaDAO.getTodasComandas(context,conn);
            consultaAutoNome.setAdapter(adpTodasComandas);

            if ((bundle != null) && (bundle.containsKey("NOME")))
            {
                comanda = ComandaDAO.getComanda((String) bundle.getSerializable("NOME"),conn);
                consultaAutoNome.setText(comanda.getNome());
                listarVendasNaoPagasPorComanda();
            }
            else
            {
                dadosComandas = ComandaDAO.getComandasAReceber(conn);
                CustomAdapterComandas customAdapterComandas = new CustomAdapterComandas(getActivity().getApplicationContext(), dadosComandas);
                lstConsulta.setAdapter(customAdapterComandas);
                editValorRecebido.setEnabled(false);
            }

            consultaAutoNome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    comanda = ComandaDAO.getComanda(adpTodasComandas.getItem(position),conn);
                    listarVendasNaoPagasPorComanda();
                }
            });
        }

        return view;

    }

    private boolean conexaoBD()
    {
        try {

            dataBase = new DataBase(context);
            conn = dataBase.getWritableDatabase();

            return true;
        }
        catch (Exception ex)
        {
            return false;
        }

    }

    public void listarVendasNaoPagasPorComanda()
    {
        txtConsultaAReceber.setText(String.valueOf(NumberFormat.getCurrencyInstance().format(comanda.getAReceber())));
        editValorRecebido.setEnabled(true);
        dadosVendas = VendaDAO.getVendasNaoPagas(context, comanda.getId(), conn);
        CustomAdapterVendas customAdapterVendas = new CustomAdapterVendas(getActivity().getApplicationContext(), dadosVendas);
        lstConsulta.setAdapter(customAdapterVendas);
    }

    public void salvarConsultarComanda (View view)
    {
        String msgError = "";

        Log.i("Comanda",comanda.getNome());
        if(comanda.getNome().equals(consultaAutoNome.getText().toString()))
        {
            Log.i("consultaAutoNome","FILTRO 1");
            if(editValorRecebido.getText() != null && (!editValorRecebido.getText().toString().equals("")))
            {
                Venda pagamento = new Venda();
                pagamento.setDescricao("Pagamento");
                pagamento.setDataVenda(Util.getDataAtual());
                pagamento.setValorTotal(-1*Double.valueOf(editValorRecebido.getText().toString()));
                pagamento.setPago(0);
                pagamento.setIntegrar(1);
                pagamento.setIdComanda(comanda.getId());
                pagamento.setIdVendedor(comanda.getIdVendedor());
                VendaDAO.upsert(pagamento,conn);

                comanda.setAReceber(comanda.getAReceber()+(-1*Double.valueOf(editValorRecebido.getText().toString())));

                if(comanda.getAReceber()==0)
                {
                    ArrayList<Venda> vendas = VendaDAO.getVendasNaoPagas(comanda,conn);

                    for(Venda v : vendas)
                    {
                        v.setPago(1);
                        v.setIntegrar(1);
                        VendaDAO.upsert(v,conn);
                    }
                }

                comanda.setIntegrar(1);
                ComandaDAO.upsert(comanda, conn);
            }
            else
                msgError = "Preencha o campo 'Valor recebido'.";

        }
        else
            msgError = "Página desatualizada! Selecione um dos nomes sugeridos.";

        if(msgError.equals(""))
        {
            Intent it = new Intent(context, ConsultarComandaFrag.class);
            it.putExtra("NOME", comanda.getNome());
            startActivityForResult(it, 0);
            getActivity().finish();
        }
        else
        {
            AlertDialog.Builder dlg = new AlertDialog.Builder(context);
            dlg.setMessage(msgError);
            dlg.setNeutralButton("OK",null);
            dlg.show();
        }
    }

    public void limparConsulta (View view)
    {
        ConsultarComandaFrag consultarComandaFrag = new ConsultarComandaFrag();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, consultarComandaFrag).addToBackStack("pilha").commit();
    }
}

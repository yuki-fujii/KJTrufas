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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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


public class ConsultarComandaFrag extends Fragment implements AdapterView.OnItemClickListener{

    private DataBase dataBase;
    private SQLiteDatabase conn;

    private AutoCompleteTextView consultaAutoNome;
    private TextView txtConsultaAReceber;
    private TextView txtValorRecebido;
    private ListView lstConsulta;
    private EditText editValorRecebido;
    private ImageView btnLimparConsulta;
    private ImageView btnSalvarConsulta;

    private DadosVendas dadosVendas;
    private DadosComandas dadosComandas;
    private ArrayAdapter<String> adpTodasComandas;
    CustomAdapterComandas customAdapterComandas;
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
        txtValorRecebido = view.findViewById(R.id.txtValorRecebido);
        lstConsulta = view.findViewById(R.id.lstConsulta);
        editValorRecebido = view.findViewById(R.id.editValorRecebido);
        btnLimparConsulta = view.findViewById(R.id.btnLimparConsulta);
        btnSalvarConsulta = view.findViewById(R.id.btnSalvarConsulta);

        if(this.conexaoBD())
        {
            Bundle bundle = getActivity().getIntent().getExtras();

            adpTodasComandas = ComandaDAO.getTodasComandas(context,conn);
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
                customAdapterComandas = new CustomAdapterComandas(getActivity().getApplicationContext(), dadosComandas);
                lstConsulta.setAdapter(customAdapterComandas);
                txtValorRecebido.setVisibility(View.INVISIBLE);
                editValorRecebido.setVisibility(View.INVISIBLE);
                btnSalvarConsulta.setVisibility(View.INVISIBLE);
                //editValorRecebido.setEnabled(false);
            }

            lstConsulta.setOnItemClickListener(this);

            consultaAutoNome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    comanda = ComandaDAO.getComanda(adpTodasComandas.getItem(position),conn);
                    listarVendasNaoPagasPorComanda();
                }
            });

            btnLimparConsulta.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ConsultarComandaFrag consultarComandaFrag = new ConsultarComandaFrag();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, consultarComandaFrag).addToBackStack("pilha").commit();

                }
            });

            btnSalvarConsulta.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    salvarConsultarComanda();
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
        txtValorRecebido.setVisibility(View.VISIBLE);
        editValorRecebido.setVisibility(View.VISIBLE);
        btnSalvarConsulta.setVisibility(View.VISIBLE);
        //editValorRecebido.setEnabled(true);
        dadosVendas = VendaDAO.getVendasNaoPagas(context, comanda.getId(), conn);
        CustomAdapterVendas customAdapterVendas = new CustomAdapterVendas(getActivity().getApplicationContext(), dadosVendas);
        lstConsulta.setAdapter(customAdapterVendas);
    }

    public void salvarConsultarComanda()
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
                Log.i("Valor Recebido",Util.duasCasas(-1*Double.valueOf(editValorRecebido.getText().toString()))+"");
                pagamento.setValorTotal(Util.duasCasas(-1*Double.valueOf(editValorRecebido.getText().toString())));
                pagamento.setPago(0);
                pagamento.setIntegrar(1);
                pagamento.setIdComanda(comanda.getId());
                pagamento.setIdVendedor(comanda.getIdVendedor());
                VendaDAO.upsert(pagamento,conn);

                Log.i("Novo a receber",Util.duasCasas(comanda.getAReceber()+(-1*Double.valueOf(editValorRecebido.getText().toString())))+"");
                comanda.setAReceber(Util.duasCasas(comanda.getAReceber()+(-1*Double.valueOf(editValorRecebido.getText().toString()))));

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
            ConsultarComandaFrag consultarComandaFrag = new ConsultarComandaFrag();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, consultarComandaFrag).addToBackStack("pilha").commit();
        }
        else
        {
            AlertDialog.Builder dlg = new AlertDialog.Builder(context);
            dlg.setMessage(msgError);
            dlg.setNeutralButton("OK",null);
            dlg.show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        comanda = ComandaDAO.getComanda(customAdapterComandas.getItem(position),conn);
        listarVendasNaoPagasPorComanda();
        consultaAutoNome.setText(comanda.getNome());
    }
}

package com.br.kjtrufas;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.br.kjtrufas.entidades.Comanda;
import com.br.kjtrufas.entidades.Produto;
import com.br.kjtrufas.entidades.Sabor;
import com.br.kjtrufas.entidades.Venda;
import com.br.kjtrufas.sql.ComandaDAO;
import com.br.kjtrufas.sql.DataBase;
import com.br.kjtrufas.sql.ProdutoDAO;
import com.br.kjtrufas.sql.SaborDAO;
import com.br.kjtrufas.sql.VendaDAO;
import com.br.kjtrufas.sql.VendedorDAO;
import com.br.kjtrufas.suporte.Util;

import java.text.NumberFormat;
import java.util.ArrayList;


public class VendasFrag extends Fragment {

    private DataBase dataBase;
    private SQLiteDatabase conn;

    private AutoCompleteTextView autoNome;
    private ArrayAdapter<String> adpTodasComandas;
    private ArrayAdapter<Produto> adpTodosProdutos;
    private ArrayAdapter<Sabor> adpTodosSabores;
    private Comanda comanda;

    private Spinner spnProdutos;
    private Spinner spnSabores;
    private EditText editQtde;
    private EditText editDesconto;
    private EditText editAcrescimo;
    private CheckBox cbxPago;
    private TextView txtValorTotal;
    private Produto auxProduto;
    private Sabor auxSabor;
    private Button btnVendido;

    private boolean possuiCreditos;
    private boolean desconto100;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_vendas_frag, null);
        super.onCreate(savedInstanceState);

        context = container.getContext();
        autoNome = view.findViewById(R.id.autoNome);
        spnProdutos = view.findViewById(R.id.spnProdutos);
        spnSabores = view.findViewById(R.id.spnSabores);
        txtValorTotal = view.findViewById(R.id.txtValorTotal);
        editQtde = view.findViewById(R.id.editQtde);
        editDesconto = view.findViewById(R.id.editDesconto);
        editAcrescimo = view.findViewById(R.id.editAcrescimo);
        cbxPago = view.findViewById(R.id.cbxPago);
        btnVendido = view.findViewById(R.id.btnVendido);

        if(this.conexaoBD())
        {
            String nome=null;

            if(getArguments()!=null)
                nome = getArguments().getString("NOME");

            if (nome != null)
                autoNome.setText(nome);

            this.adpTodasComandas = ComandaDAO.getTodasComandas(context,conn);

            autoNome.setAdapter(adpTodasComandas);

            adpTodosProdutos = ProdutoDAO.getProduto(context,conn,1);
            spnProdutos.setAdapter(adpTodosProdutos);

            adpTodosSabores = SaborDAO.getSabor(context,conn,1);
            spnSabores.setAdapter(adpTodosSabores);

            autoNome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    comanda = ComandaDAO.getComanda(adpTodasComandas.getItem(position),conn);
                    Log.i("A Receber",comanda.getAReceber()+"");

                    if(comanda.getAReceber()<0) {
                        editDesconto.setText(String.valueOf(comanda.getAReceber()*(-1)));
                        possuiCreditos = true;
                        editDesconto.setFocusable(false);
                    }
                }
            });

            spnProdutos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    if(position>0) {
                        auxProduto = adpTodosProdutos.getItem(position);

                        if(calcularTotal()!=null)
                        {
                            if(possuiCreditos)
                            {
                                txtValorTotal.setText(String.valueOf(NumberFormat.getCurrencyInstance().format(calcularTotal() + comanda.getAReceber())));
                                if((calcularTotal() + comanda.getAReceber())<=0)
                                {
                                    cbxPago.setChecked(true);
                                    desconto100 = true;
                                }
                                else
                                    desconto100= false;
                            }
                            else
                                txtValorTotal.setText(String.valueOf(NumberFormat.getCurrencyInstance().format(calcularTotal())));
                        }
                        else
                            txtValorTotal.setText("");
                    }
                    else
                        txtValorTotal.setText("");
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            spnSabores.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    if(position>0)
                        auxSabor = adpTodosSabores.getItem(position);
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            editQtde.addTextChangedListener(new TextWatcher() {
                public void afterTextChanged(Editable s) {}
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    if(auxProduto!=null)
                    {
                        if(calcularTotal()!=null) {
                            if(possuiCreditos)
                            {
                                txtValorTotal.setText(String.valueOf(NumberFormat.getCurrencyInstance().format(calcularTotal() + comanda.getAReceber())));
                                if((calcularTotal() + comanda.getAReceber())<=0)
                                {
                                    cbxPago.setChecked(true);
                                    desconto100 = true;
                                }
                                else
                                    desconto100= false;
                            }
                            else
                                txtValorTotal.setText(String.valueOf(NumberFormat.getCurrencyInstance().format(calcularTotal())));
                        }
                        else
                            txtValorTotal.setText("");
                    }
                    else
                        txtValorTotal.setText("");
                }
            });

            editDesconto.addTextChangedListener(new TextWatcher() {
                public void afterTextChanged(Editable s) {}
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    if(auxProduto!=null)
                    {
                        if(calcularTotal()!=null)
                            txtValorTotal.setText(String.valueOf(NumberFormat.getCurrencyInstance().format(calcularTotal())));
                        else
                            txtValorTotal.setText("");
                    }
                    else
                        txtValorTotal.setText("");
                }
            });

            editAcrescimo.addTextChangedListener(new TextWatcher() {
                public void afterTextChanged(Editable s) {}
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    if(auxProduto!=null)
                    {
                        if(calcularTotal()!=null)
                            txtValorTotal.setText(String.valueOf(NumberFormat.getCurrencyInstance().format(calcularTotal())));
                        else
                            txtValorTotal.setText("");
                    }
                    else
                        txtValorTotal.setText("");
                }
            });

            btnVendido.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    salvarVendido();
                }
            });

        }

        return(view);

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

    public Double calcularTotal()
    {
        Double retorno = null;

        if (editQtde.getText() != null && (!editQtde.getText().toString().equals(""))) {
            Double desconto;
            Double acrescimo;

            int quantidade = Integer.valueOf(editQtde.getText().toString());

            if (editDesconto.getText() != null && (!editDesconto.getText().toString().equals("")) && (!possuiCreditos))
                desconto = Util.duasCasas(Double.valueOf(editDesconto.getText().toString()));
            else
                desconto = (double) 0;

            if (editAcrescimo.getText() != null && (!editAcrescimo.getText().toString().equals("")))
                acrescimo = Util.duasCasas(Double.valueOf(editAcrescimo.getText().toString()));
            else
                acrescimo = (double) 0;

            retorno = Util.duasCasas(quantidade * auxProduto.getPreco() - desconto + acrescimo);
        }

        return retorno;
    }

    public void salvarVendido()
    {
        Comanda comanda = ComandaDAO.getComanda(autoNome.getText().toString(),conn);

        if(comanda!=null) {

            comanda.setIntegrar(1);

            if(!cbxPago.isChecked() || desconto100 || (cbxPago.isChecked() && calcularTotal()<0))
                comanda.setAReceber(comanda.getAReceber() + calcularTotal());

            if(cbxPago.isChecked() && (!desconto100) && possuiCreditos)
                comanda.setAReceber(0.0);
        }
        else {
            if(cbxPago.isChecked() && calcularTotal()>=0)
                comanda = new Comanda(autoNome.getText().toString(), VendedorDAO.getVendedor(conn).getId(), 0.0,1);
            else
                comanda = new Comanda(autoNome.getText().toString(), VendedorDAO.getVendedor(conn).getId(), calcularTotal(),1);
        }

        Log.i("Comanda saldo",""+comanda.getAReceber());

        ComandaDAO.upsert(comanda, conn);
        comanda = ComandaDAO.getComanda(comanda.getNome(),conn);

        Double desconto;
        Double acrescimo;

        if (editDesconto.getText() != null && (!editDesconto.getText().toString().equals("")) && (!possuiCreditos))
            desconto = Double.valueOf(editDesconto.getText().toString());
        else
            desconto = (double) 0;

        if (editAcrescimo.getText() != null && (!editAcrescimo.getText().toString().equals("")))
            acrescimo = Double.valueOf(editAcrescimo.getText().toString());
        else
            acrescimo = (double) 0;

        Venda novaVenda = new Venda(comanda.getId(), auxProduto.getId(), null,VendedorDAO.getVendedor(conn).getId(), Integer.valueOf(editQtde.getText().toString()),
                acrescimo, desconto, calcularTotal(),
                editQtde.getText().toString()+"x "+auxProduto.getNome(), Util.getDataAtual(), Util.converterBoolean(cbxPago.isChecked()),1);

        if(auxSabor!=null)
            novaVenda.setSabor(auxSabor.getId());

        VendaDAO.upsert(novaVenda, conn);

        if(comanda.getAReceber()==0)
        {
            ArrayList<Venda> vendas = VendaDAO.getVendasNaoPagas(comanda,conn);

            for(Venda v : vendas)
            {
                v.setPago(1);
                VendaDAO.upsert(v,conn);
            }
        }

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        atualizarTela(true);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        atualizarTela(false);
                        break;
                }
            }
        };

        AlertDialog.Builder dlg = new AlertDialog.Builder(context);
        dlg.setMessage("Realizar outra venda para esse cliente?").setPositiveButton("Sim", dialogClickListener).setNegativeButton("Não", dialogClickListener);
        dlg.show();

    }

    public void atualizarTela(boolean hasBundle)
    {
        VendasFrag vendasFrag = new VendasFrag();

        if(hasBundle)
        {
            Bundle bundle = new Bundle();
            bundle.putString("NOME", String.valueOf(this.autoNome.getText()));
            vendasFrag.setArguments(bundle);
        }

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, vendasFrag).addToBackStack("pilha").commit();

    }

}

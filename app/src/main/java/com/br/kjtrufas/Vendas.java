package com.br.kjtrufas;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.br.kjtrufas.entidades.Produto;
import com.br.kjtrufas.entidades.Sabor;
import com.br.kjtrufas.sql.ComandaDAO;
import com.br.kjtrufas.sql.DataBase;
import com.br.kjtrufas.sql.ProdutoDAO;
import com.br.kjtrufas.sql.SaborDAO;
import com.br.kjtrufas.sql.VendedorDAO;

import com.br.kjtrufas.entidades.Comanda;

import java.text.NumberFormat;

public class Vendas extends AppCompatActivity {

    private DataBase dataBase;
    private SQLiteDatabase conn;

    private AutoCompleteTextView autoNome;
    private ArrayAdapter<String> adpTodasComandas;
    private ArrayAdapter<Produto> adpTodosProdutos;
    private ArrayAdapter<Sabor> adpTodosSabores;

    private Spinner spnProdutos;
    private Spinner spnSabores;
    private EditText editQtde;
    private EditText editDesconto;
    private EditText editAcrescimo;
    private TextView txtValorTotal;
    private Produto auxProduto;
    private Sabor auxSabor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendas);

        autoNome = findViewById(R.id.autoNome);
        spnProdutos = findViewById(R.id.spnProdutos);
        spnSabores = findViewById(R.id.spnSabores);
        txtValorTotal = findViewById(R.id.txtValorTotal);
        editQtde = findViewById(R.id.editQtde);
        editDesconto = findViewById(R.id.editDesconto);
        editAcrescimo = findViewById(R.id.editAcrescimo);

        if(this.conexaoBD())
        {
            Bundle bundle = getIntent().getExtras();

            if ((bundle != null) && (bundle.containsKey("NOME")))
                autoNome.setText((String) bundle.getSerializable("NOME"));

            this.adpTodasComandas = ComandaDAO.getComanda(this,conn);
            autoNome.setAdapter(adpTodasComandas);

            adpTodosProdutos = ProdutoDAO.getProduto(this,conn,1);
            spnProdutos.setAdapter(adpTodosProdutos);

            adpTodosSabores = SaborDAO.getSabor(this,conn,1);
            Log.i("adpTodosSabores",adpTodosSabores.getItem(1).getNome());
            spnSabores.setAdapter(adpTodosSabores);

            spnProdutos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    if(position>0) {
                        auxProduto = adpTodosProdutos.getItem(position);

                        if (editQtde.getText() != null && (!editQtde.getText().toString().equals("")))
                        {
                            Double desconto;
                            Double acrescimo;
                            Double valorTotal;

                            int quantidade = Integer.valueOf(editQtde.getText().toString());

                            if (editDesconto.getText() != null && (!editDesconto.getText().toString().equals("")))
                                desconto = Double.valueOf(editDesconto.getText().toString());
                            else
                                desconto = (double) 0;

                            if (editAcrescimo.getText() != null && (!editAcrescimo.getText().toString().equals("")))
                                acrescimo = Double.valueOf(editAcrescimo.getText().toString());
                            else
                                acrescimo = (double) 0;

                            valorTotal = quantidade*auxProduto.getPreco()-desconto+acrescimo;

                            txtValorTotal.setText(String.valueOf(NumberFormat.getCurrencyInstance().format(valorTotal)));
                        }
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
                        if (editQtde.getText() != null && (!editQtde.getText().toString().equals("")))
                        {
                            Double desconto;
                            Double acrescimo;
                            Double valorTotal;

                            int quantidade = Integer.valueOf(editQtde.getText().toString());

                            if (editDesconto.getText() != null && (!editDesconto.getText().toString().equals("")))
                                desconto = Double.valueOf(editDesconto.getText().toString());
                            else
                                desconto = (double) 0;

                            if (editAcrescimo.getText() != null && (!editAcrescimo.getText().toString().equals("")))
                                acrescimo = Double.valueOf(editAcrescimo.getText().toString());
                            else
                                acrescimo = (double) 0;

                            valorTotal = quantidade*auxProduto.getPreco()-desconto+acrescimo;

                            txtValorTotal.setText(String.valueOf(NumberFormat.getCurrencyInstance().format(valorTotal)));
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
                        if (editQtde.getText() != null && (!editQtde.getText().toString().equals("")))
                        {
                            Double desconto;
                            Double acrescimo;
                            Double valorTotal;

                            int quantidade = Integer.valueOf(editQtde.getText().toString());

                            if (editDesconto.getText() != null && (!editDesconto.getText().toString().equals("")))
                                desconto = Double.valueOf(editDesconto.getText().toString());
                            else
                                desconto = (double) 0;

                            if (editAcrescimo.getText() != null && (!editAcrescimo.getText().toString().equals("")))
                                acrescimo = Double.valueOf(editAcrescimo.getText().toString());
                            else
                                acrescimo = (double) 0;

                            valorTotal = quantidade*auxProduto.getPreco()-desconto+acrescimo;

                            txtValorTotal.setText(String.valueOf(NumberFormat.getCurrencyInstance().format(valorTotal)));
                        }
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
                        if (editQtde.getText() != null && (!editQtde.getText().toString().equals("")))
                        {
                            Double desconto;
                            Double acrescimo;
                            Double valorTotal;

                            int quantidade = Integer.valueOf(editQtde.getText().toString());

                            if (editDesconto.getText() != null && (!editDesconto.getText().toString().equals("")))
                                desconto = Double.valueOf(editDesconto.getText().toString());
                            else
                                desconto = (double) 0;

                            if (editAcrescimo.getText() != null && (!editAcrescimo.getText().toString().equals("")))
                                acrescimo = Double.valueOf(editAcrescimo.getText().toString());
                            else
                                acrescimo = (double) 0;

                            valorTotal = quantidade*auxProduto.getPreco()-desconto+acrescimo;

                            txtValorTotal.setText(String.valueOf(NumberFormat.getCurrencyInstance().format(valorTotal)));
                        }
                        else
                            txtValorTotal.setText("");
                    }
                    else
                        txtValorTotal.setText("");
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

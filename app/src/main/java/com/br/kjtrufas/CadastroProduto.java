package com.br.kjtrufas;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.br.kjtrufas.entidades.Produto;
import com.br.kjtrufas.sql.DataBase;
import com.br.kjtrufas.sql.ProdutoDAO;

import java.text.NumberFormat;

public class CadastroProduto extends AppCompatActivity {

    private DataBase dataBase;
    private SQLiteDatabase conn;

    private Spinner spnProduto;
    private ArrayAdapter<Produto> adpTodosProdutos;
    private EditText nomeProduto;
    private EditText preco;
    private CheckBox ativo;
    private String msgError;
    private Produto produto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_produto);

        spnProduto = findViewById(R.id.spnProdutos);
        nomeProduto = findViewById(R.id.editNomeProduto);
        preco = findViewById(R.id.editPreco);
        ativo = findViewById(R.id.cbxProdutoAtivo);

        if(conexaoBD())
        {
            adpTodosProdutos = ProdutoDAO.getProduto(this, conn,0);
            spnProduto.setAdapter(adpTodosProdutos);

            spnProduto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    if(position>0)
                    {
                        produto = adpTodosProdutos.getItem(position);
                        nomeProduto.setText(produto.getNome());
                        preco.setText("R"+String.valueOf(NumberFormat.getCurrencyInstance().format(produto.getPreco())));
                        if(produto.getAtivo()==1)
                            ativo.setChecked(true);
                        else
                            ativo.setChecked(false);
                    }
                    else
                    {
                        nomeProduto.setText("");
                        preco.setText("");
                        ativo.setChecked(false);
                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
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

    public void salvarProduto(View view) {

        if (validarCampos())
        {
            ProdutoDAO.upsert(produto, conn);

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
            dlg.setMessage("Adicionar outro produto?").setPositiveButton("Sim", dialogClickListener).setNegativeButton("Não", dialogClickListener);
            dlg.show();
        }
        else
        {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setMessage(msgError);
            dlg.setNeutralButton("OK",null);
            dlg.show();
        }

    }

    public void exluirProduto(View view)
    {
        if(validarCampos())
        {
            ProdutoDAO.delete(produto.getNome(),conn);
        }
    }

    private boolean validarCampos()
    {
        if(nomeProduto.getText()==null || nomeProduto.getText().toString().equals(""))
        {
            msgError = "Preencha o campo Produto";
            return false;
        }

        if(preco.getText()==null || preco.getText().toString().equals(""))
        {
            msgError = "Preencha o campo Preço";
            return false;
        }

        produto = new Produto(nomeProduto.getText().toString(),Double.valueOf(preco.getText().toString()),0);

        if(ativo.isChecked())
            produto.setAtivo(1);

        return true;
    }

    public void atualizarTela()
    {
        Intent it = new Intent(this, CadastroProduto.class);
        startActivityForResult(it, 0);
        finish();
    }
}

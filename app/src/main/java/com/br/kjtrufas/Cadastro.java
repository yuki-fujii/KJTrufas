package com.br.kjtrufas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Cadastro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

    }

    public void chamarCadVendedor (View view) {
        Intent it = new Intent(this, CadastroVendedor.class);
        startActivityForResult(it, 0);
    }

    public void chamarCadProduto (View view) {
        Intent it = new Intent(this, CadastroProduto.class);
        startActivityForResult(it, 0);
    }

    public void chamarCadSabor (View view) {
        Intent it = new Intent(this, CadastroSabor.class);
        startActivityForResult(it, 0);
    }
}

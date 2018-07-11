package com.br.kjtrufas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Cadastro extends AppCompatActivity {

    private Button btnCadVendedor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        btnCadVendedor = (Button)findViewById(R.id.btnCadVendedor);

    }

    public void chamarCadVendedor (View view) {
        Intent it = new Intent(this, CadastroVendedor.class);
        startActivityForResult(it, 0);
    }
}

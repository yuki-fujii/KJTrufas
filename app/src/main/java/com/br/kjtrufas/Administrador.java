package com.br.kjtrufas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Administrador extends AppCompatActivity {

    private Button btnCadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrador);

        btnCadastro = (Button) findViewById(R.id.btnCadastrar);
    }

    public void chamarCadastro (View view) {
        Intent it = new Intent(this, Cadastro.class);
        startActivityForResult(it, 0);
    }
}

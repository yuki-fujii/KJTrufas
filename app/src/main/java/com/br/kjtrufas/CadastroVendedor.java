package com.br.kjtrufas;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.br.kjtrufas.sql.DataBase;
import com.br.kjtrufas.sql.VendedorDAO;

import com.br.kjtrufas.entidades.Vendedor;

public class CadastroVendedor extends AppCompatActivity {

    private DataBase dataBase;
    private SQLiteDatabase conn;

    private Button btnSalvar;
    private EditText editNome;
    private EditText editLogin;
    private EditText editSenha;
    private EditText editConfSenha;
    private String msgError;

    private Vendedor newVendedor;
    private VendedorDAO vendedorDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_vendedor);

        btnSalvar = (Button) findViewById(R.id.btnSalvar);
        editNome = (EditText)findViewById(R.id.editName);
        editLogin = (EditText)findViewById(R.id.editLogin);
        editSenha = (EditText)findViewById(R.id.editSenha);
        editConfSenha = (EditText)findViewById(R.id.editConfSenha);



    }

    public void salvarVendedor(View view)
    {
        newVendedor = new Vendedor("2908",editNome.getText().toString(),
                editLogin.getText().toString(),
                editSenha.getText().toString(),
                editConfSenha.getText().toString());

        if(validarDadosVendedor(newVendedor))
        {
            Log.i("Error","Entrou");
            if(conexaoBD())
            {
                dataBase = new DataBase(this);
                conn = dataBase.getWritableDatabase();

                VendedorDAO.upsert(newVendedor,conn);

                Log.i("ID Vendedor",VendedorDAO.getVendedor(conn).getId());

            }
            else
            {
                Toast errorToast = Toast.makeText(this, "Erro ao conectar com banco de dados.", Toast.LENGTH_SHORT);
                errorToast.show();
            }
        }
        else
        {
            Log.i("Error","Não entrou");
            Toast errorToast = Toast.makeText(this, msgError, Toast.LENGTH_SHORT);
            errorToast.show();
        }

    }

    public Boolean validarDadosVendedor(Vendedor vendedor)
    {
        if(vendedor.getNome()==null || vendedor.getNome().equals(""))
        {
            msgError = "Preencha o campo nome.";
            return false;
        }
        else if(vendedor.getLogin()==null || vendedor.getLogin().equals(""))
        {
            msgError = "Preencha o campo login.";
            return false;
        }
        else if(vendedor.getSenha()==null || vendedor.getSenha().equals(""))
        {
            msgError = "Preencha o campo senha.";
            return false;
        }
        else if(vendedor.getConfSenha()==null || vendedor.getConfSenha().equals(""))
        {
            msgError = "Preencha o campo confirmação de senha.";
            return false;
        }
        else if(!vendedor.getSenha().equals(vendedor.getConfSenha()))
        {
            msgError = "A confirmação de senha está diferente da senha.";
            return false;
        }
        else
            return true;

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
}

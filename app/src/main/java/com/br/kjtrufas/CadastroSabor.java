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

import com.br.kjtrufas.entidades.Sabor;
import com.br.kjtrufas.sql.DataBase;
import com.br.kjtrufas.sql.ProdutoDAO;
import com.br.kjtrufas.sql.SaborDAO;

public class CadastroSabor extends AppCompatActivity {

    private DataBase dataBase;
    private SQLiteDatabase conn;

    private Spinner spnSabor;
    private ArrayAdapter<Sabor> adpTodosSabores;
    private EditText nomeSabor;
    private CheckBox ativo;
    private String msgError;
    private Sabor sabor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_sabor);

        spnSabor = findViewById(R.id.spnSabores);
        nomeSabor = findViewById(R.id.editNomeSabor);
        ativo = findViewById(R.id.cbxSaborAtivo);

        if(conexaoBD())
        {
            adpTodosSabores = SaborDAO.getSabor(this, conn,0);
            spnSabor.setAdapter(adpTodosSabores);

            spnSabor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    if(position>0)
                    {
                        sabor = adpTodosSabores.getItem(position);
                        nomeSabor.setText(sabor.getNome());
                        if(sabor.getAtivo()==1)
                            ativo.setChecked(true);
                        else
                            ativo.setChecked(false);
                    }
                    else
                    {
                        nomeSabor.setText("");
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

    public void salvarSabor(View view) {

        if (validarCampos())
        {
            SaborDAO.upsert(sabor, conn);

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
            dlg.setMessage("Adicionar outro sabor?").setPositiveButton("Sim", dialogClickListener).setNegativeButton("Não", dialogClickListener);
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

    public void exluirSabor(View view)
    {
        if(validarCampos())
        {
            ProdutoDAO.delete(sabor.getNome(),conn);
        }
    }

    private boolean validarCampos()
    {
        if(nomeSabor.getText()==null || nomeSabor.getText().toString().equals(""))
        {
            msgError = "Preencha o campo Sabor";
            return false;
        }

        sabor = new Sabor(nomeSabor.getText().toString(),0);

        if(ativo.isChecked())
            sabor.setAtivo(1);

        return true;
    }

    public void atualizarTela()
    {
        Intent it = new Intent(this, CadastroSabor.class);
        startActivityForResult(it, 0);
        finish();
    }


}

package com.br.kjtrufas.salesforce;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.br.kjtrufas.entidades.Comanda;
import com.br.kjtrufas.entidades.EntidadePost;
import com.br.kjtrufas.entidades.EntidadesEncapsuladas;
import com.br.kjtrufas.entidades.EnviarRegistro;
import com.br.kjtrufas.entidades.Produto;
import com.br.kjtrufas.entidades.ProdutoDisponivel;
import com.br.kjtrufas.entidades.Sabor;
import com.br.kjtrufas.entidades.Venda;
import com.br.kjtrufas.entidades.Vendedor;
import com.br.kjtrufas.sql.ComandaDAO;
import com.br.kjtrufas.sql.ProdutoDAO;
import com.br.kjtrufas.sql.ProdutoDisponivelDAO;
import com.br.kjtrufas.sql.SaborDAO;
import com.br.kjtrufas.sql.VendaDAO;
import com.br.kjtrufas.sql.VendedorDAO;
import com.google.gson.Gson;

public class TratamentoRespostaPost
{
    public static void tratarResposta(EntidadePost entidadePost, String resposta)
    {
        if(entidadePost.getService().equals("login"))
            respostaLogin(entidadePost.getConn(), resposta);

        else if(entidadePost.getService().equals("registroWS"))
            respostaRegistroWS(entidadePost.getConn(), resposta);
    }

    private static void respostaRegistroWS(SQLiteDatabase conn, String resposta)
    {
        Gson gson = new Gson();
        EnviarRegistro enviarRegistro = gson.fromJson(resposta,EnviarRegistro.class);

        for (Comanda c : enviarRegistro.getComandas())
        {
            Log.i("Id",c.getId());
            ComandaDAO.upsert(c,conn);
        }

        for (Venda v : enviarRegistro.getVendas())
        {
            VendaDAO.upsert(v,conn);
        }

        Log.i("registroResposta","Atualizado");

    }

    private static void respostaLogin(SQLiteDatabase conn, String resposta)
    {
        Gson gson = new Gson();
        EntidadesEncapsuladas entidadesEncapsuladas = gson.fromJson(resposta,EntidadesEncapsuladas.class);

        if(entidadesEncapsuladas.getVendedor().getId() != null)
            VendedorDAO.upsert(entidadesEncapsuladas.getVendedor(),conn);

        if(!entidadesEncapsuladas.getProdutos().isEmpty())
        {
            for (Produto p: entidadesEncapsuladas.getProdutos())
            {
                ProdutoDAO.upsert(p,conn);
            }
        }

        if(!entidadesEncapsuladas.getSabores().isEmpty())
        {
            for (Sabor s: entidadesEncapsuladas.getSabores())
            {
                SaborDAO.upsert(s,conn);
            }
        }

        if(!entidadesEncapsuladas.getProdutosDisponiveis().isEmpty())
        {
            for (ProdutoDisponivel pd : entidadesEncapsuladas.getProdutosDisponiveis())
            {
                ProdutoDisponivelDAO.upsert(pd,conn);
            }
        }
    }
}

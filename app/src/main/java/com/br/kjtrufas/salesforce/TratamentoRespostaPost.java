package com.br.kjtrufas.salesforce;

import android.database.sqlite.SQLiteDatabase;

import com.br.kjtrufas.entidades.EntidadePost;
import com.br.kjtrufas.entidades.EntidadesEncapsuladas;
import com.br.kjtrufas.entidades.Produto;
import com.br.kjtrufas.entidades.ProdutoDisponivel;
import com.br.kjtrufas.entidades.Sabor;
import com.br.kjtrufas.entidades.Vendedor;
import com.br.kjtrufas.sql.ProdutoDAO;
import com.br.kjtrufas.sql.ProdutoDisponivelDAO;
import com.br.kjtrufas.sql.SaborDAO;
import com.br.kjtrufas.sql.VendedorDAO;
import com.google.gson.Gson;

public class TratamentoRespostaPost
{
    public static void tratarResposta(EntidadePost entidadePost, String resposta)
    {
        if(entidadePost.getService().equals("login"))
            respostaLogin(entidadePost.getConn(), resposta);
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

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
import com.br.kjtrufas.suporte.Util;
import com.google.gson.Gson;

public class TratamentoRespostaPost
{
    public static void tratarResposta(EntidadePost entidadePost, String resposta)
    {
        resposta(entidadePost.getConn(), resposta);
        /*if(entidadePost.getService().equals("login"))
            respostaLogin(entidadePost.getConn(), resposta);

        else if(entidadePost.getService().equals("registroWS"))
            respostaRegistroWS(entidadePost.getConn(), resposta);*/
    }

    /*
    private static void respostaRegistroWS(SQLiteDatabase conn, String resposta)
    {
        Gson gson = new Gson();
        EntidadesEncapsuladas enviarRegistro = gson.fromJson(resposta,EntidadesEncapsuladas.class);

        try
        {
            for (Comanda c : enviarRegistro.getEnviarRegistro().getComandas())
            {
                ComandaDAO.upsert(c,conn);
            }

            for (Venda v : enviarRegistro.getEnviarRegistro().getVendas())
            {
                v.setDataVenda(Util.convertDataSF(v.getDataVenda()));
                VendaDAO.upsert(v,conn);
            }
        }
        catch (Exception e)
        {
            Log.i("Erro","Falha na integração.\n"+e.getMessage());
        }
    }*/

    private static void resposta(SQLiteDatabase conn, String resposta)
    {
        Gson gson = new Gson();
        EntidadesEncapsuladas entidadesEncapsuladas = gson.fromJson(resposta,EntidadesEncapsuladas.class);

        try {
            if (entidadesEncapsuladas.getVendedor() != null)
                VendedorDAO.upsert(entidadesEncapsuladas.getVendedor(), conn);

            if (!entidadesEncapsuladas.getProdutos().isEmpty()) {
                for (Produto p : entidadesEncapsuladas.getProdutos()) {
                    ProdutoDAO.upsert(p, conn);
                }
            }

            if (!entidadesEncapsuladas.getSabores().isEmpty()) {
                for (Sabor s : entidadesEncapsuladas.getSabores()) {
                    SaborDAO.upsert(s, conn);
                }
            }

            if (!entidadesEncapsuladas.getProdutosDisponiveis().isEmpty()) {
                for (ProdutoDisponivel pd : entidadesEncapsuladas.getProdutosDisponiveis()) {
                    ProdutoDisponivelDAO.upsert(pd, conn);
                }
            }

            if (!entidadesEncapsuladas.getEnviarRegistro().getComandas().isEmpty()) {
                for (Comanda c : entidadesEncapsuladas.getEnviarRegistro().getComandas()) {
                    ComandaDAO.upsert(c, conn);
                }
            }

            if (!entidadesEncapsuladas.getEnviarRegistro().getVendas().isEmpty()) {
                for (Venda v : entidadesEncapsuladas.getEnviarRegistro().getVendas()) {
                    v.setDataVenda(Util.convertDataSF(v.getDataVenda()));
                    VendaDAO.upsert(v, conn);
                }
            }
        }
        catch (Exception e)
        {
            Log.i("Erro","Login ou senha errado.\n"+e.getMessage());
        }
    }
}

package com.br.kjtrufas.salesforce;

import android.database.sqlite.SQLiteDatabase;

import com.br.kjtrufas.entidades.EntidadePost;
import com.br.kjtrufas.entidades.Vendedor;
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
        Vendedor vendedor = gson.fromJson(resposta,Vendedor.class);

        if(vendedor.getId() != null)
            VendedorDAO.upsert(vendedor,conn);
    }
}

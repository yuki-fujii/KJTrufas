package com.br.kjtrufas.sql;

import android.util.Log;

public class ScriptSQL
{
    public static String getVendedor()
    {
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("CREATE TABLE IF NOT EXISTS VENDEDOR (                  ");
        sqlBuilder.append("ID_VENDEDOR INTEGER PRIMARY KEY AUTOINCREMENT        , ");
        sqlBuilder.append("LOGIN                           VARCHAR (15) UNIQUE  , ");
        sqlBuilder.append("SENHA                                   VARCHAR (15) , ");
        sqlBuilder.append("CONF_SENHA                              VARCHAR (15) , ");
        sqlBuilder.append("NOME                                    VARCHAR (30)   ");
        sqlBuilder.append(");");

        return sqlBuilder.toString();
    }

    public static String getComanda()
    {
        Log.i("Erro","Criou get comanda");
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("CREATE TABLE IF NOT EXISTS COMANDA (        ");
        sqlBuilder.append("ID_COMANDA                      VARCHAR (10)");
        sqlBuilder.append("PRIMARY KEY                               , ");
        sqlBuilder.append("ID_VENDEDOR                  VARCHAR (10) , ");
        sqlBuilder.append("NOME                         VARCHAR (35) , ");
        sqlBuilder.append("SALDO                         DECIMAL(10,2) ");
        sqlBuilder.append(");");

        return sqlBuilder.toString();
    }

    public static String getProduto()
    {
        Log.i("Erro","Criou get produto");
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("CREATE TABLE IF NOT EXISTS PRODUTO (        ");
        sqlBuilder.append("ID_PRODUTO                      VARCHAR (10)");
        sqlBuilder.append("PRIMARY KEY                               , ");
        sqlBuilder.append("NOME                         VARCHAR (30) , ");
        sqlBuilder.append("PRECO                       DECIMAL(10,2) , ");
        sqlBuilder.append("ATIVO                       INTEGER         ");
        sqlBuilder.append(");");

        return sqlBuilder.toString();
    }

    public static String getSabor()
    {
        Log.i("Erro","Criou get sabor");
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("CREATE TABLE IF NOT EXISTS SABOR (          ");
        sqlBuilder.append("ID_SABOR                        VARCHAR (10)");
        sqlBuilder.append("PRIMARY KEY                               , ");
        sqlBuilder.append("NOME                         VARCHAR (30) , ");
        sqlBuilder.append("ATIVO                         INTEGER       ");
        sqlBuilder.append(");");

        return sqlBuilder.toString();
    }

    public static String getProdutoDisponivel()
    {
        Log.i("Erro","Criou get prod dis");
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("CREATE TABLE IF NOT EXISTS PRODUTO_DISPONIVEL (       ");
        sqlBuilder.append("ID_SABOR                               VARCHAR (10) , ");
        sqlBuilder.append("ID_PRODUTO                             VARCHAR (10) , ");
        sqlBuilder.append("ID_VENDEDOR                            VARCHAR (10) , ");
        sqlBuilder.append("QUANTIDADE                                  INTEGER , ");
        sqlBuilder.append("PRIMARY KEY                     (ID_SABOR,ID_PRODUTO) ");
        sqlBuilder.append(");");

        return sqlBuilder.toString();
    }

    public static String getVendido()
    {
        Log.i("Erro","Criou get vendido");
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("CREATE TABLE IF NOT EXISTS VENDIDO (                      ");
        sqlBuilder.append("ID_COMANDA                                 VARCHAR (10) , ");
        sqlBuilder.append("ID_PRODUTO                                 VARCHAR (10) , ");
        sqlBuilder.append("ID_SABOR                                   VARCHAR (10) , ");
        sqlBuilder.append("ID_VENDEDOR                                VARCHAR (10) , ");
        sqlBuilder.append("QUANTIDADE                                      INTEGER , ");
        sqlBuilder.append("ACRESCIMO                                 DECIMAL(10,2) , ");
        sqlBuilder.append("DESCONTO                                  DECIMAL(10,2) , ");
        sqlBuilder.append("DATA_VENDA                                 VARCHAR (10) , ");
        sqlBuilder.append("PAGO                                            INTEGER , ");
        sqlBuilder.append("PRIMARY KEY           (ID_COMANDA,ID_PRODUTO, DATA_VENDA) ");
        sqlBuilder.append(");");

        return sqlBuilder.toString();
    }

}

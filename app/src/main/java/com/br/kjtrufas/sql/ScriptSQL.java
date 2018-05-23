package com.br.kjtrufas.sql;

public class ScriptSQL
{
    public static String getVendedor()
    {
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("CREATE TABLE IF NOT EXISTS VENDIDO (                  ");
        sqlBuilder.append("ID_VENDEDOR                            VARCHAR (10)   ");
        sqlBuilder.append("PRIMARY KEY                                         , ");
        sqlBuilder.append("LOGIN                                  VARCHAR (15) , ");
        sqlBuilder.append("SENHA                                  VARCHAR (15) , ");
        sqlBuilder.append("NOME                                   VARCHAR (30)   ");
        sqlBuilder.append(");");

        return sqlBuilder.toString();
    }

    public static String getComanda()
    {
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("CREATE TABLE IF NOT EXISTS COMANDA (        ");
        sqlBuilder.append("ID_COMANDA                      VARCHAR (10)");
        sqlBuilder.append("PRIMARY KEY                               , ");
        sqlBuilder.append("NOME                         VARCHAR (30) , ");
        sqlBuilder.append("COMPLEMENTO                  VARCHAR (30)   ");
        sqlBuilder.append(");");

        return sqlBuilder.toString();
    }

    public static String getProduto()
    {
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
        sqlBuilder.append("PRIMARY KEY           (ID_COMANDA,ID_PRODUTO, DATA_VENDA),");
        sqlBuilder.append(");");

        return sqlBuilder.toString();
    }

}

package com.br.kjtrufas.sql;

import android.util.Log;

import org.junit.BeforeClass;

public class ScriptSQL
{
    public static String getVendedor()
    {
        Log.i("Erro","Criou get Vendedor");
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("CREATE TABLE IF NOT EXISTS VENDEDOR (                  ");
        sqlBuilder.append("ID_VENDEDOR                      VARCHAR (18) UNIQUE , ");
        sqlBuilder.append("LOGIN                            VARCHAR (15) UNIQUE , ");
        sqlBuilder.append("SENHA                                   VARCHAR (15) , ");
        sqlBuilder.append("CONF_SENHA                              VARCHAR (15) , ");
        sqlBuilder.append("NOME                                    VARCHAR (30) , ");
        sqlBuilder.append("ID_MOBILE_COMANDA                            INTEGER , ");
        sqlBuilder.append("ID_MOBILE_VENDA                              INTEGER   ");
        sqlBuilder.append(");");

        return sqlBuilder.toString();
    }

    public static String getComanda()
    {
        Log.i("Erro","Criou get comanda");
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("CREATE TABLE IF NOT EXISTS COMANDA (             ");
        sqlBuilder.append("ID_COMANDA                             INTEGER , ");
        sqlBuilder.append("ID_SALESFORCE                     VARCHAR (18) , ");
        sqlBuilder.append("ID_VENDEDOR                       VARCHAR (18) , ");
        sqlBuilder.append("NOME                              VARCHAR (35) , ");
        sqlBuilder.append("A_RECEBER                        DECIMAL(10,2) , ");
        sqlBuilder.append("INTEGRAR                                INTEGER  ");
        sqlBuilder.append(");");

        return sqlBuilder.toString();
    }

    public static String getProduto()
    {
        Log.i("Erro","Criou get produto");
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("CREATE TABLE IF NOT EXISTS PRODUTO (          ");
        sqlBuilder.append("ID_PRODUTO                     VARCHAR (18) , ");
        sqlBuilder.append("NOME                           VARCHAR (30) , ");
        sqlBuilder.append("PRECO                         DECIMAL(10,2) , ");
        sqlBuilder.append("ATIVO                         INTEGER         ");
        sqlBuilder.append(");");

        return sqlBuilder.toString();
    }

    public static String getSabor()
    {
        Log.i("Erro","Criou get sabor");
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("CREATE TABLE IF NOT EXISTS SABOR (          ");
        sqlBuilder.append("ID_SABOR                     VARCHAR (18) , ");
        sqlBuilder.append("NOME                         VARCHAR (30) , ");
        sqlBuilder.append("ATIVO                         INTEGER       ");
        sqlBuilder.append(");");

        return sqlBuilder.toString();
    }

    public static String getProdutoDisponivel()
    {
        Log.i("Erro","Criou get prod dis");
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("CREATE TABLE IF NOT EXISTS PRODUTO_DISPONIVEL (          ");
        sqlBuilder.append("ID_PRODUTO_DISPONIVEL                     VARCHAR (18) , ");
        sqlBuilder.append("SABOR                                     VARCHAR (30) , ");
        sqlBuilder.append("PRODUTO                                   VARCHAR (30) , ");
        sqlBuilder.append("ID_VENDEDOR                               VARCHAR (18) , ");
        sqlBuilder.append("QUANTIDADE                                      INTEGER  ");
        sqlBuilder.append(");");

        return sqlBuilder.toString();
    }

    public static String getVendas()
    {
        Log.i("Erro","Criou get vendido");
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("CREATE TABLE IF NOT EXISTS VENDA (                        ");
        sqlBuilder.append("ID_VENDA                                        INTEGER , ");
        sqlBuilder.append("ID_SALESFORCE                              VARCHAR (18) , ");
        sqlBuilder.append("ID_COMANDA                                      INTEGER , ");
        sqlBuilder.append("PRODUTO                                    VARCHAR (30) , ");
        sqlBuilder.append("SABOR                                      VARCHAR (30) , ");
        sqlBuilder.append("ID_VENDEDOR                                VARCHAR (18) , ");
        sqlBuilder.append("QUANTIDADE                                      INTEGER , ");
        sqlBuilder.append("ACRESCIMO                                 DECIMAL(10,2) , ");
        sqlBuilder.append("DESCONTO                                  DECIMAL(10,2) , ");
        sqlBuilder.append("VALOR_TOTAL                               DECIMAL(10,2) , ");
        sqlBuilder.append("DATA_VENDA                                 VARCHAR (10) , ");
        sqlBuilder.append("DESCRICAO                                  VARCHAR (15) , ");
        sqlBuilder.append("PAGO                                            INTEGER , ");
        sqlBuilder.append("INTEGRAR                                        INTEGER   ");
        sqlBuilder.append(");");

        return sqlBuilder.toString();
    }

    public static String getTokenSF()
    {
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("CREATE TABLE IF NOT EXISTS TOKEN_SF (                        ");
        sqlBuilder.append("ID                                            VARCHAR (300), ");
        sqlBuilder.append("ACCESS_TOKEN                                  VARCHAR (300), ");
        sqlBuilder.append("INTANCE_URL                                   VARCHAR (300), ");
        sqlBuilder.append("TOKEN_TYPE                                    VARCHAR (300), ");
        sqlBuilder.append("ISSUED_AT                                     VARCHAR (300), ");
        sqlBuilder.append("SIGNATURE                                     VARCHAR (300)  ");
        sqlBuilder.append(");");

        return sqlBuilder.toString();
    }

    public static String deleteVendedor()
    {
        String retorno = "DELETE FROM VENDEDOR";
        return retorno;
    }

    public static String deleteComanda()
    {
        String retorno = "DELETE FROM COMANDA";
        return retorno;
    }

    public static String deleteProduto()
    {
        String retorno = "DELETE FROM PRODUTO";
        return retorno;
    }

    public static String deleteSabor()
    {
        String retorno = "DELETE FROM SABOR";
        return retorno;
    }

    public static String deleteProdutoDisponivel()
    {
        String retorno = "DELETE FROM PRODUTO_DISPONIVEL";
        return retorno;
    }

    public static String deleteVendas()
    {
        String retorno = "DELETE FROM VENDA";
        return retorno;
    }

    public static String deleteTokenSF()
    {
        String retorno = "DELETE FROM TOKEN_SF";
        return retorno;
    }

}

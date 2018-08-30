package com.br.kjtrufas.entidades;

import java.util.ArrayList;

public class EnviarRegistro
{
    private ArrayList<Venda> vendas;
    private ArrayList<Comanda> comandas;

    public EnviarRegistro()
    {
        vendas = new ArrayList<Venda>();
        comandas = new ArrayList<Comanda>();
    }

    public ArrayList<Venda> getVendas() {
        return vendas;
    }

    public void setVendas(ArrayList<Venda> vendas) {
        this.vendas = vendas;
    }

    public ArrayList<Comanda> getComandas() {
        return comandas;
    }

    public void setComandas(ArrayList<Comanda> comandas) {
        this.comandas = comandas;
    }

    public String toString()
    {
        String ret = "";

        for (Comanda c : getComandas())
        {
            ret = ret+c.toString2();
            ret = ret+"\n";
        }

        ret = ret+"\n";

        for (Venda v : getVendas())
        {
            ret=ret+v.toString2();
            ret = ret+"\n";
        }

        return ret;
    }

}

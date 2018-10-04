package com.br.kjtrufas.entidades;

import java.io.Serializable;

public class Comanda implements Serializable {

    private int idMobile;
    private String idSalesforce;
    private String idVendedor;
    private String nome;
    private Double aReceber;
    private Integer integrar;

    public Comanda() {}

    public Comanda(String n, String idVend, Double aReceber, Integer integrar)
    {
        this.setNome(n);
        this.setIdVendedor(idVend);
        this.setAReceber(aReceber);
        this.setIntegrar(integrar);
    }

    public String toString2()
    {
        String ret = "\nNome: "+getNome()+"\nA receber: "+getAReceber()+"\nIntegrar: "+getIntegrar()+"\nId Salesforce: "+getIdSalesforce();

        return ret;
    }

    public Integer getId() {
        return idMobile;
    }

    public void setId(int id) {
        this.idMobile = id;
    }

    public String getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(String idVendedor) {
        this.idVendedor = idVendedor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getAReceber() {
        return aReceber;
    }

    public void setAReceber(Double aReceber) {
        this.aReceber = aReceber;
    }

    public Integer getIntegrar() {
        return integrar;
    }

    public void setIntegrar(Integer integrar) {
        this.integrar = integrar;
    }

    public String toString()
    {
        String s = this.getNome()+ " - Integrar: "+getIntegrar();
        return s;
    }

    public String getIdSalesforce() {
        return idSalesforce;
    }

    public void setIdSalesforce(String idSalesforce) {
        this.idSalesforce = idSalesforce;
    }
}

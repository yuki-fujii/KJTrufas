package com.br.kjtrufas.entidades;

import java.io.Serializable;

public class Comanda implements Serializable {

    private String id;
    private String idVendedor;
    private String nome;
    private Double aReceber;
    private Integer integrar;

    public Comanda() {}

    public Comanda(String n, String idVend, Double aReceber)
    {
        this.setNome(n);
        this.setIdVendedor(idVend);
        this.setAReceber(aReceber);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}

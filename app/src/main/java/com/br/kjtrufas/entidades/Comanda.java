package com.br.kjtrufas.entidades;

public class Comanda {

    private int id;
    private String idVendedor;
    private String nome;
    private Double aReceber;

    public Comanda() {}

    public Comanda(String n, String idVend, Double aReceber)
    {
        this.setNome(n);
        this.setIdVendedor(idVend);
        this.setAReceber(aReceber);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
}

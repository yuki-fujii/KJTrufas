package com.br.kjtrufas.entidades;

public class Comanda {

    private String id;
    private String idVendedor;
    private String nome;
    private Double saldo;

    public Comanda() {}

    public Comanda(String n, String idVend, Double saldo)
    {
        this.setNome(n);
        this.setIdVendedor(idVend);
        this.setSaldo(saldo);
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

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }
}

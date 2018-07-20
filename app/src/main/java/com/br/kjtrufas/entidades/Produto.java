package com.br.kjtrufas.entidades;

public class Produto {

    private String id;
    private String nome;
    private Double preco;
    private int ativo;

    public Produto(String nome, Double preco, int ativo)
    {
        this.setNome(nome);
        this.setPreco(preco);
        this.setAtivo(ativo);
    }

    public Produto(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public int getAtivo() {
        return ativo;
    }

    public void setAtivo(int ativo) {
        this.ativo = ativo;
    }

    public String toString()
    {
        return getNome();
    }
}

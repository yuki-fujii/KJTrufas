package com.br.kjtrufas.entidades;

public class Sabor {

    private String id;
    private String nome;
    private int ativo;

    public Sabor(){}

    public Sabor(String nome, int ativo)
    {
        this.setNome(nome);
        this.setAtivo(ativo);
    }

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

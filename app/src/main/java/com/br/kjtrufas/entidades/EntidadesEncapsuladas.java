package com.br.kjtrufas.entidades;

import java.util.ArrayList;

public class EntidadesEncapsuladas
{
    private ArrayList<Produto> produtos;
    private ArrayList<Sabor> sabores;
    private ArrayList<ProdutoDisponivel> produtosDisponiveis;
    private EnviarRegistro registros;
    private Vendedor vendedor;

    public EntidadesEncapsuladas ()
    {
        produtos = new ArrayList<Produto>();
        sabores = new ArrayList<Sabor>();
        produtosDisponiveis = new ArrayList<ProdutoDisponivel>();
        registros = new EnviarRegistro();
    }

    public ArrayList<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(ArrayList<Produto> produtos) {
        this.produtos = produtos;
    }

    public ArrayList<Sabor> getSabores() {
        return sabores;
    }

    public void setSabores(ArrayList<Sabor> sabores) {
        this.sabores = sabores;
    }

    public ArrayList<ProdutoDisponivel> getProdutosDisponiveis() {
        return produtosDisponiveis;
    }

    public void setProdutosDisponiveis(ArrayList<ProdutoDisponivel> produtosDisponiveis) {
        this.produtosDisponiveis = produtosDisponiveis;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    public EnviarRegistro getEnviarRegistro() {
        return registros;
    }

    public void setEnviarRegistro(EnviarRegistro enviarRegistro) {
        this.registros = enviarRegistro;
    }
}

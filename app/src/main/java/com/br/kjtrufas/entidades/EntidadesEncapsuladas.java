package com.br.kjtrufas.entidades;

import java.util.ArrayList;

public class EntidadesEncapsuladas
{
    private ArrayList<Produto> produtos = new ArrayList<Produto>();
    private ArrayList<Sabor> sabores = new ArrayList<Sabor>();
    private ArrayList<ProdutoDisponivel> produtosDisponiveis = new ArrayList<ProdutoDisponivel>();
    private Vendedor vendedor;

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
}

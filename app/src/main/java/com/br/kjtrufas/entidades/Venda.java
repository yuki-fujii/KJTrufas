package com.br.kjtrufas.entidades;

public class Venda {

    private String id;
    private String idComanda;
    private String produto;
    private String sabor;
    private String idVendedor;
    private int quantidade;
    private Double acrescimo;
    private Double desconto;
    private Double valorTotal;
    private String descricao;
    private String dataVenda;
    private int pago;

    public Venda(){}

    public Venda(String idComanda, String produto, String sabor,String idVendedor, int quantidade, Double acrescimo, Double desconto, Double valorTotal,
                 String descricao, String dataVenda, int pago)
    {
        this.setIdComanda(idComanda);
        this.setProduto(produto);
        this.setSabor(sabor);
        this.setIdVendedor(idVendedor);
        this.setQuantidade(quantidade);
        this.setAcrescimo(acrescimo);
        this.setDesconto(desconto);
        this.setValorTotal(valorTotal);
        this.setDescricao(descricao);
        this.setDataVenda(dataVenda);
        this.setPago(pago);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdComanda() {
        return idComanda;
    }

    public void setIdComanda(String idComanda) {
        this.idComanda = idComanda;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public String getSabor() {
        return sabor;
    }

    public void setSabor(String sabor) {
        this.sabor = sabor;
    }

    public String getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(String idVendedor) {
        this.idVendedor = idVendedor;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Double getAcrescimo() {
        return acrescimo;
    }

    public void setAcrescimo(Double acrescimo) {
        this.acrescimo = acrescimo;
    }

    public Double getDesconto() {
        return desconto;
    }

    public void setDesconto(Double desconto) {
        this.desconto = desconto;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(String dataVenda) {
        this.dataVenda = dataVenda;
    }

    public int getPago() {
        return pago;
    }

    public void setPago(int pago) {
        this.pago = pago;
    }
}

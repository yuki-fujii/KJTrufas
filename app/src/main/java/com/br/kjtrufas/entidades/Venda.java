package com.br.kjtrufas.entidades;

import java.text.NumberFormat;

public class Venda {

    private String idMobile;
    private String idSalesforce;
    private String idComanda;
    private String idProduto;
    private String idSabor;
    private String idVendedor;
    private int quantidade;
    private Double acrescimo;
    private Double desconto;
    private Double valorTotal;
    private String descricao;
    private String dataVenda;
    private int pago;
    private int integrar;

    public Venda(){}

    public Venda(String idComanda, String produto, String sabor,String idVendedor, int quantidade, Double acrescimo, Double desconto, Double valorTotal,
                 String descricao, String dataVenda, int pago, int integrar)
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
        this.setIntegrar(integrar);
    }

    public String toString2()
    {
        String ret = "\nProduto: "+getProduto()+"\nQuantidade: "+getQuantidade()+"\nSabor: "+getSabor()+"\nPago: "+getPago()+"\nIntegrar: "+getIntegrar()
                +"\nID Salesforce: "+getIdSalesforce()+"\nID Vendedor: "+getIdVendedor();

        return ret;
    }

    public String getId() {
        return idMobile;
    }

    public void setId(String id) {
        this.idMobile = id;
    }

    public String getIdComanda() {
        return idComanda;
    }

    public void setIdComanda(String idComanda) {
        this.idComanda = idComanda;
    }

    public String getProduto() {
        return idProduto;
    }

    public void setProduto(String produto) {
        this.idProduto = produto;
    }

    public String getSabor() {
        return idSabor;
    }

    public void setSabor(String sabor) {
        this.idSabor = sabor;
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

    public String toString()
    {
        return getDataVenda()+" - "+getDescricao()+" - "+String.valueOf(NumberFormat.getCurrencyInstance().format(getValorTotal()));
    }

    public int getIntegrar() {
        return integrar;
    }

    public void setIntegrar(int integrar) {
        this.integrar = integrar;
    }

    public String getIdSalesforce() {
        return idSalesforce;
    }

    public void setIdSalesforce(String idSalesforce) {
        this.idSalesforce = idSalesforce;
    }

}

package com.br.kjtrufas.suporte;

public class DadosVendas {

    private String[] data;
    private String[] descricao;
    private String[] valor;

    public DadosVendas(int size)
    {
        data = new String[size];
        descricao = new String[size];
        valor = new String[size];
    }

    public String[] getData() {
        return data;
    }

    public void setData(String[] data) {
        this.data = data;
    }

    public String[] getDescricao() {
        return descricao;
    }

    public void setDescricao(String[] descricao) {
        this.descricao = descricao;
    }

    public String[] getValor() {
        return valor;
    }

    public void setValor(String[] valor) {
        this.valor = valor;
    }
}

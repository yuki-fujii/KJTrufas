package com.br.kjtrufas.suporte;

public class DadosComandas {

    private String[] nome;
    private String[] valor;

    public DadosComandas(int size)
    {
        setNome(new String[size]);
        setValor(new String[size]);
    }

    public String[] getNome() {
        return nome;
    }

    public void setNome(String[] nome) {
        this.nome = nome;
    }

    public String[] getValor() {
        return valor;
    }

    public void setValor(String[] valor) {
        this.valor = valor;
    }
}

package com.br.kjtrufas.entidades;

public class Vendedor
{
    private String id;
    private String nome;
    private String login;
    private String senha;
    private String confSenha;
    private int idMobileComanda;
    private int idMobileVenda;

    public Vendedor(String id, String nome, String login, String senha, String confSenha, Integer idMobileComanda, Integer idMobileVenda)
    {
        this.setId(id);
        this.setNome(nome);
        this.setLogin(login);
        this.setSenha(senha);
        this.setConfSenha(confSenha);
        this.setIdMobileComanda(idMobileComanda);
        this.setIdMobileVenda(idMobileVenda);
    }

    public Vendedor() {

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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getConfSenha() {
        return confSenha;
    }

    public void setConfSenha(String confSenha) {
        this.confSenha = confSenha;
    }

    public int getIdMobileComanda() {
        return idMobileComanda;
    }

    public void setIdMobileComanda(int idMobileComanda) {
        this.idMobileComanda = idMobileComanda;
    }

    public int getIdMobileVenda() {
        return idMobileVenda;
    }

    public void setIdMobileVenda(int idMobileVenda) {
        this.idMobileVenda = idMobileVenda;
    }

    public String toString()
    {
        String ret = "\nID: "+getId()+"\nNome: "+getNome()+"\nLogin: "+getLogin()+"\nSenha: "+getSenha()+"\nConf senha: "+getConfSenha()
                +"\nID Venda: "+getIdMobileVenda()+"\nID Comanda: "+getIdMobileComanda();

        return ret;
    }
}

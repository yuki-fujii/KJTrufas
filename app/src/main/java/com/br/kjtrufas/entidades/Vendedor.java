package com.br.kjtrufas.entidades;

public class Vendedor
{
    private String id;
    private String nome;
    private String login;
    private String senha;
    private String confSenha;

    public Vendedor(String nome, String login, String senha, String confSenha)
    {
        this.setNome(nome);
        this.setLogin(login);
        this.setSenha(senha);
        this.setConfSenha(confSenha);
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
}

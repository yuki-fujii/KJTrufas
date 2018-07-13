package entidades;

public class Comanda {

    private String id;
    private String idVendedor;
    private String nome;

    public Comanda(String n, String idVend)
    {
        this.setNome(n);
        this.setIdVendedor(idVend);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(String idVendedor) {
        this.idVendedor = idVendedor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}

public abstract class ComponenteTerritorial {

    protected int id;
    protected String nome;
    protected String regiao;
    protected String status; // "ATIVO" ou "INATIVO"

    public ComponenteTerritorial(int id, String nome, String regiao) {
        this.id = id;
        this.nome = nome;
        this.regiao = regiao;
        this.status = "ATIVO";
    }

    public void ativar() {
        this.status = "ATIVO";
    }

    public void desativar() {
        this.status = "INATIVO";
    }

    public abstract String verificarStatus();

    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getRegiao() { return regiao; }
    public String getStatus() { return status; }
}

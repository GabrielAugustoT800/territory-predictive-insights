/**
 * CONTRATO COMPARTILHADO (definido na main, não alterar sem avisar o grupo).
 * Responsável pela implementação: Parte A (Sensores).
 *
 * Classe abstrata: molde comum para tudo que é instalado no território.
 * Os sensores herdam desta classe E implementam a interface Sensor.
 */
public abstract class ComponenteTerritorial {

    protected int id;
    protected String nome;
    protected String regiao;
    protected String status; // "ATIVO" ou "INATIVO"

    public ComponenteTerritorial(int id, String nome, String regiao) {
        // TODO Parte A: inicializar atributos e começar com status "ATIVO"
        this.id = id;
        this.nome = nome;
        this.regiao = regiao;
        this.status = "ATIVO";
    }

    public void ativar() {
        // TODO Parte A
    }

    public void desativar() {
        // TODO Parte A
    }

    /** Método abstrato: cada componente descreve o próprio status. */
    public abstract String verificarStatus();

    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getRegiao() { return regiao; }
    public String getStatus() { return status; }
}

import java.util.ArrayList;

/**
 * CONTRATO COMPARTILHADO (definido na main, não alterar sem avisar o grupo).
 * Responsável pela implementação: Parte B (Previsão e Alertas).
 *
 * Classe abstrata base de todas as formas de prever o próximo valor de um sensor.
 */
public abstract class EstrategiaPrevisao {

    /** Abaixo disso o sistema deve "se calar" (ver Reflexão Crítica no README). */
    public static final int MINIMO_LEITURAS = 5;

    protected String nome;
    protected String descricao;

    public EstrategiaPrevisao(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    /** Retorna true se há leituras suficientes para uma previsão confiável. */
    public boolean dadosSuficientes(ArrayList<Double> leituras) {
        // TODO Parte B
        return false;
    }

    /**
     * Calcula o valor previsto a partir das leituras (mais antiga primeiro, mais recente por último).
     * Se não houver dados suficientes, retornar Double.NaN.
     */
    public abstract double prever(ArrayList<Double> leituras);

    /** Explica em texto o que o valor previsto significa (ex.: "tendência de alta"). */
    public String interpretar(double valorPrevisto, double limiar) {
        // TODO Parte B (subclasses podem sobrescrever e chamar super.interpretar(...))
        return "";
    }

    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
}

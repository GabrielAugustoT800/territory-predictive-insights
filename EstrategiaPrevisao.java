import java.util.ArrayList;
import java.util.Locale;

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
        if (leituras == null || leituras.size() < MINIMO_LEITURAS) { // verifica se tem o minimo de leituras necessarias
            return false;
        }

        for (Double valor : leituras) { // verifica se nenhuma leitura é invalida
            if (valor == null || Double.isNaN(valor) || Double.isInfinite(valor)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Calcula o valor previsto a partir das leituras (mais antiga primeiro, mais recente por último).
     * Se não houver dados suficientes, retornar Double.NaN.
     */
    public abstract double prever(ArrayList<Double> leituras);

    /** Explica em texto o que o valor previsto significa (ex.: "tendência de alta"). */
    public String interpretar(double valorPrevisto, double limiar) {
        if (Double.isNaN(valorPrevisto)) {
            return "Previsão indisponível: quantidade insuficiente de leituras válidas.";
        }

        if (Double.isInfinite(valorPrevisto)) {
            return "Previsão inválida: resultado não finito.";
        }

        if (Double.isNaN(limiar) || Double.isInfinite(limiar) || limiar <= 0) {
            return "Não foi possível interpretar: limiar inválido.";
        }

        double percentual = (valorPrevisto / limiar) * 100;

        String percentualTexto = String.format(Locale.forLanguageTag("pt-BR"), "%.1f%%", percentual);

        if (percentual < 80) {
            return "Valor previsto corresponde a " + percentualTexto + " do limiar. Situação dentro da faixa normal.";

        }
        else if (percentual < 100) {
            return "Valor previsto corresponde a " + percentualTexto + " do limiar. Atenção: está próximo do limite.";

        }
        else if (percentual < 120) {
            return "Valor previsto corresponde a " + percentualTexto + " do limiar. O limite foi atingido ou ultrapassado.";

        }
        else {
            return "Valor previsto corresponde a " + percentualTexto + " do limiar. Situação crítica.";
        }
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }
}

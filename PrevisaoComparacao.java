import java.util.ArrayList;

/**
 * Responsável: Parte B (Previsão e Alertas).
 * Compara a média do período atual com a do período anterior e projeta a tendência.
 * O construtor abaixo é CONTRATO (usado pelo menu da Parte C).
 */
public class PrevisaoComparacao extends EstrategiaPrevisao {

    private int tamanhoPeriodo;

    public PrevisaoComparacao(int tamanhoPeriodo) {
        super("Comparação de Períodos", "Período atual vs. período anterior");
        this.tamanhoPeriodo = tamanhoPeriodo;
    }

    @Override
    public double prever(ArrayList<Double> leituras) {
        // TODO Parte B
        return Double.NaN;
    }

    public int getTamanhoPeriodo() { return tamanhoPeriodo; }
}

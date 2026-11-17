import java.util.ArrayList;

/**
 * Responsável: Parte B (Previsão e Alertas).
 * Previsão = média das últimas N leituras (N = tamanhoJanela).
 * O construtor abaixo é CONTRATO (usado pelo menu da Parte C).
 */
public class PrevisaoMediaMovel extends EstrategiaPrevisao {

    private int tamanhoJanela;

    public PrevisaoMediaMovel(int tamanhoJanela) {
        super("Média Móvel", "Média das últimas N leituras");
        this.tamanhoJanela = tamanhoJanela;
    }

    @Override
    public double prever(ArrayList<Double> leituras) {
        // TODO Parte B
        return Double.NaN;
    }

    public int getTamanhoJanela() { return tamanhoJanela; }
}

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

        if (tamanhoJanela <= 0) { // validacao pra impedirt janelas com tamanho zero ou negativo
            throw new IllegalArgumentException(
                    "O tamanho da janela deve ser maior que zero."
            );
        }

        this.tamanhoJanela = tamanhoJanela;
    }

    @Override
    public double prever(ArrayList<Double> leituras) {
        if (!dadosSuficientes(leituras)) { // verifica quantidade minima e  validade dos dados
            return Double.NaN;
        }

        if (leituras.size() < tamanhoJanela) { // verifica se tem leituras suficientes pra janela
            return Double.NaN;
        }

        double soma = 0;

        // percorre apenas as ultimas N leituras
        int inicio = leituras.size() - tamanhoJanela;
        for (int i = inicio; i < leituras.size(); i++) {
            soma += leituras.get(i);
        }

        return soma / tamanhoJanela;
    }

    @Override
    public String interpretar(double valorPrevisto, double limiar) {
        String resultado = super.interpretar(valorPrevisto, limiar);

        if (Double.isNaN(valorPrevisto) || Double.isInfinite(valorPrevisto)) {
            return resultado;
        }

        return resultado + " Cálculo baseado nas últimas " + tamanhoJanela + " leituras.";
    }

    public int getTamanhoJanela() {
        return tamanhoJanela;
    }
}

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

        if (tamanhoPeriodo <= 0) { // validacao pra impedirt periodos com tamanho zero ou negativo
            throw new IllegalArgumentException("O tamanho do período deve ser maior que zero.");
        }

        this.tamanhoPeriodo = tamanhoPeriodo;
    }

    @Override
    public double prever(ArrayList<Double> leituras) {
        if (!dadosSuficientes(leituras)) {
            return Double.NaN;
        }

        if ((long) leituras.size() < 2L * tamanhoPeriodo) { // sao necessarios dois periodos completos
            return Double.NaN;
        }

        double somaAnterior = 0;
        double somaAtual = 0;

        int inicioAtual = leituras.size() - tamanhoPeriodo;
        int inicioAnterior = inicioAtual - tamanhoPeriodo;

        for (int i = inicioAnterior; i < inicioAtual; i++) { // calcula a soma do periodo anterior
            somaAnterior += leituras.get(i);
        }

        for (int i = inicioAtual; i < leituras.size(); i++) { // calcula a soma do periodo atual
            somaAtual += leituras.get(i);
        }

        double mediaAnterior = somaAnterior / tamanhoPeriodo;
        double mediaAtual = somaAtual / tamanhoPeriodo;

        double variacao = mediaAtual - mediaAnterior;
        double previsao = mediaAtual + variacao;

        return Math.max(0, previsao); // impede que a previsao seja negativa
    }

    @Override
    public String interpretar(double valorPrevisto, double limiar) {
        String resultado = super.interpretar(valorPrevisto, limiar);

        if (Double.isNaN(valorPrevisto) || Double.isInfinite(valorPrevisto)) {
            return resultado;
        }

        return resultado + " Projeção baseada na variação entre dois períodos de " + tamanhoPeriodo + " leituras.";
    }

    public int getTamanhoPeriodo() {
        return tamanhoPeriodo;
    }
}

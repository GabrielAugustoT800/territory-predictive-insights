import java.util.ArrayList;
import java.util.Locale;

public class TestePrevisao {
    public static void main(String[] args) {
        ArrayList<Double> leituras = new ArrayList<>();

        leituras.add(30.0);
        leituras.add(35.0);
        leituras.add(42.0);
        leituras.add(48.0);
        leituras.add(55.0);
        leituras.add(61.0);

        double limiarChuva = 50.0;

        // criacao das estrategias
        EstrategiaPrevisao media = new PrevisaoMediaMovel(3);
        EstrategiaPrevisao comparacao = new PrevisaoComparacao(3);

        GeradorAlertas gerador = new GeradorAlertas();

        System.out.println("===== TESTE DE PREVISÃO E ALERTAS =====");

        testarEstrategia(media, leituras, limiarChuva, gerador);
        testarEstrategia(comparacao, leituras, limiarChuva, gerador);

        // teste com quantidade insuficiente de leituras
        System.out.println("\n===== TESTE DE DADOS INSUFICIENTES =====");

        ArrayList<Double> poucasLeituras = new ArrayList<>();

        poucasLeituras.add(20.0);
        poucasLeituras.add(30.0);
        poucasLeituras.add(40.0);
        poucasLeituras.add(50.0);

        double previsao = media.prever(poucasLeituras);

        Alerta alerta = gerador.avaliar(
                "CHUVA",
                media.getNome(),
                previsao,
                limiarChuva);

        if (alerta == null) {
            System.out.println("Previsão indisponível.");
            System.out.println("O sistema se cala por falta de dados suficientes.");
        }
    }

    // executa uma estrategia e exibe o resultado dela
    private static void testarEstrategia(
            EstrategiaPrevisao estrategia,
            ArrayList<Double> leituras,
            double limiar,
            GeradorAlertas gerador) {

        System.out.println("\nEstratégia: " + estrategia.getNome());

        double previsao = estrategia.prever(leituras);

        System.out.println("Valor previsto: " + String.format(Locale.forLanguageTag("pt-BR"), "%.2f", previsao));

        System.out.println("Interpretação: "+ estrategia.interpretar(previsao, limiar));

        Alerta alerta = gerador.avaliar("CHUVA", estrategia.getNome(), previsao, limiar);

        if (alerta != null) {
            System.out.println(alerta.explicar());
        }
        else {
            System.out.println("Nenhum alerta gerado.");
        }
    }
}

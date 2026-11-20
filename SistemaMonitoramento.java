import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Responsável: Parte C (Dados, Menu e Integração).
 * Classe principal: menu interativo e loop do programa.
 * Guarda o histórico de leituras de cada sensor em ArrayList e chama
 * as previsões (Parte B) e os sensores (Parte A) apenas pelos contratos.
 */
public class SistemaMonitoramento {

    // Mapa: tipo do sensor -> histórico de leituras (mais antiga primeiro)
    private static final Map<String, ArrayList<Double>> historico = new HashMap<>();

    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        int opcao;

        // Sensores (Parte A)
        Sensor chuva = new SensorChuva(1, "Sensor de Chuva", "Centro");
        Sensor nivelRio = new SensorNivelRio(2, "Sensor de Nível do Rio", "Zona Norte");
        Sensor umidade = new SensorUmidade(3, "Sensor de Umidade", "Zona Sul");
        Sensor[] sensores = { chuva, nivelRio, umidade };

        // Dados do território (Parte C)
        DadosTerritorio dados = new DadosTerritorio("Cidade Modelo", 100000, "ADMIN123");

        // Histórico vazio e limite de cada sensor igual ao limiar protegido
        for (Sensor s : sensores) {
            historico.put(s.retornarTipo(), new ArrayList<>());
            s.definirLimite(dados.getLimiar(s.retornarTipo()));
        }

        // Estratégias de previsão (Parte B)
        EstrategiaPrevisao mediaMovel = new PrevisaoMediaMovel(3);
        EstrategiaPrevisao comparacao = new PrevisaoComparacao(3);
        EstrategiaPrevisao[] estrategias = { mediaMovel, comparacao };

        // Gerador de alertas (Parte B)
        GeradorAlertas gerador = new GeradorAlertas();

        do {
            System.out.println("\n=== Territory Predictive Insights - Clima Local ===");
            System.out.println("1. Verificar sensores");
            System.out.println("2. Rodar previsões");
            System.out.println("3. Gerenciar dados do território");
            System.out.println("4. Simular próximas leituras");
            System.out.println("5. Exibir status completo");
            System.out.println("0. Sair");
            System.out.print("Escolha: ");

            if (!entrada.hasNextLine()) {
                break; // fim da entrada (ex.: Ctrl+D)
            }
            opcao = lerInteiro(entrada.nextLine());

            switch (opcao) {
                case 1 -> verificarSensores(sensores, dados);
                case 2 -> rodarPrevisoes(sensores, dados, estrategias, gerador);
                case 3 -> gerenciarDados(sensores, dados, entrada);
                case 4 -> simularLeituras(sensores, entrada);
                case 5 -> exibirStatusCompleto(sensores, dados);
                case 0 -> System.out.println("Encerrando...");
                default -> System.out.println("Opção inválida. Digite um número de 0 a 5.");
            }
        } while (opcao != 0);

        entrada.close();
    }

    /** Converte o texto digitado em número; texto inválido vira -1 (opção inválida). */
    private static int lerInteiro(String texto) {
        try {
            return Integer.parseInt(texto.trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    // 1. Verificar sensores - lê valor atual, mostra status, guarda no histórico
    private static void verificarSensores(Sensor[] sensores, DadosTerritorio dados) {
        System.out.println("\n--- VERIFICAÇÃO DE SENSORES ---");
        for (Sensor s : sensores) {
            System.out.println(((ComponenteTerritorial) s).verificarStatus());
            if (!s.verificarFuncionamento()) {
                System.out.println("Sensor fora de operação: leitura não realizada.");
                continue;
            }
            double valor = s.lerValor();
            historico.get(s.retornarTipo()).add(valor);
            double limiar = dados.getLimiar(s.retornarTipo());
            boolean passou = s.ultrapassouLimite(valor);
            System.out.printf("Leitura atual: %.2f %s | Limiar: %.2f %s | %s%n",
                    valor, s.getUnidade(), limiar, s.getUnidade(),
                    passou ? "ULTRAPASSOU O LIMIAR!" : "OK");
        }
    }

    // 2. Rodar previsões - usa as estratégias da Parte B e gera alertas automaticamente
    private static void rodarPrevisoes(Sensor[] sensores, DadosTerritorio dados,
                                       EstrategiaPrevisao[] estrategias, GeradorAlertas gerador) {
        System.out.println("\n--- PREVISÕES E ALERTAS ---");
        int totalAlertas = 0;

        for (Sensor s : sensores) {
            ArrayList<Double> leituras = historico.get(s.retornarTipo());
            double limiar = dados.getLimiar(s.retornarTipo());

            for (EstrategiaPrevisao est : estrategias) {
                double previsto = est.prever(leituras);

                // O sistema se cala quando não há dados suficientes
                if (Double.isNaN(previsto)) {
                    System.out.printf("%s [%s]: previsão indisponível (%d leituras; mínimo %d, ou mais conforme a estratégia).%n",
                            s.retornarTipo(), est.getNome(), leituras.size(), EstrategiaPrevisao.MINIMO_LEITURAS);
                    continue;
                }

                System.out.printf("%s [%s]: previsto = %.2f %s%n",
                        s.retornarTipo(), est.getNome(), previsto, s.getUnidade());
                System.out.println("  " + est.interpretar(previsto, limiar));

                Alerta alerta = gerador.avaliar(s.retornarTipo(), est.getNome(), previsto, limiar);
                if (alerta != null && alerta.getNivel() != NivelAlerta.NORMAL) {
                    System.out.println("  >> " + alerta.explicar());
                    totalAlertas++;
                }
            }
        }

        if (totalAlertas == 0) {
            System.out.println("\nNenhum alerta gerado neste momento.");
        } else {
            System.out.println("\n" + totalAlertas + " alerta(s) gerado(s). Os alertas apoiam a decisão:"
                    + " confira as condições no local antes de agir.");
        }
    }

    // 3. Gerenciar dados do território - região, população e limiares (com código de acesso)
    private static void gerenciarDados(Sensor[] sensores, DadosTerritorio dados, Scanner entrada) {
        System.out.println("\n--- GERENCIAR DADOS DO TERRITÓRIO ---");
        System.out.println("Região: " + dados.getRegiao());
        System.out.println("População: " + dados.getPopulacao());
        System.out.println("Limiares atuais:");
        System.out.printf("  CHUVA: %.1f mm/h%n", dados.getLimiar(Sensor.TIPO_CHUVA));
        System.out.printf("  NIVEL_RIO: %.1f m%n", dados.getLimiar(Sensor.TIPO_NIVEL_RIO));
        System.out.printf("  UMIDADE: %.1f %%%n", dados.getLimiar(Sensor.TIPO_UMIDADE));

        System.out.println("\n1. Alterar região");
        System.out.println("2. Alterar população");
        System.out.println("3. Alterar limiar (exige código de acesso)");
        System.out.println("0. Voltar");
        System.out.print("Escolha: ");
        int opcao = lerInteiro(entrada.nextLine());

        switch (opcao) {
            case 1 -> {
                System.out.print("Nova região: ");
                if (dados.setRegiao(entrada.nextLine())) {
                    System.out.println("Região atualizada com sucesso!");
                }
            }
            case 2 -> {
                System.out.print("Nova população: ");
                String texto = entrada.nextLine().trim();
                try {
                    if (dados.setPopulacao(Integer.parseInt(texto))) {
                        System.out.println("População atualizada com sucesso!");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Erro: digite um número inteiro. Valor anterior mantido.");
                }
            }
            case 3 -> alterarLimiar(sensores, dados, entrada);
            case 0 -> { }
            default -> System.out.println("Opção inválida.");
        }
    }

    private static void alterarLimiar(Sensor[] sensores, DadosTerritorio dados, Scanner entrada) {
        System.out.print("Tipo de sensor (CHUVA, NIVEL_RIO, UMIDADE): ");
        String tipo = entrada.nextLine().trim().toUpperCase();

        System.out.print("Novo valor: ");
        double valor;
        try {
            valor = Double.parseDouble(entrada.nextLine().trim().replace(',', '.'));
        } catch (NumberFormatException e) {
            System.out.println("Erro: valor inválido. Limiar mantido.");
            return;
        }

        System.out.print("Código de acesso: ");
        String codigo = entrada.nextLine().trim();

        if (dados.setLimiar(tipo, valor, codigo)) {
            // Mantém o limite do sensor igual ao limiar protegido
            for (Sensor s : sensores) {
                if (s.retornarTipo().equals(tipo)) {
                    s.definirLimite(valor);
                }
            }
            System.out.println("Limiar atualizado com sucesso!");
        }
    }

    // 4. Simular próximas leituras - adiciona N leituras simuladas ao histórico
    private static void simularLeituras(Sensor[] sensores, Scanner entrada) {
        System.out.println("\n--- SIMULAR PRÓXIMAS LEITURAS ---");
        System.out.print("Quantas leituras simular por sensor (1 a 24)? ");
        int quantidade = lerInteiro(entrada.nextLine());
        if (quantidade < 1 || quantidade > 24) {
            System.out.println("Quantidade inválida. Digite um número de 1 a 24.");
            return;
        }

        for (Sensor s : sensores) {
            if (!s.verificarFuncionamento()) {
                System.out.println(s.retornarTipo() + ": sensor fora de operação, nenhuma leitura simulada.");
                continue;
            }
            ArrayList<Double> h = historico.get(s.retornarTipo());
            for (int i = 0; i < quantidade; i++) {
                h.add(s.lerValor());
            }
            System.out.printf("%s: %d leitura(s) adicionada(s). Total no histórico: %d.%n",
                    s.retornarTipo(), quantidade, h.size());
        }
    }

    // 5. Exibir status completo - sensores + histórico + dados do território
    private static void exibirStatusCompleto(Sensor[] sensores, DadosTerritorio dados) {
        System.out.println("\n=== STATUS COMPLETO ===");
        System.out.println("Região: " + dados.getRegiao() + " | População: " + dados.getPopulacao());
        for (Sensor s : sensores) {
            System.out.println("\n" + ((ComponenteTerritorial) s).verificarStatus());
            ArrayList<Double> h = historico.get(s.retornarTipo());
            System.out.printf("Histórico (%d leituras): %s%n", h.size(), h);
            System.out.printf("Limiar atual: %.2f %s%n", dados.getLimiar(s.retornarTipo()), s.getUnidade());
        }
    }
}

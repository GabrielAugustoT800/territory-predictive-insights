import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
/**
\* Responsável: Parte C (Dados, Menu e Integração).
\* Classe principal: menu interativo e loop do programa.
\* Guarda o histórico de leituras de cada sensor em ArrayList e chama
\* as previsões (Parte B) e os sensores (Parte A) apenas pelos contratos.
*/
public class SistemaMonitoramento {
   // Mapa: tipo do sensor -> histórico de leituras
   private static final Map<String, List<Double>> historico = new HashMap<>();
   public static void main(String\[] args) {
       Scanner entrada = new Scanner(System.in);
       int opcao;
       // Instancia sensores (Parte A)
       Sensor chuva = new SensorChuva(1, "Sensor de Chuva", "Centro");
       Sensor nivelRio = new SensorNivelRio(2, "Sensor de Nível do Rio", "Zona Norte");
       Sensor umidade = new SensorUmidade(3, "Sensor de Umidade", "Zona Sul");
       Sensor\[] sensores = { chuva, nivelRio, umidade };
       // Inicializa histórico para cada tipo
       for (Sensor s : sensores) {
           historico.put(s.retornarTipo(), new ArrayList<>());
       }
       // Dados do território (Parte C)
       DadosTerritorio dados = new DadosTerritorio("Cidade Modelo", 100000, "ADMIN123");
       // Estratégias de previsão (Parte B)
       EstrategiaPrevisao mediaMovel = new PrevisaoMediaMovel(3);
       EstrategiaPrevisao comparacao = new PrevisaoComparacao(3);
       EstrategiaPrevisao\[] estrategias = { mediaMovel, comparacao };
       // Gerador de alertas (Parte B)
       GeradorAlertas gerador = new GeradorAlertas();
       do {
           System.out.println("\\n=== Territory Predictive Insights - Clima Local ===");
           System.out.println("1. Verificar sensores");
           System.out.println("2. Rodar previsões");
           System.out.println("3. Gerenciar dados do território");
           System.out.println("4. Simular próximas leituras");
           System.out.println("5. Exibir status completo");
           System.out.println("0. Sair");
           System.out.print("Escolha: ");
           opcao = entrada.hasNextInt() ? entrada.nextInt() : 0;
           entrada.nextLine(); // limpar buffer
           switch (opcao) {
               case 1 -> verificarSensores(sensores, dados);
               case 2 -> rodarPrevisoes(sensores, dados, estrategias, gerador);
               case 3 -> gerenciarDados(dados, entrada);
               case 4 -> simularLeituras(sensores, dados);
               case 5 -> exibirStatusCompleto(sensores, dados);
               case 0 -> System.out.println("Encerrando...");
               default -> System.out.println("Opção inválida.");
           }
       } while (opcao != 0);
       entrada.close();
   }
   // 1. Verificar sensores - lê valor atual, mostra status, guarda no histórico
   private static void verificarSensores(Sensor\[] sensores, DadosTerritorio dados) {
       System.out.println("\\n--- VERIFICAÇÃO DE SENSORES ---");
       for (Sensor s : sensores) {
           double valor = s.lerValor();
           historico.get(s.retornarTipo()).add(valor);
           String status = ((ComponenteTerritorial) s).verificarStatus();
           double limiar = dados.getLimiar(s.retornarTipo());
           boolean alerta = s.ultrapassouLimite(valor);
           System.out.println(status);
           System.out.printf("Leitura atual: %.2f %s | Limiar: %.2f | %s%n",
                   valor, s.getUnidade(), limiar,
                   alerta ? "⚠ ULTRAPASSOU O LIMIAR!" : "OK");
       }
   }
   // 2. Rodar previsões - usa estratégias da Parte B e gera alertas
   private static void rodarPrevisoes(Sensor\[] sensores, DadosTerritorio dados,
                                      EstrategiaPrevisao\[] estrategias, GeradorAlertas gerador) {
       System.out.println("\\n--- PREVISÕES E ALERTAS ---");
       boolean algumAlerta = false;
       for (Sensor s : sensores) {
           List<Double> leituras = historico.get(s.retornarTipo());
           double limiar = dados.getLimiar(s.retornarTipo());
           if (leituras.isEmpty()) {
               System.out.printf("%s: sem leituras no histórico.%n", s.retornarTipo());
               continue;
           }
           for (EstrategiaPrevisao est : estrategias) {
               if (!est.dadosSuficientes(leituras)) {
                   System.out.printf("%s \[%s]: dados insuficientes (mínimo %d).%n",
                           s.retornarTipo(), est.getNome(), EstrategiaPrevisao.MINIMO\_LEITURAS);
                   continue;
               }
               double previsto = est.prever(leituras);
               if (Double.isNaN(previsto)) continue;
               Alerta alerta = gerador.avaliar(s.retornarTipo(), est.getNome(), previsto, limiar);
               String interpretacao = est.interpretar(previsto, limiar);
               System.out.printf("%s \[%s]: previsto = %.2f %s | %s%n",
                       s.retornarTipo(), est.getNome(), previsto, s.getUnidade(), interpretacao);
               if (alerta != null) {
                   System.out.println("  >> " + alerta.explicar());
                   algumAlerta = true;
               }
           }
       }
       if (!algumAlerta) {
           System.out.println("Nenhum alerta gerado neste momento.");
       }
   }
   // 3. Gerenciar dados do território - alterar limiares com código de acesso
   private static void gerenciarDados(DadosTerritorio dados, Scanner entrada) {
       System.out.println("\\n--- GERENCIAR DADOS DO TERRITÓRIO ---");
       System.out.println("Região: " + dados.getRegiao());
       System.out.println("População: " + dados.getPopulacao());
       System.out.println("Limiares atuais:");
       System.out.printf("  CHUVA: %.1f mm/h%n", dados.getLimiar(Sensor.TIPO\_CHUVA));
       System.out.printf("  NÍVEL RIO: %.1f m%n", dados.getLimiar(Sensor.TIPO\_NIVEL\_RIO));
       System.out.printf("  UMIDADE: %.1f %%n", dados.getLimiar(Sensor.TIPO\_UMIDADE));
       System.out.print("\\nDeseja alterar algum limiar? (s/n): ");
       String resp = entrada.nextLine().trim().toLowerCase();
       if (!resp.equals("s")) return;
       System.out.print("Tipo de sensor (CHUVA, NIVEL\_RIO, UMIDADE): ");
       String tipo = entrada.nextLine().trim().toUpperCase();
       System.out.print("Novo valor: ");
       double valor = 0;
       try { valor = Double.parseDouble(entrada.nextLine().trim()); }
       catch (NumberFormatException e) { System.out.println("Valor inválido."); return; }
       System.out.print("Código de acesso: ");
       String codigo = entrada.nextLine().trim();
       boolean ok = dados.setLimiar(tipo, valor, codigo);
       if (ok) {
           System.out.println("Limiar atualizado com sucesso!");
       }
   }
   // 4. Simular próximas leituras - adiciona leituras simuladas ao histórico
   private static void simularLeituras(Sensor\[] sensores, DadosTerritorio dados) {
       System.out.println("\\n--- SIMULAR PRÓXIMAS LEITURAS ---");
       for (Sensor s : sensores) {
           double valor = s.lerValor();
           historico.get(s.retornarTipo()).add(valor);
           System.out.printf("%s: %.2f %s adicionada ao histórico.%n",
                   s.retornarTipo(), valor, s.getUnidade());
       }
   }
   // 5. Exibir status completo - sensores + histórico + dados do território
   private static void exibirStatusCompleto(Sensor\[] sensores, DadosTerritorio dados) {
       System.out.println("\\n=== STATUS COMPLETO ===");
       System.out.println("Região: " + dados.getRegiao() + " | População: " + dados.getPopulacao());
       for (Sensor s : sensores) {
           System.out.println("\\n" + ((ComponenteTerritorial) s).verificarStatus());
           List<Double> h = historico.get(s.retornarTipo());
           System.out.printf("Histórico (%d leituras): %s%n", h.size(), h);
           System.out.printf("Limiar atual: %.2f %s%n", dados.getLimiar(s.retornarTipo()), s.getUnidade());
       }
   }
}

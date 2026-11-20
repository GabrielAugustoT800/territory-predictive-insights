import java.util.ArrayList;
public class TesteDados {
   public static void main(String[] args) {
       System.out.println("=== TESTE PARTE C - DADOS E INTEGRAÇÃO ===\n");
       // 1. Teste DadosTerritorio
       System.out.println("--- Teste DadosTerritorio ---");
       DadosTerritorio dados = new DadosTerritorio("Centro", 50000, "SENHA123");
       System.out.println("Região: " + dados.getRegiao());
       System.out.println("População: " + dados.getPopulacao());
       System.out.printf("Limiar Chuva: %.1f%n", dados.getLimiar(Sensor.TIPO_CHUVA));
       System.out.printf("Limiar Nível Rio: %.1f%n", dados.getLimiar(Sensor.TIPO_NIVEL_RIO));
       System.out.printf("Limiar Umidade: %.1f%n", dados.getLimiar(Sensor.TIPO_UMIDADE));
     
       // Teste alteração com código errado
       System.out.println("\nTentativa com código errado:");
       boolean ok = dados.setLimiar(Sensor.TIPO_CHUVA, 30.0, "ERRADO");
       System.out.println("Retorno: " + ok + " (esperado: false)");
     
       // Teste alteração com código certo
       System.out.println("\nTentativa com código correto:");
       ok = dados.setLimiar(Sensor.TIPO_CHUVA, 30.0, "SENHA123");
       System.out.println("Retorno: " + ok + " (esperado: true)");
       System.out.printf("Novo limiar Chuva: %.1f%n", dados.getLimiar(Sensor.TIPO_CHUVA));
     
       // Teste validação de faixa
       System.out.println("\nTentativa valor fora da faixa (Chuva > 500):");
       ok = dados.setLimiar(Sensor.TIPO_CHUVA, 600.0, "SENHA123");
       System.out.println("Retorno: " + ok + " (esperado: false)");
     
       // 2. Teste integração Sensor + DadosTerritorio
       System.out.println("\n--- Teste Integração Sensor + Dados ---");
       Sensor chuva = new SensorChuva(1, "Sensor de Chuva", "Centro");
       Sensor rio = new SensorNivelRio(2, "Sensor de Nível do Rio", "Norte");
       Sensor umidade = new SensorUmidade(3, "Sensor de Umidade", "Sul");
       Sensor[] sensores = { chuva, rio, umidade };
       for (Sensor s : sensores) {
           double valor = s.lerValor();
           double limiar = dados.getLimiar(s.retornarTipo());
           boolean alerta = s.ultrapassouLimite(valor);
           System.out.printf("%s: valor=%.2f %s | limiar=%.2f | alerta=%b%n",
                   s.retornarTipo(), valor, s.getUnidade(), limiar, alerta);
       }
     
       // 3. Teste histórico e previsão (usando classes da Parte B)
       System.out.println("\n--- Teste Histórico + Previsão (Média Móvel) ---");
       ArrayList<Double> leituras = new ArrayList<>();
       leituras.add(10.0); leituras.add(20.0); leituras.add(30.0);
       leituras.add(40.0); leituras.add(50.0);
       EstrategiaPrevisao media = new PrevisaoMediaMovel(3);
       System.out.println("Dados suficientes? " + media.dadosSuficientes(leituras));
       double previsto = media.prever(leituras);
       System.out.printf("Previsão Média Móvel (janela 3): %.2f%n", previsto);
       GeradorAlertas gerador = new GeradorAlertas();
       Alerta alerta = gerador.avaliar(Sensor.TIPO_CHUVA, media.getNome(), previsto, 30.0);
       if (alerta != null) {
           System.out.println("Alerta gerado: " + alerta.explicar());
       }
       System.out.println("\n=== FIM DOS TESTES ===");
   }
}

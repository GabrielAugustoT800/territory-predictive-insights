import java.util.ArrayList;
import java.util.Scanner;

/**
 * Responsável: Parte C (Dados, Menu e Integração).
 * Classe principal: menu interativo e loop do programa.
 * Guarda o histórico de leituras de cada sensor em ArrayList e chama
 * as previsões (Parte B) e os sensores (Parte A) apenas pelos contratos.
 */
public class SistemaMonitoramento {

    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n=== Territory Predictive Insights - Clima Local ===");
            System.out.println("1. Verificar sensores");
            System.out.println("2. Rodar previsões");
            System.out.println("3. Gerenciar dados do território");
            System.out.println("4. Simular próximas leituras");
            System.out.println("5. Exibir status completo");
            System.out.println("0. Sair");
            System.out.print("Escolha: ");
            opcao = entrada.hasNextInt() ? entrada.nextInt() : 0;

            switch (opcao) {
                case 1: case 2: case 3: case 4: case 5:
                    System.out.println("(TODO Parte C: opção ainda não implementada)");
                    break;
                case 0:
                    System.out.println("Encerrando...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 0);

        entrada.close();
    }
}

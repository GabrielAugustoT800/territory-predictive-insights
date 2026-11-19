public class TesteSensores {
    public static void main(String[] args) {
        System.out.println("=== TESTE DE SENSORES (PARTE A) ===");

        // 1. Instanciando sensores
        Sensor chuva = new SensorChuva(1, "Sensor de Chuva", "Centro");
        Sensor rio = new SensorNivelRio(2, "Sensor de Nível de Rio", "Zona Norte");
        Sensor umidade = new SensorUmidade(3, "Sensor de Umidade", "Zona Sul");

        Sensor[] sensores = {chuva, rio, umidade};

        // 2. Testando a leitura e status inicial
        System.out.println("--- Leituras Iniciais ---");
        for (Sensor s: sensores){
            double valor = s.lerValor();

            String statusFormatado = ((ComponenteTerritorial) s).verificarStatus();
            System.out.println(statusFormatado);
            System.out.println("Valor lido: " + valor + " " + s.getUnidade() + 
                               " | Ultrapassou limite (" + s.getLimite() + "): " + (s.ultrapassouLimite(valor) ? "sim" : "não"));
            System.out.println("-----------------------------------------------");
        }

         // 3. Testando Alteração de Limite
        System.out.println("\n--- Testando Alteração de Limite ---");
        System.out.println("Limite antigo da Chuva: " + chuva.getLimite());
        chuva.definirLimite(10.0); // Diminuindo o limite para forçar alerta
        System.out.println("Novo limite da Chuva: " + chuva.getLimite());
        
        double novoValor = 15.0;
        System.out.println("Se a leitura for " + novoValor + ", ultrapassou: " + (chuva.ultrapassouLimite(novoValor) ? "sim" : "não"));

        // 4. Testando Ativar/Desativar
        System.out.println("\n--- Testando Status Ativo/Inativo ---");
        System.out.println("Antes: " + ((ComponenteTerritorial)rio).verificarStatus());
        ((ComponenteTerritorial)rio).desativar();
        System.out.println("Depois (Desativado): " + ((ComponenteTerritorial)rio).verificarStatus());
        System.out.println("Está funcionando: " + (rio.verificarFuncionamento() ? "sim" : "não"));
    }
}

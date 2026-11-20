/**
* CONTRATO COMPARTILHADO (assinaturas públicas). Responsável: Parte C.
*
* Encapsulamento: TODOS os atributos privados, setters com validação,
* limiares protegidos por código de acesso. Alteração inválida é rejeitada
* com mensagem clara e o valor anterior é mantido (setter retorna false).
*/
public class DadosTerritorio {
   private String regiao;
   private int populacao;
   private String codigoAcesso;
   private double limiarChuva;
   private double limiarNivelRio;
   private double limiarUmidade;
   public DadosTerritorio(String regiao, int populacao, String codigoAcesso) {
       // TODO Parte C: validar
       this.regiao = regiao;
       this.populacao = populacao;
       this.codigoAcesso = codigoAcesso;
       this.limiarChuva = 50.0;    // mm/h
       this.limiarNivelRio = 3.0;  // m
       this.limiarUmidade = 90.0;  // %
   }
   /\*\* Retorna o limiar do tipo informado (use as constantes Sensor.TIPO\_\*). \*/
   public double getLimiar(String tipoSensor) {
       // TODO Parte C (esta versão provisória já permite que A e B testem)
       switch (tipoSensor) {
           case Sensor.TIPO\_CHUVA: return limiarChuva;
           case Sensor.TIPO\_NIVEL\_RIO: return limiarNivelRio;
           case Sensor.TIPO\_UMIDADE: return limiarUmidade;
           default: return Double.NaN;
       }
   }
   /\*\* Altera um limiar SOMENTE com o código de acesso correto e valor dentro da faixa. \*/
   public boolean setLimiar(String tipoSensor, double valor, String codigoInformado) {
       // CORREÇÃO: checar código nulo primeiro (evita NullPointerException)
       if (codigoInformado == null || !codigoAcesso.equals(codigoInformado)) {
           System.out.println("Erro: código de acesso incorreto.");
           return false;
       }
       // CORREÇÃO: verificar tipo nulo antes do switch
       if (tipoSensor == null) {
           System.out.println("O sensor não existe.");
           return false;
       }
       switch (tipoSensor) {
           case Sensor.TIPO\_CHUVA:
               if (valor < 0 || valor > 500) {
                   System.out.println("Erro: chuva fora da faixa (0 a 500).");
                   return false;
               }
               this.limiarChuva = valor;   // CORREÇÃO: atribuir valor
               return true;                // CORREÇÃO: só retornar true no final
           case Sensor.TIPO\_NIVEL\_RIO:
               if (valor < 0 || valor > 20) {
                   // CORREÇÃO: mensagem falava em "nível de chuva", corrigi para "nível do rio"
                   System.out.println("Erro: nível do rio fora da faixa (0 a 20).");
                   return false;
               }
               this.limiarNivelRio = valor;   // CORREÇÃO: atribuir valor
               return true;                 // CORREÇÃO: só retornar true no final
           case Sensor.TIPO\_UMIDADE:
               if (valor < 0 || valor > 100) {
                   System.out.println("Erro: umidade fora da faixa (0 a 100).");
                   return false;
               }
               this.limiarUmidade = valor;  // CORREÇÃO: atribuir valor
               return true;                 // CORREÇÃO: só retornar true no final
           default:
               System.out.println("Tipo de sensor desconhecido.");
               return false;
       }
   }
   public String getRegiao() { return regiao; }
   public boolean setRegiao(String regiao) {
       if (regiao == null || regiao.isEmpty()) {
           System.out.println("Erro: Região inválida.");
           return false;
       }
       this.regiao = regiao;
       return true;
   }
   public int getPopulacao() { return populacao; }
   public boolean setPopulacao(int populacao) {
       if (populacao < 0) {
           System.out.println("Erro: População inválida.");
           return false;
       }
       this.populacao = populacao;
       return true;
   }
}

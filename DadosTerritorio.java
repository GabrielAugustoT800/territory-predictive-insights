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

    /** Retorna o limiar do tipo informado (use as constantes Sensor.TIPO_*). */
    public double getLimiar(String tipoSensor) {
        // TODO Parte C (esta versão provisória já permite que A e B testem)
        switch (tipoSensor) {
            case Sensor.TIPO_CHUVA: return limiarChuva;
            case Sensor.TIPO_NIVEL_RIO: return limiarNivelRio;
            case Sensor.TIPO_UMIDADE: return limiarUmidade;
            default: return Double.NaN;
        }
    }

    /** Altera um limiar SOMENTE com o código de acesso correto e valor dentro da faixa. */
    public boolean setLimiar(String tipoSensor, double valor, String codigoInformado) {
        // TODO Parte C
        if (codigoAcesso.equals(codigoInformado)) {
            return true;
        }
        switch(tipoSensor) {
            case Sensor.TIPO_CHUVA:
                if (valor < 0 || valor > 500) {
                    System.out.println("Erro: chuva fora da faixa (0 a 500).");
                    return false;
                }
                this.limiarChuva = valor;
                return true;
            // faça os outros dois casos sozinho, no mesmo modelo

            0000000000case Sensor.TIPO_NIVEL_RIO:
                
            default:
                System.out.println("Erro: tipo de sensor desconhecido.");
                return false;
    }

            
        return false;
    }

    public String getRegiao() { return regiao; }

    public boolean setRegiao(String regiao) {
        // TODO Parte C: rejeitar vazio/null
        if (regiao == null || regiao.isEmpty()) {
            System.out.println("Erro: Região inválida");
            return false;
        }
        this.regiao = regiao;
        return true;

    public int getPopulacao() { return populacao; }

    public boolean setPopulacao(int populacao) {
        // TODO Parte C: rejeitar negativo
        if (populacao < 0) {
            System.out.println("População inválida");
            return false;
        }
        this.populacao = populacao;
        return true;
        return false;
    }
}

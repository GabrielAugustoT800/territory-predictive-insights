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

        if (codigoInformado == null || !codigoAcesso.equals(codigoInformado)) {
            System.out.println("Erro: código de acesso incorreto.");
            return false;
        }

        if (tipoSensor == null) {
            System.out.println("O sensor não existe.");
            return false;
        }
        
        switch(tipoSensor) {
            case Sensor.TIPO_CHUVA:
                if (valor < 0 || valor > 500) {
                    System.out.println("Erro: chuva fora da faixa (0 a 500).");
                    return false;
                }
                this.limiarChuva = valor;
                return true;

            case Sensor.TIPO_NIVEL_RIO:
                if (valor < 0 || valor > 20) {
                    System.out.println("Erro: nível de chuva fora do limite de faixa (0 a 2).");
                    return false;
                }
                this.limiarNivelRio = valor;
                return true;

            case Sensor.TIPO_UMIDADE:
                if (valor < 0 || valor > 100) {
                    System.out.println("Erro: umidade fora do limite de faixa (0 a 100).");
                    return false;
                }
                this.limiarUmidade = valor;
                return true;
    }

    public String getRegiao() { return regiao; }

    public boolean setRegiao(String regiao) {
        if (regiao == null || regiao.isEmpty()) {
            System.out.println("Erro: Região inválida.");
            return false;
        }
        this.regiao = regiao;
        return true;

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

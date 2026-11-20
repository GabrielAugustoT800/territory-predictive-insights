/**
 * Responsável: Parte C (Dados e Menu).
 *
 * Encapsulamento: TODOS os atributos privados, setters com validação,
 * limiares protegidos por código de acesso. Alteração inválida é rejeitada
 * com mensagem clara e o valor anterior é mantido (setter retorna false).
 */
public final class DadosTerritorio {

    private String regiao;
    private int populacao;
    private String codigoAcesso;
    private double limiarChuva;
    private double limiarNivelRio;
    private double limiarUmidade;

    public DadosTerritorio(String regiao, int populacao, String codigoAcesso) {
        if (codigoAcesso == null || codigoAcesso.isBlank()) {
            throw new IllegalArgumentException("O código de acesso não pode ser vazio.");
        }
        this.codigoAcesso = codigoAcesso;

        // Valores padrão, substituídos abaixo se os informados forem válidos
        this.regiao = "Região não informada";
        this.populacao = 0;
        setRegiao(regiao);
        setPopulacao(populacao);

        this.limiarChuva = 50.0;    // mm/h
        this.limiarNivelRio = 3.0;  // m
        this.limiarUmidade = 90.0;  // %
    }

    /** Retorna o limiar do tipo informado (use as constantes Sensor.TIPO_*). */
    public double getLimiar(String tipoSensor) {
        if (tipoSensor == null) {
            return Double.NaN;
        }
        switch (tipoSensor) {
            case Sensor.TIPO_CHUVA: return limiarChuva;
            case Sensor.TIPO_NIVEL_RIO: return limiarNivelRio;
            case Sensor.TIPO_UMIDADE: return limiarUmidade;
            default: return Double.NaN;
        }
    }

    /** Altera um limiar SOMENTE com o código de acesso correto e valor dentro da faixa. */
    public boolean setLimiar(String tipoSensor, double valor, String codigoInformado) {
        // Checa o código primeiro (código nulo não gera NullPointerException)
        if (codigoInformado == null || !codigoAcesso.equals(codigoInformado)) {
            System.out.println("Erro: código de acesso incorreto. Limiar mantido.");
            return false;
        }

        if (tipoSensor == null) {
            System.out.println("Erro: o sensor não existe.");
            return false;
        }

        switch (tipoSensor) {
            case Sensor.TIPO_CHUVA:
                if (valor <= 0 || valor > 500) {
                    System.out.println("Erro: limiar de chuva fora da faixa (maior que 0 até 500 mm/h). Valor anterior mantido.");
                    return false;
                }
                this.limiarChuva = valor;
                return true;

            case Sensor.TIPO_NIVEL_RIO:
                if (valor <= 0 || valor > 20) {
                    System.out.println("Erro: limiar do nível do rio fora da faixa (maior que 0 até 20 m). Valor anterior mantido.");
                    return false;
                }
                this.limiarNivelRio = valor;
                return true;

            case Sensor.TIPO_UMIDADE:
                if (valor <= 0 || valor > 100) {
                    System.out.println("Erro: limiar de umidade fora da faixa (maior que 0 até 100 %). Valor anterior mantido.");
                    return false;
                }
                this.limiarUmidade = valor;
                return true;

            default:
                System.out.println("Erro: tipo de sensor desconhecido.");
                return false;
        }
    }

    public String getRegiao() { return regiao; }

    public boolean setRegiao(String regiao) {
        if (regiao == null || regiao.isBlank()) {
            System.out.println("Erro: região inválida. Valor anterior mantido.");
            return false;
        }
        this.regiao = regiao.trim();
        return true;
    }

    public int getPopulacao() { return populacao; }

    public boolean setPopulacao(int populacao) {
        if (populacao < 0) {
            System.out.println("Erro: população não pode ser negativa. Valor anterior mantido.");
            return false;
        }
        this.populacao = populacao;
        return true;
    }
}

/**
 * Responsável: Parte A (Sensores).
 * Faixa realista sugerida para a simulação: 30 a 100 %.
 *
 * O construtor abaixo é CONTRATO (o menu da Parte C usa exatamente esta assinatura).
 */
public class SensorUmidade extends ComponenteTerritorial implements Sensor {

    private double limite;

    public SensorUmidade(int id, String nome, String regiao) {
        super(id, nome, regiao);
        // TODO Parte A: valor inicial do limite
    }

    @Override
    public double lerValor() {
        // TODO Parte A: simular leitura com Math.random()
        return 0.0;
    }

    @Override
    public boolean verificarFuncionamento() {
        // TODO Parte A
        return true;
    }

    @Override
    public String retornarTipo() {
        return Sensor.TIPO_UMIDADE;
    }

    @Override
    public String getUnidade() {
        return "%";
    }

    @Override
    public void definirLimite(double limite) {
        // TODO Parte A: validar e guardar
    }

    @Override
    public double getLimite() {
        return limite;
    }

    @Override
    public boolean ultrapassouLimite(double valor) {
        // TODO Parte A
        return false;
    }

    @Override
    public String verificarStatus() {
        // TODO Parte A: ex. "Sensor de Chuva #1 (Centro) - ATIVO - funcionando"
        return nome + " - " + status;
    }
}

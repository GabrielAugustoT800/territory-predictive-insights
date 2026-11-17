/**
 * Responsável: Parte A (Sensores).
 * Faixa realista sugerida para a simulação: 0,5 a 5,0 m.
 *
 * O construtor abaixo é CONTRATO (o menu da Parte C usa exatamente esta assinatura).
 */
public class SensorNivelRio extends ComponenteTerritorial implements Sensor {

    private double limite;

    public SensorNivelRio(int id, String nome, String regiao) {
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
        return Sensor.TIPO_NIVEL_RIO;
    }

    @Override
    public String getUnidade() {
        return "m";
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

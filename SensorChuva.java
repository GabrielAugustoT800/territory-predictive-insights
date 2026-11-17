/**
 * Responsável: Parte A (Sensores).
 * Faixa realista sugerida para a simulação: 0 a 80 mm/h.
 *
 * O construtor abaixo é CONTRATO (o menu da Parte C usa exatamente esta assinatura).
 */
public class SensorChuva extends ComponenteTerritorial implements Sensor {

    private double limite;

    public SensorChuva(int id, String nome, String regiao) {
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
        return Sensor.TIPO_CHUVA;
    }

    @Override
    public String getUnidade() {
        return "mm/h";
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

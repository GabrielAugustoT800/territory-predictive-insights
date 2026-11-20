public class SensorChuva extends ComponenteTerritorial implements Sensor {

    private static final double MIN = 0.0;
    private static final double MAX = 80.0;
    private static final double LIMITE_PADRAO = 50.0;

    private double limite;

    public SensorChuva(int id, String nome, String regiao) {
        super(id, nome, regiao);
        this.limite = LIMITE_PADRAO;
    }

    @Override
    public double lerValor() {
        double valor = MIN + Math.random() * (MAX - MIN);
        return Math.round(valor * 100.0) / 100.0;
    }

    @Override
    public boolean verificarFuncionamento() {
        return "ATIVO".equals(status);
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
        if (limite > 0) {
            this.limite = limite;
        }
    }

    @Override
    public double getLimite() {
        return limite;
    }

    @Override
    public boolean ultrapassouLimite(double valor) {
        return valor > limite;
    }

    @Override
    public String verificarStatus() {
        String funcionamento = verificarFuncionamento() ? "funcionando" : "fora de operacao";
        return nome + " #" + id + " (" + regiao + ") - " + status + " - " + funcionamento
             + " | limite: " + limite + " " + getUnidade();
    }
}

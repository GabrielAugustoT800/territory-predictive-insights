/**
 * Responsável: Parte B (Previsão e Alertas).
 * Guarda um alerta e explica o porquê: qual sensor, valor previsto, limite estourado.
 */
public class Alerta {

    private String tipoSensor;
    private String nomeEstrategia;
    private double valorPrevisto;
    private double limiar;
    private NivelAlerta nivel;

    public Alerta(String tipoSensor, String nomeEstrategia, double valorPrevisto, double limiar, NivelAlerta nivel) {
        this.tipoSensor = tipoSensor;
        this.nomeEstrategia = nomeEstrategia;
        this.valorPrevisto = valorPrevisto;
        this.limiar = limiar;
        this.nivel = nivel;
    }

    /** Texto exibido no console. A decisão final fica com a pessoa (sugerir, não ordenar). */
    public String explicar() {
        // TODO Parte B: ex. "[ALERTA] Sensor CHUVA | Média Móvel previu 62,0 mm/h | limite 50,0 ..."
        return "[" + nivel + "] " + tipoSensor;
    }

    public NivelAlerta getNivel() { return nivel; }
    public String getTipoSensor() { return tipoSensor; }
    public String getNomeEstrategia() { return nomeEstrategia; }
    public double getValorPrevisto() { return valorPrevisto; }
    public double getLimiar() { return limiar; }
}

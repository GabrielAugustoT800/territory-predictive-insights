import java.util.Locale;

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

    public Alerta(
            String tipoSensor,
            String nomeEstrategia,
            double valorPrevisto,
            double limiar,
            NivelAlerta nivel) {
        this.tipoSensor = tipoSensor;
        this.nomeEstrategia = nomeEstrategia;
        this.valorPrevisto = valorPrevisto;
        this.limiar = limiar;
        this.nivel = nivel;
    }

    /** Texto exibido no console. A decisão final fica com a pessoa (sugerir, não ordenar). */
    public String explicar() {
        // identifica o nome do nivel do alerta
        String nomeNivel;
        switch (nivel) {
            case NORMAL:
                nomeNivel = "NORMAL";
                break;
            case ATENCAO:
                nomeNivel = "ATENÇÃO";
                break;
            case ALERTA:
                nomeNivel = "ALERTA";
                break;
            case CRITICO:
                nomeNivel = "CRÍTICO";
                break;
            default:
                nomeNivel = "DESCONHECIDO";
        }

        // explicacao basica do nivel do alerat
        String explicacao;
        switch (nivel) {
            case NORMAL:
                explicacao = "Valor previsto abaixo de 80% do limiar.";
                break;
            case ATENCAO:
                explicacao = "Valor próximo do limiar de alerta (80% a menos de 100%).";
                break;
            case ALERTA:
                explicacao = "Valor atingiu ou ultrapassou o limiar (100% a menos de 120%).";
                break;
            case CRITICO:
                explicacao = "Valor previsto atingiu pelo menos 120% do limiar.";
                break;
            default:
                explicacao = "Não foi possível identificar o nível.";
        }

        // montagem da mensagem
        return String.format(
                Locale.forLanguageTag("pt-BR"),
                "[%s] Sensor %s | Estratégia: %s | Valor previsto: %.1f | Limiar: %.1f | %s",
                nomeNivel,
                tipoSensor,
                nomeEstrategia,
                valorPrevisto,
                limiar,
                explicacao
        );
    }

    public NivelAlerta getNivel() {
        return nivel;
    }

    public String getTipoSensor() {
        return tipoSensor;

    }

    public String getNomeEstrategia() {
        return nomeEstrategia;
    }

    public double getValorPrevisto() {
        return valorPrevisto;
    }

    public double getLimiar() {
        return limiar;
    }
}

/**
 * CONTRATO COMPARTILHADO. Responsável pela implementação: Parte B.
 * Compara automaticamente a previsão com o limiar e gera um Alerta.
 */
public class GeradorAlertas {

    /** Decide o nível conforme a regra descrita em NivelAlerta. */
    public NivelAlerta classificar(double valorPrevisto, double limiar) {
        // TODO Parte B
        return NivelAlerta.NORMAL;
    }

    /**
     * Gera o alerta. Usado pelo menu da Parte C. Se valorPrevisto for NaN
     * (dados insuficientes), retornar null: o sistema se cala.
     */
    public Alerta avaliar(String tipoSensor, String nomeEstrategia, double valorPrevisto, double limiar) {
        // TODO Parte B
        return null;
    }
}

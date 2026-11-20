/**
 * CONTRATO COMPARTILHADO. Responsável pela implementação: Parte B.
 * Compara automaticamente a previsão com o limiar e gera um Alerta.
 */
public class GeradorAlertas {

    /** Decide o nível conforme a regra descrita em NivelAlerta. */
    public NivelAlerta classificar(double valorPrevisto, double limiar) {
        if (Double.isNaN(valorPrevisto) || Double.isInfinite(valorPrevisto)) { // validacao dop bvalor previsto
            throw new IllegalArgumentException("O valor previsto deve ser válido.");
        }

        if (Double.isNaN(limiar) || Double.isInfinite(limiar) || limiar <= 0) { // validacao do limiar
            throw new IllegalArgumentException("O limiar deve ser um número positivo e válido.");
        }

        double percentual = valorPrevisto / limiar;

        if (percentual < 0.80) {
            return NivelAlerta.NORMAL;
        }
        else if (percentual < 1.00) {
            return NivelAlerta.ATENCAO;
        }
        else if (percentual < 1.20) {
            return NivelAlerta.ALERTA;
        }
        else {
            return NivelAlerta.CRITICO;
        }
    }

    /**
     * Gera o alerta. Usado pelo menu da Parte C. Se valorPrevisto for NaN
     * (dados insuficientes), retornar null: o sistema se cala.
     */
    public Alerta avaliar(String tipoSensor, String nomeEstrategia, double valorPrevisto, double limiar) {
        if (Double.isNaN(valorPrevisto)) { // o sistema se cala quadno nao ha previsao disponivel
            return null;
        }

        NivelAlerta nivel = classificar(valorPrevisto, limiar);

        return new Alerta(tipoSensor, nomeEstrategia, valorPrevisto, limiar, nivel);
    }
}

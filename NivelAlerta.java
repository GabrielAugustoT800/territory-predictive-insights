/**
 * CONTRATO COMPARTILHADO. Responsável: Parte B.
 * Regra combinada pelo grupo (valor previsto em relação ao limiar):
 *   NORMAL  : abaixo de 80% do limiar
 *   ATENCAO : de 80% até abaixo de 100%
 *   ALERTA  : de 100% até abaixo de 120%
 *   CRITICO : 120% ou mais
 */
public enum NivelAlerta {
    NORMAL, ATENCAO, ALERTA, CRITICO
}

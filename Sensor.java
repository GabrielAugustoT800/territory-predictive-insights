/**
 * CONTRATO COMPARTILHADO (definido na main, não alterar sem avisar o grupo).
 * Responsável pela implementação: Parte A (Sensores).
 *
 * Interface que todo sensor do território deve cumprir.
 */
public interface Sensor {

    // Tipos de sensor: use SEMPRE estas constantes (chave usada em DadosTerritorio e nos alertas).
    String TIPO_CHUVA = "CHUVA";         // mm/h
    String TIPO_NIVEL_RIO = "NIVEL_RIO"; // metros
    String TIPO_UMIDADE = "UMIDADE";     // % (umidade relativa do solo/ar)

    /** Faz uma nova leitura (simulada com Math.random() em faixa realista). */
    double lerValor();

    /** Retorna true se o sensor está funcionando. */
    boolean verificarFuncionamento();

    /** Retorna uma das constantes TIPO_* acima. */
    String retornarTipo();

    /** Unidade de medida exibida no console (ex.: "mm/h"). */
    String getUnidade();

    /** Define o limite de alerta do sensor. */
    void definirLimite(double limite);

    /** Retorna o limite de alerta atual do sensor. */
    double getLimite();

    /** Retorna true quando o valor informado passa do limite do sensor. */
    boolean ultrapassouLimite(double valor);
}

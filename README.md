# Territory Predictive Insights — Clima Local (CitizenTech)

Global Solution 2º/2026 — Programação Orientada a Objetos.

Plataforma de monitoramento territorial em Java que lê sensores de **chuva**, **nível do rio** e **umidade**, gera previsões simples e emite alertas (ATENÇÃO, ALERTA, CRÍTICO) sobre **risco de alagamento** em pontos vulneráveis.

## Como compilar e rodar

Requisito: Java 17 ou superior.

```bash
javac -encoding UTF-8 *.java
java SistemaMonitoramento
```

## Público beneficiado

<!-- Parte C consolida. Quem sofre o problema e qual decisão o sistema apoia. -->
TODO

## Estrutura e conceitos de OOP

| Arquivo | Conceito | Parte |
|---|---|---|
| `ComponenteTerritorial.java` | Classe abstrata | A |
| `Sensor.java` | Interface | A |
| `SensorChuva.java`, `SensorNivelRio.java`, `SensorUmidade.java` | `extends` + `implements` | A |
| `EstrategiaPrevisao.java` | Classe abstrata (herança) | B |
| `PrevisaoMediaMovel.java`, `PrevisaoComparacao.java` | Herança + `super()` | B |
| `NivelAlerta.java`, `Alerta.java`, `GeradorAlertas.java` | Sistema de alertas | B |
| `DadosTerritorio.java` | Encapsulamento | C |
| `SistemaMonitoramento.java` | Classe principal com menu | C |

## Decisões da equipe

### Parte A — Sensores
TODO

### Parte B — Previsão e Alertas
- **Média Móvel:** calcula a média das últimas N leituras, considerando apenas a janela mais recente definida no construtor.
- **Comparação de Períodos:** calcula a média do período atual e a do período anterior. A projeção é feita pela fórmula médiaAtual + (médiaAtual - médiaAnterior), repetindo a variação observada no período seguinte. Como os sensores representam grandezas não negativas, o resultado da projeção não fica abaixo de zero.
- As estratégias herdam de EstrategiaPrevisao. A classe base concentra a verificação das leituras e a interpretação do resultado; cada subclasse implementa seu próprio método prever() e utiliza super() no construtor.
- O sistema exige pelo menos 5 leituras e rejeita a lista se algum valor for nulo, NaN ou infinito. A estratégia de comparação também exige dois períodos completos; portanto, precisa de 2 × tamanhoPeriodo leituras, respeitado o mínimo geral de 5.
- Quando os dados são insuficientes ou inválidos, a previsão retorna Double.NaN. O GeradorAlertas não cria um alerta nesse caso (avaliar() retorna null), evitando apresentar uma previsão sem dados suficientes.
- Níveis de alerta, calculados em relação ao limiar do sensor: **NORMAL** abaixo de 80%; **ATENÇÃO** de 80% até abaixo de 100%; **ALERTA** de 100% até abaixo de 120%; **CRÍTICO** a partir de 120%.
- Cada objeto Alerta registra o tipo de sensor, a estratégia utilizada, o valor previsto, o limiar e o nível, além de produzir uma explicação para exibição no console. Os construtores das estratégias rejeitam janelas ou períodos menores ou iguais a zero.

### Parte C — Dados e Menu
TODO

## Reflexão crítica

<!-- Meia página. Responder às 3 perguntas do enunciado. -->

**Quando o sistema deve se calar?**
TODO

**O que os dados não representam?**
TODO

**Por que o alerta apoia a decisão da pessoa, em vez de decidir por ela?**
TODO

## Uso de IA

<!-- Cada integrante declara onde usou IA e como revisou. -->
- Organização do repositório, divisão de tarefas e esqueleto dos contratos (assinaturas): gerados com apoio do Claude e revisados pela equipe.
- Parte A: TODO
- Parte B: IA foi utilizada para gerar a maioria das strings (apenas a mensagem, a lógica foi feita manualmente), para gerar os parametros para o teste, e para otimizar as validações das leituras (eu estava tentando usar if/else para checar a validação, mas a IA recomendou mudar para IllegalArgumentException).
- Parte C: TODO

## Divisão de tarefas

| Parte | Integrante | Branch | Responsabilidade |
|---|---|---|---|
| A | TODO | `feature/sensores` | Interface `Sensor`, `ComponenteTerritorial`, 3 sensores |
| B | Lucas Koiti | `feature/previsao-alertas` | `EstrategiaPrevisao`, 2 previsões, sistema de alertas |
| C | TODO | `feature/dados-menu` | `DadosTerritorio`, menu `SistemaMonitoramento`, README final |

## O que faltou e como continuaríamos

TODO

## Integrantes

| Nome | RM |
|---|---|
| Gabriel Augusto da Silva | RM: 567057 |
| Lucas Koiti Uyeno de Souza | RM: 568128 |
| Lucas Morio Ikeda | RM: 567616 |

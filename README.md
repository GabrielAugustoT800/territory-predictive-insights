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
- Níveis de alerta (valor previsto em relação ao limiar): ATENÇÃO a partir de 80%, ALERTA a partir de 100%, CRÍTICO a partir de 120%.
- Com menos de 5 leituras (`EstrategiaPrevisao.MINIMO_LEITURAS`) o sistema não faz previsão.

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
- Parte B: TODO
- Parte C: TODO

## Divisão de tarefas

| Parte | Integrante | Branch | Responsabilidade |
|---|---|---|---|
| A | TODO | `feature/sensores` | Interface `Sensor`, `ComponenteTerritorial`, 3 sensores |
| B | TODO | `feature/previsao-alertas` | `EstrategiaPrevisao`, 2 previsões, sistema de alertas |
| C | TODO | `feature/dados-menu` | `DadosTerritorio`, menu `SistemaMonitoramento`, README final |

## O que faltou e como continuaríamos

TODO

## Integrantes

| Nome | RM |
|---|---|
| TODO | TODO |
| TODO | TODO |
| TODO | TODO |

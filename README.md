# Territory Predictive Insights — Clima Local (CitizenTech)

Global Solution 2º/2026 — Programação Orientada a Objetos

Sistema em Java que monitora **chuva**, **nível do rio** e **umidade** em regiões de uma cidade, prevê os próximos valores e emite alertas de **risco de alagamento** (ATENÇÃO, ALERTA, CRÍTICO), explicando o motivo de cada um.

## Como compilar e rodar

Requisito: JDK 17 ou superior.

```bash
javac -encoding UTF-8 *.java
java SistemaMonitoramento
```

Testes de cada parte (opcionais):

```bash
java TesteSensores
java TestePrevisao
java TesteDados
```

**Rodando no Windows:** se os acentos aparecerem trocados no terminal (ex.: `previs?es`), rode `chcp 65001` antes do `java`. Se o comando `javac` não for reconhecido, é preciso instalar um JDK (ex.: Eclipse Temurin) marcando a opção "Add to PATH".

## Como usar o menu

**Roteiro rápido:** escolha a opção **4**, simule **6** leituras e depois escolha a opção **2** para ver as previsões e os alertas.

```
=== Territory Predictive Insights - Clima Local ===
1. Verificar sensores
2. Rodar previsões
3. Gerenciar dados do território
4. Simular próximas leituras
5. Exibir status completo
0. Sair
```

### 1. Verificar sensores
Faz **uma leitura agora** em cada sensor e mostra o status (ativo, funcionando), o valor lido, o limiar e se o valor lido já passou do limiar. Cada leitura feita aqui também entra no histórico.

### 2. Rodar previsões
É o coração do sistema. Ela olha para o **histórico de leituras** de cada sensor e responde: *"se continuar assim, qual será o próximo valor?"*. Para isso roda as 2 estratégias de previsão:

- **Média Móvel:** faz a média das **3 últimas leituras**. Mostra o nível em que o sensor está agora, suavizando picos isolados.
- **Comparação de Períodos:** compara a média das 3 últimas leituras com a média das 3 anteriores. Se subiu, projeta que vai continuar subindo na mesma proporção (e vice-versa). Mostra a **tendência**.

Para cada previsão, o sistema mostra:
1. O **valor previsto**.
2. A **interpretação**: quanto esse valor representa do limiar (ex.: "109,3% do limiar").
3. Um **alerta**, se o valor previsto chegar perto do limiar ou passar dele:

| Nível | Quando acontece (valor previsto em relação ao limiar) |
|---|---|
| (sem alerta) | abaixo de 80% |
| ATENÇÃO | de 80% até abaixo de 100% |
| ALERTA | de 100% até abaixo de 120% |
| CRÍTICO | 120% ou mais |

Exemplo de saída:

```
NIVEL_RIO [Média Móvel]: previsto = 2,67 m
  Valor previsto corresponde a 89,0% do limiar. Atenção: está próximo do limite. Cálculo baseado nas últimas 3 leituras.
  >> [ATENÇÃO] Sensor NIVEL_RIO | Estratégia: Média Móvel | Valor previsto: 2,7 | Limiar: 3,0 | Valor próximo do limiar de alerta (80% a menos de 100%).
```

**Importante:** a previsão só acontece quando há leituras suficientes no histórico. A Média Móvel precisa de pelo menos **5** leituras e a Comparação de Períodos de **6** (dois períodos de 3). Com menos, o sistema informa "previsão indisponível" e **não emite alerta**. Por isso, use antes a opção 4 (ou a opção 1 várias vezes).

### 3. Gerenciar dados do território
Mostra região, população e limiares atuais, e abre um submenu:

1. **Alterar região:** não aceita texto vazio.
2. **Alterar população:** não aceita número negativo nem texto.
3. **Alterar limiar:** pede o tipo do sensor (`CHUVA`, `NIVEL_RIO` ou `UMIDADE`), o novo valor e o **código de acesso** `ADMIN123`. Rejeita código errado e valor fora da faixa (chuva de 0 a 500 mm/h, rio de 0 a 20 m, umidade de 0 a 100 %, sempre maior que 0). Quando aceito, o limite do sensor é atualizado junto.

Em qualquer alteração inválida, o sistema mostra o motivo e **mantém o valor anterior**.

### 4. Simular próximas leituras
Pergunta quantas leituras simular (de 1 a 24) e adiciona esse número de leituras novas ao histórico de cada sensor. É a forma rápida de juntar dados para a opção 2.

### 5. Exibir status completo
Mostra a região, a população e, para cada sensor, o status, todas as leituras do histórico e o limiar atual.

### 0. Sair
Encerra o programa.

## Público beneficiado

Moradores de áreas sujeitas a alagamento e a equipe (Defesa Civil, prefeitura ou associação de bairro) que acompanha essas regiões. O sistema apoia decisões como reforçar o monitoramento de um ponto, avisar a comunidade com antecedência ou acionar uma vistoria antes de o problema acontecer.

## Arquitetura e conceitos de OOP

| Conceito | Onde | Como foi aplicado |
|---|---|---|
| Classe abstrata | `ComponenteTerritorial` | Atributos comuns (id, nome, região, status), métodos concretos `ativar()`/`desativar()` e método abstrato `verificarStatus()` |
| Interface | `Sensor` | Contrato (`lerValor`, `verificarFuncionamento`, `retornarTipo`, limites) implementado por `SensorChuva`, `SensorNivelRio` e `SensorUmidade` |
| Encapsulamento | `DadosTerritorio` | Atributos privados, setters com validação e limiares protegidos por código de acesso; valor inválido é rejeitado e o anterior é mantido |
| Herança | `EstrategiaPrevisao` | `PrevisaoMediaMovel` (atributo `tamanhoJanela`) e `PrevisaoComparacao` (atributo `tamanhoPeriodo`) sobrescrevem `prever()` e usam `super()` no construtor e em `interpretar()` |
| Sistema de alertas | `GeradorAlertas`, `Alerta`, `NivelAlerta` | Compara a previsão com o limiar, classifica o nível e explica o motivo |
| Classe principal | `SistemaMonitoramento` | Menu, loop principal e histórico de leituras em `ArrayList` |

Os sensores usam herança e interface ao mesmo tempo: `SensorChuva extends ComponenteTerritorial implements Sensor`.

## Desenvolvimento

O grupo optou por uma estratégia de **desenvolvimento paralelo com branches separadas**, para que cada integrante pudesse trabalhar na sua parte **sem precisar do código dos outros**, estivesse a parte anterior completa, incompleta ou ainda não iniciada.

1. **Contratos primeiro:** antes da divisão, a branch `main` recebeu um esqueleto com todos os arquivos e as **assinaturas públicas** definidas (nomes de métodos, parâmetros, retornos e construtores). Os corpos dos métodos ficaram como `TODO`, com valores provisórios que já compilavam: sensores retornando `0.0`, previsões retornando "dados insuficientes" e limiares com valores padrão.
2. **Uma branch por parte:** `feature/sensores`, `feature/previsao-alertas` e `feature/dados-menu`. Cada integrante editou **apenas os arquivos da própria parte**, o que evitou conflitos no merge.
3. **Teste isolado:** cada parte tem sua própria classe de teste (`TesteSensores`, `TestePrevisao`, `TesteDados`), permitindo testar sem depender das outras.
4. **Integração:** ao final, as três branches foram unidas na `main` (A e B primeiro, C por último), e o sistema completo foi compilado e testado.

Esse modelo segue o princípio de **baixo acoplamento**: as partes dependem apenas dos contratos combinados, não da implementação umas das outras. O fluxo de trabalho detalhado está em [`GUIA_EQUIPE.md`](GUIA_EQUIPE.md).

## Decisões da equipe

- **Recorte:** clima local (risco de alagamento), por corresponder aos sensores da estrutura obrigatória.
- **Leituras simuladas** com `Math.random()` em faixas realistas: chuva de 0 a 80 mm/h, rio de 0,5 a 5 m, umidade de 30 a 100 %.
- **Níveis de alerta** em relação ao limiar: ATENÇÃO a partir de 80%, ALERTA a partir de 100%, CRÍTICO a partir de 120%.
- **Mínimo de 5 leituras** para qualquer previsão (`EstrategiaPrevisao.MINIMO_LEITURAS`).
- **Limiar como fonte única:** o limiar fica em `DadosTerritorio`, protegido por código de acesso. Quando é alterado, o limite do sensor é atualizado junto.

## Reflexão crítica

**Quando o sistema deve se calar?**
Com menos de 5 leituras, o sistema não faz previsão e informa que os dados são insuficientes: uma média de 2 ou 3 valores muda muito a cada nova leitura e geraria alarmes falsos. O mesmo vale para a Comparação de Períodos, que precisa de dois períodos completos para medir uma tendência. O sistema também não lê sensores fora de operação, em vez de inventar um valor.

**O que os dados não representam?**
Um único sensor por região descreve uma média da área inteira, não uma rua ou um córrego específico. As leituras simuladas são aleatórias e não seguem a continuidade de uma chuva real; por isso a Comparação de Períodos às vezes projeta valores impossíveis (umidade acima de 100%) ou zero. O sistema também não considera fatores como a impermeabilização do solo, bueiros entupidos ou o histórico de alagamentos de cada ponto.

**Por que o alerta apoia a decisão da pessoa, em vez de decidir por ela?**
O alerta mostra qual sensor, qual estratégia, o valor previsto e o limiar estourado, e recomenda conferir as condições no local antes de agir. Quem conhece o território (moradores, Defesa Civil) decide se evacua, interdita ou apenas observa, porque tem informações que o sistema não tem.

## Uso de IA

### Uso 1: Planejamento e esqueleto do repositório (17/11/2026)

**Ferramenta:** Claude (Anthropic)

**Contexto:** o esqueleto inicial do projeto foi montado **remotamente** pelo Claude. O pedido foi enviado por um integrante quando estava fora de casa, com o objetivo de agilizar a construção do projeto e permitir que os três começassem a trabalhar em paralelo o quanto antes.

**Onde foi usada:**
- **Análise do enunciado:** leitura do PDF da GS e lista dos requisitos obrigatórios, critérios de avaliação e riscos de nota.
- **Escolha do recorte:** sugestão do recorte "Clima local (alagamento)".
- **Divisão de tarefas:** proposta de divisão em 3 partes independentes, com análise da complexidade de cada uma.
- **Contratos compartilhados:** definição das assinaturas públicas de `Sensor`, `ComponenteTerritorial`, `EstrategiaPrevisao`, `DadosTerritorio`, `GeradorAlertas`, `Alerta` e `NivelAlerta`.
- **Esqueleto dos arquivos:** criação dos arquivos `.java` com corpos provisórios marcados como `TODO`, que compilavam mas não continham a lógica do sistema.
- **Repositório:** criação do `.gitignore`, do esqueleto do README, do `GUIA_EQUIPE.md` e das três branches.
- **Conceito:** explicação do termo "baixo acoplamento" (*loose coupling*).

**O que NÃO foi feito pela IA nessa etapa:** a implementação da lógica (leitura dos sensores, previsões, alertas, validações e menu), que ficou a cargo dos integrantes.

**Como revisamos:** o código foi compilado e todas as funcionalidades foram testadas.

### Uso 2: Integração final e documentação (19/11/2026)

**Ferramenta:** Claude (Anthropic)

**Onde foi usada:** revisão das 3 branches, merge na `main` e correções na Parte C: remoção de caracteres de formatação Markdown que impediam a compilação, troca de `List` por `ArrayList` no histórico, menu resistente a entradas inválidas, sincronização dos limites dos sensores com os limiares, rejeição de limiar 0 e edição de região e população pelo menu (commit `fix(integracao)`). Também apoiou a redação deste README.

**Como revisamos:** o código foi compilado e todas as funcionalidades foram testadas.

## Divisão de tarefas

| Parte | Integrante | Branch | Responsabilidade |
|---|---|---|---|
| A — Sensores | Gabriel Augusto da Silva | `feature/sensores` | `ComponenteTerritorial`, `Sensor`, `SensorChuva`, `SensorNivelRio`, `SensorUmidade`, `TesteSensores` |
| B — Previsão e Alertas | Lucas Koiti Uyeno | `feature/previsao-alertas` | `EstrategiaPrevisao`, `PrevisaoMediaMovel`, `PrevisaoComparacao`, `Alerta`, `NivelAlerta`, `GeradorAlertas`, `TestePrevisao` |
| C — Dados e Menu | Lucas Morio Ikeda | `feature/dados-menu` | `DadosTerritorio`, `SistemaMonitoramento`, `TesteDados` |

## O que faltou e como continuaríamos

- **Bônus** (histórico salvo em arquivo ou simulação de 24 horas): não implementado.
- **Simulação com tendência:** gerar cada leitura a partir da anterior, em vez de valores totalmente aleatórios, deixaria as previsões mais realistas.
- **Ativar e desativar sensores pelo menu**, para demonstrar o sistema se calando quando um sensor sai de operação.

## Integrantes

| Nome | RM |
|---|---|
| Gabriel Augusto da Silva | 567057 |
| Lucas Morio Ikeda | 561676 |
| Lucas Koiti Uyeno | 568128 |

**Repositório:** https://github.com/GabrielAugustoT800/territory-predictive-insights

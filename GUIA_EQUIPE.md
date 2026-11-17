# Guia da equipe — como trabalhar sem depender um do outro

## A regra de ouro

**Cada pessoa só edita os arquivos da própria parte.** Os contratos (assinaturas públicas: nomes de métodos, parâmetros, retornos e construtores) já estão na `main` e **não mudam** sem combinar com o grupo. Assim o merge final não tem conflito.

| Parte | Branch | Arquivos que você edita |
|---|---|---|
| A — Sensores | `feature/sensores` | `Sensor.java`*, `ComponenteTerritorial.java`*, `SensorChuva.java`, `SensorNivelRio.java`, `SensorUmidade.java`, `TesteSensores.java` (novo) |
| B — Previsão e Alertas | `feature/previsao-alertas` | `EstrategiaPrevisao.java`*, `PrevisaoMediaMovel.java`, `PrevisaoComparacao.java`, `NivelAlerta.java`*, `Alerta.java`, `GeradorAlertas.java`*, `TestePrevisao.java` (novo) |
| C — Dados e Menu | `feature/dados-menu` | `DadosTerritorio.java`*, `SistemaMonitoramento.java`, `TesteDados.java` (novo) |

\* Arquivo de contrato: você implementa os `TODO`, mas **não muda as assinaturas públicas**. Pode adicionar métodos `private` ou atributos novos à vontade.

README: cada um escreve apenas nas linhas `TODO` da sua parte (Decisões, Uso de IA). A Parte C faz o acabamento final.

## Por que funciona mesmo se outra parte não estiver pronta

Todos os arquivos já existem e **compilam** com valores provisórios:
- Os sensores retornam `0.0` até a Parte A terminar.
- As previsões retornam `Double.NaN` (dados insuficientes) até a Parte B terminar.
- `DadosTerritorio.getLimiar()` já retorna limiares padrão (50 mm/h, 3,0 m, 90%).

Para testar a sua parte sozinho, crie seu `Teste*.java` com um `main` próprio, como o enunciado recomenda. Exemplo para a Parte B:

```java
import java.util.ArrayList;

public class TestePrevisao {
    public static void main(String[] args) {
        ArrayList<Double> leituras = new ArrayList<>();
        leituras.add(30.0); leituras.add(35.0); leituras.add(42.0);
        leituras.add(48.0); leituras.add(55.0); leituras.add(61.0);
        EstrategiaPrevisao media = new PrevisaoMediaMovel(3);
        System.out.println(media.getNome() + ": " + media.prever(leituras));
    }
}
```

## Passo a passo no Git

### Uma vez só
```bash
git clone https://github.com/GabrielAugustoT800/territory-predictive-insights.git
cd territory-predictive-insights
git checkout feature/sensores        # troque pela SUA branch
```

### No dia a dia
```bash
git pull                              # pega o que já está na sua branch
# ... programa e testa ...
javac -encoding UTF-8 *.java          # SEMPRE compilar antes do commit
git add .
git commit -m "feat(sensores): simula leitura do SensorChuva"
git push
```

Faça commits pequenos e frequentes, com mensagens claras. O professor verifica commits de **todos** os integrantes.

### Quando a sua parte estiver pronta
1. No GitHub, abra um **Pull Request** da sua branch para a `main`.
2. Peça para outro integrante revisar e aprovar.
3. Ordem sugerida de merge: **A e B primeiro** (não dependem uma da outra), **C por último** (integra tudo e testa o menu completo).

### Se alguém precisar mudar um contrato
Avise o grupo, faça a mudança **na `main`** e cada um roda na sua branch:
```bash
git pull origin main
```

## Antes de entregar
- [ ] `javac -encoding UTF-8 *.java` compila sem erro numa pasta limpa
- [ ] `java SistemaMonitoramento` roda seguindo o README
- [ ] README sem `TODO` (público, decisões, reflexão, uso de IA, divisão, integrantes)
- [ ] Nenhum dado pessoal real (nomes, CPF, endereços)
- [ ] ZIP com todos os arquivos + nome e RM de todos + link do GitHub

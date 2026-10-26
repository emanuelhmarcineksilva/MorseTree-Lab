# Morse Tree Lab

Tradutor interativo de Código Morse com árvore binária de decisão e reprodução de som.

## Estrutura do projeto

```
src/morsetree/
  Main.java                 # Ponto de entrada da aplicação
  MorseTree.java            # Árvore binária para decodificar Morse
  MorseEncoder.java         # Codifica texto para Morse
  MorsePlayer.java          # Reproduz o som do Código Morse
  RegrasMorse.java          # Tabela de códigos Morse (letras, números, símbolos)
  TreeVisualizer.java       # Imprime a árvore no console
  ui/
    MorseTreeUI.java        # Interface gráfica (Swing)
```

## Como executar

### Pré-requisitos

- Java Development Kit (JDK) 8 ou superior instalado.

### Compilar

No terminal, dentro da pasta `MorseTree-Lab`:

```bash
javac -d out src/morsetree/*.java src/morsetree/ui/*.java
```

### Executar

```bash
java -cp out morsetree.Main
```

Ou, se estiver usando a IntelliJ IDEA, basta abrir o projeto e executar a classe `morsetree.Main`.

### Testar o player de som isoladamente

```bash
java -cp out morsetree.MorsePlayer
```

## Como usar a interface

1. **Texto → Morse**: digite uma mensagem e clique em **TRADUZIR**. O código Morse aparece na saída.
2. **Morse → Texto**: selecione o modo "Morse → Texto", digite o código (pontos e traços separados por espaços) e clique em **TRADUZIR**.
3. **Reproduzir som**: após traduzir, clique no botão **▶ Reproduzir** para ouvir o código Morse da saída.
   - Ponto (.) = bip curto
   - Traço (-) = bip longo
4. **Limpar**: limpa os campos de entrada e saída.

### Regras de separação no modo Morse → Texto

- Letras: separadas por **1 espaço**
- Palavras: separadas por **2 ou mais espaços**
- Exemplo: `... --- ...` = `SOS`

## Funcionalidade de som

A classe `MorsePlayer` gera tons senoidenciais através da API `javax.sound.sampled`:
- Frequência: 800 Hz
- Ponto: 200 ms
- Traço: 600 ms

A reprodução roda em uma thread separada para não travar a interface.

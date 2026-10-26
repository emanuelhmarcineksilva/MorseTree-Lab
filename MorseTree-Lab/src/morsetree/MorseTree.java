package morsetree;

import java.util.Map;

public class MorseTree {
    public static final class No {
        private char letra;
        private No esquerda;
        private No direita;

        public char getLetra() {
            return letra;
        }

        public No getEsquerda() {
            return esquerda;
        }

        public No getDireita() {
            return direita;
        }
    }

    private final No raiz;

    public MorseTree() {
        // Cria a raiz inicialmente vazia (sem caractere).
        raiz = new No();
        // Insere cada caractere da tabela Morse na árvore, seguindo seu código.
        for (Map.Entry<Character, String> entrada : RegrasMorse.obterTabela().entrySet()) {
            inserir(entrada.getKey(), entrada.getValue());
        }
    }

    /**
     * Insere um caractere na árvore seguindo seu código Morse.
     * O ponto ('.') leva ao filho esquerdo e o traço ('-') ao filho direito.
     */
    public final void inserir(char letra, String codigoMorse) {
        if (codigoMorse == null || codigoMorse.isEmpty()) {
            throw new IllegalArgumentException("o codigo Morse nao pode ser vazio.");
        }

        No atual = raiz;
        for (int i = 0; i < codigoMorse.length(); i++) {
            char sinal = codigoMorse.charAt(i);
            if (sinal == '.') {
                // Ponto: segue para o filho esquerdo, criando-o se necessário.
                if (atual.esquerda == null) {
                    atual.esquerda = new No();
                }
                atual = atual.esquerda;
            } else if (sinal == '-') {
                // Traço: segue para o filho direito, criando-o se necessário.
                if (atual.direita == null) {
                    atual.direita = new No();
                }
                atual = atual.direita;
            } else {
                throw new IllegalArgumentException("o codigo deve conter somente pontos e tracos.");
            }
        }

        // Ao final do caminho Morse, armazena a letra no nó alcançado.
        if (atual.letra != '\0' && atual.letra != letra) {
            throw new IllegalArgumentException("o codigo Morse ja pertence a outra letra.");
        }
        atual.letra = Character.toUpperCase(letra);
    }

    /**
     * Decodifica uma sequência Morse em texto, percorrendo a árvore binária.
     * Letras são separadas por 1 espaço e palavras por 2 ou mais espaços.
     */
    public String decifrar(String codigoMorse) {
        if (codigoMorse == null) {
            throw new IllegalArgumentException("a mensagem nao pode ser nula.");
        }

        String mensagem = codigoMorse.trim();
        if (mensagem.isEmpty()) {
            return "";
        }

        // Separa palavras por 2 ou mais espaços.
        String[] palavras = mensagem.split("\\s{2,}");
        StringBuilder resultado = new StringBuilder();
        for (int indicePalavra = 0; indicePalavra < palavras.length; indicePalavra++) {
            if (indicePalavra > 0) {
                resultado.append(' ');
            }

            // Separa códigos de letras dentro de uma palavra por 1 ou mais espaços.
            String[] codigos = palavras[indicePalavra].trim().split("\\s+");
            for (String codigo : codigos) {
                No atual = raiz;
                // Percorre a árvore seguindo cada ponto/traço do código Morse.
                for (int i = 0; i < codigo.length(); i++) {
                    char sinal = codigo.charAt(i);
                    if (sinal == '.') {
                        // Ponto: desce para o filho esquerdo.
                        atual = atual.esquerda;
                    } else if (sinal == '-') {
                        // Traço: desce para o filho direito.
                        atual = atual.direita;
                    } else {
                        throw new IllegalArgumentException(
                                "o codigo '" + codigo + "' contem um caractere diferente de ponto ou traco.");
                    }

                    // Se o caminho não existe na árvore, o código é inválido.
                    if (atual == null) {
                        throw new IllegalArgumentException("o codigo '" + codigo + "' nao existe na arvore.");
                    }
                }

                // Recupera o caractere armazenado no nó final do caminho.
                if (atual.letra == '\0') {
                    throw new IllegalArgumentException("o codigo '" + codigo + "' nao representa uma letra ou numero.");
                }
                resultado.append(atual.letra);
            }
        }
        return resultado.toString();
    }

    public No obterRaiz() {
        return raiz;
    }
}

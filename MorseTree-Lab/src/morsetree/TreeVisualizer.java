package morsetree;

public final class TreeVisualizer {
    private TreeVisualizer() {
    }

    // Imprime a árvore Morse construída no console, mostrando nós e relações hierárquicas.
    public static void imprimir(MorseTree arvore) {
        System.out.println("Arvore Morse (. = esquerda, - = direita):");
        // Obtém a raiz da árvore construída pelo programa.
        MorseTree.No raiz = arvore.obterRaiz();
        System.out.println("INICIO: " + obterConteudo(raiz));
        // Imprime recursivamente os filhos esquerdo e direito.
        imprimirFilho(raiz.getEsquerda(), "", ". esquerda", raiz.getDireita() == null);
        imprimirFilho(raiz.getDireita(), "", "- direita", true);
    }

    // Imprime recursivamente um nó e seus filhos, indicando se é filho esquerdo ou direito.
    private static void imprimirFilho(MorseTree.No no, String prefixo, String rotulo, boolean ultimo) {
        if (no == null) {
            return;
        }

        System.out.println(prefixo + (ultimo ? "\\-- " : "|-- ") + rotulo + ": " + obterConteudo(no));
        String prefixoFilhos = prefixo + (ultimo ? "    " : "|   ");
        imprimirFilho(no.getEsquerda(), prefixoFilhos, ". esquerda", no.getDireita() == null);
        imprimirFilho(no.getDireita(), prefixoFilhos, "- direita", true);
    }

    // Devolve o conteúdo do nó (letra ou "(sem caractere)" se ainda não houver letra).
    private static String obterConteudo(MorseTree.No no) {
        return no.getLetra() == '\0' ? "(sem caractere)" : String.valueOf(no.getLetra());
    }
}

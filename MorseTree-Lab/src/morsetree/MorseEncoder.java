package morsetree;

import java.text.Normalizer;
import java.util.Locale;
import java.util.Map;

public final class MorseEncoder {
    private MorseEncoder() {
    }

    /**
     * Codifica um texto comum em Código Morse.
     * Funcionalidade extra (bidirecional): texto -> Morse.
     * Letras são separadas por 1 espaço e palavras por 2 espaços.
     */
    public static String codificar(String texto) {
        if (texto == null) {
            throw new IllegalArgumentException("o texto nao pode ser nulo.");
        }

        // Normaliza: remove acentos e converte para maiúsculas.
        String normalizado = Normalizer.normalize(texto.toUpperCase(Locale.ROOT), Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");
        Map<Character, String> tabela = RegrasMorse.obterTabela();
        StringBuilder resultado = new StringBuilder();
        boolean separadorDePalavra = false;

        for (int i = 0; i < normalizado.length(); i++) {
            char caractere = normalizado.charAt(i);
            if (Character.isWhitespace(caractere)) {
                // Espaço em branco indica separação de palavra (serão 2 espaços entre códigos).
                if (!resultado.isEmpty()) {
                    separadorDePalavra = true;
                }
                continue;
            }

            String codigo = tabela.get(caractere);
            if (codigo == null) {
                throw new IllegalArgumentException(
                        "o caractere '" + caractere + "' nao possui representacao Morse.");
            }

            // Adiciona separador: 2 espaços entre palavras, 1 espaço entre letras.
            if (!resultado.isEmpty()) {
                resultado.append(separadorDePalavra ? "  " : " ");
            }
            resultado.append(codigo);
            separadorDePalavra = false;
        }

        return resultado.toString();
    }
}

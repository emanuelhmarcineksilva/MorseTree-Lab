package morsetree;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public final class RegrasMorse {
    // Tabela de códigos Morse (caractere -> sequência de pontos e traços), imutável.
    private static final Map<Character, String> CODIGOS = criarTabela();

    private RegrasMorse() {
    }

    // Devolve uma cópia não modificável da tabela de códigos Morse.
    public static Map<Character, String> obterTabela() {
        return Collections.unmodifiableMap(CODIGOS);
    }

    // Constrói a tabela completa com letras, números e símbolos do Código Morse.
    private static Map<Character, String> criarTabela() {
        Map<Character, String> codigos = new LinkedHashMap<>();
        codigos.put('A', ".-");
        codigos.put('B', "-...");
        codigos.put('C', "-.-.");
        codigos.put('D', "-..");
        codigos.put('E', ".");
        codigos.put('F', "..-.");
        codigos.put('G', "--.");
        codigos.put('H', "....");
        codigos.put('I', "..");
        codigos.put('J', ".---");
        codigos.put('K', "-.-");
        codigos.put('L', ".-..");
        codigos.put('M', "--");
        codigos.put('N', "-.");
        codigos.put('O', "---");
        codigos.put('P', ".--.");
        codigos.put('Q', "--.-");
        codigos.put('R', ".-.");
        codigos.put('S', "...");
        codigos.put('T', "-");
        codigos.put('U', "..-");
        codigos.put('V', "...-");
        codigos.put('W', ".--");
        codigos.put('X', "-..-");
        codigos.put('Y', "-.--");
        codigos.put('Z', "--..");
        codigos.put('0', "-----");
        codigos.put('1', ".----");
        codigos.put('2', "..---");
        codigos.put('3', "...--");
        codigos.put('4', "....-");
        codigos.put('5', ".....");
        codigos.put('6', "-....");
        codigos.put('7', "--...");
        codigos.put('8', "---..");
        codigos.put('9', "----.");
        codigos.put('.', ".-.-.-");
        codigos.put(',', "--..--");
        codigos.put('?', "..--..");
        codigos.put('!', "-.-.--");
        codigos.put('\'', ".----.");
        codigos.put('"', ".-..-.");
        codigos.put('(', "-.--.");
        codigos.put(')', "-.--.-");
        codigos.put('&', ".-...");
        codigos.put(':', "---...");
        codigos.put(';', "-.-.-.");
        codigos.put('=', "-...-");
        codigos.put('+', ".-.-.");
        codigos.put('-', "-....-");
        codigos.put('_', "..--.-");
        codigos.put('$', "...-..-");
        codigos.put('@', ".--.-.");
        return codigos;
    }
}

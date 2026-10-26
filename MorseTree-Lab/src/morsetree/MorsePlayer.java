package morsetree;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;

/**
 * Reproduz sons de bip para representar códigos Morse.
 * Ponto (.) = bip curto (200 ms), traço (-) = bip longo (600 ms).
 */
public final class MorsePlayer {

    private static final int SAMPLE_RATE = 8000; // Taxa de amostragem em Hz
    private static final int FREQUENCIA_PADRAO = 800; // Frequência padrão para código Morse
    private static final int DURACAO_PONTO = 200; // milissegundos
    private static final int DURACAO_TRACO = 600; // milissegundos

    private MorsePlayer() {
    }

    /**
     * Toca um bipe com frequência e duração específicas.
     *
     * @param frequency Frequência do som em Hz (ex: 800 para um som limpo)
     * @param duration  Duração do som em milissegundos
     */
    public static void playBeep(int frequency, int duration) {
        try {
            byte[] buffer = new byte[duration * SAMPLE_RATE / 1000];

            // Gera a onda senoidal pura para o bipe
            for (int i = 0; i < buffer.length; i++) {
                double angle = i / (SAMPLE_RATE / (double) frequency) * 2.0 * Math.PI;
                buffer[i] = (byte) (Math.sin(angle) * 127.0);
            }

            // Configura o formato do áudio (8kHz, 8-bit, Mono, Signed, Big Endian)
            AudioFormat format = new AudioFormat(SAMPLE_RATE, 8, 1, true, true);
            SourceDataLine line = AudioSystem.getSourceDataLine(format);

            line.open(format);
            line.start();
            line.write(buffer, 0, buffer.length);
            line.drain();
            line.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Reproduz uma sequência Morse como som, executada em segundo plano para não travar a interface.
     * Letras são separadas por 1 espaço e palavras por 2 ou mais espaços.
     *
     * @param codigoMorse String no formato Morse (ex: ".- -... -.-.")
     */
    public static void playMorse(String codigoMorse) {
        if (codigoMorse == null || codigoMorse.trim().isEmpty()) {
            return;
        }

        new Thread(() -> {
            try {
                tocar(codigoMorse, FREQUENCIA_PADRAO);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    private static void tocar(String codigoMorse, int frequency) throws InterruptedException {
        String[] palavras = codigoMorse.trim().split("\\s{2,}");
        for (int p = 0; p < palavras.length; p++) {
            if (p > 0) {
                Thread.sleep(700); // Pausa entre palavras
            }
            String[] codigos = palavras[p].trim().split("\\s+");
            for (int c = 0; c < codigos.length; c++) {
                if (c > 0) {
                    Thread.sleep(300); // Pausa entre letras
                }
                for (int i = 0; i < codigos[c].length(); i++) {
                    char sinal = codigos[c].charAt(i);
                    if (sinal == '.') {
                        playBeep(frequency, DURACAO_PONTO);
                    } else if (sinal == '-') {
                        playBeep(frequency, DURACAO_TRACO);
                    }
                    if (i < codigos[c].length() - 1) {
                        Thread.sleep(100); // Pausa entre sinais de uma mesma letra
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Tocando PONTO (.)");
        playBeep(FREQUENCIA_PADRAO, DURACAO_PONTO);

        Thread.sleep(200);

        System.out.println("Tocando TRAÇO (-)");
        playBeep(FREQUENCIA_PADRAO, DURACAO_TRACO);

        Thread.sleep(400);

        System.out.println("Tocando 'SOS' (... --- ...)");
        playMorse("... --- ...");
    }
}

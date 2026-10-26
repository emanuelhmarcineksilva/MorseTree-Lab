import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;

public class MorsePlayer {

    private static final int SAMPLE_RATE = 8000; // Taxa de amostragem em Hz

    /**
     * Toca um bipe com frequência e duração específicas.
     * @param frequency Frequência do som em Hz (ex: 800 para um som limpo)
     * @param duration Duração do som em milissegundos
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

    public static void main(String[] args) throws InterruptedException {
        int freq = 800; // Frequência padrão para código Morse

        System.out.println("Tocando PONTO (.)");
        playBeep(freq, 200); // Ponto: curto (200ms)
        
        Thread.sleep(200); // Pausa entre elementos
        
        System.out.println("Tocando TRAÇO (-)");
        playBeep(freq, 600); // Traço: longo (600ms, geralmente 3x o ponto)
    }
}

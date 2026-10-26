package morsetree.ui;

import morsetree.MorseEncoder;
import morsetree.MorsePlayer;
import morsetree.MorseTree;
import morsetree.RegrasMorse;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.util.Map;

public final class MorseTreeUI {
    private static final Color BACKGROUND = new Color(13, 16, 25);
    private static final Color PANEL = new Color(25, 29, 42);
    private static final Color PANEL_LIGHT = new Color(34, 40, 55);
    private static final Color TEXT = new Color(235, 239, 247);
    private static final Color MUTED = new Color(146, 156, 178);
    private static final Color GREEN = new Color(76, 238, 137);
    private static final Color GREEN_DARK = new Color(27, 73, 58);
    private static final Color PURPLE = new Color(190, 137, 255);
    private static final Color ERROR = new Color(255, 125, 137);

    // Árvore binária de Morse construída e usada na decodificação.
    private final MorseTree arvore = new MorseTree();
    private final JTextArea entrada = criarAreaTexto();
    private final JTextArea saida = criarAreaTexto();
    private final JLabel status = new JLabel("Pronto para traduzir");
    private final JLabel modoLabel = new JLabel("TEXTO PARA MORSE");
    private JLabel inputHint;
    private boolean textoParaMorse = true;
    private JButton botaoTexto;
    private JButton botaoMorse;
    private JButton botaoReproduzir;

    private MorseTreeUI() {
    }

    public static void iniciar() {
        SwingUtilities.invokeLater(() -> {
            configurarTema();
            new MorseTreeUI().mostrar();
        });
    }

    private static void configurarTema() {
        UIManager.put("TabbedPane.background", PANEL);
        UIManager.put("TabbedPane.foreground", TEXT);
        UIManager.put("TabbedPane.selected", PANEL_LIGHT);
        UIManager.put("TabbedPane.contentAreaColor", BACKGROUND);
        UIManager.put("ScrollPane.background", PANEL);
        UIManager.put("ScrollBar.thumb", PANEL_LIGHT);
        UIManager.put("ScrollBar.track", BACKGROUND);
    }

    private void mostrar() {
        JFrame janela = new JFrame("Morse Studio");
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setMinimumSize(new Dimension(980, 700));
        janela.setSize(1240, 820);
        janela.setLocationRelativeTo(null);

        JPanel raiz = new JPanel(new BorderLayout(0, 18));
        raiz.setBackground(BACKGROUND);
        raiz.setBorder(new EmptyBorder(24, 28, 24, 28));
        raiz.add(criarCabecalho(), BorderLayout.NORTH);
        raiz.add(criarAbas(), BorderLayout.CENTER);
        janela.setContentPane(raiz);
        janela.setVisible(true);
    }

    private JPanel criarCabecalho() {
        JPanel cabecalho = new JPanel(new BorderLayout());
        cabecalho.setOpaque(false);

        JPanel identidade = new JPanel();
        identidade.setOpaque(false);
        identidade.setLayout(new BoxLayout(identidade, BoxLayout.Y_AXIS));
        JLabel titulo = new JLabel("MORSE STUDIO");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 25));
        titulo.setForeground(TEXT);
        JLabel subtitulo = new JLabel("Tradutor interativo · árvore binária de decisão");
        subtitulo.setFont(new Font("SansSerif", Font.PLAIN, 13));
        subtitulo.setForeground(MUTED);
        identidade.add(titulo);
        identidade.add(Box.createVerticalStrut(5));
        identidade.add(subtitulo);

        JLabel selo = new JLabel("●  ÁRVORE ATIVA");
        selo.setForeground(GREEN);
        selo.setFont(new Font("SansSerif", Font.BOLD, 11));
        selo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(44, 96, 72), 1, true),
                new EmptyBorder(9, 12, 9, 12)));
        cabecalho.add(identidade, BorderLayout.WEST);
        cabecalho.add(selo, BorderLayout.EAST);
        return cabecalho;
    }

    private JTabbedPane criarAbas() {
        JTabbedPane abas = new JTabbedPane();
        abas.setFont(new Font("SansSerif", Font.BOLD, 13));
        abas.addTab("  Tradutor  ", criarPainelTradutor());
        abas.addTab("  Árvore Morse  ", criarPainelArvore());
        abas.addTab("  Referência  ", criarPainelReferencia());
        return abas;
    }

    private JPanel criarPainelTradutor() {
        JPanel painel = new JPanel(new BorderLayout(18, 0));
        painel.setBackground(BACKGROUND);
        painel.setBorder(new EmptyBorder(20, 4, 4, 4));

        JPanel principal = new JPanel();
        principal.setOpaque(false);
        principal.setLayout(new BoxLayout(principal, BoxLayout.Y_AXIS));
        principal.add(criarSeletorModo());
        principal.add(Box.createVerticalStrut(14));
        principal.add(criarCartaoEntrada());
        principal.add(Box.createVerticalStrut(14));
        principal.add(criarCartaoSaida());
        principal.add(Box.createVerticalStrut(12));
        principal.add(criarBarraAcoes());

        painel.add(principal, BorderLayout.CENTER);
        painel.add(criarPainelLateral(), BorderLayout.EAST);
        atualizarModo();
        return painel;
    }

    private JPanel criarSeletorModo() {
        JPanel linha = new JPanel(new BorderLayout());
        linha.setOpaque(false);
        JPanel botoes = new JPanel(new GridLayout(1, 2, 6, 0));
        botoes.setOpaque(false);
        botaoTexto = criarBotao("Texto  →  Morse", true);
        botaoMorse = criarBotao("Morse  →  Texto", false);
        botaoTexto.addActionListener(evento -> {
            textoParaMorse = true;
            atualizarModo();
        });
        botaoMorse.addActionListener(evento -> {
            textoParaMorse = false;
            atualizarModo();
        });
        botoes.add(botaoTexto);
        botoes.add(botaoMorse);

        modoLabel.setFont(new Font("SansSerif", Font.BOLD, 11));
        modoLabel.setForeground(MUTED);
        linha.add(botoes, BorderLayout.WEST);
        linha.add(modoLabel, BorderLayout.EAST);
        return linha;
    }

    private JPanel criarCartaoEntrada() {
        JPanel cartao = criarCartao();
        cartao.setLayout(new BorderLayout(0, 10));
        cartao.setPreferredSize(new Dimension(600, 205));
        cartao.setMaximumSize(new Dimension(Integer.MAX_VALUE, 205));
        JPanel rotulo = criarRotuloSecao("SUA MENSAGEM", "");
        inputHint = new JLabel("Escreva uma mensagem em português");
        inputHint.setForeground(MUTED);
        inputHint.setFont(new Font("SansSerif", Font.PLAIN, 11));
        rotulo.add(inputHint, BorderLayout.EAST);
        cartao.add(rotulo, BorderLayout.NORTH);
        JScrollPane rolagem = criarRolagem(entrada);
        cartao.add(rolagem, BorderLayout.CENTER);
        return cartao;
    }

    private JPanel criarCartaoSaida() {
        JPanel cartao = criarCartao();
        cartao.setLayout(new BorderLayout(0, 10));
        cartao.setPreferredSize(new Dimension(600, 190));
        cartao.setMaximumSize(new Dimension(Integer.MAX_VALUE, 190));
        cartao.add(criarRotuloSecao("RESULTADO", "A tradução aparece aqui"), BorderLayout.NORTH);
        saida.setEditable(false);
        saida.setForeground(GREEN);
        saida.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 17));
        cartao.add(criarRolagem(saida), BorderLayout.CENTER);
        return cartao;
    }

    private JPanel criarBarraAcoes() {
        JPanel barra = new JPanel(new BorderLayout());
        barra.setOpaque(false);
        JPanel botoes = new JPanel();
        botoes.setOpaque(false);
        JButton traduzir = criarBotao("  TRADUZIR  ", true);
        traduzir.addActionListener(evento -> traduzirEntrada());
        JButton limpar = criarBotao("Limpar", false);
        limpar.addActionListener(evento -> {
            entrada.setText("");
            entrada.setForeground(TEXT);
            saida.setText("");
            definirStatus("Campos limpos", MUTED);
        });
        botaoReproduzir = criarBotao("▶  Reproduzir", false);
        botaoReproduzir.addActionListener(evento -> reproduzirSaida());
        botoes.add(traduzir);
        botoes.add(Box.createHorizontalStrut(8));
        botoes.add(limpar);
        botoes.add(Box.createHorizontalStrut(8));
        botoes.add(botaoReproduzir);
        status.setFont(new Font("SansSerif", Font.PLAIN, 12));
        status.setForeground(MUTED);
        barra.add(botoes, BorderLayout.WEST);
        barra.add(status, BorderLayout.EAST);
        return barra;
    }

    private JPanel criarPainelLateral() {
        JPanel lateral = new JPanel();
        lateral.setOpaque(false);
        lateral.setPreferredSize(new Dimension(240, 0));
        lateral.setLayout(new BoxLayout(lateral, BoxLayout.Y_AXIS));
        lateral.add(criarCartaoInfo("COMO SEPARAR", new String[]{
                "Uma letra: 1 espaço",
                "Uma palavra: 2 espaços",
                "Acentos são normalizados",
                "Ex.: OLÁ → OLA"
        }));
        lateral.add(Box.createVerticalStrut(14));
        lateral.add(criarCartaoInfo("ESTRUTURA", new String[]{
                "Ponto  → filho esquerdo",
                "Traço   → filho direito",
                "Letras e números",
                "53 símbolos disponíveis"
        }));
        lateral.add(Box.createVerticalGlue());
        return lateral;
    }

    private JPanel criarCartaoInfo(String titulo, String[] linhas) {
        JPanel cartao = criarCartao();
        cartao.setLayout(new BoxLayout(cartao, BoxLayout.Y_AXIS));
        cartao.setMaximumSize(new Dimension(Integer.MAX_VALUE, 190));
        cartao.add(criarRotuloSecao(titulo, ""));
        cartao.add(Box.createVerticalStrut(14));
        for (String linha : linhas) {
            JLabel texto = new JLabel(linha);
            texto.setFont(new Font("SansSerif", Font.PLAIN, 12));
            texto.setForeground(TEXT);
            texto.setAlignmentX(JLabel.LEFT_ALIGNMENT);
            cartao.add(texto);
            cartao.add(Box.createVerticalStrut(9));
        }
        return cartao;
    }

    private JPanel criarPainelArvore() {
        JPanel painel = new JPanel(new BorderLayout(0, 12));
        painel.setBackground(BACKGROUND);
        painel.setBorder(new EmptyBorder(18, 4, 4, 4));
        JPanel legenda = new JPanel(new BorderLayout());
        legenda.setOpaque(false);
        JLabel instrucao = new JLabel("Cada caminho forma um código Morse.");
        instrucao.setForeground(MUTED);
        instrucao.setFont(new Font("SansSerif", Font.PLAIN, 13));
        JPanel chaves = new JPanel();
        chaves.setOpaque(false);
        JLabel ponto = new JLabel("· esquerda");
        ponto.setForeground(GREEN);
        JLabel traco = new JLabel("— direita");
        traco.setForeground(PURPLE);
        chaves.add(ponto);
        chaves.add(Box.createHorizontalStrut(12));
        chaves.add(traco);
        legenda.add(instrucao, BorderLayout.WEST);
        legenda.add(chaves, BorderLayout.EAST);
        painel.add(legenda, BorderLayout.NORTH);

        // Desenha a árvore construída, obtendo sua raiz diretamente da árvore Morse.
        TreeCanvas desenho = new TreeCanvas(arvore.obterRaiz());
        JScrollPane rolagem = new JScrollPane(desenho);
        rolagem.setBorder(BorderFactory.createLineBorder(new Color(49, 56, 74)));
        rolagem.getViewport().setBackground(PANEL);
        painel.add(rolagem, BorderLayout.CENTER);
        return painel;
    }

    private JPanel criarPainelReferencia() {
        JPanel painel = new JPanel(new BorderLayout(0, 12));
        painel.setBackground(BACKGROUND);
        painel.setBorder(new EmptyBorder(18, 4, 4, 4));
        JLabel instrucao = new JLabel("Alfabeto, números e sinais suportados pela árvore.");
        instrucao.setForeground(MUTED);
        instrucao.setFont(new Font("SansSerif", Font.PLAIN, 13));
        painel.add(instrucao, BorderLayout.NORTH);

        JPanel grade = new JPanel(new GridLayout(0, 4, 10, 8));
        grade.setBackground(BACKGROUND);
        for (Map.Entry<Character, String> codigo : RegrasMorse.obterTabela().entrySet()) {
            JPanel item = new JPanel(new BorderLayout());
            item.setBackground(PANEL);
            item.setBorder(new EmptyBorder(10, 14, 10, 14));
            JLabel simbolo = new JLabel(String.valueOf(codigo.getKey()));
            simbolo.setForeground(GREEN);
            simbolo.setFont(new Font("SansSerif", Font.BOLD, 15));
            JLabel morse = new JLabel(codigo.getValue());
            morse.setForeground(TEXT);
            morse.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
            item.add(simbolo, BorderLayout.WEST);
            item.add(morse, BorderLayout.EAST);
            grade.add(item);
        }
        JScrollPane rolagem = new JScrollPane(grade);
        rolagem.setBorder(BorderFactory.createEmptyBorder());
        rolagem.getViewport().setBackground(BACKGROUND);
        painel.add(rolagem, BorderLayout.CENTER);
        return painel;
    }

    private void traduzirEntrada() {
        String texto = entrada.getText().trim();
        if (texto.isEmpty()) {
            definirStatus("Digite uma mensagem antes de traduzir", ERROR);
            return;
        }

        try {
            // No modo Morse -> Texto, usa a árvore binária para decodificar.
            // No modo Texto -> Morse, usa o codificador (funcionalidade extra).
            String traducao = textoParaMorse ? MorseEncoder.codificar(texto) : arvore.decifrar(texto);
            saida.setText(traducao);
            definirStatus("Tradução concluída", GREEN);
        } catch (IllegalArgumentException erro) {
            // Trata entrada inválida sem produzir resultado incorreto.
            saida.setText(textoParaMorse ? "Caractere sem representação Morse." : "Sequência inválida!");
            definirStatus(erro.getMessage(), ERROR);
        }
    }

    private void reproduzirSaida() {
        String codigo = saida.getText().trim();
        if (codigo.isEmpty()) {
            definirStatus("Não há resultado para reproduzir", ERROR);
            return;
        }
        try {
            MorsePlayer.playMorse(codigo);
            definirStatus("Reproduzindo som Morse...", GREEN);
        } catch (Exception erro) {
            definirStatus("Erro ao reproduzir som: " + erro.getMessage(), ERROR);
        }
    }

    private void atualizarModo() {
        modoLabel.setText(textoParaMorse ? "TEXTO PARA MORSE" : "MORSE PARA TEXTO");
        botaoTexto.setBackground(textoParaMorse ? GREEN : PANEL_LIGHT);
        botaoTexto.setForeground(textoParaMorse ? BACKGROUND : TEXT);
        botaoMorse.setBackground(textoParaMorse ? PANEL_LIGHT : GREEN);
        botaoMorse.setForeground(textoParaMorse ? TEXT : BACKGROUND);
        entrada.setText("");
        saida.setText("");
        entrada.setForeground(TEXT);
        inputHint.setText(textoParaMorse
                ? "Escreva uma mensagem em português"
                : "Separe letras com 1 espaço e palavras com 2 espaços");
        definirStatus("Pronto para traduzir", MUTED);
    }

    private void definirStatus(String mensagem, Color cor) {
        status.setText(mensagem);
        status.setForeground(cor);
    }

    private static JPanel criarCartao() {
        JPanel cartao = new JPanel();
        cartao.setBackground(PANEL);
        cartao.setBorder(new EmptyBorder(16, 18, 16, 18));
        return cartao;
    }

    private static JPanel criarRotuloSecao(String titulo, String descricao) {
        JPanel rotulo = new JPanel(new BorderLayout());
        rotulo.setOpaque(false);
        JLabel nome = new JLabel(titulo);
        nome.setForeground(TEXT);
        nome.setFont(new Font("SansSerif", Font.BOLD, 11));
        rotulo.add(nome, BorderLayout.WEST);
        if (!descricao.isEmpty()) {
            JLabel detalhe = new JLabel(descricao);
            detalhe.setForeground(MUTED);
            detalhe.setFont(new Font("SansSerif", Font.PLAIN, 11));
            rotulo.add(detalhe, BorderLayout.EAST);
        }
        return rotulo;
    }

    private static JButton criarBotao(String texto, boolean destaque) {
        JButton botao = new JButton(texto);
        botao.setFont(new Font("SansSerif", Font.BOLD, 12));
        botao.setForeground(destaque ? BACKGROUND : TEXT);
        botao.setBackground(destaque ? GREEN : PANEL_LIGHT);
        botao.setFocusPainted(false);
        botao.setBorder(new EmptyBorder(11, 16, 11, 16));
        botao.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
        return botao;
    }

    private static JTextArea criarAreaTexto() {
        JTextArea area = new JTextArea();
        area.setBackground(PANEL_LIGHT);
        area.setForeground(TEXT);
        area.setCaretColor(GREEN);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setFont(new Font("SansSerif", Font.PLAIN, 16));
        area.setBorder(new EmptyBorder(12, 12, 12, 12));
        return area;
    }

    private static JScrollPane criarRolagem(JTextArea area) {
        JScrollPane rolagem = new JScrollPane(area);
        rolagem.setBorder(BorderFactory.createLineBorder(new Color(49, 56, 74)));
        rolagem.getViewport().setBackground(PANEL_LIGHT);
        return rolagem;
    }

    private static final class TreeCanvas extends JPanel {
        private static final long serialVersionUID = 1L;
        private static final int RADIUS = 17;
        private static final int TOP = 58;
        private static final int LEVEL_GAP = 95;
        // Raiz da árvore Morse a ser desenhada.
        private final transient MorseTree.No raiz;
        private final int profundidade;

        private TreeCanvas(MorseTree.No raiz) {
            this.raiz = raiz;
            // Calcula a altura da árvore para dimensionar o canvas.
            profundidade = altura(raiz);
            int largura = Math.max(1080, 36 * (1 << profundidade));
            int altura = TOP * 2 + profundidade * LEVEL_GAP;
            setPreferredSize(new Dimension(largura, altura));
            setBackground(PANEL);
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            Graphics2D g = (Graphics2D) graphics.create();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            // Desenha ramos e nós a partir da raiz, centralizados no topo.
            desenharRamos(g, raiz, getWidth() / 2.0, TOP, getWidth() / 4.0, 0);
            desenharNos(g, raiz, getWidth() / 2.0, TOP, getWidth() / 4.0, 0);
            g.dispose();
        }

        // Desenha recursivamente os ramos (linhas) entre nós de níveis consecutivos.
        private void desenharRamos(Graphics2D g, MorseTree.No no, double x, double y, double deslocamento, int nivel) {
            if (no == null || nivel >= profundidade) {
                return;
            }
            desenharRamo(g, no.getEsquerda(), x, y, x - deslocamento, y + LEVEL_GAP, true);
            desenharRamo(g, no.getDireita(), x, y, x + deslocamento, y + LEVEL_GAP, false);
            double proximoDeslocamento = deslocamento / 2.0;
            desenharRamos(g, no.getEsquerda(), x - deslocamento, y + LEVEL_GAP, proximoDeslocamento, nivel + 1);
            desenharRamos(g, no.getDireita(), x + deslocamento, y + LEVEL_GAP, proximoDeslocamento, nivel + 1);
        }

        // Desenha um único ramo entre pai e filho. Verde = filho esquerdo (ponto), roxo = filho direito (traço).
        private void desenharRamo(Graphics2D g, MorseTree.No filho, double x1, double y1, double x2, double y2,
                                  boolean ponto) {
            if (filho == null) {
                return;
            }
            g.setColor(ponto ? GREEN : PURPLE);
            g.setStroke(ponto
                    ? new BasicStroke(1.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1f, new float[]{2f, 5f}, 0f)
                    : new BasicStroke(1.7f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1f, new float[]{7f, 5f}, 0f));
            g.drawLine((int) x1, (int) (y1 + RADIUS), (int) x2, (int) (y2 - RADIUS));
        }

        // Desenha recursivamente os nós (ovais) com suas letras, se houver.
        private void desenharNos(Graphics2D g, MorseTree.No no, double x, double y, double deslocamento, int nivel) {
            if (no == null) {
                return;
            }
            char letra = no.getLetra();
            // Nó sem letra é desenhado em cinza; nó com letra em verde escuro.
            g.setColor(letra == '\0' ? PANEL_LIGHT : GREEN_DARK);
            g.fillOval((int) x - RADIUS, (int) y - RADIUS, RADIUS * 2, RADIUS * 2);
            g.setColor(letra == '\0' ? MUTED : GREEN);
            g.setStroke(new BasicStroke(1.5f));
            g.drawOval((int) x - RADIUS, (int) y - RADIUS, RADIUS * 2, RADIUS * 2);
            if (letra != '\0') {
                g.setFont(new Font("SansSerif", Font.BOLD, 12));
                g.setColor(TEXT);
                String valor = String.valueOf(letra);
                int larguraTexto = g.getFontMetrics().stringWidth(valor);
                g.drawString(valor, (int) x - larguraTexto / 2, (int) y + 4);
            }
            if (nivel < profundidade) {
                double proximoDeslocamento = deslocamento / 2.0;
                desenharNos(g, no.getEsquerda(), x - deslocamento, y + LEVEL_GAP, proximoDeslocamento, nivel + 1);
                desenharNos(g, no.getDireita(), x + deslocamento, y + LEVEL_GAP, proximoDeslocamento, nivel + 1);
            }
        }

        // Calcula a altura da árvore (número de níveis) a partir de um nó.
        private static int altura(MorseTree.No no) {
            if (no == null) {
                return 0;
            }
            return 1 + Math.max(altura(no.getEsquerda()), altura(no.getDireita()));
        }
    }
}

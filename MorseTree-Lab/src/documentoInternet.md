Trabalho: A árvore de Morse.
Antes de qualquer coisa, antes até mesmo de escrever a primeira linha de código faça o seguinte
exercício: LEIA, O TEXTO A SEGUIR COM CARINHO.
Samuel Finley Breese Morse foi o inventor do Código que ainda hoje tem o seu nome, Morse. Intrigado com o
electromagnetismo Samuel torna-se bastante interessado num aparelho chamado telégrafo. Em 1838 concebe um
aparelho ao qual chamou telégrafo elétrico. Este aparelho tinha como base o primeiro telégrafo mas sua
base para comunicar era um Código, cujo o nome tomou ao seu inventor, Morse. Mais tarde, em 1842 consegue
um financiamento ao Senado Americano para ligar Washington a Baltimore, vindo a ser bem sucedido pouco
mais de um ano mais tarde. A partir daí este meio de comunicação expandiu-se tanto nos Estados Unidos
como mais tarde na Europa, chegando a receber um prémio de reconhecimento em 1860 entregue por Napoleão
III.
O Código Morse como muitas invenções teve grande desenvolvimento durante as guerras entre as quais, a
Guerra Civil Americana, onde finalmente se desenvolveram técnicas para colocar linhas no subsolo; nas
guerras Hispano-Americanas (1898), em que os jornalistas usaram esta técnica pela primeira vez como
comunicação; e finalmente as Guerras Russo-Japonesas, que foram pioneiras devido a ser usado pela
primeira vez o Código para comunicação entre tropas.
Atualmente código é usado para comunicações de longa distância mas até esta utilidade devido às novas
tecnologias tem tendência a desaparecer.
Este sistema representa letras, números e sinais de pontuação apenas com uma seqüência de pontos, traços,
e espaços. Letras são separadas por um único espaço e palavras são separadas por dois espaços.
O código pode ser facilmente representado em uma árvore de decisão. Uma árvore de decisão é uma
representação para expressar quais decisões levaram a determinado estado, que caminho foi tomado para que
aquele resultado fosse obtido.
Construa uma árvore de decisão que represente o alfabeto do código morse, contendo as letras e símbolos,
conforme tabela abaixo. Os nós da árvore representam a letra ou símbolo e suas ramificações indicam a
decisão tomada (esquerda para "." e direita para "-"). Implemente a função construirArvore() para
realizar esta tarefa. O nó raiz deve ser vazio e uma letra deve ser adicionada por vez.
A
B
C
D
E
F
G
H
I
J
K
L
M
.-
-...
-.-.
-..
.
..-.
--.
....
..
.---
-.-
.-..
--
O
P
Q
R
S
T
U
W
X
Y
N -.
---
.--.
--.-
.-.
...
-
..-
V ...-
.--
-..-
-.--
Z --..
Após a construção da árvore, você deve implementar as seguintes funções:
- codifica : codifica um texto normal para notação em código morse
- traduz : traduz uma sequencia em código morse para texto normal
- imprimir : imprime uma tabela com o alfabeto morse
As letras são separadas por 1 espaço em branco e as palavras, por 2 espaços.
Caso o resultado seja nulo, significa que a sequência não é válida e a mensagem: "Sequencia inválida!"
deve ser impressa.
A entrada do programa é composta por um número inteiro que indicará a função (1 para codifica, 2 para
traduz e 3 para imprimir) seguido da sequencia de entrada (caso ocorra). Quando o inteiro obtiver valor
0, o programa deve ser encerrado.
Para as funções codifica e traduz, a saída deve ser apenas o texto ou sequência resultante.
A função imprimir deve ter o seguinta formato de saída: letra\tcodigo.
EX:
Entrada:
1A CASA DE MARCOS E MARIA TEM DOIS QUARTOS
2
-.-. .- -.. .- -. --- -.. . ..- -- .- .- .-. ...- --- .-. . -... .. -. .- .-. .. .-
-.. . - . .-. -. --- -- .- -..- .. -- --- -.. --- .. ... ..-. .. .-.. .... --- ...
0
Saida:
.- -.-. .- ... .- -.. . -- .- .-. -.-. --- ... . -- .- .-. .. .-
--.- ..- .- .-. - --- ...
CADA NO DE UMA ARVORE BINARIA PODE TER NO MAXIMO DOIS FILHOS
- . --
.--. ---
-.. --- .. ...
Construa a solução para o mesmo problema utilizando apenas listas encadeadas e busca sequencial. Você
deve gerar um relatório contendo a descrição de suas soluções, bem como a comparação de desempenho entre
elas, apresentando decisões de projeto e conclusões. Deve conter, também, um diagrama de como o alfabeto
foi estruturado na árvore.
Como submeter o seu trabalho: O trabalho deve ser enviado por email em um arquivo .zip com o seguinte
nome: “alunoSobrenome_alunoSobrenome.zip”. O assunto do email DEVE ser “[aedii] trabalhoMorse
alunoSobrenome_alunoSobrenome”. Envie o seu email para cavmelo@dcc.ufam.edu.br, e é OBRIGATÓRIO enviar
uma cópia do seu email para: leticia.n.santos@gmail.com e lr.fabioml@gmail.com. Caso não siga as
orientações, seu trabalho NÃO irá cair no filtro do email e portanto, o trabalho será dado como não
entregue.
Sobre o Relatório: O relatório deve conter os nomes e números de matrícula da dupla, descrição completa
de como o algoritmo foi implementado, foque na descrição das estruturas foram utilizadas. Ainda, mostre
as informações referentes a execução de seu programa, i.e tempo de processamento e número de
comparações, comentários e suas conclusões solicitados. O relatório Deve ter um nome equivalente a:
“alunoSobrenome_alunoSobrenome.pdf”.
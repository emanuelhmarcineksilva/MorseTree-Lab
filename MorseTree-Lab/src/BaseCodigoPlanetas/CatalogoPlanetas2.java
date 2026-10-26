// Missao Orbita: Catalogo de Exoplanetas (BST)
// Nome do aluno(a): _Emanuel Henrique Marcinek Silva_____________________________
//
// Complete todos os trechos marcados com TODO.
// NAO altere as assinaturas dos metodos publicos nem o main().

public class CatalogoPlanetas2 {

    // ---------------- Tarefa 1: o no ----------------
    private class No {
        int distancia;        // chave: distancia em anos-luz (unica)
        // TODO: declare os campos nome (String), tipo (String),
        //       habitavel (boolean), esquerda (No) e direita (No)
        String nome;
        String tipo;
        boolean habitavel;
        No esquerdaNo;
        No direitaNo;


        No(int distancia, String nome, String tipo, boolean habitavel) {
            this.distancia = distancia;
            this.nome = nome;
            this.tipo = tipo;
            this.habitavel = habitavel;
            esquerdaNo = null;
            direitaNo = null;
        }
    }

    private No raiz;
    private int quantidade;

    // ---------------- Tarefa 2: catalogar (inserir) ----------------
    // Insere o planeta respeitando a regra da BST.
    // Se ja existir planeta com a mesma distancia, imprime
    // "Recusado: ja existe planeta a X anos-luz." e devolve false.
    public boolean catalogar(int distancia, String nome, String tipo, boolean habitavel) {
        
        if (raiz == null) {
            raiz = new No(distancia, nome, tipo, habitavel);
            quantidade++;
            System.out.println("Primeiro planeta adicionado");
            return true;
        }

        No atual = raiz;

        while (atual != null) {
            if (atual.distancia < distancia) {
                if (atual.direitaNo != null) {
                    System.out.println("fui para direita");
                    atual = atual.direitaNo;
                } else {
                    System.out.println("adicionei na direita");
                    atual.direitaNo = new No(distancia, nome, tipo, habitavel);
                    quantidade++;
                    return true;
                }
            } else if (atual.distancia > distancia) {
                if (atual.esquerdaNo != null) {
                    System.out.println("fui para esquerda");
                    atual = atual.esquerdaNo;
                } else {
                    System.out.println("adicionei na esquerda");
                    atual.esquerdaNo = new No(distancia, nome, tipo, habitavel);
                    quantidade++;
                    return true;
                }
            } else if (atual.distancia == distancia) {
                System.out.println("Recusado: ja existe planeta a " + distancia + " anos-luz.");
                return false;
            }
        }

        
        return false;
    }

    // ---------------- Tarefa 3: localizar (buscar) ----------------
    // Imprime a rota percorrida e devolve o nome do planeta (ou null).
    // Formato:  Rota ate 45: 50 30 40 45 -> Bolha (4 saltos)
    //           Rota ate 55: 50 75 60 -> sinal perdido (3 saltos)
    public String localizar(int distancia) {
        
        No atual = raiz;

        String rota = "Rota ate " + distancia + " : ";
        int saltos = 0;

        while (atual != null) {
            if (atual.distancia == distancia) {
                rota = rota + " " + atual.distancia;
                saltos++;
                System.out.println(rota + " -> " + atual.nome + "(" + saltos + " saltos)");
                return rota + " -> " + atual.nome + "(" + saltos + " saltos)";
            } else if (atual.distancia < distancia){
                if (atual.direitaNo != null) {
                    rota = rota + " " + atual.distancia;
                    atual = atual.direitaNo;
                    saltos++;
                } else {
                    rota = rota + " " + atual.distancia;
                    atual = atual.direitaNo;
                    saltos++;
                    System.out.println(rota + " -> sinal perdido (" + saltos + " saltos)");
                    return rota + " -> sinal perdido (" + saltos + " saltos)";
                }
            } else if (atual.distancia > distancia){
                if (atual.esquerdaNo != null) {
                    rota = rota + " " + atual.distancia;
                    atual = atual.esquerdaNo;
                    saltos++;
                } else {
                    rota = rota + " " + atual.distancia;
                    atual = atual.esquerdaNo;
                    saltos++;
                    System.out.println(rota + " -> sinal perdido (" + saltos + " saltos)");
                    return rota + " -> sinal perdido (" + saltos + " saltos)";
                }
            }
        }

        return rota;
    }

    // ---------------- Tarefa 4: extremos ----------------
    // Devolvem o nome do planeta mais proximo / mais distante (null se vazio).
    public String maisProximo() {

        if (raiz == null) { return null;}
        No atual = raiz;
        while (atual.esquerdaNo != null) {
            atual = atual.esquerdaNo;
        }

        return atual.nome;
    }

    private No menorNo(No no) {
        if (no == null) {return null;}

        if (no.esquerdaNo == null) {
            return no;
        }

        return menorNo(no.esquerdaNo);
    }

    public String maisDistante() {
        if (raiz == null) { return null;}
        No atual = raiz;
        while (atual.direitaNo != null) {
            atual = atual.direitaNo;
        }

        return atual.nome;
    }

    private No maiorNo(No no) {
        if (no == null) { return null;}

        
        if(no.direitaNo == null) {
            return no;
        }

        return maiorNo(no.direitaNo);
    }

    // ---------------- Tarefa 5: descatalogar (remover) ----------------
    // Os dois metodos removem o planeta e devolvem true (false se nao existir).
    // No caso de dois filhos:
    //   - PelosMenores: o substituto e o Maior da subarvore esquerda
    //   - PelosMaiores: o substituto e o Menor da subarvore direita
    // Atencao: copie TODOS os dados do substituto (nao so a distancia)!

    private boolean existe (int distancia) {
        No atual = raiz;

        while(atual != null) {
            if(distancia == atual.distancia) {
                return true;
            }

            atual = distancia < atual.distancia ? atual.esquerdaNo : atual.direitaNo;
        }

        return false;
    }


    public boolean descatalogarPelosMenores(int distancia) {
        if (!existe(distancia)) {return false;}

        raiz = remover(raiz, distancia, true);
        quantidade--;
        return true;
    }

    public boolean descatalogarPelosMaiores(int distancia) {
        if (!existe(distancia)) {return false;}

        raiz = remover(raiz, distancia, false);
        quantidade--;
        return true;
    }

    private No remover(No no, int distancia, boolean pelosMenores) {
        if (no == null) {return null;}

        if (distancia < no.distancia){
            no.esquerdaNo = remover(no.esquerdaNo, distancia, pelosMenores);
        } else if (distancia > no.distancia) {
            no.direitaNo = remover(no.direitaNo, distancia, pelosMenores);
        } else{

            // sem filhos
            if (no.esquerdaNo == null && no.direitaNo == null) {
                return null;
            }

            // um filho
            if (no.esquerdaNo == null) {
                return no.direitaNo;
            }

            if (no.direitaNo == null) {
                return no.esquerdaNo;
            }

            // dois filhos
            if (no.direitaNo != null && no.esquerdaNo != null){

                if (pelosMenores) {
                    No substituto = maiorNo(no.esquerdaNo);
                    no.habitavel = substituto.habitavel;
                    no.tipo = substituto.tipo;
                    no.nome = substituto.nome;
                    no.distancia = substituto.distancia;
                    no.esquerdaNo = remover(no.esquerdaNo, substituto.distancia, true);
                } else if (!pelosMenores) {
                    No substituto = menorNo(no.direitaNo);
                    no.habitavel = substituto.habitavel;
                    no.tipo = substituto.tipo;
                    no.nome = substituto.nome;
                    no.distancia = substituto.distancia;
                    no.direitaNo = remover(no.direitaNo, substituto.distancia, false);
                }
            }
        }

        return no;

    }


    // ---------------- Tarefa 6: relatorios ----------------
    // Formato: Catalogo: 10(Pipoca) 15(Rubi-9) ... 95(Tita)
    public void listarEmOrdem() {
        System.out.println("Catalogo: ");
        emOrdem(raiz);
    }

    private void emOrdem(No no) {
        if(no == null) {return;}

        emOrdem(no.esquerdaNo);
        System.out.println(no.distancia + "(" + no.nome + ")");
        emOrdem(no.direitaNo);
    }

    // Altura: arvore vazia = -1, so a raiz = 0
    public int altura() {
        return altura(raiz);

    }

    private int altura(No no) {

        if (no == null) {
            return -1;
        }

        int esquerda = altura(no.esquerdaNo);
        int direita = altura(no.direitaNo);

        if (esquerda > direita) {
            return esquerda + 1;
        } else {
            return direita + 1;
        }
    }


    public int contarHabitaveis() {
        return contarHabitaveis(raiz);
    }

    private int contarHabitaveis(No no) {
        if (no == null) {
            return 0;
        }

        
        int habitavelDireita = contarHabitaveis(no.direitaNo);
        int habitavelEsquerda = contarHabitaveis(no.esquerdaNo);

        if (no.habitavel == true) {
            return habitavelDireita + habitavelEsquerda + 1;
        }

        return habitavelDireita + habitavelEsquerda;
    }

    public int getQuantidade() {
        return quantidade;
    }

    // ---------------- Desafio (bônus) ----------------
    // Lista, em ordem, os nomes dos planetas com distancia entre min e max.
    // So vale o bonus se NAO visitar subarvores que nao podem ter resposta.
    // Formato: Alcance [33, 66]: Lumi Nimbus Bolha Kepleria Verdana
    public void listarIntervalo(int min, int max) {
        
        String intervalo = listarIntervalo(min, max, raiz);
        System.out.println("Alcance [" +min+ "," +max+ "]:" + intervalo);

    }

    private String listarIntervalo(int min, int max, No no) {
        
        return null;
    }

    // ================= Não altere daqui para baixo =================
    public static CatalogoPlanetas2 montarCatalogo() {
        CatalogoPlanetas2 c = new CatalogoPlanetas2();
        c.catalogar(50, "Kepleria", "rochoso", true);
        c.catalogar(30, "Zuno", "gasoso", false);
        c.catalogar(75, "Glacius", "gelado", false);
        c.catalogar(15, "Rubi-9", "rochoso", false);
        c.catalogar(40, "Nimbus", "gasoso", false);
        c.catalogar(60, "Oasis", "rochoso", true);
        c.catalogar(100, "Vortex", "gasoso", false);
        c.catalogar(120, "Vortex", "gasoso", false);
        c.catalogar(130, "Vortex", "gasoso", false);
        c.catalogar(140, "Vortex", "gasoso", false);
        c.catalogar(10, "Pipoca", "rochoso", false);
        c.catalogar(20, "Cristalina", "gelado", false);
        c.catalogar(35, "Lumi", "rochoso", true);
        c.catalogar(45, "Bolha", "gasoso", false);
        c.catalogar(65, "Verdana", "rochoso", true);
        c.catalogar(85, "Neve-7", "gelado", false);
        c.catalogar(95, "Tita", "gasoso", false);
        return c;
    }

    public static void main(String[] args) {
        CatalogoPlanetas2 c = montarCatalogo();
        c.catalogar(40, "Impostor", "gasoso", false);   // duplicado
        c.listarEmOrdem();
        System.out.println("Planetas: " + c.getQuantidade() + " | Altura: " + c.altura()
                + " | Habitaveis: " + c.contarHabitaveis());
        System.out.println("Mais proximo: " + c.maisProximo() + " | Mais distante: " + c.maisDistante());

        c.localizar(45);
        c.localizar(55);

        c.descatalogarPelosMenores(10);   // folha
        c.descatalogarPelosMenores(60);   // um filho
        c.descatalogarPelosMenores(30);   // dois filhos
        c.descatalogarPelosMaiores(75);   // dois filhos
        c.descatalogarPelosMaiores(140);   // dois filhos
        c.localizar(35);
        c.listarEmOrdem();
        System.out.println("Planetas: " + c.getQuantidade() + " | Altura: " + c.altura()
                + " | Habitaveis: " + c.contarHabitaveis());

        c.listarIntervalo(33, 66);
    }
}

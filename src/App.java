import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import grafo.Aresta;
import grafo.Cidade;
import grafo.Grafo;
import grafo.Vertice;

public class App {
    public static void main(String[] args) throws Exception {

        Grafo<Cidade> grafo = new Grafo<Cidade>();
        /*
         * Ler a entrada e armezana os valores dentro dos objetos da classe Cidade (nome
         * e codigo)
         */
        try {
            FileReader arq = new FileReader("./src/testes/entrada_wikimedia.txt");
            BufferedReader lerArq = new BufferedReader(arq);

            String value = lerArq.readLine();
            String linha = lerArq.readLine();
            for (int i = 0; i < Integer.parseInt(value); i++) {
                String[] linhaSplit = linha.split(";");
                grafo.adicionarVertice(new Cidade(linhaSplit[1], Integer.parseInt(linhaSplit[0])));
                linha = lerArq.readLine(); // lê da segunda até a última linha
            }
            for (int i = 0; i < Integer.parseInt(value); i++) {
                String[] linhaSplit = linha.split(";");
                ArrayList<Vertice<Cidade>> verticesGrafo = grafo.getVertices();
                for (int j = 0; j < Integer.parseInt(value); j++) {
                    if (Float.parseFloat(linhaSplit[j]) > 0)
                        grafo.adicionarAresta(verticesGrafo.get(i).getValor(),
                                verticesGrafo.get(j).getValor(),
                                Float.parseFloat(linhaSplit[j]));
                }
                linha = lerArq.readLine(); // lê da segunda até a última linha
            }

            arq.close();
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n",
                    e.getMessage());
        }

        // Lista as cidades presentes no grafo
        ArrayList<Vertice<Cidade>> cidades = grafo.getVertices();
        System.out.println("Cidades: ");
        for (int i = 0; i < cidades.size(); i++) {
            Vertice<Cidade> cidade = cidades.get(i);
            System.out.println(cidade.getValor());
        }
        Menu(grafo);
    }

    /**
     * @param grafo - Grafo onde as operações serão realizadas
     */
    public static void Menu(Grafo<Cidade> grafo) {
        /*
         * i. Obter cidades vizinhas: ao escolher essa opção o usuário deverá informar o
         * código de uma cidade
         * e então o programa deve exibir os códigos e nomes de todas as cidades
         * vizinhas da cidade informada (vértices adjacentes)
         * bem como a distância da cidade escolhida para cada uma das vizinhas.
         * ii. Obter todos os caminhos a partir de uma cidade: o usuário deverá informar
         * o código de uma cidade
         * e o programa deverá exibir todas as cidades (código e nome) às quais é
         * possível chegar saindo da cidade dada
         * (seria um caminhamento em largura no grafo usando a cidade dada como vértice
         * de origem do caminhamento).
         * iii. Sair: o programa é encerrado.
         */
        Scanner userinput = new Scanner(System.in);
        int codigoUm, codigoDois;
        boolean rodando = true;
        ArrayList<Vertice<Cidade>> cidades = grafo.getVertices();
        while (rodando) {
            System.out.println(
                    "\nEscolha uma opção: \n1 - Obter cidades vizinhas\n2 - Obter todos os caminhos a partir de uma cidade\n3 - Obter caminho mínimo entre dois vértices (Dijkstra)\n4 - Sair");
            int escolha = userinput.nextInt();
            if (escolha == 1) {
                System.out.print("Digite o código da cidade ao qual deseja saber as vizinhas: ");
                // Corrigimos o valor selecionado para que fique de acordo com a indexação
                // iniciada em 0
                codigoUm = userinput.nextInt() - 1;
                if (codigoUm < 0 || codigoUm + 1 > cidades.size()) {
                    System.out.println("Código inválido");
                } else {
                    ArrayList<Aresta<Cidade>> destinos = grafo.obterDestino(cidades.get(codigoUm));
                    for (int i = 0; i < destinos.size(); i++) {
                        System.out.println(destinos.get(i).getDestino().getValor());
                    }
                }
            } else if (escolha == 2) {
                System.out.print("Digite o código da cidade que deseja saber os caminhos: ");
                codigoUm = userinput.nextInt() - 1;
                if (codigoUm < 0 || codigoUm + 1 > cidades.size()) {
                    System.out.println("Código inválido");
                } else {
                    grafo.buscaEmLargura(cidades.get(codigoUm));
                }
            } else if (escolha == 3) {
                System.out.print("Digite o código da cidade origem para o caminho mínimo desejado: ");
                codigoUm = userinput.nextInt() - 1;
                if (codigoUm < 0 || codigoUm + 1 > cidades.size()) {
                    System.out.println("Código inválido");
                } else {
                    System.out.print("Digite o código da cidade destino do caminho mínimo desejado: ");
                    codigoDois = userinput.nextInt() - 1;
                    if (codigoDois < 0 || codigoDois + 1 > cidades.size()) {
                        System.out.println("Código inválido");
                    } else {
                        System.out.println(grafo.buscaCaminhoMinimo(cidades.get(codigoUm), cidades.get(codigoDois)));
                    }
                }
            } else if (escolha == 4) {
                rodando = false;
            } else {
                System.out.println("Código inválido!");
            }
        }
        userinput.close();
    }
}
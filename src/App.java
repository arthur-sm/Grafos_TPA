import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import grafo.Aresta;
import grafo.Grafo;
import grafo.Vertice;

public class App {
    public static void main(String[] args) throws Exception {

        Grafo<String> grafo = new Grafo<String>();

        try {
            FileReader arq = new FileReader("./src/testes/entrada.txt");
            BufferedReader lerArq = new BufferedReader(arq);

            String value = lerArq.readLine();
            String linha = lerArq.readLine();
            for (int i = 0; i < Integer.parseInt(value); i++) {
                String[] linhaSplit = linha.split(";");
                grafo.adicionarVertice(linhaSplit[1]);
                linha = lerArq.readLine(); // lê da segunda até a última linha
            }
            for (int i = 0; i < Integer.parseInt(value); i++) {
                String[] linhaSplit = linha.split(";");
                ArrayList<Vertice<String>> verticesGrafo = grafo.getVertices();
                for (int j = 0; j < Integer.parseInt(value); j++) {
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

        ArrayList<Aresta> arestas = grafo.getArestas();

        for (int i = 0; i < arestas.size(); i++) {
            Aresta aresta = arestas.get(i);

            System.out.println("Cidade Origem " + aresta.getOrigem().getValor() + "\nCidade Destino: "
                    + aresta.getDestino().getValor() + "\nDistância: " + aresta.getPeso()
                    + "\n---------------------------------------------\n");

        }

        grafo.buscaEmLargura();

        // Lista as cidades presentes no grafo
        ArrayList<Vertice<String>> cidades = grafo.getVertices();
        System.out.println("Cidades: ");
        for (int i = 0; i < cidades.size(); i++) {
            Vertice<String> cidade = cidades.get(i);
            System.out.println(i + " " + cidade.getValor());
        }
        Menu(grafo);
    }

    /**
     * @param grafo - Grafo onde as operações serão realizadas
     */
    public static void Menu(Grafo<String> grafo) {
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
        int codigo;
        ArrayList<Aresta> arestas = grafo.getArestas();
        ArrayList<Vertice<String>> cidades = grafo.getVertices();
        while (true) {
            System.out.println(
                    "Escolha uma opção: \n1 - Obter cidades vizinhas\n2 - Obter todos os caminhos a partir de uma cidade\n3 - Sair");
            int escolha = userinput.nextInt();
            if (escolha == 1) {
                System.out.print("Digite o código da cidade ao qual deseja saber as vizinhas: ");
                codigo = userinput.nextInt();
                System.out.println("\n Cidades vizinhas da cidade " + cidades.get(codigo).getValor() + ": ");
                for (int i = 0; i < arestas.size(); i++) {
                    Aresta aresta = arestas.get(i);
                    if (aresta.getOrigem() == cidades.get(codigo))
                        System.out.print(aresta.getDestino().getValor() + " ");
                }
            } else if (escolha == 2) {
                System.out.print("Digite o código da cidade que deseja saber os caminhos: ");
                codigo = userinput.nextInt();
                //TODO: Adicionar função que mostra para onde há caminhos sem mostrar a distância
            } else if (escolha == 3) {
                userinput.close();
                break;
            }
        }
    }
}
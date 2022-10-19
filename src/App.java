import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

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

        ArrayList<Aresta> arestas= grafo.getArestas();

        for (int i = 0; i < arestas.size(); i++) {
            Aresta aresta = arestas.get(i);

            System.out.println("Cidade Origem " + aresta.getOrigem().getValor() + "\nCidade Destino: " + aresta.getDestino().getValor() + "\nDistância: " + aresta.getPeso() + "\n---------------------------------------------\n" );

        }

    }
}

/**
 * @author Cleber Salustinao
 * @author Arthur Miguel
 * 
 * Grafo criado com a ideia baseada na Lista de Arestas.
 */

package grafo;

import java.util.ArrayList;

public class Grafo<T> {
  private ArrayList<Aresta<T>> arestas;
  private ArrayList<Vertice<T>> vertices;

  public Grafo() {
    this.arestas = new ArrayList<Aresta<T>>();
    this.vertices = new ArrayList<Vertice<T>>();
  }

  /**
   * Função repsonsável por adicionar um vertice ao grafo, criando o vertice
   * baseado em um valor.
   * 
   * @param valor
   * @return
   */
  public Vertice<T> adicionarVertice(T valor) {
    Vertice<T> novo = new Vertice<T>(valor);
    this.vertices.add(novo);

    return novo;
  }

  /**
   * Função responsável para obter o vértice a partir de um determinado valor.
   * 
   * @param valor
   * @return
   */
  private Vertice obterVertice(T valor) {
    Vertice vertice;
    for (int i = 0; i < this.vertices.size(); i++) {
      vertice = this.vertices.get(i);
      if (vertice.getValor().equals(valor))
        return vertice;
    }

    return null;
  }

  /**
   * Função repsonsável por adicionar um aresta ao grafo, criando o vertice
   * baseado em uma origem, destino e peso.
   * E criando os vértices desses.
   * 
   * @param origem
   * @param destino
   * @param peso
   */
  public void adicionarAresta(T origem, T destino, float peso) {
    Vertice verticeOrigem, verticeDestino;
    Aresta novaAresta;

    verticeOrigem = obterVertice(origem);

    if (verticeOrigem == null)
      verticeOrigem = adicionarVertice(destino);

    verticeDestino = obterVertice(destino);

    if (verticeDestino == null)
      verticeDestino = adicionarVertice(destino);
    novaAresta = new Aresta(verticeOrigem, verticeDestino, peso);

    if (!verticeOrigem.equals(verticeDestino))
      this.arestas.add(novaAresta);
  }

  /**
   * Função responsável por obter os destinos a partir de um vertice
   * 
   * @param vertice
   * @return
   */
  public ArrayList<Aresta<T>> obterDestino(Vertice vertice) {
    ArrayList<Aresta<T>> destinos = new ArrayList<>();
    Aresta atual;
    for (int i = 0; i < this.arestas.size(); i++) {
      atual = this.arestas.get(i);
      if (atual.getOrigem().equals(vertice))
        destinos.add(atual);
    }
    return destinos;
  }

  /**
   * Função que busca imprimir os elementos a partir do primeiro vertice, que
   * possua conexão com outros
   */
  public void buscaEmLargura() {
    buscaEmLargura(vertices.get(0));
  }

  /**
   * Função responsável por imprimir os elementos que possuem alguma conexão com
   * outros, baseado em um vertice de origem.
   * 
   * @param origem
   */
  public void buscaEmLargura(Vertice origem) {
    ArrayList<Vertice> marcados = new ArrayList<>();
    ArrayList<Vertice> fila = new ArrayList<>();
    Vertice atual = origem;
    Vertice proximo;

    fila.add(atual);
    while (fila.size() > 0) {
      atual = fila.get(0);
      fila.remove(0);
      marcados.add(atual);
      System.out.println(atual.getValor());
      ArrayList<Aresta<T>> destinos = this.obterDestino(atual);
      for (int i = 0; i < destinos.size(); i++) {
        proximo = destinos.get(i).getDestino();
        if (!marcados.contains(proximo) && !fila.contains(proximo)) {
          fila.add(proximo);
        }
      }
    }
  }

  public ArrayList<Aresta<T>> caminhamentoMinimo(Vertice origem) {
    ArrayList<Aresta<T>> custoMinimo = new ArrayList<>();
    ArrayList<Vertice> abertos = new ArrayList<>();

    for (Vertice vertice : vertices) {
      if (vertice.equals(origem)) {
        custoMinimo.add(new Aresta<>(vertice, vertice, 0));
      } else {
        custoMinimo.add(new Aresta<>(null, vertice, 1000000000));
      }
      abertos.add(vertice);
    }

    float peso = 1000000;
    int contador = 0;

    while (abertos.size() != 0 && contador < 5) {
      Vertice aberto = abertos.get(0);
      for (Aresta k : custoMinimo) {
        if (k.getPeso() < peso && abertos.contains(k.getDestino())) {
          aberto = k.getDestino();
        }
      }
      for (Aresta aresta : arestas) {
        if (aresta.getOrigem().equals(aberto) && aberto.equals(origem)) {
          for (int i = 0; i < custoMinimo.size(); i++) {
            if (custoMinimo.get(i).getPeso() > aresta.getPeso()
                && aresta.getDestino().equals(custoMinimo.get(i).getDestino())) {
              custoMinimo.get(i).setOrigem(aresta.getOrigem());
              custoMinimo.get(i).setPeso(aresta.getPeso());
            }
          }
        } else if (aresta.getOrigem().equals(aberto)) {
          for (Aresta cAresta : custoMinimo) {
            if (cAresta.getDestino().equals(aresta.getOrigem())) {
              peso = cAresta.getPeso();
            }
          }

          for (int i = 0; i < custoMinimo.size(); i++) {
            if (custoMinimo.get(i).getPeso() > (aresta.getPeso() + peso)
                && aresta.getDestino().equals(custoMinimo.get(i).getDestino())) {
              // System.out.println(custoMinimo.get(i).getDestino().getValor() + " " +
              // aresta.getPeso() + " " + peso +" = " + (peso + aresta.getPeso()));
              custoMinimo.get(i).setOrigem(aresta.getOrigem());
              custoMinimo.get(i).setPeso(aresta.getPeso() + peso);
            }
          }
        }
      }
      abertos.remove(aberto);
    }
    for (Aresta k : custoMinimo) {
      System.out.println(k.getOrigem().getValor() + " -> " + k.getDestino().getValor() + " " + k.getPeso());
    }

    return custoMinimo;
  }

  public ArrayList<Aresta<T>> getArestas() {
    return arestas;
  }

  public ArrayList<Vertice<T>> getVertices() {
    return vertices;
  }

}

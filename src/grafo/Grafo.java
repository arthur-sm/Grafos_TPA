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
   * Função repsonsável por adicionar um vertice ao grafo, criando o vertice baseado em um valor.
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
   * Função repsonsável por adicionar um aresta ao grafo, criando o vertice baseado em uma origem, destino e peso.
   * E criando os vértices desses.
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
   * @param vertice
   * @return
   */
  private ArrayList<Aresta<T>> obterDestino(Vertice vertice) {
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
   * Função responsável por imprimir os elementos que possuem alguma conexão com outros.
   */
  public void buscaEmLargura() {
    ArrayList<Vertice> marcados = new ArrayList<>();
    ArrayList<Vertice> fila = new ArrayList<>();
    Vertice atual = this.vertices.get(0);

    fila.add(atual);
    while (fila.size() > 0) {
      atual = fila.get(0);
      fila.remove(0);
      marcados.add(atual);
      System.out.println(atual.getValor());
      ArrayList<Aresta<T>> destinos = this.obterDestino(atual);
      Vertice proximo;
      for (int i = 0; i < destinos.size(); i++) {
        proximo = destinos.get(i).getDestino();
        if (!marcados.contains(proximo) && !fila.contains(proximo)) {
          fila.add(proximo);
        }
      }
    }
  }

  /**
   * Função responsável por imprimir os elementos que possuem alguma conexão com outros, baseado em um vertice de origem.
   * @param origem
   */
  public void buscaEmLargura(Vertice origem) {
    ArrayList<Vertice> marcados = new ArrayList<>();
    ArrayList<Vertice> fila = new ArrayList<>();
    Vertice atual = origem;

    fila.add(atual);
    while (fila.size() > 0) {
      atual = fila.get(0);
      fila.remove(0);
      marcados.add(atual);
      System.out.println(atual.getValor());
      ArrayList<Aresta<T>> destinos = this.obterDestino(atual);
      Vertice proximo;
      for (int i = 0; i < destinos.size(); i++) {
        proximo = destinos.get(i).getDestino();
        if (!marcados.contains(proximo) && !fila.contains(proximo)) {
          fila.add(proximo);
        }
      }
    }
  }

  public ArrayList<Aresta<T>> getArestas() {
    return arestas;
  }

  public ArrayList<Vertice<T>> getVertices() {
    return vertices;
  }
}

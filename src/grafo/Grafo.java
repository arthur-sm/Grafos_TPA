/**
 * @author Cleber Salustinao
 * @author Arthur Miguel
 * 
 * Grafo criado com a ideia baseada na Lista de Arestas.
 */

package grafo;

import java.util.ArrayList;

public class Grafo<T> {
  private ArrayList<Aresta> arestas;
  private ArrayList<Vertice<T>> vertices;

  public Grafo() {
    this.arestas = new ArrayList<Aresta>();
    this.vertices = new ArrayList<Vertice<T>>();
  }

  public Vertice<T> adicionarVertice(T valor) {
    Vertice<T> novo = new Vertice<T>(valor);
    this.vertices.add(novo);

    return novo;
  }

  private Vertice obterVertice(T valor) {
    Vertice vertice;
    for (int i = 0; i < this.vertices.size(); i++) {
      vertice = this.vertices.get(i);
      if (vertice.getValor().equals(valor))
        return vertice;
    }

    return null;
  }

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

  private ArrayList<Aresta> obterDestino(Vertice vertice) {
    ArrayList<Aresta> destinos = new ArrayList<>();
    Aresta atual;
    for (int i = 0; i < this.arestas.size(); i++) {
      atual = this.arestas.get(i);
      if (atual.getOrigem().equals(vertice))
        destinos.add(atual);
    }

    return destinos;
  }

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
      ArrayList<Aresta> destinos = this.obterDestino(atual);
      Vertice proximo;
      for (int i = 0; i < destinos.size(); i++) {
        proximo = destinos.get(i).getDestino();
        if (!marcados.contains(proximo) && !fila.contains(proximo)) {
          fila.add(proximo);
        }
      }
    }
  }

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
      ArrayList<Aresta> destinos = this.obterDestino(atual);
      Vertice proximo;
      for (int i = 0; i < destinos.size(); i++) {
        proximo = destinos.get(i).getDestino();
        if (!marcados.contains(proximo) && !fila.contains(proximo)) {
          fila.add(proximo);
        }
      }
    }
  }

  public ArrayList<Aresta> getArestas() {
    return arestas;
  }

  public ArrayList<Vertice<T>> getVertices() {
    return vertices;
  }
}

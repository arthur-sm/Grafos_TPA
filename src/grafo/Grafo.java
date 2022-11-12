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
    /*
     * A estrutura feita por Vertice, estimativa e precedente, foi utilizada de
     * forma
     * análoga utilizando arestas onde Vertice = VerticeDestino (destino),
     * Precedente = VerticeOrigem(origem)
     * e estimativa é o Peso.
     */
    ArrayList<Aresta<T>> arestasCaminhoMinimo = new ArrayList<>();
    // Aqui são os vértices que não foram verificados ainda.
    ArrayList<Vertice> abertos = new ArrayList<>();

    // Aqui estamos populando os vertices do caminho minimo, já colocando a origem
    // com o peso 0.
    for (Vertice vertice : vertices) {
      if (vertice.equals(origem)) {
        arestasCaminhoMinimo.add(new Aresta<>(vertice, vertice, 0));
      } else {
        arestasCaminhoMinimo.add(new Aresta<>(null, vertice, 1000000000));
      }
      abertos.add(vertice);
    }


    /*
     * Executando o algoritmo até todos estarem fechados.
     * Estão sendo considerados fechados, os que não estão dentro do arraylist de
     * abertos
     */
    while (abertos.size() != 0) {
      // Esse peso é para identificar qual possui menor dentre os existentes dentro do caminho mínimo
      float peso = 100000000;
      
      /*
       * O vertice aberto é iniciado e recebe o primeiro dos abertos a fim de iniciação.
       * Ele é setado no for abaixo a partir da busca pelo menor peso, que no caso
       * é a menor estimativa.
       */ 
      Vertice aberto = abertos.get(0);
      for (Aresta k : arestasCaminhoMinimo) {
        if (k.getPeso() < peso && abertos.contains(k.getDestino())) {
          aberto = k.getDestino();
        }
      }

      /*
       * A partir daqui temos a busca por dentro todas as arestas
       * para começar a fazer as estimativas
       */
      for (Aresta aresta : arestas) {
        /*
         * Caso as arestas possuam a origem igual ao vertice aberto
         * porém o vertice aberto é a origem então o set sera feito com base no
         * peso da aresta + 0, pois a estimativa para a origem é sempre zero
         */
        if (aresta.getOrigem().equals(aberto) && aberto.equals(origem)) {
          for (int i = 0; i < arestasCaminhoMinimo.size(); i++) {
            if (arestasCaminhoMinimo.get(i).getPeso() > aresta.getPeso()
                && aresta.getDestino().equals(arestasCaminhoMinimo.get(i).getDestino())) {
              arestasCaminhoMinimo.get(i).setOrigem(aresta.getOrigem());
              arestasCaminhoMinimo.get(i).setPeso(aresta.getPeso());
            }
          }
        } 
        /*
         * Porém caso não seja a origem, é importante saber qual a estimativa do vertice
         * Para somar com o peso da aresta que vai até o novo nó, e saber se essa estimativa é menor
         * que a anterior. 
         */
        else if (aresta.getOrigem().equals(aberto)) {
          float pesoEstimativaCaminhoMinimo = 0;
          /*
           * Aqui então percorremos aos vertices, estimativas e precedentes do caminho mínimo
           * E assim a ideia é que se a origem da aresta que está sendo vista, for igual 
           * ao vertice dentro do caminhoMínimo, então devemos somar a estimativa dentro do caminho mínimo
           * com o peso da aresta
           */
          for (Aresta arestaCM : arestasCaminhoMinimo) {
            if (arestaCM.getDestino().equals(aresta.getOrigem())) {
              pesoEstimativaCaminhoMinimo = arestaCM.getPeso();
            }
          }

          for (int i = 0; i < arestasCaminhoMinimo.size(); i++) {
            if (arestasCaminhoMinimo.get(i).getPeso() > (aresta.getPeso() + pesoEstimativaCaminhoMinimo)
                && aresta.getDestino().equals(arestasCaminhoMinimo.get(i).getDestino())) {
              arestasCaminhoMinimo.get(i).setOrigem(aresta.getOrigem());
              arestasCaminhoMinimo.get(i).setPeso(aresta.getPeso() + pesoEstimativaCaminhoMinimo);
            }
          }
        }
      }
      /*
       * Aqui fecha o aberto atual.
       */
      abertos.remove(aberto);
    }
    for (Aresta k : arestasCaminhoMinimo) {
      System.out.println(k.getOrigem().getValor() + " -> " + k.getDestino().getValor() + " " + k.getPeso());
    }

    return arestasCaminhoMinimo;
  }

  public ArrayList<Aresta<T>> getArestas() {
    return arestas;
  }

  public ArrayList<Vertice<T>> getVertices() {
    return vertices;
  }

}

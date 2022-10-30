package grafo;
/**
 * Classe para armazenar as cidades lidas no arquivo de entrada.
 */
public class Cidade {
  private String nome;
  private int codigo;
  public String getNome() {
    return nome;
  }
  public void setNome(String nome) {
    this.nome = nome;
  }
  public int getCodigo() {
    return codigo;
  }
  public void setCodigo(int codigo) {
    this.codigo = codigo;
  }
  public Cidade(String nome, int codigo) {
    this.nome = nome;
    this.codigo = codigo;
  }
  @Override
  public String toString() {
    return codigo + " " + nome;
  }

  
}

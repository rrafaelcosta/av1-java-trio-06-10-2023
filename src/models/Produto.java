package models;

public class Produto {
    private static int contador = 0;
    private final int codigo;
    private String nome;
    private double preco;
    private int quantidade;
    private Categoria categoria;

    public Produto() {
        contador++;
        codigo = contador;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public void entradaEstoque(int qtd) {
        quantidade += qtd;
    }

    public void saidaEstoque(int qtd) {
        quantidade -= qtd;
    }

    public void getSaldo() {
        System.out.printf("""
                        codigo = %d
                        nome = %s
                        preco = %.2f
                        quantidade = %d
                        """,
                codigo,
                nome,
                preco,
                quantidade
                );
    }
}

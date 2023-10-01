package models;

public class Categoria {
    private static int contador = 0;
    private final int codigoCategoria;
    private String nome;

    public Categoria() {
        contador++;
        codigoCategoria = contador;
    }

    public int getCodigoCategoria() {
        return codigoCategoria;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return String.format("""
            código= %d
            nome= %s
            """, codigoCategoria, nome);
    }
}

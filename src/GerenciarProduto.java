import models.Categoria;
import models.Produto;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GerenciarProduto {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcao;
        List<Produto> produtos;
        List<Categoria> categorias;
        produtos = new ArrayList<>();
        categorias = new ArrayList<>();

        while(true) {
            System.out.println("\nO que deseja fazer?");
            System.out.println(
                    """
                            \u001B[32mCadastrar categoria (1)
                            Cadastrar produto (2)
                            Dar entrada no produto no estoque (3)
                            Dar saída do produto do estoque (4)
                            Mostrar o saldo disponível de todos os produtos cadastrados (5)
                            Encerrar programa (0)\u001B[0m
                            """);

            System.out.print("Opção: ");
            try {
                opcao = Integer.parseInt(sc.nextLine());

                if (opcao < 0 || opcao > 5) {
                    System.out.println("\u001B[33mOpção inválida! Por favor, escolha uma opção válida.\u001B[0m");
                    continue;
                }

                if (opcao == 0) {
                    break;
                }

                if (opcao == 1) {
                    System.out.print("Digite o nome da categoria: ");
                    String nomeCategoria = sc.nextLine();

                    boolean categoriaExistente = false;
                    for (Categoria categoria : categorias) {
                        if (categoria.getNome().equalsIgnoreCase(nomeCategoria)) {
                            categoriaExistente = true;
                            break;
                        }
                    }

                    if (categoriaExistente) {
                        System.out.println("\u001B[33mA categoria já existe.\u001B[0m");
                    } else {
                        Categoria novaCategoria = new Categoria();
                        novaCategoria.setNome(nomeCategoria);
                        categorias.add(novaCategoria);
                        System.out.println("\u001B[34mCategoria cadastrada com sucesso.\u001B[0m");
                    }
                }

                if (opcao == 2) {
                    if (categorias.isEmpty()) {
                        System.out.println("\u001B[33mNão existe nenhuma categoria cadastrada, por favor cadastre pelo menos uma categoria.\u001B[0m");
                        continue;
                    }

                    System.out.print("Digite o nome do produto: ");
                    String nomeProduto = sc.nextLine();

                    boolean produtoExistente = false;
                    for (Produto produto : produtos) {
                        if (produto.getNome().equalsIgnoreCase(nomeProduto)) {
                            produtoExistente = true;
                            break;
                        }
                    }

                    if (produtoExistente) {
                        System.out.println("\u001B[33mO produto já existe.\u001B[0m");
                        continue;
                    }

                    boolean escolhendoCategoria = true;
                    while (escolhendoCategoria) {
                        boolean erro = false;
                        System.out.println("Escolha um codigo da categoria para " + nomeProduto);
                        System.out.println();
                        for (Categoria categoria : categorias) {
                            System.out.println(categoria);
                        }

                        System.out.print("Código: ");

                        try {
                            int codigo = Integer.parseInt(sc.nextLine());
                            for (Categoria categoria : categorias) {
                                if (categoria.getCodigoCategoria() == codigo) {
                                    Produto novoProduto = new Produto();
                                    boolean escolhendoPreco = true;
                                    boolean escolhendoQuantidade = true;

                                    while (escolhendoPreco) {
                                        System.out.printf("Digite o preço do produto %s: ", nomeProduto);
                                        try {
                                            double preco = Double.parseDouble(sc.nextLine());
                                            if (preco < 0) {
                                                System.out.println("\u001B[33mPreço deve ser positivo!\u001B[0m");
                                                continue;
                                            }

                                            novoProduto.setPreco(preco);
                                            escolhendoPreco = false;
                                        } catch (NumberFormatException e) {
                                            System.out.println("\u001B[31mPreço inválido! Tente novamente.\u001B[0m");
                                        }
                                    }

                                    while (escolhendoQuantidade) {
                                        System.out.printf("Digite a quantidade do produto %s: ", nomeProduto);
                                        try {
                                            int quantidade = Integer.parseInt(sc.nextLine());
                                            if (quantidade < 0) {
                                                System.out.println("\u001B[33mQuantidade deve ser positivo!\u001B[0m");
                                                continue;
                                            }

                                            novoProduto.setQuantidade(quantidade);
                                            escolhendoQuantidade = false;
                                        } catch (NumberFormatException e) {
                                            erro = true;
                                            System.out.println("\u001B[31mQuantidade inválida! Tente novamente.\u001B[0m");
                                        }
                                    }

                                    novoProduto.setNome(nomeProduto);
                                    novoProduto.setCategoria(categoria);
                                    produtos.add(novoProduto);

                                    escolhendoCategoria = false;
                                    break;
                                }
                            }
                        }catch(NumberFormatException e) {
                            erro = true;
                            System.out.println("\u001B[31mOpção inválida! Por favor, digite um número válido.\u001B[0m");
                        }
                        if (escolhendoCategoria && !erro) {
                            System.out.println("\u001B[33mCódigo inválido! Por favor, escolha uma código válido.\u001B[0m");
                        }
                    }

                    System.out.println("\u001B[34mProduto cadastrado com sucesso!\u001B[0m");
                }

                if (opcao == 3) {
                    if (produtos.isEmpty()) {
                        System.out.println("\u001B[33mNão existe nenhum produto cadastrado, por favor cadastre pelo menos um produto.\u001B[0m");
                        continue;
                    }

                    System.out.print("Produtos cadastrados\n\n");

                    boolean escolhendoCodigo = true;
                    while (escolhendoCodigo) {
                        boolean erro = false;

                        for (Produto produto: produtos) {
                            System.out.printf("""
                                codigo = %d
                                nome = %s
                                quantidade: %d
                                ----------------------
                                """, produto.getCodigo(), produto.getNome(), produto.getQuantidade());
                        }

                        try {
                            System.out.print("Código do produto: ");
                            int codigo = Integer.parseInt(sc.nextLine());

                            for (Produto produto: produtos) {
                                if (produto.getCodigo() == codigo) {
                                    try {
                                        while (true) {
                                            System.out.printf("Digite a quantidade a adicionar para %s: ", produto.getNome());
                                            int quantidade = Integer.parseInt(sc.nextLine());

                                            if (quantidade >= 0) {
                                                produto.entradaEstoque(quantidade);
                                                System.out.println("\u001B[34mQuantidade adicionada com sucesso!\u001B[0m");
                                                escolhendoCodigo = false;
                                                break;
                                            }

                                            System.out.println("\u001B[33mQuantidade deve ser maior ou igual a 0\u001B[0m");
                                        }
                                    } catch (NumberFormatException e) {
                                        erro = true;
                                        System.out.println("\u001B[31mQuantidade inválida! Tente novamente.\u001B[0m");
                                    }
                                }

                                if (!escolhendoCodigo) {
                                    break;
                                }
                            }
                        } catch (NumberFormatException e) {
                            erro = true;
                            System.out.println("\u001B[31mCódigo inválido! Tente novamente.\u001B[0m");
                        }

                        if (escolhendoCodigo && !erro) {
                            System.out.println("\u001B[33mCódigo não existe, tente de novo.\u001B[0m");
                        }
                    }

                }

                if (opcao == 4) {
                    if (produtos.isEmpty()) {
                        System.out.println("\u001B[33mNão existe nenhum produto cadastrado, por favor cadastre pelo menos um produto.\u001B[0m");
                        continue;
                    }

                    System.out.print("Produtos cadastrados\n\n");

                    boolean escolhendoCodigo = true;
                    while (escolhendoCodigo) {
                        boolean erro = false;

                        for (Produto produto: produtos) {
                            System.out.printf("""
                                codigo = %d
                                nome = %s
                                quantidade = %d
                                ----------------------
                                """, produto.getCodigo(), produto.getNome(), produto.getQuantidade());
                        }

                        try {
                            System.out.print("Código do produto: ");
                            int codigo = Integer.parseInt(sc.nextLine());

                            for (Produto produto: produtos) {
                                if (produto.getCodigo() == codigo) {
                                    try {
                                        while (true) {
                                            System.out.printf("Digite a quantidade a remover para %s: ", produto.getNome());
                                            int quantidade = Integer.parseInt(sc.nextLine());

                                            if (quantidade >= 0) {
                                                if (produto.getQuantidade() - quantidade < 0) {
                                                    System.out.println("\u001B[31mQuantidade negativa! Tente de novo.\u001B[0m");
                                                    escolhendoCodigo = false;
                                                    break;
                                                }

                                                produto.saidaEstoque(quantidade);
                                                System.out.println("\u001B[34mQuantidade removida com sucesso!\u001B[0m");
                                                escolhendoCodigo = false;
                                                break;
                                            }

                                            System.out.println("\u001B[33mQuantidade deve ser maior ou igual a 0\u001B[0m");
                                        }
                                    } catch (NumberFormatException e) {
                                        erro = true;
                                        System.out.println("\u001B[31mQuantidade inválida! Tente novamente.\u001B[0m");
                                    }
                                }

                                if (!escolhendoCodigo) {
                                    break;
                                }
                            }
                        } catch (NumberFormatException e) {
                            erro = true;
                            System.out.println("\u001B[31mCódigo inválido! Tente novamente.\u001B[0m");
                        }

                        if (escolhendoCodigo && !erro) {
                            System.out.println("\u001B[33mCódigo não existe, tente de novo.\u001B[0m");
                        }
                    }

                }

                if (opcao == 5) {
                    if (produtos.isEmpty()) {
                        System.out.println("\u001B[33mNão existe nenhum produto cadastrado.\u001B[0m");
                        continue;
                    }

                    for (Categoria categoria: categorias) {
                        System.out.printf("Categoria %s\n", categoria.getNome());
                        System.out.println("-----------------------------");
                        for (Produto produto:  produtos) {
                            if (produto.getCategoria().getCodigoCategoria() == categoria.getCodigoCategoria()) {
                                produto.getSaldo();
                                System.out.println("-----------------------------");
                            }
                        }
                        System.out.print("\n\n");
                    }


                }
            }catch(NumberFormatException e) {
                System.out.println("\u001B[31mOpção inválida! Por favor, digite um número válido.\u001B[0m");
            }
        }
    }
}
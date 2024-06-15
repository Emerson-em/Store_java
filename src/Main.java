import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean encerrarPrograma = false;
        boolean respostaValida = false;
        int respostaFinal = 0;
        int respostaCadastro = 0;
        Admin admin = new Admin("AdmiN", "A@admin123456");
        List<Product> itensCadastrados = new ArrayList<>();
        itensCadastrados.add(new RealProduct("Samsung S24 ULTRA", 3000.00));
        itensCadastrados.add(new RealProduct("Notebook ASUS VIVOBOOK", 2600.00));
        itensCadastrados.add(new RealProduct("Mouse LOGITECH G502 COM FIO", 300.00));

        while (!encerrarPrograma) {
            respostaValida = false; // Reseta a variável para o próximo loop
            while (!respostaValida) {
                try {
                    System.out.println("Você quer se cadastrar? 1-Sim  2-Não");
                    respostaCadastro = scanner.nextInt();
                    if (respostaCadastro == 1 || respostaCadastro == 2 || respostaCadastro == 123) {
                        respostaValida = true;
                    } else {
                        throw new IllegalArgumentException("Resposta inválida. Por favor, digite 1 ou 2.");
                    }
                } catch (java.util.InputMismatchException e) {
                    System.out.println("Entrada inválida. Por favor, insira um número inteiro.");
                    scanner.next();
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }
            if (respostaCadastro == 1) {
                System.out.println("Digite seu primeiro nome e sua senha.");
                RegisteredClient user = new RegisteredClient(scanner.next(), scanner.next());
                System.out.println("Obrigado por se registrar, você receberá um cupom de desconto de 10%");
                processarCompras(scanner, user, itensCadastrados);
            } else if (respostaCadastro == 2) {
                NotRegisteredClient user1 = new NotRegisteredClient();
                processarCompras(scanner, user1, itensCadastrados);
            } else if (respostaCadastro == 123) {
                System.out.println("Digite o login do admin e a senha:");
                Admin admin1 = new Admin(scanner.next(), scanner.next());
                if (admin.getUsername().equalsIgnoreCase(admin1.getUsername()) && admin.getPassword().equals(admin1.getPassword())) {
                    System.out.println("Modo Administrador Ativado.");
                    boolean continuar = true;
                    while (continuar) {
                        System.out.println("Escolha uma opção: 1-Adicionar Produto, 2-Remover Produto, 3-Editar Produto, 4-Listar Produtos, 5-Sair");
                        int opcao = scanner.nextInt();
                        scanner.nextLine(); // Limpa o buffer

                        try {
                            switch (opcao) {
                                case 1:
                                    System.out.println("Digite o nome do produto:");
                                    String nomeProdutoAdd = scanner.nextLine();
                                    System.out.println("Digite o preço do produto:");
                                    double precoProdutoAdd = scanner.nextDouble();
                                    try {
                                        RealProduct novoProduto = new RealProduct(nomeProdutoAdd, precoProdutoAdd);
                                        admin.addProduct(novoProduto);
                                        itensCadastrados.add(novoProduto);
                                    } catch (InvalidPriceException e) {
                                        System.out.println(e.getMessage());
                                    }
                                    break;
                                case 2:
                                    System.out.println("Digite o nome do produto a ser removido:");
                                    String nomeProdutoRem = scanner.nextLine();
                                    admin.removeProduct(nomeProdutoRem);
                                    itensCadastrados.removeIf(p -> p.getName().equalsIgnoreCase(nomeProdutoRem));
                                    break;
                                case 3:
                                    System.out.println("Digite o nome do produto a ser editado:");
                                    String nomeProdutoEdit = scanner.nextLine();
                                    System.out.println("Digite o novo preço do produto:");
                                    double precoProdutoEdit = scanner.nextDouble();
                                    try {
                                        admin.editProduct(nomeProdutoEdit, precoProdutoEdit);
                                        for (Product p : itensCadastrados) {
                                            if (p.getName().equalsIgnoreCase(nomeProdutoEdit)) {
                                                p.setPrice(precoProdutoEdit);
                                                break;
                                            }
                                        }
                                    } catch (InvalidPriceException e) {
                                        System.out.println(e.getMessage());
                                    }
                                    break;
                                case 4:
                                    admin.listProducts();
                                    break;
                                case 5:
                                    continuar = false;
                                    break;
                                default:
                                    System.out.println("Opção inválida. Digite novamente.");
                            }
                        } catch (ProductNotFoundException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
            }
            System.out.println("Você deseja continuar no programa ou sair? Digite 1 para continuar, digite 0 para finalizar.");
            respostaFinal = scanner.nextInt();
            switch (respostaFinal) {
                case 1:
                    System.out.println("Ok!");
                    break;
                case 0:
                    encerrarPrograma = true;
                    break;
                default:
                    System.out.println("Opção inválida. Digite novamente.");
            }
        }
    }

    public static void processarCompras(Scanner scanner, RegisteredClient user, List<Product> itensCadastrados) {
        boolean compras = false;

        while (!compras) {
            try {
                exibirItensDisponiveis(itensCadastrados);
                System.out.println("Digite o número dos produtos que você deseja adicionar ao carrinho:");
                System.out.println("Ao selecionar todos os itens desejados, digite 0");
                int shop = scanner.nextInt();

                if (shop > 0 && shop <= itensCadastrados.size()) {
                    user.addToCart(itensCadastrados.get(shop - 1));
                } else if (shop == 0) {
                    compras = true;
                } else {
                    throw new IllegalArgumentException("Produto inexistente. Por favor, selecione um dos itens válidos.");
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, insira um número inteiro.");
                scanner.next();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        exibirCarrinhoEProcessarPagamento(user);
    }

    public static void processarCompras(Scanner scanner, NotRegisteredClient user, List<Product> itensCadastrados) {
        boolean compras = false;

        while (!compras) {
            try {
                exibirItensDisponiveis(itensCadastrados);
                System.out.println("Digite o número dos produtos que você deseja adicionar ao carrinho:");
                System.out.println("Ao selecionar todos os itens desejados, digite 0");
                int shop = scanner.nextInt();

                if (shop > 0 && shop <= itensCadastrados.size()) {
                    user.addToCart(itensCadastrados.get(shop - 1));
                } else if (shop == 0) {
                    compras = true;
                } else {
                    throw new IllegalArgumentException("Produto inexistente. Por favor, selecione um dos itens válidos.");
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, insira um número inteiro.");
                scanner.next();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        exibirCarrinhoEProcessarPagamento(user);
    }

    public static void exibirCarrinhoEProcessarPagamento(RegisteredClient user) {
        for (Product p : user.getCart().productsList()) {
            System.out.println("Produto no carrinho: " + p.getName() + "\nValor: " + p.getPrice() + "R$");
        }
        double total = user.getCart().totalPrice();
        System.out.println("Preço final: " + total + "R$");

        double totalComDesconto = ((RealCart) user.getCart()).applyDiscount(total);
        System.out.println("Valor total com desconto: " + totalComDesconto + "R$");

        if (user.paymentProcess(totalComDesconto)) {
            user.generateReport();
        }
    }

    public static void exibirCarrinhoEProcessarPagamento(NotRegisteredClient user) {
        for (Product p : user.getCart().productsList()) {
            System.out.println("Produto no carrinho: " + p.getName() + "\nValor: " + p.getPrice() + "R$");
        }
        double total = user.getCart().totalPrice();
        System.out.println("Preço final: " + total + "R$");

        if (user.paymentProcess(total)) {
            user.generateReport();
        }
    }

    public static void exibirItensDisponiveis(List<Product> itens) {
        System.out.println("Itens disponíveis:");
        for (int i = 0; i < itens.size(); i++) {
            System.out.println((i + 1) + ". " + itens.get(i).getName() + " - R$" + itens.get(i).getPrice());
        }
    }
}
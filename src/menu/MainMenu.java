package menu;

import java.util.Scanner;

public class MainMenu {

    public void exibirMenu() {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n========= SISTEMA DE RESTAURANTE =========");
            System.out.println("1 - Gerenciar Cargos");
            System.out.println("2 - Gerenciar Funcionários");
            System.out.println("3 - Gerenciar Comandas");
            System.out.println("4 - Gerenciar Produtos");
            System.out.println("5 - Gerenciar Categorias");
            System.out.println("6 - Gerenciar Pagamentos");
            System.out.println("7 - Gerenciar Adicionais");
            System.out.println("8 - Gerenciar Pedidos");
            System.out.println("9 - Relatórios");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());

                switch (opcao) {
                    case 1 -> new CargoMenu().exibirMenu();
                    case 2 -> new FuncionarioMenu().exibirMenu();
                    case 3 -> new ComandaMenu().exibirMenu();
                    case 4 -> new ProdutoMenu().exibirMenu();
                    case 5 -> new CategoriaMenu().exibirMenu();
                    case 6 -> new PagamentoMenu().exibirMenu();
                    case 7 -> new AdicionalMenu().exibirMenu();
                    case 8 -> new PedidoMenu().exibirMenu();
                    case 9 -> new RelatorioMenu().exibirMenu();
                    case 0 -> System.out.println("👋 Encerrando o sistema. Até logo!");
                    default -> System.out.println("❌ Opção inválida.");
                }

            } catch (NumberFormatException e) {
                System.out.println("❌ Entrada inválida. Digite um número.");
                opcao = -1;
            }

        } while (opcao != 0);
    }
}

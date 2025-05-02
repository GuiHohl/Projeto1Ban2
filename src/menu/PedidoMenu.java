package menu;

import db.ConnectionFactory;
import service.PedidoService;

import java.sql.Connection;
import java.util.Scanner;

public class PedidoMenu {

    public void exibirMenu() {
        try (Connection connection = ConnectionFactory.getConnection()) {
            PedidoService service = new PedidoService(connection);
            Scanner scanner = new Scanner(System.in);
            int opcao;

            do {
                System.out.println("\n=== MENU DE PEDIDOS ===");
                System.out.println("1 - Listar pedidos");
                System.out.println("2 - Criar novo pedido");
                System.out.println("3 - Alterar status do pedido");
                System.out.println("4 - Excluir pedido");
                System.out.println("0 - Voltar");
                System.out.print("Escolha uma opção: ");

                try {
                    opcao = Integer.parseInt(scanner.nextLine());

                    switch (opcao) {
                        case 1 -> service.listarPedidosDetalhado();
                        case 2 -> service.criarPedido();
                        case 3 -> service.alterarStatusPedido();
                        case 4 -> service.excluirPedido();
                        case 0 -> System.out.println("🔙 Voltando...");
                        default -> System.out.println("❌ Opção inválida.");
                    }


                } catch (NumberFormatException e) {
                    System.out.println("❌ Entrada inválida.");
                    opcao = -1;
                }

            } while (opcao != 0);

        } catch (Exception e) {
            System.out.println("❌ Erro no menu de pedidos:");
            e.printStackTrace();
        }
    }
}

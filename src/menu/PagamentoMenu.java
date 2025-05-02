package menu;

import db.ConnectionFactory;
import service.PagamentoService;

import java.sql.Connection;
import java.util.Scanner;

public class PagamentoMenu {

    public void exibirMenu() {
        try (Connection connection = ConnectionFactory.getConnection()) {
            PagamentoService service = new PagamentoService(connection);
            Scanner scanner = new Scanner(System.in);
            int opcao;

            do {
                System.out.println("\n=== MENU DE PAGAMENTOS ===");
                System.out.println("1 - Listar pagamentos");
                System.out.println("2 - Registrar pagamento");
                System.out.println("0 - Voltar");
                System.out.print("Escolha uma opção: ");

                try {
                    opcao = Integer.parseInt(scanner.nextLine());

                    switch (opcao) {
                        case 1 -> service.listarPagamentos();
                        case 2 -> service.registrarPagamento();
                        case 0 -> System.out.println("🔙 Voltando ao menu principal...");
                        default -> System.out.println("❌ Opção inválida.");
                    }

                } catch (NumberFormatException e) {
                    System.out.println("❌ Entrada inválida.");
                    opcao = -1;
                }

            } while (opcao != 0);

        } catch (Exception e) {
            System.out.println("❌ Erro no menu de pagamentos:");
            e.printStackTrace();
        }
    }
}


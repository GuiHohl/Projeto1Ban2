package menu;


import db.ConnectionFactory;
import service.ComandaService;

import java.sql.Connection;
import java.util.Scanner;

public class ComandaMenu {

    public void exibirMenu() {
        try (Connection connection = ConnectionFactory.getConnection()) {
            ComandaService service = new ComandaService(connection);
            Scanner scanner = new Scanner(System.in);
            int opcao;

            do {
                System.out.println("\n=== MENU DE COMANDAS ===");
                System.out.println("1 - Listar comandas");
                System.out.println("2 - Abrir nova comanda");
                System.out.println("3 - Cancelar da comanda");
                System.out.println("4 - Excluir comanda");
                System.out.println("0 - Voltar");
                System.out.print("Escolha uma opção: ");

                try {
                    opcao = Integer.parseInt(scanner.nextLine());

                    switch (opcao) {
                        case 1 -> service.listarComandas();
                        case 2 -> service.criarComanda();
                        case 3 -> service.cancelarComanda();
                        case 4 -> service.excluirComanda();
                        case 0 -> System.out.println("🔙 Voltando ao menu principal...");
                        default -> System.out.println("❌ Opção inválida.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("❌ Entrada inválida. Digite um número.");
                    opcao = -1;
                }

            } while (opcao != 0);

        } catch (Exception e) {
            System.out.println("❌ Erro no menu de comandas:");
            e.printStackTrace();
        }
    }
}


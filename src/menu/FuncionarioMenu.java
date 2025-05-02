package menu;

import db.ConnectionFactory;
import service.FuncionarioService;

import java.sql.Connection;
import java.util.Scanner;

public class FuncionarioMenu {

    public void exibirMenu() {
        try (Connection connection = ConnectionFactory.getConnection()) {
            FuncionarioService service = new FuncionarioService(connection);
            Scanner scanner = new Scanner(System.in);
            int opcao;

            do {
                System.out.println("\n=== MENU DE FUNCIONÁRIOS ===");
                System.out.println("1 - Listar funcionários");
                System.out.println("2 - Cadastrar novo funcionário");
                System.out.println("3 - Editar funcionário");
                System.out.println("4 - Excluir funcionário");
                System.out.println("0 - Voltar");
                System.out.print("Escolha uma opção: ");

                try {
                    opcao = Integer.parseInt(scanner.nextLine());

                    switch (opcao) {
                        case 1 -> service.listarFuncionarios();
                        case 2 -> service.criarFuncionarioViaConsole();
                        case 3 -> service.editarFuncionario();
                        case 4 -> service.excluirFuncionario();
                        case 0 -> System.out.println("🔙 Retornando ao menu principal...");
                        default -> System.out.println("❌ Opção inválida.");
                    }

                } catch (NumberFormatException e) {
                    System.out.println("❌ Entrada inválida. Digite um número.");
                    opcao = -1;
                }

            } while (opcao != 0);

        } catch (Exception e) {
            System.out.println("🚨 Erro no menu de funcionários:");
            e.printStackTrace();
        }
    }
}

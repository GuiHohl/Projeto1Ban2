package menu;

import db.ConnectionFactory;
import service.CategoriaService;

import java.sql.Connection;
import java.util.Scanner;

public class CategoriaMenu {

    public void exibirMenu() {
        try (Connection connection = ConnectionFactory.getConnection()) {
            CategoriaService service = new CategoriaService(connection);
            Scanner scanner = new Scanner(System.in);
            int opcao;

            do {
                System.out.println("\n=== MENU DE CATEGORIAS ===");
                System.out.println("1 - Listar categorias");
                System.out.println("2 - Cadastrar nova categoria");
                System.out.println("3 - Editar categoria");
                System.out.println("4 - Excluir categoria");
                System.out.println("0 - Voltar");
                System.out.print("Escolha uma opção: ");

                try {
                    opcao = Integer.parseInt(scanner.nextLine());

                    switch (opcao) {
                        case 1 -> service.listarCategorias();
                        case 2 -> service.cadastrarCategoria();
                        case 3 -> service.editarCategoria();
                        case 4 -> service.excluirCategoria();
                        case 0 -> System.out.println("🔙 Voltando ao menu principal...");
                        default -> System.out.println("❌ Opção inválida.");
                    }

                } catch (NumberFormatException e) {
                    System.out.println("❌ Entrada inválida.");
                    opcao = -1;
                }

            } while (opcao != 0);
        } catch (Exception e) {
            System.out.println("❌ Erro no menu de categorias:");
            e.printStackTrace();
        }
    }
}

package menu;

import db.ConnectionFactory;
import service.RelatorioService;

import java.sql.Connection;
import java.util.Scanner;

public class RelatorioMenu {

    public void exibirMenu() {
        try (Connection connection = ConnectionFactory.getConnection()) {
            RelatorioService service = new RelatorioService(connection);
            Scanner scanner = new Scanner(System.in);
            int opcao;

            do {
                System.out.println("\n===== MENU DE RELATÓRIOS =====");
                System.out.println("1 - Relatório de Faturamento Diário");
                System.out.println("2 - Relatório de Produtos Mais Vendidos");
                System.out.println("3 - Relatório de Vendas por Método de Pagamento");
                System.out.println("4 - Relatório de Funcionários com Mais Comandas Atendidas");
                System.out.println("0 - Voltar");
                System.out.print("Escolha uma opção: ");

                try {
                    opcao = Integer.parseInt(scanner.nextLine());

                    switch (opcao) {
                        case 1 -> service.relatorioFaturamentoDiario();
                        case 2 -> service.relatorioProdutosMaisVendidos();
                        case 3 -> service.relatorioVendasPorMetodoPagamento();
                        case 4 -> service.relatorioFuncionariosComMaisComandas();
                        case 0 -> System.out.println("🔙 Voltando...");
                        default -> System.out.println("❌ Opção inválida.");
                    }

                } catch (NumberFormatException e) {
                    System.out.println("❌ Entrada inválida.");
                    opcao = -1;
                }

            } while (opcao != 0);

        } catch (Exception e) {
            System.out.println("❌ Erro no menu de relatórios:");
            e.printStackTrace();
        }
    }
}

package service;

import dao.FuncionarioDAO;
import dao.MetodoPagamentoDAO;
import dao.PagamentoDAO;
import dao.ProdutoDAO;
import dto.RelatorioFaturamentoDiarioDTO;
import dto.RelatorioFuncionariosComMaisComandasDTO;
import dto.RelatorioProdutosMaisVendidosDTO;
import dto.RelatorioVendasPorMetodoPagamentoDTO;
import model.MetodoPagamentoModel;

import java.sql.Connection;
import java.util.List;

public class RelatorioService {

    private final PagamentoDAO pagamentoDAO;
    private final ProdutoDAO produtoDAO;
    private final FuncionarioDAO funcionarioDAO;
    private final MetodoPagamentoDAO metodoPagamentoDAO;

    public RelatorioService(Connection connection) {
        this.pagamentoDAO = new PagamentoDAO(connection);
        this.produtoDAO = new ProdutoDAO(connection);
        this.funcionarioDAO = new FuncionarioDAO(connection);
        this.metodoPagamentoDAO = new MetodoPagamentoDAO(connection);
    }

    public void relatorioFaturamentoDiario() {
        try {
            List<RelatorioFaturamentoDiarioDTO> relatorio = pagamentoDAO.findFaturamentoDiario();

            System.out.println("\nRelatório de Faturamento Diário:");
            System.out.printf("%-15s %-15s %-15s%n", "Data", "Total Comandas", "Faturamento Total");

            for (RelatorioFaturamentoDiarioDTO dto : relatorio) {
                System.out.println(dto);
            }
        } catch (Exception e) {
            System.out.println("Erro ao gerar relatório de faturamento diário:");
            e.printStackTrace();
        }
    }

    public void relatorioProdutosMaisVendidos() {
        try {
            List<RelatorioProdutosMaisVendidosDTO> relatorio = produtoDAO.findProdutosMaisVendidos();

            System.out.println("\nRelatório de Produtos Mais Vendidos:");
            System.out.printf("%-30s %-15s %-15s%n", "Produto", "Vendas", "Faturamento");

            for (RelatorioProdutosMaisVendidosDTO dto : relatorio) {
                System.out.println(dto);
            }
        } catch (Exception e) {
            System.out.println("Erro ao gerar relatório de produtos mais vendidos:");
            e.printStackTrace();
        }
    }

    public void relatorioVendasPorMetodoPagamento() {
        try {
            List<RelatorioVendasPorMetodoPagamentoDTO> relatorio = pagamentoDAO.findVendasPorMetodo();
            List<MetodoPagamentoModel> metodos = metodoPagamentoDAO.findAll();

            System.out.println("\nRelatório de Vendas por Método de Pagamento:");
            System.out.printf("%-20s %-15s %-15s%n", "Método", "Total Pagamentos", "Total Faturamento");

            for (RelatorioVendasPorMetodoPagamentoDTO dto : relatorio) {
                String nomeMetodo = metodos.stream()
                        .filter(m -> String.valueOf(m.getId()).equals(dto.getMetodoPagamento()))
                        .map(MetodoPagamentoModel::getNome)
                        .findFirst()
                        .orElse("Desconhecido");

                System.out.printf("%-20s %-15d R$ %-13.2f%n",
                        nomeMetodo,
                        dto.getTotalPagamentos(),
                        dto.getTotalVendas());
            }
        } catch (Exception e) {
            System.out.println("Erro ao gerar relatório de vendas por método de pagamento:");
            e.printStackTrace();
        }
    }

    public void relatorioFuncionariosComMaisComandas() {
        try {
            List<RelatorioFuncionariosComMaisComandasDTO> relatorio = funcionarioDAO.findFuncionariosComMaisComandas();

            System.out.println("\nRelatório de Funcionários com Mais Comandas Atendidas:");
            System.out.printf("%-30s %-15s%n", "Funcionário", "Comandas Atendidas");

            for (RelatorioFuncionariosComMaisComandasDTO dto : relatorio) {
                System.out.println(dto);
            }
        } catch (Exception e) {
            System.out.println("Erro ao gerar relatório de funcionários com mais comandas atendidas:");
            e.printStackTrace();
        }
    }
}

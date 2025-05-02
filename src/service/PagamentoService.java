package service;

import dao.ComandaDAO;
import dao.PagamentoDAO;
import model.ComandaModel;
import model.PagamentoModel;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class PagamentoService {

    private final PagamentoDAO pagamentoDAO;
    private final ComandaDAO comandaDAO;

    public PagamentoService(Connection connection) {
        this.pagamentoDAO = new PagamentoDAO(connection);
        this.comandaDAO = new ComandaDAO(connection);
    }

    public void registrarPagamento() {
        Scanner scanner = new Scanner(System.in);

        try {
            List<ComandaModel> comandas = comandaDAO.findAll();
            List<PagamentoModel> pagos = pagamentoDAO.findAll();
            List<Integer> idsPagos = pagos.stream().map(PagamentoModel::getIdComanda).toList();

            List<ComandaModel> abertasSemPagamento = comandas.stream()
                    .filter(c -> "Aberta".equalsIgnoreCase(c.getStatusComanda()) && !idsPagos.contains(c.getIdComanda()))
                    .toList();

            if (abertasSemPagamento.isEmpty()) {
                System.out.println("Nenhuma comanda aberta sem pagamento.");
                return;
            }

            System.out.println("\nSelecione a comanda para pagamento:");
            for (int i = 0; i < abertasSemPagamento.size(); i++) {
                ComandaModel c = abertasSemPagamento.get(i);
                System.out.printf("%d - Comanda #%d | Mesa: %d%n", i + 1, c.getIdComanda(), c.getNumMesa());
            }

            System.out.print("Digite o número: ");
            int escolha = Integer.parseInt(scanner.nextLine());

            if (escolha < 1 || escolha > abertasSemPagamento.size()) {
                System.out.println("Opção inválida.");
                return;
            }

            ComandaModel comandaSelecionada = abertasSemPagamento.get(escolha - 1);

            System.out.print("Valor do pagamento: ");
            BigDecimal valor = new BigDecimal(scanner.nextLine());

            System.out.print("Método de pagamento (ex: Pix, Dinheiro, Cartão): ");
            String metodo = scanner.nextLine();

            PagamentoModel pagamento = new PagamentoModel();
            pagamento.setIdComanda(comandaSelecionada.getIdComanda());
            pagamento.setValor(valor);
            pagamento.setMetodoPagamento(metodo);
            pagamento.setDataPagamento(Timestamp.valueOf(LocalDateTime.now()));

            pagamentoDAO.create(pagamento);

            // Atualiza status da comanda
            comandaSelecionada.setStatusComanda("Fechada");
            comandaDAO.update(comandaSelecionada);

            System.out.println("Pagamento registrado e comanda fechada!");

        } catch (Exception e) {
            System.out.println("Erro ao registrar pagamento:");
            e.printStackTrace();
        }
    }

    public void listarPagamentos() {
        try {
            List<PagamentoModel> lista = pagamentoDAO.findAll();

            if (lista.isEmpty()) {
                System.out.println("Nenhum pagamento registrado.");
                return;
            }

            System.out.println("\nPagamentos:");
            for (PagamentoModel p : lista) {
                System.out.printf("Pagamento #%d | Comanda: %d | R$ %.2f | Método: %s | Data: %s%n",
                        p.getIdPagamento(), p.getIdComanda(), p.getValor(), p.getMetodoPagamento(), p.getDataPagamento());
            }

        } catch (Exception e) {
            System.out.println("Erro ao listar pagamentos:");
            e.printStackTrace();
        }
    }
}

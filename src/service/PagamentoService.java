package service;

import dao.*;
import model.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class PagamentoService {

    private final PagamentoDAO pagamentoDAO;
    private final ComandaDAO comandaDAO;
    private final MetodoPagamentoDAO metodoPagamentoDAO;
    private final PedidoDAO pedidoDAO;
    private final ProdutosPedidosDAO produtosPedidosDAO;
    private final ProdutoDAO produtoDAO;
    private final AdicionaisProdutosPedidosDAO adicionaisProdutosPedidosDAO;
    private final AdicionalDAO adicionalDAO;

    public PagamentoService(Connection connection) {
        this.pagamentoDAO = new PagamentoDAO(connection);
        this.comandaDAO = new ComandaDAO(connection);
        this.metodoPagamentoDAO = new MetodoPagamentoDAO(connection);
        this.pedidoDAO = new PedidoDAO(connection);
        this.produtosPedidosDAO = new ProdutosPedidosDAO(connection);
        this.produtoDAO = new ProdutoDAO(connection);
        this.adicionaisProdutosPedidosDAO = new AdicionaisProdutosPedidosDAO(connection);
        this.adicionalDAO = new AdicionalDAO(connection);
    }

    public void registrarPagamento() {
        Scanner scanner = new Scanner(System.in);

        try {
            List<ComandaModel> comandas = comandaDAO.findAll();
            List<PagamentoModel> pagos = pagamentoDAO.findAll();
            List<Integer> idsPagos = pagos.stream().map(PagamentoModel::getIdComanda).toList();

            List<ComandaModel> abertasSemPagamento = comandas.stream()
                    .filter(c -> c.getIdStatusComanda() == 1 && !idsPagos.contains(c.getIdComanda()))
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

            BigDecimal total = BigDecimal.ZERO;
            List<PedidoModel> pedidos = pedidoDAO.findAll();
            for (PedidoModel pedido : pedidos) {
                if (pedido.getIdComanda() == comandaSelecionada.getIdComanda()) {
                    List<ProdutosPedidosModel> produtos = produtosPedidosDAO.findByPedidoId(pedido.getIdPedido());
                    for (ProdutosPedidosModel p : produtos) {
                        ProdutoModel prod = produtoDAO.read(p.getIdProduto());
                        total = total.add(prod.getPreco());

                        List<AdicionaisProdutosPedidosModel> adicionais = adicionaisProdutosPedidosDAO.findByProdutoPedidoId(p.getIdProdutoPedido());
                        for (AdicionaisProdutosPedidosModel a : adicionais) {
                            AdicionalModel ad = adicionalDAO.read(a.getIdAdicional());
                            total = total.add(ad.getPreco().multiply(BigDecimal.valueOf(a.getQtdAdicional())));
                        }
                    }
                }
            }

            System.out.printf("Valor total da comanda: R$ %.2f%n", total);
            System.out.print("Valor do pagamento: ");
            BigDecimal valor = new BigDecimal(scanner.nextLine());

            System.out.println("Selecione o método de pagamento:");
            List<MetodoPagamentoModel> metodos = metodoPagamentoDAO.findAll();
            for (MetodoPagamentoModel m : metodos) {
                System.out.printf("%d - %s%n", m.getId(), m.getNome());
            }
            int idMetodo = Integer.parseInt(scanner.nextLine());

            PagamentoModel pagamento = new PagamentoModel();
            pagamento.setIdComanda(comandaSelecionada.getIdComanda());
            pagamento.setValor(valor);
            pagamento.setIdMetodoPagamento(idMetodo);
            pagamento.setDataPagamento(Timestamp.valueOf(LocalDateTime.now()));

            pagamentoDAO.create(pagamento);

            comandaSelecionada.setIdStatusComanda(2);
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
            List<MetodoPagamentoModel> metodos = metodoPagamentoDAO.findAll();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

            if (lista.isEmpty()) {
                System.out.println("Nenhum pagamento registrado.");
                return;
            }

            System.out.println("\nPagamentos:");
            for (PagamentoModel p : lista) {
                String nomeMetodo = metodos.stream()
                        .filter(m -> m.getId() == p.getIdMetodoPagamento())
                        .map(MetodoPagamentoModel::getNome)
                        .findFirst()
                        .orElse("Desconhecido");

                String dataFormatada = p.getDataPagamento().toLocalDateTime().format(formatter);

                System.out.printf("Pagamento #%d | Comanda: %d | R$ %.2f | Método: %s | Data: %s%n",
                        p.getIdPagamento(), p.getIdComanda(), p.getValor(), nomeMetodo, dataFormatada);
            }

        } catch (Exception e) {
            System.out.println("Erro ao listar pagamentos:");
            e.printStackTrace();
        }
    }
}

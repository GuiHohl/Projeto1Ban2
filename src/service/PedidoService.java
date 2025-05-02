package service;

import dao.*;
import model.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

public class PedidoService {

    private final PedidoDAO pedidoDAO;
    private final ComandaDAO comandaDAO;
    private final ProdutoDAO produtoDAO;
    private final ProdutosPedidosDAO produtosPedidosDAO;
    private final AdicionaisProdutosPedidosDAO adicionaisProdutosPedidosDAO;
    private final AdicionalDAO adicionalDAO;

    public PedidoService(Connection connection) {
        this.pedidoDAO = new PedidoDAO(connection);
        this.comandaDAO = new ComandaDAO(connection);
        this.produtoDAO = new ProdutoDAO(connection);
        this.produtosPedidosDAO = new ProdutosPedidosDAO(connection);
        this.adicionaisProdutosPedidosDAO = new AdicionaisProdutosPedidosDAO(connection);
        this.adicionalDAO = new AdicionalDAO(connection);
    }

    public void criarPedido() {
        Scanner scanner = new Scanner(System.in);

        try {
            // Escolher comanda aberta
            List<ComandaModel> comandas = comandaDAO.findAll();
            List<ComandaModel> abertas = comandas.stream()
                    .filter(c -> c.getStatusComanda().equalsIgnoreCase("Aberta"))
                    .toList();

            if (abertas.isEmpty()) {
                System.out.println("Nenhuma comanda aberta encontrada.");
                return;
            }

            System.out.println("\nSelecione a comanda:");
            for (int i = 0; i < abertas.size(); i++) {
                ComandaModel c = abertas.get(i);
                System.out.printf("%d - Comanda #%d | Mesa %d%n", i + 1, c.getIdComanda(), c.getNumMesa());
            }

            int escolha = Integer.parseInt(scanner.nextLine());
            if (escolha < 1 || escolha > abertas.size()) {
                System.out.println("Opção inválida.");
                return;
            }

            ComandaModel comandaSelecionada = abertas.get(escolha - 1);

            // Criar pedido
            PedidoModel pedido = new PedidoModel();
            pedido.setIdComanda(comandaSelecionada.getIdComanda());
            pedido.setDataPedido(Timestamp.valueOf(LocalDateTime.now()));
            pedido.setStatusPedido("Aberto");

            int idPedido = pedidoDAO.create(pedido); // retorna ID
            pedido.setIdPedido(idPedido);

            System.out.println("Pedido criado. Adicione produtos:");

            boolean continuar = true;
            while (continuar) {
                List<ProdutoModel> produtos = produtoDAO.findAll();
                for (int i = 0; i < produtos.size(); i++) {
                    ProdutoModel p = produtos.get(i);
                    System.out.printf("%d - %s (R$ %.2f)%n", i + 1, p.getNome(), p.getPreco());
                }

                System.out.print("Selecione o produto: ");
                int prodIndex = Integer.parseInt(scanner.nextLine()) - 1;

                if (prodIndex < 0 || prodIndex >= produtos.size()) {
                    System.out.println("Produto inválido.");
                    continue;
                }

                ProdutoModel produtoSelecionado = produtos.get(prodIndex);

                // Cria produto_pedido
                ProdutosPedidosModel produtoPedido = new ProdutosPedidosModel();
                produtoPedido.setIdPedido(idPedido);
                produtoPedido.setIdProduto(produtoSelecionado.getIdProduto());

                int idProdutoPedido = produtosPedidosDAO.create(produtoPedido);
                produtoPedido.setIdProdutoPedido(idProdutoPedido);

                // Adicionais
                System.out.print("Deseja adicionar adicionais para este produto? (s/n): ");
                if (scanner.nextLine().equalsIgnoreCase("s")) {
                    List<AdicionalModel> adicionais = adicionalDAO.findAll();
                    for (int i = 0; i < adicionais.size(); i++) {
                        AdicionalModel a = adicionais.get(i);
                        System.out.printf("%d - %s (R$ %.2f)%n", i + 1, a.getNome(), a.getPreco());
                    }

                    while (true) {
                        System.out.print("Número do adicional (ou 0 para encerrar): ");
                        int adIndex = Integer.parseInt(scanner.nextLine());
                        if (adIndex == 0) break;

                        if (adIndex < 1 || adIndex > adicionais.size()) {
                            System.out.println("Inválido.");
                            continue;
                        }

                        AdicionalModel adicionalSel = adicionais.get(adIndex - 1);
                        System.out.print("Quantidade: ");
                        int qtd = Integer.parseInt(scanner.nextLine());

                        AdicionaisProdutosPedidosModel adicionalPedido = new AdicionaisProdutosPedidosModel();
                        adicionalPedido.setIdProdutoPedido(idProdutoPedido);
                        adicionalPedido.setIdAdicional(adicionalSel.getIdAdicional());
                        adicionalPedido.setQtdAdicional(qtd);

                        adicionaisProdutosPedidosDAO.create(adicionalPedido);
                    }
                }

                System.out.print("Adicionar outro produto ao pedido? (s/n): ");
                continuar = scanner.nextLine().equalsIgnoreCase("s");
            }

            System.out.println("Pedido registrado com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro ao registrar pedido:");
            e.printStackTrace();
        }
    }

    public void alterarStatusPedido() {
        Scanner scanner = new Scanner(System.in);
        try {
            List<PedidoModel> pedidos = pedidoDAO.findAll();
            if (pedidos.isEmpty()) {
                System.out.println("Nenhum pedido encontrado.");
                return;
            }

            System.out.println("\nSelecione o pedido para alterar o status:");
            for (int i = 0; i < pedidos.size(); i++) {
                PedidoModel p = pedidos.get(i);
                System.out.printf("%d - Pedido #%d | Status: %s%n", i + 1, p.getIdPedido(), p.getStatusPedido());
            }

            int escolha = Integer.parseInt(scanner.nextLine());
            if (escolha < 1 || escolha > pedidos.size()) {
                System.out.println("Escolha inválida.");
                return;
            }

            PedidoModel pedido = pedidos.get(escolha - 1);

            System.out.print("Novo status para o pedido: ");
            String novoStatus = scanner.nextLine();

            // Atualiza apenas o status, mantendo os outros dados do pedido
            pedido.setStatusPedido(novoStatus);
            pedidoDAO.update(pedido);

            System.out.println("Status do pedido atualizado com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro ao alterar status:");
            e.printStackTrace();
        }
    }


    public void excluirPedido() {
        Scanner scanner = new Scanner(System.in);
        try {
            List<PedidoModel> pedidos = pedidoDAO.findAll();
            if (pedidos.isEmpty()) {
                System.out.println("Nenhum pedido para excluir.");
                return;
            }

            System.out.println("\nSelecione o pedido para excluir:");
            for (int i = 0; i < pedidos.size(); i++) {
                PedidoModel p = pedidos.get(i);
                System.out.printf("%d - Pedido #%d | Status: %s%n", i + 1, p.getIdPedido(), p.getStatusPedido());
            }

            int escolha = Integer.parseInt(scanner.nextLine());
            if (escolha < 1 || escolha > pedidos.size()) {
                System.out.println("Escolha inválida.");
                return;
            }

            PedidoModel pedido = pedidos.get(escolha - 1);

            pedidoDAO.delete(pedido.getIdPedido());
            System.out.println("Pedido removido com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro ao excluir pedido:");
            e.printStackTrace();
        }
    }

    public void listarPedidosDetalhado() {
        try {
            List<PedidoModel> pedidos = pedidoDAO.findAll();
            for (PedidoModel pedido : pedidos) {
                System.out.printf("\nPedido #%d | Comanda: %d | Status: %s | Data: %s%n",
                        pedido.getIdPedido(),
                        pedido.getIdComanda(),
                        pedido.getStatusPedido(),
                        pedido.getDataPedido());

                List<ProdutosPedidosModel> produtos = produtosPedidosDAO.findByPedidoId(pedido.getIdPedido());
                BigDecimal totalPedido = BigDecimal.ZERO;

                for (ProdutosPedidosModel produtoPedido : produtos) {
                    ProdutoModel produto = produtoDAO.read(produtoPedido.getIdProduto());
                    BigDecimal precoProduto = produto.getPreco();

                    System.out.printf("  %s (R$ %.2f)%n", produto.getNome(), precoProduto);
                    totalPedido = totalPedido.add(precoProduto);

                    List<AdicionaisProdutosPedidosModel> adicionais = adicionaisProdutosPedidosDAO.findByProdutoPedidoId(produtoPedido.getIdProdutoPedido());
                    for (AdicionaisProdutosPedidosModel adicionalPedido : adicionais) {
                        AdicionalModel adicional = adicionalDAO.read(adicionalPedido.getIdAdicional());
                        BigDecimal totalAdicional = adicional.getPreco().multiply(BigDecimal.valueOf(adicionalPedido.getQtdAdicional()));
                        totalPedido = totalPedido.add(totalAdicional);

                        System.out.printf("    %s x%d (R$ %.2f)%n",
                                adicional.getNome(),
                                adicionalPedido.getQtdAdicional(),
                                totalAdicional);
                    }
                }

                System.out.printf("  Total: R$ %.2f%n", totalPedido);
            }

        } catch (Exception e) {
            System.out.println("Erro ao listar pedidos:");
            e.printStackTrace();
        }
    }
}

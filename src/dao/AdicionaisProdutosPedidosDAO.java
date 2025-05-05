package dao;

import model.AdicionaisProdutosPedidosModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdicionaisProdutosPedidosDAO {
    private final Connection connection;

    public AdicionaisProdutosPedidosDAO(Connection connection) {
        this.connection = connection;
    }

    public int create(AdicionaisProdutosPedidosModel adicionalPedido) throws Exception {
        String sql = "INSERT INTO adicionais_produtos_pedidos (id_produto_pedido, id_adicional, qtd_adicional) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, adicionalPedido.getIdProdutoPedido());
            stmt.setInt(2, adicionalPedido.getIdAdicional());
            stmt.setInt(3, adicionalPedido.getQtdAdicional());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                } else {
                    throw new SQLException("Não foi possível recuperar o ID do adicional_produto_pedido.");
                }
            }
        }
    }

    public AdicionaisProdutosPedidosModel read(int idAdicionalProdutoPedido) throws SQLException {
        String sql = "SELECT * FROM adicionais_produtos_pedidos WHERE id_adicional_produto_pedido = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idAdicionalProdutoPedido);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                AdicionaisProdutosPedidosModel adicionaisProdutosPedidos = new AdicionaisProdutosPedidosModel();
                adicionaisProdutosPedidos.setIdAdicionalProdutoPedido(rs.getInt("id_adicional_produto_pedido"));
                adicionaisProdutosPedidos.setIdProdutoPedido(rs.getInt("id_produto_pedido"));
                adicionaisProdutosPedidos.setIdAdicional(rs.getInt("id_adicional"));
                adicionaisProdutosPedidos.setQtdAdicional(rs.getInt("qtd_adicional"));
                return adicionaisProdutosPedidos;
            }
        }
        return null;
    }

    public void update(AdicionaisProdutosPedidosModel adicionaisProdutosPedidos) throws SQLException {
        String sql = "UPDATE adicionais_produtos_pedidos SET id_produto_pedido = ?, id_adicional = ?, qtd_adicional = ? WHERE id_adicional_produto_pedido = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, adicionaisProdutosPedidos.getIdProdutoPedido());
            stmt.setInt(2, adicionaisProdutosPedidos.getIdAdicional());
            stmt.setInt(3, adicionaisProdutosPedidos.getQtdAdicional());
            stmt.setInt(4, adicionaisProdutosPedidos.getIdAdicionalProdutoPedido());
            stmt.executeUpdate();
        }
    }

    public void delete(int idAdicionalProdutoPedido) throws SQLException {
        String sql = "DELETE FROM adicionais_produtos_pedidos WHERE id_adicional_produto_pedido = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idAdicionalProdutoPedido);
            stmt.executeUpdate();
        }
    }

    public List<AdicionaisProdutosPedidosModel> findByProdutoPedidoId(int idProdutoPedido) throws Exception {
        List<AdicionaisProdutosPedidosModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM adicionais_produtos_pedidos WHERE id_produto_pedido = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idProdutoPedido);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    AdicionaisProdutosPedidosModel item = new AdicionaisProdutosPedidosModel();
                    item.setIdAdicionalProdutoPedido(rs.getInt("id_adicional_produto_pedido"));
                    item.setIdProdutoPedido(rs.getInt("id_produto_pedido"));
                    item.setIdAdicional(rs.getInt("id_adicional"));
                    item.setQtdAdicional(rs.getInt("qtd_adicional"));
                    lista.add(item);
                }
            }
        }

        return lista;
    }
}

package dao;

import model.ProdutosPedidosModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutosPedidosDAO {
    private final Connection connection;

    public ProdutosPedidosDAO(Connection connection) {
        this.connection = connection;
    }

    public int create(ProdutosPedidosModel produtoPedido) throws Exception {
        String sql = "INSERT INTO produtos_pedidos (id_pedido, id_produto) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, produtoPedido.getIdPedido());
            stmt.setInt(2, produtoPedido.getIdProduto());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                } else {
                    throw new SQLException("Não foi possível recuperar o ID do produto_pedido.");
                }
            }
        }
    }

    public ProdutosPedidosModel read(int idProdutoPedido) throws SQLException {
        String sql = "SELECT * FROM produtos_pedidos WHERE id_produto_pedido = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idProdutoPedido);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                ProdutosPedidosModel produtosPedidos = new ProdutosPedidosModel();
                produtosPedidos.setIdProdutoPedido(rs.getInt("id_produto_pedido"));
                produtosPedidos.setIdPedido(rs.getInt("id_pedido"));
                produtosPedidos.setIdProduto(rs.getInt("id_produto"));
                return produtosPedidos;
            }
        }
        return null;
    }

    public void update(ProdutosPedidosModel produtosPedidos) throws SQLException {
        String sql = "UPDATE produtos_pedidos SET id_pedido = ?, id_produto = ? WHERE id_produto_pedido = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, produtosPedidos.getIdPedido());
            stmt.setInt(2, produtosPedidos.getIdProduto());
            stmt.setInt(3, produtosPedidos.getIdProdutoPedido());
            stmt.executeUpdate();
        }
    }

    public void delete(int idProdutoPedido) throws SQLException {
        String sql = "DELETE FROM produtos_pedidos WHERE id_produto_pedido = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idProdutoPedido);
            stmt.executeUpdate();
        }
    }

    public List<ProdutosPedidosModel> findByPedidoId(int idPedido) throws Exception {
        List<ProdutosPedidosModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM produtos_pedidos WHERE id_pedido = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idPedido);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ProdutosPedidosModel item = new ProdutosPedidosModel();
                    item.setIdProdutoPedido(rs.getInt("id_produto_pedido"));
                    item.setIdPedido(rs.getInt("id_pedido"));
                    item.setIdProduto(rs.getInt("id_produto"));
                    lista.add(item);
                }
            }
        }

        return lista;
    }
}

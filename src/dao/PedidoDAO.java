package dao;

import model.PedidoModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {
    private final Connection connection;

    public PedidoDAO(Connection connection) {
        this.connection = connection;
    }

    public int create(PedidoModel pedido) throws Exception {
        String sql = "INSERT INTO pedidos (id_comanda, data_pedido, id_status_pedido) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, pedido.getIdComanda());
            stmt.setTimestamp(2, pedido.getDataPedido());
            stmt.setInt(3, pedido.getIdStatusPedido());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                } else {
                    throw new SQLException("Não foi possível recuperar o ID do pedido.");
                }
            }
        }
    }

    public PedidoModel read(int idPedido) throws SQLException {
        String sql = "SELECT * FROM pedidos WHERE id_pedido = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idPedido);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                PedidoModel pedido = new PedidoModel();
                pedido.setIdPedido(rs.getInt("id_pedido"));
                pedido.setIdComanda(rs.getInt("id_comanda"));
                pedido.setDataPedido(rs.getTimestamp("data_pedido"));
                pedido.setIdStatusPedido(rs.getInt("id_status_pedido"));
                return pedido;
            }
        }
        return null;
    }

    public void update(PedidoModel pedido) throws SQLException {
        String sql = "UPDATE pedidos SET id_comanda = ?, data_pedido = ?, id_status_pedido = ? WHERE id_pedido = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, pedido.getIdComanda());
            stmt.setTimestamp(2, pedido.getDataPedido());
            stmt.setInt(3, pedido.getIdStatusPedido());
            stmt.setInt(4, pedido.getIdPedido());
            stmt.executeUpdate();
        }
    }

    public void delete(int idPedido) throws SQLException {
        String sql = "DELETE FROM pedidos WHERE id_pedido = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idPedido);
            stmt.executeUpdate();
        }
    }

    public List<PedidoModel> findAll() throws Exception {
        List<PedidoModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM pedidos ORDER BY id_pedido";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                PedidoModel p = new PedidoModel();
                p.setIdPedido(rs.getInt("id_pedido"));
                p.setIdComanda(rs.getInt("id_comanda"));
                p.setDataPedido(rs.getTimestamp("data_pedido"));
                p.setIdStatusPedido(rs.getInt("id_status_pedido"));
                lista.add(p);
            }
        }
        return lista;
    }
}

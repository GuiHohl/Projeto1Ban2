package dao;

import model.StatusPedidoModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StatusPedidoDAO {
    private final Connection connection;

    public StatusPedidoDAO(Connection connection) {
        this.connection = connection;
    }

    public List<StatusPedidoModel> findAll() throws Exception {
        List<StatusPedidoModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM status_pedido ORDER BY id_status_pedido";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                StatusPedidoModel sp = new StatusPedidoModel();
                sp.setId(rs.getInt("id_status_pedido"));
                sp.setNome(rs.getString("nome"));
                lista.add(sp);
            }
        }

        return lista;
    }

    public StatusPedidoModel findById(int id) throws Exception {
        String sql = "SELECT * FROM status_pedido WHERE id_status_pedido = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    StatusPedidoModel sp = new StatusPedidoModel();
                    sp.setId(rs.getInt("id_status_pedido"));
                    sp.setNome(rs.getString("nome"));
                    return sp;
                }
            }
        }
        return null;
    }
}

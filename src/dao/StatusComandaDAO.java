package dao;

import model.StatusComandaModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StatusComandaDAO {
    private final Connection connection;

    public StatusComandaDAO(Connection connection) {
        this.connection = connection;
    }

    public List<StatusComandaModel> findAll() throws Exception {
        List<StatusComandaModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM status_comanda ORDER BY id_status_comanda";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                StatusComandaModel sc = new StatusComandaModel();
                sc.setId(rs.getInt("id_status_comanda"));
                sc.setNome(rs.getString("nome"));
                lista.add(sc);
            }
        }

        return lista;
    }

    public StatusComandaModel findById(int id) throws Exception {
        String sql = "SELECT * FROM status_comanda WHERE id_status_comanda = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    StatusComandaModel sc = new StatusComandaModel();
                    sc.setId(rs.getInt("id_status_comanda"));
                    sc.setNome(rs.getString("nome"));
                    return sc;
                }
            }
        }
        return null;
    }
}

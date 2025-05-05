package dao;

import model.ComandaModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComandaDAO {
    private final Connection connection;

    public ComandaDAO(Connection connection) {
        this.connection = connection;
    }

    public void create(ComandaModel comanda) throws SQLException {
        String sql = "INSERT INTO comanda (id_funcionario, data_abertura, id_status_comanda, num_mesa) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, comanda.getIdFuncionario());
            stmt.setTimestamp(2, comanda.getDataAbertura());
            stmt.setInt(3, comanda.getIdStatusComanda());
            stmt.setInt(4, comanda.getNumMesa());
            stmt.executeUpdate();
        }
    }

    public ComandaModel read(int idComanda) throws SQLException {
        String sql = "SELECT * FROM comanda WHERE id_comanda = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idComanda);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                ComandaModel comanda = new ComandaModel();
                comanda.setIdComanda(rs.getInt("id_comanda"));
                comanda.setIdFuncionario(rs.getInt("id_funcionario"));
                comanda.setDataAbertura(rs.getTimestamp("data_abertura"));
                comanda.setIdStatusComanda(rs.getInt("id_status_comanda"));
                comanda.setNumMesa(rs.getInt("num_mesa"));
                return comanda;
            }
        }
        return null;
    }

    public void update(ComandaModel comanda) throws SQLException {
        String sql = "UPDATE comanda SET id_funcionario = ?, data_abertura = ?, id_status_comanda = ?, num_mesa = ? WHERE id_comanda = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, comanda.getIdFuncionario());
            stmt.setTimestamp(2, comanda.getDataAbertura());
            stmt.setInt(3, comanda.getIdStatusComanda());
            stmt.setInt(4, comanda.getNumMesa());
            stmt.setInt(5, comanda.getIdComanda());
            stmt.executeUpdate();
        }
    }

    public void delete(int idComanda) throws SQLException {
        String sql = "DELETE FROM comanda WHERE id_comanda = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idComanda);
            stmt.executeUpdate();
        }
    }

    public List<ComandaModel> findAll() throws Exception {
        List<ComandaModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM comanda ORDER BY id_comanda";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ComandaModel c = new ComandaModel();
                c.setIdComanda(rs.getInt("id_comanda"));
                c.setIdFuncionario(rs.getInt("id_funcionario"));
                c.setDataAbertura(rs.getTimestamp("data_abertura"));
                c.setIdStatusComanda(rs.getInt("id_status_comanda"));
                c.setNumMesa(rs.getInt("num_mesa"));

                lista.add(c);
            }
        }

        return lista;
    }
}

package dao;

import model.AdicionalModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdicionalDAO {
    private final Connection connection;

    public AdicionalDAO(Connection connection) {
        this.connection = connection;
    }

    public void create(AdicionalModel adicional) throws SQLException {
        String sql = "INSERT INTO adicionais (nome, preco) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, adicional.getNome());
            stmt.setBigDecimal(2, adicional.getPreco());
            stmt.executeUpdate();
        }
    }

    public AdicionalModel read(int idAdicional) throws SQLException {
        String sql = "SELECT * FROM adicionais WHERE id_adicional = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idAdicional);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                AdicionalModel adicional = new AdicionalModel();
                adicional.setIdAdicional(rs.getInt("id_adicional"));
                adicional.setNome(rs.getString("nome"));
                adicional.setPreco(rs.getBigDecimal("preco"));
                return adicional;
            }
        }
        return null;
    }

    public void update(AdicionalModel adicional) throws SQLException {
        String sql = "UPDATE adicionais SET nome = ?, preco = ? WHERE id_adicional = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, adicional.getNome());
            stmt.setBigDecimal(2, adicional.getPreco());
            stmt.setInt(3, adicional.getIdAdicional());
            stmt.executeUpdate();
        }
    }

    public void delete(int idAdicional) throws SQLException {
        String sql = "DELETE FROM adicionais WHERE id_adicional = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idAdicional);
            stmt.executeUpdate();
        }
    }

    public List<AdicionalModel> findAll() throws Exception {
        List<AdicionalModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM adicionais ORDER BY id_adicional";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                AdicionalModel a = new AdicionalModel();
                a.setIdAdicional(rs.getInt("id_adicional"));
                a.setNome(rs.getString("nome"));
                a.setPreco(rs.getBigDecimal("preco"));
                lista.add(a);
            }
        }

        return lista;
    }
}

package dao;

import model.CategoriaModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {
    private final Connection connection;

    public CategoriaDAO(Connection connection) {
        this.connection = connection;
    }

    public void create(CategoriaModel categoria) throws SQLException {
        String sql = "INSERT INTO categorias (nome) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, categoria.getNome());
            stmt.executeUpdate();
        }
    }

    public CategoriaModel read(int idCategoria) throws SQLException {
        String sql = "SELECT * FROM categorias WHERE id_categoria = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idCategoria);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                CategoriaModel categoria = new CategoriaModel();
                categoria.setIdCategoria(rs.getInt("id_categoria"));
                categoria.setNome(rs.getString("nome"));
                return categoria;
            }
        }
        return null;
    }

    public void update(CategoriaModel categoria) throws SQLException {
        String sql = "UPDATE categorias SET nome = ? WHERE id_categoria = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, categoria.getNome());
            stmt.setInt(2, categoria.getIdCategoria());
            stmt.executeUpdate();
        }
    }

    public void delete(int idCategoria) throws SQLException {
        String sql = "DELETE FROM categorias WHERE id_categoria = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idCategoria);
            stmt.executeUpdate();
        }
    }

    public List<CategoriaModel> findAll() throws Exception {
        List<CategoriaModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM categorias ORDER BY id_categoria";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                CategoriaModel c = new CategoriaModel();
                c.setIdCategoria(rs.getInt("id_categoria"));
                c.setNome(rs.getString("nome"));
                lista.add(c);
            }
        }

        return lista;
    }
}

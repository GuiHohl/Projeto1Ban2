package dao;

import dto.RelatorioFuncionariosComMaisComandasDTO;
import model.FuncionarioModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO {
    private final Connection connection;

    public FuncionarioDAO(Connection connection) {
        this.connection = connection;
    }

    public void create(FuncionarioModel funcionario) throws SQLException {
        String sql = "INSERT INTO funcionarios (nome, id_cargo) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, funcionario.getNome());
            stmt.setInt(2, funcionario.getIdCargo());
            stmt.executeUpdate();
        }
    }

    public FuncionarioModel read(int idFuncionario) throws SQLException {
        String sql = "SELECT * FROM funcionarios WHERE id_funcionario = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idFuncionario);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                FuncionarioModel funcionario = new FuncionarioModel();
                funcionario.setIdFuncionario(rs.getInt("id_funcionario"));
                funcionario.setNome(rs.getString("nome"));
                funcionario.setIdCargo(rs.getInt("id_cargo"));
                return funcionario;
            }
        }
        return null;
    }

    public void update(FuncionarioModel funcionario) throws SQLException {
        String sql = "UPDATE funcionarios SET nome = ?, id_cargo = ? WHERE id_funcionario = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, funcionario.getNome());
            stmt.setInt(2, funcionario.getIdCargo());
            stmt.setInt(3, funcionario.getIdFuncionario());
            stmt.executeUpdate();
        }
    }

    public void delete(int idFuncionario) throws SQLException {
        String sql = "DELETE FROM funcionarios WHERE id_funcionario = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idFuncionario);
            stmt.executeUpdate();
        }
    }

    public List<FuncionarioModel> findAll() throws SQLException {
        String sql = """
            SELECT f.id_funcionario, f.nome, f.id_cargo, c.nome AS cargo_nome
            FROM funcionarios f
            JOIN cargos c ON f.id_cargo = c.id_cargo
            ORDER BY f.id_funcionario
        """;

        List<FuncionarioModel> lista = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                FuncionarioModel f = new FuncionarioModel();
                f.setIdFuncionario(rs.getInt("id_funcionario"));
                f.setNome(rs.getString("nome"));
                f.setIdCargo(rs.getInt("id_cargo"));
                lista.add(f);
            }
        }
        return lista;
    }

    public List<RelatorioFuncionariosComMaisComandasDTO> findFuncionariosComMaisComandas() throws Exception {
        List<RelatorioFuncionariosComMaisComandasDTO> lista = new ArrayList<>();
        String sql = """
        SELECT f.nome, COUNT(c.id_comanda) AS total_comandas
        FROM funcionarios f
        JOIN comanda c ON c.id_funcionario = f.id_funcionario
        GROUP BY f.nome
        ORDER BY total_comandas DESC;
        """;

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                RelatorioFuncionariosComMaisComandasDTO dto = new RelatorioFuncionariosComMaisComandasDTO(
                    rs.getString("nome"),
                    rs.getInt("total_comandas")
                );
                lista.add(dto);
            }
        }

        return lista;
    }

}

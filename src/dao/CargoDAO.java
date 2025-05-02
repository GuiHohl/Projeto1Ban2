package dao;

import model.CargoModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CargoDAO {
    private final Connection connection;

    public CargoDAO(Connection connection) {
        this.connection = connection;
    }

    public void create(CargoModel cargo) throws SQLException {
        String sql = "INSERT INTO cargos (nome) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cargo.getNome());
            stmt.executeUpdate();
        }
    }

    public CargoModel read(int idCargo) throws SQLException {
        String sql = "SELECT * FROM cargos WHERE id_cargo = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idCargo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                CargoModel cargo = new CargoModel();
                cargo.setIdCargo(rs.getInt("id_cargo"));
                cargo.setNome(rs.getString("nome"));
                return cargo;
            }
        }
        return null;
    }

    public void update(CargoModel cargo) throws SQLException {
        String sql = "UPDATE cargos SET nome = ? WHERE id_cargo = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cargo.getNome());
            stmt.setInt(2, cargo.getIdCargo());
            stmt.executeUpdate();
        }
    }

    public void delete(int idCargo) throws SQLException {
        String sql = "DELETE FROM cargos WHERE id_cargo = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idCargo);
            stmt.executeUpdate();
        }
    }

    public List<CargoModel> findAll() throws SQLException {
        List<CargoModel> cargos = new ArrayList<>();
        String sql = "SELECT * FROM cargos ORDER BY id_cargo";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                CargoModel cargo = new CargoModel();
                cargo.setIdCargo(rs.getInt("id_cargo"));
                cargo.setNome(rs.getString("nome"));
                cargos.add(cargo);
            }
        }

        return cargos;
    }
}

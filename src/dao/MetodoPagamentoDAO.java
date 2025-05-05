package dao;

import model.MetodoPagamentoModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MetodoPagamentoDAO {
    private final Connection connection;

    public MetodoPagamentoDAO(Connection connection) {
        this.connection = connection;
    }

    public List<MetodoPagamentoModel> findAll() throws Exception {
        List<MetodoPagamentoModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM metodos_pagamento ORDER BY id_metodo_pagamento";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                MetodoPagamentoModel mp = new MetodoPagamentoModel();
                mp.setId(rs.getInt("id_metodo_pagamento"));
                mp.setNome(rs.getString("nome"));
                lista.add(mp);
            }
        }

        return lista;
    }

    public MetodoPagamentoModel findById(int id) throws Exception {
        String sql = "SELECT * FROM metodos_pagamento WHERE id_metodo_pagamento = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    MetodoPagamentoModel mp = new MetodoPagamentoModel();
                    mp.setId(rs.getInt("id_metodo_pagamento"));
                    mp.setNome(rs.getString("nome"));
                    return mp;
                }
            }
        }
        return null;
    }
}

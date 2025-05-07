package dao;

import dto.RelatorioFaturamentoDiarioDTO;
import dto.RelatorioVendasPorMetodoPagamentoDTO;
import model.PagamentoModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PagamentoDAO {
    private final Connection connection;

    public PagamentoDAO(Connection connection) {
        this.connection = connection;
    }

    public void create(PagamentoModel pagamento) throws SQLException {
        String sql = "INSERT INTO pagamentos (id_comanda, valor, id_metodo_pagamento, data_pagamento) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, pagamento.getIdComanda());
            stmt.setBigDecimal(2, pagamento.getValor());
            stmt.setInt(3, pagamento.getIdMetodoPagamento());
            stmt.setTimestamp(4, pagamento.getDataPagamento());
            stmt.executeUpdate();
        }
    }

    public PagamentoModel read(int idPagamento) throws SQLException {
        String sql = "SELECT * FROM pagamentos WHERE id_pagamento = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idPagamento);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                PagamentoModel pagamento = new PagamentoModel();
                pagamento.setIdPagamento(rs.getInt("id_pagamento"));
                pagamento.setIdComanda(rs.getInt("id_comanda"));
                pagamento.setValor(rs.getBigDecimal("valor"));
                pagamento.setIdMetodoPagamento(rs.getInt("id_metodo_pagamento"));
                pagamento.setDataPagamento(rs.getTimestamp("data_pagamento"));
                return pagamento;
            }
        }
        return null;
    }

    public void update(PagamentoModel pagamento) throws SQLException {
        String sql = "UPDATE pagamentos SET id_comanda = ?, valor = ?, id_metodo_pagamento = ?, data_pagamento = ? WHERE id_pagamento = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, pagamento.getIdComanda());
            stmt.setBigDecimal(2, pagamento.getValor());
            stmt.setInt(3, pagamento.getIdMetodoPagamento());
            stmt.setTimestamp(4, pagamento.getDataPagamento());
            stmt.setInt(5, pagamento.getIdPagamento());
            stmt.executeUpdate();
        }
    }

    public void delete(int idPagamento) throws SQLException {
        String sql = "DELETE FROM pagamentos WHERE id_pagamento = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idPagamento);
            stmt.executeUpdate();
        }
    }

    public List<PagamentoModel> findAll() throws Exception {
        List<PagamentoModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM pagamentos ORDER BY id_pagamento";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                PagamentoModel p = new PagamentoModel();
                p.setIdPagamento(rs.getInt("id_pagamento"));
                p.setIdComanda(rs.getInt("id_comanda"));
                p.setValor(rs.getBigDecimal("valor"));
                p.setIdMetodoPagamento(rs.getInt("id_metodo_pagamento"));
                p.setDataPagamento(rs.getTimestamp("data_pagamento"));
                lista.add(p);
            }
        }

        return lista;
    }

    public List<RelatorioFaturamentoDiarioDTO> findFaturamentoDiario() throws Exception {
        List<RelatorioFaturamentoDiarioDTO> lista = new ArrayList<>();
        String sql = """
            SELECT DATE(data_pagamento) AS data_pagamento,
                   COUNT(id_comanda) AS total_comandas,
                   SUM(valor) AS faturamento_total
            FROM pagamentos
            GROUP BY DATE(data_pagamento)
            ORDER BY data_pagamento DESC;
        """;

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                RelatorioFaturamentoDiarioDTO dto = new RelatorioFaturamentoDiarioDTO(
                        rs.getString("data_pagamento"),
                        rs.getInt("total_comandas"),
                        rs.getBigDecimal("faturamento_total")
                );
                lista.add(dto);
            }
        }

        return lista;
    }

    public List<RelatorioVendasPorMetodoPagamentoDTO> findVendasPorMetodo() throws Exception {
        List<RelatorioVendasPorMetodoPagamentoDTO> lista = new ArrayList<>();
        String sql = """
            SELECT mp.nome AS metodo_nome,
                   COUNT(p.id_pagamento) AS total_pagamentos,
                   SUM(p.valor) AS total_vendas
            FROM pagamentos p
            JOIN metodos_pagamento mp ON p.id_metodo_pagamento = mp.id_metodo_pagamento
            GROUP BY mp.nome
            ORDER BY total_vendas DESC;
        """;

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                RelatorioVendasPorMetodoPagamentoDTO dto = new RelatorioVendasPorMetodoPagamentoDTO(
                        rs.getString("metodo_nome"),
                        rs.getInt("total_pagamentos"),
                        rs.getBigDecimal("total_vendas")
                );
                lista.add(dto);
            }
        }

        return lista;
    }

}

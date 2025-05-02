package dao;

import dto.RelatorioProdutosMaisVendidosDTO;
import model.ProdutoModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {
    private final Connection connection;

    public ProdutoDAO(Connection connection) {
        this.connection = connection;
    }

    public void create(ProdutoModel produto) throws SQLException {
        String sql = "INSERT INTO produtos (id_categoria, nome, descrição, preco) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, produto.getIdCategoria());
            stmt.setString(2, produto.getNome());
            stmt.setString(3, produto.getDescricao());
            stmt.setBigDecimal(4, produto.getPreco());
            stmt.executeUpdate();
        }
    }

    public ProdutoModel read(int idProduto) throws SQLException {
        String sql = "SELECT * FROM produtos WHERE id_produto = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idProduto);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                ProdutoModel produto = new ProdutoModel();
                produto.setIdProduto(rs.getInt("id_produto"));
                produto.setIdCategoria(rs.getInt("id_categoria"));
                produto.setNome(rs.getString("nome"));
                produto.setDescricao(rs.getString("descrição"));
                produto.setPreco(rs.getBigDecimal("preco"));
                return produto;
            }
        }
        return null;
    }

    public void update(ProdutoModel produto) throws SQLException {
        String sql = "UPDATE produtos SET id_categoria = ?, nome = ?, descrição = ?, preco = ? WHERE id_produto = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, produto.getIdCategoria());
            stmt.setString(2, produto.getNome());
            stmt.setString(3, produto.getDescricao());
            stmt.setBigDecimal(4, produto.getPreco());
            stmt.setInt(5, produto.getIdProduto());
            stmt.executeUpdate();
        }
    }

    public void delete(int idProduto) throws SQLException {
        String sql = "DELETE FROM produtos WHERE id_produto = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idProduto);
            stmt.executeUpdate();
        }
    }

    public List<ProdutoModel> findAll() throws Exception {
        List<ProdutoModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM produtos ORDER BY id_produto";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ProdutoModel p = new ProdutoModel();
                p.setIdProduto(rs.getInt("id_produto"));
                p.setIdCategoria(rs.getInt("id_categoria"));
                p.setNome(rs.getString("nome"));
                p.setDescricao(rs.getString("descrição"));
                p.setPreco(rs.getBigDecimal("preco"));
                lista.add(p);
            }
        }

        return lista;
    }

    public List<RelatorioProdutosMaisVendidosDTO> findProdutosMaisVendidos() throws Exception {
        List<RelatorioProdutosMaisVendidosDTO> lista = new ArrayList<>();
        String sql = """
            SELECT pr.nome, COUNT(pp.id_produto) AS vezes_vendido, SUM(pr.preco) AS faturamento
            FROM produtos_pedidos pp
            JOIN produtos pr ON pp.id_produto = pr.id_produto
            GROUP BY pr.nome
            ORDER BY vezes_vendido DESC;
        """;

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                RelatorioProdutosMaisVendidosDTO dto = new RelatorioProdutosMaisVendidosDTO(
                    rs.getString("nome"),
                    rs.getInt("vezes_vendido"),
                    rs.getBigDecimal("faturamento")
                );
                lista.add(dto);
            }
        }

        return lista;
    }

}

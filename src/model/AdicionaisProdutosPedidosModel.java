package model;

public class AdicionaisProdutosPedidosModel {
    private int idAdicionalProdutoPedido;
    private int idProdutoPedido;
    private int idAdicional;
    private int qtdAdicional;

    public int getIdAdicionalProdutoPedido() {
        return idAdicionalProdutoPedido;
    }

    public void setIdAdicionalProdutoPedido(int idAdicionalProdutoPedido) {
        this.idAdicionalProdutoPedido = idAdicionalProdutoPedido;
    }

    public int getIdProdutoPedido() {
        return idProdutoPedido;
    }

    public void setIdProdutoPedido(int idProdutoPedido) {
        this.idProdutoPedido = idProdutoPedido;
    }

    public int getIdAdicional() {
        return idAdicional;
    }

    public void setIdAdicional(int idAdicional) {
        this.idAdicional = idAdicional;
    }

    public int getQtdAdicional() {
        return qtdAdicional;
    }

    public void setQtdAdicional(int qtdAdicional) {
        this.qtdAdicional = qtdAdicional;
    }
}
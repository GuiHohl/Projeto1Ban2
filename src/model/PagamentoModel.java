package model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class PagamentoModel {
    private int idPagamento;
    private int idComanda;
    private BigDecimal valor;
    private int idMetodoPagamento;
    private Timestamp dataPagamento;

    public int getIdPagamento() {
        return idPagamento;
    }

    public void setIdPagamento(int idPagamento) {
        this.idPagamento = idPagamento;
    }

    public int getIdComanda() {
        return idComanda;
    }

    public void setIdComanda(int idComanda) {
        this.idComanda = idComanda;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public int getIdMetodoPagamento() {
        return idMetodoPagamento;
    }

    public void setIdMetodoPagamento(int idMetodoPagamento) {
        this.idMetodoPagamento = idMetodoPagamento;
    }

    public Timestamp getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Timestamp dataPagamento) {
        this.dataPagamento = dataPagamento;
    }
}
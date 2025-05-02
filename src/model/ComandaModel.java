package model;

import java.sql.Timestamp;

public class ComandaModel {
    private int idComanda;
    private int idFuncionario;
    private Timestamp dataAbertura;
    private String statusComanda;
    private int numMesa;

    public int getIdComanda() {
        return idComanda;
    }

    public void setIdComanda(int idComanda) {
        this.idComanda = idComanda;
    }

    public int getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(int idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public Timestamp getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(Timestamp dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public String getStatusComanda() {
        return statusComanda;
    }

    public void setStatusComanda(String statusComanda) {
        this.statusComanda = statusComanda;
    }

    public int getNumMesa() {
        return numMesa;
    }

    public void setNumMesa(int numMesa) {
        this.numMesa = numMesa;
    }
}
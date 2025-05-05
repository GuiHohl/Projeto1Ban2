package model;

import java.sql.Timestamp;

public class ComandaModel {
    private int idComanda;
    private int idFuncionario;
    private Timestamp dataAbertura;
    private int idStatusComanda;
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

    public int getIdStatusComanda() {
        return idStatusComanda;
    }

    public void setIdStatusComanda(int idStatusComanda) {
        this.idStatusComanda = idStatusComanda;
    }

    public int getNumMesa() {
        return numMesa;
    }

    public void setNumMesa(int numMesa) {
        this.numMesa = numMesa;
    }
}
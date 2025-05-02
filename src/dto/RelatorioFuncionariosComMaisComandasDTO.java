package dto;

public class RelatorioFuncionariosComMaisComandasDTO {
    private String nomeFuncionario;
    private int totalComandas;

    public RelatorioFuncionariosComMaisComandasDTO(String nomeFuncionario, int totalComandas) {
        this.nomeFuncionario = nomeFuncionario;
        this.totalComandas = totalComandas;
    }

    // Getters e Setters
    public String getNomeFuncionario() {
        return nomeFuncionario;
    }

    public void setNomeFuncionario(String nomeFuncionario) {
        this.nomeFuncionario = nomeFuncionario;
    }

    public int getTotalComandas() {
        return totalComandas;
    }

    public void setTotalComandas(int totalComandas) {
        this.totalComandas = totalComandas;
    }

    @Override
    public String toString() {
        return String.format("Funcionário: %s | Comandas Atendidas: %d", nomeFuncionario, totalComandas);
    }
}


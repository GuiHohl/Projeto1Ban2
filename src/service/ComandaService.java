package service;



import dao.ComandaDAO;
import dao.FuncionarioDAO;
import model.ComandaModel;
import model.FuncionarioModel;

import java.sql.Connection;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class ComandaService {

    private final ComandaDAO comandaDAO;
    private final FuncionarioDAO funcionarioDAO;

    public ComandaService(Connection connection) {
        this.comandaDAO = new ComandaDAO(connection);
        this.funcionarioDAO = new FuncionarioDAO(connection);
    }

    public void criarComanda() {
        Scanner scanner = new Scanner(System.in);
        try {
            FuncionarioModel funcionario = selecionarFuncionario();
            if (funcionario == null) return;

            System.out.print("Número da mesa: ");
            int numMesa = Integer.parseInt(scanner.nextLine());

            ComandaModel comanda = new ComandaModel();
            comanda.setIdFuncionario(funcionario.getIdFuncionario());
            comanda.setDataAbertura(Timestamp.valueOf(LocalDateTime.now()));
            comanda.setStatusComanda("Aberta");
            comanda.setNumMesa(numMesa);

            comandaDAO.create(comanda);
            System.out.println("Comanda aberta com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao abrir comanda:");
            e.printStackTrace();
        }
    }

    public void listarComandas() {
        try {
            List<ComandaModel> lista = comandaDAO.findAll();
            System.out.println("\nComandas:");
            for (ComandaModel c : lista) {
                FuncionarioModel f = funcionarioDAO.read(c.getIdFuncionario());
                String nomeFuncionario = (f != null) ? f.getNome() : "Desconhecido";

                System.out.printf("ID: %d | Mesa: %d | Aberta por: %s | Status: %s | Abertura: %s%n",
                        c.getIdComanda(), c.getNumMesa(), nomeFuncionario,
                        c.getStatusComanda(), c.getDataAbertura());
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar comandas:");
            e.printStackTrace();
        }
    }

    public void cancelarComanda() {
        Scanner scanner = new Scanner(System.in);
        try {
            List<ComandaModel> lista = comandaDAO.findAll();
            if (lista.isEmpty()) {
                System.out.println("Nenhuma comanda encontrada.");
                return;
            }

            System.out.println("\nSelecione a comanda para cancelar:");
            for (int i = 0; i < lista.size(); i++) {
                ComandaModel c = lista.get(i);
                System.out.printf("%d - Comanda #%d (Mesa %d, Status: %s)%n",
                        i + 1, c.getIdComanda(), c.getNumMesa(), c.getStatusComanda());
            }

            System.out.print("Digite o número da comanda: ");
            int escolha = Integer.parseInt(scanner.nextLine());

            if (escolha < 1 || escolha > lista.size()) {
                System.out.println("Opção inválida.");
                return;
            }

            ComandaModel selecionada = lista.get(escolha - 1);
            selecionada.setStatusComanda("Cancelada");

            comandaDAO.update(selecionada);
            System.out.println("Comanda #" + selecionada.getIdComanda() + " foi cancelada.");

        } catch (Exception e) {
            System.out.println("Erro ao cancelar comanda:");
            e.printStackTrace();
        }
    }

    public void excluirComanda() {
        Scanner scanner = new Scanner(System.in);
        try {
            List<ComandaModel> lista = comandaDAO.findAll();
            if (lista.isEmpty()) {
                System.out.println("Nenhuma comanda para excluir.");
                return;
            }

            System.out.println("\nSelecione a comanda para excluir:");
            for (int i = 0; i < lista.size(); i++) {
                System.out.printf("%d - Comanda #%d (Mesa %d)%n",
                        i + 1, lista.get(i).getIdComanda(), lista.get(i).getNumMesa());
            }

            System.out.print("Digite o número da comanda: ");
            int escolha = Integer.parseInt(scanner.nextLine());

            if (escolha < 1 || escolha > lista.size()) {
                System.out.println("Opção inválida.");
                return;
            }

            comandaDAO.delete(lista.get(escolha - 1).getIdComanda());
            System.out.println("Comanda excluída com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro ao excluir comanda:");
            e.printStackTrace();
        }
    }

    private FuncionarioModel selecionarFuncionario() throws Exception {
        Scanner scanner = new Scanner(System.in);
        List<FuncionarioModel> lista = funcionarioDAO.findAll();
        if (lista.isEmpty()) {
            System.out.println("Nenhum funcionário disponível.");
            return null;
        }

        System.out.println("\nSelecione o funcionário que abrirá a comanda:");
        for (int i = 0; i < lista.size(); i++) {
            System.out.printf("%d - %s%n", i + 1, lista.get(i).getNome());
        }

        System.out.print("Digite o número: ");
        int escolha = Integer.parseInt(scanner.nextLine());

        if (escolha < 1 || escolha > lista.size()) {
            System.out.println("Opção inválida.");
            return null;
        }

        return lista.get(escolha - 1);
    }
}

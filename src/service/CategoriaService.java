package service;

import dao.CategoriaDAO;
import model.CategoriaModel;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

public class CategoriaService {

    private final CategoriaDAO categoriaDAO;

    public CategoriaService(Connection connection) {
        this.categoriaDAO = new CategoriaDAO(connection);
    }

    public void cadastrarCategoria() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("📦 Nome da categoria: ");
        String nome = scanner.nextLine();

        CategoriaModel categoria = new CategoriaModel();
        categoria.setNome(nome);

        try {
            categoriaDAO.create(categoria);
            System.out.println("Categoria cadastrada com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar categoria:");
            e.printStackTrace();
        }
    }

    public void listarCategorias() {
        try {
            List<CategoriaModel> lista = categoriaDAO.findAll();
            System.out.println("\nCategorias:");
            for (CategoriaModel c : lista) {
                System.out.printf("ID: %d | Nome: %s%n", c.getIdCategoria(), c.getNome());
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar categorias:");
            e.printStackTrace();
        }
    }

    public void editarCategoria() {
        Scanner scanner = new Scanner(System.in);
        try {
            List<CategoriaModel> lista = categoriaDAO.findAll();
            if (lista.isEmpty()) {
                System.out.println("Nenhuma categoria encontrada.");
                return;
            }

            System.out.println("\nSelecione uma categoria para editar:");
            for (int i = 0; i < lista.size(); i++) {
                System.out.printf("%d - %s%n", i + 1, lista.get(i).getNome());
            }

            System.out.print("Número da categoria: ");
            int escolha = Integer.parseInt(scanner.nextLine());

            if (escolha < 1 || escolha > lista.size()) {
                System.out.println("Opção inválida.");
                return;
            }

            CategoriaModel categoria = lista.get(escolha - 1);
            System.out.print("Novo nome para \"" + categoria.getNome() + "\": ");
            categoria.setNome(scanner.nextLine());

            categoriaDAO.update(categoria);
            System.out.println("Categoria atualizada!");

        } catch (Exception e) {
            System.out.println("Erro ao editar categoria:");
            e.printStackTrace();
        }
    }

    public void excluirCategoria() {
        Scanner scanner = new Scanner(System.in);
        try {
            List<CategoriaModel> lista = categoriaDAO.findAll();
            if (lista.isEmpty()) {
                System.out.println("Nenhuma categoria para excluir.");
                return;
            }

            System.out.println("\nSelecione a categoria a excluir:");
            for (int i = 0; i < lista.size(); i++) {
                System.out.printf("%d - %s%n", i + 1, lista.get(i).getNome());
            }

            System.out.print("Número da categoria: ");
            int escolha = Integer.parseInt(scanner.nextLine());

            if (escolha < 1 || escolha > lista.size()) {
                System.out.println("Opção inválida.");
                return;
            }

            categoriaDAO.delete(lista.get(escolha - 1).getIdCategoria());
            System.out.println("Categoria excluída!");

        } catch (Exception e) {
            System.out.println("Erro ao excluir categoria:");
            e.printStackTrace();
        }
    }
}

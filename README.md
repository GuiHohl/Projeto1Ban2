## **INSTRUÇÕES PARA COMPILAÇÃO E EXECUÇÃO DO SISTEMA DE GERENCIAMENTO DE RESTAURANTE**

Este sistema foi desenvolvido em Java com acesso a banco de dados PostgreSQL, utilizando JDBC e interface de texto via console. Abaixo estão as etapas necessárias para compilar e executar o projeto localmente.

---

### Pré-requisitos

- Java JDK 17 ou superior instalado.
- Uma IDE como IntelliJ IDEA ou Eclipse.
- Driver JDBC do PostgreSQL (postgresql-*.jar) adicionado ao classpath.

---

### Como adicionar o driver JDBC ao classpath

- Se estiver usando IntelliJ IDEA:
    - Clique com o botão direito no arquivo .jar > Add as Library.
    - Escolha Project Library e clique em OK.

- Se estiver usando Eclipse:
    - Clique com o botão direito no projeto > Properties
    - Vá em Java Build Path > aba Libraries
    - Clique em Add External JARs e selecione o .jar
    - Clique em Apply and Close

---

### Opções de Banco de Dados PostgreSQL

Opção 1 - O projeto já possui a url e parametros de acesso para um servidor PostgreSQL em nuvem, que contém já as tabelas criadas e dados inseridos.

Opção 2 - Caso queira criar um servidor local para rodar o programa, segue abaixo o passo a passo para cria-lo.

---

### Passo a Passo para Criar Banco de Dados Local

1. Abrir o pgAdmin

2. Criar um novo banco (Database)
    - No painel à esquerda: 
        - Expanda Servers > PostgreSQL (localhost).
        - Expanda Databases.
        - Clique com o botão direito em Databases → Create > Database.
    - Na tela que abrir:
        - Database name: projetoBan2 (ou o nome que quiser).
        - Owner: pode deixar como postgres.
        - Clique em Save.

3. Rode o Arquivo SQL de Backup no Banco Criado
    - Vá até Servers > PostgreSQL > Databases > projetoBan2 > Schemas
    - Clique com o botão direito no Schema public
    - Selecione Query Tool
    - Copie o conteúdo do arquivo de backup e cole no console
    - Execute todas as linhas

4. Verificar dados de conexão
    - Anote as seguintes informações:
        - Nome do banco: projetoBan2 (ou o nome que você escolheu)
        - User: postgres 
        - Password: a senha que você definiu ao instalar o PostgreSQL
        - Host: localhost
        - Port: 5432 (padrão)

5. Ajustar código Java
    - Na classe ConnectionFactory.java do pacote "db" ajuste os atributos abaixo:
        - URL = "jdbc:postgresql://localhost:5432/projetoBan2"; // altere os elementos do URL conforme você os definiu
        - USER = "postgres"; // o usuário que você selecionou
        - PASSWORD = "sua_senha"; // a senha que você criou para o PostgreSQL

---

### Compilação e Execução

- Abra e rode o arquivo Main.java em sua IDE.

- Em caso de erros:
    - Verifique se o driver JDBC foi adicionado corretamente ao projeto.
    - Caso esteja usando um banco de dados local, certifique-se de que os atributos de acesso foram inseridos corretamente.

---

## Desenvolvido por

- Emanuelle de Toledo Medeiros 
- Guilherme Weber Hohl

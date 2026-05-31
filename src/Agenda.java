import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Ponto de entrada da aplicação <b>Agenda Telefônica</b>.
 *
 * <p>Esta classe é responsável exclusivamente pela interação com o usuário
 * via terminal: exibe menus, lê entradas e delega toda a lógica de negócio
 * ao {@link AgendaService}.</p>
 *
 * <p><b>Execução:</b>
 * <pre>
 * javac -d bin src/Contato.java src/AgendaService.java src/Agenda.java
 * java  -cp bin Agenda
 * </pre>
 * </p>
 *
 * @author  mathdejesus
 * @version 2.0
 * @see     AgendaService
 * @see     Contato
 */
public class Agenda {

    // -------------------------------------------------------------------------
    // Constantes de apresentação
    // -------------------------------------------------------------------------

    /** Linha separadora exibida entre seções do menu. */
    private static final String LINHA =
        "--------------------------------------------------";

    // -------------------------------------------------------------------------
    // Ponto de entrada
    // -------------------------------------------------------------------------

    /**
     * Inicia a aplicação e mantém o loop principal do menu interativo.
     *
     * @param args argumentos de linha de comando (não utilizados)
     */
    public static void main(String[] args) {
        if (args.length == 0 || !"--cli".equals(args[0])) {
            AgendaService service = new AgendaService();
            EventQueue.invokeLater(() -> new AgendaGUI(service).setVisible(true));
            return;
        }
        startCLI();
    }

    private static void startCLI() {
        Scanner scanner       = new Scanner(System.in);
        AgendaService service = new AgendaService();

        System.out.println("Bem-vindo à Agenda Telefônica!");

        while (true) {
            mostraMenu();
            int opcao = lerOpcao(scanner);

            switch (opcao) {
                case 1 -> adicionarContato(service, scanner);
                case 2 -> listarContatos(service);
                case 3 -> procurarContato(service, scanner);
                case 4 -> procurarPorTelefone(service, scanner);
                case 5 -> editarContato(service, scanner);
                case 6 -> excluirContato(service, scanner);
                case 7 -> {
                    printLinha();
                    System.out.println("Encerrando a agenda. Até logo!");
                    printLinha();
                    scanner.close();
                    return;          // encerra sem System.exit() para permitir testes
                }
                default -> {
                    printLinha();
                    System.out.println("Opção inválida. Escolha entre 1 e 7.");
                    printLinha();
                }
            }
        }
    }

    // -------------------------------------------------------------------------
    // Operações do menu
    // -------------------------------------------------------------------------

    /**
     * Solicita ao usuário os dados de um novo contato e o adiciona à agenda.
     *
     * <p>Exibe mensagens de erro sem encerrar o programa quando a entrada
     * for inválida (campo vazio, e-mail mal formatado ou nome duplicado).</p>
     *
     * @param service instância de {@link AgendaService}
     * @param scanner leitor de entrada do usuário
     */
    public static void adicionarContato(AgendaService service, Scanner scanner) {
        printLinha();
        String nome = lerCampo(scanner, "Nome");

        List<String> telefones = new ArrayList<>();
        telefones.add(lerCampo(scanner, "Telefone 1"));
        int cont = 2;
        while (true) {
            System.out.print("Adicionar outro telefone? (s/n): ");
            String resp = scanner.nextLine().trim().toLowerCase();
            if (!"s".equals(resp)) break;
            telefones.add(lerCampo(scanner, "Telefone " + cont));
            cont++;
        }

        String email = lerCampo(scanner, "E-mail");

        try {
            Contato contato = new Contato(nome, telefones, email);
            service.adicionar(contato);
            printLinha();
            System.out.println("Contato adicionado com sucesso!");
        } catch (IllegalArgumentException | IllegalStateException e) {
            printLinha();
            System.out.println("Erro ao adicionar contato: " + e.getMessage());
        } finally {
            printLinha();
        }
    }

    /**
     * Lista todos os contatos da agenda em ordem alfabética.
     *
     * <p>Se a agenda estiver vazia, informa o usuário sem lançar exceção.</p>
     *
     * @param service instância de {@link AgendaService}
     */
    public static void listarContatos(AgendaService service) {
        printLinha();
        List<Contato> todos = service.listarTodos();

        if (todos.isEmpty()) {
            System.out.println("A agenda está vazia.");
            printLinha();
            return;
        }

        System.out.printf("%-3s contato(s) encontrado(s):%n", todos.size());
        printLinha();

        for (int i = 0; i < todos.size(); i++) {
            System.out.printf("[%d]%n", i + 1);
            System.out.println(todos.get(i));
            printLinha();
        }
    }

    /**
     * Solicita um trecho do nome e exibe todos os contatos que correspondam
     * à busca parcial, sem distinção de maiúsculas/minúsculas.
     *
     * @param service instância de {@link AgendaService}
     * @param scanner leitor de entrada do usuário
     */
    public static void procurarContato(AgendaService service, Scanner scanner) {
        printLinha();
        System.out.print("Digite o nome (ou parte dele): ");
        String trecho = scanner.nextLine().trim();

        if (trecho.isBlank()) {
            System.out.println("Termo de busca não pode ser vazio.");
            printLinha();
            return;
        }

        List<Contato> encontrados = service.buscarPorNome(trecho);

        printLinha();
        if (encontrados.isEmpty()) {
            System.out.println("Nenhum contato encontrado para: \"" + trecho + "\"");
        } else {
            System.out.printf("%d contato(s) encontrado(s):%n", encontrados.size());
            printLinha();
            for (Contato c : encontrados) {
                System.out.println(c);
                printLinha();
            }
        }
    }

    /**
     * Solicita um trecho do telefone e exibe todos os contatos que correspondam
     * à busca parcial.
     *
     * @param service instância de {@link AgendaService}
     * @param scanner leitor de entrada do usuário
     */
    public static void procurarPorTelefone(AgendaService service, Scanner scanner) {
        printLinha();
        System.out.print("Digite o telefone (ou parte dele): ");
        String trecho = scanner.nextLine().trim();

        if (trecho.isBlank()) {
            System.out.println("Termo de busca não pode ser vazio.");
            printLinha();
            return;
        }

        List<Contato> encontrados = service.buscarPorTelefone(trecho);

        printLinha();
        if (encontrados.isEmpty()) {
            System.out.println("Nenhum contato encontrado para: \"" + trecho + "\"");
        } else {
            System.out.printf("%d contato(s) encontrado(s):%n", encontrados.size());
            printLinha();
            for (Contato c : encontrados) {
                System.out.println(c);
                printLinha();
            }
        }
    }

    /**
     * Solicita o nome do contato a ser excluído e confirma a operação com
     * o usuário antes de remover.
     *
     * @param service instância de {@link AgendaService}
     * @param scanner leitor de entrada do usuário
     */
    public static void excluirContato(AgendaService service, Scanner scanner) {
        printLinha();
        System.out.print("Digite o nome do contato a excluir: ");
        String nome = scanner.nextLine().trim();

        if (nome.isBlank()) {
            System.out.println("Nome não pode ser vazio.");
            printLinha();
            return;
        }

        List<Contato> encontrados = service.buscarPorNome(nome);
        if (encontrados.isEmpty()) {
            printLinha();
            System.out.println("Contato não encontrado: \"" + nome + "\"");
            printLinha();
            return;
        }

        // Confirmação antes de excluir
        System.out.println("Contato a ser excluído:");
        System.out.println(encontrados.get(0));
        System.out.print("Confirmar exclusão? (s/n): ");
        String confirmacao = scanner.nextLine().trim().toLowerCase();

        printLinha();
        if ("s".equals(confirmacao)) {
            boolean removido = service.excluir(encontrados.get(0).getNome());
            System.out.println(removido
                ? "Contato excluído com sucesso!"
                : "Não foi possível excluir o contato."
            );
        } else {
            System.out.println("Exclusão cancelada.");
        }
        printLinha();
    }

    /**
     * Permite editar os dados de um contato existente.
     *
     * <p>Exibe os dados atuais do contato e permite que o usuário digite
     * novos valores. Campos deixados em branco mantêm o valor atual.</p>
     *
     * @param service instância de {@link AgendaService}
     * @param scanner leitor de entrada do usuário
     */
    public static void editarContato(AgendaService service, Scanner scanner) {
        printLinha();
        System.out.print("Digite o nome do contato a editar: ");
        String nome = scanner.nextLine().trim();

        if (nome.isBlank()) {
            System.out.println("Nome não pode ser vazio.");
            printLinha();
            return;
        }

        List<Contato> encontrados = service.buscarPorNome(nome);
        if (encontrados.isEmpty()) {
            System.out.println("Contato não encontrado: \"" + nome + "\"");
            printLinha();
            return;
        }

        Contato original = encontrados.get(0);
        System.out.println("Contato encontrado:");
        System.out.println(original);
        printLinha();

        System.out.println("Deixe em branco para manter o valor atual.");
        String novoNome  = lerCampoComPadrao(scanner, "Nome", original.getNome());

        List<String> telefonesOrig = original.getTelefones();
        List<String> novosTelefones = new ArrayList<>();
        for (int i = 0; i < telefonesOrig.size(); i++) {
            String val = lerCampoComPadrao(scanner, "Telefone " + (i + 1), telefonesOrig.get(i));
            novosTelefones.add(val);
        }
        while (true) {
            System.out.print("Adicionar outro telefone? (s/n): ");
            String resp = scanner.nextLine().trim().toLowerCase();
            if (!"s".equals(resp)) break;
            novosTelefones.add(lerCampo(
                scanner, "Telefone " + (novosTelefones.size() + 1)));
        }

        String novoEmail = lerCampoComPadrao(scanner, "E-mail", original.getEmail());

        boolean telefoneChanged = !novosTelefones.equals(telefonesOrig);
        if (novoNome.equals(original.getNome())
            && !telefoneChanged
            && novoEmail.equals(original.getEmail())) {
            System.out.println("Nenhuma alteração detectada.");
            printLinha();
            return;
        }

        System.out.println("Novos dados:");
        System.out.printf("Nome:      %s%n", novoNome);
        for (int i = 0; i < novosTelefones.size(); i++) {
            System.out.printf("%s%s%n", i == 0 ? "Telefone:  " : "           ", novosTelefones.get(i));
        }
        System.out.printf("E-mail:    %s%n", novoEmail);
        System.out.print("Confirmar alteração? (s/n): ");
        String confirmacao = scanner.nextLine().trim().toLowerCase();

        printLinha();
        if ("s".equals(confirmacao)) {
            try {
                Contato atualizado = new Contato(novoNome, novosTelefones, novoEmail);
                boolean editado = service.editar(original.getNome(), atualizado);
                System.out.println(editado
                    ? "Contato editado com sucesso!"
                    : "Não foi possível editar o contato."
                );
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Erro ao editar contato: " + e.getMessage());
            }
        } else {
            System.out.println("Edição cancelada.");
        }
        printLinha();
    }

    // -------------------------------------------------------------------------
    // Auxiliares de apresentação
    // -------------------------------------------------------------------------

    /**
     * Exibe o menu principal de opções no terminal.
     */
    public static void mostraMenu() {
        System.out.println();
        System.out.println("=== AGENDA TELEFÔNICA ===");
        System.out.println("1 - Adicionar Contato");
        System.out.println("2 - Listar Contatos");
        System.out.println("3 - Procurar Contato");
        System.out.println("4 - Buscar por Telefone");
        System.out.println("5 - Editar Contato");
        System.out.println("6 - Excluir Contato");
        System.out.println("7 - Sair");
        System.out.print("Escolha uma opção: ");
    }

    /**
     * Imprime uma linha separadora de 50 traços no terminal.
     */
    public static void printLinha() {
        System.out.println(LINHA);
    }

    /**
     * Lê uma opção numérica inteira do usuário, tratando entradas inválidas.
     *
     * <p>Em caso de entrada não numérica, descarta a linha e retorna {@code -1},
     * o que aciona o {@code default} do {@code switch} no chamador.</p>
     *
     * @param scanner leitor de entrada
     * @return inteiro digitado, ou {@code -1} se a entrada for inválida
     */
    private static int lerOpcao(Scanner scanner) {
        try {
            String linha = scanner.nextLine().trim();
            return Integer.parseInt(linha);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * Solicita ao usuário o preenchimento de um campo obrigatório,
     * repetindo o prompt enquanto a entrada estiver em branco.
     *
     * @param scanner leitor de entrada
     * @param campo   nome do campo exibido no prompt (ex.: {@code "Nome"})
     * @return valor digitado, nunca {@code null} nem vazio
     */
    private static String lerCampo(Scanner scanner, String campo) {
        String valor;
        do {
            System.out.print(campo + ": ");
            valor = scanner.nextLine().trim();
            if (valor.isBlank()) {
                System.out.println("  ⚠  " + campo + " não pode ser vazio. Tente novamente.");
            }
        } while (valor.isBlank());
        return valor;
    }

    /**
     * Lê um campo opcional exibindo o valor atual como padrão.
     *
     * <p>Se o usuário pressionar Enter sem digitar nada, retorna o
     * valor atual. Caso contrário, retorna o valor digitado.</p>
     *
     * @param scanner   leitor de entrada
     * @param campo     nome do campo exibido no prompt
     * @param valorAtual valor exibido como padrão
     * @return valor digitado ou o valor atual se a entrada for vazia
     */
    private static String lerCampoComPadrao(Scanner scanner, String campo, String valorAtual) {
        System.out.print(campo + " [" + valorAtual + "]: ");
        String valor = scanner.nextLine().trim();
        return valor.isBlank() ? valorAtual : valor;
    }
}
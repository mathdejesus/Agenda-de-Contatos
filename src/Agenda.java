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
                case 4 -> excluirContato(service, scanner);
                case 5 -> {
                    printLinha();
                    System.out.println("Encerrando a agenda. Até logo!");
                    printLinha();
                    scanner.close();
                    return;          // encerra sem System.exit() para permitir testes
                }
                default -> {
                    printLinha();
                    System.out.println("Opção inválida. Escolha entre 1 e 5.");
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
        String nome      = lerCampo(scanner, "Nome");
        String telefone  = lerCampo(scanner, "Telefone");
        String email     = lerCampo(scanner, "E-mail");

        try {
            Contato contato = new Contato(nome, telefone, email);
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
            boolean removido = service.excluir(nome);
            System.out.println(removido
                ? "Contato excluído com sucesso!"
                : "Não foi possível excluir o contato."
            );
        } else {
            System.out.println("Exclusão cancelada.");
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
        System.out.println("4 - Excluir Contato");
        System.out.println("5 - Sair");
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
}
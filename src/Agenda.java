import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Agenda {
    public static void main(String[] args) throws Exception {
      Scanner scanner = new Scanner(System.in);

      List<Contato> agenda = new ArrayList<Contato>();

      while (true){
        mostraMenu();
        int opcao = scanner.nextInt();
        scanner.nextLine();
        switch (opcao){
            case 1:
                adicionarContato(agenda, scanner);
                break;
            case 2:
                listarContato(agenda);
                break;
            case 3:
                procurarContato(agenda, scanner);
                break;
            case 4:
                excluirContato(agenda, scanner);
                break; 
            case 5:
                printLinha();
                System.out.println("Saindo...");
                printLinha();
                System.exit(0);
                return;
            default:
                printLinha();
                System.out.println("Opção inválida. Tente novamente.");
                printLinha();
                break;
        }
      }
    }
    public static void excluirContato(List<Contato> agenda, Scanner scanner){
        System.out.println("Digite o nome do contato que deseja excluir: ");
        String procurar = scanner.nextLine();

        boolean contatoEncontrado = false;

        for (int i = 0; i < agenda.size(); i++) {
            if (procurar.equals(agenda.get(i).getNome())) {
                agenda.remove(i);
                printLinha();
                System.out.println("Contato excluído!");
                printLinha();
                contatoEncontrado = true;
                break;
            }
        }
        if (!contatoEncontrado){
            printLinha();
            System.out.println("Contato não encontrado!");
        }
    }
    public static void procurarContato(List<Contato> agenda, Scanner scanner){
        System.out.println("Digite o nome do contato: ");
        String procurar = scanner.nextLine();

        boolean contatoEncontrado = false;

        for (int i = 0; i < agenda.size(); i++) {
            if (procurar.equals(agenda.get(i).getNome())) {
                printLinha();
                System.out.println("Contato encontrado");
                System.out.println(agenda.get(i).toString());
                printLinha();
                contatoEncontrado = true;
                break;
            }
        }
        if (!contatoEncontrado){
            printLinha();
            System.out.println("Contato não encontrado!");
        }
    }
    public static void listarContato(List<Contato> agenda){
        printLinha();
        for (int i = 0; i < agenda.size(); i++) {
            System.out.println("Contato: ");
            System.out.println(agenda.get(i).toString());
            printLinha();
        }
        printLinha();
        System.out.println("Contato adicionado com sucesso!");
        printLinha();
      }
    public static void adicionarContato(List<Contato> agenda, Scanner scanner){
        System.out.println("Digite o nome do contato:");
        String nome = scanner.nextLine();
        System.out.println("Digite o telefone do contato:");
        String telefone = scanner.nextLine();
        System.out.println("Digite o email do contato:");
        String email = scanner.nextLine();

        Contato contato = new Contato(nome, telefone, email);
        agenda.add(contato);
        printLinha();
        System.out.println("Contato adicionado com sucesso!");
        printLinha();

      }
      public static void mostraMenu(){
        System.out.println("1 - Adicionar Contato");
        System.out.println("2 - Listar Contatos");
        System.out.println("3 - Procurar Contato");
        System.out.println("4 - Excluir Contato");
        System.out.println("5 - Sair");
      }
      public static void printLinha(){
        System.out.println("--------------------------------------------------");
      }
    }

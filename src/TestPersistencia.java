import java.io.*;
import java.util.List;

/**
 * Testes para a persistência em arquivo da {@link AgendaService}.
 *
 * <p>Executar com a flag {@code -ea} (enable assertions):
 * <pre>
 * java -ea -cp bin TestPersistencia
 * </pre>
 * </p>
 */
public class TestPersistencia {

    static int falhas = 0;
    static final String ARQUIVO = "contatos.txt";

    public static void main(String[] args) {
        limparArquivo();
        testCarregarVazio();
        testSalvarECarregar();
        testCarregarMultiplosTelefones();
        testCarregarFormatoAntigo();
        testSalvarAposExcluir();
        limparArquivo();

        if (falhas == 0) {
            System.out.println("TestPersistencia: TODOS OS TESTES PASSARAM.");
        } else {
            System.out.println("TestPersistencia: " + falhas + " TESTE(S) FALHOU(FRAM).");
            System.exit(1);
        }
    }

    static void assertTrue(boolean cond, String msg) {
        if (!cond) {
            System.out.println("  FALHA: " + msg);
            falhas++;
        }
    }

    static void limparArquivo() {
        File f = new File(ARQUIVO);
        if (f.exists()) f.delete();
    }

    static void testCarregarVazio() {
        limparArquivo();
        AgendaService s = new AgendaService();
        assertTrue(s.total() == 0, "Sem arquivo, agenda deveria estar vazia");
        assertTrue(s.listarTodos().isEmpty(), "listarTodos() deveria ser vazio");
    }

    static void testSalvarECarregar() {
        limparArquivo();
        AgendaService s1 = new AgendaService();
        s1.adicionar(new Contato("João", "11912345678", "joao@email.com"));
        assertTrue(s1.total() == 1, "Deveria ter 1 contato");

        // Verificar que o arquivo foi criado
        File f = new File(ARQUIVO);
        assertTrue(f.exists(), "Arquivo " + ARQUIVO + " deveria existir");

        // Criar nova instância e verificar se carregou
        AgendaService s2 = new AgendaService();
        assertTrue(s2.total() == 1, "Nova instância deveria carregar 1 contato");
        List<Contato> lista = s2.listarTodos();
        assertTrue("João".equals(lista.get(0).getNome()),
            "Contato carregado deveria ser 'João'");

        limparArquivo();
    }

    static void testCarregarMultiplosTelefones() {
        limparArquivo();
        try (PrintWriter w = new PrintWriter(new FileWriter(ARQUIVO))) {
            w.println("Maria#21987654321;11999999999#maria@email.com");
        } catch (IOException e) {
            assertTrue(false, "Erro de I/O ao preparar arquivo: " + e.getMessage());
            return;
        }

        AgendaService s = new AgendaService();
        assertTrue(s.total() == 1, "Deveria carregar 1 contato");
        List<String> tels = s.listarTodos().get(0).getTelefones();
        assertTrue(tels.size() == 2, "Deveria ter 2 telefones");
        assertTrue("21987654321".equals(tels.get(0)), "Primeiro telefone errado");
        assertTrue("11999999999".equals(tels.get(1)), "Segundo telefone errado");

        limparArquivo();
    }

    static void testCarregarFormatoAntigo() {
        limparArquivo();
        try (PrintWriter w = new PrintWriter(new FileWriter(ARQUIVO))) {
            w.println("Ana#11911111111#ana@email.com");
        } catch (IOException e) {
            assertTrue(false, "Erro de I/O ao preparar arquivo: " + e.getMessage());
            return;
        }

        AgendaService s = new AgendaService();
        assertTrue(s.total() == 1, "Deveria carregar 1 contato no formato antigo");
        assertTrue("Ana".equals(s.listarTodos().get(0).getNome()),
            "Nome deveria ser 'Ana'");
        assertTrue(s.listarTodos().get(0).getTelefones().size() == 1,
            "Deveria ter 1 telefone");

        limparArquivo();
    }

    static void testSalvarAposExcluir() {
        limparArquivo();
        AgendaService s = new AgendaService();
        s.adicionar(new Contato("João", "11912345678", "joao@email.com"));
        s.adicionar(new Contato("Maria", "21987654321", "maria@email.com"));
        assertTrue(s.total() == 2, "Deveria ter 2 contatos");

        s.excluir("João");
        assertTrue(s.total() == 1, "Deveria ter 1 após excluir");

        // Carregar em nova instância
        AgendaService s2 = new AgendaService();
        assertTrue(s2.total() == 1, "Nova instância deveria ter 1 contato");
        assertTrue("Maria".equals(s2.listarTodos().get(0).getNome()),
            "Contato restante deveria ser Maria");

        limparArquivo();
    }
}

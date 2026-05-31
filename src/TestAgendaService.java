import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * Testes unitários para a classe {@link AgendaService}.
 *
 * <p>Executar com a flag {@code -ea} (enable assertions):
 * <pre>
 * java -ea -cp bin TestAgendaService
 * </pre>
 * </p>
 */
public class TestAgendaService {

    static int falhas = 0;

    public static void main(String[] args) {
        testAdicionar();
        testAdicionarDuplicata();
        testAdicionarNull();
        testListarVazio();
        testListarOrdenado();
        testBuscarPorNome();
        testBuscarPorNomeVazio();
        testBuscarPorTelefone();
        testBuscarPorTelefoneMultiplo();
        testExcluir();
        testExcluirInexistente();
        testEditar();
        testEditarNomeDuplicata();
        testExistePorNome();
        testTotal();

        if (falhas == 0) {
            System.out.println("TestAgendaService: TODOS OS TESTES PASSARAM.");
        } else {
            System.out.println("TestAgendaService: " + falhas + " TESTE(S) FALHOU(FRAM).");
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
        new File("contatos.txt").delete();
    }

    static AgendaService criarComContatos() {
        limparArquivo();
        AgendaService s = new AgendaService();
        s.adicionar(new Contato("Ana", "11911111111", "ana@email.com"));
        s.adicionar(new Contato("Carlos", "11933333333", "carlos@email.com"));
        s.adicionar(new Contato("Bia", "11922222222", "bia@email.com"));
        return s;
    }

    static void testAdicionar() {
        limparArquivo();
        AgendaService s = new AgendaService();
        s.adicionar(new Contato("João", "11912345678", "joao@email.com"));
        assertTrue(s.total() == 1, "Total deveria ser 1 após adicionar");
    }

    static void testAdicionarDuplicata() {
        limparArquivo();
        AgendaService s = new AgendaService();
        s.adicionar(new Contato("João", "11912345678", "joao@email.com"));
        try {
            s.adicionar(new Contato("João", "11987654321", "joao@outro.com"));
            assertTrue(false, "Deveria lançar IllegalStateException para nome duplicado");
        } catch (IllegalStateException e) {
            // esperado
        }
    }

    static void testAdicionarNull() {
        limparArquivo();
        AgendaService s = new AgendaService();
        try {
            s.adicionar(null);
            assertTrue(false, "Deveria lançar IllegalArgumentException para contato null");
        } catch (IllegalArgumentException e) {
            // esperado
        }
    }

    static void testListarVazio() {
        limparArquivo();
        AgendaService s = new AgendaService();
        assertTrue(s.listarTodos().isEmpty(), "Lista deveria estar vazia");
    }

    static void testListarOrdenado() {
        AgendaService s = criarComContatos();
        List<Contato> lista = s.listarTodos();
        assertTrue(lista.size() == 3, "Deveria ter 3 contatos");
        assertTrue("Ana".equals(lista.get(0).getNome()), "Primeiro deveria ser Ana");
        assertTrue("Bia".equals(lista.get(1).getNome()), "Segundo deveria ser Bia");
        assertTrue("Carlos".equals(lista.get(2).getNome()), "Terceiro deveria ser Carlos");
    }

    static void testBuscarPorNome() {
        AgendaService s = criarComContatos();
        List<Contato> res = s.buscarPorNome("a");
        assertTrue(res.size() == 3, "Busca por 'a' deveria encontrar 3 contatos");
        assertTrue("Ana".equals(res.get(0).getNome()), "Primeiro resultado deveria ser Ana");
    }

    static void testBuscarPorNomeVazio() {
        AgendaService s = criarComContatos();
        List<Contato> res = s.buscarPorNome("");
        assertTrue(res.size() == 3, "Busca por string vazia deveria retornar todos os contatos");
    }

    static void testBuscarPorTelefone() {
        AgendaService s = criarComContatos();
        List<Contato> res = s.buscarPorTelefone("2222");
        assertTrue(res.size() == 1, "Busca por '2222' deveria encontrar 1");
        assertTrue("Bia".equals(res.get(0).getNome()), "Deveria encontrar Bia");
    }

    static void testBuscarPorTelefoneMultiplo() {
        limparArquivo();
        AgendaService s = new AgendaService();
        s.adicionar(new Contato("João",
            Arrays.asList("11912345678", "21987654321"), "joao@email.com"));
        List<Contato> res = s.buscarPorTelefone("8765");
        assertTrue(res.size() == 1, "Busca por '8765' deveria encontrar João (segundo telefone)");
    }

    static void testExcluir() {
        AgendaService s = criarComContatos();
        boolean removido = s.excluir("Ana");
        assertTrue(removido, "excluir('Ana') deveria retornar true");
        assertTrue(s.total() == 2, "Total deveria ser 2 após excluir Ana");
    }

    static void testExcluirInexistente() {
        AgendaService s = criarComContatos();
        boolean removido = s.excluir("Fulano");
        assertTrue(!removido, "excluir('Fulano') deveria retornar false");
        assertTrue(s.total() == 3, "Total deveria permanecer 3");
    }

    static void testEditar() {
        AgendaService s = criarComContatos();
        Contato editado = new Contato("Ana Silva", "11999999999", "ana.silva@email.com");
        boolean result = s.editar("Ana", editado);
        assertTrue(result, "editar() deveria retornar true");
        List<Contato> res = s.buscarPorNome("Ana Silva");
        assertTrue(!res.isEmpty(), "Deveria encontrar 'Ana Silva'");
        assertTrue("11999999999".equals(res.get(0).getTelefone()),
            "Telefone deveria estar atualizado");
    }

    static void testEditarNomeDuplicata() {
        AgendaService s = criarComContatos();
        Contato editado = new Contato("Bia", "11999999999", "bia@novo.com");
        try {
            s.editar("Ana", editado);
            assertTrue(false, "Deveria lançar IllegalStateException para nome duplicado");
        } catch (IllegalStateException e) {
            // esperado
        }
    }

    static void testExistePorNome() {
        AgendaService s = criarComContatos();
        assertTrue(s.existePorNome("Ana"), "existePorNome('Ana') deveria ser true");
        assertTrue(!s.existePorNome("Fulano"), "existePorNome('Fulano') deveria ser false");
    }

    static void testTotal() {
        limparArquivo();
        AgendaService s = new AgendaService();
        assertTrue(s.total() == 0, "Total inicial deveria ser 0");
        s.adicionar(new Contato("João", "11912345678", "joao@email.com"));
        assertTrue(s.total() == 1, "Total deveria ser 1");
    }
}

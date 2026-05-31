import java.util.Arrays;
import java.util.List;

/**
 * Testes unitários para a classe {@link Contato}.
 *
 * <p>Executar com a flag {@code -ea} (enable assertions):
 * <pre>
 * java -ea -cp bin TestContato
 * </pre>
 * </p>
 */
public class TestContato {

    static int falhas = 0;

    public static void main(String[] args) {
        testCriacaoValida();
        testCriacaoComLista();
        testNomeVazio();
        testNomeNull();
        testTelefoneVazio();
        testTelefoneNull();
        testTelefoneCurto();
        testEmailVazio();
        testEmailNull();
        testEmailSemArroba();
        testEmailArrobaSemDominio();
        testEmailTldCurto();
        testEmailValido();
        testAddTelefone();
        testGetTelefones();
        testSetTelefones();
        testSetTelefonesVazio();
        testToString();

        if (falhas == 0) {
            System.out.println("TestContato: TODOS OS TESTES PASSARAM.");
        } else {
            System.out.println("TestContato: " + falhas + " TESTE(S) FALHOU(FRAM).");
            System.exit(1);
        }
    }

    static void assertTrue(boolean cond, String msg) {
        if (!cond) {
            System.out.println("  FALHA: " + msg);
            falhas++;
        }
    }

    static void testCriacaoValida() {
        Contato c = new Contato("João", "11912345678", "joao@email.com");
        assertTrue("João".equals(c.getNome()), "getNome() deveria retornar 'João'");
        assertTrue("11912345678".equals(c.getTelefone()), "getTelefone() deveria retornar '11912345678'");
        assertTrue("joao@email.com".equals(c.getEmail()), "getEmail() deveria retornar 'joao@email.com'");
        assertTrue(c.getTelefones().size() == 1, "getTelefones() deveria ter 1 elemento");
    }

    static void testCriacaoComLista() {
        List<String> tels = Arrays.asList("11912345678", "21987654321");
        Contato c = new Contato("Maria", tels, "maria@email.com");
        assertTrue(c.getTelefones().size() == 2, "Deveria ter 2 telefones");
        assertTrue("11912345678".equals(c.getTelefone()), "getTelefone() deveria ser o primeiro");
    }

    static void testNomeVazio() {
        try {
            new Contato("", "11912345678", "joao@email.com");
            assertTrue(false, "Deveria lançar IllegalArgumentException para nome vazio");
        } catch (IllegalArgumentException e) {
            // esperado
        }
    }

    static void testNomeNull() {
        try {
            new Contato(null, "11912345678", "joao@email.com");
            assertTrue(false, "Deveria lançar IllegalArgumentException para nome null");
        } catch (IllegalArgumentException e) {
            // esperado
        }
    }

    static void testTelefoneVazio() {
        try {
            new Contato("João", "", "joao@email.com");
            assertTrue(false, "Deveria lançar IllegalArgumentException para telefone vazio");
        } catch (IllegalArgumentException e) {
            // esperado
        }
    }

    static void testTelefoneNull() {
        try {
            new Contato("João", (String) null, "joao@email.com");
            assertTrue(false, "Deveria lançar IllegalArgumentException para telefone null");
        } catch (IllegalArgumentException e) {
            // esperado
        }
    }

    static void testTelefoneCurto() {
        try {
            new Contato("João", "1234567", "joao@email.com");
            assertTrue(false, "Deveria lançar IllegalArgumentException para < 8 dígitos");
        } catch (IllegalArgumentException e) {
            // esperado
        }
    }

    static void testEmailVazio() {
        try {
            new Contato("João", "11912345678", "");
            assertTrue(false, "Deveria lançar IllegalArgumentException para email vazio");
        } catch (IllegalArgumentException e) {
            // esperado
        }
    }

    static void testEmailNull() {
        try {
            new Contato("João", "11912345678", null);
            assertTrue(false, "Deveria lançar IllegalArgumentException para email null");
        } catch (IllegalArgumentException e) {
            // esperado
        }
    }

    static void testEmailSemArroba() {
        try {
            new Contato("João", "11912345678", "joaoemail.com");
            assertTrue(false, "Deveria rejeitar email sem @");
        } catch (IllegalArgumentException e) {
            // esperado
        }
    }

    static void testEmailArrobaSemDominio() {
        try {
            new Contato("João", "11912345678", "joao@");
            assertTrue(false, "Deveria rejeitar email sem domínio");
        } catch (IllegalArgumentException e) {
            // esperado
        }
    }

    static void testEmailTldCurto() {
        try {
            new Contato("João", "11912345678", "joao@email.c");
            assertTrue(false, "Deveria rejeitar email com TLD de 1 caractere");
        } catch (IllegalArgumentException e) {
            // esperado
        }
    }

    static void testEmailValido() {
        Contato c = new Contato("João", "11912345678", "user.name+tag@sub.domain.co");
        assertTrue("user.name+tag@sub.domain.co".equals(c.getEmail()),
            "Deveria aceitar email com pontos, + e subdomínio");
    }

    static void testAddTelefone() {
        Contato c = new Contato("João", "11912345678", "joao@email.com");
        c.addTelefone("21987654321");
        assertTrue(c.getTelefones().size() == 2, "Deveria ter 2 telefones após addTelefone()");
        assertTrue("21987654321".equals(c.getTelefones().get(1)),
            "Segundo telefone deveria ser o adicionado");
    }

    static void testGetTelefones() {
        Contato c = new Contato("João", "11912345678", "joao@email.com");
        try {
            c.getTelefones().add("outro");
            assertTrue(false, "getTelefones() deveria retornar lista imutável");
        } catch (UnsupportedOperationException e) {
            // esperado
        }
    }

    static void testSetTelefones() {
        Contato c = new Contato("João", "11912345678", "joao@email.com");
        c.setTelefones(Arrays.asList("11900000000", "21900000000"));
        assertTrue(c.getTelefones().size() == 2, "Deveria ter 2 telefones após setTelefones()");
        assertTrue("11900000000".equals(c.getTelefone()), "getTelefone() deveria ser o primeiro");
    }

    static void testSetTelefonesVazio() {
        Contato c = new Contato("João", "11912345678", "joao@email.com");
        try {
            c.setTelefones(Arrays.asList());
            assertTrue(false, "Deveria rejeitar lista vazia");
        } catch (IllegalArgumentException e) {
            // esperado
        }
    }

    static void testToString() {
        Contato c = new Contato("João", "11912345678", "joao@email.com");
        String s = c.toString();
        assertTrue(s.contains("João"), "toString() deveria conter o nome");
        assertTrue(s.contains("11912345678"), "toString() deveria conter o telefone");
        assertTrue(s.contains("joao@email.com"), "toString() deveria conter o email");
    }
}

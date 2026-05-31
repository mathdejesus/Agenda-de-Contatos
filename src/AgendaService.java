import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Serviço responsável pela lógica de negócio da agenda telefônica.
 *
 * <p>Encapsula a coleção de contatos e todas as operações sobre ela,
 * separando a lógica de negócio da camada de apresentação ({@link Agenda}).</p>
 *
 * <p><b>Exemplo de uso:</b>
 * <pre>{@code
 * AgendaService service = new AgendaService();
 * service.adicionar(new Contato("Ana", "(21) 99999-0000", "ana@email.com"));
 * service.buscarPorNome("ana").ifPresent(System.out::println);
 * }</pre>
 * </p>
 *
 * @author  mathdejesus
 * @version 2.0
 * @see     Contato
 */
public class AgendaService {

    /** Lista interna de contatos gerenciados por este serviço. */
    private final List<Contato> contatos = new ArrayList<>();

    // -------------------------------------------------------------------------
    // Operações CRUD
    // -------------------------------------------------------------------------

    /**
     * Adiciona um contato à agenda.
     *
     * @param contato contato a ser adicionado; não pode ser {@code null}
     * @throws IllegalArgumentException se {@code contato} for {@code null}
     * @throws IllegalStateException    se já existir um contato com o mesmo nome
     *                                  (comparação sem distinção de maiúsculas)
     */
    public void adicionar(Contato contato) {
        if (contato == null) {
            throw new IllegalArgumentException("Contato não pode ser nulo.");
        }
        if (existePorNome(contato.getNome())) {
            throw new IllegalStateException(
                "Já existe um contato com o nome: " + contato.getNome()
            );
        }
        contatos.add(contato);
    }

    /**
     * Retorna uma visão não modificável de todos os contatos em ordem
     * alfabética pelo nome.
     *
     * @return lista imutável de contatos ordenada alfabeticamente
     */
    public List<Contato> listarTodos() {
        List<Contato> ordenados = new ArrayList<>(contatos);
        ordenados.sort((a, b) ->
            a.getNome().compareToIgnoreCase(b.getNome())
        );
        return Collections.unmodifiableList(ordenados);
    }

    /**
     * Busca contatos cujo nome contenha o trecho informado,
     * sem distinção de maiúsculas/minúsculas.
     *
     * @param trecho parte do nome a pesquisar; não pode ser {@code null}
     * @return lista (possivelmente vazia) com os contatos correspondentes
     */
    public List<Contato> buscarPorNome(String trecho) {
        if (trecho == null) {
            throw new IllegalArgumentException("Termo de busca não pode ser nulo.");
        }
        List<Contato> resultado = new ArrayList<>();
        String termoBusca = trecho.trim().toLowerCase();
        for (Contato c : contatos) {
            if (c.getNome().toLowerCase().contains(termoBusca)) {
                resultado.add(c);
            }
        }
        resultado.sort((a, b) ->
            a.getNome().compareToIgnoreCase(b.getNome())
        );
        return resultado;
    }

    /**
     * Busca contatos cujo telefone contenha o trecho informado.
     *
     * @param trecho parte do telefone a pesquisar; nulo ou vazio retorna lista vazia
     * @return lista (possivelmente vazia) com os contatos correspondentes
     */
    public List<Contato> buscarPorTelefone(String trecho) {
        if (trecho == null || trecho.isBlank()) {
            return new ArrayList<>();
        }
        List<Contato> resultado = new ArrayList<>();
        String termoBusca = trecho.trim().toLowerCase();
        for (Contato c : contatos) {
            if (c.getTelefone().toLowerCase().contains(termoBusca)) {
                resultado.add(c);
            }
        }
        resultado.sort((a, b) ->
            a.getNome().compareToIgnoreCase(b.getNome())
        );
        return resultado;
    }

    /**
     * Remove o primeiro contato cujo nome corresponda exatamente ao
     * informado (sem distinção de maiúsculas/minúsculas).
     *
     * @param nome nome do contato a excluir
     * @return {@code true} se o contato foi encontrado e removido;
     *         {@code false} caso contrário
     */
    public boolean excluir(String nome) {
        if (nome == null || nome.isBlank()) return false;
        for (int i = 0; i < contatos.size(); i++) {
            if (contatos.get(i).getNome().equalsIgnoreCase(nome.trim())) {
                contatos.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Verifica se há algum contato cadastrado com o nome informado,
     * sem distinção de maiúsculas/minúsculas.
     *
     * @param nome nome a verificar
     * @return {@code true} se existir um contato com esse nome
     */
    public boolean existePorNome(String nome) {
        if (nome == null) return false;
        String nomeBusca = nome.trim().toLowerCase();
        for (Contato c : contatos) {
            if (c.getNome().toLowerCase().equals(nomeBusca)) return true;
        }
        return false;
    }

    /**
     * Retorna a quantidade de contatos na agenda.
     *
     * @return número de contatos cadastrados
     */
    public int total() {
        return contatos.size();
    }
}
import java.io.*;
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

    /** Caminho do arquivo de persistência dos contatos. */
    private static final String ARQUIVO = "contatos.txt";

    /**
     * Cria um serviço de agenda e carrega os contatos salvos anteriormente.
     */
    public AgendaService() {
        loadFromFile();
    }

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
        saveToFile();
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
            for (String tel : c.getTelefones()) {
                if (tel.toLowerCase().contains(termoBusca)) {
                    resultado.add(c);
                    break;
                }
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
                saveToFile();
                return true;
            }
        }
        return false;
    }

    /**
     * Substitui os dados de um contato identificado pelo nome original.
     *
     * @param nomeOriginal nome exato (case-insensitive) do contato a editar
     * @param atualizado   novo {@link Contato} com os dados atualizados
     * @return {@code true} se o contato foi encontrado e atualizado
     * @throws IllegalArgumentException se algum parâmetro for inválido
     * @throws IllegalStateException    se o novo nome já pertencer a outro contato
     */
    public boolean editar(String nomeOriginal, Contato atualizado) {
        if (nomeOriginal == null || nomeOriginal.isBlank()) {
            throw new IllegalArgumentException("Nome original não pode ser vazio.");
        }
        if (atualizado == null) {
            throw new IllegalArgumentException("Contato atualizado não pode ser nulo.");
        }

        int index = -1;
        for (int i = 0; i < contatos.size(); i++) {
            if (contatos.get(i).getNome().equalsIgnoreCase(nomeOriginal.trim())) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            return false;
        }

        // Se o nome foi alterado, verificar duplicata com outros contatos
        if (!atualizado.getNome().equalsIgnoreCase(nomeOriginal.trim())
            && existePorNome(atualizado.getNome())) {
            throw new IllegalStateException(
                "Já existe um contato com o nome: " + atualizado.getNome()
            );
        }

        contatos.set(index, atualizado);
        saveToFile();
        return true;
    }

    // -------------------------------------------------------------------------
    // Persistência
    // -------------------------------------------------------------------------

    /**
     * Salva todos os contatos no arquivo {@link #ARQUIVO} no formato
     * {@code nome#telefone#email}, um por linha.
     *
     * <p>Erros de I/O são logados no stderr sem interromper a execução.</p>
     */
    private void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ARQUIVO))) {
            for (Contato c : contatos) {
                String telefonesStr = String.join(";", c.getTelefones());
                writer.println(c.getNome() + "#" + telefonesStr + "#" + c.getEmail());
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar contatos: " + e.getMessage());
        }
    }

    /**
     * Carrega os contatos do arquivo {@link #ARQUIVO}.
     *
     * <p>Se o arquivo não existir, a lista permanece vazia. Linhas
     * inválidas são ignoradas com aviso no stderr.</p>
     */
    private void loadFromFile() {
        File arquivo = new File(ARQUIVO);
        if (!arquivo.exists()) {
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                linha = linha.trim();
                if (linha.isBlank()) continue;
                String[] partes = linha.split("#", 3);
                if (partes.length < 3) {
                    System.err.println("Ignorando linha (formato inválido): " + linha);
                    continue;
                }
                try {
                    String[] telefonesArray = partes[1].split(";", -1);
                    List<String> telefonesList = new ArrayList<>();
                    for (String tel : telefonesArray) {
                        String t = tel.trim();
                        if (!t.isEmpty()) telefonesList.add(t);
                    }
                    contatos.add(new Contato(partes[0], telefonesList, partes[2]));
                } catch (IllegalArgumentException e) {
                    System.err.println("Ignorando linha (dados inválidos): " + linha);
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar contatos: " + e.getMessage());
        }
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
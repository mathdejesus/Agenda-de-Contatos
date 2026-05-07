/**
 * Representa um contato da agenda telefônica.
 *
 * <p>Armazena nome, telefone e e-mail de uma pessoa e fornece
 * métodos de acesso (getters/setters) e uma representação textual
 * legível via {@link #toString()}.</p>
 *
 * @author  mathdejesus
 * @version 2.0
 */
public class Contato {

    /** Nome completo do contato. */
    private String nome;

    /** Número de telefone do contato (ex.: {@code (11) 91234-5678}). */
    private String telefone;

    /** Endereço de e-mail do contato. */
    private String email;

    // -------------------------------------------------------------------------
    // Construtor
    // -------------------------------------------------------------------------

    /**
     * Cria um novo contato com os dados fornecidos.
     *
     * @param nome      nome completo; não deve ser {@code null} nem vazio
     * @param telefone  número de telefone; não deve ser {@code null} nem vazio
     * @param email     endereço de e-mail; não deve ser {@code null} nem vazio
     * @throws IllegalArgumentException se qualquer parâmetro for {@code null} ou em branco
     */
    public Contato(String nome, String telefone, String email) {
        setNome(nome);
        setTelefone(telefone);
        setEmail(email);
    }

    // -------------------------------------------------------------------------
    // Getters e Setters
    // -------------------------------------------------------------------------

    /**
     * Retorna o nome do contato.
     *
     * @return nome do contato
     */
    public String getNome() {
        return nome;
    }

    /**
     * Atualiza o nome do contato.
     *
     * @param nome novo nome; não pode ser {@code null} nem vazio
     * @throws IllegalArgumentException se {@code nome} for {@code null} ou em branco
     */
    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome não pode ser vazio.");
        }
        this.nome = nome.trim();
    }

    /**
     * Retorna o telefone do contato.
     *
     * @return número de telefone
     */
    public String getTelefone() {
        return telefone;
    }

    /**
     * Atualiza o telefone do contato.
     *
     * @param telefone novo número; não pode ser {@code null} nem vazio
     * @throws IllegalArgumentException se {@code telefone} for {@code null} ou em branco
     */
    public void setTelefone(String telefone) {
        if (telefone == null || telefone.isBlank()) {
            throw new IllegalArgumentException("Telefone não pode ser vazio.");
        }
        this.telefone = telefone.trim();
    }

    /**
     * Retorna o e-mail do contato.
     *
     * @return endereço de e-mail
     */
    public String getEmail() {
        return email;
    }

    /**
     * Atualiza o e-mail do contato.
     *
     * <p>Verifica se o valor contém {@code @} e pelo menos um {@code .}
     * após o arroba como validação mínima de formato.</p>
     *
     * @param email novo e-mail; não pode ser {@code null} nem vazio
     * @throws IllegalArgumentException se {@code email} for inválido
     */
    public void setEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("E-mail não pode ser vazio.");
        }
        String trimmed = email.trim();
        int arrobaIdx = trimmed.indexOf('@');
        if (arrobaIdx < 1 || trimmed.indexOf('.', arrobaIdx) < 0) {
            throw new IllegalArgumentException("E-mail inválido: " + trimmed);
        }
        this.email = trimmed;
    }

    // -------------------------------------------------------------------------
    // Object
    // -------------------------------------------------------------------------

    /**
     * Retorna uma representação textual do contato.
     *
     * <p>Formato:
     * <pre>
     * Nome:      João da Silva
     * Telefone:  (11) 91234-5678
     * E-mail:    joao@email.com
     * </pre>
     * </p>
     *
     * @return string com todos os campos do contato
     */
    @Override
    public String toString() {
        return String.format(
            "Nome:      %s%nTelefone:  %s%nE-mail:    %s",
            nome, telefone, email
        );
    }
}
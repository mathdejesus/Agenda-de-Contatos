import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class Contato {

    private static final Pattern EMAIL_PATTERN =
        Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

    private String nome;

    private List<String> telefones = new ArrayList<>();

    private String email;

    // -------------------------------------------------------------------------
    // Construtores
    // -------------------------------------------------------------------------

    public Contato(String nome, String telefone, String email) {
        setNome(nome);
        setTelefone(telefone);
        setEmail(email);
    }

    public Contato(String nome, List<String> telefones, String email) {
        setNome(nome);
        setTelefones(telefones);
        setEmail(email);
    }

    // -------------------------------------------------------------------------
    // Getters e Setters
    // -------------------------------------------------------------------------

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome não pode ser vazio.");
        }
        this.nome = nome.trim();
    }

    public String getTelefone() {
        return telefones.isEmpty() ? "" : telefones.get(0);
    }

    public List<String> getTelefones() {
        return Collections.unmodifiableList(telefones);
    }

    public void setTelefone(String telefone) {
        if (telefone == null || telefone.isBlank()) {
            throw new IllegalArgumentException("Telefone não pode ser vazio.");
        }
        String trimmed = telefone.trim();
        validarTelefone(trimmed);
        this.telefones = new ArrayList<>();
        this.telefones.add(trimmed);
    }

    public void setTelefones(List<String> telefones) {
        if (telefones == null || telefones.isEmpty()) {
            throw new IllegalArgumentException("Deve haver ao menos um telefone.");
        }
        List<String> validadas = new ArrayList<>();
        for (String tel : telefones) {
            if (tel == null || tel.isBlank()) continue;
            String trimmed = tel.trim();
            validarTelefone(trimmed);
            validadas.add(trimmed);
        }
        if (validadas.isEmpty()) {
            throw new IllegalArgumentException("Deve haver ao menos um telefone válido.");
        }
        this.telefones = validadas;
    }

    public void addTelefone(String telefone) {
        if (telefone == null || telefone.isBlank()) {
            throw new IllegalArgumentException("Telefone não pode ser vazio.");
        }
        String trimmed = telefone.trim();
        validarTelefone(trimmed);
        telefones.add(trimmed);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("E-mail não pode ser vazio.");
        }
        String trimmed = email.trim();
        if (!EMAIL_PATTERN.matcher(trimmed).matches()) {
            throw new IllegalArgumentException("E-mail inválido: " + trimmed);
        }
        this.email = trimmed;
    }

    // -------------------------------------------------------------------------
    // Validação
    // -------------------------------------------------------------------------

    private static void validarTelefone(String telefone) {
        long digitCount = telefone.chars().filter(Character::isDigit).count();
        if (digitCount < 8) {
            throw new IllegalArgumentException(
                "Telefone inválido: " + telefone + " — deve conter ao menos 8 dígitos."
            );
        }
    }

    // -------------------------------------------------------------------------
    // Object
    // -------------------------------------------------------------------------

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Nome:      ").append(nome).append(System.lineSeparator());
        for (int i = 0; i < telefones.size(); i++) {
            sb.append(i == 0 ? "Telefone:  " : "           ")
              .append(telefones.get(i))
              .append(System.lineSeparator());
        }
        sb.append("E-mail:    ").append(email);
        return sb.toString();
    }
}
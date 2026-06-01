# Agenda-de-Contatos

Java GUI/CLI phonebook CRUD. Pure Java SE (11+), no external deps, no build tool.

## Context Navigation
- Query knowledge graph for codebase understanding: `/graphify query "your question"`
- Browse `graphify-out/` as navigation entrypoint for structure

## Build & Run
```bash
javac -d bin src/*.java
java -cp bin Agenda           # GUI (default)
java -cp bin Agenda --cli     # terminal
```
- `src/` — 7 `.java` files (4 main + 3 test) in **default package** (no `package` declarations)
- `bin/` — compiled `.class` files, committed to git
- Entrypoint: `Agenda.main(String[])` — no args → GUI (`AgendaGUI`, Swing); `--cli` → CLI loop

## Tests
Three test classes using Java `assert` (run with `-ea`):
```bash
javac -d bin src/*.java
java -ea -cp bin TestContato
java -ea -cp bin TestAgendaService
java -ea -cp bin TestPersistencia
# All three (each exits 1 on failure via System.exit):
java -ea -cp bin TestContato && java -ea -cp bin TestAgendaService && java -ea -cp bin TestPersistencia
```
- Tests create/delete `contatos.txt`; run from project root

## Conventions
- Javadoc on all public methods (English); README in Portuguese
- Persistence: `contatos.txt` at project root, format `nome#tel1;tel2#email` per line, NOT tracked in git
- Default package — never add `package` declarations
- `.idea/` files tracked; avoid modifying unless necessary
- `bin/` `.class` files committed; recompile and stage `bin/` when source changes
- Only `Agenda.java` avoids `System.exit()` (exits via `return`); test classes use `System.exit(1)` on failure
- IntelliJ configured for JDK 21; source uses Java 11+ features (`isBlank()`, `trim()`)

# Agenda-de-Contatos

Java GUI/CLI phonebook CRUD. Pure Java SE (8+), no external deps, no build tool.

## Build & Run
```bash
javac -d bin src/*.java
java -cp bin Agenda           # GUI (default)
java -cp bin Agenda --cli     # terminal
```
- `src/` — 4 files in **default package** (no `package` declarations)
- `bin/` — compiled `.class` files, committed to git
- Entrypoint: `Agenda.main(String[])` — dispatches to GUI or CLI via `--cli` arg

## Tests
Three test classes using Java `assert` (run with `-ea`):
```bash
javac -d bin src/*.java
java -ea -cp bin TestContato
java -ea -cp bin TestAgendaService
java -ea -cp bin TestPersistencia
java -ea -cp bin TestContato && java -ea -cp bin TestAgendaService && java -ea -cp bin TestPersistencia
```

## Conventions
- Code comments and Javadoc in English; README in Portuguese
- `.gitignore` — project-specific entry: `/out/` (IntelliJ); also generic ignores (`.env`, `*.key`, etc.)
- Javadoc on all public methods

## Agent constraints
- A runtime file `contatos.txt` is created at project root for persistence; it is NOT tracked in git (listed in `.gitignore`)
- Default package — never add `package` declarations
- `.idea/` files are tracked; avoid modifying them unless necessary
- `bin/` `.class` files committed to git; recompile (`javac -d bin ...`) and stage `bin/` when source changes
- `System.exit()` not used; main loop exits via `return` from `main`
- De facto Java 11+ (`isBlank()`, `trim()`); IntelliJ configured for JDK 21

## Workflow hints
- `brainstorming` before creative work → `writing-plans` → `tdd` → `verification-before-completion`
- `systematic-debugging` for bugs

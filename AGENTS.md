# Agenda-de-Contatos

Java CLI phonebook CRUD. Pure Java SE (8+), no external deps, no build tool.

## Build & Run
```bash
javac -d bin src/Contato.java src/AgendaService.java src/Agenda.java
java -cp bin Agenda
```
Dependency order: `Contato` → `AgendaService` → `Agenda`. No Makefile/Maven/Gradle.

## Structure
- `src/` — 3 files in **default package** (no `package` declarations)
- `bin/` — compiled `.class` files, committed to git
- Entrypoint: `Agenda.main(String[])`

## Tests
None. No test framework or directory.

## Conventions
- Code comments and Javadoc in English; README in Portuguese
- `.gitignore` — project-specific entry: `/out/` (IntelliJ); also generic ignores (`.env`, `*.key`, etc.)
- Javadoc on all public methods

## Skills workflow
- `brainstorming` → `writing-plans` → `tdd` → `verification-before-completion`
- `systematic-debugging` for bugs
- `caveman` / `zoom-out` for communication

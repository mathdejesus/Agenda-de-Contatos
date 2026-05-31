# 📒 Agenda de Contatos

Uma aplicação de **agenda telefônica** desenvolvida em **Java puro**, executada inteiramente via terminal. Permite adicionar, listar, buscar e excluir contatos de forma simples e interativa, sem dependências externas.

---

## 📋 Sumário

- [Sobre o Projeto](#sobre-o-projeto)
- [Funcionalidades](#funcionalidades)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Diagrama de Classes](#diagrama-de-classes)
- [Pré-requisitos](#pré-requisitos)
- [Como Executar](#como-executar)
- [Exemplo de Uso](#exemplo-de-uso)
- [Documentação da API](#documentação-da-api)
- [Limitações Conhecidas](#limitações-conhecidas)
- [Melhorias Futuras](#melhorias-futuras)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Autor](#autor)
- [Licença](#licença)

---

## Sobre o Projeto

O **Agenda de Contatos** é um projeto de console desenvolvido em Java com o objetivo de praticar os conceitos fundamentais da linguagem, incluindo:

- Orientação a Objetos (classes, encapsulamento, getters/setters)
- Separação de responsabilidades (camada de apresentação × camada de serviço)
- Coleções (`ArrayList`, `List`, `Collections`)
- Leitura de entrada do usuário com `Scanner`
- Estruturas de controle (`while`, `switch`, `for`)
- Tratamento de exceções (`try/catch/finally`, exceções customizadas)
- Javadoc e documentação de código

---

## Funcionalidades

| # | Opção no Menu      | Descrição                                                              |
|---|--------------------|------------------------------------------------------------------------|
| 1 | Adicionar Contato  | Cadastra um novo contato (nome, telefone e e-mail) com validação       |
| 2 | Listar Contatos    | Exibe todos os contatos em ordem alfabética                            |
| 3 | Procurar Contato   | Busca parcial e case-insensitive pelo nome                             |
| 4 | Buscar por Telefone| Busca parcial pelo telefone                                            |
| 5 | Excluir Contato    | Remove um contato com confirmação prévia                               |
| 6 | Sair               | Encerra a aplicação                                                    |

### Destaques

- ✅ **Validação de e-mail** — rejeita endereços sem `@`, sem domínio, ou com TLD inválido
- ✅ **Validação de telefone** — rejeita números com menos de 8 dígitos
- ✅ **Múltiplos telefones** — cada contato pode ter quantos telefones desejar
- ✅ **Interface gráfica** — Swing, com tabela, busca e diálogos de formulário
- ✅ **Busca por nome ou telefone** — busca parcial e case-insensitive em ambos os campos
- ✅ **Prevenção de duplicatas** — impede cadastro de dois contatos com o mesmo nome
- ✅ **Confirmação antes de excluir** — exibe o contato e aguarda `s/n`
- ✅ **Lista ordenada alfabeticamente** — resultados sempre consistentes
- ✅ **Persistência em arquivo** — contatos salvos automaticamente em `contatos.txt`
- ✅ **Separação de responsabilidades** — lógica de negócio isolada em `AgendaService`

---

## Estrutura do Projeto

```
Agenda-de-Contatos/
├── .idea/                   # Configurações do IntelliJ IDEA (ignorar)
├── bin/                     # Bytecode compilado (.class)
│   ├── Agenda.class
│   ├── AgendaService.class
│   └── Contato.class
├── src/                     # Código-fonte Java
│   ├── Agenda.java          # Camada de apresentação — entrypoint (GUI ou --cli)
│   ├── AgendaGUI.java       # Interface gráfica Swing
│   ├── AgendaService.java   # Camada de serviço — lógica de negócio e coleção de contatos
│   └── Contato.java         # Modelo de dados — representa um contato
├── .gitignore
├── Agenda-Telefonica.iml    # Arquivo de módulo do IntelliJ IDEA
└── README.md
```

---

## Diagrama de Classes

```
┌──────────────────────────────────────────────────┐
│                    Contato                        │
├──────────────────────────────────────────────────┤
│ - nome     : String                              │
│ - telefone : String                              │
│ - email    : String                              │
├──────────────────────────────────────────────────┤
│ + Contato(nome, telefone, email)                 │
│ + getNome()        : String                      │
│ + setNome(String)  : void                        │
│ + getTelefone()    : String                      │
│ + setTelefone(String) : void                     │
│ + getEmail()       : String                      │
│ + setEmail(String) : void                        │
│ + toString()       : String                      │
└──────────────────────────────────────────────────┘
                        ▲
                        │ gerencia
┌──────────────────────────────────────────────────┐
│                 AgendaService                     │
├──────────────────────────────────────────────────┤
│ - contatos : List<Contato>                       │
├──────────────────────────────────────────────────┤
│ + adicionar(Contato)            : void               │
│ + listarTodos()                 : List<Contato>      │
│ + buscarPorNome(String)         : List<Contato>      │
│ + buscarPorTelefone(String)     : List<Contato>      │
│ + excluir(String)               : boolean            │
│ + existePorNome(String)         : boolean            │
│ + total()                       : int                │
└──────────────────────────────────────────────────┘
                        ▲
                        │ usa
┌──────────────────────────────────────────────────┐
│                    Agenda                         │
├──────────────────────────────────────────────────┤
│ - LINHA : String  (constante)                    │
├──────────────────────────────────────────────────┤
│ + main(String[])                           : void   │
│ + adicionarContato(AgendaService, Scanner)   : void   │
│ + listarContatos(AgendaService)             : void   │
│ + procurarContato(AgendaService, Scanner)    : void   │
│ + procurarPorTelefone(AgendaService, Scanner): void   │
│ + excluirContato(AgendaService, Scanner)     : void   │
│ + mostraMenu()                             : void   │
│ + printLinha()                             : void   │
│ - lerOpcao(Scanner)                        : int    │
│ - lerCampo(Scanner, String)                : String │
└──────────────────────────────────────────────────┘
```

---

## Pré-requisitos

| Ferramenta | Versão mínima | Download                                      |
|------------|---------------|-----------------------------------------------|
| Java JDK   | 8+            | [https://adoptium.net](https://adoptium.net)  |

Verifique se o Java está instalado:

```bash
java -version
```

---

## Como Executar

### 1. Clone o repositório

```bash
git clone https://github.com/mathdejesus/Agenda-de-Contatos.git
cd Agenda-de-Contatos
```

### 2. Compile os arquivos Java

```bash
javac -d bin src/*.java
```

### 3. Execute a aplicação

```bash
java -cp bin Agenda           # Interface gráfica (Swing)
java -cp bin Agenda --cli     # Terminal interativo
```

> **IntelliJ IDEA:** abra a pasta raiz do projeto, aguarde a indexação e execute a classe `Agenda` diretamente pela IDE.

---

## Exemplo de Uso

```
Bem-vindo à Agenda Telefônica!

=== AGENDA TELEFÔNICA ===
1 - Adicionar Contato
2 - Listar Contatos
3 - Procurar Contato
4 - Buscar por Telefone
5 - Excluir Contato
6 - Sair
Escolha uma opção: 1
--------------------------------------------------
Nome: João da Silva
Telefone: (11) 91234-5678
E-mail: joao@email.com
--------------------------------------------------
Contato adicionado com sucesso!
--------------------------------------------------

=== AGENDA TELEFÔNICA ===
...
Escolha uma opção: 3
--------------------------------------------------
Digite o nome (ou parte dele): joão
--------------------------------------------------
1 contato(s) encontrado(s):
--------------------------------------------------
Nome:      João da Silva
Telefone:  (11) 91234-5678
E-mail:    joao@email.com
--------------------------------------------------

=== AGENDA TELEFÔNICA ===
...
Escolha uma opção: 4
--------------------------------------------------
Digite o telefone (ou parte dele): 91234
--------------------------------------------------
1 contato(s) encontrado(s):
--------------------------------------------------
Nome:      João da Silva
Telefone:  (11) 91234-5678
E-mail:    joao@email.com
--------------------------------------------------

=== AGENDA TELEFÔNICA ===
...
Escolha uma opção: 5
--------------------------------------------------
Digite o nome do contato a excluir: João da Silva
Contato a ser excluído:
Nome:      João da Silva
Telefone:  (11) 91234-5678
E-mail:    joao@email.com
Confirmar exclusão? (s/n): s
--------------------------------------------------
Contato excluído com sucesso!
--------------------------------------------------
```

---

## Documentação da API

### Classe `Contato`

Modelo de dados que representa um contato da agenda.

#### Construtor

```java
Contato(String nome, String telefone, String email)
```

Cria um novo contato validando todos os campos. Lança `IllegalArgumentException` se qualquer parâmetro for nulo, vazio ou com e-mail inválido.

#### Métodos

| Método                  | Retorno  | Descrição                                              |
|-------------------------|----------|--------------------------------------------------------|
| `getNome()`             | `String` | Retorna o nome do contato                              |
| `setNome(String)`       | `void`   | Atualiza o nome; rejeita nulo ou vazio                 |
| `getTelefone()`         | `String` | Retorna o primeiro telefone do contato                 |
| `getTelefones()`        | `List<String>` | Retorna lista imutável de todos os telefones     |
| `setTelefone(String)`   | `void`   | Substitui todos os telefones por um único; rejeita nulo, vazio ou menos de 8 dígitos |
| `setTelefones(List)`    | `void`   | Define múltiplos telefones; valida cada um             |
| `addTelefone(String)`   | `void`   | Adiciona um telefone à lista existente                 |
| `getEmail()`            | `String` | Retorna o e-mail do contato                            |
| `setEmail(String)`      | `void`   | Atualiza o e-mail com validação por regex              |
| `toString()`            | `String` | Representação formatada com nome, telefones e e-mail   |

---

### Classe `AgendaService`

Camada de serviço que encapsula a coleção de contatos e toda a lógica de negócio.

#### Construtor

```java
AgendaService()
```

Inicializa uma agenda vazia.

#### Métodos

| Método                          | Retorno          | Descrição                                                                         |
|---------------------------------|------------------|-----------------------------------------------------------------------------------|
| `adicionar(Contato)`            | `void`           | Adiciona o contato; lança exceção se nulo ou nome duplicado                       |
| `listarTodos()`                 | `List<Contato>`  | Retorna lista imutável de todos os contatos em ordem alfabética                   |
| `buscarPorNome(String)`         | `List<Contato>`  | Busca parcial e case-insensitive pelo nome; resultados em ordem alfabética       |
| `buscarPorTelefone(String)`     | `List<Contato>`  | Busca parcial pelo telefone; resultados em ordem alfabética                       |
| `excluir(String)`               | `boolean`        | Remove o primeiro contato com nome exato (case-insensitive); retorna `true` se removido |
| `existePorNome(String)`         | `boolean`        | Verifica se há contato com o nome informado (case-insensitive)                    |
| `total()`                       | `int`            | Retorna a quantidade de contatos cadastrados                                      |

---

### Classe `Agenda`

Camada de apresentação com o ponto de entrada (`main`) e os métodos de interação com o terminal.

#### Métodos públicos

| Método                                            | Descrição                                                   |
|---------------------------------------------------|-------------------------------------------------------------|
| `main(String[])`                                  | Inicia a aplicação e o loop do menu interativo              |
| `adicionarContato(AgendaService, Scanner)`        | Lê dados, cria o contato e delega ao serviço                |
| `listarContatos(AgendaService)`                   | Exibe todos os contatos em formato numerado                 |
| `procurarContato(AgendaService, Scanner)`         | Lê um trecho do nome e exibe os resultados encontrados      |
| `procurarPorTelefone(AgendaService, Scanner)`     | Lê um trecho do telefone e exibe os resultados encontrados  |
| `excluirContato(AgendaService, Scanner)`          | Lê o nome, exibe o contato e pede confirmação antes de excluir |
| `mostraMenu()`                                    | Exibe o menu de opções no terminal                          |
| `printLinha()`                                    | Imprime uma linha separadora de 50 traços                   |

---

## Limitações Conhecidas

- **Validação de e-mail por regex aproximada:** não substitui uma validação completa RFC 5322 (ex.: não valida domínios reais nem subdomínios complexos).
- **Validação de telefone simples:** verifica apenas a quantidade de dígitos (mínimo 8); não valida DDD, operadora ou formato regional.
- **Exclusão pelo primeiro resultado:** quando a busca retorna múltiplos contatos, apenas o primeiro é apresentado para exclusão.
- **Edição pelo primeiro resultado:** quando a busca retorna múltiplos contatos, apenas o primeiro é editável.

---

## Melhorias Futuras

- [x] Persistência em arquivo `.txt`
- [x] Validação completa de formato de e-mail (regex)
- [x] Suporte a múltiplos telefones por contato
- [x] Edição de contatos existentes
- [x] Interface gráfica com Swing (padrão ao executar `java Agenda`; use `--cli` para terminal)
- [x] Testes unitários (com `assert` do Java, sem JUnit)

---

## Tecnologias Utilizadas

- **Java SE 8+**
- **Java Collections Framework** (`ArrayList`, `List`, `Collections`)
- **java.util.Scanner** para leitura do terminal
- **Javadoc** para documentação do código
- **IntelliJ IDEA** (ambiente de desenvolvimento)

---

## Autor

**mathdejesus**
🔗 [github.com/mathdejesus](https://github.com/mathdejesus)

---

## Licença

Este projeto está disponível sob os termos da licença **MIT**.
Consulte o arquivo [LICENSE](LICENSE) para mais informações.

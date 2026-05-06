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
- Coleções (`ArrayList`, `List`)
- Leitura de entrada do usuário com `Scanner`
- Estruturas de controle (`while`, `switch`, `for`)
- Métodos estáticos e reutilização de código

---

## Funcionalidades

| # | Opção no Menu       | Descrição                                                   |
|---|---------------------|-------------------------------------------------------------|
| 1 | Adicionar Contato   | Cadastra um novo contato (nome, telefone e e-mail)          |
| 2 | Listar Contatos     | Exibe todos os contatos armazenados na agenda               |
| 3 | Procurar Contato    | Busca um contato pelo nome (correspondência exata)          |
| 4 | Excluir Contato     | Remove um contato da agenda pelo nome (correspondência exata)|
| 5 | Sair                | Encerra a aplicação                                         |

---

## Estrutura do Projeto

```
Agenda-de-Contatos/
├── .idea/                  # Configurações do IntelliJ IDEA (ignorar)
├── bin/                    # Bytecode compilado (.class)
│   ├── Agenda.class
│   └── Contato.class
├── src/                    # Código-fonte Java
│   ├── Agenda.java         # Classe principal — ponto de entrada e lógica do menu
│   └── Contato.java        # Modelo de dados — representa um contato
├── .gitignore
├── Agenda-Telefonica.iml   # Arquivo de módulo do IntelliJ IDEA
└── README.md
```

---

## Diagrama de Classes

```
┌─────────────────────────────────────────────────┐
│                    Contato                       │
├─────────────────────────────────────────────────┤
│ - nome     : String                             │
│ - telefone : String                             │
│ - email    : String                             │
├─────────────────────────────────────────────────┤
│ + Contato(nome, telefone, email)                │
│ + getNome()      : String                       │
│ + setNome(nome)  : void                         │
│ + getTelefone()  : String                       │
│ + setTelefone(t) : void                         │
│ + getEmail()     : String                       │
│ + setEmail(e)    : void                         │
│ + toString()     : String                       │
└─────────────────────────────────────────────────┘
                        ▲
                        │ usa
┌─────────────────────────────────────────────────┐
│                    Agenda                        │
├─────────────────────────────────────────────────┤
│ (sem atributos de instância — classe utilitária) │
├─────────────────────────────────────────────────┤
│ + main(args)                        : void      │
│ + adicionarContato(agenda, scanner) : void      │
│ + listarContato(agenda)             : void      │
│ + procurarContato(agenda, scanner)  : void      │
│ + excluirContato(agenda, scanner)   : void      │
│ + mostraMenu()                      : void      │
│ + printLinha()                      : void      │
└─────────────────────────────────────────────────┘
```

---

## Pré-requisitos

| Ferramenta | Versão mínima | Download |
|------------|--------------|---------|
| Java JDK   | 8+           | [https://adoptium.net](https://adoptium.net) |

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
javac -d bin src/Contato.java src/Agenda.java
```

### 3. Execute a aplicação

```bash
java -cp bin Agenda
```

> **IntelliJ IDEA:** abra a pasta raiz do projeto, aguarde a indexação e execute a classe `Agenda` diretamente pela IDE.

---

## Exemplo de Uso

```
1 - Adicionar Contato
2 - Listar Contatos
3 - Procurar Contato
4 - Excluir Contato
5 - Sair
1
Digite o nome do contato:
João da Silva
Digite o telefone do contato:
(11) 91234-5678
Digite o email do contato:
joao@email.com
--------------------------------------------------
Contato adicionado com sucesso!
--------------------------------------------------
1 - Adicionar Contato
2 - Listar Contatos
3 - Procurar Contato
4 - Excluir Contato
5 - Sair
3
Digite o nome do contato:
João da Silva
--------------------------------------------------
Contato encontrado
Contato
nome: João da Silva,
telefone: (11) 91234-5678,
email: joao@email.com
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

Cria um novo contato com nome, telefone e e-mail fornecidos.

#### Métodos

| Método              | Retorno  | Descrição                                   |
|---------------------|----------|---------------------------------------------|
| `getNome()`         | `String` | Retorna o nome do contato                   |
| `setNome(String)`   | `void`   | Atualiza o nome do contato                  |
| `getTelefone()`     | `String` | Retorna o telefone do contato               |
| `setTelefone(String)` | `void` | Atualiza o telefone do contato              |
| `getEmail()`        | `String` | Retorna o e-mail do contato                 |
| `setEmail(String)`  | `void`   | Atualiza o e-mail do contato                |
| `toString()`        | `String` | Representação textual com todos os campos   |

---

### Classe `Agenda`

Classe principal com o ponto de entrada (`main`) e todos os métodos de operação sobre a lista de contatos.

#### Métodos

| Método                                          | Descrição                                                  |
|-------------------------------------------------|------------------------------------------------------------|
| `main(String[])`                                | Inicia a aplicação e o loop do menu interativo             |
| `adicionarContato(List<Contato>, Scanner)`      | Lê dados do usuário e adiciona um novo contato             |
| `listarContato(List<Contato>)`                  | Imprime todos os contatos da agenda                        |
| `procurarContato(List<Contato>, Scanner)`       | Busca e exibe um contato pelo nome (busca exata)           |
| `excluirContato(List<Contato>, Scanner)`        | Remove o primeiro contato cujo nome coincida com a entrada |
| `mostraMenu()`                                  | Exibe o menu de opções no terminal                         |
| `printLinha()`                                  | Imprime uma linha separadora de 50 traços                  |

---

## Limitações Conhecidas

- **Sem persistência:** os dados são armazenados apenas em memória. Ao encerrar o programa, todos os contatos são perdidos.
- **Busca case-sensitive:** a pesquisa e exclusão por nome diferenciam maiúsculas de minúsculas (`João` ≠ `joão`).
- **Sem validação de entrada:** o programa não valida se o telefone ou e-mail possuem formato correto.
- **Duplicatas permitidas:** é possível cadastrar dois contatos com o mesmo nome.
- **Mensagem incorreta em `listarContato`:** após listar os contatos, o método exibe erroneamente a mensagem _"Contato adicionado com sucesso!"_.

---

## Melhorias Futuras

- [ ] Persistência em arquivo `.txt` ou `.json`
- [ ] Validação de formato de telefone e e-mail
- [ ] Busca case-insensitive (usando `equalsIgnoreCase`)
- [ ] Busca parcial por nome (usando `contains`)
- [ ] Prevenção de contatos duplicados
- [ ] Interface gráfica com JavaFX ou Swing
- [ ] Suporte a múltiplos telefones por contato
- [ ] Ordenação alfabética da lista

---

## Tecnologias Utilizadas

- **Java SE 8+**
- **Java Collections Framework** (`ArrayList`, `List`)
- **java.util.Scanner** para leitura do terminal
- **IntelliJ IDEA** (ambiente de desenvolvimento)

---

## Autor

**mathdejesus**  
🔗 [github.com/mathdejesus](https://github.com/mathdejesus)

---

## Licença

Este projeto está disponível sob os termos da licença **MIT**.  
Consulte o arquivo [LICENSE](LICENSE) para mais informações.

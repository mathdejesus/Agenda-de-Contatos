# Graph Report - .  (2026-05-31)

## Corpus Check
- Corpus is ~7,405 words - fits in a single context window. You may not need a graph.

## Summary
- 167 nodes · 457 edges · 8 communities (6 shown, 2 thin omitted)
- Extraction: 86% EXTRACTED · 14% INFERRED · 0% AMBIGUOUS · INFERRED: 62 edges (avg confidence: 0.8)
- Token cost: 0 input · 0 output

## Community Hubs (Navigation)
- [[_COMMUNITY_GUI Table & View|GUI Table & View]]
- [[_COMMUNITY_Service CRUD Operations|Service CRUD Operations]]
- [[_COMMUNITY_Contato Tests & Validation|Contato Tests & Validation]]
- [[_COMMUNITY_CLI Interface|CLI Interface]]
- [[_COMMUNITY_Contato Domain Model|Contato Domain Model]]
- [[_COMMUNITY_Core Architecture|Core Architecture]]
- [[_COMMUNITY_Persistence Testing|Persistence Testing]]
- [[_COMMUNITY_Custom Rendering|Custom Rendering]]

## God Nodes (most connected - your core abstractions)
1. `TestContato` - 21 edges
2. `TestAgendaService` - 20 edges
3. `Agenda` - 14 edges
4. `Contato` - 13 edges
5. `AgendaService` - 12 edges
6. `String` - 12 edges
7. `AgendaGUI` - 11 edges
8. `ContatoDialog` - 11 edges
9. `AgendaTableModel` - 10 edges
10. `Scanner` - 9 edges

## Surprising Connections (you probably didn't know these)
- `Agenda` --semantically_similar_to--> `AgendaGUI`  [INFERRED] [semantically similar]
  src/Agenda.java → src/AgendaGUI.java
- `AgendaService` --shares_data_with--> `Contato`  [INFERRED]
  src/AgendaService.java → src/Contato.java
- `ContatoDialog` --references--> `Contato`  [EXTRACTED]
  src/AgendaGUI.java → src/Contato.java
- `TestAgendaService` --references--> `Contato`  [EXTRACTED]
  src/TestAgendaService.java → src/Contato.java
- `TestContato` --calls--> `Contato`  [EXTRACTED]
  src/TestContato.java → src/Contato.java

## Import Cycles
- None detected.

## Hyperedges (group relationships)
- **CRUD Data Flow** — Agenda, AgendaGUI, AgendaService, Contato, concept_FilePersistence [INFERRED 0.90]
- **Presentation Layer Alternatives** — Agenda, AgendaGUI, concept_CLI, concept_GUI [INFERRED 0.85]

## Communities (8 total, 2 thin omitted)

### Community 0 - "GUI Table & View"
Cohesion: 0.13
Nodes (11): AbstractTableModel, AgendaGUI, AgendaTableModel, ContatoDialog, AgendaService, Contato, List, Override (+3 more)

### Community 1 - "Service CRUD Operations"
Cohesion: 0.17
Nodes (7): AgendaService, Contato, List, String, AgendaService, String, TestAgendaService

### Community 3 - "CLI Interface"
Cohesion: 0.33
Nodes (4): Agenda, AgendaService, String, Scanner

### Community 4 - "Contato Domain Model"
Cohesion: 0.29
Nodes (4): Contato, List, Override, String

### Community 5 - "Core Architecture"
Cohesion: 0.27
Nodes (13): Agenda, AgendaGUI, AgendaService, AgendaTableModel, Contato, ContatoDialog, MultiLineCellRenderer, TestAgendaService (+5 more)

### Community 7 - "Custom Rendering"
Cohesion: 0.25
Nodes (6): MultiLineCellRenderer, Component, JTable, JTextArea, Object, TableCellRenderer

## Knowledge Gaps
- **7 isolated node(s):** `AgendaService`, `JTable`, `Component`, `Override`, `AgendaService` (+2 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **2 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `Contato` connect `Contato Domain Model` to `Contato Tests & Validation`?**
  _High betweenness centrality (0.075) - this node is a cross-community bridge._
- **Why does `MultiLineCellRenderer` connect `Custom Rendering` to `GUI Table & View`?**
  _High betweenness centrality (0.067) - this node is a cross-community bridge._
- **What connects `AgendaService`, `JTable`, `Component` to the rest of the system?**
  _7 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `GUI Table & View` be split into smaller, more focused modules?**
  _Cohesion score 0.13229018492176386 - nodes in this community are weakly interconnected._
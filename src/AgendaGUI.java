import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.*;

public class AgendaGUI extends JFrame {

    private final AgendaService service;
    private JTable table;
    private AgendaTableModel tableModel;
    private JTextField searchField;
    private JLabel totalLabel;

    public AgendaGUI(AgendaService service) {
        this.service = service;
        initUI();
    }

    private void initUI() {
        setTitle("Agenda Telefônica");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);

        tableModel = new AgendaTableModel();
        table = new JTable(tableModel);
        table.setDefaultRenderer(Object.class, new MultiLineCellRenderer());
        table.getTableHeader().setReorderingAllowed(false);

        searchField = new JTextField();
        searchField.addActionListener(e -> buscar());
        JButton searchBtn = new JButton("Buscar");
        searchBtn.addActionListener(e -> buscar());

        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchBtn, BorderLayout.EAST);

        JButton addBtn = new JButton("Adicionar");
        JButton editBtn = new JButton("Editar");
        JButton deleteBtn = new JButton("Excluir");
        addBtn.addActionListener(e -> adicionarContato());
        editBtn.addActionListener(e -> editarContato());
        deleteBtn.addActionListener(e -> excluirContato());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addBtn);
        buttonPanel.add(editBtn);
        buttonPanel.add(deleteBtn);

        totalLabel = new JLabel("Total: 0");

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(totalLabel, BorderLayout.WEST);
        bottomPanel.add(buttonPanel, BorderLayout.EAST);

        setLayout(new BorderLayout());
        add(searchPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        refreshTable();
    }

    private void refreshTable() {
        tableModel.refresh();
        totalLabel.setText("Total: " + service.total());
        ajustarAlturaLinhas();
    }

    private void ajustarAlturaLinhas() {
        for (int row = 0; row < table.getRowCount(); row++) {
            int altura = 20;
            for (int col = 0; col < table.getColumnCount(); col++) {
                Component comp = table.prepareRenderer(
                    table.getCellRenderer(row, col), row, col);
                altura = Math.max(altura, comp.getPreferredSize().height + 4);
            }
            table.setRowHeight(row, altura);
        }
    }

    private void buscar() {
        String termo = searchField.getText().trim();
        if (termo.isEmpty()) {
            refreshTable();
        } else {
            tableModel.search(termo);
            totalLabel.setText("Encontrados: " + tableModel.getRowCount());
            ajustarAlturaLinhas();
        }
    }

    private void adicionarContato() {
        ContatoDialog dialog = new ContatoDialog(this, "Adicionar Contato", null);
        dialog.setVisible(true);
        if (!dialog.isConfirmed()) return;
        try {
            Contato contato = new Contato(
                dialog.getNome(), dialog.getTelefones(), dialog.getEmail());
            service.adicionar(contato);
            refreshTable();
        } catch (IllegalArgumentException | IllegalStateException e) {
            JOptionPane.showMessageDialog(this,
                "Erro ao adicionar: " + e.getMessage(),
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editarContato() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this,
                "Selecione um contato para editar.");
            return;
        }
        Contato original = tableModel.getContactAt(row);
        ContatoDialog dialog = new ContatoDialog(this, "Editar Contato", original);
        dialog.setVisible(true);
        if (!dialog.isConfirmed()) return;
        try {
            Contato atualizado = new Contato(
                dialog.getNome(), dialog.getTelefones(), dialog.getEmail());
            service.editar(original.getNome(), atualizado);
            refreshTable();
        } catch (IllegalArgumentException | IllegalStateException e) {
            JOptionPane.showMessageDialog(this,
                "Erro ao editar: " + e.getMessage(),
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluirContato() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this,
                "Selecione um contato para excluir.");
            return;
        }
        Contato c = tableModel.getContactAt(row);
        int confirm = JOptionPane.showConfirmDialog(this,
            "Excluir contato:\n" + c.getNome() + "?",
            "Confirmar exclusão", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            service.excluir(c.getNome());
            refreshTable();
        }
    }

    public static void main(String[] args) {
        AgendaService service = new AgendaService();
        EventQueue.invokeLater(() -> new AgendaGUI(service).setVisible(true));
    }

    // -------------------------------------------------------------------------
    // Table Model
    // -------------------------------------------------------------------------

    private class AgendaTableModel extends AbstractTableModel {
        private List<Contato> data = new ArrayList<>();
        private final String[] columns = {"Nome", "Telefones", "E-mail"};

        void refresh() {
            data = service.listarTodos();
            fireTableDataChanged();
        }

        void search(String termo) {
            String t = termo.toLowerCase();
            data = new ArrayList<>();
            for (Contato c : service.listarTodos()) {
                if (c.getNome().toLowerCase().contains(t)
                    || c.getEmail().toLowerCase().contains(t)
                    || telefonesContem(c, t)) {
                    data.add(c);
                }
            }
            fireTableDataChanged();
        }

        private boolean telefonesContem(Contato c, String termo) {
            for (String tel : c.getTelefones()) {
                if (tel.toLowerCase().contains(termo)) return true;
            }
            return false;
        }

        Contato getContactAt(int row) {
            return data.get(row);
        }

        @Override
        public int getRowCount() {
            return data.size();
        }

        @Override
        public int getColumnCount() {
            return columns.length;
        }

        @Override
        public String getColumnName(int col) {
            return columns[col];
        }

        @Override
        public Object getValueAt(int row, int col) {
            Contato c = data.get(row);
            if (col == 0) return c.getNome();
            if (col == 1) return c.getTelefones();
            if (col == 2) return c.getEmail();
            return "";
        }
    }

    // -------------------------------------------------------------------------
    // Multi-line Cell Renderer
    // -------------------------------------------------------------------------

    private class MultiLineCellRenderer extends JTextArea implements TableCellRenderer {
        MultiLineCellRenderer() {
            setLineWrap(true);
            setWrapStyleWord(true);
            setOpaque(true);
            setFont(UIManager.getFont("Table.font"));
            setBorder(BorderFactory.createEmptyBorder(2, 4, 2, 4));
        }

        @Override
        public Component getTableCellRendererComponent(
                JTable table, Object value,
                boolean isSelected, boolean hasFocus,
                int row, int column) {

            if (value instanceof List) {
                StringBuilder sb = new StringBuilder();
                for (Object item : (List<?>) value) {
                    sb.append(item).append('\n');
                }
                setText(sb.toString());
            } else {
                setText(value != null ? value.toString() : "");
            }

            setBackground(isSelected
                ? table.getSelectionBackground()
                : table.getBackground());
            setForeground(isSelected
                ? table.getSelectionForeground()
                : table.getForeground());

            int colWidth = table.getColumnModel().getColumn(column).getWidth();
            setSize(colWidth, Short.MAX_VALUE);
            int height = Math.max(getPreferredSize().height + 4, 20);
            if (table.getRowHeight(row) != height) {
                table.setRowHeight(row, height);
            }
            return this;
        }
    }

    // -------------------------------------------------------------------------
    // Contato Dialog
    // -------------------------------------------------------------------------

    private class ContatoDialog extends JDialog {
        private JTextField nomeField;
        private JTextField emailField;
        private DefaultListModel<String> phoneListModel;
        private JList<String> phoneList;
        private boolean confirmed = false;

        ContatoDialog(JFrame parent, String title, Contato contato) {
            super(parent, title, true);
            initUI();
            if (contato != null) {
                nomeField.setText(contato.getNome());
                emailField.setText(contato.getEmail());
                for (String tel : contato.getTelefones()) {
                    phoneListModel.addElement(tel);
                }
            }
            pack();
            setMinimumSize(new Dimension(350, 300));
            setLocationRelativeTo(parent);
        }

        private void initUI() {
            nomeField = new JTextField(20);
            emailField = new JTextField(20);
            phoneListModel = new DefaultListModel<>();
            phoneList = new JList<>(phoneListModel);

            JPanel form = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(4, 4, 4, 4);
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.anchor = GridBagConstraints.NORTHWEST;

            gbc.gridx = 0;
            gbc.gridy = 0;
            form.add(new JLabel("Nome:"), gbc);
            gbc.gridx = 1;
            gbc.weightx = 1;
            form.add(nomeField, gbc);

            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.weightx = 0;
            form.add(new JLabel("E-mail:"), gbc);
            gbc.gridx = 1;
            gbc.weightx = 1;
            form.add(emailField, gbc);

            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.gridwidth = 2;
            gbc.weightx = 0;
            form.add(new JLabel("Telefones:"), gbc);

            gbc.gridy = 3;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.weightx = 1;
            gbc.weighty = 1;
            JScrollPane scroll = new JScrollPane(phoneList);
            scroll.setPreferredSize(new Dimension(250, 100));
            form.add(scroll, gbc);

            gbc.gridy = 4;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.weighty = 0;
            JPanel phoneButtons = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
            JButton addPhoneBtn = new JButton("Adicionar");
            JButton removePhoneBtn = new JButton("Remover");
            addPhoneBtn.addActionListener(e -> adicionarTelefone());
            removePhoneBtn.addActionListener(e -> removerTelefone());
            phoneButtons.add(addPhoneBtn);
            phoneButtons.add(removePhoneBtn);
            form.add(phoneButtons, gbc);

            JPanel buttons = new JPanel();
            JButton okBtn = new JButton("OK");
            JButton cancelBtn = new JButton("Cancelar");
            okBtn.addActionListener(e -> {
                if (validar()) {
                    confirmed = true;
                    dispose();
                }
            });
            cancelBtn.addActionListener(e -> dispose());
            buttons.add(okBtn);
            buttons.add(cancelBtn);

            setLayout(new BorderLayout());
            add(form, BorderLayout.CENTER);
            add(buttons, BorderLayout.SOUTH);
        }

        private void adicionarTelefone() {
            String tel = JOptionPane.showInputDialog(this, "Telefone:");
            if (tel != null && !tel.isBlank()) {
                phoneListModel.addElement(tel.trim());
            }
        }

        private void removerTelefone() {
            int idx = phoneList.getSelectedIndex();
            if (idx >= 0) {
                phoneListModel.remove(idx);
            }
        }

        private boolean validar() {
            if (nomeField.getText().isBlank()) {
                JOptionPane.showMessageDialog(this, "Nome não pode ser vazio.");
                nomeField.requestFocus();
                return false;
            }
            if (emailField.getText().isBlank()) {
                JOptionPane.showMessageDialog(this, "E-mail não pode ser vazio.");
                emailField.requestFocus();
                return false;
            }
            if (phoneListModel.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Adicione ao menos um telefone.");
                return false;
            }
            return true;
        }

        String getNome() {
            return nomeField.getText().trim();
        }

        String getEmail() {
            return emailField.getText().trim();
        }

        List<String> getTelefones() {
            List<String> list = new ArrayList<>();
            for (int i = 0; i < phoneListModel.size(); i++) {
                list.add(phoneListModel.get(i));
            }
            return list;
        }

        boolean isConfirmed() {
            return confirmed;
        }
    }
}

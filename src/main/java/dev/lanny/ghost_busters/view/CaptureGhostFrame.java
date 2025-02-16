package dev.lanny.ghost_busters.view;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.Random;

import dev.lanny.ghost_busters.controller.HunterController;
import dev.lanny.ghost_busters.model.GhostModel;
import dev.lanny.ghost_busters.model.GhostClass;
import dev.lanny.ghost_busters.model.ThreatLevel;

public class CaptureGhostFrame extends JFrame {
    private JTextField nameField, abilityField, dateField;
    private JComboBox<GhostClass> ghostClassComboBox;
    private JComboBox<ThreatLevel> threatLevelComboBox;
    private JLabel statusLabel;
    private final HunterController hunterController;
    private final MainFrame mainFrame;

    public CaptureGhostFrame(MainFrame mainFrame, HunterController hunterController) {
        this.hunterController = hunterController;
        this.mainFrame = mainFrame;
        setupFrame();

        JPanel panel = createPanel();
        GridBagConstraints gbc = setupGridConstraints();

        nameField = createTextField("nameField");
        abilityField = createTextField("abilityField");
        dateField = createTextField("dateField", LocalDate.now().toString());
        ghostClassComboBox = createComboBox(GhostClass.values(), "ghostClassComboBox");
        threatLevelComboBox = createComboBox(ThreatLevel.values(), "threatLevelComboBox");

        addFormField(panel, "👻 Nombre:", nameField, gbc, 0);
        addFormField(panel, "👻 Clase:", ghostClassComboBox, gbc, 1);
        addFormField(panel, "⚠ Peligro:", threatLevelComboBox, gbc, 2);
        addFormField(panel, "✨ Habilidad:", abilityField, gbc, 3);
        addFormField(panel, "📅 Fecha (YYYY-MM-DD):", dateField, gbc, 4);

        setupCaptureButton(panel, gbc);
        setupStatusLabel(panel, gbc);
        add(panel);
        setVisible(true);
    }

    private void setupFrame() {
        setTitle("👻 Capturar Fantasma");
        setSize(600, 350);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private JPanel createPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setBackground(Color.BLACK);
        return panel;
    }

    private GridBagConstraints setupGridConstraints() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        return gbc;
    }

    private void setupCaptureButton(JPanel panel, GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;

        JButton captureButton = createButton("📷 Capturar", "captureButton");
        captureButton.addActionListener(e -> captureGhost());
        panel.add(captureButton, gbc);
    }

    private void setupStatusLabel(JPanel panel, GridBagConstraints gbc) {
        gbc.gridy = 6;
        statusLabel = createLabel("", false);
        statusLabel.setName("statusLabel");
        panel.add(statusLabel, gbc);
    }

    private void captureGhost() {
        String name = nameField.getText().trim();
        String ability = abilityField.getText().trim();
        String captureDate = dateField.getText().trim();

        if (name.isEmpty() || ability.isEmpty()) {
            updateStatusLabel("❌ Nombre y habilidad no pueden estar vacíos.");
            return;
        }
        if (!captureDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
            updateStatusLabel("❌ Fecha inválida. Use formato YYYY-MM-DD.");
            return;
        }
        try {
            GhostModel ghost = new GhostModel(name,
                    (GhostClass) ghostClassComboBox.getSelectedItem(),
                    (ThreatLevel) threatLevelComboBox.getSelectedItem(),
                    ability, captureDate);

            hunterController.captureGhost(ghost);
            updateStatusLabel("✅ ¡Fantasma capturado!");

            showGhostDetailsDialog(ghost, new Random().nextInt(10) + 1);
            dispose();
        } catch (IllegalArgumentException ex) {
            updateStatusLabel("❌ " + ex.getMessage());
        }
    }

    private void showGhostDetailsDialog(GhostModel ghost, int affinity) {
        JDialog dialog = new JDialog(this, "👻 Detalles del Fantasma", true);
        dialog.setSize(400, 350);
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(false);

        JPanel panel = new JPanel(new GridLayout(8, 1));
        panel.setBackground(new Color(30, 30, 30));

        panel.add(createLabel("📌 Nombre: " + ghost.getName(), true));
        panel.add(createLabel("📌 Clase: " + ghost.getGhostClass(), true));
        panel.add(createLabel("📌 Peligro: " + ghost.getThreatLevel(), true));
        panel.add(createLabel("📌 Habilidad: " + ghost.getSpecialAbility(), true));
        panel.add(createLabel("📌 Fecha: " + ghost.getCaptureDate(), true));
        panel.add(createLabel("📌 Afinidad Ectoplásmica: " + affinity + "/10", true));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(30, 30, 30));
        buttonPanel.add(createDialogButton("➕ Añadir Otro Fantasma", dialog,
                () -> new CaptureGhostFrame(mainFrame, hunterController)));
        buttonPanel.add(createDialogButton("🏠 Menú Principal", dialog, () -> {
            mainFrame.setVisible(true);
        }));

        panel.add(buttonPanel);
        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void addFormField(JPanel panel, String labelText, JComponent field, GridBagConstraints gbc, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(createLabel(labelText, true), gbc);
        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    private JLabel createLabel(String text, boolean highlight) {
        JLabel label = new JLabel(text);
        label.setForeground(highlight ? Color.GRAY : Color.WHITE);
        label.setFont(new Font("SansSerif", Font.BOLD, 16));
        return label;
    }

    private JTextField createTextField(String name) {
        return createTextField(name, "");
    }

    private JTextField createTextField(String name, String defaultValue) {
        JTextField textField = new JTextField(defaultValue, 20);
        textField.setBackground(new Color(30, 30, 30));
        textField.setForeground(Color.WHITE);
        textField.setCaretColor(Color.WHITE);
        textField.setBorder(BorderFactory.createLineBorder(new Color(0, 180, 180), 2));
        textField.setName(name);
        return textField;
    }

    private <T> JComboBox<T> createComboBox(T[] items, String name) {
        JComboBox<T> comboBox = new JComboBox<>(items);
        comboBox.setBackground(Color.BLACK);
        comboBox.setForeground(Color.WHITE);
        comboBox.setBorder(BorderFactory.createLineBorder(new Color(0, 180, 180), 2));
        comboBox.setName(name);
        return comboBox;
    }

    private JButton createButton(String text, String name) {
        JButton button = new JButton(text);
        button.setBackground(new Color(0, 180, 180));
        button.setForeground(new Color(30, 30, 30));
        button.setFont(new Font("SansSerif", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(0, 180, 180), 2));
        button.setName(name);
        return button;
    }

    private JButton createDialogButton(String text, JDialog dialog, Runnable action) {
        JButton button = createButton(text, "");
        button.addActionListener(e -> {
            dialog.dispose();
            action.run();
        });
        return button;
    }

    private void updateStatusLabel(String message) {
        statusLabel.setText(message);
        revalidate();
        repaint();
    }
}

package dev.lanny.ghost_busters.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.*;

import dev.lanny.ghost_busters.controller.HunterController;
import dev.lanny.ghost_busters.model.HunterModel;

public class MainFrame extends JFrame {
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 600;

    private static final Color bacgroundColor = new Color(34, 34, 34);
    private static final Color textWhite = Color.WHITE;
    private static final Color buttonBlue = new Color(0, 180, 180);
    private static final Color buttonBG = new Color(50, 50, 50);

    private final HunterController hunterController;
    private JLabel backgroundLabel;

    public MainFrame(HunterController hunterController) {
        if (hunterController == null) {
            throw new IllegalArgumentException("❌ ERROR: hunterController not can be NULL in MainFrame");
        }
        this.hunterController = hunterController;

        configureFrame();
        configureUI();
    }

    private void configureFrame() {
        setTitle("👻 GhostBusters Asturias - Base de Operaciones");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void configureUI() {
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        setupBackground(layeredPane);
        setupTitle(layeredPane);
        setupButtons(layeredPane);

        setContentPane(layeredPane);
        setVisible(true);
    }

    private void setupBackground(JLayeredPane layeredPane) {
        ImageIcon backgroundIcon = loadImage("background.png");
        if (backgroundIcon != null) {
            Image backgroundImage = backgroundIcon.getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);
            backgroundLabel = new JLabel(new ImageIcon(backgroundImage));
            backgroundLabel.setBounds(0, 0, WIDTH, HEIGHT);
            layeredPane.add(backgroundLabel, JLayeredPane.DEFAULT_LAYER);
        } else {
            showErrorDialog("No se pudo cargar la imagen de fondo.");
        }
    }

    private void setupTitle(JLayeredPane layeredPane) {
        JLabel titleLabel = new JLabel("👻 GhostBusters Asturias - Base de Operaciones 👻", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 26));
        titleLabel.setForeground(textWhite);
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(0, 0, 0, 160));
        titleLabel.setBounds(0, 0, WIDTH, 80);
        layeredPane.add(titleLabel, JLayeredPane.MODAL_LAYER);
    }

    private void setupButtons(JLayeredPane layeredPane) {
        JPanel buttonPanel = new JPanel(null);
        buttonPanel.setOpaque(false);
        buttonPanel.setBounds(0, 0, WIDTH, HEIGHT);

        buttonPanel.add(
                createButton("📷 Capturar Fantasma", 450, 200, () -> new CaptureGhostFrame(this, hunterController)));
        buttonPanel
                .add(createButton("📜 Ver Lista de Fantasmas", 450, 270, () -> new ListGhostsFrame(hunterController)));
        buttonPanel.add(createButton("🔍 Eliminar Fantasmas", 450, 340,
                () -> new DeleteGhostFrame(this, hunterController).setVisible(true)));
        buttonPanel.add(createButton("🚪 Salir", 450, 410, this::exitApplication));

        layeredPane.add(buttonPanel, JLayeredPane.PALETTE_LAYER);
    }

    private JButton createButton(String text, int x, int y, Runnable action) {
        JButton button = new JButton(text);
        button.setBounds(x, y, 300, 50);
        button.setFont(new Font("SansSerif", Font.BOLD, 16));
        button.setBackground(buttonBG);
        button.setForeground(textWhite);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(buttonBlue, 2));

        button.addActionListener(e -> action.run());
        applyHoverEffect(button, buttonBlue);

        return button;
    }

    private void applyHoverEffect(JButton button, Color hoverColor) {
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverColor);
                button.setForeground(Color.BLACK);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(buttonBG);
                button.setForeground(textWhite);
            }
        });
    }

    private void exitApplication() {
        UIManager.put("OptionPane.background", bacgroundColor);
        UIManager.put("Panel.background", bacgroundColor);
        UIManager.put("OptionPane.messageForeground", textWhite);
        UIManager.put("Button.background", buttonBlue);
        UIManager.put("Button.foreground", Color.BLACK);
        UIManager.put("Button.border", BorderFactory.createLineBorder(buttonBlue, 2));
        UIManager.put("Button.font", new Font("SansSerif", Font.BOLD, 14));

        String message = "<html><body style='text-align: center;'>"
                + "<p style='font-size:14px; color:white;'>"
                + "👻 ¿Estás seguro de que deseas salir del juego?"
                + "</p></body></html>";

        int option = JOptionPane.showConfirmDialog(
                this,
                message,
                "🔴 Confirmar Salida",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
        System.err.println(message);
    }

    private ImageIcon loadImage(String filename) {
        try {
            return new ImageIcon(getClass().getClassLoader().getResource(filename));
        } catch (Exception e) {
            System.err.println("Error al cargar la imagen: " + filename);
            return null;
        }
    }

    public static void main(String[] args) {
        HunterModel hunterModel = new HunterModel("Egon Spengler", new ArrayList<>());
        HunterController hunterController = new HunterController(hunterModel);

        SwingUtilities.invokeLater(() -> new MainFrame(hunterController));
    }
}

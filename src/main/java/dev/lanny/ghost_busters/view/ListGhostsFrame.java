package dev.lanny.ghost_busters.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import dev.lanny.ghost_busters.controller.HunterController;
import dev.lanny.ghost_busters.model.GhostModel;

public class ListGhostsFrame extends JFrame {
    private HunterController hunterController;

    public ListGhostsFrame(HunterController hunterController) {
        if (hunterController == null) {
            throw new IllegalArgumentException("❌ ERROR: hunterController no puede ser NULL en ListGhostsFrame");
        }

        this.hunterController = hunterController;
        setTitle("📜 Lista de Fantasmas Capturados");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(50, 50, 50)); 

   
        JLabel titleLabel = new JLabel("📜 Lista de Fantasmas Capturados", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 26));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(50, 50, 50)); 
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        
        JTable ghostsTable = createGhostsTable();
        JScrollPane scrollPane = new JScrollPane(ghostsTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Botón para salir
        JButton backButton = new JButton("🚪 Volver");
        backButton.setFont(new Font("Sans", Font.BOLD, 16));
        backButton.setBackground(new Color(50, 50, 50));
        backButton.setForeground(Color.white);
        backButton.setFocusPainted(false);
        backButton.setBorder(BorderFactory.createLineBorder((new Color(0, 180, 180)), 2));
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); 
            }
        });

       
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(50, 50, 50)); 
        buttonPanel.add(backButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
        setVisible(true);
    }

    private JTable createGhostsTable() {
        String[] columnNames = {"ID", "Nombre","Peligro", "Clase","Habilidad", "Fecha/Captura"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        List<GhostModel> ghosts = hunterController.getCapturedGhosts();
        for (GhostModel ghost : ghosts) {
            Object[] row = {
                ghost.getId(),
                ghost.getName(),
                ghost.getThreatLevel(),
                ghost.getGhostClass().toString(),
                ghost.getSpecialAbility(),
                ghost.getCaptureDate().toString()
            };
            model.addRow(row);
        }

        JTable table = new JTable(model);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.setForeground(Color.white);
        table.setBackground(new Color(50, 50, 50));
        table.setGridColor(new Color(0, 180, 180));
        table.setSelectionBackground(Color.blue);
        table.setSelectionForeground(Color.blue);
        table.setRowHeight(25);

        return table;
    }
}

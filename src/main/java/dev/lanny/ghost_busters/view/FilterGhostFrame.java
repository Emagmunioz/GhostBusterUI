package dev.lanny.ghost_busters.view;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;

public class FilterGhostFrame extends JFrame {
    private JButton b1, b2, b3, b4, b5, b6, b7, b8, b9;
    public JColorChooser colorChooser;

    public FilterGhostFrame() {
        super("Filter Ghosts y Type");
        setLayout(new GridLayout(3, 3));
        setSize(500, 500);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton b1 = new JButton("CLASS I");
        b1.setBackground(new Color(147, 116, 10)); // Brown
        add(b1);

        JButton b2 = new JButton("CLASS II");
        b2.setBackground(new Color(147, 100, 75)); // Purple
        add(b2);
        JButton b3 = new JButton("CLASS III");
        b3.setBackground(new Color(100, 162, 139));
        add(b3);
        JButton b4 = new JButton("CLASS IV");
        b4.setBackground(new Color(66, 100, 63));
        add(b4);
        JButton b5 = new JButton("CLASS V");
        b5.setBackground(new Color(106, 86, 159));
        add(b5);
        JButton b6 = new JButton("CLASS VI");
        b6.setBackground(new Color(232, 216, 163));
        add(b6);
        JButton b7 = new JButton("CLASS VII");
        b7.setBackground(new Color(232, 173, 163));
        add(b7);
        JButton b8 = new JButton("CLASS VIII");
        b8.setBackground(new Color(174, 163, 232));
        add(b8);
        JButton b9 = new JButton("CLASS IX");
        b9.setBackground(new Color(110, 110, 110));
        add(b9);

    }

    public JButton getB1() {
        return b1;
    }

    public void setB1(JButton b1) {
        this.b1 = b1;
    }

    public JButton getB2() {
        return b2;
    }

    public void setB2(JButton b2) {
        this.b2 = b2;
    }

    public JButton getB3() {
        return b3;
    }

    public void setB3(JButton b3) {
        this.b3 = b3;
    }

    public JButton getB4() {
        return b4;
    }

    public void setB4(JButton b4) {
        this.b4 = b4;
    }

    public JButton getB5() {
        return b5;
    }

    public void setB5(JButton b5) {
        this.b5 = b5;
    }

    public JButton getB6() {
        return b6;
    }

    public void setB6(JButton b6) {
        this.b6 = b6;
    }

    public JButton getB7() {
        return b7;
    }

    public void setB7(JButton b7) {
        this.b7 = b7;
    }

    public JButton getB8() {
        return b8;
    }

    public void setB8(JButton b8) {
        this.b8 = b8;
    }

    public JButton getB9() {
        return b9;
    }

    public void setB9(JButton b9) {
        this.b9 = b9;
    }

    public static void main(String[] args) {
        new FilterGhostFrame();
    }

}

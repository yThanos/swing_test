package dev.fraporti.test;

import javax.swing.*;
import java.awt.*;

/**
 * @author vitor.rosmann on 19/03/2025
 */
public class Main {
    public static void main(String[] args) {
        GridFrame frame = new GridFrame(6, 5, "Grid Frame");

        final JPanel drawer = new JPanel();
        drawer.setBackground(Color.GRAY);
        frame.addToGrid(drawer, 6, 1, 0, 0);

        final JPanel header = new JPanel();
        header.setBackground(Color.GRAY);
        frame.addToGrid(header, 1, 4, 1, 0);

        final JPanel body = new JPanel();
        body.setBackground(Color.DARK_GRAY);
        frame.addToGrid(body,5, 4, 1, 1);

        JPanel headerTitlePanel = new JPanel();

        JLabel headerTitle = new JLabel();
        headerTitle.setText("Example of swing application");
        headerTitlePanel.add(headerTitle);

        header.add(headerTitlePanel);

        JPanel buttonsPanel = new JPanel();

        buttonsPanel.add(new JButton("Botão 1"));
        buttonsPanel.add(new JButton("Botão 2"));
        buttonsPanel.add(new JButton("Botão 3"));

        header.add(buttonsPanel);

        for(Component c: frame.getContentPane().getComponents()){
            System.out.println(c.getBackground().toString() + " " + c.getBounds());
        }

        frame.setVisible();
    }
}

package dev.fraporti.test;

import javax.swing.*;
import java.awt.*;

/**
 * @author vitor.rosmann on 19/03/2025
 */
public class Main {
    public static void main(String[] args) {
        GridFrame frame = new GridFrame(4, 6, "Grid Frame");

        JPanel p1 = new JPanel();
        p1.setBackground(Color.BLUE);
        frame.addToGrid(p1, 4, 1, 0, 0);

        JPanel p2 = new JPanel();
        p2.setBackground(Color.GREEN);
        frame.addToGrid(p2, 1, 5, 1, 0);

        JPanel p3 = new JPanel();
        p3.setBackground(Color.RED);
        frame.addToGrid(p3,3, 5, 1, 1);

        for(Component c: frame.getContentPane().getComponents()){
            System.out.println(c.getBackground().toString() + " " + c.getBounds());
        }

        frame.setVisible();
    }
}

package dev.fraporti.test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.List;

/**
 * @author vitor.rosmann on 19/03/2025
 */
public class GridFrame extends JFrame {
    private final int rows;
    private final int columns;

    private final int[][] matriz;

    private int tileWidth;
    private int tileHeight;

    private final List<DynamicComponent> components = new ArrayList<>();

    /**
     * Creates a frame divided like grid
     * @param rows the number of rows te grid will have
     * @param columns the number of columns the grid will have
     * @param title the title of the grid frame
     */
    public GridFrame(int rows, int columns, String title){
        this.rows = rows;
        this.columns = columns;

        this.matriz = new int[columns][rows];

        this.tileWidth = (1200 / columns);
        this.tileHeight = (800 / rows);

        addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                tileHeight = e.getComponent().getHeight() / rows;
                tileWidth = e.getComponent().getWidth() / columns;
                components.forEach(c->c.resize(tileWidth, tileHeight));
            }
            @Override
            public void componentMoved(ComponentEvent e) {}
            @Override
            public void componentShown(ComponentEvent e) {}
            @Override
            public void componentHidden(ComponentEvent e) {}
        });

        setSize(1200, 800);
        setTitle(title);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void addToGrid(Component component, int rows, int columns, int x, int y){
        if(rows + y > this.rows || columns + x > this.columns){
            throw new RuntimeException("Tamanho invalido!");
        }
        for(int i = 0; i < this.columns; i++){
            for(int j = 0; j < this.rows; j++){
                if ((i >= x && i < x + columns) && (j >= y && j < y + rows)) {
                    if(this.matriz[i][j] != 0){
                        throw new RuntimeException("Já tem algo nesse tile");
                    }
                    this.matriz[i][j] = 1;
                    System.out.println("setou o tile: " + i + " " + j);
                }
            }
        }

        int height = rows * tileHeight, width = columns * tileWidth;

        System.out.println(columns + " " + tileWidth + " " + width);
        System.out.println(rows + " "+ tileHeight + " " + height);

        this.components.add(new DynamicComponent(component, x, y, rows, columns));
        component.setBounds(x, y, width, height);
        add(component);
        System.out.println(component.getBounds());
    }

    public void setVisible() {
        setVisible(true);
    }
}

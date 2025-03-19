package dev.fraporti.test;

import java.awt.*;

/**
 * @author vitor.rosmann on 19/03/2025
 */
public class DynamicComponent {
    private final Component component;
    private final int x, y, width, height;

    public DynamicComponent(Component component, int x, int y, int height, int width){
        this.component = component;
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
    }

    public void resize(int tileWidth, int tileHeight){
        this.component.setBounds(x * tileWidth, y * tileHeight, width * tileWidth, height * tileHeight);
    }
}

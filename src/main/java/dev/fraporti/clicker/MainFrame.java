package dev.fraporti.clicker;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author vitor.rosmann on 19/03/2025
 */
public class MainFrame extends JFrame {
    private final static JButton cookie = new JButton();
    private static final JLabel cookies = new JLabel();

    private static final ScheduledExecutorService scheduler = new ScheduledThreadPoolExecutor(1);

    private static int cookieAmount = 0;
    private static int clickMultiplier = 1;
    private static int clickIncrementPrice = 10;

    private static final Object monitor = new Object();

    public MainFrame() throws IOException {
        this.setTitle("Cookie clicker DEMO");
        this.setSize(200, 200);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        //this.setLayout(new BorderLayout());

        JPanel cookiesPanel = new JPanel();
        cookiesPanel.setBounds(0,0,200,100);
        cookie.setText("COOKIE");
        cookie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                synchronized (monitor){
                    cookieAmount += clickMultiplier;
                }
            }
        });
        cookiesPanel.add(cookie);
        cookiesPanel.add(cookies);

        this.add(cookiesPanel);

        scheduler.scheduleAtFixedRate(()->{
            synchronized (monitor){
                cookies.setText(cookieAmount + " cookies");
            }
        }, 0, 200, TimeUnit.MILLISECONDS);

        JPanel upgradesPanel = getUpgradesPanel();
        upgradesPanel.setBounds(0, 100, 200, 100);
        this.add(upgradesPanel);
    }

    private static JPanel getUpgradesPanel() {
        JPanel upgradesPanel = new JPanel();
        JButton incrementClickMultiplier = new JButton("Increment click strength");
        incrementClickMultiplier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                synchronized (monitor){
                    if(cookieAmount > clickIncrementPrice){
                        clickMultiplier++;
                        cookieAmount -= clickIncrementPrice;
                        clickIncrementPrice += (int) (clickIncrementPrice * 1.2);
                    }
                }
            }
        });
        upgradesPanel.add(incrementClickMultiplier);
        return upgradesPanel;
    }

    public static void main(String[] args) throws IOException {
        new MainFrame().setVisible(true);
    }
}

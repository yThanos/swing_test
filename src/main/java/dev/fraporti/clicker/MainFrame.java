package dev.fraporti.clicker;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author vitor.rosmann on 19/03/2025
 */
public class MainFrame extends JFrame {
    private static MainFrame mainFrame;

    //cookies
    private final static JButton cookie = new JButton();
    private static final JLabel cookies = new JLabel();
    private static int cookieAmount = 0;

    //click strength
    private static final JLabel clickIncrementLabel = new JLabel();
    private static int clickMultiplier = 1;
    private static int clickIncrementPrice = 10;

    //clicker
    private static final JLabel clickerLabel = new JLabel();
    private static int clickerCPS = 0;
    private static int clickerPrice = 100;

    //grandma
    private static final JLabel grandmaLabel = new JLabel();
    private static int grandmaCPS = 0;
    private static int grandmaPrice = 1000;

    //scheduler for passive income
    private static final ScheduledExecutorService scheduler = new ScheduledThreadPoolExecutor(1);

    //synchronized monitor
    private static final Object monitor = new Object();

    public MainFrame() {
        this.setTitle("Cookie clicker DEMO");
        this.setSize(800, 200);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.setLayout(new GridLayout(1,2));

        //cookie button
        JPanel cookiesPanel = getCookiesPanel();
        this.add(cookiesPanel);

        //Click
        JPanel upgradesPanel = getUpgradesPanel();
        this.add(upgradesPanel);
    }

    private static JPanel getCookiesPanel() {
        JPanel cookiesPanel = new JPanel();
        cookiesPanel.setLayout(new BoxLayout(cookiesPanel, BoxLayout.Y_AXIS));

        cookiesPanel.add(Box.createVerticalStrut(40));

        //cookie amount label
        cookies.setText(cookieAmount + " cookies");
        cookies.setAlignmentX(Component.CENTER_ALIGNMENT);
        cookiesPanel.add(cookies);

        cookie.setText("COOKIE");
        cookie.addActionListener(e-> incrementCookieAmount(TypeOfCookieIncrement.CLICK));
        cookie.setAlignmentX(Component.CENTER_ALIGNMENT);
        cookiesPanel.add(cookie);

        return cookiesPanel;
    }

    private static JPanel getUpgradesPanel() {
        //panel
        JPanel upgradesPanel = new JPanel();
        upgradesPanel.setLayout(new BoxLayout(upgradesPanel, BoxLayout.Y_AXIS));

        clickIncrementLabel.setText("$" + clickIncrementPrice + " cookies");
        clickIncrementLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        upgradesPanel.add(clickIncrementLabel);

        //increment click button
        JButton incrementClickMultiplier = new JButton("Increment click strength");
        incrementClickMultiplier.addActionListener(MainFrame::incrementClickStrength);
        incrementClickMultiplier.setAlignmentX(Component.CENTER_ALIGNMENT);
        upgradesPanel.add(incrementClickMultiplier);

        upgradesPanel.add(Box.createVerticalStrut(10));

        clickerLabel.setText("$" + clickerPrice + " cookies");
        clickerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        upgradesPanel.add(clickerLabel);

        //buy clicker
        JButton buyClicker = new JButton("Buy 1 clicker");
        buyClicker.addActionListener(MainFrame::buyClicker);
        buyClicker.setAlignmentX(Component.CENTER_ALIGNMENT);
        upgradesPanel.add(buyClicker);

        upgradesPanel.add(Box.createVerticalStrut(10));

        grandmaLabel.setText("$" + grandmaPrice + " cookies");
        grandmaLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        upgradesPanel.add(grandmaLabel);

        //buy grandma
        JButton buyGrandma = new JButton("Buy 1 grandma");
        buyGrandma.addActionListener(MainFrame::buyGrandma);
        buyGrandma.setAlignmentX(Component.CENTER_ALIGNMENT);
        upgradesPanel.add(buyGrandma);

        return upgradesPanel;
    }

    private static void buyClicker(Object actionEvent) {
        synchronized (monitor){
            if(cookieAmount >= clickerPrice){
                cookieAmount -= clickerPrice;
                clickerPrice = (int) (clickerPrice * 1.5);
                if(clickerCPS < 2){
                    clickerCPS = 2;
                    scheduler.scheduleAtFixedRate(()->incrementCookieAmount(TypeOfCookieIncrement.CLICKER), 0, 1, TimeUnit.SECONDS);
                } else {
                    clickerCPS = (int)(clickerCPS * 1.5);
                }
                cookies.setText(cookieAmount + " cookies");
                clickerLabel.setText("$" + clickerPrice + " cookies");
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Not enough cookies!");
            }
        }
    }

    private static void incrementClickStrength(Object actionEvent) {
        synchronized (monitor){
            if(cookieAmount >= clickIncrementPrice){
                clickMultiplier++;
                cookieAmount -= clickIncrementPrice;
                clickIncrementPrice = (int) (clickIncrementPrice * 1.2);
                clickIncrementLabel.setText("$" + clickIncrementPrice + " cookies");
                cookies.setText(cookieAmount + " cookies");
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Not enough cookies!");
            }
        }
    }

    private static void incrementCookieAmount(TypeOfCookieIncrement type) {
        synchronized (monitor){
            switch (type){
                case CLICK -> {
                    cookieAmount += clickMultiplier;
                }
                case CLICKER -> {
                    cookieAmount += clickerCPS;
                }
            }
            cookies.setText(cookieAmount + " cookies");
        }
    }

    private static void buyGrandma(Object e){
        synchronized (monitor){
            if(cookieAmount >= grandmaPrice){
                cookieAmount -= grandmaPrice;
                grandmaLabel.setText("$" + grandmaPrice + " cookies");
                grandmaPrice = (int) (grandmaPrice * 1.5);
                if(grandmaCPS < 2){
                    grandmaCPS = 10;
                    scheduler.scheduleAtFixedRate(()->incrementCookieAmount(TypeOfCookieIncrement.CLICKER), 0, 1, TimeUnit.SECONDS);
                } else {
                    grandmaCPS = (int)(grandmaCPS * 1.5);
                }
                cookies.setText(cookieAmount + " cookies");
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Not enough cookies!");
            }
        }
    }

    private enum TypeOfCookieIncrement {
        CLICK,
        CLICKER
    }

    public static void main(String[] args) throws IOException {
        mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    }
}

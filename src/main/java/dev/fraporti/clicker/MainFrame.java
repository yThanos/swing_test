package dev.fraporti.clicker;

import javax.swing.*;
import java.io.IOException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author vitor.rosmann on 19/03/2025
 */
public class MainFrame extends JFrame {
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


    //scheduler for passive income
    private static final ScheduledExecutorService scheduler = new ScheduledThreadPoolExecutor(1);

    //synchronized monitor
    private static final Object monitor = new Object();

    public MainFrame() throws IOException {
        this.setTitle("Cookie clicker DEMO");
        this.setSize(800, 200);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        //cookie button
        JPanel cookiesPanel = getCookiesPanel();
        this.add(cookiesPanel);

        //Click
        JPanel upgradesPanel = getUpgradesPanel();
        this.add(upgradesPanel);


    }

    private static JPanel getCookiesPanel() {
        JPanel cookiesPanel = new JPanel();
        cookiesPanel.setBounds(0,0,200,100);
        cookie.setText("COOKIE");
        cookie.addActionListener(e-> incrementCookieAmount(TypeOfCookieIncrement.CLICK));
        cookiesPanel.add(cookie);

        //cookie amount label
        cookies.setText(cookieAmount + " cookies");
        cookiesPanel.add(cookies);
        return cookiesPanel;
    }

    private static JPanel getUpgradesPanel() {
        //panel
        JPanel upgradesPanel = new JPanel();
        upgradesPanel.setBounds(0, 100, 400, 100);
        //increment click button
        JButton incrementClickMultiplier = new JButton("Increment click strength");
        incrementClickMultiplier.addActionListener(MainFrame::incrementClickStrength);
        upgradesPanel.add(incrementClickMultiplier);
        clickIncrementLabel.setText("$" + clickIncrementPrice + " cookies");
        upgradesPanel.add(clickIncrementLabel);

        //buy clicker
        JButton buyClicker = new JButton("Buy 1 clicker");
        buyClicker.addActionListener(MainFrame::buyClicker);
        clickerLabel.setText("$" + clickerPrice + " cookies");

        upgradesPanel.add(buyClicker);
        upgradesPanel.add(clickerLabel);
        return upgradesPanel;
    }

    private static void buyClicker(Object actionEvent) {
        synchronized (monitor){
            if(cookieAmount >= clickerPrice){
                cookieAmount -= clickerPrice;
                clickerLabel.setText("$" + clickerPrice + " cookies");
                clickerPrice = (int) (clickerPrice * 1.5);
                if(clickerCPS < 2){
                    clickerCPS = 2;
                    scheduler.scheduleAtFixedRate(()->incrementCookieAmount(TypeOfCookieIncrement.CLICKER), 0, 1, TimeUnit.SECONDS);
                } else {
                    clickerCPS = (int)(clickerCPS * 1.5);
                }
                cookies.setText(cookieAmount + " cookies");
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

    private static enum TypeOfCookieIncrement {
        CLICK,
        CLICKER
    }

    public static void main(String[] args) throws IOException {
        new MainFrame().setVisible(true);
    }
}

package com.pj.game.cheats.util;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import javax.swing.ImageIcon;

/**
 *
 * @author PJ
 */
public class AppsSystemTray {

    private SystemTray systemTray;
    private TrayIcon trayIcon;

    public void initSystemTray() {
        try {
            createAndShowSystemTray();
        } catch (AWTException awte) {
            System.out.println("--- cannot initialize system tray ---");
            awte.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createAndShowSystemTray() throws AWTException {
        if (!SystemTray.isSupported()) {
            System.out.println("System Tray is not supported.");
            System.exit(0);
            return;
        }

        systemTray = SystemTray.getSystemTray();
        PopupMenu popupMenu = new PopupMenu("PC Cheats : Personal Collection");
        trayIcon = new TrayIcon(createTrayIcon("/com/pj/game/cheats/images/icon.png"));
        trayIcon.setImageAutoSize(true);
        trayIcon.setToolTip("PC Cheats");

        MenuItem exit = new MenuItem(" Exit ");
        MenuItem aboutMe = new MenuItem(" About Me ");

        exit.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                isClosing();
            }
        });

        popupMenu.add(aboutMe);
        popupMenu.add(exit);

        trayIcon.setPopupMenu(popupMenu);
        try {
            systemTray.add(trayIcon);
        } catch (AWTException awte) {
            System.out.println("System tray icon could not be added.");
            return;
        }
    }

    public void isClosing() {
        System.gc();
        System.out.println("----- Application is closed -----");
        System.exit(0);
    }

    private static Image createTrayIcon(String string) {
        URL imageURL = AppsSystemTray.class.getResource(string);
        if (imageURL == null) {
            return null;
        } else {
            return (new ImageIcon(imageURL).getImage());
        }
    }
}

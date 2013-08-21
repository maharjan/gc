package com.pj.game.cheats.util;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import com.pj.game.cheats.ui.AddNewCheat;
import com.pj.game.cheats.ui.CheatTable;
import com.pj.game.cheats.ui.GameCheats;


/**
 *
 * @author Sherif
 */
public class RightClickPopupMenu implements ActionListener, ItemListener {

    private GameCheats mainFrame;
    private AddNewCheat addNewCheat;
    private CheatTable cheatTable;

    public RightClickPopupMenu(GameCheats f) {
        mainFrame = f;
        try {
            createAndShowPopupMenu();
        } catch (Exception e) {
        }
    }

    public void createAndShowPopupMenu() {
        String title = "Right click menu.";
        Font font = new Font("Bookman Old Style", 0, 14);
        JPopupMenu popup = new JPopupMenu(title);

        JMenuItem updateMenu = new JMenuItem(" Update ");
        JMenuItem clearMenu = new JMenuItem(" Clear ");
        JMenuItem deleteMenu = new JMenuItem(" Delete ");

        popup.add(updateMenu);
        popup.add(clearMenu);
        popup.add(deleteMenu);

        MouseListener popupListener = new PopupListener(popup);
        mainFrame.pcGameInfoList.addMouseListener(popupListener);
        mainFrame.tabbedPane.addMouseListener(popupListener);
        addNewCheat.centralPanel.addMouseListener(popupListener);
        cheatTable.centerPanel.addMouseListener(popupListener);
        cheatTable.cheatDisplayTable.addMouseListener(popupListener);

    }

    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void itemStateChanged(ItemEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

class PopupListener extends MouseAdapter {

    JPopupMenu popup;

    PopupListener(JPopupMenu popupMenu) {
        popup = popupMenu;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        maybeShowPopup(e);
    }

    public void mouseReleased(MouseEvent e) {
        maybeShowPopup(e);
    }

    private void maybeShowPopup(MouseEvent e) {
        if (e.isPopupTrigger()) {
            popup.show(e.getComponent(),
                    e.getX(), e.getY());
        }
    }
}

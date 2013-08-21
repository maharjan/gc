package com.pj.game.cheats.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;

/**
 *
 * @author Sherif
 */
public class GameWalkthrough extends JPanel {

    private GameCheats mainFrame;
    private JPanel mainPanel;
    private JScrollPane editorScrollPane;
    private JEditorPane editorPane;
    public String walkthroughURL;
    private Dimension dimension;

    public GameWalkthrough(GameCheats mf) {
        walkthroughURL = "";

        mainFrame = mf;
//        createAndShowUI();
        mainPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
        mainPanel.add(editorPane, BorderLayout.CENTER);
        mainPanel.updateUI();
        add(mainPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    public JEditorPane createAndShowUI() {
        dimension = Toolkit.getDefaultToolkit().getScreenSize();
        editorPane = new JEditorPane();
//        editorScrollPane = new JScrollPane(editorPane);
        editorPane.setPreferredSize(new Dimension((int) dimension.getWidth() - 470, 150));
        editorPane.setText("-->>");
        return editorPane;
    }
}

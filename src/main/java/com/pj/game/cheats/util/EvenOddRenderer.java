package com.pj.game.cheats.util;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import org.jdesktop.swingx.renderer.DefaultTableRenderer;

/**
 * Supports both JTable and JXTable cell rendering
 * 
 * @author prajin
 */
public class EvenOddRenderer implements TableCellRenderer {

    
    public static final DefaultTableRenderer DEFAULT_RENDERER = new DefaultTableRenderer(); 

    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        
    	Component renderer = DEFAULT_RENDERER.getTableCellRendererComponent(
                table, value, isSelected, hasFocus, row, column);
        ((JLabel) renderer).setOpaque(true);
        Color foreground, background;

        /**
         * List of color in RGB
         * 1)169, 194, 217
         * 2)198, 220, 240
         * 3)215, 230, 245
         * 4)225, 232, 235
         * 5)222, 222, 222(Feble light grey)
         * 6)235, 235, 235(Near White)
         * 7)230, 230, 230(Near White)
         * 8)215, 180, 150
         * 
         * List of Color in RGB for cell selected case
         * 1)160, 195, 230
         * 2)175, 200, 225
         * 3)225, 200, 175(Orange with light gray mixed - light brown)
         * 4)125, 165, 210
         */
        if (isSelected) {
            foreground = Color.WHITE;
            background = new Color(125, 165, 210);
        } else {
            if (row % 2 == 0) {
                foreground = Color.BLACK;
                background = Color.white;
            } else {
                foreground = Color.BLACK;
                background = new Color(215, 215, 215);
            }
        }

        renderer.setForeground(foreground);
        renderer.setBackground(background);
        return renderer;
    }
}

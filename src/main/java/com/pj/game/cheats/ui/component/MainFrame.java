package com.pj.game.cheats.ui.component;

import java.awt.Component;
import java.io.Serializable;

import javax.swing.JFrame;


/**
 * Provide JFrame singleton instance
 * 
 * @author pj
 * 
 */
public class MainFrame implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3819758363647790125L;
	
	private static JFrame rootPanel;

	public MainFrame() {
		// DO nothing
	}

	public static synchronized JFrame getInstance() {
		if (rootPanel == null) {
			rootPanel = new JFrame();
		}
		return rootPanel;
	}

	public static void add(Component component, Object object) {
		getInstance().add(component, object);
	}

	public static void show() {
		if (!rootPanel.isVisible()) {
			rootPanel.setVisible(true);
		}
	}

	public static void hide() {
		if (rootPanel.isVisible()) {
			rootPanel.setVisible(false);
		}
	}

	public static void dispose() {
		if (rootPanel != null) {
			rootPanel.dispose();
		}
	}

	/**
	 * Close/exit the application. Disposes the <strong>main JFrame</strong>.
	 */
	public static void applicationExit() {
		System.gc();
		dispose();
		System.exit(0);
	}
}

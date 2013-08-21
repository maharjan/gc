package com.pj.game.cheats.ui.component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import com.pj.game.cheats.core.db.ConnectionController;
import com.pj.game.cheats.util.FontUtil;

/**
 * Generate menu contents and their action in predefined order.
 * 
 * @author pj
 *
 */
public class MenuNavigationBar {

	public JMenuBar show() {
		 
		JMenuItem newFileMenuItem = new JMenuItem("New ");
		newFileMenuItem.setFont(FontUtil.font14());
		newFileMenuItem.setMnemonic(KeyEvent.VK_N);
		newFileMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
		newFileMenuItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				ConnectionController connectionController = new ConnectionController();
				// connectionController.dropAlltables();
			}
		});

		JMenuItem printFileMenuItem = new JMenuItem("Print.. ");
		printFileMenuItem.setFont(FontUtil.font14());
		printFileMenuItem.setMnemonic(KeyEvent.VK_P);
		printFileMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_DOWN_MASK));
		printFileMenuItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				System.out.println("...commencing printing...");
			}
		});

		JMenuItem exitAppMenuItem = new JMenuItem("Exit! ");
		exitAppMenuItem.setFont(FontUtil.font14());
		exitAppMenuItem.setMnemonic(KeyEvent.VK_Q);
		exitAppMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_MASK));
		exitAppMenuItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				MainFrame.applicationExit();
			}
		});

		JMenuItem undoEditMenuItem = new JMenuItem("Undo ");
		undoEditMenuItem.setFont(FontUtil.font14());
		undoEditMenuItem.setMnemonic(KeyEvent.VK_Z);
		undoEditMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK));
		undoEditMenuItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
			}
		});

		JMenuItem redoEditMenuItem = new JMenuItem("Redo ");
		redoEditMenuItem.setFont(FontUtil.font14());
		redoEditMenuItem.setMnemonic(KeyEvent.VK_Y);
		redoEditMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_DOWN_MASK));
		redoEditMenuItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
			}
		});

		JMenuItem cutEditMenuItem = new JMenuItem("Cut ");
		cutEditMenuItem.setFont(FontUtil.font14());
		cutEditMenuItem.setMnemonic(KeyEvent.VK_X);
		cutEditMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK));
		cutEditMenuItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
			}
		});

		JMenuItem copyEditMenuItem = new JMenuItem("Copy ");
		copyEditMenuItem.setFont(FontUtil.font14());
		copyEditMenuItem.setMnemonic(KeyEvent.VK_C);
		copyEditMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
		copyEditMenuItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
			}
		});

		JMenuItem pasteEditMenuItem = new JMenuItem("Paste ");
		pasteEditMenuItem.setFont(FontUtil.font14());
		pasteEditMenuItem.setMnemonic(KeyEvent.VK_V);
		pasteEditMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK));
		pasteEditMenuItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
			}
		});

		JMenuItem addUserOptionMenuItem = new JMenuItem("Add User ");
		addUserOptionMenuItem.setFont(FontUtil.font14());
		addUserOptionMenuItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				System.out.println("...new user panel called...");
			}
		});

		JMenuItem helpContentHelpMenuItem = new JMenuItem("Help Content ");
		helpContentHelpMenuItem.setFont(FontUtil.font14());
		helpContentHelpMenuItem.setMnemonic(KeyEvent.VK_F1);
		helpContentHelpMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		helpContentHelpMenuItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				System.out.println("");
			}
		});

		JMenu fileMenu = new JMenu(" File ");
		fileMenu.setFont(FontUtil.font14());
		fileMenu.add(newFileMenuItem);
		fileMenu.add(printFileMenuItem);
		fileMenu.add(exitAppMenuItem);

		JMenu editMenu = new JMenu(" Edit ");
		editMenu.setFont(FontUtil.font14());
		editMenu.add(undoEditMenuItem);
		editMenu.add(redoEditMenuItem);
		editMenu.add(cutEditMenuItem);
		editMenu.add(copyEditMenuItem);
		editMenu.add(pasteEditMenuItem);

		JMenu optionMenu = new JMenu(" Option ");
		optionMenu.setFont(FontUtil.font14());
		optionMenu.add(addUserOptionMenuItem);

		JMenu helpMenu = new JMenu(" Help ");
		helpMenu.setFont(FontUtil.font14());
		helpMenu.add(helpContentHelpMenuItem);

		JMenuBar mainMenu = new JMenuBar();
		mainMenu.add(fileMenu);
		mainMenu.add(editMenu);
		mainMenu.add(optionMenu);
		mainMenu.add(helpMenu);
		return mainMenu;
	}
	
	
}

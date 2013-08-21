package com.pj.game.cheats.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import com.pj.game.cheats.core.db.ConnectionController;
import com.pj.game.cheats.entity.GameCheatCode;
import com.pj.game.cheats.entity.GameInformation;
import com.pj.game.cheats.entity.GameInstruction;
import com.pj.game.cheats.util.EvenOddRenderer;
import com.pj.game.cheats.util.TableColumnAdjuster;

/**
 * 
 * @author PJ
 *
 */
public class CheatTable extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4030837268326853743L;
	private GameCheats mainFrame;
	public JTable cheatDisplayTable;
	public JTable gameInstructionDisplayTable;
	public JTable gameInformationDisplayTable;
	public JScrollPane cheatTableScrollPane;
	public JScrollPane gameInformationTableScrollPane;
	public JScrollPane gameInstructionTableScrollPane;
	private ConnectionController connection;
	public JPanel centerPanel;
	public JPanel subCenterPanel;
	public JPanel mainPanel;
	private Font font;
	private Font fontSmall;
	private Dimension dimension;

	public CheatTable(GameCheats mf) {
		mainFrame = mf;
		dimension = Toolkit.getDefaultToolkit().getScreenSize();

		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout(5, 5));
		centerPanel = new JPanel();
		subCenterPanel = new JPanel();
		mainPanel.add(centerPanel, BorderLayout.NORTH);
		mainPanel.add(subCenterPanel, BorderLayout.SOUTH);
		font = new Font("Bookman Old Style", Font.TRUETYPE_FONT, 16);
		fontSmall = new Font("Bookman Old Style", 0, 14);

		add(mainPanel);
		getAndShowCheatTable();
		setVisible(true);
	}

	/**
	 * It is for showing entire table that'll contain cheats and it's effect.
	 * If the user choice is 1st row('All') then only this table will be shown.
	 * If other choice, then this table with other table(below) will be show
	 * 
	 * @return JTable
	 */
	public JTable getAndShowCheatDisplayTable() {
		connection = new ConnectionController();
		String[] header = { "S No.", "Game Name", "Cheat Code", "Result", "Remarks" };
		final DefaultTableModel defaultModel;
		defaultModel = new DefaultTableModel(header, 0);

		cheatDisplayTable = new JTable(defaultModel);
		cheatDisplayTable.setFont(font);
		cheatTableScrollPane = new JScrollPane(cheatDisplayTable);
		cheatTableScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		cheatTableScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		cheatTableScrollPane.setPreferredSize(new Dimension((int) dimension.getWidth() - 150, (int) dimension.getHeight() - 420));
		cheatDisplayTable.setAutoscrolls(true);
		cheatDisplayTable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
		cheatDisplayTable.setShowHorizontalLines(true);
		cheatDisplayTable.setShowVerticalLines(true);
		cheatDisplayTable.setCellSelectionEnabled(true);

		TableCellRenderer renderer = new EvenOddRenderer();
		cheatDisplayTable.setDefaultRenderer(Object.class, renderer);
		TableColumnAdjuster tca = new TableColumnAdjuster(cheatDisplayTable);
		int tempId = 0;
		tempId = mainFrame.tempGameId;
		int prevGameId = 0;
		int nextGameId = 0;
		if (tempId > 0) {
			cheatDisplayTable.setAutoCreateRowSorter(true);
		}
		List<GameCheatCode> gameCheatCode = connection.getAndShowAllGameCheatCode(tempId);
		boolean checkGameInfoValue = false;
		try {
			checkGameInfoValue = gameCheatCode.isEmpty();
		} catch (NullPointerException npe) {
			return null;
		}
		if (checkGameInfoValue == true) {
			JOptionPane.showMessageDialog(null, "No cheat data found for \"" + mainFrame.pcGameTitle + "\"");
			return null;
		} else {
			for (int i = 0; i < gameCheatCode.size(); i++) {
				GameCheatCode gameInfo = gameCheatCode.get(i);
				Object[] rowData = new Object[5];
				// is the row clicked is first? If yes, then this condition
				if (i == 0) {
					rowData[0] = i + 1;
					rowData[1] = gameInfo.getGameId();
					rowData[2] = gameInfo.getPcCheatCode();
					rowData[3] = gameInfo.getCheatCodeResult();
					rowData[4] = gameInfo.getCheatCodeRemark();
					prevGameId = nextGameId = gameInfo.getGameId();
				}
				// if not the 1st row, do this for the first time
				if (prevGameId == nextGameId) {
					rowData[0] = i + 1;
					rowData[1] = gameInfo.getGameId();
					rowData[2] = gameInfo.getPcCheatCode();
					rowData[3] = gameInfo.getCheatCodeResult();
					rowData[4] = gameInfo.getCheatCodeRemark();
					nextGameId = gameInfo.getGameId();
				}
				// check condition
				if (nextGameId > prevGameId) {
					i--;
					rowData[0] = null;
					rowData[1] = null;
					rowData[2] = null;
					rowData[3] = null;
					rowData[4] = null;
					prevGameId = nextGameId;
				}
				defaultModel.addRow(rowData);
			}
		}
		// cheatDisplayTable.doLayout();
		cheatDisplayTable.doLayout();
		cheatDisplayTable.setRowHeight(20);
		// cheatDisplayTable.getColumn("S No.").setMaxWidth(50);
		// cheatDisplayTable.getColumn("S No.").setMinWidth(50);
		//
		if (tempId > 0) {
			cheatDisplayTable.removeColumn(cheatDisplayTable.getColumn("Game Name"));
		}
		//
		cheatDisplayTable.setRowHeight(18);
		tca.adjustColumns();
		cheatDisplayTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			public void valueChanged(ListSelectionEvent e) {
			}
		});
		return cheatDisplayTable;
	}

	/**
	 * It is for the showing game information like who's manufacture,
	 * released date, bersion and game type (Genre)
	 * 
	 * @return JTable
	 */
	public JTable getAndShowGameInformationTable() {
		connection = new ConnectionController();
		String[] header = { "Publisher", "Genre", "Version", "Released Date" };
		final DefaultTableModel defaultModel;
		defaultModel = new DefaultTableModel(header, 0);

		gameInformationDisplayTable = new JTable(defaultModel);
		gameInformationDisplayTable.setFont(fontSmall);
		gameInformationDisplayTable.setPreferredSize(new Dimension((int) dimension.getWidth() - 470, 50));
		gameInformationTableScrollPane = new JScrollPane(gameInformationDisplayTable);
		gameInformationTableScrollPane.setPreferredSize(new Dimension((int) dimension.getWidth() - 470, 50));
		gameInformationTableScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		gameInformationDisplayTable.setAutoscrolls(true);
		gameInformationDisplayTable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
		gameInformationDisplayTable.setShowHorizontalLines(true);
		gameInformationDisplayTable.setShowVerticalLines(true);
		gameInformationDisplayTable.setCellSelectionEnabled(true);

		TableColumnAdjuster tca = new TableColumnAdjuster(gameInformationDisplayTable);
		int tempId = 0;
		tempId = mainFrame.tempGameId;

		List<GameInformation> gameInformation = connection.getAndShowAllGameInformation(tempId);
		for (int i = 0; i < gameInformation.size(); i++) {
			GameInformation gameInfo = gameInformation.get(i);
			Object[] rowData = new Object[4];
			rowData[0] = gameInfo.getGamePublisher();
			rowData[1] = gameInfo.getGameGenre();
			rowData[2] = gameInfo.getGameVersion();
			rowData[3] = gameInfo.getReleasedDate();
			System.out.println(" ... then " + rowData[0]);
			defaultModel.addRow(rowData);
		}
		gameInformationDisplayTable.getColumn("Publisher").setMinWidth(200);
		// gameInformationDisplayTable.getColumn("Genre").setMinWidth(100);
		// gameInformationDisplayTable.getColumn("Genre").setMaxWidth(150);
		// gameInformationDisplayTable.getColumn("Version").setMinWidth(120);
		// gameInformationDisplayTable.getColumn("Version").setMaxWidth(120);
		// gameInformationDisplayTable.getColumn("Released Date").setMinWidth(100);
		// gameInformationDisplayTable.getColumn("Released Date").setMaxWidth(100);
		gameInformationDisplayTable.setRowHeight(20);
		tca.adjustColumns();
		return gameInformationDisplayTable;
	}

	/**
	 * It shows the game instruction if any,
	 * like how to play game, what must be done to enter cheats etc.
	 * It is available when user clicks in row greater than 1st( row with data
	 * "All")
	 * 
	 * @return JTable
	 */
	public JTable getAndShowGameInstructionTable() {
		connection = new ConnectionController();
		String[] header = { "Instruction", "Last Updated" };
		final DefaultTableModel defaultModel;
		defaultModel = new DefaultTableModel(header, 0);

		gameInstructionDisplayTable = new JTable(defaultModel);
		gameInstructionDisplayTable.setFont(fontSmall);
		// gameInstructionDisplayTable.setPreferredSize(new Dimension((int)
		// dimension.getWidth() - 20, 50));
		gameInstructionTableScrollPane = new JScrollPane(gameInstructionDisplayTable);
		gameInstructionTableScrollPane.setPreferredSize(new Dimension((int) dimension.getWidth() - 470, 50));
		gameInstructionTableScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		gameInstructionDisplayTable.setAutoscrolls(true);
		gameInstructionDisplayTable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
		gameInstructionDisplayTable.setShowHorizontalLines(true);
		gameInstructionDisplayTable.setShowVerticalLines(true);
		gameInstructionDisplayTable.setCellSelectionEnabled(true);

		TableCellRenderer renderer = new EvenOddRenderer();
		gameInstructionDisplayTable.setDefaultRenderer(Object.class, renderer);
		TableColumnAdjuster tea = new TableColumnAdjuster(gameInstructionDisplayTable);
		int tempId = 0;
		tempId = mainFrame.tempGameId;
		String gameInstHold = null;
		List<GameInstruction> gameInstruction = connection.getAndShowGameInstruction(tempId);
		boolean checkGameInfoValue = false;
		try {
			checkGameInfoValue = gameInstruction.isEmpty();
		} catch (NullPointerException npe) {
			JOptionPane.showMessageDialog(null, "No game instruction found.");
			return null;
		}
		if (checkGameInfoValue == true) {
			return null;
		} else {
			for (int i = 0; i < gameInstruction.size(); i++) {
				GameInstruction gameNote = gameInstruction.get(i);
				Object[] rowData = new Object[2];
				gameInstHold = gameNote.getGameInstruction();
				rowData[0] = gameInstHold;
				rowData[1] = gameNote.getLastUpdate();

				defaultModel.addRow(rowData);
			}
		}
		gameInstructionDisplayTable.getColumn("Last Updated").setMinWidth(100);
		gameInstructionDisplayTable.getColumn("Last Updated").setMaxWidth(100);
		gameInstructionDisplayTable.setRowHeight(20);
		return gameInstructionDisplayTable;
	}

	/**
     *
     */
	public void getAndShowCheatTable() {
		// cheatDisplayTable.removeAll();
		// cheatTableScrollPane.removeAll();
		centerPanel.removeAll();
		subCenterPanel.removeAll();
		subCenterPanel.setVisible(false);
		int hsbPolicy = JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED;
		int vsbPolicy = JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED;

		getAndShowCheatDisplayTable();
		cheatDisplayTable.revalidate();
		cheatTableScrollPane.updateUI();
		JScrollPane tableViewScrollPane = new JScrollPane();

		if (mainFrame.tempGameId == 0) {
			tableViewScrollPane = new JScrollPane(cheatTableScrollPane, vsbPolicy, hsbPolicy);
			tableViewScrollPane.setPreferredSize(new Dimension((int) dimension.getWidth() - 470, (int) dimension.getHeight() - 250));
		}
		if (mainFrame.tempGameId > 0) {
			tableViewScrollPane = new JScrollPane(cheatTableScrollPane, vsbPolicy, hsbPolicy);
			tableViewScrollPane.setPreferredSize(new Dimension((int) dimension.getWidth() - 470, (int) dimension.getHeight() - 400));

			subCenterPanel.removeAll();
			subCenterPanel.setLayout(new BorderLayout(0, 10));

			getAndShowGameInstructionTable();
			gameInstructionTableScrollPane.updateUI();

			gameInformationDisplayTable = new JTable();
			gameInformationTableScrollPane = new JScrollPane();
			getAndShowGameInformationTable();
			gameInformationTableScrollPane.updateUI();

			subCenterPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Instruction / Information",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new java.awt.Font("Bookman Old Style", Font.ROMAN_BASELINE, 12), Color.BLUE));
			subCenterPanel.add(gameInstructionTableScrollPane, BorderLayout.NORTH);
			subCenterPanel.add(gameInformationTableScrollPane, BorderLayout.CENTER);
			subCenterPanel.setPreferredSize(new Dimension((int) dimension.getWidth() - 480, 140));
			subCenterPanel.updateUI();
			subCenterPanel.setVisible(true);
		}

		String gameName = mainFrame.pcGameTitle;
		centerPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, gameName, TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, new java.awt.Font("Bookman Old Style", Font.ROMAN_BASELINE, 15), Color.BLUE));
		centerPanel.add(tableViewScrollPane);
		centerPanel.updateUI();
		mainPanel.updateUI();
		updateUI();
	}
	// private void createAndShowCheatTab() {
	//
	// }
}

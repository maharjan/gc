package com.pj.game.cheats.ui;

import javax.swing.event.ListSelectionEvent;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import com.pj.game.cheats.core.db.ConnectionController;
import com.pj.game.cheats.entity.GameInformation;
import com.pj.game.cheats.ui.component.MainFrame;
import com.pj.game.cheats.ui.component.MenuNavigationBar;
import com.pj.game.cheats.util.FontUtil;
import com.pj.game.cheats.util.AppsSystemTray;
import com.pj.game.cheats.util.EvenOddRenderer;
import com.pj.game.cheats.util.RightClickPopupMenu;
import com.pj.game.cheats.util.TableColumnAdjuster;

/**
 * 
 * @author PJ
 */
public final class GameCheats {

	private CheatTable cheatTable;
	private AddNewCheat addNewCheat;
	private GameWalkthrough gameWalkthrough;
	private RightClickPopupMenu rightClickPopup;
	private ConnectionController connection;
	public JList pcGameInfoList;
	private JPanel centralPanel;
	private JToolBar upperToolBar;
	private JToolBar lowerToolBar;
	public JTabbedPane tabbedPane;
	private JSplitPane centerSplitPane;
	private JScrollPane tabbedScrollPane;
	private Dimension dimension;
	private AppsSystemTray appsSystemTray;
	private int[] pcInfoListArray;
	public int tempGameId;
	public String pcGameTitle;
	private JTable pcGameListTable;
	private JScrollPane pcGameListTableScrollPane;

	public GameCheats() {

	}

	public void run() {
		tempGameId = 0;
		initializeApplication();
	}

	/**
	 * Create and show all ui necessary for the main frame.
	 * Menubar with menu and menu items.
	 * Menu item performs action, has shortcut keys like(Ctrl + N).
	 * 
	 */
	private void initializeApplication() {
		dimension = Toolkit.getDefaultToolkit().getScreenSize();

		upperToolBar = new JToolBar(JToolBar.HORIZONTAL);
		upperToolBar.setPreferredSize(new Dimension(dimension.width, 30));
		upperToolBar.setRollover(true);
		upperToolBar.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
		upperToolBar.setFloatable(false);

		Insets margin = new Insets(1, 1, 1, 1);
		upperToolBar.setMargin(margin);
		centralPanel = new JPanel();
		centerSplitPane = new JSplitPane();

		getAndShowPcGameListTable();
		createAndShowTabbedPane();
		centerSplitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		centerSplitPane.setPreferredSize(new Dimension(dimension.width - 20, dimension.height - 150));
		centerSplitPane.setRightComponent(tabbedScrollPane);
		centerSplitPane.setLeftComponent(pcGameListTableScrollPane);
		centerSplitPane.setDividerLocation(380);

		lowerToolBar = new JToolBar(JToolBar.HORIZONTAL);
		lowerToolBar.setFloatable(false);
		lowerToolBar.setRollover(true);
		lowerToolBar.setBorder(new BevelBorder(BevelBorder.LOWERED));
		lowerToolBar.setPreferredSize(new Dimension(dimension.width, 25));
		lowerToolBar.setMargin(margin);

		centralPanel.add(upperToolBar, BorderLayout.NORTH);
		centralPanel.add(centerSplitPane, BorderLayout.CENTER);
		centralPanel.add(lowerToolBar, BorderLayout.SOUTH);
		centralPanel.setPreferredSize(new Dimension((int) dimension.getWidth() - 20, (int) dimension.getHeight() - 150));

		MenuNavigationBar menuNavigationBar = new MenuNavigationBar();

		MainFrame.getInstance().setTitle("-: Game Cheats :-");
		MainFrame.getInstance().pack();
		MainFrame.getInstance().add(menuNavigationBar.show(), BorderLayout.NORTH);
		MainFrame.getInstance().add(centralPanel, BorderLayout.CENTER);
		MainFrame.getInstance().setFont(FontUtil.font14());
		Image image = createFrameIcon("/com/pj/game/cheats/images/gc.png");
		MainFrame.getInstance().setIconImage(image);
		MainFrame.getInstance().setMinimumSize(new Dimension(350, 150));
		MainFrame.getInstance().setExtendedState(MainFrame.getInstance().getExtendedState() | JFrame.MAXIMIZED_BOTH);
		MainFrame.getInstance().setResizable(true);
		MainFrame.getInstance().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		MainFrame.getInstance().setVisible(true);
	}

	/**
	 * Create an show tabbed pane with specified dimensions.
	 * Add two tabs, one for showing table and other for entering new cheats.
	 */
	public void createAndShowTabbedPane() {
		tabbedPane = new JTabbedPane();
		tabbedPane.setTabPlacement(JTabbedPane.TOP);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		tabbedPane.setFont(FontUtil.font14());
		tabbedScrollPane = new JScrollPane(tabbedPane);
		tabbedScrollPane.setPreferredSize(new Dimension(dimension.width - 470, dimension.height - 350));
		cheatTable = new CheatTable(this);
		addNewCheat = new AddNewCheat(this);
		// gameWalkthrough = new GameWalkthrough(this);
		tabbedPane.addTab("PC Cheats Table", cheatTable);
		// tabbedPane.addTab("Walkthrough", gameWalkthrough);
		tabbedPane.addTab("New Cheats", addNewCheat);
		tabbedPane.addChangeListener(new ChangeListener() {

			public void stateChanged(ChangeEvent e) {
			}
		});
	}

	/**
	 * Create JTable for listing all available games from database.<br/>
	 * 1.First row is for listing all games.<br/>
	 * 2.Other rows are for listing individual game cheats.<br/>
	 * <br/>
	 * Has three colomns, first for game Serial Number, second for game
	 * genre(like FPS or RPG) and
	 * third for Game Title.<br/>
	 * <br/>
	 * 
	 * <p>
	 * If there is no data related to the game in database then it returns null
	 * and message.
	 * </p>
	 * 
	 * @return JTable
	 */
	public JTable getAndShowPcGameListTable() {
		connection = new ConnectionController();
		String[] header = { "", " ", "PC Game" };
		final DefaultTableModel defaultModel;
		defaultModel = new DefaultTableModel(header, 0);

		pcGameListTable = new JTable(defaultModel);
		pcGameListTableScrollPane = new JScrollPane(pcGameListTable);
		pcGameListTableScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		pcGameListTableScrollPane.setPreferredSize(new Dimension(330, (int) dimension.getHeight() - 150));
		pcGameListTable.setFont(new Font("Bookman Old Style", Font.ROMAN_BASELINE, 16));
		pcGameListTable.setPreferredSize(new Dimension(330, (int) dimension.getHeight() - 150));
		pcGameListTable.setAutoscrolls(true);
		pcGameListTable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
		pcGameListTable.setShowHorizontalLines(true);
		pcGameListTable.setShowVerticalLines(true);
		TableCellRenderer renderer = new EvenOddRenderer();
		pcGameListTable.setDefaultRenderer(Object.class, renderer);
		TableColumnAdjuster tea = new TableColumnAdjuster(pcGameListTable);
		List<GameInformation> gameInformation = new ArrayList<GameInformation>();
		gameInformation = connection.getAndShowAllGameInformation(0);
		pcInfoListArray = new int[gameInformation.size() + 1];

		String tempHold = null;
		String firstPart = null;
		String mainTitle = null;
		String subTitle = null;

		Object[] rowData = new Object[3];
		rowData[0] = rowData[1] = "";
		rowData[2] = "ALL";
		pcInfoListArray[0] = 0;
		defaultModel.addRow(rowData);

		for (int i = 0; i < gameInformation.size(); i++) {
			GameInformation info = gameInformation.get(i);
			int id = i + 1;

			pcInfoListArray[i + 1] = info.getId();
			tempHold = info.getGameGenre();
			firstPart = tempHold.substring(0, 3);
			mainTitle = info.getGameTitle();
			subTitle = info.getGameSubTitle();
			rowData[0] = i + 1;
			rowData[1] = firstPart;
			if (subTitle.equals("") || subTitle.isEmpty()) {
				rowData[2] = mainTitle;
			} else {
				rowData[2] = mainTitle + " : " + subTitle;
			}
			defaultModel.addRow(rowData);
		}
		// for (int j = gameInformation.size(); j > -1; j--) {
		// System.out.println("Test game id is : " + pcInfoListArray[j]);
		// }

		tea.adjustColumn(0);
		pcGameListTable.setRowHeight(25);
		pcGameListTable.getColumn("").setMaxWidth(30);
		pcGameListTable.getColumn("").setMinWidth(30);
		pcGameListTable.getColumn(" ").setMaxWidth(50);
		pcGameListTable.getColumn(" ").setMinWidth(50);
		pcGameListTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			public void valueChanged(ListSelectionEvent e) {
				int rowSelected = pcGameListTable.getSelectedRow();
				tempGameId = pcInfoListArray[rowSelected];
				pcGameTitle = (String) pcGameListTable.getValueAt(rowSelected, 1);
				cheatTable.getAndShowCheatTable();
				System.out.println("JTable row selected is : " + rowSelected);
			}
		});

		return pcGameListTable;
	}

	/**
	 * Get the Icon for application that will appear when application is
	 * running.
	 * 
	 * @param path
	 *            (String)
	 * @return Image
	 */
	private Image createFrameIcon(String path) {
		URL imageURL = GameCheats.class.getResource(path);
		if (imageURL == null) {
			System.err.println("Resource not found " + path);
			return null;
		} else {
			return (new ImageIcon(imageURL).getImage());
		}
	}

	/**
	 * main method of the application.
	 * 
	 * @param args
	 *            (String[])
	 */
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				try {
					// UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
					GameCheats gameCheats = new GameCheats();
					gameCheats.run();
					// } catch (UnsupportedLookAndFeelException ex) {
					// System.out.println("The LaF isn't supported in this platform");
				} catch (Exception e) {
				}
			}
		});
	}
}

package com.pj.game.cheats.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import com.pj.game.cheats.core.db.ConnectionController;
import com.pj.game.cheats.util.EvenOddRenderer;
import com.pj.game.cheats.util.TableColumnAdjuster;

/**
 *
 * @author Sherif
 */
public class AddNewCheat extends JPanel {

    private GameCheats mainFrame;
    public JTable addNewCheatTable;
    public JScrollPane addNewCheatTableScrollPane;
    private ConnectionController connection;
    private Dimension dimension;
    public JPanel centralPanel;
    private JPanel bottomButtonPanel;
    private JButton uploadFromExcelBtn;
    private JButton uploadFromTableBtn;
    private JButton clearBtn;
    private JButton updateBtn;
    private JButton deleteBtn;
    private JTable uploadTable;
    private Font font;

    public AddNewCheat(GameCheats mf) {
        mainFrame = mf;
        dimension = Toolkit.getDefaultToolkit().getScreenSize();
        createAndShowUI();
    }

    /**
     * create and show the UI related to add new cheats to program.
     */
    public void createAndShowUI() {
        dimension = Toolkit.getDefaultToolkit().getScreenSize();
        font = new Font("Bookman Old Style", 0, 12);
        centralPanel = new JPanel();
        bottomButtonPanel = new JPanel();
        Dimension buttonDimension = new Dimension(120, 30);
        uploadFromTableBtn = new JButton("Upload from tables");
        clearBtn = new JButton("Clear");
        updateBtn = new JButton("Update");
        deleteBtn = new JButton("Delete");

        uploadFromTableBtn.setFont(font);
        uploadFromTableBtn.setPreferredSize(buttonDimension);

        updateBtn.setFont(font);
        updateBtn.setSize(buttonDimension);

        clearBtn.setFont(font);
        clearBtn.setPreferredSize(buttonDimension);

        deleteBtn.setFont(font);
        deleteBtn.setSize(buttonDimension);

        bottomButtonPanel.setLayout(new GridLayout(1, 5, 20, 0));
        bottomButtonPanel.setPreferredSize(new Dimension(dimension.width - 470, 30));
        bottomButtonPanel.add(uploadFromTableBtn);
        bottomButtonPanel.add(updateBtn);
        bottomButtonPanel.add(clearBtn);
        bottomButtonPanel.add(deleteBtn);

        getAndShowAddNewCheatTable();
        centralPanel.add(addNewCheatTableScrollPane);
//        centralPanel.setPreferredSize(new Dimension((int) dimension.getWidth() - 330, (int) dimension.getHeight() - 250));
        centralPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        add(centralPanel, BorderLayout.CENTER);
        add(bottomButtonPanel, BorderLayout.SOUTH);

        setPreferredSize(new Dimension((int) dimension.getWidth() - 470, (int) dimension.getHeight() - 200));
        setVisible(true);
    }

    public JTable getAndShowAddNewCheatTable() {
//        connection = new ConnectionController();
        String[] header = {"S No.", "Game ID", "Cheat Code", "Result", "Remarks"};
        final DefaultTableModel defaultModel;
        defaultModel = new DefaultTableModel(header, 0);
        addNewCheatTable = new JTable(defaultModel);
//        cheatDisplayTable.setPreferredSize(new Dimension((int) dimension.getWidth() - 250, dimension.height - 150));
//        cheatDisplayTable.setSize(new Dimension((int) dimension.getWidth() - 250, dimension.height - 150));
        addNewCheatTable.setFont(new Font("Bookman Old Style", 0, 14));
        addNewCheatTableScrollPane = new JScrollPane(addNewCheatTable);
//        cheatTableScrollPane.setSize(new Dimension((int) dimension.getWidth() - 250, dimension.height - 150));
        addNewCheatTableScrollPane.setPreferredSize(new Dimension((int) dimension.getWidth() - 470, (int) dimension.getHeight() - 260));
        addNewCheatTable.setAutoscrolls(true);
        addNewCheatTable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        addNewCheatTable.setShowHorizontalLines(true);
        addNewCheatTable.setShowVerticalLines(true);
        addNewCheatTable.setCellSelectionEnabled(true);
        TableCellRenderer renderer = new EvenOddRenderer();
        addNewCheatTable.setDefaultRenderer(Object.class, renderer);
        TableColumnAdjuster tea = new TableColumnAdjuster(addNewCheatTable);
        for (int i = 0; i < 5; i++) {

            Object[] rowData = new Object[5];
            rowData[0] = null;
            rowData[1] = null;
            rowData[2] = null;
            rowData[3] = null;
            rowData[4] = null;

            defaultModel.addRow(rowData);

        }
        addNewCheatTable.setRowHeight(18);
        return addNewCheatTable;
    }
}

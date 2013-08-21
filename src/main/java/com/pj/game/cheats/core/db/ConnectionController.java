package com.pj.game.cheats.core.db;

import java.io.File;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.swing.JOptionPane;

import org.h2.jdbc.JdbcSQLException;

import com.pj.game.cheats.entity.GameCheatCode;
import com.pj.game.cheats.entity.GameInformation;
import com.pj.game.cheats.entity.GameInstruction;

/**
 * 
 * @author Sherif
 */
public class ConnectionController {

	FirstTimeInsertData firstTimeInsertData;
	Connection connection;
	Properties props;

	public ConnectionController() {
		firstTimeInsertData = new FirstTimeInsertData();
		doConnection();
	}

	public void getApplicationProperties() {
		props = new Properties();
		props.put("user", "pccheats");
		props.put("password", "pcheats1.0.0");
	}

	/**
	 * Make connection
	 * get all necessary attributes from property file(application.properties)
	 */
	public void doConnection() {
		try {
			getApplicationProperties();
			// String driver = "org.apache.derby.jdbc.EmbeddedDriver";
			Class.forName("org.h2.Driver");
			String userHomeDir = System.getProperty("user.dir");
			String databaseUrl = "jdbc:h2:" + userHomeDir + File.separator + "database" + File.separator + "pc_cheat;IFEXISTS=TRUE";
			String databaseCreate = "jdbc:h2:" + userHomeDir + File.separator + "database" + File.separator + "pc_cheat";
			String dbFileLog = userHomeDir + File.separator + "database" + File.separator + "pc_cheat.h2.db";
			File file = new File(dbFileLog);
			if (!file.exists()) {
				connection = DriverManager.getConnection(databaseCreate, props);
				System.out.println("... For the first time");
				createPcGameCheatTables();
				JOptionPane.showMessageDialog(null, "created tables");
				insertDataToTables();
			} else {
				connection = DriverManager.getConnection(databaseUrl, props);
			}
			// System.out.println(":: - Connected to Database Server as user : "
			// + userName);
		} catch (JdbcSQLException jdbce) {
			jdbce.printStackTrace();
			System.out.println("... can't insert data.");
			JOptionPane.showMessageDialog(null, jdbce.getMessage());
		} catch (ClassNotFoundException cnf) {
			System.out.println("-> Class not found Error.");
			cnf.printStackTrace();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Database server isn't running.", "Warning", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			System.out.println("Database server isn't running.");
			System.exit(0);
		}
	}

	/**
	 * Dis-connect the connection if it is alive
	 */
	public void killConnection() {
		try {
			if (connection != null) {
				connection.close();
			} else {
				connection = null;
				connection.close();
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// public void dropAlltables() {
	// String dropTable = "TRUNCATE TABLE GAME_CHEATS";
	// Statement statement = null;
	// try {
	// statement = connection.createStatement();
	// statement.execute(dropTable);
	// } catch (SQLException ex) {
	// System.out.println(">>> Couldn't create table.");
	// ex.printStackTrace();
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }

	private void createPcGameCheatTables() {
		String createTableGameCheats = "CREATE TABLE GAME_CHEATS (cheat_id INTEGER NOT NULL PRIMARY KEY,GAME_ID INTEGER, CHEAT_CODE VARCHAR(100),RESULT VARCHAR(200), REMARKS VARCHAR(200))";

		String createTableGameInfo = "CREATE TABLE game_info (info_id INTEGER NOT NULL PRIMARY KEY, main_title varchar(100) default NULL," +
				"sub_title varchar(100) default NULL," +
				"publisher varchar(100) default NULL," +
				"released_date date default NULL," +
				"version varchar(10) default NULL," +
				"genre varchar(100) default NULL)";

		String createTableGameInstruction = "CREATE TABLE game_instruction (instruct_id INTEGER NOT NULL PRIMARY KEY," +
				" game_id INTEGER default NULL," +
				"instruction varchar(500) default NULL," +
				"last_updated date default NULL)";
		Statement statement = null;
		try {
			statement = connection.createStatement();
			statement.execute(createTableGameCheats);
			System.out.println("... created table game_cheats");
			statement.execute(createTableGameInfo);
			System.out.println("... create table game_info");
			statement.execute(createTableGameInstruction);
			System.out.println("... create table game_instruction");

		} catch (SQLException ex) {
			System.out.println(">>> Couldn't create table.");
			ex.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insertDataToTables() {
		String insertQuery1 = firstTimeInsertData.insertIntoGameCheats;
		String insertQuery2 = firstTimeInsertData.insertIntoGameInfo;
		String insertQuery3 = firstTimeInsertData.insertIntoGameInstruction;
		Statement statement = null;
		try {
			statement = connection.createStatement();
			statement.executeUpdate(insertQuery1);
			statement.executeUpdate(insertQuery2);
			statement.executeUpdate(insertQuery3);
			System.out.println("... data insertion success");
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}

	/**
	 * Get the game cheats, it's effect and remarks based on param.
	 * If param is equals to zero then get all game cheats
	 * else only get game cheats whose id matches
	 * 
	 * @param gameId
	 *            (int)
	 * @return List<GameCheatCode>
	 */
	public List<GameCheatCode> getAndShowAllGameCheatCode(int gameId) {
		List<GameCheatCode> gameCheatCode = new ArrayList<GameCheatCode>();
		try {
			if (connection == null) {
				doConnection();
			}
			String query = null;
			Statement stmt = connection.createStatement();
			if (gameId == 0) {
				query = "SELECT * from game_cheats ORDER BY game_id, cheat_code";
			} else if (gameId > 0) {
				query = "SELECT * from game_cheats WHERE game_id='" + gameId + "' ORDER BY game_id, cheat_code";
			}
			// query = "SELECT * from game_cheats game_info";

			ResultSet rs = stmt.executeQuery(query);
			if (rs == null) {
				System.out.println("--- NO data found. Empty Cheat database.");
				return null;
			}
			while (rs.next()) {
				GameCheatCode cheatCode = new GameCheatCode();
				int id = rs.getInt("cheat_id");
				int game_id = rs.getInt("game_id");
				String cheat_code = rs.getString("cheat_code");
				String result = rs.getString("result");
				String remark = rs.getString("remarks");

				cheatCode.setGameId(id);
				cheatCode.setGameId(game_id);
				cheatCode.setPcCheatCode(cheat_code);
				cheatCode.setCheatCodeResult(result);
				cheatCode.setCheatCodeRemark(remark);
				gameCheatCode.add(cheatCode);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return gameCheatCode;
	}

	/**
	 * Get the game information related on basis of game id.
	 * 
	 * @param gameId
	 *            (int)
	 * @return List<GameInformation>
	 */
	public List<GameInformation> getAndShowAllGameInformation(int gameId) {
		List<GameInformation> gameInformation = new ArrayList<GameInformation>();
		try {
			if (connection == null) {
				doConnection();
			}
			String query = null;
			Statement stmt = (Statement) connection.createStatement();

			if (gameId == 0) {
				query = "SELECT * from game_info ORDER BY main_title";
			} else if (gameId > 0) {
				query = "SELECT * from game_info WHERE info_id='" + gameId + "' ORDER BY info_id";
			}

			ResultSet rs = stmt.executeQuery(query);
			if (rs == null) {
				System.out.println("--- NO data found. Empty Cheat database.");
				return null;
			}
			while (rs.next()) {
				GameInformation information = new GameInformation();
				int id = rs.getInt("info_id");
				String mainTitle = rs.getString("main_title");
				String subTitle = rs.getString("sub_title");
				if (subTitle.isEmpty()) {
					subTitle = "";
				}
				String publisher = rs.getString("publisher");
				String version = rs.getString("version");
				Date releasedDate = rs.getDate("released_date");
				String genre = rs.getString("genre");
				information.setId(id);
				information.setGameTitle(mainTitle);
				information.setGameSubTitle(subTitle);
				information.setGamePublisher(publisher);
				information.setGameVersion(version);
				information.setReleasedDate(releasedDate);
				information.setGameGenre(genre);
				gameInformation.add(information);
			}
		} catch (SQLException sqle) {
		} catch (Exception e) {
		}
		return gameInformation;
	}

	/**
	 * Get related game instruction on basis of game id.
	 * 
	 * @param gameId
	 * @return List<GameInstruction>
	 */
	public List<GameInstruction> getAndShowGameInstruction(int gameId) {
		List<GameInstruction> gameInstruction = new ArrayList<GameInstruction>();
		try {
			if (connection == null) {
				doConnection();
			}
			String query = null;
			Statement stmt = (Statement) connection.createStatement();

			if (gameId == 0) {
				query = "SELECT * from game_instruction ORDER BY game_id";
			} else if (gameId > 0) {
				query = "SELECT * from game_instruction WHERE game_id='" + gameId + "' ORDER BY game_id";
			}

			ResultSet rs = stmt.executeQuery(query);

			if (rs == null) {
				System.out.println("--- NO data found. Empty Cheat database.");
				return null;
			}
			while (rs.next()) {
				GameInstruction instruction = new GameInstruction();
				int id = rs.getInt("INSTRUCT_ID");
				int game_id = rs.getInt("game_id");
				String gameNote = rs.getString("instruction");
				Date lastUpdated = rs.getDate("last_updated");

				instruction.setId(id);
				instruction.setGameId(game_id);
				instruction.setGameInstruction(gameNote);
				instruction.setLastUpdate(lastUpdated);
				gameInstruction.add(instruction);
				return gameInstruction;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}

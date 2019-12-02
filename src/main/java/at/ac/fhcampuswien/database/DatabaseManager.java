package at.ac.fhcampuswien.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.scene.paint.Color;

public class DatabaseManager {

	private static Connection conn;

	public DatabaseManager() {
		try {
			Class.forName(DatabaseConstants.JDBC_DRIVER);
			this.conn = DriverManager.getConnection(DatabaseConstants.DB_URL, DatabaseConstants.USER,
					DatabaseConstants.PASS);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	public Statement createStatement() {
		try {
			return conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void truncateTable(String table) {
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(String.format("TRUNCATE TABLE %s", table));
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insertBodyPart(Color color, double height, double width, double posX, double posY) {
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			String sql = String.format(DatabaseConstants.SQL_INSERT_SBP, color.getRed(), color.getGreen(),
					color.getBlue(), height, width, posX, posY);
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException se2) {
				// nothing to do
			}
		}
	}

	public ResultSet selectBodyParts() {
		ResultSet rs = null;
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(DatabaseConstants.SQL_SELECT_BODYPARTS);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public void close() {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

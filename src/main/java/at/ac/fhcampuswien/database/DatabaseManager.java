package at.ac.fhcampuswien.database;

import java.sql.*;
import java.util.List;

import at.ac.fhcampuswien.parser.ResultSetToSnake;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.apache.log4j.Logger;

public class DatabaseManager {

	private static Connection conn;
	private static Logger logger = Logger.getLogger(DatabaseManager.class.getName());
	private ResultSetToSnake resultSetToSnakeParser = new ResultSetToSnake();

	public DatabaseManager() {
		try {
			this.conn = DriverManager.getConnection(DatabaseConstants.DB_URL, DatabaseConstants.USER,
					DatabaseConstants.PASS);

		} catch ( SQLException e) {
			logger.error("Could not establish a database connection!");
			logger.debug(e.getMessage());
		}
	}

	public void truncateTable(String table){
		try (PreparedStatement truncateSnakeBodyParts = conn.prepareStatement(DatabaseConstants.SQL_TRUNCATE_TABLE)) {
			truncateSnakeBodyParts.executeUpdate();
		} catch (SQLException e) {
			logger.error(String.format("Error while truncating Table %s", table));
			logger.error(e.getMessage());
		}
	}

	public void insertBodyPart(Color color, double height, double width, double posX, double posY){
		try (PreparedStatement insertSnakeBodyParts = conn.prepareStatement(DatabaseConstants.SQL_INSERT_SNAKE_BODY_PARTS)){
			insertSnakeBodyParts.setDouble(1, color.getRed());
			insertSnakeBodyParts.setDouble(2, color.getGreen());
			insertSnakeBodyParts.setDouble(3, color.getBlue());
			insertSnakeBodyParts.setDouble(4, height);
			insertSnakeBodyParts.setDouble(5, width);
			insertSnakeBodyParts.setDouble(6, posX);
			insertSnakeBodyParts.setDouble(7, posY);
			insertSnakeBodyParts.executeUpdate();
		} catch (SQLException se) {
			logger.error("Error while inserting into Table SNAKE_BODY_PART");
			logger.error(String.format("Sql Statement: %s ",DatabaseConstants.SQL_INSERT_SNAKE_BODY_PARTS));
			logger.error(se.getMessage());
		}
	}

	public List<Rectangle> selectBodyParts(){
		ResultSet snakeBodyPartsResultSet;
		List<Rectangle> snakeBodyPartList = null;
		try(PreparedStatement getSnakeBodyParts = conn.prepareStatement(DatabaseConstants.SQL_SELECT_SNAKE_BODY_PARTS)){
			snakeBodyPartsResultSet = getSnakeBodyParts.executeQuery();
			snakeBodyPartList = resultSetToSnakeParser.getSnakeBodyPartsFromDatabase(snakeBodyPartsResultSet);
		} catch (SQLException e) {
			logger.error("Error while fetching Bodyparts from database.");
			logger.error(String.format("SQL statement: %s",DatabaseConstants.SQL_SELECT_SNAKE_BODY_PARTS));
			logger.error(e.getMessage());
		}
		return snakeBodyPartList;
	}
	
	public void close() {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			logger.error(DatabaseConstants.SQL_ERROR_CLOSE);
			logger.error(e.getMessage());
		}
	}
}

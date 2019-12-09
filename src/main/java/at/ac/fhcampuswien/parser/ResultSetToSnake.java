package at.ac.fhcampuswien.parser;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ResultSetToSnake {

    public List<Rectangle> getSnakeBodyPartsFromDatabase(ResultSet databaseSnakeBodyParts) throws SQLException {
        LinkedList<Rectangle> snakeBodyPartsList = new LinkedList<>();
        while (databaseSnakeBodyParts.next()) {
            snakeBodyPartsList.add(new Rectangle(databaseSnakeBodyParts.getDouble("height"), databaseSnakeBodyParts.getDouble("width")));
            Color color = new Color(databaseSnakeBodyParts.getDouble("red"), databaseSnakeBodyParts.getDouble("green"), databaseSnakeBodyParts.getDouble("blue"), 1);
            snakeBodyPartsList.getLast().setFill(color);
            snakeBodyPartsList.getLast().relocate(databaseSnakeBodyParts.getDouble("pos_x"), databaseSnakeBodyParts.getDouble("pos_y"));
        }
        return snakeBodyPartsList;
    }
}

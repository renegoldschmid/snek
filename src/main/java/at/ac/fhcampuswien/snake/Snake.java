package at.ac.fhcampuswien.snake;

import java.awt.Point;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import org.apache.log4j.Logger;

import at.ac.fhcampuswien.database.DatabaseConstants;
import at.ac.fhcampuswien.database.DatabaseManager;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

class Snake {

    private static Logger logger = Logger.getLogger(Snake.class.getName());
    long frameDelay = GameConstants.FRAMEDELAY; //25-30 mill. guter Startwert
    LinkedList<Rectangle> snakeBodyPartsList = new LinkedList<>();
    private Rectangle head = new Rectangle(GameConstants.SNAKE_WIDTH, GameConstants.SNAKE_HEIGHT); // hier Initialisiert, weil in mehreren Methoden
    private DatabaseManager db = new DatabaseManager();

    Snake(Group group, Stage stage) {
        resetSnake(stage);
        group.getChildren().add(snakeBodyPartsList.getFirst());
    }

    private void resetSnake(Stage stage) {
        snakeBodyPartsList.clear();
        snakeBodyPartsList.add(head);
        snakeBodyPartsList.getFirst().relocate(stage.getWidth() / 2, stage.getHeight() / 2);
    }

    long getframeDelay() {
        return frameDelay;
    }

    void respawn(Group group, GameObject food, Score score, Stage stage, Control control) {
        group.getChildren().clear();
        resetSnake(stage);
        group.getChildren().add(snakeBodyPartsList.getFirst());
        food.setFood(group, stage); // setet neues random food und getchilded es
        score.scoreRespawn(group); // respawn Mehtode für Score
        frameDelay = GameConstants.FRAMEDELAY; // zurück zum Standardwert

        control.stopMovement();
    }

    private void snakeDead(Group group, Control control, Stage stage) {
        //Last Minute - wird gebraucht um Score nicht zu früh zu löschen (überlegung nur respawn zu verwenden mit dieser implementierung fehlgeschlagen)
        group.getChildren().clear();
        resetSnake(stage);
        frameDelay = GameConstants.FRAMEDELAY; // zurück zum Standardwert
        control.stopMovement();
    }


    void eat(Group group, Score score, GameObject food) {
        snakeBodyPartsList.add(new Rectangle(GameConstants.SNAKE_WIDTH, GameConstants.SNAKE_HEIGHT));//added ein tail rectangle, übernimmt color von food,erhöht score um 1, macht schneller
        snakeBodyPartsList.getLast().setFill(Color.color(food.getColor()[0], food.getColor()[1], food.getColor()[2])); //holt sich aus deathsoundMedia GameObject die Color von Food für sein Tail
        group.getChildren().add(snakeBodyPartsList.getLast()); //bringt den tail auf die Szene
        score.upScoreValue(); // added +1 zu scoreValue
        if (frameDelay >= GameConstants.FRAMEDELAY_MAX) { //maximale Grenze sonst wirds zu schnell
            //von speedRefresh abziehen
            long delayDecrease = GameConstants.DELAY_DECREASE;
            frameDelay -= delayDecrease;
            logger.debug(String.format("Framedelay: %s", frameDelay));
        }
    }

    void collision(GameObject food, Group group, Bounds foodBound, Score score, Control control, Stage stage, Gameboard gameboard) { //gameobject sind obstacles so wie Food, Boundarys für Collisions
        Bounds headBox = head.getBoundsInParent(); // erstellt eine Boundary um den Snakekopf

        if (headBox.intersects(foodBound)) {//überprüfung Collision Head mit Food Boundary
            eat(group, score, food);
            food.setFood(group, stage);
            AudioManager.playEatsound();
        }

        if (snakeBodyPartsList.getFirst().getLayoutX() <= 0 || snakeBodyPartsList.getFirst().getLayoutX() >= stage.getWidth() - 30 || // Überprüfung ob Head den Rand trifft
        		snakeBodyPartsList.getFirst().getLayoutY() <= 0 || snakeBodyPartsList.getFirst().getLayoutY() >= stage.getHeight() - 54) {
            snakeDead(group, control, stage);
            gameboard.setDeathTouchWall(score, group, stage);
            AudioManager.playDeathsound();
            AudioManager.stopIngamemusic();
            AudioManager.restartGameovermusic();
        }

        for (int i = 1; i < this.snakeBodyPartsList.size(); i++) { //Überprüfung Snake beisst sich in den oasch
            if (headBox.intersects(this.snakeBodyPartsList.get(i).getBoundsInParent())) {
                logger.info("Snake died!");
                snakeDead(group, control, stage);
                gameboard.setDeathTouchTail(score, group, stage);
                AudioManager.playDeathsound();
                AudioManager.stopIngamemusic();
                AudioManager.restartGameovermusic();
            }
        }
    }

    void moveSnake(Point direction) { //dx bzw dy ist jeweils + oder - speed, war zuvor 5
        if (direction.x != 0 || direction.y != 0) { //gibt es überhaupt dx/dy werte (wenn wir stehen z.B. nicht)
            LinkedList<Rectangle> snakehelp = new LinkedList<>();

            for (int i = 0; i < snakeBodyPartsList.size(); i++) {
                snakehelp.add(new Rectangle());
                snakehelp.get(i).relocate(snakeBodyPartsList.get(i).getLayoutX(), snakeBodyPartsList.get(i).getLayoutY());
            }

            int x = (int) snakeBodyPartsList.getFirst().getLayoutX() + direction.x;
            int y = (int) snakeBodyPartsList.getFirst().getLayoutY() + direction.y;
            snakeBodyPartsList.getFirst().relocate(x, y);    // bewegt erstmal nur den Kopf

            for (int i = 1; i < snakeBodyPartsList.size(); i++) {

                int helpX = (int) snakehelp.get(i - 1).getLayoutX();
                int helpY = (int) snakehelp.get(i - 1).getLayoutY();
                snakeBodyPartsList.get(i).relocate(helpX, helpY);
            }
        }
    }

	public void saveState(Group group, GameObject food, Score score) {
		db.truncateTable(DatabaseConstants.SQL_TABLE_SBP);
		for (Rectangle bodyPart : snakeBodyPartsList) {
			Color color = (Color) bodyPart.getFill();
			db.insertBodyPart(color, bodyPart.getHeight(), bodyPart.getWidth(), bodyPart.getLayoutX(),
					bodyPart.getLayoutY());
		}
	}
	
	public void loadState(Score score, Group group) {
		ResultSet rs = db.selectBodyParts();
		try {
			while (rs.next()) {
				snakeBodyPartsList.add(new Rectangle(rs.getDouble("height"), rs.getDouble("width")));
				Color color = new Color(rs.getDouble("red"), rs.getDouble("green"), rs.getDouble("blue"), 1);
				snakeBodyPartsList.getLast().setFill(color);
				snakeBodyPartsList.getLast().relocate(rs.getDouble("pos_x"), rs.getDouble("pos_y"));
				group.getChildren().add(snakeBodyPartsList.getLast());
				if (rs.getInt("id") != 1) { // Don't increase score for the head of the snake
					score.upScoreValue();
				}
				if (frameDelay >= GameConstants.FRAMEDELAY_MAX) {
					frameDelay -= GameConstants.DELAY_DECREASE;
				}
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void reloadSnake(Group group, GameObject food, Score score, Stage stage, Control control) {
		group.getChildren().clear();
		snakeBodyPartsList.clear();
        food.setFood(group, stage);
        score.scoreRespawn(group);
        frameDelay = GameConstants.FRAMEDELAY;
        
        control.stopMovement();
	}
}

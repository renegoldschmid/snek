package at.ac.fhcampuswien.snake;

import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.util.LinkedList;

@ExtendWith(ApplicationExtension.class)
public class SnakeTest {

    private LinkedList<Rectangle> snakeBody; // List of rectangles that form the snake body in the game.
    private Group group;
    private Stage stage;
    private GameObject food;
    private Score score;
    private Control control;
    private Pane backgroundPane = new Pane();
    private Snake snake;
    private Gameboard gameboard;

    @Start
    public void start(Stage stage) {
        group = new Group();
        stage.setWidth(1500);
        stage.setHeight(700);
        this.stage = stage;
        food = new GameObject();
        score = new Score(group);
        control = new Control();
        Image imgSource = new Image("file:src/main/resources/at/ac/fhcampuswien/media/grassTile.png");
        BackgroundImage backgroundImage = new BackgroundImage(imgSource, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background backgroundView = new Background(backgroundImage);
        backgroundPane.setBackground(backgroundView);
        Scene scene = new Scene(backgroundPane, stage.getWidth(), stage.getHeight(), Color.DARKGREEN);
        snake = new Snake(group, stage);
        scene.setOnKeyPressed(keyEvent -> {
            control.keyHandler(keyEvent, snake, group, food, score, stage);//control nimmt Keyevent und schaut speziell nach WASD
        });
        gameboard = new Gameboard();
        snakeBody = new LinkedList<>();
    }

    @Test
    void snake_respawn() {
        snake.respawn(group, food, score, stage, control);
    }

    @Test
    void snake_collision() {
        snake.collision(food, group, food.getBound(), score, control, stage, gameboard);
    }

    @Test
    void snake_eat() {
        snake_ate();
        Assert.assertEquals(1, snakeBody.size());
        Assert.assertEquals(Color.color(food.getColor()[0], food.getColor()[1], food.getColor()[2]), snakeBody.getLast().getFill());
    }

    void snake_ate() {
        snakeBody.add(new Rectangle(20, 20));
        snakeBody.getLast().setFill(Color.color(food.getColor()[0], food.getColor()[1], food.getColor()[2])); //holt sich aus deathsoundMedia GameObject die Color von Food für sein Tail
        group.getChildren().add(snakeBody.getLast()); //bringt den tail auf die Szene
        score.upScoreValue(); // added +1 zu scoreValue
    }

    @Test
    void testGetframeDelay() {
    }

    @Test
    void testRespawn() {
    }

    @Test
    void testCollision() {
    }

    @Test
    void testMoveSnake() {
    }
}

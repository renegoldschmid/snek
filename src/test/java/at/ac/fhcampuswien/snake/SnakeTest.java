package at.ac.fhcampuswien.snake;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;


@ExtendWith(ApplicationExtension.class)
public class SnakeTest {

    private Group group;
    private Stage stage;
    private GameObject food;
    private Score score;
    private Control control;
    private Pane backgroundPane = new Pane();
    private Snake snake;
    private Gameboard gameboard;
    private ApplicationInteractions applicationInteractions;

    @Start
    public void start(Stage stage) {
        PropertyConfigurator.configure("./etc/log4jTests.properties");
        group = new Group();
        stage.setWidth(GameConstants.STAGE_WIDTH);
        stage.setHeight(GameConstants.STAGE_HEIGHT);
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
        scene.setOnKeyPressed(keyEvent -> control.keyHandler(keyEvent, snake, group, food, score, stage));//control nimmt Keyevent und schaut speziell nach WASD);
        gameboard = new Gameboard();
        applicationInteractions = new ApplicationInteractions(group,stage);

    }

    @Test
    void testSnake_Initial() {
        Assert.assertEquals(1, snake.snakeBodyPartsList.size());
        Assert.assertEquals(0,score.getScoreValue());
        Assert.assertEquals(Color.color(food.getColor()[0], food.getColor()[1], food.getColor()[2]), snake.snakeBodyPartsList.getLast().getFill());
    }

    @Test
    void testSnakeEat_ateOne(){
        food.setFood(group, stage);
        snake.eat(group,score,food);
        Assert.assertEquals(2, snake.snakeBodyPartsList.size());
        Assert.assertEquals(1,score.getScoreValue());
        Assert.assertEquals(Color.color(food.getColor()[0],food.getColor()[1],food.getColor()[2]),snake.snakeBodyPartsList.getLast().getFill());
    }

    @Test
    void testSnakeEat_ateTwo(){
        food.setFood(group, stage);
        snake.eat(group,score,food);
        food.setFood(group, stage);
        snake.eat(group,score,food);
        Assert.assertEquals(3, snake.snakeBodyPartsList.size());
        Assert.assertEquals(2,score.getScoreValue());
        Assert.assertEquals(Color.color(food.getColor()[0],food.getColor()[1],food.getColor()[2]),snake.snakeBodyPartsList.getLast().getFill());
    }

    @Test
    void testSnakeEat_ateThree(){
        food.setFood(group, stage);
        snake.eat(group,score,food);
        food.setFood(group, stage);
        snake.eat(group,score,food);
        food.setFood(group, stage);
        snake.eat(group,score,food);
        Assert.assertEquals(4, snake.snakeBodyPartsList.size());
        Assert.assertEquals(3,score.getScoreValue());
        Assert.assertEquals(Color.color(food.getColor()[0],food.getColor()[1],food.getColor()[2]),snake.snakeBodyPartsList.getLast().getFill());
    }

    @Test
    void testSnakeEat_ateSevenTeen(){
        for (int eatCount = 0; eatCount < 17; eatCount++) {
            food.setFood(group, stage);
            snake.eat(group,score,food);
        }
        Assert.assertEquals(18, snake.snakeBodyPartsList.size());
        Assert.assertEquals(17,score.getScoreValue());
        Assert.assertEquals(Color.color(food.getColor()[0],food.getColor()[1],food.getColor()[2]),snake.snakeBodyPartsList.getLast().getFill());
    }

    @Test
    void testWall_Death(){
        for (int snakeMovementSteps = 0; snakeMovementSteps <= GameConstants.STAGE_HEIGHT / 2 / GameConstants.SPEED; snakeMovementSteps++) {
            testMoveSnakeUp_Movement();
        }
        testSnakeEat_ateOne();
        snake.collision(food, group, food.getBound(), score, control, stage, gameboard);
        Assert.assertEquals(GameConstants.STAGE_HEIGHT / 2,snake.snakeBodyPartsList.getFirst().getLayoutY(),0);
        Assert.assertEquals(GameConstants.STAGE_WIDTH / 2,snake.snakeBodyPartsList.getFirst().getLayoutX(),0);
        Assert.assertEquals(GameConstants.FRAMEDELAY,snake.frameDelay); // zurück zum Standardwert
        Assert.assertFalse(control.goDown);
        Assert.assertFalse(control.goUp);
        Assert.assertFalse(control.goRight);
        Assert.assertFalse(control.goLeft);
        Assert.assertEquals(1,snake.snakeBodyPartsList.size());
    }


    @Test
    void testTail_Death(){
        testSnakeEat_ateSevenTeen();
        testMoveSnakeLeft_Movement();
        testMoveSnakeLeft_Movement();
        testMoveSnakeDown_Movement();
        testMoveSnakeUp_Movement();
        snake.collision(food, group, food.getBound(), score, control, stage, gameboard);
        Assert.assertEquals(GameConstants.STAGE_HEIGHT / 2,snake.snakeBodyPartsList.getFirst().getLayoutY(),0);
        Assert.assertEquals(GameConstants.STAGE_WIDTH / 2,snake.snakeBodyPartsList.getFirst().getLayoutX(),0);
        Assert.assertEquals(GameConstants.FRAMEDELAY,snake.frameDelay); // zurück zum Standardwert
        Assert.assertFalse(control.goDown);
        Assert.assertFalse(control.goUp);
        Assert.assertFalse(control.goRight);
        Assert.assertFalse(control.goLeft);
        Assert.assertEquals(1,snake.snakeBodyPartsList.size());
    }

    @Test
    void testMoveSnakeUp_Movement(){
        control.goUp = true;
        double xBeforeMove = snake.snakeBodyPartsList.getFirst().getLayoutX();
        double yBeforeMove = snake.snakeBodyPartsList.getFirst().getLayoutY();
        applicationInteractions.intervalMoveSnake(snake, food, score, control, gameboard,GameConstants.SPEED);
        Assert.assertEquals(xBeforeMove,snake.snakeBodyPartsList.getFirst().getLayoutX(),0);
        Assert.assertEquals(yBeforeMove-GameConstants.SPEED,snake.snakeBodyPartsList.getFirst().getLayoutY(),0);
    }

    @Test
    void testMoveSnakeDown_Movement(){
        control.goDown = true;
        double xBeforeMove = snake.snakeBodyPartsList.getFirst().getLayoutX();
        double yBeforeMove = snake.snakeBodyPartsList.getFirst().getLayoutY();
        applicationInteractions.intervalMoveSnake(snake, food, score, control, gameboard,GameConstants.SPEED);
        Assert.assertEquals(xBeforeMove,snake.snakeBodyPartsList.getFirst().getLayoutX(),0);
        Assert.assertEquals(yBeforeMove+GameConstants.SPEED,snake.snakeBodyPartsList.getFirst().getLayoutY(),0);
    }

    @Test
    void testMoveSnakeRight_Movement(){
        control.goRight = true;
        double xBeforeMove = snake.snakeBodyPartsList.getFirst().getLayoutX();
        double yBeforeMove = snake.snakeBodyPartsList.getFirst().getLayoutY();
        applicationInteractions.intervalMoveSnake(snake, food, score, control, gameboard,GameConstants.SPEED);
        Assert.assertEquals(xBeforeMove+GameConstants.SPEED,snake.snakeBodyPartsList.getFirst().getLayoutX(),0);
        Assert.assertEquals(yBeforeMove,snake.snakeBodyPartsList.getFirst().getLayoutY(),0);
    }

    @Test
    void testMoveSnakeLeft_Movement(){
        control.goLeft = true;
        double xBeforeMove = snake.snakeBodyPartsList.getFirst().getLayoutX();
        double yBeforeMove = snake.snakeBodyPartsList.getFirst().getLayoutY();
        applicationInteractions.intervalMoveSnake(snake, food, score, control, gameboard,GameConstants.SPEED);
        Assert.assertEquals(xBeforeMove-GameConstants.SPEED,snake.snakeBodyPartsList.getFirst().getLayoutX(),0);
        Assert.assertEquals(yBeforeMove,snake.snakeBodyPartsList.getFirst().getLayoutY(),0);
    }


}

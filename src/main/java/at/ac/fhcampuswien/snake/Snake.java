package at.ac.fhcampuswien.snake;

import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.awt.Point;
import java.util.LinkedList;

public class Snake {

    public long frameDelay = GameConstants.FRAMEDELAY; //25-30 mill. guter Startwert
    public long delayDecrease = GameConstants.DELAY_DECREASE;  //von speedRefresh abziehen
    private Rectangle head = new Rectangle(GameConstants.SNAKE_WIDTH, GameConstants.SNAKE_HEIGHT); // hier Initialisiert, weil in mehreren Methoden
    private LinkedList<Rectangle> snake = new LinkedList<>();

    public Snake(Group group, Stage stage) {
        resetSnake(stage);
        group.getChildren().add(snake.getFirst());
    }
    
    private void resetSnake(Stage stage) {
    	snake.clear();
    	snake.add(head);
    	snake.getFirst().relocate(stage.getWidth() / 2, stage.getHeight() / 2);
    }

    public long getframeDelay() {
        return frameDelay;
    }

    public void respawn(Group group, GameObject food, Score score, Stage stage, Control control) {
        group.getChildren().clear();
        resetSnake(stage);
        group.getChildren().add(snake.getFirst());
        food.setFood(group, stage); // setet neues random food und getchilded es
        score.scoreRespawn(group); // respawn Mehtode für Score
        frameDelay = GameConstants.FRAMEDELAY; // zurück zum Standardwert

        control.stopMovement();
    }

    public void snakeDead(Group group, GameObject food, Score score, Control control, Stage stage) {
        //Last Minute - wird gebraucht um Score nicht zu früh zu löschen (überlegung nur respawn zu verwenden mit dieser implementierung fehlgeschlagen)
        group.getChildren().clear();
        resetSnake(stage);
        frameDelay = GameConstants.FRAMEDELAY; // zurück zum Standardwert
        control.stopMovement();
    }


    public void eat(Group group, Score score, GameObject food) {//added ein tail rectangle, übernimmt color von food,erhöht score um 1, macht schneller
        snake.add(new Rectangle(GameConstants.SNAKE_WIDTH, GameConstants.SNAKE_HEIGHT));
        snake.getLast().setFill(Color.color(food.getColor()[0], food.getColor()[1], food.getColor()[2])); //holt sich aus deathsoundMedia GameObject die Color von Food für sein Tail
        group.getChildren().add(snake.getLast()); //bringt den tail auf die Szene
        score.upScoreValue(); // added +1 zu scoreValue
        if (frameDelay >= GameConstants.FRAMEDELAY_MAX) { //maximale Grenze sonst wirds zu schnell
            frameDelay -= delayDecrease;
            System.out.println(frameDelay);
        }
    }

    public void collision(GameObject food, Group group, Bounds foodBound, Score score, Control control, Stage stage, Gameboard gameboard) { //gameobject sind obstacles so wie Food, Boundarys für Collisions
        Bounds headBox = head.getBoundsInParent(); // erstellt eine Boundary um den Snakekopf


        if (headBox.intersects(foodBound)) {//überprüfung Collision Head mit Food Boundary
            eat(group, score, food);
            food.setFood(group, stage);
            AudioManager.playEatsound();
        }

        if (head.getLayoutX() <= 0 || head.getLayoutX() >= stage.getWidth() - 30 || // Überprüfung ob Head den Rand trifft
                head.getLayoutY() <= 0 || head.getLayoutY() >= stage.getHeight() - 54) {
            snakeDead(group, food, score, control, stage);
            gameboard.setDeathTouchWall(score, group, stage);
            AudioManager.playDeathsound();
            AudioManager.stopIngamemusic();
            AudioManager.restartGameovermusic();
        }


        for (int i = 1; i < this.snake.size(); i++) { //Überprüfung Snake beisst sich in den oasch
            if (headBox.intersects(this.snake.get(i).getBoundsInParent())) {
                System.out.println("DEAD");
                snakeDead(group, food, score, control, stage);
                gameboard.setDeathTouchTail(score, group, stage);
                AudioManager.playDeathsound();
                AudioManager.stopIngamemusic();
                AudioManager.restartGameovermusic();
            }
        }
    }

    public void moveSnake(Point direction, Stage stage) { //dx bzw dy ist jeweils + oder - speed, war zuvor 5
    	
        if (direction.x != 0 || direction.y != 0) { //gibt es überhaupt dx/dy werte (wenn wir stehen z.B. nicht)
            LinkedList<Rectangle> snakehelp = new LinkedList<>();

            for (int i = 0; i < snake.size(); i++) {
                snakehelp.add(new Rectangle());
                snakehelp.get(i).relocate(snake.get(i).getLayoutX(), snake.get(i).getLayoutY());
            }

            int x = (int) snake.getFirst().getLayoutX() + direction.x;
            int y = (int) snake.getFirst().getLayoutY() + direction.y;
            snake.getFirst().relocate(x, y);	// bewegt erstmal nur den Kopf

            for (int i = 1; i < snake.size(); i++) {

                int helpX = (int) snakehelp.get(i - 1).getLayoutX();
                int helpY = (int) snakehelp.get(i - 1).getLayoutY();
                snake.get(i).relocate(helpX, helpY);
            }
        }
    }
}

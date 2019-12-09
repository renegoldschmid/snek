package at.ac.fhcampuswien.snake;

import javafx.scene.Group;
import javafx.stage.Stage;

import java.awt.*;

class ApplicationInteractions {

    private Group root;
    private Stage primaryStage;
    
     ApplicationInteractions(Group root, Stage primaryStage) {
        this.root = root;
        this.primaryStage = primaryStage;
    }

    void intervalMoveSnake(Snake snake, GameObject food, Score score, Control control, Gameboard gameboard, int offset) {

        Point direction = new Point(0,0);
        snake.collision(food, root, food.getBound(), score, control, primaryStage, gameboard);

        if (control.getgoUp()) {
            direction.y += -offset; //offset="speed"
        }
        else if (control.getgoDown()) {
            direction.y += offset;
        }
        else if (control.getgoRight()) {
            direction.x += offset;
        }
        else if (control.getgoLeft()) {
            direction.x += -offset;
        }

        snake.moveSnake(direction);
    }
}

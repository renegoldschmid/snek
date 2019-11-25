package at.ac.fhcampuswien.snake;

import javafx.scene.Group;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class Control {

    private boolean goUp, goDown, goRight, goLeft;

    public void stopMovement() {
        goUp = false;
        goDown = false;
        goRight = false;
        goLeft = false;
    }


    public boolean getgoUp() {
        return goUp;
    }

    public boolean getgoDown() {
        return goDown;
    }

    public boolean getgoLeft() {
        return goLeft;
    }

    public boolean getgoRight() {
        return goRight;
    }


    public void keyHandler(KeyEvent keyEvent, Snake snake, Group group, GameObject food, Score score, Stage stage) {

        switch (keyEvent.getCode()) {
            case W:
                if (goDown)          //dass nicht nach oben gelenkt werden kann während sich nach unten bewegt
                    break;
                goUp = true;
                goDown = false;     //"false" Fälle: dass man sich nicht schräg bewegen kann
                goRight = false;
                goLeft = false;
                break;

            case S:
                if (goUp)
                    break;
                goDown = true;
                goUp = false;
                goRight = false;
                goLeft = false;
                break;

            case D:
                if (goLeft)
                    break;
                goRight = true;
                goUp = false;
                goDown = false;
                goLeft = false;
                break;
            case A:
                if (goRight)
                    break;
                goLeft = true;
                goUp = false;
                goDown = false;
                goRight = false;
                break;
            case R:
                snake.respawn(group, food, score, stage, this);
                GameLoop.stopGameovermusic();
                GameLoop.restartIngamemusic();
                break;


        }
    }
}


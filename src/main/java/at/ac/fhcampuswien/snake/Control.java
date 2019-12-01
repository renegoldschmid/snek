package at.ac.fhcampuswien.snake;

import javafx.scene.Group;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

class Control {

    private boolean goUp;
    private boolean goDown;
    private boolean goRight;
    private boolean goLeft;
    private static Logger logger = Logger.getLogger(Control.class.getName());

    void stopMovement() {
        goUp = false;
        goDown = false;
        goRight = false;
        goLeft = false;
    }


    boolean getgoUp() {
        return goUp;
    }

    boolean getgoDown() {
        return goDown;
    }

    boolean getgoLeft() {
        return goLeft;
    }

    boolean getgoRight() {
        return goRight;
    }


    void keyHandler(KeyEvent keyEvent, Snake snake, Group group, GameObject food, Score score, Stage stage) {

        switch (keyEvent.getCode()) {
            case W:
                if (goDown)          //dass nicht nach oben gelenkt werden kann während sich nach unten bewegt
                    break;
                goUp = true;
                goDown = false;     //"false" Fälle: dass man sich nicht schräg bewegen kann
                goRight = false;
                goLeft = false;
                logger.debug("Snake is moving down!");
                break;

            case S:
                if (goUp)
                    break;
                goDown = true;
                goUp = false;
                goRight = false;
                goLeft = false;
                logger.debug("Snake is moving up!");
                break;

            case D:
                if (goLeft)
                    break;
                goRight = true;
                goUp = false;
                goDown = false;
                goLeft = false;
                logger.debug("Snake is moving left!");
                break;
            case A:
                if (goRight)
                    break;
                goLeft = true;
                goUp = false;
                goDown = false;
                goRight = false;
                logger.debug("Snake is moving right!");
                break;
            case R:
                snake.respawn(group, food, score, stage, this);
                AudioManager.stopGameovermusic();
                AudioManager.restartIngamemusic();
                logger.debug("Game was reset!");
                break;
            default:
                break;
            default:
                break;


        }
    }
}


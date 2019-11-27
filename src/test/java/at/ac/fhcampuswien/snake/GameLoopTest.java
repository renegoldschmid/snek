package at.ac.fhcampuswien.snake;

import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.util.concurrent.CountDownLatch;

@ExtendWith(ApplicationExtension.class)
public class GameLoopTest {

    private GameLoop mainGame;

    @Start
    public void start(Stage stage) throws InterruptedException {
        Platform.setImplicitExit(true);
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            mainGame = new GameLoop();
            mainGame.start(new Stage());
            latch.countDown();
        });

    }

    @Test
    void should_contain_button_with_text(FxRobot robot) {
        robot.press(KeyCode.getKeyCode("W"));
    }
}

package at.ac.fhcampuswien.snake;

import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.log4j.PropertyConfigurator;

import java.io.File;

public class GameLoop extends Application {


	private static File splashFile = new File("src/main/resources/at/ac/fhcampuswien/media/splash.mp4");
    private static Media splashMedia = new Media(splashFile.toURI().toString());
    private static MediaPlayer splashPlayer = new MediaPlayer(splashMedia);
    private static MediaView splashView = new MediaView(splashPlayer);
    
    private Group root = new Group();
    private Pane backgroundPane = new Pane();
    private Group splashscreen = new Group();
    private ApplicationInteractions applicationInteractions;
    private long lastUpdate = 0;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        PropertyConfigurator.configure("./src/main/resources/at/ac/fhcampuswien/configurations/main/log4j.properties");
        AnimationTimer timer;

        primaryStage.setWidth(GameConstants.STAGE_WIDTH);
        primaryStage.setHeight(GameConstants.STAGE_HEIGHT);
        
        primaryStage.setMinWidth(GameConstants.STAGE_MIN_WIDTH);
        primaryStage.setMinHeight(GameConstants.STAGE_MIN_HEIGHT);

        applicationInteractions= new ApplicationInteractions(root,primaryStage);

        Image imgSource = new Image("file:src/main/resources/at/ac/fhcampuswien/media/grassTile.png");
        BackgroundImage backgroundImage = new BackgroundImage(imgSource, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background backgroundView = new Background(backgroundImage);
        backgroundPane.setBackground(backgroundView);


        int offset = GameConstants.SPEED; //TODO Variable Namen anpassen
        Gameboard gameboard = new Gameboard();
        Control control = new Control();
        Snake snake = new Snake(root, primaryStage); //erstellt neues Snake Listen Objekt und getChilded es
        GameObject food = new GameObject();
        Score score = new Score(root);
        food.setFood(root, primaryStage);//setzt ein neues Food random ab
        Scene scene = new Scene(backgroundPane, primaryStage.getWidth(), primaryStage.getHeight(), Color.DARKGREEN);
        backgroundPane.getChildren().add(root); //TODO NEU Background - root (Group) zu backgroundPane als Child added

        FadeTransition fadeBlackToTransparent = sceneTransition(primaryStage);
        createIntro(primaryStage);

        //Keyeventhandler fragt ab obs ein Keyevent gibt
        scene.setOnKeyPressed(keyEvent -> control.keyHandler(keyEvent, snake, root, food, score, primaryStage));

        timer = createTimer(offset, gameboard, control, snake, food, score);
        splashPlayer.setOnEndOfMedia(() -> {
            primaryStage.setScene(scene);
            fadeBlackToTransparent.play();
            timer.start();
            AudioManager.restartIngamemusic();
        });
    }
    
	private FadeTransition sceneTransition(Stage primaryStage) {
		Rectangle blackBackground = new Rectangle();
		blackBackground.setFill(Color.BLACK);
		blackBackground.setHeight(primaryStage.getHeight());
		blackBackground.setWidth(primaryStage.getWidth());
        FadeTransition fadeBlackToTransparent = new FadeTransition(Duration.millis(700), blackBackground);
        fadeBlackToTransparent.setFromValue(1.0);
        fadeBlackToTransparent.setToValue(0.0);
        root.getChildren().add(blackBackground);
		return fadeBlackToTransparent;
	}
	
	private void createIntro(Stage primaryStage) {
		Scene intro = new Scene(splashscreen, primaryStage.getWidth(), primaryStage.getHeight());
        splashscreen.getChildren().add(splashView);
        splashView.setFitHeight(500);
        splashView.setFitWidth(1000);
        intro.setFill(Color.BLACK);
        splashView.setX(400);
        splashView.setY(100);
        primaryStage.setScene(intro);
        primaryStage.setTitle("Rainbow Snake");
        primaryStage.show();
        splashPlayer.play();
	}
    
    
	private AnimationTimer createTimer(int offset, Gameboard gameboard, Control control, Snake snake,
			GameObject food, Score score) {
		
		AnimationTimer timer;
		timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (now - lastUpdate >= snake.getframeDelay()) {
                    applicationInteractions.intervalMoveSnake(snake, food, score, control, gameboard, offset);
                    lastUpdate = now;
                }
            }
        };
		return timer;
	}



}

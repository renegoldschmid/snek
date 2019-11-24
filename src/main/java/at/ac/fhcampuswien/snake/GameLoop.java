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

import java.io.File;

public class GameLoop extends Application {

    private static final File splashFile = new File("src/main/resources/at/ac/fhcampuswien/media/splash.mp4");
    private static final Media splashMedia = new Media(splashFile.toURI().toString());
    private static final MediaPlayer splashPlayer = new MediaPlayer(splashMedia);
    private static final MediaView splashView = new MediaView(splashPlayer);
    private static final File ingamemusicFile = new File("src/main/resources/at/ac/fhcampuswien/media/sound/music/ingame2.mp3");
    private static final Media ingamemusicMedia = new Media(ingamemusicFile.toURI().toString());
    private static final MediaPlayer ingamemusicPlayer = new MediaPlayer(ingamemusicMedia);
    private static final File gameovermusicFile = new File("src/main/resources/at/ac/fhcampuswien/media/sound/music/gameover1.mp3");
    private static final Media gameovermusicMedia = new Media(gameovermusicFile.toURI().toString());
    private static final MediaPlayer gameovermusicPlayer = new MediaPlayer(gameovermusicMedia);
    private static final File eatsoundFile = new File("src/main/resources/at/ac/fhcampuswien/media/sound/eat2.mp3");
    private static final Media eatsoundMedia = new Media(eatsoundFile.toURI().toString());
    private static final MediaPlayer eatsoundPlayer = new MediaPlayer(eatsoundMedia);
    private static final File deathsoundFile = new File("src/main/resources/at/ac/fhcampuswien/media/sound/death1.mp3");
    private static final Media deathsoundMedia = new Media(deathsoundFile.toURI().toString());
    private static final MediaPlayer deathsoundPlayer = new MediaPlayer(deathsoundMedia);
    private final Group root = new Group();
    private final Pane backgroundPane = new Pane(); //TODO NEU für Background
    private final Group splashscreen = new Group();
    //TODO NEU - Background stuff
    private Image imgSource;
    private BackgroundImage backgroundImage;
    private Background backgroundView;
    private long lastUpdate = 0; //für Geschwindigkeitssteuerung

    static void restartIngamemusic() { //Startet Ingame Musik von vorne
        ingamemusicPlayer.seek(Duration.ZERO);
        ingamemusicPlayer.play();
    }

    static void stopIngamemusic() {
        ingamemusicPlayer.stop();
    }

    static void restartGameovermusic() {
        gameovermusicPlayer.seek(Duration.ZERO);
        gameovermusicPlayer.play();
    }

    static void stopGameovermusic() {
        gameovermusicPlayer.stop();
    }

    static void playEatsound() {
        eatsoundPlayer.seek(Duration.ZERO);
        eatsoundPlayer.play();
    }

    static void playDeathsound() {
        deathsoundPlayer.seek(Duration.ZERO);
        deathsoundPlayer.play();
    }
    //TODO END Background

    public static void main(String[] args) {
        launch(args);

    }


    @Override
    public void start(Stage primaryStage) {
        AnimationTimer timer;

        primaryStage.setWidth(1500);
        primaryStage.setHeight(700);

        primaryStage.setMinHeight(50);
        primaryStage.setMinWidth(50);

        //TODO NEU - Background stuff
        imgSource = new Image("file:src/main/resources/at/ac/fhcampuswien/media/grassTile.png");
        backgroundImage = new BackgroundImage(imgSource, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        backgroundView = new Background(backgroundImage);
        backgroundPane.setBackground(backgroundView);
        //TODO END Background


        int offset = 21; //TODO Variable Namen anpassen
        Gameboard gameboard = new Gameboard(); // TODO NEW
        Control control = new Control();
        Snake snake = new Snake(root, primaryStage); //erstellt neues Snake Listen Objekt und getChilded es
        GameObject food = new GameObject();
        Score score = new Score(root);
        food.setFood(root, primaryStage);//setzt ein neues Food random ab
        Scene scene = new Scene(backgroundPane, primaryStage.getWidth(), primaryStage.getHeight(), Color.DARKGREEN);
        backgroundPane.getChildren().add(root); //TODO NEU Background - root (Group) zu backgroundPane als Child added

        Rectangle blackrect = new Rectangle();  //Schwarzer Block der für eine Szenentransition missbraucht wird
        blackrect.setFill(Color.BLACK);
        blackrect.setHeight(primaryStage.getHeight());
        blackrect.setWidth(primaryStage.getWidth());
        FadeTransition fadeblacktotransparent = new FadeTransition(Duration.millis(700), blackrect);
        fadeblacktotransparent.setFromValue(1.0);
        fadeblacktotransparent.setToValue(0.0);
        root.getChildren().add(blackrect);

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

        ingamemusicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        /*inp.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                inp.seek(Duration.ZERO);
            }
        });
        */
        //Keyeventhandler fragt ab obs ein Keyevent gibt
        scene.setOnKeyPressed(keyEvent -> {
            control.keyHandler(keyEvent, snake, root, food, score, primaryStage);//control nimmt Keyevent und schaut speziell nach WASD

        });


        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {

                if (now - lastUpdate >= snake.getframeDelay()) {

                    int dx = 0, dy = 0;

                    snake.collision(food, root, food.getBound(), score, control, primaryStage, gameboard);

                    if (control.getgoUp()) dy += -offset; //offset="speed"
                    else if (control.getgoDown()) dy += offset;
                    else if (control.getgoRight()) dx += offset;
                    else if (control.getgoLeft()) dx += -offset;
                    snake.moveSnake(dx, dy);

                    lastUpdate = now;

                }
            }
        };
        splashPlayer.setOnEndOfMedia(() -> {
            primaryStage.setScene(scene);
            fadeblacktotransparent.play();
            timer.start(); //Animationtimer startet nun erst nach dem Fade out des Hundevideos
            restartIngamemusic();
        });

    }


}

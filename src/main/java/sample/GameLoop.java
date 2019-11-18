package sample;

import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.util.Duration;

import java.io.File;

public class GameLoop extends Application {

    static File splashFile = new File("src/media/splash.mp4");
    static Media splashMedia = new Media(splashFile.toURI().toString());
    static MediaPlayer splashPlayer = new MediaPlayer(splashMedia);
    static MediaView splashView = new MediaView(splashPlayer);
    static File ingamemusicFile = new File("src/media/sound/music/ingame2.mp3");
    static Media ingamemusicMedia = new Media(ingamemusicFile.toURI().toString());
    static MediaPlayer ingamemusicPlayer = new MediaPlayer(ingamemusicMedia);
    static File gameovermusicFile = new File("src/media/sound/music/gameover1.mp3");
    static Media gameovermusicMedia = new Media(gameovermusicFile.toURI().toString());
    static MediaPlayer gameovermusicPlayer = new MediaPlayer(gameovermusicMedia);
    static File eatsoundFile = new File("src/media/sound/eat2.mp3");
    static Media eatsoundMedia = new Media(eatsoundFile.toURI().toString());
    static MediaPlayer eatsoundPlayer = new MediaPlayer(eatsoundMedia);
    static File deathsoundFile = new File("src/media/sound/death1.mp3");
    static Media deathsoundMedia = new Media(deathsoundFile.toURI().toString());
    static MediaPlayer deathsoundPlayer = new MediaPlayer(deathsoundMedia);
    Group root = new Group();
    Pane backgroundPane = new Pane(); //TODO NEU für Background
    Group splashscreen = new Group();
    //TODO NEU - Background stuff
    Image imgSource;
    BackgroundImage backgroundImage;
    Background backgroundView;
    private long lastUpdate = 0; //für Geschwindigkeitssteuerung

    public static void restartIngamemusic() { //Startet Ingame Musik von vorne
        ingamemusicPlayer.seek(Duration.ZERO);
        ingamemusicPlayer.play();
    }

    public static void stopIngamemusic() {
        ingamemusicPlayer.stop();
    }

    public static void restartGameovermusic() {
        gameovermusicPlayer.seek(Duration.ZERO);
        gameovermusicPlayer.play();
    }

    public static void stopGameovermusic() {
        gameovermusicPlayer.stop();
    }

    public static void playEatsound() {
        eatsoundPlayer.seek(Duration.ZERO);
        eatsoundPlayer.play();
    }

    public static void playDeathsound() {
        deathsoundPlayer.seek(Duration.ZERO);
        deathsoundPlayer.play();
    }
    //TODO END Background

    public static void main(String[] args) {
        launch(args);

    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        AnimationTimer timer;

        primaryStage.setWidth(1500);
        primaryStage.setHeight(700);

        primaryStage.setMinHeight(50);
        primaryStage.setMinWidth(50);

        //TODO NEU - Background stuff
        imgSource = new Image("media/grassTile.png");
        backgroundImage = new BackgroundImage(imgSource, BackgroundRepeat.REPEAT.REPEAT, BackgroundRepeat.REPEAT.REPEAT,
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
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {//Keyeventhandler fragt ab obs ein Keyevent gibt
            @Override
            public void handle(KeyEvent keyEvent) {
                control.keyHandler(keyEvent, snake, root, food, score, primaryStage);//control nimmt Keyevent und schaut speziell nach WASD

            }
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
                    snake.moveSnake(dx, dy, primaryStage);

                    lastUpdate = now;

                }
            }
        };
        splashPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                primaryStage.setScene(scene);
                fadeblacktotransparent.play();
                timer.start(); //Animationtimer startet nun erst nach dem Fade out des Hundevideos
                restartIngamemusic();
            }
        });

    }


}

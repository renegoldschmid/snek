package at.ac.fhcampuswien.snake;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import org.apache.log4j.Logger;

import java.io.File;


class AudioManager {
  private static Logger logger = Logger.getLogger(AudioManager.class.getName());
	private static File ingamemusicFile = new File("src/main/resources/at/ac/fhcampuswien/media/sound/music/ingame2.mp3");
	private static Media ingamemusicMedia = new Media(ingamemusicFile.toURI().toString());
    private static MediaPlayer ingamemusicPlayer = new MediaPlayer(ingamemusicMedia);
    private static File gameovermusicFile = new File("src/main/resources/at/ac/fhcampuswien/media/sound/music/gameover1.mp3");
    private static Media gameovermusicMedia = new Media(gameovermusicFile.toURI().toString());
    private static MediaPlayer gameovermusicPlayer = new MediaPlayer(gameovermusicMedia);
    private static File eatsoundFile = new File("src/main/resources/at/ac/fhcampuswien/media/sound/eat2.mp3");
    private static Media eatsoundMedia = new Media(eatsoundFile.toURI().toString());
    private static MediaPlayer eatsoundPlayer = new MediaPlayer(eatsoundMedia);
    private static File deathsoundFile = new File("src/main/resources/at/ac/fhcampuswien/media/sound/death1.mp3");
    private static Media deathsoundMedia = new Media(deathsoundFile.toURI().toString());
    private static MediaPlayer deathsoundPlayer = new MediaPlayer(deathsoundMedia);

    private AudioManager() {
    }

    static void restartIngamemusic() {
    	ingamemusicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        ingamemusicPlayer.seek(Duration.ZERO);
        ingamemusicPlayer.play();
        logger.debug(String.format("Ingame music restarted: %s", ingamemusicPlayer.getMedia().getSource()));
    }

    static void stopIngamemusic() {
        ingamemusicPlayer.stop();
        logger.debug(String.format("Ingame music stopped: %s", ingamemusicPlayer.getMedia().getSource()));
    }

    static void restartGameovermusic() {
        gameovermusicPlayer.seek(Duration.ZERO);
        gameovermusicPlayer.play();
        logger.debug(String.format("Restart gameover music: %s", gameovermusicPlayer.getMedia().getSource()));
    }

    static void stopGameovermusic() {
        gameovermusicPlayer.stop();
        logger.debug(String.format("Stopping gameover music: %s", gameovermusicPlayer.getMedia().getSource()));
    }

    static void playEatsound() {
        eatsoundPlayer.seek(Duration.ZERO);
        eatsoundPlayer.play();
        logger.debug(String.format("Playing eat sound: %s", eatsoundPlayer.getMedia().getSource()));
    }

    static void playDeathsound() {
        deathsoundPlayer.seek(Duration.ZERO);
        deathsoundPlayer.play();
        logger.debug(String.format("Playing death sound: %s", deathsoundPlayer.getMedia().getSource()));
    }
}

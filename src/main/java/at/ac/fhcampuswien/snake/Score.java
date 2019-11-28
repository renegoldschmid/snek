package at.ac.fhcampuswien.snake;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class Score {
    private int scoreValue = GameConstants.INIT_SCORE;
    private final Label score = new Label("Score: " + scoreValue);

    Score(Group group) {
        score.setFont(new Font("Arial", 50));
        score.setText("Score: " + scoreValue);
        group.getChildren().add(score);

    }


    void scoreRespawn(Group group) {
        scoreValue = GameConstants.INIT_SCORE;
        score.setFont(new Font("Arial", 50));
        score.setText("Score: " + scoreValue);
        group.getChildren().add(score);

    }

    void upScoreValue() {
        scoreValue++;
        score.setText("Score: " + scoreValue);
    }

    int getScore() {
        return scoreValue;
    }


}

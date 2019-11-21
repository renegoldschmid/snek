package at.ac.fhcampuswien.snake;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class Score {
    private int scoreValue = 0;
    Label score = new Label("Score: " + scoreValue);

    public Score(Group group) {
        score.setFont(new Font("Arial", 50));
        score.setText("Score: " + scoreValue);
        group.getChildren().add(score);

    }

    public void scoreRespawn(Group group) {
        scoreValue = 0;
        score.setFont(new Font("Arial", 50));
        score.setText("Score: " + scoreValue);
        group.getChildren().add(score);

    }

    public void upScoreValue() {
        scoreValue++;
        score.setText("Score: " + scoreValue);
    }

    public int getScore() {
        return scoreValue;
    }


}

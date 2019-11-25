package at.ac.fhcampuswien.snake;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

class Score {
    private int scoreValue = 0;
    private final Label score = new Label("Score: " + scoreValue);

    Score(Group group) {
        score.setFont(new Font("Arial", 50));
        score.setText("Score: " + scoreValue);
        group.getChildren().add(score);

    }

    void scoreRespawn(Group group) {
        scoreValue = 0;
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

package at.ac.fhcampuswien.snake;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

class Score {
    private int scoreValue = GameConstants.INIT_SCORE;
    private final Label scoreLabel = new Label(GameConstants.MESSAGE_SCORE + scoreValue);

    Score(Group group) {
        scoreLabel.setFont(new Font("Arial", 50));
        scoreLabel.setText(GameConstants.MESSAGE_SCORE + scoreValue);
        group.getChildren().add(scoreLabel);

    }


    void scoreRespawn(Group group) {
        scoreValue = GameConstants.INIT_SCORE;
        scoreLabel.setFont(new Font("Arial", 50));
        scoreLabel.setText(GameConstants.MESSAGE_SCORE + scoreValue);
        group.getChildren().add(scoreLabel);

    }

    void upScoreValue() {
        scoreValue++;
        scoreLabel.setText(GameConstants.MESSAGE_SCORE + scoreValue);
    }

    int getScoreLabel() {
        return scoreValue;
    }


}

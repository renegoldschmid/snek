package at.ac.fhcampuswien.snake;


import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Random;

//Labels
class Gameboard {
    private final String[] touchWall = new String[9];
    private final String[] touchTail = new String[6];
    private final Random rand = new Random();
    private final MessageFactory gameMessages = new MessageFactory();

    Gameboard() {
        initializeMessageFactory();
    }

    private void initializeMessageFactory() {
        //add touch wall deaths
        gameMessages.addGameMessage("Stop touching the wall \nas if it's your boyfriend.....", "wallDeath");
        gameMessages.addGameMessage("You never touch me \nin the way you touched that wall :*(", "wallDeath");
        gameMessages.addGameMessage("Walls are your favorite thing huh?", "wallDeath");
        gameMessages.addGameMessage("The wall you touched is solid,\n no comin through", "wallDeath");
        gameMessages.addGameMessage("Wall:1, You:0", "wallDeath");
        gameMessages.addGameMessage("Mimimimi you dead!", "wallDeath");
        gameMessages.addGameMessage("No one would survive this...", "wallDeath");
        gameMessages.addGameMessage("You colored the wall, \nwhat a nice thing to do", "wallDeath");
        gameMessages.addGameMessage("No touchy touchy le wall mi Friend! ", "wallDeath");

        //add touch tail deaths
        gameMessages.addGameMessage("Touching yourself huh? ; )", "tailDeath");
        gameMessages.addGameMessage("Snake ate herself in fury", "tailDeath");
        gameMessages.addGameMessage("Not your best day is it?....", "tailDeath");
        gameMessages.addGameMessage("Well...you tried...", "tailDeath");
        gameMessages.addGameMessage("Stop trying...", "tailDeath");
        gameMessages.addGameMessage("You touched that ass (tail..)!", "tailDeath");

    }


    void setDeathTouchWall(Score score, Group group, Stage stage) {
        Label deathTouchWall = new Label(gameMessages.getRandomGameMessageOfType("wallDeath")
                + "\nPress R for respawn" + "\nScore: " + score.getScore());
        deathTouchWall.setFont(new Font("Calibri",80));
        deathTouchWall.setTextFill(Color.BLACK);

        group.getChildren().clear();
        deathTouchWall.relocate(200, stage.getHeight()/2-300);
        group.getChildren().add(deathTouchWall);

    }

    void setDeathTouchTail(Score score, Group group, Stage stage) {
        Label deathTouchTail = new Label(gameMessages.getRandomGameMessageOfType("tailDeath") + "\nPress R for respawn"
                +"\nScore: " + score.getScore());
        deathTouchTail.setFont(new Font("Calibri",80));
        deathTouchTail.setTextFill(Color.BLACK);

        group.getChildren().clear();
        deathTouchTail.relocate(200, stage.getHeight()/2-200);
        group.getChildren().add(deathTouchTail);

    }

}

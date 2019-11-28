package at.ac.fhcampuswien.snake;


import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

//Labels
class Gameboard {
    private final MessageFactory gameMessages = new MessageFactory();

    Gameboard() {
        initializeMessageFactory();
    }

    private void initializeMessageFactory() {
        //add touch wall deaths
        gameMessages.addGameMessage("Stop touching the wall \nas if it's your boyfriend.....", GameConstants.WALL_DEATH);
        gameMessages.addGameMessage("You never touch me \nin the way you touched that wall :*(", GameConstants.WALL_DEATH);
        gameMessages.addGameMessage("Walls are your favorite thing huh?", GameConstants.WALL_DEATH);
        gameMessages.addGameMessage("The wall you touched is solid,\n no comin through", GameConstants.WALL_DEATH);
        gameMessages.addGameMessage("Wall:1, You:0", GameConstants.WALL_DEATH);
        gameMessages.addGameMessage("Mimimimi you dead!", GameConstants.WALL_DEATH);
        gameMessages.addGameMessage("No one would survive this...", GameConstants.WALL_DEATH);
        gameMessages.addGameMessage("You colored the wall, \nwhat a nice thing to do", GameConstants.WALL_DEATH);
        gameMessages.addGameMessage("No touchy touchy le wall mi Friend! ", GameConstants.WALL_DEATH);

        //add touch tail deaths
        gameMessages.addGameMessage("Touching yourself huh? ; )", GameConstants.TAIL_DEATH);
        gameMessages.addGameMessage("Snake ate herself in fury", GameConstants.TAIL_DEATH);
        gameMessages.addGameMessage("Not your best day is it?....", GameConstants.TAIL_DEATH);
        gameMessages.addGameMessage("Well...you tried...", GameConstants.TAIL_DEATH);
        gameMessages.addGameMessage("Stop trying...", GameConstants.TAIL_DEATH);
        gameMessages.addGameMessage("You touched that ass (tail..)!", GameConstants.TAIL_DEATH);

    }


    void setDeathTouchWall(Score score, Group group, Stage stage) {
        Label deathTouchWall = new Label(gameMessages.getRandomGameMessageOfType("wallDeath")
                + "\nPress R for respawn" + "\nScore: " + score.getScoreLabel());
        deathTouchWall.setFont(new Font("Calibri",80));
        deathTouchWall.setTextFill(Color.BLACK);

        group.getChildren().clear();
        deathTouchWall.relocate(200, stage.getHeight()/2-300);
        group.getChildren().add(deathTouchWall);

    }

    void setDeathTouchTail(Score score, Group group, Stage stage) {
        Label deathTouchTail = new Label(gameMessages.getRandomGameMessageOfType("tailDeath") + "\nPress R for respawn"
                + "\nScore: " + score.getScoreLabel());
        deathTouchTail.setFont(new Font("Calibri",80));
        deathTouchTail.setTextFill(Color.BLACK);

        group.getChildren().clear();
        deathTouchTail.relocate(200, stage.getHeight()/2-200);
        group.getChildren().add(deathTouchTail);

    }

}

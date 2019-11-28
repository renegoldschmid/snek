package at.ac.fhcampuswien.snake;

import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.Random;


class GameObject {
    private Random randomFoodColorOffset = new Random();
    private Rectangle food = new Rectangle(GameConstants.FOOD_WIDTH, GameConstants.FOOD_HEIGHT); //public um X/Y Koordinaten zu bekommen
    private double redPart;
    private double greenPart;
    private double bluePart;
    private Bounds fbound;

    double[] getColor() { // returned ein double Array mit den Farben für den Schwanz der Schlange, wird nacher von eat aufgerufen
        double[] colors = new double[3];
        colors[0] = redPart;
        colors[1] = greenPart;
        colors[2] = bluePart;
        return colors;

    }


    void setFood(Group g, Stage stage) {
        g.getChildren().remove(food);//um vorheriges Food verschwinden zu lassen
        redPart = randomFoodColorOffset.nextDouble();
        greenPart = randomFoodColorOffset.nextDouble();
        bluePart = randomFoodColorOffset.nextDouble();
        food.setFill(Color.color(redPart, greenPart, bluePart)); // hier werden zufällige Farben für das Food (und damit auch den Tail) übergeben
        food.relocate(randomFoodColorOffset.nextInt((int) stage.getWidth() - GameConstants.FOOD_BORDER_OFFSET), randomFoodColorOffset.nextInt((int) stage.getHeight() - GameConstants.FOOD_BORDER_OFFSET)); // Random Location mit Abstand vom Rand jeweils 40
        g.getChildren().add(food);
        fbound = food.getBoundsInParent();

    }

    Bounds getBound() {
        return fbound;
    }
}

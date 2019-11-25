package at.ac.fhcampuswien.snake;

import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.Random;


public class GameObject {
    public Rectangle food = new Rectangle(GameConstants.FOOD_WIDTH, GameConstants.FOOD_HEIGHT); //public um X/Y Koordinaten zu bekommen
    private Random rand;
    private double redPart, greenPart, bluePart;
    private Bounds fbound;

    public GameObject() {

    }

    public double[] getColor() { // returned ein double Array mit den Farben für den Schwanz der Schlange, wird nacher von eat aufgerufen
        double[] colors = new double[3];
        colors[0] = redPart;
        colors[1] = greenPart;
        colors[2] = bluePart;
        return colors;

    }


    public void setFood(Group g, Stage stage) {
        g.getChildren().remove(food);//um vorheriges Food verschwinden zu lassen
        rand = new Random();

        food.setFill(Color.color(redPart = rand.nextDouble(), greenPart = rand.nextDouble(), bluePart = rand.nextDouble())); // hier werden zufällige Farben für das Food (und damit auch den Tail) übergeben
        food.relocate(rand.nextInt((int) stage.getWidth() - GameConstants.FOOD_BORDER_OFFSET), rand.nextInt((int) stage.getHeight() - GameConstants.FOOD_BORDER_OFFSET)); // Random Location mit Abstand vom Rand jeweils 40
        g.getChildren().add(food);
        fbound = food.getBoundsInParent();

    }

    public Bounds getBound() {
        return fbound;
    }
}

package at.ac.fhcampuswien.snake;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class SecondaryController {

    public Button secondaryButton;

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}
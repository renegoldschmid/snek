module at.ac.fhcampuswien.snake {
    requires javafx.controls;
    requires javafx.media;
    requires javafx.fxml;

    opens at.ac.fhcampuswien.snake to javafx.fxml;
    exports at.ac.fhcampuswien.snake;
}
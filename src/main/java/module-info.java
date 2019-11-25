module at.ac.fhcampuswien.snake {
    requires javafx.controls;
    requires javafx.media;
    requires javafx.fxml;
	requires java.desktop;

    opens at.ac.fhcampuswien.snake to javafx.fxml;
    exports at.ac.fhcampuswien.snake;
}
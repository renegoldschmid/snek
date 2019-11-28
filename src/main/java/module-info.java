module at.ac.fhcampuswien.snake {
    requires javafx.controls;
    requires javafx.media;
    requires javafx.fxml;
	requires java.desktop;
    requires org.apache.logging.log4j;
    requires log4j;

    opens at.ac.fhcampuswien.snake to javafx.fxml;
    exports at.ac.fhcampuswien.snake;
}
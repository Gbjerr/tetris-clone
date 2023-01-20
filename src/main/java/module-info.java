module com.example.tetrisclone {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    exports startup;
    opens startup to javafx.fxml;
}
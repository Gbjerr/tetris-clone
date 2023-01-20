package startup;

import controller.GameController;
import view.View;
import javafx.application.Application;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    GameController gameController;

    @Override
    public void start(Stage primaryStage) {

        View view = new View();
        this.gameController = new GameController(view);
        primaryStage.setTitle("Tetris");

        primaryStage.setOnCloseRequest((event) -> {
            Platform.exit();
            System.exit(0);
        });

        Scene scene = new Scene(view);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

    }
    public static void main(String[] args) { launch(args); }
}

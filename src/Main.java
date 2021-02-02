import javafx.application.Application;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import view.View;

public class Main extends Application {

    GameController gameController;

    @Override
    public void start(Stage primaryStage) throws Exception{

        View view = new View();
        this.gameController = new GameController(view);
        primaryStage.setTitle("Tetris");

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });

        Scene scene = new Scene(view);
        initListeners(scene);
        primaryStage.setScene(scene);
        primaryStage.show();

    }


    private void initListeners(Scene scene) {

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                Platform.runLater(()-> {
                    gameController.handleKeyCode(keyEvent.getCode());
                });
            }
        });


    }




    public static void main(String[] args) { launch(args); }
}

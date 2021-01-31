package sample;

import controller.Controller;
import javafx.application.Application;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import view.View;

public class Main extends Application {

    Controller controller;

    @Override
    public void start(Stage primaryStage) throws Exception{

        View view = new View();
        this.controller = new Controller(view);
        primaryStage.setTitle("Tetris");
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
                    controller.handleKeyCode(keyEvent.getCode());
                });
            }
        });
    }




    public static void main(String[] args) { launch(args); }
}

package view;

import model.Point;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Class represents all that is contained in the GUI.
 */
public class View extends Pane {
    private final GameDisplay gameDisplay;
    private final ShapeDisplay shapeDisplay;
    private final Text score;
    private final Text level;
    private final Text gameOver;
    private final static Font RETRO = Font.loadFont(View.class.getResourceAsStream("/fonts/Retro_Gaming.ttf"), 120);

    private final Button startButton;


    /**
     * constructor
     */
    public View() {

        setPrefSize(475, 630);
        setStyle("-fx-background-color: black;");

        Rectangle box = new Rectangle(302.5, 602.5);
        box.setLayoutX(13.5);
        box.setLayoutY(13.5);
        box.setFill(Color.WHITE);

        score = new Text("Score: \n0");
        // css stylings
        score.setStyle("-fx-fill: white;" +
                       "-fx-alignment: center;" +
                       "-fx-font-size: 20;");
        score.setFont(RETRO);

        score.setLayoutX(320);
        score.setLayoutY(100);

        level = new Text("Level: \n1");
        // css stylings
        level.setStyle("-fx-fill: white;" +
                "-fx-alignment: center;" +
                "-fx-font-size: 20;");
        level.setFont(RETRO);

        level.setLayoutX(320);
        level.setLayoutY(180);

        gameOver = new Text();
        gameOver.setStyle("-fx-fill: red;" +
                "-fx-alignment: center;" +
                "-fx-font-size: 35;");
        gameOver.setFont(RETRO);

        gameOver.setLayoutX(330);
        gameOver.setLayoutY(250);

        Text nextFigure = new Text("Next figure: ");

        // css stylings
        nextFigure.setStyle("-fx-fill: white;" +
                "-fx-alignment: center;" +
                "-fx-font-size: 20;");
        nextFigure.setFont(RETRO);
        nextFigure.setLayoutX(320);
        nextFigure.setLayoutY(350);

        gameDisplay = new GameDisplay();
        gameDisplay.setLayoutX(15);
        gameDisplay.setLayoutY(15);

        shapeDisplay = new ShapeDisplay();
        shapeDisplay.setLayoutX(330);
        shapeDisplay.setLayoutY(370);

        startButton = new Button("Start");
        startButton.setPrefSize(100, 50);
        // stylings for button
        startButton.setStyle("-fx-background-color: \n" +
                "        #090a0c,\n" +
                "        linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%),\n" +
                "        linear-gradient(#20262b, #191d22),\n" +
                "        radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));\n" +
                "        -fx-background-radius: 5,4,3,5;\n" +
                "        -fx-background-insets: 0,1,2,0;\n" +
                "        -fx-text-fill: white;\n" +
                "        -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );\n" +
                "        -fx-text-fill: linear-gradient(white, #d0d0d0);\n" +
                "        -fx-font-size: 12px;\n" +
                "        -fx-padding: 10 20 10 20;");
        startButton.setFont(RETRO);

        startButton.setLayoutX(330);
        startButton.setLayoutY(500);

        getChildren().addAll(startButton, score, level, nextFigure, gameOver, box, gameDisplay, shapeDisplay);
    }

    public void updateShapeDisplay(Point[] points, Color color) {
        shapeDisplay.update(points, color);
    }

    public void updateGameDisplay(Point[] points, Color color, boolean[][] field) {
        gameDisplay.update(points, color, field);
    }

    public void resetScore() {
        score.setText("Score: \n0");
    }

    public void updateLevel(int level) {
        this.level.setText("Level: \n" + level);
    }

    public void updateScore(int score) {
        this.score.setText("Score: \n" + score);
    }
    public Button getStartButton() {
        return startButton;
    }

    public void setGameOver(boolean bool) {
        if(bool) {
            gameOver.setText("Game\nOver");
        }
        else {
            gameOver.setText("");
        }
    }
    public void resetLevel() {
        level.setText("Level: \n1");
    }
}

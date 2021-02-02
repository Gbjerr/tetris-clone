package view;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * Class represents the View which contains all that is going to be displayed
 * on the GUI
 */
public class View extends Pane {
    public GameDisplay gameDisplay;
    public shapeDisplay shapeDisplay;
    public Text score;
    public Text nextFigure;

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
                        "-fx-font-family: Orbitron;" +
                       "-fx-font-size: 20;");
        score.setLayoutX(330);
        score.setLayoutY(100);

        nextFigure = new Text("Next figure: ");
        // css stylings
        nextFigure.setStyle("-fx-fill: white;" +
                "-fx-alignment: center;" +
                "-fx-font-family: Orbitron;" +
                "-fx-font-size: 20;");
        nextFigure.setLayoutX(330);
        nextFigure.setLayoutY(300);

        gameDisplay = new GameDisplay();
        gameDisplay.setLayoutX(15);
        gameDisplay.setLayoutY(15);

        shapeDisplay = new shapeDisplay();
        shapeDisplay.setLayoutX(330);
        shapeDisplay.setLayoutY(340);

        getChildren().addAll(score, nextFigure, box, gameDisplay, shapeDisplay);
    }

}

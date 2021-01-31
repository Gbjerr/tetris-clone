package view;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class View extends Pane {
    public GameDisplay gameDisplay;
    public FigureDisplay figureDisplay;
    public Text score;
    public Text nextFigure;

    public View() {

        setPrefSize(475, 630);
        setStyle("-fx-background-color: black;");

        Rectangle box = new Rectangle(302.5, 602.5);
        box.setLayoutX(13.5);
        box.setLayoutY(13.5);
        box.setFill(Color.WHITE);

        score = new Text("Score: \n0");
        score.setStyle("-fx-fill: white;" +
                       "-fx-alignment: center;" +
                        "-fx-font-family: Orbitron;" +
                       "-fx-font-size: 20;");
        score.setLayoutX(330);
        score.setLayoutY(100);

        nextFigure = new Text("Next figure: ");
        nextFigure.setStyle("-fx-fill: white;" +
                "-fx-alignment: center;" +
                "-fx-font-family: Orbitron;" +
                "-fx-font-size: 20;");
        nextFigure.setLayoutX(330);
        nextFigure.setLayoutY(300);

        gameDisplay = new GameDisplay();
        gameDisplay.setLayoutX(15);
        gameDisplay.setLayoutY(15);

        figureDisplay = new FigureDisplay();
        figureDisplay.setLayoutX(330);
        figureDisplay.setLayoutY(340);

        getChildren().addAll(score, nextFigure, box, gameDisplay, figureDisplay);
    }

}

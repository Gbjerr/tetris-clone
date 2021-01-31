package view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import model.Figure;

import java.awt.Point;

public class FigureDisplay extends Canvas {
    private final int WIDTH = 120;
    private final int HEIGHT = 120;
    public GraphicsContext gc;

    public Figure figure;

    public FigureDisplay() {
        gc = getGraphicsContext2D();
        setHeight(HEIGHT);
        setWidth(WIDTH);

        //this.figure = figure;

    }

    public void update(Figure figure) {

        this.figure = figure;

        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, WIDTH, HEIGHT);

        paintFigure();

        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1.5);

        for(int i = 0; i < 4; i++) {
            gc.strokeLine(i*30, 0, i*30, HEIGHT);
        }

        for(int i = 0; i < 4; i++) {
            gc.strokeLine(0, i*30, WIDTH, i*30);
        }
    }



    public void paintFigure() {
        gc.setFill(figure.getColor());
        for(Point p : figure.getCoordinates()) {
            gc.fillRect((int) (p.x-1) * 30, (int) (p.y) * 30, 30, 30);
        }
    }
}

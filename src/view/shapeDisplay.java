package view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import resources.Shape;

import java.awt.Point;

/**
 * Class that represents a display, where the next figure is represented in
 * the tetris game
 */

public class shapeDisplay extends Canvas {
    private final int WIDTH = 120;
    private final int HEIGHT = 120;
    public GraphicsContext gc;

    public Shape shape;

    /**
     * constructor
     */
    public shapeDisplay() {
        gc = getGraphicsContext2D();
        setHeight(HEIGHT);
        setWidth(WIDTH);

    }

    /**
     * Method updates the display
     * @param shape - the figure to be displayed
     */
    public void update(Shape shape) {

        this.shape = shape;

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


    /**
     * Method paints the figure on the GraphicsContext
     */
    public void paintFigure() {
        gc.setFill(shape.getColor());
        for(Point p : shape.getCoordinates()) {
            gc.fillRect((int) (p.x-1) * 30, (int) (p.y) * 30, 30, 30);
        }
    }
}

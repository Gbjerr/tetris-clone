package view;

import model.Point;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Class that represents a display, where the next figure is represented in
 * the tetris game
 */
public class ShapeDisplay extends Canvas {
    private final int WIDTH;
    private final int HEIGHT;
    private final int EDGE_LEN;
    private final GraphicsContext gc;

    public ShapeDisplay() {
        gc = getGraphicsContext2D();

        WIDTH = 120;
        HEIGHT = 60;
        EDGE_LEN = 30;

        setHeight(HEIGHT);
        setWidth(WIDTH);
    }

    /**
     * Updates the display.
     *
     * @param points the figure to be displayed
     */
    public void update(Point[] points, Color color) {

        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, WIDTH, HEIGHT);

        gc.setFill(color);
        for(Point p : points) {
            gc.fillRect(p.x * EDGE_LEN, p.y * EDGE_LEN, EDGE_LEN, EDGE_LEN);
        }

        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1.5);

        for(int i = 0; i < 4; i++) {
            gc.strokeLine(i*EDGE_LEN, 0, i*EDGE_LEN, HEIGHT);
        }

        for(int i = 0; i < 4; i++) {
            gc.strokeLine(0, i*EDGE_LEN, WIDTH, i*EDGE_LEN);
        }
    }

}

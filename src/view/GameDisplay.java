package view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import resources.*;

import java.awt.Point;

/**
 * Class represents the Canvas where the game is displayed, and that which
 * appears on it.
 */
public class GameDisplay extends Canvas {

    final int WIDTH = 300;
    final int HEIGHT = 600;

    GraphicsContext gc;

    /**
     * constructor
     */
    public GameDisplay() {

        setWidth(WIDTH);
        setHeight(HEIGHT);
        gc = getGraphicsContext2D();

    }

    /**
     * Given a shape, paints it on the GraphicsContext
     * @param shape - The shape to display
     */
    public void paintshape(Shape shape) {
        gc.setFill(shape.getColor());
        for(Point p : shape.getCoordinates()) {
            gc.fillRect((int) p.x * 30, (int) p.y * 30, 30, 30);
        }
    }

    /**
     *
     * @param shape
     * @param field
     */
    public void update(Shape shape, boolean[][] field) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, WIDTH, HEIGHT);

        gc.setFill(Color.GREEN);

        // nested loop paints the blocks on the bottom
        for(int x = 0; x < field.length; x++) {

            for(int y = 0; y < field[0].length; y++) {
                if(field[x][y]){
                    gc.setFill(Color.GREEN);
                    gc.fillRect(x * 30, y * 30, 30, 30);
                }
            }

        }

        paintshape(shape);


        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1.5);

        for(int i = 0; i < 10; i++) {
            gc.strokeLine(i*30, 0, i*30, HEIGHT);
        }

        for(int i = 0; i < 20; i++) {
            gc.strokeLine(0, i*30, WIDTH, i*30);
        }
    }
}

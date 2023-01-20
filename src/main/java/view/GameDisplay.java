package view;

import model.Point;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * Class represents the screen where animations, tetromino pieces and other game elements is displayed.
 */
public class GameDisplay extends Canvas {

    private final int WIDTH;
    private final int HEIGHT;
    private final int EDGE_LEN;

    private final GraphicsContext gc;

    public GameDisplay() {
        WIDTH = 300;
        HEIGHT = 600;
        EDGE_LEN = 30;

        setWidth(WIDTH);
        setHeight(HEIGHT);
        gc = getGraphicsContext2D();
    }

    /**
     * Given a shape, paints it on the GraphicsContext.
     *
     * @param points the shape to display
     */
    public void paintFigure(Point[] points, Color color) {
        if(points == null) return;
        gc.setFill(color);
        for(Point p : points) {
            gc.fillRect(p.x * EDGE_LEN, p.y * EDGE_LEN, EDGE_LEN, EDGE_LEN);
        }
    }

    /**
     * Paints everything that is displayed on the game screen.
     *
     * @param points coordinates of the figure
     * @param field the game field with occupied and non-occupied coordinates
     */
    public void update(Point[] points, Color color, boolean[][] field) {
        // paint the background black
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, WIDTH, HEIGHT);

        gc.setFill(Color.GREEN);
        // nested loop paints the obstacles
        for(int x = 0; x < field.length; x++) {
            for(int y = 0; y < field[0].length; y++) {
                if(field[x][y]){
                    gc.fillRect(x * EDGE_LEN, y * EDGE_LEN, EDGE_LEN, EDGE_LEN);
                }
            }
        }
        // draw the figure
        if(color != null) paintFigure(points, color);

        // draw lines
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1.5);

        for(int i = 0; i < WIDTH / EDGE_LEN; i++) {
            gc.strokeLine(i*EDGE_LEN, 0, i*EDGE_LEN, HEIGHT);
        }

        for(int i = 0; i < HEIGHT / EDGE_LEN; i++) {
            gc.strokeLine(0, i*EDGE_LEN, WIDTH, i*EDGE_LEN);
        }
        if(points != null) updateForecast(points, field, color);
    }

    /**
     * Paints the forecast of current figure.
     *
     * @param points coordinates of the figure
     * @param field the game field
     * @param color color of the figure
     */
    private void updateForecast(Point[] points, boolean[][] field, Color color) {
        gc.setStroke(color);
        gc.setLineWidth(1.5);
        // get the coordinate whose y coordinate is the greatest
        int max = 0;
        for(Point p : points) if(max < p.y) max = p.y;
        // distance in depth between the obstacles and current coordinate
        int yDiff = field[0].length - 1 - max;
        outer:
        for(int i = 0; i < field[0].length - max; i++) {
            for(Point p : points) {
                // break if next step in the Y direction for current coordinate surpasses the floor
                if(p.y + i + 1 == field[0].length) break;
                // if next step in the Y direction for current coordinate collides with obstacles, then break and save
                // number of iterated steps
                if(field[p.x][p.y + i + 1]) {
                    yDiff = i;
                    break outer;
                }
            }
        }
        // copy over coordinates, X coordinates remain the same while Y is updated
        Point[] forecast = new Point[points.length];
        for(int i = 0; i < points.length; i++) {
            forecast[i] = new Point(points[i].x, points[i].y + yDiff);
        }
        paintHollowFigure(forecast);
    }

    /**
     * Paints only the outer edges of the tetromino.
     *
     * @param tetrominoCoordinates  coordinates of the tetromino
     */
    private void paintHollowFigure(Point[] tetrominoCoordinates) {

        // list where each coordinate represents an edge
        ArrayList<Point> edges = new ArrayList<>();

        /*
         add all edges from each box to the list

         NOTE: X or Y coordinate marked with minus represents that a change should be made in that axis by EDGE_LEN when
         edge is being drawn. Yes this is VERY unintuitive.
         */
        for(Point p : tetrominoCoordinates) {

            Point temp = new Point(-(p.x*EDGE_LEN + EDGE_LEN), p.y*EDGE_LEN);
            addToList(temp, edges);

            temp = new Point(p.x*EDGE_LEN, -(p.y*EDGE_LEN + EDGE_LEN));
            addToList(temp, edges);

            temp = new Point(-(p.x*EDGE_LEN + EDGE_LEN), p.y*EDGE_LEN + EDGE_LEN);
            addToList(temp, edges);

            temp = new Point(p.x*EDGE_LEN + EDGE_LEN, -(p.y*EDGE_LEN + EDGE_LEN));
            addToList(temp, edges);
        }

        // draw edges
        for(Point p : edges) {
            if(p.x <= 0) {
                gc.strokeLine(-1 * p.x - EDGE_LEN, p.y, -1 * p.x, p.y);
            } else {
                gc.strokeLine(p.x, -1 * p.y - EDGE_LEN, p.x, -1 * p.y);
            }
        }
    }

    /**
     * Adds coordinate to list.
     *
     * @param p the coordinate
     * @param list the list
     */
    private void addToList(Point p, ArrayList<Point> list) {
        if(list.contains(p)) {
            list.remove(p);
        } else {
            list.add(p);
        }
    }
}

package view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import model.*;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class GameDisplay extends Canvas {

    final int WIDTH = 300;
    final int HEIGHT = 600;

    GraphicsContext gc;

    public GameDisplay() {

        setWidth(WIDTH);
        setHeight(HEIGHT);
        gc = getGraphicsContext2D();

    }

    public void paintFigure(Figure figure) {
        gc.setFill(figure.getColor());
        for(Point p : figure.getCoordinates()) {
            gc.fillRect((int) p.x * 30, (int) p.y * 30, 30, 30);
        }
    }

    public void update(Figure figure, boolean[][] field) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, WIDTH, HEIGHT);

        gc.setFill(Color.GREEN);

        for(int x = 0; x < field.length; x++) {

            for(int y = 0; y < field[0].length; y++) {
                if(field[x][y]){
                    gc.setFill(Color.GREEN);
                    gc.fillRect(x * 30, y * 30, 30, 30);
                }
            }

        }

        paintFigure(figure);


        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1.5);

        for(int i = 0; i < 10; i++) {
            gc.strokeLine(i*30, 0, i*30, HEIGHT);
        }

        for(int i = 0; i < 20; i++) {
            gc.strokeLine(0, i*30, WIDTH, i*30);
        }
    }
    /*

    public void handleControls(KeyCode code) {

        if(code.equals(KeyCode.LEFT)) {

            for(Point p : figure.getCoordinates()) {
                if(p.x < 1) {
                    System.out.println("out of bounds!");
                    return;
                }
            }

            if(checkOverLapLeft(figure.getCoordinates())) return;
            figure.decrement(1, 0);

        } else if(code.equals(KeyCode.RIGHT)) {

            for(Point p : figure.getCoordinates()) {
                if(p.x > 8) {
                    System.out.println("out of bounds!");
                    return;
                }
            }

            if(checkOverLapRight(figure.getCoordinates())) return;
            figure.increment(1, 0);
        } else if(code.equals(KeyCode.UP)) {

            ArrayList<Point> list = figure.getNextRotation();

            for (Point p : list) {
                if (p.x < 1 || p.x > 8) {
                    System.out.println("out of bounds!");
                    return;
                }
            }

            if (!checkCollision(list)) {
                figure.rotate();
            }


        }
    }

     */
}

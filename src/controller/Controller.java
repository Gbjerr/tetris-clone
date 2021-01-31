package controller;

import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import model.*;
import view.View;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class Controller implements Runnable{
    private View view;
    private Thread gameThread;

    private Figure figure;
    private Figure nextFigure;
    private GameField gameField;

    public Color[] colors;

    int score;

    public Controller(View view) {
        this.score = 0;

        this.colors = new Color[]{Color.YELLOW, Color.RED, Color.TURQUOISE, Color.ORANGE, Color.BLUE,
                Color.CYAN, Color.DEEPPINK};

        this.gameField = new GameField();

        this.figure = initFigure();
        this.nextFigure = initFigure();



        this.view = view;
        this.gameThread = new Thread(this, "Game Thread");
        this.gameThread.start();

    }

    @Override
    public void run() {

        while(!gameField.isGameOver()) {

            System.out.println();
            int level = gameField.getClearedLevel();

            if(level != -1) {
                gameField.removeLevel(level);
                view.score.setText("Score: \n" + (score += 40));
            }

            figure.increment(0, 1);

            if(gameField.checkCollision(figure.getCoordinates())) {
                figure.decrement(0, 1);

                for(Point p : figure.getCoordinates()) {
                    gameField.getField()[p.x][p.y] = true;
                }

                figure = nextFigure;
                nextFigure = initFigure();

                view.figureDisplay.update(nextFigure);
            }


            try {
                java.util.concurrent.TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Platform.runLater(() -> {
                view.gameDisplay.update(figure, gameField.getField());
            });



        }

        System.out.println("Game over boys!!");
    }

    public Figure initFigure() {

        Random rand = new Random(System.currentTimeMillis());
        Color randColor = colors[rand.nextInt(7)];
        Figure temp = null;

        switch(rand.nextInt(7)) {

            case 0: temp = new Line(randColor); break;
            case 1: temp = new ZigzagLeft(randColor); break;
            case 2: temp = new Square(randColor); break;
            case 3: temp = new ZigzagRight(randColor); break;
            case 4: temp = new LLeft(randColor); break;
            case 5: temp = new LRight(randColor); break;
            case 6: temp = new Pyramid(randColor); break;
            default: break;
        }

        return temp;
    }


    public void handleKeyCode(KeyCode code) {

        if(code.equals(KeyCode.LEFT)) {

            for(Point p : figure.getCoordinates()) {
                if(p.x <= 0) {
                    System.out.println("out of bounds!");
                    return;
                }
            }

            if(gameField.checkCollisionLeft(figure.getCoordinates())) return;
            figure.decrement(1, 0);

        } else if(code.equals(KeyCode.RIGHT)) {

            for(Point p : figure.getCoordinates()) {
                if(p.x >= gameField.getField().length - 1) {
                    System.out.println("out of bounds!");
                    return;
                }
            }

            if(gameField.checkCollisionRight(figure.getCoordinates())) return;
            figure.increment(1, 0);
        } else if(code.equals(KeyCode.UP)) {

            ArrayList<Point> list = figure.getNextRotation();

            for (Point p : list) {
                if (p.x <= 0 || p.x >= gameField.getField().length) {
                    System.out.println("out of bounds!");
                    return;
                }
            }

            if (!gameField.checkCollision(list)) {
                figure.rotate();
            }


        }
    }


}

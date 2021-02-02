import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import resources.*;
import view.View;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

/**
 * Class that controls what happens at different keylistener actions, for example
 * at keypresses and what should be displayed at the different screens in the view
 * at what times.
 */
public class GameController {
    private View view;
    private Task<Void> gameLoop;

    private Shape shape;
    private Shape nextShape;
    private GameField gameField;

    public Color[] colors;

    int score;

    /**
     * Constructor for gameController class
     * @param view
     */
    public GameController(View view) {
        this.score = 0;
        this.colors = new Color[]{Color.rgb(248, 121, 41), Color.rgb(11, 165, 223), Color.rgb(192, 58, 180),
                Color.rgb(135, 212, 47), Color.rgb(215, 23, 53), Color.rgb(44, 87, 220), Color.rgb(251, 187, 49)};

        this.gameField = new GameField();

        this.shape = initShape();
        this.nextShape = initShape();



        this.view = view;

        this.gameLoop = new Task<Void>() {

            @Override
            protected Void call() throws Exception {

                while(!gameField.isGameOver()) {

                    System.out.println();

                    // check if there's a cleared level on the field, and if so,
                    // remove it and increase points
                    int level = gameField.getClearedLevel();
                    if(level != -1) {
                        gameField.removeLevel(level);
                        view.score.setText("Score: \n" + (score += 40));
                    }

                    // increment y position one step
                    shape.increment(0, 1);

                    // when a collision happens, add the tiles from the shape to
                    // the game field and initiate a new shape
                    if(gameField.checkCollision(shape.getCoordinates())) {
                        shape.decrement(0, 1);

                        for(Point p : shape.getCoordinates()) {
                            gameField.getField()[p.x][p.y] = true;
                        }

                        shape = nextShape;
                        nextShape = initShape();

                        view.shapeDisplay.update(nextShape);
                    }


                    try {
                        java.util.concurrent.TimeUnit.MILLISECONDS.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Platform.runLater(() -> {
                        view.gameDisplay.update(shape, gameField.getField());
                    });



                }

                System.out.println("Game over boys!!");

                return null;
            }
        };

        new Thread(gameLoop).start();

    }

    /**
     * Initiates a random shape and returns it
     * @return - random shape
     */
    public Shape initShape() {

        Random rand = new Random(System.currentTimeMillis());
        Color randColor = colors[rand.nextInt(colors.length)];
        Shape temp = null;

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

    /**
     * Based on entered keycode, handle actions for the current shape
     * @param code - the keycode
     */
    public void handleKeyCode(KeyCode code) {

        // if statement for different keycodes
        if(code.equals(KeyCode.LEFT)) {

            for(Point p : shape.getCoordinates()) {
                if(p.x <= 0) {
                    System.out.println("out of bounds!");
                    return;
                }
            }

            if(gameField.checkCollisionLeft(shape.getCoordinates())) return;
            shape.decrement(1, 0);

        } else if(code.equals(KeyCode.RIGHT)) {

            for(Point p : shape.getCoordinates()) {
                if(p.x >= gameField.getField().length - 1) {
                    System.out.println("out of bounds!");
                    return;
                }
            }

            if(gameField.checkCollisionRight(shape.getCoordinates())) return;
            shape.increment(1, 0);
        } else if(code.equals(KeyCode.UP)) {

            ArrayList<Point> list = shape.getNextRotation();

            for (Point p : list) {
                if (p.x <= 0 || p.x >= gameField.getField().length) {
                    System.out.println("out of bounds!");
                    return;
                }
            }

            if (!gameField.checkCollision(list)) {
                shape.rotate();
            }


        }
    }


}

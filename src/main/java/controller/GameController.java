package controller;

import model.GameField;
import model.Tetromino;
import model.Point;
import view.View;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;

/**
 * Class that controls what happens at button presses and key presses. Also decides what should be displayed on
 * the different screens in the view.
 */
public class GameController {
    private final View view;
    private final GameField gameField;
    private final int defaultDelay;
    private int delay;
    private Tetromino fallingPiece;
    private Tetromino nextPiece;
    private boolean isRunning;
    private int level;
    private int clearedLines;
    private int score;

    public GameController(View view) {
        score = 0;
        level = 1;
        clearedLines = 0;
        gameField = new GameField();

        // delays in nano seconds
        defaultDelay = 500000000;
        delay = defaultDelay;

        fallingPiece = null;
        nextPiece = new Tetromino();


        this.view = view;
        Platform.runLater(() -> {
            this.view.updateShapeDisplay(nextPiece.getCoordinates(), nextPiece.getColor());
            this.view.updateGameDisplay(null, null, gameField.getField());
        });

        initListeners();
    }

    /**
     * Initializes the animation timer which has the game loop, start command and stop command.
     */
    private void initGameLoop() {

        AnimationTimer at = new AnimationTimer() {
            private long lastUpdate;

            @Override
            public void stop() {
                isRunning = false;
                fallingPiece = null;
                Platform.runLater(() -> {
                    view.setGameOver(true);
                    view.updateGameDisplay(null, null, gameField.getField());
                });
                super.stop();
            }

            @Override
            public void start() {
                lastUpdate = System.nanoTime();
                isRunning = true;

                fallingPiece = new Tetromino();
                fallingPiece.moveX(3);
                fallingPiece.moveY(1);

                gameField.clear();
                score = 0;
                level = 1;

                Platform.runLater(() -> {
                    view.updateGameDisplay(fallingPiece.getCoordinates(), fallingPiece.getColor(), gameField.getField());
                    view.resetScore();
                    view.resetLevel();
                    view.setGameOver(false);
                });

                super.start();
            }

            // game loop is here
            @Override
            public void handle(long now) {
                if(gameField.isGameOver()) stop();
                long elapsedNanoSeconds = now - lastUpdate;

                // when the elapsed time is une delay unit, move piece down a step and update display
                if(elapsedNanoSeconds >= delay) {
                    fallingPiece.moveY(1);
                    Platform.runLater(() -> view.updateGameDisplay(fallingPiece.getCoordinates(), fallingPiece.getColor(), gameField.getField()));
                    lastUpdate = now;
                }

                // when a collision happens, add the tiles from the shape to
                // the game field and initiate a new shape
                if(gameField.checkCollision(fallingPiece.getCoordinates())) {
                    fallingPiece.moveY(-1);
                    for(Point p : fallingPiece.getCoordinates()) {
                            if(p.y < 0) continue;
                            gameField.getField()[p.x][p.y] = true;
                    }

                    if(gameField.isGameOver()) stop();
                    fallingPiece = nextPiece;
                    fallingPiece.moveX(3);
                    fallingPiece.moveY(1);

                    nextPiece = new Tetromino();

                    int tempNormalDelay = defaultDelay - level * 30000000;
                    if (delay != tempNormalDelay) delay = tempNormalDelay;

                    Platform.runLater(() -> {
                        view.updateShapeDisplay(nextPiece.getCoordinates(), nextPiece.getColor());
                        view.updateGameDisplay(fallingPiece.getCoordinates(), fallingPiece.getColor(), gameField.getField());
                    });

                    // check if there is cleared lines on the field, and if so,
                    // remove those and increase points
                    ArrayList<Integer> currentClearedLines = gameField.getClearedLines();
                    if(currentClearedLines != null) {
                        clearedLines += currentClearedLines.size();
                        score += calculatePoints(currentClearedLines.size(), level);
                        for(int i : currentClearedLines) {
                            gameField.removeLine(i);
                        }
                        Platform.runLater(() -> {
                            view.updateScore(score);
                            view.updateGameDisplay(fallingPiece.getCoordinates(), fallingPiece.getColor(), gameField.getField());
                        });
                    }

                    // level up once when every 5 lines is cleared
                    if(clearedLines >= 5) {
                        Platform.runLater(() -> view.updateLevel(++level));
                        delay = defaultDelay - level * 30000000;
                        clearedLines %= 5;
                    }
                }
            }
        };
        at.start();
    }

    /**
     * Calculates earned points based on current level and number of lines cleared.
     *
     * @param numClearedLines number of cleared lines
     * @param level current level
     * @return amount of points earned
     */
    private int calculatePoints(int numClearedLines, int level) {
        int points = 0;
        switch (numClearedLines) {
            case 1 :
                points += 40 * level;
                break;
            case 2 :
                points += 100 * level;
                break;
            case 3 :
                points += 300 * level;
                break;
            case 4 :
                points += 1200 * level;
                break;
        }
        return points;
    }

    /**
     * Method handles listeners for user input.
     */
    private void initListeners() {
        // initialize listener for start button
        view.getStartButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(isRunning) return;
                initGameLoop();
            }
        });

        // initialize listener for key presses
        view.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                handleKeyCode(keyEvent.getCode());
            }
        });
    }

    /**
     * Handle logic for each defined key code here.
     *
     * @param code the keycode
     */
    public void handleKeyCode(KeyCode code) {
        if (!isRunning) return;

        // switch statement for different keycodes
        switch (code) {
            // LEFT arrow pressed - move figure left if possible
            case LEFT:
                for (Point p : fallingPiece.getCoordinates()) {
                    if (p.x <= 0) return;
                }
                if (gameField.checkPotentialCollisionLeft(fallingPiece.getCoordinates())) return;
                fallingPiece.decrement(1, 0);
                break;
            // RIGHT arrow pressed - move figure right if possible
            case RIGHT:
                for (Point p : fallingPiece.getCoordinates()) {
                    if (p.x >= gameField.getField().length - 1) return;
                }
                if (gameField.checkPotentialCollisionRight(fallingPiece.getCoordinates())) return;
                fallingPiece.moveX(1);
                break;
            // UP arrow pressed - rotate figure if possible
            case UP:
                Point[] points = fallingPiece.getNextRotation();
                if(points == null) return;
                for (Point p : points) {
                    if (p.x < 0 || p.x >= gameField.getField().length || p.y < 0) return;
                }
                if (!gameField.checkCollision(points)) {
                    fallingPiece.rotate();
                }
                break;
            // DOWN arrow pressed - move figure faster to the ground
            case DOWN:
                delay = defaultDelay / 15;
                break;
            // default: do nothing
            default:
                break;
        }
        // update screen
        Platform.runLater(() -> view.updateGameDisplay(fallingPiece.getCoordinates(), fallingPiece.getColor(), gameField.getField()));
    }
}

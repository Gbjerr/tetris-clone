package model;

import java.util.ArrayList;

/**
 * Class represents the game field, and which part of the field that makes up the
 * stack (collided blocks)
 */
public class GameField {

    // two-dimensional array marks which blocks that are free
    private final boolean[][] field;

    public GameField() {
        this.field = new boolean[10][20];
    }

    /**
     * Methods checks if coordinates in list collides with the stack of collided blocks.
     *
     * @param points the coordinates to be checked
     * @return true or false whether a collision has happened or not
     */
    public boolean checkCollision(Point[] points) {
        for(Point p : points) {
            if(p.y >= field[0].length) {
                return true;
            } else if(field[p.x][p.y]) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method checks if some coordinate in the list, being shifted one position
     * to the right, conflicts with any existing block on the field.
     *
     * @param points the coordinates to be checked
     * @return if any given coordinate conflicts
     */
    public boolean checkPotentialCollisionRight(Point[] points) {
        for(Point p : points) {
            if(field[p.x + 1][p.y]) return true;
        }
        return false;
    }

    /**
     * Method checks if some coordinate in the list, being shifted one position
     * to the left, conflicts with any existing block on the field.
     *
     * @param points the coordinates to be checked
     * @return if any given coordinate conflicts
     */
    public boolean checkPotentialCollisionLeft(Point[] points) {
        for(Point p : points) {
            if(field[p.x - 1][p.y]) return true;
        }
        return false;
    }

    /**
     * Returns Y (row) positions where all positions on that row is occupied.
     *
     * @return rows
     */
    public ArrayList<Integer> getClearedLines() {
        ArrayList<Integer> lines = new ArrayList<>();
        int count = 0;
        for(int y = 0; y < field[0].length; y++) {
            for(int x = 0; x < field.length; x++) {

                if(field[x][y]) {
                    count++;
                } else {
                    count = 0;
                    break;
                }
            }
            if(count == field.length) {
                lines.add(y);
            }
            count = 0;
        }
        return lines;
    }

    /**
     * Removes all coordinates on a given row, copies and moves all rows above it one step down.
     *
     * @param level the specific row to remove
     */
    public void removeLine(int level) {
        int count = 0;
        // move and copy rows from above one step down
        for(int y = level - 1; y >= 0; y--){

            for(int x = 0; x < field.length; x++) {
                field[x][y + 1] = field[x][y];
                if(!field[x][y]) {
                    count++;
                }
            }
            // level above was empty, thus stop
            if(count == field.length) return;
            count = 0;
        }
    }

    /**
     * Determines if game is over by looking at the roof of the game field.
     *
     * @return whether the game is over
     */
    public boolean isGameOver() {
        for(int x = 0; x < field.length; x++) {
            if(field[x][0]) {
                return true;
            }
        }
        return false;
    }

    /**
     * Getter for the field.
     *
     * @return the field
     */
    public boolean[][] getField() {
        return field;
    }

    /**
     * Simply reset the game field.
     */
    public void clear() {
        for(int x = 0; x < field.length; x++) {
            for(int y = 0; y < field[0].length; y++) {
                field[x][y] = false;
            }
        }
    }
}

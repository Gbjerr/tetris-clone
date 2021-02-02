package resources;

import java.awt.Point;
import java.util.ArrayList;

/**
 * Class represents the gamefield, and which part of the field that makes up the
 * stack (collided blocks)
 */
public class GameField {

    // two-dimensional array marks which blocks that have been, in one way or another
    // collided and makes up the stack
    private boolean[][] field;

    public GameField() {
        this.field = new boolean[10][20];
    }

    /**
     * Methods checks if coordinates in list collides with the stack of collided blocks
     * @param list
     * @return - returns true or false whether a collision has happened or not
     */
    public boolean checkCollision(ArrayList<Point> list) {

        for(Point p : list) {

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
     * to the right, collides with the stack of collided blocks
     * @param list
     * @return
     */
    public boolean checkCollisionRight(ArrayList<Point> list) {

        for(Point p : list) {
            if(field[p.x + 1][p.y]) return true;
        }

        return false;
    }

    /**
     * Method checks if some coordinate in the list, being shifted one position
     * to the left, collides with the stack of collided blocks
     * @param list
     * @return
     */
    public boolean checkCollisionLeft(ArrayList<Point> list) {

        for(Point p : list) {
            if(field[p.x - 1][p.y]) return true;
        }

        return false;
    }

    /**
     * Method returns a Y position, if all tiles on that Y-level is reserved
     * is a part of the stack of collided blocks
     * @return - The Y position
     */
    public int getClearedLevel() {

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

            if(count == 10) return y;
        }
        return -1;
    }

    /**
     * Removes all coordinates from the stack of collided blocks, on a certain
     * Y level
     * @param level
     */
    public void removeLevel(int level) {

        for(int y = level - 1; y >= 0; y--){

            for(int x = 0; x < field.length; x++) {

                if(field[x][y]) {
                    field[x][y + 1] = true;
                } else {
                    field[x][y + 1] = false;
                }
            }
        }

    }

    /**
     * Determines if game is over, by looking at the roof of the game field
     * @return
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
     * getter for the field
     * @return
     */
    public boolean[][] getField() {
        return field;
    }
}

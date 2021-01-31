package model;

import java.awt.Point;
import java.util.ArrayList;

public class GameField {

    private boolean[][] field;

    public GameField() {
        this.field = new boolean[10][20];
    }

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

    public boolean checkCollisionRight(ArrayList<Point> list) {

        for(Point p : list) {
            if(field[p.x + 1][p.y]) return true;
        }

        return false;
    }

    public boolean checkCollisionLeft(ArrayList<Point> list) {

        for(Point p : list) {
            if(field[p.x - 1][p.y]) return true;
        }

        return false;
    }

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

    public boolean isGameOver() {

        for(int x = 0; x < field.length; x++) {
            if(field[x][0]) {
                return true;
            }
        }

        return false;
    }

    public boolean[][] getField() {
        return field;
    }
}

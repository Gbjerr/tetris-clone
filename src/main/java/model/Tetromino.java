package model;

import javafx.scene.paint.Color;

import java.util.Random;

/**
 * Class for representing shapes in the tetris game. Contains logic for rotating the figure and getting the next rotation
 * which can be applied for all shapes
 */
public class Tetromino {
    private final Color[] COLORS = new Color[] {
            Color.rgb(248, 121, 41),
            Color.rgb(11, 165, 223),
            Color.rgb(192, 58, 180),
            Color.rgb(124, 212, 47),
            Color.rgb(215, 23, 53),
            Color.rgb(44, 87, 220),
            Color.rgb(251, 187, 49)};

    // shapes are labeled as enums
    private enum Shape {
        I(new Point[]{new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(3, 1)}),
        L(new Point[]{new Point(2, 0), new Point(0, 1), new Point(1, 1), new Point(2, 1)}),
        J(new Point[]{new Point(0, 1), new Point(2, 1), new Point(1, 1), new Point(0, 0)}),
        O(new Point[]{new Point(0, 0), new Point(1, 0), new Point(0, 1), new Point(1,1)}),
        Z(new Point[]{new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(2,1)}),
        T(new Point[]{new Point(1, 0), new Point(2, 1), new Point(1, 1), new Point(0, 1)}),
        S(new Point[]{new Point(2, 0), new Point(0,1), new Point(1,0), new Point(1, 1)});

        Shape(Point[] coordinates) {
            this.coordinates = coordinates;
        }

        private final Point[] coordinates;

        public Point[] cloneCoordinates() {
            Point[] clone = new Point[4];
            for(int i = 0; i < clone.length; i++) {
                clone[i] = (Point) coordinates[i].clone();
            }
            return clone;
        }
    }

    private Point pivot;
    private Color color;
    private Point[] coordinates;

    // constructor
    public Tetromino() {
        randomize();
    }

    /**
     * Initializes shape and color randomly for a Tetromino piece.
     */
    private void randomize() {
        Random random = new Random(System.currentTimeMillis());
        Shape[] shapes = Shape.values();
        int selector = random.nextInt(shapes.length);
        Shape shape = shapes[selector];

        this.color = COLORS[random.nextInt(COLORS.length)];
        this.coordinates = shapes[selector].cloneCoordinates();
        this.pivot = shape == Shape.O ? null : coordinates[2];
    }

    /**
     * Rotates current shape 90 degrees counter-clockwise around the pivot point
     */
    public void rotate() {
        if(pivot == null) return;

        coordinates = getNextRotation();
    }

    /**
     * returns the coordinates of the shape
     * @return coordinates
     */
    public Point[] getCoordinates() {
        return coordinates;
    }

    /**
     * returns the color of the shape
     * @return color
     */
    public Color getColor() {
        return color;
    }


    public void decrement(int x, int y) {

        for(int i = 0; i < 4; i++) {
            coordinates[i].setPosition(coordinates[i].x - x, coordinates[i].y - y);
        }
    }

    public void moveX(int x) {

        for(int i = 0; i < 4; i++) {
            coordinates[i].x += x;
        }
    }

    public void moveY(int y) {

        for(int i = 0; i < 4; i++) {
            coordinates[i].y += y;
        }
    }

    /**
     * Rotates current shape 90 degrees counter-clockwise around the pivot point. We do this by using relative vector derived from
     * difference between the pivot point and a current tile. A transformed vector is then derived by the dot product of the rotation matrix
     * and the relative vector.<br/>
     * rot. matrix:        |cos(θ) -sin(θ)|  =>  θ = 90  =>  |0  -1|
     *                     |sin(θ)  cos(θ)|                  |1   0|
     * We can use the transformed vector to get the new position of the rotated coordinate.
     * Reference: <a href="https://www.youtube.com/watch?v=Atlr5vvdchY">link</a>
     */
    public Point[] getNextRotation() {
        if(pivot == null) return null;
        Point[] temp = new Point[coordinates.length];
        int[][] rotMatrix = {
                {0, -1},
                {1, 0}
        };

        int[][] relativeVector = new int[2][1];
        int[][] transformedVector = new int[2][1];
        for (int i = 0; i < coordinates.length; i++) {

            Point coordinate = coordinates[i];
            if (coordinate.x == pivot.x && coordinate.y == pivot.y) {
                temp[i] = coordinates[i];
                continue;
            }
            // calculate the relative vector from current tile to pivot tile
            relativeVector[0][0] = coordinate.x - pivot.x;
            relativeVector[1][0] = coordinate.y - pivot.y;

            // calculate the transformed vector
            for (int y = 0; y < transformedVector.length; y++) {
                for (int xRot = 0; xRot < rotMatrix[0].length; xRot++) {
                    transformedVector[y][0] += (rotMatrix[y][xRot] * relativeVector[xRot][0]);
                }
            }
            temp[i] = new Point(pivot.x + transformedVector[0][0], pivot.y + transformedVector[1][0]);
            // reset vectors for re-use
            for (int j = 0; j < 2; j++) {
                relativeVector[j][0] = 0;
                transformedVector[j][0] = 0;
            }
        }
        return temp;
    }
}

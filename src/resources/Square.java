package resources;

import javafx.scene.paint.Color;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class Square implements Shape {
    private ArrayList<Point> coordinates;

    private ArrayList<Point> rotation1;
    private Color color;


    public Square(Color color) {

        rotation1 = new ArrayList<>();
        Collections.addAll(rotation1, new Point(2, 1), new Point(3, 1), new Point(2, 0), new Point(3, 0));

        coordinates = rotation1;
        this.color = color;
    }

    @Override
    public ArrayList<Point> getCoordinates() {
        return coordinates;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void decrement(int x, int y) {
        for(int i = 0; i < 4; i++) {
            rotation1.set(i, new Point(rotation1.get(i).x - x, rotation1.get(i).y - y));
        }

    }

    @Override
    public void increment(int x, int y) {
        for(int i = 0; i < 4; i++) {
            rotation1.set(i, new Point(rotation1.get(i).x + x, rotation1.get(i).y + y));
        }
    }


    @Override
    public void rotate() {

    }

    @Override
    public ArrayList<Point> getNextRotation() {

        return rotation1;
    }
}

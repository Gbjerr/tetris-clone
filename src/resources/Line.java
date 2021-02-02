package resources;

import javafx.scene.paint.Color;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class Line implements Shape {
    private ArrayList<Point> coordinates;

    private ArrayList<Point> rotation1;
    private ArrayList<Point> rotation2;

    private Color color;
    private int rotNum;

    public Line(Color color) {

        rotNum = 1;
        rotation1 = new ArrayList<>();
        Collections.addAll(rotation1, new Point(2, 0), new Point(3, 0), new Point(4, 0), new Point(5, 0));

        rotation2 = new ArrayList<>();
        Collections.addAll(rotation2, new Point(4, 3), new Point(4, 2), new Point(4, 1), new Point(4, 0));

        coordinates = rotation2;

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
            rotation2.set(i, new Point(rotation2.get(i).x - x, rotation2.get(i).y - y));

        }
    }

    @Override
    public void increment(int x, int y) {

        for(int i = 0; i < 4; i++) {
            rotation1.set(i, new Point(rotation1.get(i).x + x, rotation1.get(i).y + y));
            rotation2.set(i, new Point(rotation2.get(i).x + x, rotation2.get(i).y + y));

        }
    }

    @Override
    public void rotate() {

        rotNum = (rotNum + 1) % 2;
        switch(rotNum) {
            case 0:
                coordinates = rotation1;
                break;
            case 1:
                coordinates = rotation2;
                break;
            default:
                break;
        }

    }

    @Override
    public ArrayList<Point> getNextRotation() {

        if((rotNum + 1) % 2 == 1) {
            return rotation2;
        }

        return rotation1;
    }
}

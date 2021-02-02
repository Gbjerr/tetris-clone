package resources;

import javafx.scene.paint.Color;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class Pyramid implements Shape {
    private ArrayList<Point> coordinates;

    private ArrayList<Point> rotation1;
    private ArrayList<Point> rotation2;
    private ArrayList<Point> rotation3;
    private ArrayList<Point> rotation4;
    private Color color;

    int rotNum;

    public Pyramid(Color color) {
        rotNum = 0;
        rotation1 = new ArrayList<>();
        Collections.addAll(rotation1, new Point(2, 1), new Point(3, 1), new Point(3, 0), new Point(4, 1));

        rotation2 = new ArrayList<>();
        Collections.addAll(rotation2, new Point(3,2), new Point(3, 1), new Point(3,0), new Point(4, 1));

        rotation3 = new ArrayList<>();
        Collections.addAll(rotation3, new Point(2, 1), new Point(3,2), new Point(3,1), new Point(4,1));

        rotation4 = new ArrayList<>();
        Collections.addAll(rotation4, new Point(3, 2), new Point(3, 1), new Point(3,0), new Point(2,1));

        this.color = color;
        coordinates = rotation1;
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
            rotation3.set(i, new Point(rotation3.get(i).x - x, rotation3.get(i).y - y));
            rotation4.set(i, new Point(rotation4.get(i).x - x, rotation4.get(i).y - y));

        }

    }

    @Override
    public void increment(int x, int y) {
        for(int i = 0; i < 4; i++) {
            rotation1.set(i, new Point(rotation1.get(i).x + x, rotation1.get(i).y + y));
            rotation2.set(i, new Point(rotation2.get(i).x + x, rotation2.get(i).y + y));
            rotation3.set(i, new Point(rotation3.get(i).x + x, rotation3.get(i).y + y));
            rotation4.set(i, new Point(rotation4.get(i).x + x, rotation4.get(i).y + y));

        }
    }

    @Override
    public void rotate() {
        rotNum = (rotNum + 1) % 4;
        switch (rotNum) {
            case 0:
                coordinates = rotation1;
                break;
            case 1:
                coordinates = rotation2;
                break;
            case 2:
                coordinates = rotation3;
                break;
            case 3:
                coordinates = rotation4;
                break;
            default:
                break;
        }
    }

    @Override
    public ArrayList<Point> getNextRotation() {

        switch ((rotNum + 1) % 4) {
            case 0:
                return rotation1;
            case 1:
                return rotation2;
            case 2:
                return rotation3;
            case 3:
                return rotation4;
            default:
                break;

        }

        return null;
    }

}

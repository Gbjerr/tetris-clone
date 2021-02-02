package resources;

import javafx.scene.paint.Color;

import java.awt.Point;
import java.util.ArrayList;

public interface Shape {
    public ArrayList<Point> getCoordinates();
    public Color getColor();
    public void decrement(int x, int y);
    public void increment(int x, int y);
    public void rotate();
    public ArrayList<Point> getNextRotation();
}

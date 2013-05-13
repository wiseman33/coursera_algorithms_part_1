/****************************************************************************
 *  Class Point
 *    for an assignment of week 3 for course Algorithms Part 1 at the coursera.org
 *  
 *  Copyright (c) 2013 Danil Knysh
 ****************************************************************************/

/*************************************************************************
 * Name:
 * Email:
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new bySlope();       // YOUR DEFINITION HERE

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }
    
    private final class bySlope implements Comparator<Point>
    {
        public int compare(Point p1, Point p2)
        {
            double slope1 = slopeTo(p1);
            double slope2 = slopeTo(p2);
            
            if (slope1 < slope2)
                return -1;
            if (slope1 > slope2)
                return 1;
            
            return 0;
        }
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        if (compareTo(that) == 0)
        {
            return Double.NEGATIVE_INFINITY;
        }        
        
        if (y == that.y)//Treat the slope of a horizontal line segment as positive zero [added 7/29]
        {
            return 0.0;
        }
        
        if (x == that.x)//treat the slope of a vertical line segment as positive infinity
        {
            return Double.POSITIVE_INFINITY;
        }                
        
        return (double)(that.y - y) / (that.x - x);
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        if (y < that.y)
            return -1;
        if (y > that.y)
            return 1;
        else if (y == that.y)
        {
            if (x < that.x)
                return -1;
            if (x > that.x)
                return 1;
            if (x == that.x)
                return 0;
        }
        
        return 0;
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }        
    
   // unit test
    public static void main(String[] args) {
        /* YOUR CODE HERE */
    }
}

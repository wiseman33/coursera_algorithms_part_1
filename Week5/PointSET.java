/****************************************************************************
 *  Class PointSET
 *    for an assignment of week 5 for course Algorithms Part 1 at the coursera.org
 *  
 *  Copyright (c) 2013 Danil Knysh
 ****************************************************************************/

public class PointSET {
   public PointSET()                               // construct an empty set of points
   {
       points = new SET<Point2D>();
   }
   
   public boolean isEmpty()                        // is the set empty?
   {
       return points.isEmpty();
   }
   
   public int size()                               // number of points in the set
   {
       return points.size();
   }
   
   public void insert(Point2D p)                   // add the point p to the set (if it is not already in the set)
   {
       if (p == null)
           return;
       
       if (!points.contains(p))
           points.add(p);
   }
   
   public boolean contains(Point2D p)              // does the set contain the point p?
   {
       return points.contains(p);
   }
   
   public void draw()                              // draw all of the points to standard draw
   {
       for (Point2D p : points)
           p.draw();
   }
   
   public Iterable<Point2D> range(RectHV rect)     // all points in the set that are inside the rectangle
   {
       SET<Point2D> inside = new SET<Point2D>();
       for (Point2D p : points)
           if (rect.contains(p))
               inside.add(p);
       return inside;
   }
   
   public Point2D nearest(Point2D p)               // a nearest neighbor in the set to p; null if set is empty
   {
       double min = Double.MAX_VALUE;
       Point2D point = null;
       for (Point2D pnt : points)
       {
           double dist = pnt.distanceTo(p);
           if (min > dist)               
           {
               min = dist;
               point = pnt;
           }
       }
       return point;
   }
   
   private SET<Point2D> points;
}
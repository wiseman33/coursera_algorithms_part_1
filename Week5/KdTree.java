/****************************************************************************
 *  Class KdTree
 *    for an assignment of week 5 for course Algorithms Part 1 at the coursera.org
 *  
 *  Copyright (c) 2013 Danil Knysh
 ****************************************************************************/

public class KdTree {
    private class Node
    {
        public Node(Point2D point, Node left, Node right, boolean isHorizontal, RectHV rect)
        {
            key = point;
            left_ = left;
            right_ = right;
            isHorizontal_ = isHorizontal;
            nElement_ = 1;
            rect_ = rect;
        }
        
        public int getSize() {
            return nElement_;
        }
        
        public void setSize(int size) {
            nElement_ = size;
        }
        
        /**
         * if true - horizontal
         * false - vertical
         */
        public boolean isH() {
            return isHorizontal_;
        }
        
        public boolean isLess(Point2D p)
        {
            if (isHorizontal_)
                return Point2D.Y_ORDER.compare(key, p) < 0;
            else
                return Point2D.X_ORDER.compare(key, p) < 0;
        }
        
        public void setRight(Node right) {
            right_ = right;
        }
        
        public Node getRight() {
            return right_;
        }
                
        public void setLeft(Node left) {
            left_ = left;
        }
        
        public Node getLeft() {
            return left_;
        }
        
        public Point2D getP() {
            return key;
        }
        
        public RectHV getR() {
            return rect_;
        }
        
        private Point2D key;
        private Node left_;
        private Node right_;
        private RectHV rect_;
        private boolean isHorizontal_;
        private int nElement_;
    }
    
    public KdTree()                               // construct an empty set of points
    {
        root = null;
    }
   
    public boolean isEmpty()                        // is the set empty?
    {
        return root == null;
    }
    
    public int size()                               // number of points in the set
    {
        if (root == null)
            return 0;
        return root.getSize();
    }
    
    private Node insertToTree(Point2D p, Node n, boolean orientaion, RectHV r)
    {
        if (n == null) return new Node(p, null, null, !orientaion, r);
        
        if (n.getP().equals(p))
            return n;
        
        if (n.isLess(p)) {
            RectHV rect = null;
            if (n.isH()) {//H
                rect = new RectHV(n.getR().xmin(), n.getP().y(), n.getR().xmax(), n.getR().ymax());
            }
            else {
                rect = new RectHV(n.getP().x(), n.getR().ymin(), n.getR().xmax(), n.getR().ymax());
            }
            n.setRight(insertToTree(p, n.getRight(), n.isH(), rect));
            n.setSize(1 + n.getRight().getSize() + ((n.getLeft() != null) ? n.getLeft().getSize() : 0));
        }
        else {
            RectHV rect = null;
            if (n.isH()) {//H
                rect = new RectHV(n.getR().xmin(), n.getR().ymin(), n.getR().xmax(), n.getP().y());
            }
            else {
                rect = new RectHV(n.getR().xmin(), n.getR().ymin(), n.getP().x(), n.getR().ymax());
            }
            n.setLeft(insertToTree(p, n.getLeft(), n.isH(), rect));
            n.setSize(1 + n.getLeft().getSize() + ((n.getRight() != null) ? n.getRight().getSize() : 0));
        }
        return n;
    }
    
    private boolean searchInTree(Point2D p, Node n)
    {
        if (n == null) return false;        
        if (n.getP().equals(p)) return true;
        
        if (n.isLess(p))
            return searchInTree(p, n.getRight());
        else
            return searchInTree(p, n.getLeft());        
    }
    
    public void insert(Point2D p)                   // add the point p to the set (if it is not already in the set)
    {
        root = insertToTree(p, root, true, new RectHV(0,0,1,1));        
    }
    
    public boolean contains(Point2D p)              // does the set contain the point p?
    {
        return searchInTree(p, root);
    }
    
    private void drawPointInTree(Node n) {
        if (n == null) return;
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);
        
        Point2D p = n.getP();
        p.draw();
        
        StdDraw.setPenRadius();
        if (n.isH()) {//H
            StdDraw.setPenColor(StdDraw.BLUE);
            
            p.drawTo(new Point2D(n.getR().xmin(), p.y()));
            p.drawTo(new Point2D(n.getR().xmax(), p.y()));            
        }
        else {
            StdDraw.setPenColor(StdDraw.RED);
            
            p.drawTo(new Point2D(p.x(), n.getR().ymin()));
            p.drawTo(new Point2D(p.x(), n.getR().ymax()));            
        }                
        drawPointInTree(n.getLeft());
        drawPointInTree(n.getRight());
    }
    
    public void draw()                              // draw all of the points to standard draw
    {                
        drawPointInTree(root);
    }
    
    private void detectPoints(RectHV r, Node n, SET<Point2D> set) {
        if (n == null) return;
        
        if (r.intersects(n.getR())) {
            if (r.contains(n.getP()))
                set.add(n.getP());
            detectPoints(r, n.getLeft(), set);
            detectPoints(r, n.getRight(), set);
        }
    }
    
    public Iterable<Point2D> range(RectHV rect)     // all points in the set that are inside the rectangle
    {
        SET<Point2D> set = new SET<Point2D>();
        
        detectPoints(rect, root, set);
        
        return set;
    }
    
    private void getNearest(Node n, Point2D p, Double min) {
        if (n == null) return;
        
        double d = n.getP().distanceTo(p);
        if (min.compareTo(d) > 0) {            
            nearest = n.getP();
            min = d;
            getNearest(n.getLeft(), p, min);
            getNearest(n.getRight(), p, min);
        }        
        else
            getNearest(n.getLeft(), p, min);
    }
    
    public Point2D nearest(Point2D p)               // a nearest neighbor in the set to p; null if set is empty
    {
        Double min = Double.MAX_VALUE;
        nearest = null;
        getNearest(root, p, min);
        return nearest;
    }
       
    private Node root;  
    private Point2D nearest;
}
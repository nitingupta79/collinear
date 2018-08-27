import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Test2 {
    public static void main(String[] args) {
        String length = StdIn.readLine();
        
        Point[] points = new Point[Integer.parseInt(length)];
        int count=0;
        
        while (!StdIn.isEmpty()) {
            points[count++] = new Point(StdIn.readInt(), StdIn.readInt());
        }
        
     // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            if(segment != null)
                segment.draw();
        }
        StdDraw.show();
    }
}

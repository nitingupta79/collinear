import java.util.Arrays;

/*----------------------------------------------------------------
 *  Author:        Nitin Gupta
 *  Written:       08/25/2018
 *  Last updated:  08/26/2018
 *  
 *  Algorithm to find Collinear points using Brute n4 performance
 *
 *----------------------------------------------------------------*/
public class BruteCollinearPoints {
    private LineSegment[] segments = new LineSegment[1];
    private int noOfSegments;

    public BruteCollinearPoints(Point[] points) {
        // finds all line segments containing 4 points
        if (points == null) {
            throw new IllegalArgumentException("Provide points");
        }
        
        for (Point p : points) {
            if (p == null) {
                throw new IllegalArgumentException("Null point");
            }
        }
        
        Arrays.sort(points);

        for (int level1 = 0; level1 < points.length - 3; level1++) {
            if (points[level1] == null) {
                throw new IllegalArgumentException("Invalid point");
            }

            for (int level2 = level1 + 1; level2 < points.length - 2; level2++) {
                double slope1 = validatePoint(points[level2], points[level1]);

                for (int level3 = level2 + 1; level3 < points.length - 1; level3++) {
                    double slope2 = validatePoint(points[level3], points[level1]);
                    
                    if (Double.valueOf(slope1).equals(slope2)) {
                        for (int level4 = level3 + 1; level4 < points.length; level4++) {
                            double slope3 = validatePoint(points[level4], points[level1]);

                            if (Double.valueOf(slope2).equals(slope3)) {
                                if (noOfSegments == segments.length) {
                                    resize(noOfSegments * 2);
                                }

                                segments[noOfSegments++] = new LineSegment(points[level1], points[level4]);
                            }
                        }
                    }
                }
            }
        }

        if (noOfSegments > 0) {
            resize(noOfSegments);
        } else {
            segments = new LineSegment[0];
        }
    }

    public int numberOfSegments() {
        // the number of line segments
        return noOfSegments;
    }

    public LineSegment[] segments() {
        // the line segments
        return Arrays.copyOf(segments, segments.length);
    }
    
    private double validatePoint(Point point1, Point point2) {
        double slope = 0;
        if (point2 != null) {
            slope = point2.slopeTo(point1);
            
            if (slope == Double.NEGATIVE_INFINITY) {
                throw new IllegalArgumentException("Duplicate point");
            }
        }
        return slope;
    }

    /**
     * Resize array in amortized time
     */
    private void resize(int capacity) {
        LineSegment[] temp = new LineSegment[capacity];
        for (int count = 0; count < noOfSegments; count++) {
            temp[count] = segments[count];
        }
        segments = temp;
    }

}

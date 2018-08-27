import java.util.Arrays;

/*----------------------------------------------------------------
 *  Author:        Nitin Gupta
 *  Written:       08/25/2018
 *  Last updated:  08/26/2018
 *  
 *  Algorithm to find Collinear points using MergeSort 
 *
 *----------------------------------------------------------------*/
public class FastCollinearPoints {
    private LineSegment[] segments = new LineSegment[1];
    private int noOfSegments;

    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("Provide points");
        }
        
        
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException("Null point");
            }
        }

        // finds all line segments containing 4 or more points
        Point[] tempPoints = Arrays.copyOf(points, points.length);

        for (int i = 0; i < points.length; i++) {
            Arrays.sort(tempPoints, points[i].slopeOrder());

            Point endPoint = points[i];

            double lastslope = 0;
            int noOfPoints = 0;
            boolean subSegment = false;

            for (int j = 1; j < tempPoints.length; j++) {
                if (tempPoints[j].slopeTo(points[i]) == Double.NEGATIVE_INFINITY) {
                    noOfSegments = 0;
                    segments = new LineSegment[0];
                    throw new IllegalArgumentException("Duplicate point");
                }

                lastslope = tempPoints[j - 1].slopeTo(points[i]);

                if (!Double.valueOf(tempPoints[j].slopeTo(points[i])).equals(lastslope)) {
                    if (noOfPoints >= 3 && !subSegment) {
                        if (noOfSegments == segments.length) {
                            resize(noOfSegments * 2);
                        }

                        segments[noOfSegments++] = new LineSegment(points[i], endPoint);
                    }

                    noOfPoints = 1;
                    if (tempPoints[j].compareTo(points[i]) >= 0) {
                        endPoint = tempPoints[j];
                        subSegment = false;
                    } else {
                        subSegment = true;
                    }
                }

                if (Double.valueOf(tempPoints[j].slopeTo(points[i])).equals(lastslope) && !subSegment) {
                    if (tempPoints[j].compareTo(points[i]) >= 0) {
                        if (tempPoints[j].compareTo(endPoint) >= 0) {
                            endPoint = tempPoints[j];
                        }
                        noOfPoints++;
                    } else {
                        subSegment = true;
                    }
                }
            }
            if (noOfPoints >= 3 && !subSegment) {
                if (noOfSegments == segments.length) {
                    resize(noOfSegments * 2);
                }
                segments[noOfSegments++] = new LineSegment(points[i], endPoint);
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

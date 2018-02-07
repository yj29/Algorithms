package hw24;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Given n different points with coordinates, find the maximum number of
 * pairs of points that can be aligned.
 *
 * @author Yash Jain(yj8359, section-05) and Soni Pandey(sp4547,section-03)
 */
public class AlignPoints {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numberOfPoints = scanner.nextInt();
        Points[] points = new Points[numberOfPoints];
        ArrayList<ArrayList<PointsSlope>> arrayOfSlope = new ArrayList<ArrayList<PointsSlope>>();
        ArrayList<PointsSlope> arrayList = new ArrayList<PointsSlope>();

        //Create all points
        for (int i = 0; i < numberOfPoints; i++) {
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            points[i] = new Points(x, y);
        }

        //For each pair of points, find the slope,midpoint coordinates and Y-intercepts
        for (int i = 0; i < numberOfPoints; i++) {
            for (int j = i + 1; j < numberOfPoints; j++) {
                double slope = findSlope(points[i], points[j]);
                double midXPoint = findXMid(points[i], points[j]);
                double midYPoint = findYMid(points[i], points[j]);
                double yIntercept = findYIntercept(midXPoint, midYPoint, slope);
                PointsSlope p = new PointsSlope(points[i].x, points[i].y, points[j].x, points[j].y, slope, midXPoint, midYPoint, yIntercept);
                arrayList.add(p);
            }
        }

        //sort the slopes and Y-intercepts
        ArrayList<PointsSlope> sorted = mergeSort(arrayList);

        //count the maximum number of lines having same slopes and y-intercepts
        int max = 1;
        int count = 1;
        for (int j = 0; j < sorted.size() - 1; j++) {
            if (sorted.get(j).slope == sorted.get(j + 1).slope && sorted.get(j).yIntercept == sorted.get(j + 1).yIntercept) {
                count++;
            } else {
                count = 1;
            }
            if (count > max) {
                max = count;
            }
        }
        System.out.println(max);

    }



    /**
     * MergeSort implementation
     *
     * @param arrayList     array of PointSlope
     * @return
     */
    private static ArrayList<PointsSlope> mergeSort(ArrayList<PointsSlope> arrayList) {
        int mid = arrayList.size() / 2;
        if (arrayList.size() < 2) {
            return arrayList;
        }
        ArrayList<PointsSlope> firstHalf = new ArrayList<PointsSlope>(arrayList.subList(0, mid));
        ArrayList<PointsSlope> secondHalf = new ArrayList<PointsSlope>(arrayList.subList(mid, arrayList.size()));

        ArrayList<PointsSlope> fH = mergeSort(firstHalf);
        ArrayList<PointsSlope> sH = mergeSort(secondHalf);

        ArrayList<PointsSlope> res = merge(fH, sH);
        return res;
    }



    /**
     * Merge function
     *
     * @param fH    first half
     * @param sH    second half
     * @return
     */
    private static ArrayList<PointsSlope> merge(ArrayList<PointsSlope> fH, ArrayList<PointsSlope> sH) {
        int i = 0, j = 0, k = 0;
        ArrayList<PointsSlope> res = new ArrayList<PointsSlope>();
        while (i < fH.size() && j < sH.size()) {
            //if they have the same slope,sort by y-intercept
            if (fH.get(i).slope == sH.get(j).slope) {
                if (fH.get(i).yIntercept < sH.get(j).yIntercept) {
                    res.add(k++, fH.get(i++));
                } else {
                    res.add(k++, sH.get(j++));
                }
            } else if (fH.get(i).slope < sH.get(j).slope) {
                res.add(k++, fH.get(i++));
            } else {
                res.add(k++, sH.get(j++));
            }
        }

        while (i < fH.size()) {
            res.add(k++, fH.get(i++));
        }
        while (j < sH.size()) {
            res.add(k++, sH.get(j++));
        }
        return res;
    }

    /**
     * Method to find the y-intercept
     *
     * @param midXPoint     x-coordinate of midpoint
     * @param midYPoint     y-coordinate of midpoint
     * @param slope         slope of line
     * @return
     */
    private static double findYIntercept(double midXPoint, double midYPoint, double slope) {
        if (slope == 0) {
            return midXPoint;
        } else {
            double c = midYPoint + ((midXPoint / slope));
            return c;
        }
    }

    /**
     * Method to find the x-coordinate of midpoint of two points
     *
     * @param point     first point
     * @param point1    second point
     * @return
     */
    private static double findYMid(Points point, Points point1) {
        return (point1.y + point.y) / 2;
    }

    /**
     * Method to find the y-coordinate of midpoint of two points
     *
     * @param point     first point
     * @param point1    second point
     * @return
     */
    private static double findXMid(Points point, Points point1) {
        return (point1.x + point.x) / 2;
    }

    /**
     * Method to find the slope
     *
     * @param p1
     * @param p2
     * @return
     */
    public static double findSlope(Points p1, Points p2) {
        double slope = 0;
        try {
            if (p2.x - p1.x == 0) {
                slope = Double.POSITIVE_INFINITY;
            } else {
                slope = (p2.y - p1.y) / (p2.x - p1.x);
            }
        } catch (ArithmeticException e) {
        }
        return slope;
    }

}

/**
 * Points Class : Blueprint of point
 *
 */
class Points {
    double x, y;

    public Points(double x, double y) {
        this.x = x;
        this.y = y;
    }
}

/**
 * Point slope class : Blueprint of details of two points.
 *
 */
class PointsSlope  {
    double x1, y1, x2, y2;
    double slope;
    double midx, midy;
    double yIntercept;

    public PointsSlope(double x1, double y1, double x2, double y2, double slope, double midx, double midy, double yIntercept) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.slope = slope;
        this.midx = midx;
        this.midy = midy;
        this.yIntercept = yIntercept;
    }

}

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class FoldingPlane {

    public static void main(String[] args) {
        ArrayList<Point> points = new ArrayList<Point>();
        /*points.add(new Point(-2, 1));
        points.add(new Point(0 , 2));
        points.add(new Point(0 ,-1));
        points.add(new Point(1 ,-2));
        points.add(new Point(2 , 0));
        points.add(new Point(2 , 1));
        points.add(new Point(1 , 2));
        points.add(new Point(2 , 2));*/
        Scanner scanner = new Scanner(System.in);
        int numberOfPoints = scanner.nextInt();
        //Point[] point = new Point[numberOfPoints];
        for (int i = 0; i < numberOfPoints; i++) {
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            points.add(new Point(x, y));
        }

        int max = maximumNumberOfPairsWhenFolded(points);

        System.out.println("max=" + (max + 1));
    }

    static int maximumNumberOfPairsWhenFolded(ArrayList<Point> points) {
        // 1. Create an array of lines between each point
        ArrayList<Line> lines = constructLinesfromPoints(points);
        // 2. Group lines with the same slope
        HashMap<Double, ArrayList<Line>> mapFromSlopesToLines = constructMapfromSlopesToLines(lines);

        int max = 0;
        // 3. For each line in the group
        for (ArrayList<Line> slopeLines : mapFromSlopesToLines.values()) {
            // 3.1 java -Xmx1024m com.mycompany.MyClassCreate an array of lines between each of the midpoints
            ArrayList<Line> linesFromMidPoints = constructLinesfromMidPoints(slopeLines);
            // 3.2 Find the max number of collinear midpoints
            max = Math.max(max, maxNumberOfCollinearMidPointsInLines(linesFromMidPoints));
        }
        return max;
    }

    static ArrayList<Line> constructLinesfromPoints(ArrayList<Point> points) {
        ArrayList<Line> result = new ArrayList<Line>();
        for (int i = 0; i < points.size(); i++) {
            for (int j = i + 1; j < points.size(); j++) {
                result.add(new Line(points.get(i), points.get(j)));
            }
        }
        return result;
    }

    static ArrayList<Line> constructLinesfromMidPoints(ArrayList<Line> lines) {
        ArrayList<Line> result = new ArrayList<Line>();
        for (int i = 0; i < lines.size(); i++) {
            for (int j = i + 1; j < lines.size(); j++) {
                result.add(new Line(lines.get(i).midPoint, lines.get(j).midPoint));
            }
        }
        return result;
    }

    static HashMap<Double, ArrayList<Line>> constructMapfromSlopesToLines(ArrayList<Line> lines) {
        HashMap<Double, ArrayList<Line>> map = new HashMap<Double, ArrayList<Line>>();
        for (Line l : lines) {
            ArrayList<Line> slopeLines;
            if (map.get(l.slope) == null) {
                slopeLines = new ArrayList<Line>();
            } else {
                slopeLines = map.get(l.slope);
            }
            slopeLines.add(l);
            map.put(l.slope, slopeLines);
        }
        return map;
    }

    static int maxNumberOfCollinearMidPointsInLines(ArrayList<Line> linesFromMidPoints) {
        HashMap<Double, ArrayList<Line>> map = new HashMap<Double, ArrayList<Line>>();
        for (Line l : linesFromMidPoints) {
            ArrayList<Line> slopeLines;
            if (map.get(l.slope) == null) {
                slopeLines = new ArrayList<Line>();
            } else {
                slopeLines = map.get(l.slope);
            }
            slopeLines.add(l);
            map.put(l.slope, slopeLines);
        }
        int max = 0;
        for (ArrayList<Line> lines : map.values()) {
            max = Math.max(max, lines.size());
        }
        return max;
    }

    static class Point {
        double x;
        double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public String toString() {
            return "(" + x + "," + y + ")";
        }
    }

    static class Line {
        double slope;
        Point midPoint;

        public Line(Point p1, Point p2) {
            if (p1.x != p2.x) {
                slope = (p2.y - p1.y) / (p2.x - p1.x);
            } else {
                slope = Double.MAX_VALUE;
            }
            midPoint = new Point((p1.x + p2.x) / 2, (p1.y + p2.y) / 2);
        }

        public String toString() {
            return "(sl=" + slope + ", mp=" + midPoint + ")";
        }
    }
}

package hw63;

import java.util.ArrayList;
import java.util.Scanner;


/**
 * Program to find maximum sum of registered students over all courses
 *
 * @author Yash Jain (yj8359)    ----  Section - 05
 *         Soni Pandey (sp4547)  ----  Section - 03
 */

public class Register {
    static int MAXLIMIT = 3;
    static int numberOfStudents;
    static int numberOfCoures;
    static int count = 0;
    static ArrayList<Course> courses = new ArrayList<Course>();
    static Student[] students;
    static int[][] graph;
    static int numberOfNodes;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String studentsAndCourses = scanner.nextLine();
        String[] studAndCourse = studentsAndCourses.split(" ");
        numberOfStudents = Integer.parseInt(studAndCourse[0]);
        numberOfCoures = Integer.parseInt(studAndCourse[1]);

        //creating the course objects
        for (int i = 0; i < numberOfCoures; i++) {
            Course course = new Course();
            course.courseID = i + 1;
            courses.add(course);
        }

        //creating the students object
        students = new Student[numberOfStudents];
        //scanner = new Scanner(System.in);
        for (int i = 0; i < numberOfStudents; i++) {
            Student student = new Student();
            student.studID = i + 1;

            // String garbage = scanner.next();
            String line = scanner.nextLine();
            String[] split = line.split(" ");
            student.courseIds = new ArrayList<Integer>();

            for (int j = 0; j < split.length; j++) {
                if (!student.courseIds.contains(Integer.parseInt(split[j]))) {
                    student.courseIds.add(Integer.parseInt(split[j]));
                }
            }
            students[i] = student;
        }


        for (int i = 0; i < numberOfCoures; i++) {
            courses.get(i).maxLimit = scanner.nextInt();
        }

        numberOfNodes = numberOfStudents + numberOfCoures + 1;

        createGraph(numberOfNodes);

        computeMaxCoursesRegistered();
        System.out.println(count);
    }

    /**
     * Method for creating the graph from student to course.
     * and also connect the source to student and from course to sink.
     *
     * @param numberOfNodes     number of nodes
     */
    private static void createGraph(int numberOfNodes) {
        graph = new int[numberOfNodes][numberOfNodes];
        for (int i = 0; i < numberOfStudents; i++) {
            ArrayList<Integer> c = students[i].courseIds;
            for (int j = 0; j < c.size(); j++) {
                graph[i][c.get(j) + numberOfStudents - 1] = 1;
            }
        }

        for (int i = 0; i < numberOfCoures; i++) {
            graph[numberOfStudents + i][numberOfNodes - 1] = courses.get(i).maxLimit;
        }
    }

    /**
     * Method for calculating the maximum course registered by student.
     */
    private static void computeMaxCoursesRegistered() {
        for (int i = 0; i < numberOfStudents; i++) {
            compute(i);
        }
    }

    /**
     * Helper function for calculating the maximum course registered.
     * @param i
     */
    private static void compute(int i) {
        for (int j = numberOfStudents; j < numberOfNodes - 1; j++) {
            if (graph[i][j] > 0 && students[i].coursesRegistered < MAXLIMIT) {
                if (graph[j][numberOfNodes - 1] == 0 && graph[numberOfNodes - 1][j] > 0) {
                    if (findStudent(j)) {
                        students[i].coursesRegistered++;
                        graph[i][j] = graph[i][j] - 1;
                        graph[j][i] = graph[j][i] + 1;
                        graph[j][numberOfNodes - 1] = graph[j][numberOfNodes - 1] - 1;
                        graph[numberOfNodes - 1][j] = graph[numberOfNodes - 1][j] + 1;
                        count++;
                    }
                }
                if (graph[j][numberOfNodes - 1] > 0) {
                    count++;
                    students[i].coursesRegistered++;
                    graph[i][j] = graph[i][j] - 1;
                    graph[j][i] = graph[j][i] + 1;
                    graph[j][numberOfNodes - 1] = graph[j][numberOfNodes - 1] - 1;
                    graph[numberOfNodes - 1][j] = graph[numberOfNodes - 1][j] + 1;
                }
            }
        }
    }

    /**
     * Method to find students with backward edges. Check if possible to allocate them different course
     * by using backward edge
     *
     * @param j     student ID
     * @return
     */
    private static boolean findStudent(int j) {
        for (int i = 0; i < numberOfStudents; i++) {
            if (graph[j][i] > 0) {
                ArrayList<Integer> t = students[i].courseIds;
                for (int k = 0; k < t.size(); k++) {
                    if (graph[i][numberOfStudents + t.get(k) - 1] > 0 && graph[numberOfStudents + t.get(k) - 1][numberOfNodes - 1] > 0) {
                        graph[j][i] = graph[j][i] - 1;
                        graph[i][j] = graph[i][j] + 1;
                        graph[j][numberOfNodes - 1] = graph[j][numberOfNodes - 1] + 1;
                        graph[numberOfNodes - 1][j] = graph[numberOfNodes - 1][j] - 1;

                        graph[i][numberOfStudents + t.get(k) - 1] = graph[i][numberOfStudents + t.get(k) - 1] - 1;
                        graph[numberOfStudents + t.get(k) - 1][i] = graph[numberOfStudents + t.get(k) - 1][i] + 1;
                        graph[numberOfStudents + t.get(k) - 1][numberOfNodes - 1] = graph[numberOfStudents + t.get(k) - 1][numberOfNodes - 1] - 1;
                        graph[numberOfNodes - 1][numberOfStudents + t.get(k) - 1] = graph[numberOfNodes - 1][numberOfStudents + t.get(k) - 1] + 1;
                        return true;
                    }
                }
            }
        }
        return false;
    }
}

/**
 * Student class to store the student details.
 */
class Student {
    int studID;
    int coursesRegistered;
    ArrayList<Integer> courseIds;

}

/**
 * Course class to store the course ID and maximum limit of
 * each course
 */
class Course {
    int courseID;
    int maxLimit;
}

package hw0;

import java.util.Scanner;

/**
 * Program to print perfect cube less than or equal to a number
 * @author Yash Jain(yj8359)
 */
public class Cubes {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int number = scanner.nextInt();
        findCubesTillNumber(number);
    }

    /**
     * Method to find the cube until cube is less than number
     *
     * @param number : input parameter
     */
    private static void findCubesTillNumber(int number) {
        int i = 0;
        int cube;
        while (true) {
            cube = i * i * i;
            if (cube > number) {
                return;
            } else {
                System.out.println(cube);
            }
            i++;
        }
    }
}

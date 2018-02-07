package hw0;

import java.util.Scanner;

/**
 * Program to add even numbers, first number to accept is number of numbers whose sum
 * need to be calculated if they are even
 * @author  Yash Jain(yj8359)
 */
public class Evensum {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numberCount = scanner.nextInt();
        int sum = 0;
        for (int i = 0; i < numberCount; i++) {
            int number = scanner.nextInt();
            if (isEven(number)){
                sum += number;
            }
        }
        System.out.println(sum);
    }

    /**
     * Method to determine if a number is even or not
     * @param number : input number
     * @return : returns true if number is even
     */
    private static boolean isEven(int number) {
        if (number % 2 == 0) {
            return true;
        } else {
            return false;
        }
    }
}

package hw13;

import java.util.Scanner;

/**
 * Program to find duplicate value in a sorted array(of length n+1) that contains all
 * of the non-negative integers in the range[0,n], inclusive, as well as
 * one duplicate value.
 *
 * @author Yash Jain(yj8359) and Soni Pandey(sp4547)
 */
public class OneDup {
    static boolean isDupFound = false;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numberOfNumbers = scanner.nextInt();
        int arrayOfNumbers[] = new int[numberOfNumbers + 1];
        int start = 0;
        int end = numberOfNumbers;

        for (int i = 0; i <= numberOfNumbers; i++) {
            arrayOfNumbers[i] = scanner.nextInt();
        }

        findIfArrayHasDuplicate(arrayOfNumbers, start, end);

    }

    /**
     * Method to find if array has any duplicate
     *
     * @param arrayOfNumbers array of numbers
     * @param start          index of start
     * @param end            index of end
     */
    private static void findIfArrayHasDuplicate(int[] arrayOfNumbers, int start, int end) {
        if (!isDupFound) {
            int mid = (end + start) / 2;

            if (arrayOfNumbers[mid] == arrayOfNumbers[mid + 1]) {
                System.out.println(arrayOfNumbers[mid]);
                isDupFound = true;
                return;
            }

            //This is main idea behind whole algorithm
            //In question it says that numbers are in range[0,n] that means it is consecutive
            if (arrayOfNumbers[mid] < mid) {
                findIfArrayHasDuplicate(arrayOfNumbers, start, mid);
            } else {
                findIfArrayHasDuplicate(arrayOfNumbers, mid + 1, end);
            }
        }
    }
}

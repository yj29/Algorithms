package hw25;

import java.util.Scanner;

/**
 * Program to determine whether there exists an integer x in an
 * array of integers more than n/2 times or n/3 times.
 *
 * @author Yash Jain(yj8359,section-05) and Soni Pandey(sp4547,section-03)
 */
public class Majority {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numberOfElements = scanner.nextInt();
        int[] array = new int[numberOfElements];
        int maxValue = 0;

        for (int i = 0; i < numberOfElements; i++) {
            array[i] = scanner.nextInt();
        }

        int range = findRangeOfArray(array);
        int[] countOfNumbers = new int[range + 1];
        findCount(array, countOfNumbers);
        boolean isHalf = findIfItIsGreaterThanHalf(countOfNumbers, numberOfElements,0.5);
        boolean isOneThird = findIfItIsGreaterThanHalf(countOfNumbers, numberOfElements,0.33);

        if (isHalf) {
            System.out.println("YES");
        } else {
            System.out.println("NO");
        }

        if (isOneThird) {
            System.out.println("YES");
        } else {
            System.out.println("NO");
        }
    }

    /**
     * Method to find if a number occurs more than n/2 or n/3 times in an array
     * @param countOfNumbers
     * @param numberOfElements
     * @param fraction
     * @return
     */
    private static boolean findIfItIsGreaterThanHalf(int[] countOfNumbers, int numberOfElements,double fraction) {
        int halfOfNumbers = (int) (numberOfElements * fraction);
        for (int i = 0; i < countOfNumbers.length; i++) {
            if (countOfNumbers[i] > halfOfNumbers) {
                return true;
            }
        }
        return false;
    }

    /**
     * keep count of each elements of input array
     * @param array
     * @param countOfNumbers
     */
    private static void findCount(int[] array, int[] countOfNumbers) {
        for (int i = 0; i < array.length; i++) {
            countOfNumbers[array[i]]++;
        }
    }

    /**
     * Method to find the maximum element of input array which will be
     * the range of count array.
     * @param array
     * @return
     */
    private static int findRangeOfArray(int[] array) {
        int maxValue = array[0];
        for (int i = 0; i < array.length; i++) {
            if (array[i] > maxValue) {
                maxValue = array[i];
            }
        }
        return maxValue;
    }
}

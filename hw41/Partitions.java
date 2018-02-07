package hw41;

import java.util.Scanner;

/**
 * @author Yash Jain (yj8359)    ----  Section - 05
 *         Soni Pandey (sp4547)  ----  Section - 03
 */

public class Partitions {
    static int input[];
    static Scanner scanner = new Scanner(System.in);
    static int odd, even;
    static int result[];
    static boolean captureEvenIndex = true;
    static boolean firstOdd = false;
    static int sum = 0;
    static int evenIndex = 0;
    static int[] input1;

    public static void main(String[] args) {
        int numberOfElements = scanner.nextInt();
        input = new int[numberOfElements];
        input1 = new int[numberOfElements + 1];
        acceptInput(numberOfElements);

        partition1a(numberOfElements);
        //  partition1b(numberOfElements);
        partition1c(numberOfElements);


    }

    private static void partition1c(int numberOfElements) {
        findNumberOfOddSetsc(numberOfElements);
        System.out.println(result[numberOfElements]);
    }


    /**
     * Method to find number of odd sets in O(n) time
     *
     * @param num
     */
    private static void findNumberOfOddSetsc(int num) {
        result = new int[num + 1];
        for (int i = 1; i <= num; i++) {
            if (input1[i] % 2 == 0) {
                result[i] = result[i - 1];
                if (captureEvenIndex && firstOdd) {
                    evenIndex = i;
                    captureEvenIndex = false;
                }
                sum = sum + result[i];
            } else if (input1[i] % 2 != 0 && !firstOdd) {
                firstOdd = true;
                result[i] = 1;
            } else if (input1[i] % 2 != 0 && input1[i - 1] % 2 != 0) {
                result[i] = result[i - 1] + result[i - 2];
            } else if (input1[i] % 2 != 0 && input1[i - 1] % 2 == 0) {
                if (evenIndex >= 2) {
                    result[i] = sum + result[evenIndex - 1] + result[evenIndex - 2];
                    sum = 0;
                    captureEvenIndex = true;
                } else {
                    result[i] = result[i - 1];
                    sum = 0;
                    captureEvenIndex = true;
                }
            }
        }
    }

    private static void partition1b(int numberOfElements) {
        findNumberOfOddSets(numberOfElements);
        System.out.println(result[numberOfElements]);
    }

    private static void partition1a(int numberOfElements) {
        for (int i = 0; i < numberOfElements; i++) {
            if (input[i] % 2 == 0 && odd % 2 == 0) {
                even++;
            } else if (input[i] % 2 != 0) {
                odd++;
                if (odd % 2 == 0) {
                    even++;
                }
            }
        }
        System.out.println((int) Math.pow(2, even - 1));
    }

    /**
     * Method to find number of odd sets O(n^2)
     */
    private static void findNumberOfOddSets(int num) {
        for (int i = 1; i <= num; i++) {
            int sum = 0;
            if (input[i] % 2 == 0) {
                result[i] = result[i - 1];
            } else if (input[i] % 2 != 0 && !firstOdd) {
                firstOdd = true;
                result[i] = 1;
            } else if (input[i] % 2 != 0 && input[i - 1] % 2 != 0) {
                result[i] = result[i - 1] + result[i - 2];
            } else if (input[i] % 2 != 0 && input[i - 1] % 2 == 0) {
                for (int j = i - 1; j > 0; j--) {
                    if (input[j] % 2 != 0) {
                        sum = sum + result[j] + result[j - 1];
                        result[i] = sum;
                        break;
                    }
                    sum = sum + result[j];
                }
            }
        }
    }

    /**
     * Function to take input
     *
     * @param numberOfElements
     */
    private static void acceptInput(int numberOfElements) {
        for (int i = 0; i < numberOfElements; i++) {
            input[i] = scanner.nextInt();
            input1[i + 1] = input[i];
        }
    }
}

package hw42;

import java.util.Scanner;

/**
 * @author Yash Jain (yj8359)    ----  Section - 05
 *         Soni Pandey (sp4547)  ----  Section - 03
 */

public class LargestSum {
    static int S[];
    static int input[];
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int numberOfElements = scanner.nextInt();

        input = new int[numberOfElements];
        S = new int[numberOfElements];

        //Accepting input
        acceptInput(numberOfElements);

        //find the maximum sum of elements in
        //an increasing subsequence
        findMaxSum(numberOfElements);

        int result = finMax();
        System.out.println(result);
    }

    /**
     * Method to find the maximum value in an output arrays
     *
     * @return
     */
    private static int finMax() {
        int max = 0;
        for (int i = 0; i < S.length; i++) {
            if (S[i] > max) {
                max = S[i];
            }
        }
        return max;
    }

    /**
     * Method to find the largest possible sum of elements
     * in an increasing subsequences
     *
     * @param numberOfElements number of elementss
     */
    private static void findMaxSum(int numberOfElements) {
        for (int i = 0; i < numberOfElements; i++) {
            for (int j = 0; j < i; j++) {
                if (input[j] < input[i]) {
                    S[i] = Math.max(S[j] + input[i], S[i]);
                }
            }
        }
    }

    /**
     * Method to accept input
     *
     * @param numberOfElements number of elements
     */
    private static void acceptInput(int numberOfElements) {
        for (int i = 0; i < numberOfElements; i++) {
            input[i] = scanner.nextInt();
            S[i] = input[i];
        }
    }
}


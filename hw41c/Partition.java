package hw41c;

import java.util.Scanner;

/**
 * Created by YASH on 4/4/16.
 */
public class Partition {
    static int input[];
    static Scanner scanner = new Scanner(System.in);
    static int evenIndex = 0;
    static int result[];
    static boolean captureEvenIndex = true;
    static boolean firstOdd = false;
    static int sum = 0;

    public static void main(String[] args) {
        int numberOfElements = scanner.nextInt();
        result = new int[numberOfElements + 1];
        input = new int[numberOfElements + 1];
        acceptInput(numberOfElements);

        findNumberOfOddSets(numberOfElements);
        System.out.println(result[numberOfElements]);
    }

    private static void findNumberOfOddSets(int num) {

        for (int i = 1; i <= num; i++) {

            if (input[i] % 2 == 0) {
                result[i] = result[i - 1];
                if (captureEvenIndex && firstOdd) {
                    evenIndex = i;
                    captureEvenIndex = false;
                }
                sum = sum + result[i];
            } else if (input[i] % 2 != 0 && !firstOdd) {
                firstOdd = true;
                result[i] = 1;
            } else if (input[i] % 2 != 0 && input[i - 1] % 2 != 0) {
                result[i] = result[i - 1] + result[i - 2];
            } else if (input[i] % 2 != 0 && input[i - 1] % 2 == 0) {
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

    private static void acceptInput(int numberOfElements) {
        for (int i = 1; i <= numberOfElements; i++) {
            input[i] = scanner.nextInt();
        }
    }
}

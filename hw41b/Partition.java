package hw41b;

import java.util.Scanner;

/**
 * Created by YASH on 4/4/16.
 */
public class Partition {
    static int input[];
    static Scanner scanner = new Scanner(System.in);
    static int odd, even;
    static int result[];
    static boolean firstOdd = false;

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

    private static void acceptInput(int numberOfElements) {
        for (int i = 1; i <= numberOfElements; i++) {
            input[i] = scanner.nextInt();
        }
    }
}

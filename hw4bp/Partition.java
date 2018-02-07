package hw4bp;

import java.util.Scanner;

/**
 * Created by YASH on 4/4/16.
 */
public class Partition {
    static int input[];
    static Scanner scanner = new Scanner(System.in);
    static int odd, even;
    static int result[][];

    public static void main(String[] args) {
        int numberOfElements = scanner.nextInt();
        result = new int[numberOfElements][numberOfElements];
        input = new int[numberOfElements];
        acceptInput(numberOfElements);

        solution(numberOfElements);
    }

    private static void solution(int n) {
        for (int i = 0; i < n; i++) {
            if (input[i] % 2 != 0) {
                result[i][i] = 1;
            }
        }
    }

    private static void acceptInput(int numberOfElements) {
        for (int i = 0; i < numberOfElements; i++) {
            input[i] = scanner.nextInt();
        }
    }
}

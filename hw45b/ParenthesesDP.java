package hw45b;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * @author Yash Jain (yj8359)    ----  Section - 05
 *         Soni Pandey (sp4547)  ----  Section - 03
 */

public class ParenthesesDP {
    static ArrayList<Integer> numbers = new ArrayList<Integer>();
    static ArrayList<String> operator = new ArrayList<String>();
    static int[][] maxS;
    static int[][] minS;

    public static void main(String[] args) {
        String input = new Scanner(System.in).nextLine();
        String[] array = input.split(" ");
        ArrayList<String> arr = new ArrayList<String>(Arrays.asList(array));

        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i).equals("+") || arr.get(i).equals("-")) {
                operator.add(arr.get(i));
            } else {
                numbers.add(Integer.parseInt(arr.get(i)));
            }
        }

        maxS = new int[numbers.size() + 1][numbers.size() + 1];
        minS = new int[numbers.size() + 1][numbers.size() + 1];
        computeMax(arr);
    }

    /**
     * Method to compute max value with all possible combinations
     * @param arr
     */
    private static void computeMax(ArrayList<String> arr) {
        for (int i = 0; i < maxS[0].length; i++) {
            for (int j = 0; j < maxS[0].length; j++) {
                if (i == j) {
                    maxS[i][j] = 0;
                } else if (i == 0 || j == 0) {
                    maxS[i][j] = 0;
                }
            }
        }

        for (int d = 1; d <= numbers.size() - 1; d++) {
            for (int L = 1; L <= numbers.size() - d; L++) {
                int R = L + d;
                maxS[L][R] = 0;
                int tmp = -9999;
                int mintmp = 9999;
                for (int k = L; k <= R - 1; k++) {
                    int tmp1;
                    int tmp2;
                    int mintmp1 = 0;
                    int mintmp2 = 0;
                    if (k == L) {
                        tmp1 = numbers.get(k - 1);
                        mintmp1 = numbers.get(k - 1);
                    } else {
                        tmp1 = maxS[L][k];
                        mintmp1 = minS[L][k];
                    }
                    if (k + 1 == R) {
                        tmp2 = numbers.get(k);
                        mintmp2 = numbers.get(k);
                    } else {
                        tmp2 = maxS[k + 1][R];
                        mintmp2 = minS[k + 1][R];
                    }

                    int temp1 = compute(tmp1, tmp2, operator.get(k - 1));
                    int temp2 = compute(mintmp1, tmp2, operator.get(k - 1));
                    int temp3 = compute(tmp1, mintmp2, operator.get(k - 1));
                    int temp4 = compute(mintmp1, mintmp2, operator.get(k - 1));
                    tmp = Math.max(tmp, temp1);
                    tmp = Math.max(tmp, temp2);
                    tmp = Math.max(tmp, temp3);
                    tmp = Math.max(tmp, temp4);

                    mintmp = Math.min(mintmp, temp1);
                    mintmp = Math.min(mintmp, temp2);
                    mintmp = Math.min(mintmp, temp3);
                    mintmp = Math.min(mintmp, temp4);
                }
                maxS[L][R] = tmp;
                minS[L][R] = mintmp;
            }
        }
        System.out.println(maxS[1][maxS.length - 1]);
    }

    /**
     * Perform addition or subtraction based on operator provided
     * @param l
     * @param k
     * @param op
     * @return
     */
    private static int compute(int l, int k, String op) {
        if (op.equals("+")) {
            return l + k;
        } else {
            return l - k;
        }
    }
}

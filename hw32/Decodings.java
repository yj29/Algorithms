package hw32;

import java.util.*;

/**
 * For any input encoding of size n bits, give an O(n) algorithm that
 * determines how many different decodings of those n bits are possible
 * using the table /set
 *
 * @author Yash Jain (yj8359)   -----  Section 05
 *         Soni Pandey (sp4547) -----  Section 03
 */
public class Decodings {
    static Set<String> set = new HashSet<String>();
    static int count[];

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        //building set
        buildSet();

        char[] charOfInput = input.toCharArray();

        //initializing count array to 0
        count = new int[input.length() + 1];
        for (int i = 0; i <= input.length(); i++) {
            count[i] = 0;
        }

        //Getting count
        getCountWithDynamicProgramming(charOfInput);

        //Output is last element of count array
        System.out.println(count[count.length - 1]);

    }

    /**
     * Method to count number of possible strings using input set
     * @param charOfInput
     */
    private static void getCountWithDynamicProgramming(char[] charOfInput) {
        for (int i = 0; i < charOfInput.length; i++) {
            if (i == 0) {
                count[i + 1] = 1;
                continue;
            } else if (i == 1) {
                String s = String.valueOf(charOfInput[0]) + String.valueOf(charOfInput[1]);
                if (set.contains(s)) {
                    count[i + 1] = 2;
                } else {
                    count[i + 1] = 1;
                }
                continue;
            } else if (i == 2) {
                count[i + 1] = 1;
                if (i - 1 >= 0 && set.contains(String.valueOf(charOfInput[i - 1]) + String.valueOf(charOfInput[i]))) {
                    count[i + 1] = (count[i + 1] + 1);
                }
                if (i - 2 >= 0 && set.contains(String.valueOf(charOfInput[i - 2]) + String.valueOf(charOfInput[i - 1]))) {
                    count[i + 1] = count[i + 1] + 1;
                }
                if (i - 2 >= 0 && set.contains(String.valueOf(charOfInput[i - 2]) + String.valueOf(charOfInput[i - 1]) + String.valueOf(charOfInput[i]))) {
                    count[i + 1] = count[i + 1] + 1;
                }
                continue;
            }
            count[i + 1] = count[i];
            if (i - 1 >= 0 && set.contains(String.valueOf(charOfInput[i - 1]) + String.valueOf(charOfInput[i]))) {
                count[i + 1] = count[i + 1] + count[i - 1];
            }

            if (i - 2 >= 0 && set.contains(String.valueOf(charOfInput[i - 2]) + String.valueOf(charOfInput[i - 1]) + String.valueOf(charOfInput[i]))) {
                count[i + 1] = count[i + 1] + count[i - 2];
            }
        }
    }

    /**
     * Building set with given values
     */
    private static void buildSet() {
        set.add("0");
        set.add("1");
        set.add("10");
        set.add("01");
        set.add("111");
        set.add("011");
    }
}

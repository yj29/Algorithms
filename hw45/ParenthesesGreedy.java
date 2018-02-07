package hw45;

import java.util.*;

/**
 * @author Yash Jain (yj8359)    ----  Section - 05
 *         Soni Pandey (sp4547)  ----  Section - 03
 */

public class ParenthesesGreedy {
    static Stack<String> operator = new Stack<String>();
    static Stack<Integer> num = new Stack<Integer>();

    public static void main(String[] args) {
        String input = new Scanner(System.in).nextLine();
        String[] array = input.split(" ");
        ArrayList<String> arr = new ArrayList<String>(Arrays.asList(array));

        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i).equals("+")) {
                int num1 = num.pop();
                int num2 = Integer.parseInt(arr.get(i + 1));
                arr.remove(i + 1);
                num.push(num1 + num2);
            } else if (arr.get(i).equals("*")) {
                operator.add(arr.get(i));
            } else {
                num.add(Integer.parseInt(arr.get(i)));
            }
        }

        while (!operator.isEmpty()) {
            int num1 = num.pop();
            int num2 = num.pop();
            num.push(num1 * num2);
            operator.pop();
        }
        System.out.println(num.peek());
    }
}

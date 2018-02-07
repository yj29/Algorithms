package hw35;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Yash Jain (yj8359)   -----  Section 05
 *         Soni Pandey (sp4547) -----  Section 03
 */
public class DoubleKnapsack {
    static ArrayList<Item> arrayList = new ArrayList<Item>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numberOfItems = scanner.nextInt();
        int maxWeightForFirstBag = scanner.nextInt();
        int maxWeightForSecondBag = scanner.nextInt();


        for (int i = 0; i < numberOfItems; i++) {
            int weight = scanner.nextInt();
            int value = scanner.nextInt();
            arrayList.add(new Item(value, weight));
        }

        createTable(arrayList.size(), maxWeightForFirstBag, maxWeightForSecondBag);

    }

    /**
     * Method that creates output array using concept of Dynamic Programming
     *
     * @param numberOfItems         number of items
     * @param maxWeightForFirstBag  maximum weight bag1 can be filled with
     * @param maxWeightForSecondBag maximum weight bag2 can be filled with
     */
    private static void createTable(int numberOfItems, int maxWeightForFirstBag, int maxWeightForSecondBag) {
        int[][][] output = new int[numberOfItems + 1][maxWeightForFirstBag + 1][maxWeightForSecondBag + 1];

        //initialized
        for (int i = 0; i < numberOfItems + 1; i++) {
            for (int j = 0; j < maxWeightForFirstBag + 1; j++) {
                for (int k = 0; k < maxWeightForSecondBag + 1; k++) {
                    output[i][j][k] = 0;
                }
            }
        }

        for (int i = 1; i < numberOfItems + 1; i++) {
            for (int j = 0; j < maxWeightForFirstBag + 1; j++) {
                for (int k = 0; k < maxWeightForSecondBag + 1; k++) {
                    //nothing selected
                    int temp1 = output[i - 1][j][k];

                    //if selected in first knapsack
                    int tempw = arrayList.get(i - 1).weight;
                    int temp2 = 0;
                    if (tempw <= j) {
                        temp2 = arrayList.get(i - 1).value + output[i - 1][j - tempw][k];
                    }

                    //if selected in second knapsack
                    int temp3 = 0;
                    if (tempw <= k) {
                        temp3 = arrayList.get(i - 1).value + output[i - 1][j][k - tempw];
                    }
                    output[i][j][k] = Math.max(temp1, Math.max(temp2, temp3));
                }
            }
        }

        //Output
        System.out.println(output[numberOfItems][maxWeightForFirstBag][maxWeightForSecondBag]);
    }
}

/**
 * Item is blueprint of every item we are going to fill in bag
 */
class Item {
    int value;
    int weight;

    public Item(int value, int weight) {
        this.value = value;
        this.weight = weight;
    }
}

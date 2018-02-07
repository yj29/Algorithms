package hw34;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @author Yash Jain (yj8359)    ----  Section - 05
 *         Soni Pandey (sp4547)  ----  Section - 03
 */
public class MinimumGap {
    static double S, F;
    static Slots[] arrayOfSlots;
    static double[] rho;
    static double[] output;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numberOfSlots = scanner.nextInt();
        S = scanner.nextDouble();
        F = scanner.nextDouble();

        arrayOfSlots = new Slots[numberOfSlots];
        rho = new double[numberOfSlots];
        output = new double[numberOfSlots + 1];

        for (int i = 0; i < numberOfSlots; i++) {
            double start = scanner.nextDouble();
            double end = scanner.nextDouble();
            arrayOfSlots[i] = new Slots(start, end);
            rho[i] = 0;
            output[i] = 0;
        }

        arrayOfSlots = mergeSort(arrayOfSlots);

        findRho();

        findMaxDistribution();

        System.out.println((int) ((F - S) - output[output.length - 1]));
    }

    /**
     * Method to calculate and fill output array using Dynamic Programming
     */
    private static void findMaxDistribution() {
        for (int i = 0; i <= rho.length; i++) {
            if (i == 0) {
                output[i] = 0;
                continue;
            }
            output[i] = Math.max(output[i - 1], arrayOfSlots[i - 1].value + output[(int) rho[i - 1]]);
        }
    }

    /**
     *
     */
    private static void findRho() {
        for (int i = 0; i < rho.length; i++) {
            int j = i - 1;
            while (j >= 0) {
                if (arrayOfSlots[i].start >= arrayOfSlots[j].end) {
                    rho[i] = j + 1;
                    break;
                }
                j--;
            }
        }
    }

    /**
     * Merger sort to sort all jobs based on their finish time
     *
     * @param arrayOfSlots
     * @return
     */
    private static Slots[] mergeSort(Slots[] arrayOfSlots) {

        if (arrayOfSlots.length < 2) {
            return arrayOfSlots;
        }
        int mid = arrayOfSlots.length / 2;

        Slots[] firstHalf = Arrays.copyOfRange(arrayOfSlots, 0, mid);
        Slots[] secondHalf = Arrays.copyOfRange(arrayOfSlots, mid, arrayOfSlots.length);
        Slots[] fH = mergeSort(firstHalf);
        Slots[] sH = mergeSort(secondHalf);

        Slots[] res = merge(fH, sH);

        return res;
    }

    /**
     * Part of merge sort
     *
     * @param firstHalf
     * @param secondHalf
     * @return merged partitions sorted
     */
    private static Slots[] merge(Slots[] firstHalf, Slots[] secondHalf) {
        int i = 0, j = 0, k = 0;
        Slots[] res = new Slots[firstHalf.length + secondHalf.length];
        while (i < firstHalf.length && j < secondHalf.length) {
            if (firstHalf[i].end < secondHalf[j].end) {
                res[k++] = firstHalf[i++];
            } else {
                res[k++] = secondHalf[j++];
            }

        }

        while (i < firstHalf.length) {
            res[k++] = firstHalf[i++];
        }
        while (j < secondHalf.length) {
            res[k++] = secondHalf[j++];
        }
        return res;
    }
}

/**
 * Class slots is bluprintof every single job's details
 */
class Slots {
    double start;
    double end;
    double value;

    public Slots(double start, double end) {
        this.start = start;
        this.end = end;
        this.value = end - start;
    }
}

package hw13;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 * Program to implement Merge Sort, Insertion Sort and Bucket Sort
 *
 * @author Yash Jain(yj8359) and Soni Pandey(sp4547)
 */

@SuppressWarnings("unchecked")
public class SortingTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //int numberOfNumbers = scanner.nextInt();
        double mu = 0.5;
        double sigma = 0.0001;

        int numberOfNumbers = 1;
        for (int i = 0; i < 6; i++) {
            numberOfNumbers = numberOfNumbers * 10;
            System.out.println("NUMBER OF ELEMENTS: " + numberOfNumbers);
            double arr[] = getArray(mu, sigma, numberOfNumbers);
            double arr1[] = getArray(numberOfNumbers);


            //Merge Sort
            long startTime = System.currentTimeMillis();
            mergeSort(arr1);
            long endTime = System.currentTimeMillis();
            System.out.println("Time taken by algorithm(merge sort) to sort(Without Gaussian) " + numberOfNumbers + " elements is: " + (endTime - startTime) + " milliseconds");


            long startTime1 = System.currentTimeMillis();
            mergeSort(arr);
            long endTime1 = System.currentTimeMillis();
            System.out.println("Time taken by algorithm(merge sort) to sort(Gaussian) " + numberOfNumbers + " elements is: " + (endTime1 - startTime1) + " milliseconds");

            //Insertion Sort
            insertionSort(arr1, "Without Gaussian");
            insertionSort(arr, "Gaussian");

            //Bucket Sort
            bucketSort(numberOfNumbers, arr1, "Without Gaussian");
            bucketSort(numberOfNumbers, arr, "Gaussian");

            System.out.println("----------------------------------------------------------------------");
            System.out.println("");
        }
    }

    /**
     * Method to generate random floating point numbers
     *
     * @param numberOfNumbers
     * @return
     */
    private static double[] getArray(int numberOfNumbers) {
        double[] array = new double[numberOfNumbers];
        Random random = new Random();
        for (int i = 0; i < numberOfNumbers; i++) {
            array[i] = random.nextFloat();
        }
        return array;
    }

    /**
     * Method that generates array with uniform distribution ( Gaussian distribution )
     *
     * @param mu
     * @param sigma
     * @param sizeOfArray
     * @return
     */
    private static double[] getArray(double mu, double sigma, int sizeOfArray) {
        double numberArray[] = new double[sizeOfArray];
        Random random = new Random();
        for (int i = 0; i < sizeOfArray; i++) {
            double val = random.nextGaussian() * sigma + mu;
            numberArray[i] = val;
        }
        return numberArray;
    }


    /**
     * Implementation of Bucket Sort
     *
     * @param numberOfNumbers
     * @param arr
     */
    private static void bucketSort(int numberOfNumbers, double[] arr, String method) {
        Node[] nodeArray = new Node[numberOfNumbers];
        int length = arr.length;

        //initialize all arraylists
        for (int i = 0; i < length; i++) {
            nodeArray[i] = new Node<Double>();
        }

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < length; i++) {
            int temp = (int) Math.floor(length * arr[i]);
            Node<Double> newNode = new Node<Double>();
            newNode.setData(arr[i]);
            Node<Double> head = nodeArray[temp];
            Node<Double> current = head;
            if (head.data == null && head.next == null) {
                head.setData(newNode.data);
            } else {
                while (current.next != null) {
                    current = current.next;
                }
                current.next = newNode;
            }
        }

        for (int i = 0; i < length; i++) {
            insertionSortForBucket(nodeArray[i]);
        }
        long endTime = System.currentTimeMillis();

        /*//Printing sorted output
        for (int i = 0; i < length; i++) {
            Node<Double> head = nodeArray[i];
            while (head != null) {
                if (head.data != null) {
                    System.out.println(head.data);
                }
                head = head.next;
            }
        }*/

        System.out.println("Time taken by algorithm(bucket sort) to sort(" + method + ") " + numberOfNumbers + " elements is: " + (endTime - startTime) + " milliseconds");

    }

    /**
     * Insertion sort to sort nodes in bucket sort
     *
     * @param head
     */
    private static void insertionSortForBucket(Node<Double> head) {
        //No elements present in nodelist to sort
        if (head.next == null) {
            return;
        }

        Node<Double> nextToHead = head.next;
        while (nextToHead != null) {
            Node<Double> pointer1 = head;
            double currentValue = nextToHead.data;
            if (head.next == nextToHead) {
                if (head.data > nextToHead.data) {
                    double temp = head.data;
                    head.data = nextToHead.data;
                    nextToHead.data = temp;
                }
            } else {
                while (pointer1 != nextToHead && pointer1.data < currentValue) {
                    pointer1 = pointer1.next;
                }
                double temp = pointer1.data;
                pointer1.data = nextToHead.data;
                nextToHead.data = temp;
            }
            nextToHead = nextToHead.next;
        }

    }

    /**
     * Implementation of insertion sort algorithm
     *
     * @param arr
     */
    public static void insertionSort(double[] arr, String method) {
        int len = arr.length;
        double[] res;
        res = Arrays.copyOf(arr, len);

        long startTime = System.currentTimeMillis();

        for (int i = 1; i < len; i++) {
            double item = res[i];
            int pos = i;
            while ((pos > 0) && (res[pos - 1] > item)) {
                res[pos] = res[pos - 1];
                pos--;
            }
            res[pos] = item;
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Time taken by algorithm(insertion sort) to sort(" + method + ") " + len + " elements is: " + (endTime - startTime) + " milliseconds");

    }

    /**
     * Implementation of merge sort algorithm
     *
     * @param arr
     * @return
     */
    public static double[] mergeSort(double[] arr) {
        int length = arr.length;
        double[] tempArray = Arrays.copyOf(arr, arr.length);

        if (length <= 1) {
            return tempArray;
        }
        int mid = length / 2;
        double[] first_half;
        double[] second_half;

        first_half = Arrays.copyOfRange(tempArray, 0, mid);
        second_half = Arrays.copyOfRange(tempArray, (mid), (tempArray.length));
        mergeSort(first_half);
        mergeSort(second_half);

        merge(first_half, second_half, tempArray);
        return tempArray;
    }

    /**
     * Merege elements ( used for merging in merge sort )
     *
     * @param first_half
     * @param second_half
     * @param res
     */
    public static void merge(double[] first_half, double[] second_half, double[] res) {
        int i = 0, j = 0, k = 0;
        while (i < first_half.length && j < second_half.length) {
            if (first_half[i] < second_half[j]) {
                res[k++] = first_half[i++];
            } else {
                res[k++] = second_half[j++];
            }

        }

        while (i < first_half.length) {
            res[k++] = first_half[i++];
        }
        while (j < second_half.length) {
            res[k++] = second_half[j++];
        }
    }

}

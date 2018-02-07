package hw23;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Given the age and height of students(7 and 8 years old) and a teacher in a random order,
 * arrange them from left to right, first all of the 7 years old in increasing order,
 * teacher should be in the middle and then all of the 8 years old in decreasing order of height.
 * program to find the minimum number of swaps necessary to get the class into the desired
 * order.
 *
 * @author Yash Jain(yj8359,section-05) and Soni Pandey(sp4547,section-03)
 */
public class Picture {
    static int count = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numberOfPersons = scanner.nextInt();
        ArrayList<Person> personsArrayList = new ArrayList<Person>();
        for (int i = 0; i < numberOfPersons; i++) {
            int age = scanner.nextInt();
            double height = scanner.nextDouble();
            int priority = 0;

            if (age == 7) {
                priority = 1;
            } else if (age == 44) {
                priority = 2;
            } else {
                priority = 3;
            }
            personsArrayList.add(new Person(age, height, priority));
        }

        ArrayList<Person> finalRes = mergeSort(personsArrayList);
        System.out.println(count);

    }

    /**
     * MergeSort implementation. Sort the person list first by
     * age then by height.
     *
     * @param personsArrayList  person list to be sorted
     * @return
     */
    private static ArrayList<Person> mergeSort(ArrayList<Person> personsArrayList) {
        int mid = personsArrayList.size() / 2;
        if (personsArrayList.size() < 2) {
            return personsArrayList;
        }
        ArrayList<Person> firstHalf = new ArrayList<Person>(personsArrayList.subList(0, mid));
        ArrayList<Person> secondHalf = new ArrayList<Person>(personsArrayList.subList(mid, personsArrayList.size()));

        ArrayList<Person> fH = mergeSort(firstHalf);
        ArrayList<Person> sH = mergeSort(secondHalf);

        ArrayList<Person> res = merge(fH, sH);
        return res;
    }

    /**
     * Combine the two sorted list.
     *
     * @param fH
     * @param sH
     * @return
     */
    private static ArrayList<Person> merge(ArrayList<Person> fH, ArrayList<Person> sH) {
        int j = 0, k = 0;
        ArrayList<Person> res = new ArrayList<Person>();
        for (int i = 0; i < sH.size(); i++) {
            int loc = findLocation(fH, sH.get(i));
            count = count + (fH.size() - loc);
            Person p = sH.get(i);
            fH.add(loc, p);
        }

        return fH;
    }

    /**
     * Find the correct location of a given person in the list
     *
     * @param res
     * @param sH
     * @return
     */
    private static int findLocation(ArrayList<Person> res, Person sH) {
        for (int i = 0; i < res.size(); i++) {
            if (res.get(i).priority > sH.priority) {
                return i;
            } else if (res.get(i).priority == sH.priority && res.get(i).age == 7) {
                int j = 0;
                for (j = i; j < res.size(); j++) {
                    if (res.get(j).priority != sH.priority) {
                        return j;
                    }

                    if (res.get(j).height > sH.height) {
                        return j;
                    }

                }
                return j;
            } else if (res.get(i).priority == sH.priority && res.get(i).age == 8) {
                int j = 0;
                for (j = i; j < res.size(); j++) {
                    if (res.get(j).priority != sH.priority) {
                        return j;
                    }
                    if (res.get(j).height < sH.height) {
                        return j;
                    }
                }
                return j;
            } else {
                if (sH.priority == 2) {
                    int j = 0;
                    for (j = 0; j < res.size(); j++) {
                        if (res.get(j).priority > sH.priority) {
                            return j;
                        }
                    }
                    return j;
                } else {
                    int j = 0;
                    for (j = 0; j < res.size(); j++) {
                        if (sH.priority < res.get(j).priority) {
                            return j;
                        }
                        if (sH.priority == res.get(j).priority) {
                            return j + findLocation(new ArrayList<Person>(res.subList(j, res.size())), sH);
                        }
                    }
                    return j;
                }

            }

        }
        return 0;
    }

}


/**
 * Person class
 */
class Person {
    int age;
    double height;
    int priority;

    public Person(int age, double height, int priority) {
        this.age = age;
        this.height = height;
        this.priority = priority;
    }
}
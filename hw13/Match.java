package hw13;

import java.util.Scanner;
import java.util.Stack;

/**
 * Given two disjoint sets, along with preference lists for each
 * element of each set. Program to determine if there us more than one
 * stable matching for the given input.
 *
 * @author Yash Jain(yj8359) and Soni Pandey(sp4547)
 */
public class Match {

    public static void main(String[] args) {
        int numberOfElements = 0;
        Scanner scanner = new Scanner(System.in);
        numberOfElements = scanner.nextInt();
        Stack<Integer> stackOfMen = new Stack<Integer>();
        Stack<Integer> stackOfWomen = new Stack<Integer>();
        String temp = new String();
        temp.hashCode();
        int[][] preferenceGroup1 = new int[numberOfElements][numberOfElements];
        int[][] preferenceGroup2 = new int[numberOfElements][numberOfElements];

        //Accepting preference of group 1
        for (int i = 0; i < numberOfElements; i++) {
            for (int j = 0; j < numberOfElements; j++) {
                preferenceGroup1[i][j] = scanner.nextInt();
            }
        }

        //Accepting preference of group 2
        for (int i = 0; i < numberOfElements; i++) {
            for (int j = 0; j < numberOfElements; j++) {
                preferenceGroup2[i][j] = scanner.nextInt();
            }
        }

        //Pushing all men in order in stack
        pushInStack(numberOfElements, stackOfMen);

        //Pushing all women in order in stack
        pushInStack(numberOfElements, stackOfWomen);

        //Finding match-pair considering group-1 proposing to elements in group-2 as per preference
        int[] pair1 = stableMatching(numberOfElements, stackOfMen, preferenceGroup1, preferenceGroup2);

        //Finding match-pair considering group-2 proposing to elements in group-1 as per preference
        int[] pair2 = stableMatching(numberOfElements, stackOfWomen, preferenceGroup2, preferenceGroup1);

        boolean areMultipleCombinations = checkIfMultipleMatch(numberOfElements, pair1, pair2);
        if (areMultipleCombinations) {
            System.out.println("YES");
        } else {
            System.out.println("NO");
        }
    }

    /**
     * Method to check if both combinations are same
     *
     * @param numberOfElements
     * @param pair1
     * @param pair2
     * @return
     */
    private static boolean checkIfMultipleMatch(int numberOfElements, int[] pair1, int[] pair2) {
        for (int i = 0; i < numberOfElements; i++) {
            if (i != pair2[(pair1[i])]) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method to implement stable matching algorithm
     *
     * @param numberOfElements
     * @param stack
     * @param preferenceGroup1
     * @param preferenceGroup2
     * @return
     */
    private static int[] stableMatching(int numberOfElements, Stack<Integer> stack, int[][] preferenceGroup1, int[][] preferenceGroup2) {
        boolean freeElements[] = new boolean[numberOfElements];
        int[] womenEngangedTo = new int[numberOfElements];

        //Initializing boolean array to true to make sure all elements are free initially
        for (int i = 0; i < numberOfElements; i++) {
            freeElements[i] = true;
            womenEngangedTo[i] = -1;
        }

        //loops runs till all elements of a group are matched
        while (!stack.empty()) {
            int element = stack.pop();
            for (int i = 0; i < numberOfElements; i++) {
                if (preferenceGroup1[element][i] != -1) {
                    //If women is free
                    if (freeElements[preferenceGroup1[element][i]]) {
                        freeElements[preferenceGroup1[element][i]] = false;
                        int engaged = preferenceGroup1[element][i];
                        womenEngangedTo[engaged] = element;
                        preferenceGroup1[element][i] = -1;
                        break;
                    } else {
                        int womenEngangedToMen = womenEngangedTo[preferenceGroup1[element][i]];
                        boolean pref = isPreferenceOfCurrentPartnerIsLowerThanCurrentProposal(numberOfElements, preferenceGroup1[element][i], element, womenEngangedToMen, preferenceGroup2);
                        if (pref) {
                            stack.push(womenEngangedToMen);
                            womenEngangedTo[preferenceGroup1[element][i]] = element;
                            break;
                        }
                    }
                } else {
                    continue;
                }
            }

        }
        return womenEngangedTo;
    }

    /**
     * Method that computes if current person proposing has higher preference than current partner
     *
     * @param numberOfElements
     * @param women
     * @param currentMenProposing
     * @param womenAlreadyEngangedToMen
     * @param preference
     * @return
     */
    private static boolean isPreferenceOfCurrentPartnerIsLowerThanCurrentProposal(int numberOfElements, int women, int currentMenProposing, int womenAlreadyEngangedToMen, int[][] preference) {
        int indexOfCurrentMenProposing = 0;
        int indexOfCurrentPartner = 0;
        for (int i = 0; i < numberOfElements; i++) {
            if (preference[women][i] == currentMenProposing) {
                indexOfCurrentMenProposing = i;
            } else if (preference[women][i] == womenAlreadyEngangedToMen) {
                indexOfCurrentPartner = i;
            }
        }
        if (indexOfCurrentPartner > indexOfCurrentMenProposing) {
            return true;
        }
        return false;
    }

    /**
     * Method to push elements in stack
     *
     * @param numberOfElements
     * @param stack
     */
    private static void pushInStack(int numberOfElements, Stack<Integer> stack) {
        for (int i = numberOfElements - 1; i >= 0; i--) {
            stack.push(i);
        }
    }
}

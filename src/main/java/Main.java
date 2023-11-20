import searches.BinarySearch;
import searches.LinearSearch;
import sorts.InsertionSort;
import sorts.MergeSort;

import java.util.Scanner;

public class Main {

    private static Scanner scanner;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        System.out.println("Enter 1 to run sorts, 2 to run searches");
        int choice = scanner.nextInt();
        if (choice == 1)
            runSorts();
        else if (choice == 2)
            runSearches();
        else
            System.out.println("Invalid choice");
    }

    private static void runSorts() {
        int length = 10000;
        int[] unsortedArray = new int[length];
        for (int i = 0; i < unsortedArray.length; i++) {
            unsortedArray[i] = (int) (Math.random() * length);
        }
        runInsertionSort(unsortedArray);
        runMergeSort(unsortedArray);
    }

    private static void runSearches() {

        int length = 1000000;
        int[] sortedArray = new int[length];
        for (int i = 0; i < sortedArray.length; i++) {
            sortedArray[i] = i;
        }
        int randomNumber = (int) (Math.random() * length);

        runLinearSearch(sortedArray, randomNumber);
        runBinarySearch(sortedArray, randomNumber);
        runRecursiveBinarySearch(sortedArray, randomNumber);
    }

    public static void runInsertionSort(int[] array) {
        long start = System.currentTimeMillis();
        InsertionSort.sort(array);
        long end = System.currentTimeMillis();
        System.out.println("Insertion sort took " + (end - start) + " milliseconds");
    }

    public static void runMergeSort(int[] array) {
        long start = System.currentTimeMillis();
        MergeSort.sort(array);
        long end = System.currentTimeMillis();
        System.out.println("Merge sort took " + (end - start) + " milliseconds");
    }


    public static void runLinearSearch(int[] array, int searchValue) {
        long start = System.nanoTime();
        int found = LinearSearch.search(array, searchValue);
        long end = System.nanoTime();
        System.out.println("Linear search took " + (end - start) + " nanoseconds");
        System.out.println("Found at " + found + "/" + (array.length - 1));
    }

    public static void runBinarySearch(int[] array, int searchValue) {
        long start = System.nanoTime();
        int found = BinarySearch.search(array, searchValue);
        long end = System.nanoTime();
        System.out.println("Binary search took " + (end - start) + " nanoseconds");
        System.out.println("Found at " + found + "/" + (array.length - 1));
    }

    public static void runRecursiveBinarySearch(int[] array, int searchValue) {
        long start = System.nanoTime();
        int found = BinarySearch.recursiveSearch(array, 0, array.length - 1, searchValue);
        long end = System.nanoTime();
        System.out.println("Recursive Binary search took " + (end - start) + " nanoseconds");
        System.out.println("Found at " + found + "/" + (array.length - 1));
    }
}

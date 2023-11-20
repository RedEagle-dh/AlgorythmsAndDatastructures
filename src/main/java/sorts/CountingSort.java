package sorts;

public class CountingSort {
    public static void countingSort(int[] array) {
        if (array == null || array.length == 0) {
            return;
        }

        // Schritt 1: Finde das Maximum
        int max = array[0];
        for (int num : array) {
            if (num > max) {
                max = num;
            }
        }

        // Schritt 2: Erstelle die Buckets
        int[] count = new int[max + 1];

        // Schritt 3: Verteile die Elemente in die Buckets
        for (int num : array) {
            count[num]++;
        }

        // Jetzt kannst du die Elemente aus den Buckets zurück ins ursprüngliche Array kopieren.
        int index = 0;
        for (int i = 0; i <= max; i++) {
            while (count[i] > 0) {
                array[index++] = i;
                count[i]--;
            }
        }
    }

    public static void main(String[] args) {
        int[] array = {4, 2, 7, 1, 9, 5, 8};
        countingSort(array);

        // Ausgabe des sortierten Arrays
        for (int num : array) {
            System.out.print(num + " ");
        }
    }
}

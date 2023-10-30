public class BinarySearch {

        public static int search(int[] arr, int x) {
            int l = 0, r = arr.length - 1;
            while (l <= r) {
                int m = (l + r) / 2;
                if (arr[m] == x)
                    return m;
                if (arr[m] < x)
                    l = m + 1;
                else
                    r = m - 1;
            }
            return -1;
        }

        public static int recursiveSearch(int[] arr, int i, int j, int key) {
            if (i > j)
                return -1;
            int m = (i + j) / 2;
            if (arr[m] == key)
                return m;
            if (arr[m] < key)
                return recursiveSearch(arr, m + 1, j, key);
            return recursiveSearch(arr, i, m - 1, key);
        }
}

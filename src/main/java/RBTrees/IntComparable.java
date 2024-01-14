package RBTrees;

public class IntComparable implements Comparable<IntComparable> {
    private int value;

    public IntComparable(int value) {
        this.value = value;
    }

    @Override
    public int compareTo(IntComparable other) {
        return Integer.compare(this.value, other.value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
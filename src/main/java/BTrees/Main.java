package BTrees;

public class Main {
    public static void main(String[] args) {
        BTree t = new BTree(3); // A B-Tree with minium degree 3
        t.insert(10);
        t.insert(20);
        t.insert(5);
        t.insert(6);
        t.insert(12);
        t.insert(30);
        t.insert(7);
        t.insert(17);

        System.out.println("Traversal of the constructed tree is ");
        t.traverse();

        int k = 6;
        if (t.search(k) != null) {
            System.out.println("\nPresent");
        } else {
            System.out.println("\nNot Present");
        }
    }
}
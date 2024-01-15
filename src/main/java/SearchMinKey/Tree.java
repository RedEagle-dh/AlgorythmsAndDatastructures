package SearchMinKey;

import java.util.NoSuchElementException;

public class Tree {

    public static void main(String[] args) {
        Tree t = new Tree();
        t.searchOneHigherKeyThanInputKey(-50, t.root.getLeft(), t.root.getRight());
        t.searchOneHigherKeyThanInputKey(-42, t.root.getLeft(), t.root.getRight());
        t.searchOneHigherKeyThanInputKey(-41, t.root.getLeft(), t.root.getRight());
    }

    Node root;

    Tree() {
        Node leftLeft = new Node(-42, null, null);
        Node leftRight = new Node(2, null, null);

        Node rightRight = new Node(420, null, null);
        Node rightLeft = new Node(27, null, null);

        Node leftRoot = new Node(1, leftLeft, leftRight);
        Node rightRoot = new Node(12, rightLeft, rightRight);
        this.root = new Node(12, leftRoot, rightRoot);

    }

    void searchOneHigherKeyThanInputKey(int key, Node left, Node right) {
        if (left == null && right == null) {
            throw new NoSuchElementException("No higher key found");
        }

        if (left != null && left.getValue() == key) {
            System.out.println("Found exact key: " + left.getValue());
            return;
        }

        if (right != null && right.getValue() == key) {
            System.out.println("Found exact key: " + right.getValue());
            return;
        }

        if (left != null && left.getValue() > key) {
            System.out.println("Found higher key: " + left.getValue());
            return;
        }

        if (right != null && right.getValue() > key) {
            System.out.println("Found higher key: " + right.getValue());
            return;
        }

        if (left != null) {
            searchOneHigherKeyThanInputKey(key, left.getLeft(), left.getRight());
        }

        if (right != null) {
            searchOneHigherKeyThanInputKey(key, right.getLeft(), right.getRight());
        }
    }



    static class Node {
        int value;
        Node left;
        Node right;

        Node(int value, Node left, Node right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }

        void setValue(int value) {
            this.value = value;
        }

        void setLeft(Node left) {
            this.left = left;
        }

        void setRight(Node right) {
            this.right = right;
        }

        Node getLeft() {
            return this.left;
        }

        Node getRight() {
            return this.right;
        }

        int getValue() {
            return this.value;
        }
    }
}

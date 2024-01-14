package RBTrees;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class RBTree<T extends Comparable<T>> {

    static final boolean RED = true;
    static final boolean BLACK = false;
    Node<T> root;

    public void printDOT(String filename) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            bw.write("digraph RBTree {\n");
            bw.write("    node [fontname=\"Arial\"];\n");

            if (root != null) {
                printDOTRecursive(root, bw);
            }

            bw.write("}\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printPreOrder() {
        printPreOrderRecursive(root);
    }

    // Hilfsmethode für die rekursive Pre-Order-Traversierung
    private void printPreOrderRecursive(Node<T> node) {
        if (node == null) {
            return; // Basisfall der Rekursion
        }

        // Knotendaten und Farbe ausgeben
        System.out.println(node.data + " - " + (node.color ? "Rot" : "Schwarz"));

        // Rekursive Aufrufe für linken und rechten Unterbaum
        printPreOrderRecursive(node.left);
        printPreOrderRecursive(node.right);
    }


    private void printDOTRecursive(Node<T> node, BufferedWriter bw) throws IOException {
        if (node == null) {
            return;
        }

        // Darstellung des aktuellen Knotens
        String nodeColor = node.color ? "red" : "black";
        bw.write("    \"" + node.data + "\" [color=" + nodeColor + ", fontcolor=white, style=filled];\n");

        // Linker Kindknoten
        if (node.left != null) {
            bw.write("    \"" + node.data + "\" -> \"" + node.left.data + "\";\n");
            printDOTRecursive(node.left, bw);
        } else {
            // Darstellung eines "nil" Blattknotens für den linken Teilbaum
            bw.write("    \"nilL" + node.data + "\" [label=\"nil\", shape=box, color=black, fontcolor=black, style=filled];\n");
            bw.write(" \"" + node.data + "\" -> \"nilL" + node.data + "\";\n");
        }

        // Rechter Kindknoten
        if (node.right != null) {
            bw.write("    \"" + node.data + "\" -> \"" + node.right.data + "\";\n");
            printDOTRecursive(node.right, bw);
        } else {
            // Darstellung eines "nil" Blattknotens für den rechten Teilbaum
            bw.write("    \"nilR" + node.data + "\" [label=\"nil\", shape=box, color=black, fontcolor=black, style=filled];\n");
            bw.write("    \"" + node.data + "\" -> \"nilR" + node.data + "\";\n");
        }
    }

    private Node<T> getUncle(Node<T> parent) {
        Node<T> grandparent = parent.parent;
        if (grandparent.left == parent) {
            return grandparent.right;
        } else if (grandparent.right == parent) {
            return grandparent.left;
        } else {
            throw new IllegalStateException("Parent is not a child of its grandparent");
        }
    }

    private void fixRedBlackPropertiesAfterInsert(Node<T> node) {
        Node<T> parent = node.parent;

        // Case 1: Parent is null, we've reached the root, the end of the recursion
        if (parent == null) {
            node.color = BLACK;
            return;
        }

        // Parent is black --> nothing to do
        if (parent.color == BLACK) {
            return;
        }

        // From here on, parent is red
        Node<T> grandparent = parent.parent;


        // Get the uncle (may be null/nil, in which case its color is BLACK)
        Node<T> uncle = getUncle(parent);

        // Case 3: Uncle is red -> recolor parent, grandparent and uncle
        if (uncle != null && uncle.color == RED) {
            parent.color = BLACK;
            grandparent.color = RED;
            uncle.color = BLACK;

            // Call recursively for grandparent, which is now red.
            // It might be root or have a red parent, in which case we need to fix more...
            fixRedBlackPropertiesAfterInsert(grandparent);
        }

        // Parent is left child of grandparent
        else if (parent == grandparent.left) {
            // Case 4a: Uncle is black and node is left->right "inner child" of its grandparent
            if (node == parent.right) {
                rotateLeft(parent);

                // Let "parent" point to the new root node of the rotated sub-tree.
                // It will be recolored in the next step, which we're going to fall-through to.
                parent = node;
            }

            // Case 5a: Uncle is black and node is left->left "outer child" of its grandparent
            rotateRight(grandparent);

            // Recolor original parent and grandparent
            parent.color = BLACK;
            grandparent.color = RED;
        }

        // Parent is right child of grandparent
        else {
            // Case 4b: Uncle is black and node is right->left "inner child" of its grandparent
            if (node == parent.left) {
                rotateRight(parent);

                // Let "parent" point to the new root node of the rotated sub-tree.
                // It will be recolored in the next step, which we're going to fall-through to.
                parent = node;
            }

            // Case 5b: Uncle is black and node is right->right "outer child" of its grandparent
            rotateLeft(grandparent);

            // Recolor original parent and grandparent
            parent.color = BLACK;
            grandparent.color = RED;
        }
    }

    public void insertNode(T key) {
        Node<T> node = root;
        Node<T> parent = null;
        // Traverse the tree to the left or right depending on the key
        while (node != null) {
            parent = node;
            int cmp = key.compareTo(node.data);
            if (cmp < 0) {
                node = node.left;
            } else if (cmp > 0) {
                node = node.right;
            } else {
                throw new IllegalArgumentException("BST already contains a node with key " + key);
            }
        }

        // Insert new node
        Node<T> newNode = new Node<T>(key);
        newNode.color = RED;
        if (parent == null) {
            root = newNode;
        } else if (key.compareTo(parent.data) < 0) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }
        newNode.parent = parent;

        fixRedBlackPropertiesAfterInsert(newNode);
    }

    private void replaceParentsChild(Node<T> parent, Node<T> oldChild, Node<T> newChild) {
        if (parent == null) {
            root = newChild;
        } else if (parent.left == oldChild) {
            parent.left = newChild;
        } else if (parent.right == oldChild) {
            parent.right = newChild;
        } else {
            throw new IllegalStateException("Node is not a child of its parent");
        }

        if (newChild != null) {
            newChild.parent = parent;
        }
    }

    private void rotateLeft(Node<T> node) {
        Node<T> parent = node.parent;
        Node<T> rightChild = node.right;

        node.right = rightChild.left;
        if (rightChild.left != null) {
            rightChild.left.parent = node;
        }

        rightChild.left = node;
        node.parent = rightChild;

        replaceParentsChild(parent, node, rightChild);
    }

    private void rotateRight(Node<T> node) {
        Node<T> parent = node.parent;
        Node<T> leftChild = node.left;

        node.left = leftChild.right;
        if (leftChild.right != null) {
            leftChild.right.parent = node;
        }

        leftChild.right = node;
        node.parent = leftChild;

        replaceParentsChild(parent, node, leftChild);
    }


    public static class Node<T extends Comparable<T>> {
        T data;

        Node<T> left, right, parent;

        boolean color;

        public Node(T data) {
            this.data = data;
        }
    }

}

package SparseProject;

public class SparseVector {

    Node head;

    public SparseVector() {
        head = null;
    }

    /**
     * Create Sparse Vector with n dimensions. Data value filled with 0.0.
     * @param length Integer
     */
    public SparseVector(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Length must be greater than zero");
        }
        head = new Node(0.0, 0, null);
        Node current = head;

        for (int i = 1; i < length; i++) {
            Node newNode = new Node(0.0, i, null);
            current.next = newNode;
            current = newNode;
        }
    }

    /**
     * Sets an element at index n.
     * @param index where the element should be set.
     * @param value which value should be set at index.
     */
    public void setElement(int index, double value) {
        boolean foundAndSet = false;
        Node current = head;
        while (current != null) {
            if (current.index == index) {
                current.value = value;
                foundAndSet = true;
                break;
            }
            current = current.next;
        }
        if (!foundAndSet) {
            throw new ArrayIndexOutOfBoundsException("Index " + index + " out of bounds for length " + getLength());
        }
    }

    /**
     * Returns the dimension of the vector.
     * @return Integer
     */
    public int getLength() {
        int length = 0;
        Node current = head;
        while (current != null) {
            length++;
            current = current.next;
        }
        return length;
    }

    /**
     * Method for getting an element on given index.
     * @param index Integer
     * @return the value of the index or 0.0
     */
    public double getElement(int index) {
        Node current = head;
        while (current != null) {
            if (current.index == index) {
                return current.value;
            }
            current = current.next;
        }
        return 0.0;
    }

    /**
     * Removes an element at index n.
     * @param index where an element should be removed.
     */
    public void removeElement(int index) {
        boolean foundAndRemoved = false;
        Node current = head;
        Node last = current;
        while (current != null) {
            if (current.index == index) {
                last.next = current.next;
                foundAndRemoved = true;
                break;
            }
            last = current;
            current = current.next;
        }
        if (!foundAndRemoved) {
            throw new ArrayIndexOutOfBoundsException("Index " + index + " out of bounds for length " + getLength());
        }
    }


    /**
     * Adding another vector to the current.
     * @param other The other vector.
     */
    public void add(SparseVector other) {
        Node otherCurrent = other.head;
        while (otherCurrent != null) {
            // Äquivalenter node zum other index
            Node tmp = getNode(otherCurrent.index);
            if (tmp != null) {
                if (tmp.index == otherCurrent.index) {
                    setElement(otherCurrent.index, tmp.value + otherCurrent.value);
                } else {
                    Node newNode = new Node(otherCurrent.value, otherCurrent.index, tmp.next);
                    tmp.next = newNode;
                }
            }
            otherCurrent = otherCurrent.next;
        }
    }

    /**
     * Checks if 2 vectors are equal.
     * @param other The other vector.
     * @return whether they are equal or not.
     */
    public boolean equals(SparseVector other) {
        if (getLength() != other.getLength()) {
            return false;
        }
        Node otherCurrent = other.head;
        while (otherCurrent != null) {
            Node tmp = getNode(otherCurrent.index);
            if (tmp.index != otherCurrent.index || tmp.value != otherCurrent.value) {
                return false;
            }
            otherCurrent = otherCurrent.next;
        }
        return true;
    }


    /**
     * Private Method to get a Node from index or the last node if the index does not exist.
     * @param index The index to check for a node.
     * @return the node at index n or the node before index n.
     */
    private Node getNode(int index) {
        Node current = head;
        Node last = head;
        while (current != null) {
            if (current.index == index) {
                return current;
            } else if (current.index > index) {
                return last;
            }
            last = current;
            current = current.next;
        }
        return last;
    }



    static class Node {
        double value;
        int index;
        Node next;
        public Node(double value, int index, Node next) {
            this.value = value;
            this.index = index;
            this.next = next;
        }
    }

}

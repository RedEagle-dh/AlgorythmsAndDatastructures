package SparseProject;

public class SparseVector {
    int size = 0;
    int dimensions;
    Node head = new Node(-1, -1);

    /**
     * Constructs an empty SparseVector with zero dimensions.
     * This constructor initializes a SparseVector with no elements, representing a vector with zero dimensions.
     * The SparseVector class is optimized for memory efficiency by only storing non-zero elements.
     *
     * @see SparseVector
     */
    public SparseVector() {
        this.dimensions = 0;
    }

    /**
     * Constructs a SparseVector with the specified dimensions.
     * This constructor initializes a SparseVector with the given dimensions, representing a vector with sparse data.
     * The SparseVector class is optimized for memory efficiency by only storing non-zero elements.
     *
     * @param dimensions The number of dimensions for the SparseVector. Must be greater than 0.
     * @throws IllegalArgumentException If the specified dimensions are less than or equal to zero.
     * @see SparseVector
     */
    public SparseVector(int dimensions) {
        if (dimensions <= 0) {
            throw new IllegalArgumentException("The dimensions must be greater than 0");
        }
        this.dimensions = dimensions;
    }

    /**
     * Retrieves the dimension of the Vector.
     *
     * @return The dimension of the Vector.
     * @see SparseVector
     */
    int getLength() {
        return dimensions;
    }


    /**
     * Sets the element at the specified index in the SparseVector to the given value.
     *
     * @param index The index at which to set the element. Must be greater than 0 and less than or equal to dimensions.
     * @param value The value to set at the specified index.
     * @throws IllegalArgumentException If the index is greater than the dimensions or less than or equal to zero.
     * @see SparseVector
     */
    void setElement(int index, double value) {
        checkArguments(index);

        // erstes Element
        if (size == 0) {
            head.setNext(new Node(index, value));
            size++;
            return;
        }
        Node current = head;
        while (current.getNext() != null) {
            // Das Element hat bereits eine Node
            if (current.getNext().getIndex() == index) {
                // nur der Wert muss angepasst werden
                current.getNext().setValue(value);
                return;
            }
            if (index < current.getNext().getIndex()) {
                // vorher einfügen
                Node newNode = new Node(index, value);
                newNode.setNext(current.getNext());
                current.setNext(newNode);
                size++;
                return;
            }
            if (index > current.getNext().getIndex() && current.getNext().getNext() == null || index < current.getNext().getNext().getIndex()) {
                // nachher einfügen (zwischen das nächste und übernächste oder zum Schluss)
                Node newNode = new Node(index, value);
                newNode.setNext(current.getNext().getNext());
                current.getNext().setNext(newNode);
                size++;
                return;
            }

            current = current.getNext();

        }
    }

    /**
     * Retrieves the element at the specified index in the SparseVector.
     *
     * @param index The index of the element to retrieve. Must be greater than 0 and less than or equal to dimensions.
     * @return The value of the element at the specified index. Returns 0.0 if the index is not found.
     * @throws IllegalArgumentException If the index is greater than the dimensions or less than or equal to zero.
     * @see SparseVector
     */
    double getElement(int index) {
        checkArguments(index);

        Node current = head;
        while (current.getNext() != null) {
            if (current.getNext().getIndex() == index) {
                return current.getNext().getValue();
            }
            current = current.getNext();
        }
        return 0.0;
    }

    /**
     * Removes the element at the specified index from the SparseVector.
     *
     * @param index The index of the element to remove. Must be greater than 0 and less than or equal to dimensions.
     * @throws IllegalArgumentException If the index is greater than the dimensions or less than or equal to zero.
     * @see SparseVector
     */
    void removeElement(int index) {
        checkArguments(index);
        if (size != 0) {
            Node current = head;
            while (current.getNext() != null) {
                if (current.getNext().getIndex() == index) {
                    if (current.getNext().getNext() == null) {
                        current.setNext(null);
                        size--;
                        return;
                    } else {
                        current.setNext(current.getNext().getNext());
                        size--;
                        return;
                    }
                }
                current = current.getNext();
            }
        }
    }

    /**
     * Checks if the SparseVector is equal to another SparseVector.
     *
     * @param other The SparseVector to compare with.
     * @return {@code true} if the SparseVectors are equal, {@code false} otherwise.
     * @see SparseVector
     */
    boolean equals(SparseVector other) {
        Node thisCurrent = this.head;
        Node otherCurrent = other.head;

        if (this.dimensions != other.dimensions || this.size != other.size) {
            return false;
        }
        if (this.dimensions == 0) {
            return true;
        }

        while (thisCurrent.getNext() != null && otherCurrent.getNext() != null) {
            thisCurrent = thisCurrent.getNext();
            otherCurrent = otherCurrent.getNext();
            if (thisCurrent.getIndex() != otherCurrent.getIndex() || thisCurrent.getValue() != otherCurrent.getValue()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Adds the elements of another SparseVector to this SparseVector.
     *
     * @param other The SparseVector to add to this SparseVector.
     * @throws IllegalArgumentException If the dimensions of the vectors are not equal or if both dimensions are not greater than 0.
     * @see SparseVector
     */
    void add(SparseVector other) {
        Node thisCurrent = this.head;
        Node otherCurrent = other.head;

        if (this.dimensions != other.dimensions) {
            throw new IllegalArgumentException("The dimensions of the vectors must be equal");
        }
        if (this.dimensions == 0) {
            throw new IllegalArgumentException("The dimensions of the vectors must be greater than 0");
        }
        if (other.size == 0) {
            //es wird nichts addiert
            return;
        }
        while (otherCurrent.getNext() != null) {

            while (thisCurrent.getNext() != null) {

                if (thisCurrent.getNext().getIndex() == otherCurrent.getNext().getIndex()) {
                    //Node schon vorhanden, aufaddieren
                    thisCurrent.getNext().setValue(thisCurrent.getNext().getValue() + otherCurrent.getNext().getValue());
                    thisCurrent = thisCurrent.getNext();
                    break;

                } else if (otherCurrent.getNext().getIndex() < thisCurrent.getNext().getIndex()) {
                    //vorher einfügen
                    Node newNode = new Node(otherCurrent.getNext().getIndex(), otherCurrent.getNext().getValue());
                    newNode.setNext(thisCurrent.getNext());
                    thisCurrent.setNext(newNode);
                    size++;
                    thisCurrent = thisCurrent.getNext();
                    break;

                } else if (otherCurrent.getNext().getIndex() > thisCurrent.getNext().getIndex() && thisCurrent.getNext().getNext() == null || otherCurrent.getNext().getIndex() < thisCurrent.getNext().getNext().getIndex()) {
                    //nachher
                    Node newNode = new Node(otherCurrent.getNext().getIndex(), otherCurrent.getNext().getValue());
                    newNode.setNext(thisCurrent.getNext().getNext());
                    thisCurrent.getNext().setNext(newNode);
                    size++;
                    thisCurrent = thisCurrent.getNext();
                    break;
                }
                thisCurrent = thisCurrent.getNext();
            }

            otherCurrent = otherCurrent.getNext();
        }


    }


    static class Node {
        double value;
        int index;
        Node next;

        public Node(int index, double value) {
            this.index = index;
            this.value = value;
        }

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }

        public int getIndex() {
            return index;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }

    /**
     * Checks the validity of the index argument for SparseVector operations.
     *
     * @param index The index to be checked.
     * @throws IllegalArgumentException If the index is greater than the dimensions or less than or equal to zero.
     * @see SparseVector
     */
    public void checkArguments(int index) {
        if (this.dimensions < index) {
            throw new IllegalArgumentException("The index must be less than or equal to the dimension");
        }
        if (index <= 0) {
            throw new IllegalArgumentException("The index must be greater than 0");
        }
    }
}


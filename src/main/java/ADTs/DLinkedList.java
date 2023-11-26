package ADTs;

public class DLinkedList {

    Node head;

    public DLinkedList() {
        head = null;
    }

    public DLinkedList(int length) {
        Node newNode = new Node(0, 0, null, null);
        head = newNode;
        for (int i = 1; i < length; i++) {
            newNode.next = new Node(0, i, null, newNode);
            newNode = newNode.next;
        }
    }

    static class Node {
        int value;
        int index;
        Node next;
        Node prev;

        Node(int value, int index, Node next, Node prev) {
            this.value = value;
            this.index = index;
            this.next = next;
            this.prev = prev;
        }
    }

}

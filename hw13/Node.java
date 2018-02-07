package hw13;

/**
 * Node class
 * @author Yash Jain(yj8359) and Soni Pandey(sp4547)
 */
public class Node<E> {
    Node<E> next;
    E data;

    public Node(E dataValue, Node<E> nextValue) {
        this.next = nextValue;
        this.data = dataValue;
    }

    public Node() {
        this.next = null;
        this.data = null;
    }

    public E getData() {
        return data;
    }

    public void setData(E dataValue) {
        data = dataValue;
    }
}

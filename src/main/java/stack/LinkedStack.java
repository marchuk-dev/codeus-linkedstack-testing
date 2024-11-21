package stack;

import java.util.EmptyStackException;
import java.util.Objects;
import java.util.stream.Stream;

public class LinkedStack<T> implements Stack<T> {
    private static class Node<T> {
        T element;
        Node<T> next;

        public static <T> Node<T> valueOf(T element) {
            return new Node<>(element);
        }

        private Node(T element) {
            this.element = element;
        }
    }

    private Node<T> head;
    private int size = 0;

    public static <T> LinkedStack<T> of(T... elements) {
        LinkedStack<T> linkedStack = new LinkedStack<>();
        Stream.of(elements).forEach(linkedStack::push);
        return linkedStack;
    }

    @Override
    public void push(T element) {
        Objects.requireNonNull(element);
        Node<T> newNode = Node.valueOf(element);
        if (head != null) {
            newNode.next = head;
        }
        head = newNode;
        size++;
    }

    @Override
    public T peek() {
        if (head != null) {
            return head.element;
        } else {
            throw new EmptyStackException();
        }
    }

    @Override
    public T pop() {
        if (head != null) {
            size--;
            return retrieveHead();
        } else {
            throw new EmptyStackException();
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    private T retrieveHead() {
        T element = head.element;
        this.head = head.next;
        return element;
    }
}
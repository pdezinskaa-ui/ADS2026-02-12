package by.it.group510901.dezinskaaa.lesson02.lesson11;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class MyHashSet<E> implements Set<E> {

    private static class Node {
        Object value;
        Node next;

        Node(Object value) {
            this.value = value;
        }
    }

    private Node[] buckets;
    private int size;

    public MyHashSet() {
        this.buckets = new Node[16];
        this.size = 0;
    }

    private int indexFor(Object o) {//вычисл индекс корзины для эл
        if (o == null) {
            return 0;
        }
        int hash = o.hashCode();
        return Math.abs(hash) % buckets.length;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = null;
        }
        size = 0;
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }
        String result = "[";
        boolean first = true;
        for (int i = 0; i < buckets.length; i++) {
            Node current = buckets[i];
            while (current != null) {
                if (!first) {
                    result = result + ", ";
                }
                result = result + current.value;
                first = false;
                current = current.next;
            }
        }
        result = result + "]";
        return result;
    }

    @Override
    public boolean add(E element) { //добав эл счит ном ящика потом ид по спис в эт ящик
        int index = indexFor(element);
        Node current = buckets[index];
        while (current != null) { //проверка на дубликат
            boolean match = (element == null) ? (current.value == null) : element.equals(current.value);
            if (match) {
                return false;
            }
            current = current.next;
        }
        Node newNode = new Node(element);
        newNode.next = buckets[index];
        buckets[index] = newNode;
        size++;
        return true;
    }

    @Override
    public boolean contains(Object o) { //провка есть ли эл
        int index = indexFor(o);
        Node current = buckets[index];
        while (current != null) {
            boolean match = (o == null) ? (current.value == null) : o.equals(current.value);
            if (match) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public boolean remove(Object o) { //удалить эл
        int index = indexFor(o);
        Node current = buckets[index];
        Node prev = null;
        while (current != null) {
            boolean match = (o == null) ? (current.value == null) : o.equals(current.value);
            if (match) {
                if (prev == null) { //удал голову
                    buckets[index] = current.next;
                } else {
                    prev.next = current.next;
                }
                size--;
                return true;
            }
            prev = current;
            current = current.next;
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<E> iterator() {
        throw new UnsupportedOperationException();
    }
}
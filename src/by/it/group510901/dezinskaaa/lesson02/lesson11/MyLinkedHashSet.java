package by.it.group510901.dezinskaaa.lesson02.lesson11;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class MyLinkedHashSet<E> implements Set<E> {

    private static class Node {
        Object value;
        Node next;//след в корзине
        Node nextInOrder;//след в цепочке порядка

        Node(Object value) {
            this.value = value;
        }
    }

    private Node[] buckets;
    private Node head;
    private Node tail;
    private int size;

    public MyLinkedHashSet() {
        this.buckets = new Node[16];
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    private int indexFor(Object o) {//индекс корзины
        if (o == null) {
            return 0;
        }
        return Math.abs(o.hashCode()) % buckets.length;
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
        head = null; //обнуляем цепочку порядка
        tail = null;
        size = 0;
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }
        String result = "[";
        Node current = head;//идем по цеп порядка
        boolean first = true;
        while (current != null) {
            if (!first) {
                result = result + ", ";
            }
            result = result + current.value;
            first = false;
            current = current.nextInOrder;
        }
        result = result + "]";
        return result;
    }

    @Override
    public boolean add(E element) { //добавление
        int index = indexFor(element);
        Node current = buckets[index];
        while (current != null) {
            boolean match = (element == null) ? (current.value == null) : element.equals(current.value);
            if (match) {
                return false;
            }
            current = current.next;
        }
        Node newNode = new Node(element);
        newNode.next = buckets[index];
        buckets[index] = newNode;
        if (tail == null) { //если цеп поряд пуста перв эл
            head = newNode;
            tail = newNode;
        } else { //в хвост це пор
            tail.nextInOrder = newNode;
            tail = newNode;
        }
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
    public boolean remove(Object o) { //удаление эл из о
        int index = indexFor(o);
        Node current = buckets[index];
        Node prev = null;
        while (current != null) {
            boolean match = (o == null) ? (current.value == null) : o.equals(current.value);
            if (match) {
                if (prev == null) {
                    buckets[index] = current.next;
                } else {
                    prev.next = current.next;
                }
                removeFromOrder(current);
                size--;
                return true;
            }
            prev = current;
            current = current.next;
        }
        return false;
    }

    private void removeFromOrder(Node target) { //вырез узел из цеп поряд
        if (target == head) {
            head = target.nextInOrder;
        } else {
            Node current = head;
            while (current != null && current.nextInOrder != target) {
                current = current.nextInOrder;
            }
            if (current != null) {
                current.nextInOrder = target.nextInOrder;
            }
        }
        if (target == tail) {
            tail = null;
            if (head != null) {
                Node current = head;
                while (current.nextInOrder != null) {
                    current = current.nextInOrder;
                }
                tail = current;
            }
        }
    }

    @Override
    public boolean containsAll(Collection<?> c) { //все эл с есть в множ
        for (Object o : c) {
            if (!contains(o)) {
                return false; //хоть один не найд
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {//добав все из с
        boolean changed = false;
        for (E element : c) {
            if (add(element)) {
                changed = true; //хоть один добав
            }
        }
        return changed;
    }

    @Override
    public boolean removeAll(Collection<?> c) { //удал все что есть в с
        boolean changed = false;
        Node current = head;
        while (current != null) {//ид по цеп пор
            Node nextNode = current.nextInOrder;
            if (c.contains(current.value)) {
                remove(current.value);
                changed = true;
            }
            current = nextNode;
        }
        return changed;
    }

    @Override
    public boolean retainAll(Collection<?> c) { //остав только кто есть в с
        boolean changed = false;
        Node current = head;
        while (current != null) {
            Node nextNode = current.nextInOrder;
            if (!c.contains(current.value)) {
                remove(current.value);
                changed = true;
            }
            current = nextNode;
        }
        return changed;
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

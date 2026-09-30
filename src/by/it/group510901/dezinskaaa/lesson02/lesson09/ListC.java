package by.it.group510901.dezinskaaa.lesson02.lesson09;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ListC<E> implements List<E> {

    private class Node {
        E value;
        Node next;

        Node(E value) {
            this.value = value;
        }
    }

    private Node head = null;
    private int size = 0;

    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }
        String result = "[";
        Node current = head;
        while (current != null) {
            result = result + current.value;
            if (current.next != null) {
                result = result + ", ";
            }
            current = current.next;
        }
        result = result + "]";
        return result;
    }

    @Override
    public boolean add(E e) {
        Node newNode = new Node(e);
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
        return true;
    }

    @Override
    public E remove(int index) {
        checkIndex(index);
        if (index == 0) {
            E oldValue = head.value;
            head = head.next;
            size--;
            return oldValue;
        }
        Node prev = nodeAt(index - 1);
        Node current = prev.next;
        E oldValue = current.value;
        prev.next = current.next;
        current.next = null;
        current.value = null;
        size--;
        return oldValue;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void add(int index, E element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("index: " + index + ", size: " + size);
        }
        if (index == 0) {
            Node newNode = new Node(element);
            newNode.next = head;
            head = newNode;
            size++;
            return;
        }
        Node prev = nodeAt(index - 1);
        Node newNode = new Node(element);
        newNode.next = prev.next;
        prev.next = newNode;
        size++;
    }

    @Override
    public boolean remove(Object o) {
        if (head == null) {
            return false;
        }
        if (o == null ? head.value == null : o.equals(head.value)) {
            head = head.next;
            size--;
            return true;
        }
        Node current = head;
        while (current.next != null) {
            if (o == null ? current.next.value == null : o.equals(current.next.value)) {
                Node toRemove = current.next;
                current.next = toRemove.next;
                toRemove.next = null;
                toRemove.value = null;
                size--;
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public E set(int index, E element) {
        checkIndex(index);
        Node current = nodeAt(index);
        E oldValue = current.value;
        current.value = element;
        return oldValue;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        Node current = head;
        while (current != null) {
            Node next = current.next;
            current.next = null;
            current.value = null;
            current = next;
        }
        head = null;
        size = 0;
    }

    @Override
    public int indexOf(Object o) {
        int index = 0;
        Node current = head;
        while (current != null) {
            if (o == null ? current.value == null : o.equals(current.value)) {
                return index;
            }
            current = current.next;
            index++;
        }
        return -1;
    }

    @Override
    public E get(int index) {
        checkIndex(index);
        return nodeAt(index).value;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) != -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        int index = 0;
        int lastIndex = -1;
        Node current = head;
        while (current != null) {
            if (o == null ? current.value == null : o.equals(current.value)) {
                lastIndex = index;
            }
            current = current.next;
            index++;
        }
        return lastIndex;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean changed = false;
        for (E e : c) {
            add(e);
            changed = true;
        }
        return changed;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("index: " + index + ", size: " + size);
        }
        boolean changed = false;
        int i = index;
        for (E e : c) {
            add(i, e);
            i++;
            changed = true;
        }
        return changed;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean changed = false;
        while (head != null && c.contains(head.value)) {
            head = head.next;
            size--;
            changed = true;
        }
        Node current = head;
        while (current != null && current.next != null) {
            if (c.contains(current.next.value)) {
                Node toRemove = current.next;
                current.next = toRemove.next;
                toRemove.next = null;
                toRemove.value = null;
                size--;
                changed = true;
            } else {
                current = current.next;
            }
        }
        return changed;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean changed = false;
        while (head != null && !c.contains(head.value)) {
            head = head.next;
            size--;
            changed = true;
        }
        Node current = head;
        while (current != null && current.next != null) {
            if (!c.contains(current.next.value)) {
                Node toRemove = current.next;
                current.next = toRemove.next;
                toRemove.next = null;
                toRemove.value = null;
                size--;
                changed = true;
            } else {
                current = current.next;
            }
        }
        return changed;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex > size || fromIndex > toIndex) {
            throw new IndexOutOfBoundsException();
        }
        ListC<E> sub = new ListC<>();
        Node current = nodeAt(fromIndex);
        for (int i = fromIndex; i < toIndex; i++) {
            sub.add(current.value);
            current = current.next;
        }
        return sub;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("index: " + index + ", size: " + size);
        }
        return new ListIterator<E>() {
            private Node nextNode = (index == size) ? null : nodeAt(index);
            private Node lastReturned = null;
            private int nextIndex = index;

            @Override
            public boolean hasNext() {
                return nextIndex < size;
            }

            @Override
            public E next() {
                lastReturned = nextNode;
                nextNode = nextNode.next;
                nextIndex++;
                return lastReturned.value;
            }

            @Override
            public boolean hasPrevious() {
                return nextIndex > 0;
            }

            @Override
            public E previous() {
                if (nextIndex == 0) {
                    throw new java.util.NoSuchElementException();
                }
                Node prevNode = nodeAt(nextIndex - 1);
                lastReturned = prevNode;
                nextNode = prevNode;
                nextIndex--;
                return prevNode.value;
            }

            @Override
            public int nextIndex() {
                return nextIndex;
            }

            @Override
            public int previousIndex() {
                return nextIndex - 1;
            }

            @Override
            public void remove() {
                if (lastReturned == null) {
                    throw new IllegalStateException();
                }
                int idx = indexOf(lastReturned.value);
                ListC.this.remove(idx);
                if (idx < nextIndex) {
                    nextIndex--;
                } else {
                    nextNode = (nextIndex == size) ? null : nodeAt(nextIndex);
                }
                lastReturned = null;
            }

            @Override
            public void set(E e) {
                if (lastReturned == null) {
                    throw new IllegalStateException();
                }
                lastReturned.value = e;
            }

            @Override
            public void add(E e) {
                if (nextIndex == 0) {
                    Node newNode = new Node(e);
                    newNode.next = head;
                    head = newNode;
                } else {
                    Node prev = nodeAt(nextIndex - 1);
                    Node newNode = new Node(e);
                    newNode.next = prev.next;
                    prev.next = newNode;
                }
                size++;
                nextIndex++;
                lastReturned = null;
            }
        };
    }

    @Override
    public ListIterator<E> listIterator() {
        return listIterator(0);
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            a = (T[]) new Object[size];
        }
        int i = 0;
        Node current = head;
        while (current != null) {
            a[i++] = (T) current.value;
            current = current.next;
        }
        if (a.length > size) {
            a[size] = null;
        }
        return a;
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[size];
        int i = 0;
        Node current = head;
        while (current != null) {
            result[i++] = current.value;
            current = current.next;
        }
        return result;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private Node nextNode = head;

            @Override
            public boolean hasNext() {
                return nextNode != null;
            }

            @Override
            public E next() {
                E value = nextNode.value;
                nextNode = nextNode.next;
                return value;
            }
        };
    }

    private Node nodeAt(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index: " + index + ", size: " + size);
        }
        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index: " + index + ", size: " + size);
        }
    }
}
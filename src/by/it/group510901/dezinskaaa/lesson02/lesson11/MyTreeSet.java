package by.it.group510901.dezinskaaa.lesson02.lesson11;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class MyTreeSet<E> implements Set<E> {

    private E[] elements;
    private int size;

    @SuppressWarnings("unchecked")
    public MyTreeSet() {
        this.elements = (E[]) new Object[10];
        this.size = 0;
    }

    @SuppressWarnings("unchecked")
    private int compare(E a, E b) {//сравн двух эл
        return ((Comparable<E>) a).compareTo(b);
    }

    @SuppressWarnings("unchecked")
    private void grow() { //расшир масс вдвое
        E[] newElements = (E[]) new Object[elements.length * 2];
        for (int i = 0; i < size; i++) {
            newElements[i] = elements[i];
        }
        elements = newElements;
    }

    @SuppressWarnings("unchecked")
    private int findPosition(E element) {//бинарн поиск
        int low = 0;
        int high = size - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            int cmp = compare(elements[mid], element);
            if (cmp < 0) {
                low = mid + 1;
            } else if (cmp > 0) {
                high = mid - 1;
            } else {
                return mid; //возр инд
            }
        }
        return -(low + 1);
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
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }
        String result = "[";
        for (int i = 0; i < size; i++) {
            result = result + elements[i];
            if (i < size - 1) {
                result = result + ", ";
            }
        }
        result = result + "]";
        return result;
    }

    @Override
    public boolean add(E element) { //добав
        int pos = findPosition(element);
        if (pos >= 0) {
            return false;
        }
        int insertAt = -(pos + 1);
        if (size == elements.length) {
            grow();
        }
        for (int i = size - 1; i >= insertAt; i--) {
            elements[i + 1] = elements[i];
        }
        elements[insertAt] = element;
        size++;
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean contains(Object o) {//провка есть ли эл
        int pos = findPosition((E) o);
        return pos >= 0;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean remove(Object o) {//удал эл из о
        int pos = findPosition((E) o);
        if (pos < 0) {
            return false;
        }
        for (int i = pos; i < size - 1; i++) {//влев
            elements[i] = elements[i + 1];
        }
        elements[size - 1] = null;
        size--;
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c) {//все эл с есть в множ
        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {//добав все эл с есть в множ
        boolean changed = false;
        for (E element : c) {
            if (add(element)) {
                changed = true; //хоть один добав
            }
        }
        return changed;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean removeAll(Collection<?> c) {// удалить тех кто есть в c
        E[] kept = (E[]) new Object[elements.length];
        int newSize = 0;
        for (int i = 0; i < size; i++) {
            if (!c.contains(elements[i])) {
                kept[newSize++] = elements[i];
            }
        }
        boolean changed = newSize != size;
        elements = kept;
        size = newSize;
        return changed;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean retainAll(Collection<?> c) {// оставить тех, кто есть в c
        E[] kept = (E[]) new Object[elements.length];
        int newSize = 0;
        for (int i = 0; i < size; i++) {
            if (c.contains(elements[i])) {
                kept[newSize++] = elements[i];
            }
        }
        boolean changed = newSize != size;
        elements = kept;
        size = newSize;
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

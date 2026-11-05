package by.it.group510901.dezinskaaa.lesson02.lesson10;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;

public class MyPriorityQueue<E> implements Queue<E> {

    private E[] heap;
    private int size;

    @SuppressWarnings("unchecked")
    public MyPriorityQueue() {
        this.heap = (E[]) new Object[10];
        this.size = 0;
    }

    @SuppressWarnings("unchecked")
    private int compare(E a, E b) { //сравнить два элемента
        return ((Comparable<E>) a).compareTo(b);
    }

    private void swap(int i, int j) {//меняет местами элементы массива
        E tmp = heap[i];
        heap[i] = heap[j];
        heap[j] = tmp;
    }

    private void siftUp(int i) {//поднятие ребенка к родителю
        while (i > 0) {
            int parent = (i - 1) / 2;
            if (compare(heap[i], heap[parent]) < 0) { //сравнение ребенка и родителя если реб мен род меняет
                swap(i, parent);
                i = parent;
            } else {
                break;
            }
        }
    }

    private void siftDown(int i) {//сравнивает род с дет , выбираем мин потомк и сравн  с род
        while (true) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;
            int smallest = i;
            if (left < size && compare(heap[left], heap[smallest]) < 0) {
                smallest = left;
            }
            if (right < size && compare(heap[right], heap[smallest]) < 0) {
                smallest = right;
            }
            if (smallest == i) {
                break;
            }
            swap(i, smallest);
            i = smallest;
        }
    }

    @SuppressWarnings("unchecked")
    private void grow() {
        E[] newHeap = (E[]) new Object[heap.length * 2];
        for (int i = 0; i < size; i++) {
            newHeap[i] = heap[i];
        }
        heap = newHeap;
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }
        String result = "[";
        for (int i = 0; i < size; i++) {
            result = result + heap[i];
            if (i < size - 1) {
                result = result + ", ";
            }
        }
        result = result + "]";
        return result;
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
            heap[i] = null;
        }
        size = 0;
    }

    @Override
    public boolean add(E element) { //добавление в массив в конец вызов сифап
        if (size == heap.length) {
            grow();
        }
        heap[size] = element;
        size++;
        siftUp(size - 1);
        return true;
    }

    @Override
    public boolean offer(E element) { //тож самое что эд
        return add(element);
    }

    @Override
    public E element() { //перв знач
        if (size == 0) {
            throw new NoSuchElementException();
        }
        return heap[0];
    }

    @Override
    public E peek() { //перв эл
        if (size == 0) {
            return null;
        }
        return heap[0];
    }

    @Override
    public E poll() { // удаление   замен корень последн эл и вызыв сивдаун
        if (size == 0) {
            return null;
        }
        E result = heap[0];
        heap[0] = heap[size - 1];
        heap[size - 1] = null;
        size--;
        if (size > 0) {
            siftDown(0);
        }
        return result;
    }

    @Override
    public E remove() { //удал
        if (size == 0) {
            throw new NoSuchElementException();
        }
        return poll();
    }

    @Override
    public boolean contains(Object o) { //проверяет наличие элемента в куче
        for (int i = 0; i < size; i++) {
            if (o == null ? heap[i] == null : o.equals(heap[i])) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) { //если все эл есть в куче то тру
        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) { //добавл все знач из какойто коллекц
        boolean changed = false;
        for (E element : c) {
            add(element);
            changed = true;
        }
        return changed;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean removeAll(Collection<?> c) { //удаляет все эл которых есть в коллекц с
        E[] kept = (E[]) new Object[heap.length];
        int newSize = 0;
        for (int i = 0; i < size; i++) {
            if (!c.contains(heap[i])) {
                kept[newSize++] = heap[i];
            }
        }
        boolean changed = newSize != size;
        heap = kept;
        size = newSize;
        for (int i = size / 2 - 1; i >= 0; i--) {
            siftDown(i);
        }
        return changed; //тру если хоть чтото удалили
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean retainAll(Collection<?> c) { //оставить тех кто есть в с
        E[] kept = (E[]) new Object[heap.length];
        int newSize = 0;
        for (int i = 0; i < size; i++) {
            if (c.contains(heap[i])) { //если есть в с остав
                kept[newSize++] = heap[i];
            }
        }
        boolean changed = newSize != size;
        heap = kept;
        size = newSize;
        for (int i = size / 2 - 1; i >= 0; i--) {
            siftDown(i);
        }
        return changed;
    }

    @Override
    public boolean remove(Object o) {
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
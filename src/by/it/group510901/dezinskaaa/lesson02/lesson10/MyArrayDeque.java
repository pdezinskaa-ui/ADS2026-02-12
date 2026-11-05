package by.it.group510901.dezinskaaa.lesson02.lesson10;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyArrayDeque<E> implements Deque<E> {

    private E[] elements;
    private int size;

    @SuppressWarnings("unchecked")
    public MyArrayDeque() { //
        this.elements = (E[]) new Object[10];
        this.size = 0;
    }

    @Override
    public String toString() { //элементы через запятую
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
    public int size() {
        return size;
    }

    @Override
    public boolean add(E element) { //добавляет в конец
        addLast(element);
        return true;
    }

    @Override
    public void addFirst(E element) { //добавить элемент в наало
        if (size == elements.length) {
            grow();
        }
        for (int i = size - 1; i >= 0; i--) {
            elements[i + 1] = elements[i];
        }
        elements[0] = element;
        size++;
    }

    @Override
    public void addLast(E element) { //добавить в конец
        if (size == elements.length) {
            grow();
        }
        elements[size] = element;
        size++;
    }

    @Override
    public E element() { //
        return getFirst();
    }

    @Override
    public E getFirst() { //вернуть первый элеиент
        if (size == 0) {
            throw new NoSuchElementException();
        }
        return elements[0];
    }

    @Override
    public E getLast() { //вернуть последнюю
        if (size == 0) {
            throw new NoSuchElementException();
        }
        return elements[size - 1];
    }

    @Override
    public E poll() {
        return pollFirst();
    }

    @Override
    public E pollFirst() { // удалить первый и вернуть его
        if (size == 0) {
            return null;
        }
        E result = elements[0];
        for (int i = 0; i < size - 1; i++) {
            elements[i] = elements[i + 1];
        }
        elements[size - 1] = null;
        size--;
        return result;
    }

    @Override
    public E pollLast() { // удаляем последний и возвращаем его
        if (size == 0) {
            return null;
        }
        E result = elements[size - 1];
        elements[size - 1] = null;
        size--;
        return result;
    }

    @Override
    public boolean isEmpty() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean offer(E e) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean offerFirst(E e) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean offerLast(E e) {
        throw new UnsupportedOperationException();
    }

    @Override
    public E peek() {
        throw new UnsupportedOperationException();
    }

    @Override
    public E peekFirst() {
        throw new UnsupportedOperationException();
    }

    @Override
    public E peekLast() {
        throw new UnsupportedOperationException();
    }

    @Override
    public E remove() {
        throw new UnsupportedOperationException();
    }

    @Override
    public E removeFirst() {
        throw new UnsupportedOperationException();
    }

    @Override
    public E removeLast() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void push(E e) {
        throw new UnsupportedOperationException();
    }

    @Override
    public E pop() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean contains(Object o) {
        throw new UnsupportedOperationException();
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
    public void clear() {
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

    @Override
    public Iterator<E> descendingIterator() {
        throw new UnsupportedOperationException();
    }

    @SuppressWarnings("unchecked")
    private void grow() {//новый массив в 2 раза больше
        E[] newElements = (E[]) new Object[elements.length * 2];
        for (int i = 0; i < size; i++) {
            newElements[i] = elements[i];
        }
        elements = newElements;
    }
}
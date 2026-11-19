package by.it.group510901.dezinskaaa.lesson02.lesson12;

import java.util.*;
//самобалансирующееся двоич дер поска

//для каждого узла высоты его левого и правого
//  поддерев отлич не более чем на 1.
public class MyAvlMap implements Map<Integer, String> {
    private static class Node {
        Integer key;
        String value;
        Node left, right;
        int height; //высота поддерев с корн в эт узл

        Node(Integer key, String value) {
            this.key = key;
            this.value = value;
            this.height = 1; //нов узел лист, выст 1
        }
    }

    private Node root;
    private int size;

    private int height(Node n) { //высот у пуст
        return n == null ? 0 : n.height;
    }

    private void updateHeight(Node n) { //пересчит выс узл по его дет
        n.height = 1 + Math.max(height(n.left), height(n.right));
    }

    private int balance(Node n) { //>0 слева, <0 справ, 0 ровно
        return n == null ? 0 : height(n.left) - height(n.right);
    }

    private Node rotateRight(Node y) { //лев реб х подним наверх, у станов прав ребен х, а сред дер t2 перевеш к у.
        Node x = y.left;
        Node t2 = x.right;
        x.right = y;
        y.left = t2;
        updateHeight(y);
        updateHeight(x);
        return x;
    }

    private Node rotateLeft(Node x) { //левый поворт
        Node y = x.right;
        Node t2 = y.left;
        y.left = x;
        x.right = t2;
        updateHeight(x);
        updateHeight(y);
        return y;
    }

    private Node rebalance(Node node) { //
        updateHeight(node);
        int b = balance(node);
        if (b > 1) {
            if (balance(node.left) < 0) node.left = rotateLeft(node.left);
            return rotateRight(node);
        }
        if (b < -1) {
            if (balance(node.right) > 0) node.right = rotateRight(node.right);
            return rotateLeft(node);
        }
        return node;
    }

    private Node insert(Node node, Integer key, String value) {
        if (node == null) {
            size++;
            return new Node(key, value);
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0) node.left = insert(node.left, key, value);
        else if (cmp > 0) node.right = insert(node.right, key, value);
        else { node.value = value; return node; }
        return rebalance(node);
    }

    private Node minNode(Node node) {
        while (node.left != null) node = node.left;
        return node;
    }

    private Node delete(Node node, Integer key) {
        if (node == null) return null;
        int cmp = key.compareTo(node.key);
        if (cmp < 0) node.left = delete(node.left, key);
        else if (cmp > 0) node.right = delete(node.right, key);
        else {
            if (node.left == null || node.right == null) {
                node = (node.left != null) ? node.left : node.right;
            } else {
                Node m = minNode(node.right);
                node.key = m.key;
                node.value = m.value;
                node.right = delete(node.right, m.key);
            }
        }
        if (node == null) return null;
        return rebalance(node);
    }

    @Override public int size() { return size; }
    @Override public boolean isEmpty() { return size == 0; }
    @Override public void clear() { root = null; size = 0; }

    @Override
    public String put(Integer key, String value) {
        Node found = find(key);
        String old = found == null ? null : found.value;
        if (found != null) found.value = value;
        else root = insert(root, key, value);
        return old;
    }

    @Override
    public String remove(Object key) {
        Node found = find((Integer) key);
        if (found == null) return null;
        String old = found.value;
        root = delete(root, (Integer) key);
        size--;
        return old;
    }

    @Override public String get(Object key) {
        Node n = find((Integer) key);
        return n == null ? null : n.value;
    }

    @Override public boolean containsKey(Object key) { return find((Integer) key) != null; }

    @Override
    public boolean containsValue(Object value) {
        return containsValue(root, value);
    }

    private boolean containsValue(Node n, Object v) {
        if (n == null) return false;
        if (v == null ? n.value == null : v.equals(n.value)) return true;
        return containsValue(n.left, v) || containsValue(n.right, v);
    }

    private Node find(Integer key) {
        Node n = root;
        while (n != null) {
            int c = key.compareTo(n.key);
            if (c < 0) n = n.left;
            else if (c > 0) n = n.right;
            else return n;
        }
        return null;
    }

    @Override public void putAll(Map<? extends Integer, ? extends String> m) {
        for (Map.Entry<? extends Integer, ? extends String> e : m.entrySet()) put(e.getKey(), e.getValue());
    }
    @Override public Set<Integer> keySet() { return null; }
    @Override public Collection<String> values() { return null; }
    @Override public Set<Map.Entry<Integer, String>> entrySet() { return null; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        append(root, sb);
        if (sb.length() > 1) sb.setLength(sb.length() - 2);
        return sb.append('}').toString();
    }

    private void append(Node n, StringBuilder sb) {
        if (n == null) return;
        append(n.left, sb);
        sb.append(n.key).append('=').append(n.value).append(", ");
        append(n.right, sb);
    }
}
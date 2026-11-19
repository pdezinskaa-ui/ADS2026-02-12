package by.it.group510901.dezinskaaa.lesson02.lesson12;

import java.util.*;

public class MyRbMap implements SortedMap<Integer, String> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private static final class Node {
        int key;
        String value;
        Node left, right, parent;
        boolean color;

        Node(int key, String value) {
            this.key = key;
            this.value = value;
            this.color = RED;
        }
    }

    private Node root;
    private int size;

    // ---------- базовые проверки ----------

    private static boolean isRed(Node n) {
        return n != null && n.color == RED;
    }

    private static boolean isBlack(Node n) {
        return n == null || n.color == BLACK;
    }

    // ---------- вращения ----------

    private void rotateLeft(Node x) {
        Node y = x.right;
        x.right = y.left;
        if (y.left != null) y.left.parent = x;

        y.parent = x.parent;
        if (x.parent == null) root = y;
        else if (x == x.parent.left) x.parent.left = y;
        else x.parent.right = y;

        y.left = x;
        x.parent = y;
    }

    private void rotateRight(Node x) {
        Node y = x.left;
        x.left = y.right;
        if (y.right != null) y.right.parent = x;

        y.parent = x.parent;
        if (x.parent == null) root = y;
        else if (x == x.parent.right) x.parent.right = y;
        else x.parent.left = y;

        y.right = x;
        x.parent = y;
    }

    // ---------- вставка ----------

    private void insertFixup(Node z) {
        while (z.parent != null && isRed(z.parent)) {
            Node p = z.parent;
            Node g = p.parent;

            if (p == g.left) {
                Node u = g.right;
                if (isRed(u)) {
                    p.color = BLACK;
                    u.color = BLACK;
                    g.color = RED;
                    z = g;
                } else {
                    if (z == p.right) {
                        z = p;
                        rotateLeft(z);
                        p = z.parent;
                        g = p.parent;
                    }
                    p.color = BLACK;
                    g.color = RED;
                    rotateRight(g);
                }
            } else {
                Node u = g.left;
                if (isRed(u)) {
                    p.color = BLACK;
                    u.color = BLACK;
                    g.color = RED;
                    z = g;
                } else {
                    if (z == p.left) {
                        z = p;
                        rotateRight(z);
                        p = z.parent;
                        g = p.parent;
                    }
                    p.color = BLACK;
                    g.color = RED;
                    rotateLeft(g);
                }
            }
        }
        root.color = BLACK;
    }

    // ---------- удаление ----------

    private void transplant(Node u, Node v) {
        if (u.parent == null) root = v;
        else if (u == u.parent.left) u.parent.left = v;
        else u.parent.right = v;
        if (v != null) v.parent = u.parent;
    }

    private Node minNode(Node n) {
        while (n.left != null) n = n.left;
        return n;
    }

    private Node maxNode(Node n) {
        while (n.right != null) n = n.right;
        return n;
    }

    /*
     * x — узел, который занял место удалённого (может быть null).
     * xParent — его текущий родитель (нужен, если x == null).
     */
    private void deleteFixup(Node x, Node xParent) {
        while (x != root && isBlack(x)) {
            if (xParent == null) break; // защита от некорректного состояния

            if (x == xParent.left) {
                Node w = xParent.right;

                if (isRed(w)) {
                    w.color = BLACK;
                    xParent.color = RED;
                    rotateLeft(xParent);
                    w = xParent.right;
                }

                if (isBlack(w.left) && isBlack(w.right)) {
                    if (w != null) w.color = RED;
                    x = xParent;
                    xParent = x.parent;
                } else {
                    if (isBlack(w.right)) {
                        if (w.left != null) w.left.color = BLACK;
                        w.color = RED;
                        rotateRight(w);
                        w = xParent.right;
                    }
                    w.color = xParent.color;
                    xParent.color = BLACK;
                    if (w.right != null) w.right.color = BLACK;
                    rotateLeft(xParent);
                    x = root;
                    xParent = null;
                }
            } else {
                Node w = xParent.left;

                if (isRed(w)) {
                    w.color = BLACK;
                    xParent.color = RED;
                    rotateRight(xParent);
                    w = xParent.left;
                }

                if (isBlack(w.left) && isBlack(w.right)) {
                    if (w != null) w.color = RED;
                    x = xParent;
                    xParent = x.parent;
                } else {
                    if (isBlack(w.left)) {
                        if (w.right != null) w.right.color = BLACK;
                        w.color = RED;
                        rotateLeft(w);
                        w = xParent.left;
                    }
                    w.color = xParent.color;
                    xParent.color = BLACK;
                    if (w.left != null) w.left.color = BLACK;
                    rotateRight(xParent);
                    x = root;
                    xParent = null;
                }
            }
        }
        if (x != null) x.color = BLACK;
    }

    private void deleteNode(Node z) {
        Node y = z;
        Node x;
        Node xParent;
        boolean yOriginalColor = y.color;

        if (z.left == null) {
            x = z.right;
            xParent = z.parent;
            transplant(z, z.right);
        } else if (z.right == null) {
            x = z.left;
            xParent = z.parent;
            transplant(z, z.left);
        } else {
            y = minNode(z.right);
            yOriginalColor = y.color;
            x = y.right;

            if (y.parent == z) {
                xParent = y;
                // y остаётся на своём месте: y.right всё ещё x
            } else {
                xParent = y.parent;
                transplant(y, y.right);
                y.right = z.right;
                y.right.parent = y;
            }

            transplant(z, y);
            y.left = z.left;
            y.left.parent = y;
            y.color = z.color;
        }

        if (yOriginalColor == BLACK) {
            deleteFixup(x, xParent);
        }
    }

    // ---------- Map ----------

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
        root = null;
        size = 0;
    }

    @Override
    public String put(Integer key, String value) {
        if (key == null) throw new NullPointerException("key == null");
        int k = key;

        if (root == null) {
            root = new Node(k, value);
            root.color = BLACK;
            size = 1;
            return null;
        }

        Node parent = null;
        Node cur = root;
        int cmp = 0;

        while (cur != null) {
            parent = cur;
            cmp = Integer.compare(k, cur.key);
            if (cmp < 0) cur = cur.left;
            else if (cmp > 0) cur = cur.right;
            else {
                String old = cur.value;
                cur.value = value;
                return old;
            }
        }

        Node z = new Node(k, value);
        z.parent = parent;
        if (cmp < 0) parent.left = z;
        else parent.right = z;

        size++;
        insertFixup(z);
        return null;
    }

    @Override
    public String remove(Object key) {
        if (key == null) return null;
        if (!(key instanceof Integer)) return null;
        Node z = find((Integer) key);
        if (z == null) return null;

        String old = z.value;
        deleteNode(z);
        size--;
        return old;
    }

    @Override
    public String get(Object key) {
        if (key == null) return null;
        if (!(key instanceof Integer)) return null;
        Node n = find((Integer) key);
        return n == null ? null : n.value;
    }

    @Override
    public boolean containsKey(Object key) {
        if (key == null) return false;
        if (!(key instanceof Integer)) return false;
        return find((Integer) key) != null;
    }

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
        int k = key;
        while (n != null) {
            int c = Integer.compare(k, n.key);
            if (c < 0) n = n.left;
            else if (c > 0) n = n.right;
            else return n;
        }
        return null;
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends String> m) {
        for (Map.Entry<? extends Integer, ? extends String> e : m.entrySet()) {
            put(e.getKey(), e.getValue());
        }
    }

    @Override public Set<Integer> keySet() { return null; }
    @Override public Collection<String> values() { return null; }
    @Override public Set<Map.Entry<Integer, String>> entrySet() { return null; }
    @Override public Comparator<? super Integer> comparator() { return null; }

    // ---------- SortedMap ----------

    @Override
    public SortedMap<Integer, String> subMap(Integer fromKey, Integer toKey) {
        MyRbMap m = new MyRbMap();
        copyRange(root, fromKey, true, toKey, false, m);
        return m;
    }

    @Override
    public SortedMap<Integer, String> headMap(Integer toKey) {
        MyRbMap m = new MyRbMap();
        copyRange(root, null, true, toKey, false, m);
        return m;
    }

    @Override
    public SortedMap<Integer, String> tailMap(Integer fromKey) {
        MyRbMap m = new MyRbMap();
        copyRange(root, fromKey, true, null, true, m);
        return m;
    }

    @Override
    public Integer firstKey() {
        if (root == null) throw new NoSuchElementException();
        return minNode(root).key;
    }

    @Override
    public Integer lastKey() {
        if (root == null) throw new NoSuchElementException();
        return maxNode(root).key;
    }

    private void copyRange(Node n,
                           Integer from, boolean fromInclusive,
                           Integer to, boolean toInclusive,
                           MyRbMap target) {
        if (n == null) return;
        copyRange(n.left, from, fromInclusive, to, toInclusive, target);

        boolean okFrom = true;
        if (from != null) {
            int c = Integer.compare(n.key, from);
            okFrom = fromInclusive ? c >= 0 : c > 0;
        }

        boolean okTo = true;
        if (to != null) {
            int c = Integer.compare(n.key, to);
            okTo = toInclusive ? c <= 0 : c < 0;
        }

        if (okFrom && okTo) {
            target.put(n.key, n.value);
        }

        copyRange(n.right, from, fromInclusive, to, toInclusive, target);
    }

    // ---------- toString ----------

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
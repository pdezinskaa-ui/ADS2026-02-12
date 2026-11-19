package by.it.group510901.dezinskaaa.lesson02.lesson12;

import java.util.*;

public class MySplayMap implements NavigableMap<Integer, String> {
    private static class Node {
        Integer key;
        String value;
        Node left, right, parent;

        Node(Integer k, String v) { key = k; value = v; }
    }

    private Node root;
    private int size;

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

    private void splay(Node x) {
        while (x.parent != null) {
            Node p = x.parent, g = p.parent;
            if (g == null) {
                if (x == p.left) rotateRight(p); else rotateLeft(p);
            } else if (x == p.left && p == g.left) {
                rotateRight(g); rotateRight(p);
            } else if (x == p.right && p == g.right) {
                rotateLeft(g); rotateLeft(p);
            } else if (x == p.right && p == g.left) {
                rotateLeft(p); rotateRight(g);
            } else {
                rotateRight(p); rotateLeft(g);
            }
        }
    }

    // Возвращает узел с ключом key, поднимает его (или последний посещённый) в корень
    private Node findAndSplay(Integer key) {
        Node cur = root, last = null;
        while (cur != null) {
            last = cur;
            int c = key.compareTo(cur.key);
            if (c < 0) cur = cur.left;
            else if (c > 0) cur = cur.right;
            else { splay(cur); return cur; }
        }
        if (last != null) splay(last);
        return null;
    }

    @Override public int size() { return size; }
    @Override public boolean isEmpty() { return size == 0; }
    @Override public void clear() { root = null; size = 0; }

    @Override
    public String get(Object key) {
        Node n = findAndSplay((Integer) key);
        return n == null ? null : n.value;
    }

    @Override
    public boolean containsKey(Object key) { return get(key) != null; }

    @Override
    public String put(Integer key, String value) {
        if (root == null) {
            root = new Node(key, value);
            size++;
            return null;
        }
        Node cur = root, last = null;
        while (cur != null) {
            last = cur;
            int c = key.compareTo(cur.key);
            if (c < 0) cur = cur.left;
            else if (c > 0) cur = cur.right;
            else { String old = cur.value; cur.value = value; splay(cur); return old; }
        }
        Node z = new Node(key, value);
        int c = key.compareTo(last.key);
        z.parent = last;
        if (c < 0) last.left = z; else last.right = z;
        size++;
        splay(z);
        return null;
    }

    @Override
    public String remove(Object key) {
        Node n = findAndSplay((Integer) key);
        if (n == null) return null;
        String old = n.value;
        Node L = n.left, R = n.right;
        if (L != null) L.parent = null;
        if (R != null) R.parent = null;
        if (L == null) {
            root = R;
        } else {
            // Максимум левого поддерева поднять в корень
            Node max = L;
            while (max.right != null) max = max.right;
            root = L;
            splay(max);
            root.right = R;
            if (R != null) R.parent = root;
        }
        size--;
        return old;
    }

    @Override
    public boolean containsValue(Object value) { return containsValue(root, value); }

    private boolean containsValue(Node n, Object v) {
        if (n == null) return false;
        if (v == null ? n.value == null : v.equals(n.value)) return true;
        return containsValue(n.left, v) || containsValue(n.right, v);
    }

    @Override public void putAll(Map<? extends Integer, ? extends String> m) {
        for (Map.Entry<? extends Integer, ? extends String> e : m.entrySet()) put(e.getKey(), e.getValue());
    }
    @Override public Set<Integer> keySet() { return null; }
    @Override public Collection<String> values() { return null; }
    @Override public Set<Map.Entry<Integer, String>> entrySet() { return null; }
    @Override public Comparator<? super Integer> comparator() { return null; }

    @Override public SortedMap<Integer, String> subMap(Integer a, Integer b) {
        MySplayMap m = new MySplayMap(); copyRange(root, a, true, b, false, m); return m;
    }
    @Override public SortedMap<Integer, String> headMap(Integer to) {
        MySplayMap m = new MySplayMap(); copyRange(root, null, true, to, false, m); return m;
    }
    @Override public SortedMap<Integer, String> tailMap(Integer from) {
        MySplayMap m = new MySplayMap(); copyRange(root, from, true, null, true, m); return m;
    }

    @Override public Integer firstKey() { if (root == null) throw new NoSuchElementException(); Node n = root; while (n.left != null) n = n.left; return n.key; }
    @Override public Integer lastKey() { if (root == null) throw new NoSuchElementException(); Node n = root; while (n.right != null) n = n.right; return n.key; }

    @Override public Map.Entry<Integer, String> lowerEntry(Integer k) { return null; }
    @Override public Map.Entry<Integer, String> floorEntry(Integer k) { return null; }
    @Override public Map.Entry<Integer, String> ceilingEntry(Integer k) { return null; }
    @Override public Map.Entry<Integer, String> higherEntry(Integer k) { return null; }
    @Override public Map.Entry<Integer, String> firstEntry() { return null; }
    @Override public Map.Entry<Integer, String> lastEntry() { return null; }
    @Override public Map.Entry<Integer, String> pollFirstEntry() { return null; }
    @Override public Map.Entry<Integer, String> pollLastEntry() { return null; }
    @Override public NavigableMap<Integer, String> descendingMap() { return null; }
    @Override public NavigableSet<Integer> navigableKeySet() { return null; }
    @Override public NavigableSet<Integer> descendingKeySet() { return null; }

    @Override
    public Integer lowerKey(Integer key) {
        Integer best = null;
        Node n = root;
        while (n != null) {
            if (n.key < key) { best = n.key; n = n.right; } else n = n.left;
        }
        return best;
    }

    @Override
    public Integer floorKey(Integer key) {
        Integer best = null;
        Node n = root;
        while (n != null) {
            if (n.key <= key) { best = n.key; n = n.right; } else n = n.left;
        }
        return best;
    }

    @Override
    public Integer ceilingKey(Integer key) {
        Integer best = null;
        Node n = root;
        while (n != null) {
            if (n.key >= key) { best = n.key; n = n.left; } else n = n.right;
        }
        return best;
    }

    @Override
    public Integer higherKey(Integer key) {
        Integer best = null;
        Node n = root;
        while (n != null) {
            if (n.key > key) { best = n.key; n = n.left; } else n = n.right;
        }
        return best;
    }

    @Override
    public NavigableMap<Integer, String> subMap(Integer a, boolean ai, Integer b, boolean bi) {
        MySplayMap m = new MySplayMap(); copyRange(root, a, ai, b, bi, m); return m;
    }
    @Override
    public NavigableMap<Integer, String> headMap(Integer to, boolean inc) {
        MySplayMap m = new MySplayMap(); copyRange(root, null, true, to, inc, m); return m;
    }
    @Override
    public NavigableMap<Integer, String> tailMap(Integer from, boolean inc) {
        MySplayMap m = new MySplayMap(); copyRange(root, from, inc, null, true, m); return m;
    }

    private void copyRange(Node n, Integer from, boolean fi, Integer to, boolean ti, MySplayMap t) {
        if (n == null) return;
        copyRange(n.left, from, fi, to, ti, t);
        boolean okFrom = true;
        if (from != null) { int c = n.key.compareTo(from); okFrom = fi ? c >= 0 : c > 0; }
        boolean okTo = true;
        if (to != null) { int c = n.key.compareTo(to); okTo = ti ? c <= 0 : c < 0; }
        if (okFrom && okTo) t.put(n.key, n.value);
        copyRange(n.right, from, fi, to, ti, t);
    }

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
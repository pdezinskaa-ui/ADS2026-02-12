package by.it.group510901.dezinskaaa.lesson02.lesson13;

import java.util.*;

public class GraphA {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextLine()) return;
        String line = sc.nextLine().trim();

        Set<String> vertices = new TreeSet<>();
        Map<String, List<String>> graph = new HashMap<>();
        Map<String, Integer> indegree = new HashMap<>();

        String[] edges = line.split(",");
        for (String edge : edges) {
            String[] parts = edge.split("->");
            if (parts.length != 2) continue;
            String from = parts[0].trim();
            String to = parts[1].trim();

            vertices.add(from);
            vertices.add(to);

            graph.computeIfAbsent(from, k -> new ArrayList<>()).add(to);
            graph.putIfAbsent(to, new ArrayList<>());

            indegree.putIfAbsent(from, 0);
            indegree.put(to, indegree.getOrDefault(to, 0) + 1);
        }

        // Очередь с приоритетом для лексикографического порядка
        PriorityQueue<String> pq = new PriorityQueue<>();
        for (String v : vertices) {
            if (indegree.getOrDefault(v, 0) == 0) {
                pq.add(v);
            }
        }

        List<String> result = new ArrayList<>();
        while (!pq.isEmpty()) {
            String v = pq.poll();
            result.add(v);
            for (String next : graph.get(v)) {
                indegree.put(next, indegree.get(next) - 1);
                if (indegree.get(next) == 0) {
                    pq.add(next);
                }
            }
        }

        System.out.println(String.join(" ", result));
    }
}
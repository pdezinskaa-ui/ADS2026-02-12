package by.it.group510901.dezinskaaa.lesson02.lesson13;

import java.util.*;

public class GraphC {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextLine()) return;
        String line = sc.nextLine().trim();

        // Исходный граф и транспонированный граф
        Map<String, List<String>> graph = new HashMap<>();
        Map<String, List<String>> transposed = new HashMap<>();
        Set<String> vertices = new HashSet<>();

        // Парсим строку
        if (!line.isEmpty()) {
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

                transposed.computeIfAbsent(to, k -> new ArrayList<>()).add(from);
                transposed.putIfAbsent(from, new ArrayList<>());
            }
        }

        // 1-й проход DFS: заполняем стек порядком завершения
        Set<String> visited = new HashSet<>();
        Stack<String> stack = new Stack<>();

        for (String v : vertices) {
            if (!visited.contains(v)) {
                dfs1(v, graph, visited, stack);
            }
        }

        // 2-й проход DFS: по транспонированному графу
        visited.clear();
        List<List<String>> components = new ArrayList<>();

        while (!stack.isEmpty()) {
            String v = stack.pop();
            if (!visited.contains(v)) {
                List<String> component = new ArrayList<>();
                dfs2(v, transposed, visited, component);
                // Сортируем вершины внутри компоненты по алфавиту
                Collections.sort(component);
                components.add(component);
            }
        }

        // Вывод результата
        for (List<String> comp : components) {
            // Склеиваем вершины без пробелов
            StringBuilder sb = new StringBuilder();
            for (String vertex : comp) {
                sb.append(vertex);
            }
            System.out.println(sb.toString());
        }
    }

    // Первый обход в глубину (исходный граф)
    private static void dfs1(String v, Map<String, List<String>> graph, Set<String> visited, Stack<String> stack) {
        visited.add(v);
        List<String> neighbors = graph.get(v);
        if (neighbors != null) {
            for (String next : neighbors) {
                if (!visited.contains(next)) {
                    dfs1(next, graph, visited, stack);
                }
            }
        }
        stack.push(v);
    }

    // Второй обход в глубину (транспонированный граф)
    private static void dfs2(String v, Map<String, List<String>> graph, Set<String> visited, List<String> component) {
        visited.add(v);
        component.add(v);
        List<String> neighbors = graph.get(v);
        if (neighbors != null) {
            for (String next : neighbors) {
                if (!visited.contains(next)) {
                    dfs2(next, graph, visited, component);
                }
            }
        }
    }
}
package by.it.group510901.dezinskaaa.lesson02.lesson13;

import java.util.*;

public class GraphB {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextLine()) return;
        String line = sc.nextLine().trim();

        // Граф: вершина -> список исходящих
        Map<String, List<String>> graph = new HashMap<>();
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
                graph.putIfAbsent(to, new ArrayList<>()); // чтобы не было null
            }
        }

        // Множества для отслеживания посещенных вершин при DFS
        Set<String> visited = new HashSet<>();
        Set<String> recStack = new HashSet<>(); // текущий путь рекурсии

        boolean hasCycle = false;
        for (String v : vertices) {
            if (hasCycle(v, graph, visited, recStack)) {
                hasCycle = true;
                break;
            }
        }

        if (hasCycle) {
            System.out.println("yes");
        } else {
            System.out.println("no");
        }
    }

    // Вспомогательный метод для поиска в глубину (DFS)
    private static boolean hasCycle(String v, Map<String, List<String>> graph,
                                    Set<String> visited, Set<String> recStack) {
        if (recStack.contains(v)) {
            return true; // нашли вершину, которая уже есть в текущем стеке -> цикл
        }
        if (visited.contains(v)) {
            return false; // уже проверяли эту ветку, цикла нет
        }

        visited.add(v);
        recStack.add(v);

        List<String> neighbors = graph.get(v);
        if (neighbors != null) {
            for (String next : neighbors) {
                if (hasCycle(next, graph, visited, recStack)) {
                    return true;
                }
            }
        }

        recStack.remove(v); // убираем из стека при возврате
        return false;
    }
}
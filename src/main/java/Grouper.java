import java.util.*;

public class Grouper {
    public List<String> lines;

    public Grouper(List<String> lines) {
        this.lines = lines;
    }

    public List<List<String>> getUniqueGroups() {
        Map<Integer, Integer> tree = getLinesTree();
        Map<Integer, List<String>> groups = getGroupsFromTree(tree);
        return groups.values().stream()
                .sorted((o1, o2) -> Integer.compare(o2.size(), o1.size()))
                .toList();
    }

    private Map<Integer, Integer> getLinesTree() {
        List<Map<String, Integer>> columnMaps = new ArrayList<>();
        Map<Integer, Integer> tree = new HashMap<>();

        // Проходимся по строкам
        for (int lineNum = 0; lineNum < lines.size(); lineNum++) {
            String line = lines.get(lineNum).replace("\"", "");
            String[] elements = line.split(";");

            // Проходимся по элементам строки
            for (int elNum = 0; elNum < elements.length; elNum++) {
                String element = elements[elNum];

                if (columnMaps.size() == elNum) {
                    columnMaps.add(new HashMap<>());
                }

                if (element.isEmpty())
                    continue;

                // Получаем мапу соответствующей колонки
                Map<String, Integer> elementMap = columnMaps.get(elNum);

                // Добавляем элемент в мапу (элемент -> номер строки)
                if (elementMap.containsKey(element)) {
                    Integer oldLineNum = elementMap.get(element);
                    union(lineNum, oldLineNum, tree);
                } else if (!tree.containsKey(lineNum)) {
                    tree.put(lineNum, lineNum);
                }
                elementMap.put(element, lineNum);
            }
        }
        return tree;
    }

    private int findRoot(int lineNum, Map<Integer, Integer> tree) {
        while(tree.get(lineNum) != null && tree.get(lineNum) != lineNum) {
            lineNum = tree.get(lineNum);
        }
        return lineNum;
    }

    private void union(int line1, int line2, Map<Integer, Integer> tree) {
        int root1 = findRoot(line1, tree);
        int root2 = findRoot(line2, tree);
        if (root1 != root2) {
            tree.put(root1, root2);
        }
    }

    private Map<Integer, List<String>> getGroupsFromTree(Map<Integer, Integer> tree) {
        Map<Integer, List<String>> groups = new HashMap<>();
        for (Integer child : tree.keySet()) {
            Integer root = findRoot(child, tree);
            groups.computeIfAbsent(root, k -> new LinkedList<>()).add(lines.get(child));
        }
        return groups;
    }
}

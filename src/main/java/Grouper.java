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
                    tree.put(lineNum, findRoot(oldLineNum, tree));
                }
                elementMap.put(element, lineNum);
            }
        }
        return tree;
    }

    private int findRoot(int lineNum, Map<Integer, Integer> tree) {
        while(tree.containsKey(lineNum)) {
            lineNum = tree.get(lineNum);
        }
        return lineNum;
    }

    private Map<Integer, List<String>> getGroupsFromTree(Map<Integer, Integer> tree) {
        Map<Integer, List<String>> groups = new HashMap<>();
        for (Integer child : tree.keySet()) {
            Integer parent = tree.get(child);
            List<String> group;
            if (!groups.containsKey(parent)) {
                group = new LinkedList<>();
                group.add(lines.get(parent));
                groups.put(parent, group);
            } else {
                group = groups.get(parent);
            }
            group.add(lines.get(child));
        }
        return groups;
    }
}

package util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

public class FileUtil {
    public static List<String> readLines(File file) {
        List<String> lines;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            lines = br.lines().distinct()
                    .filter(StringValidator::isValidLine)
                    .collect(Collectors.toList());
            return lines;
        } catch (IOException e) {
            throw new RuntimeException("Ошибка ввода данных");
        }
    }

    public static void writeGroups(File file, List<List<String>> groups) {
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
            bw.write(String.format("Количество групп: %d%n", groups.size()));

            int groupNumber = 1;
            for (List<String> group : groups) {
                bw.write(String.format("Группа %d%n", groupNumber++));
                for (String line : group) {
                    bw.write(line);
                    bw.write("\n");
                }
                bw.write("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException("Ошибка записи данных");
        }
    }
}

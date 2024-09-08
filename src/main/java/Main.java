import util.FileUtil;

import java.io.File;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Instant start = Instant.now();

        File inputFile = new File(args[0]);
        File outputFile = new File("groups.txt");

        List<String> lines = FileUtil.readLines(inputFile);
        Grouper grouper = new Grouper(lines);
        List<List<String>> groups = grouper.getUniqueGroups();

        FileUtil.writeGroups(outputFile, groups);

        Instant end = Instant.now();
        System.out.printf("Время работы: %s с\n", Duration.between(start, end).toSeconds());
    }
}

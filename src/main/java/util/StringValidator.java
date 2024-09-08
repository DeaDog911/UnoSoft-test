package util;

public class StringValidator {
    public static boolean isValidLine(String line) {
        return line.matches("^(\"[^\";]*\")(;\"[^\";]*\")*$");
    }
}

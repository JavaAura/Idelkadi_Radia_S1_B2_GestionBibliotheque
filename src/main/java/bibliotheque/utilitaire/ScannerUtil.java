package bibliotheque.utilitaire;

import java.util.Scanner;

public class ScannerUtil {
    private static final Scanner SCANNER = new Scanner(System.in);

    private ScannerUtil() {
    }

    public static Scanner getScanner() {
        return SCANNER;
    }
}

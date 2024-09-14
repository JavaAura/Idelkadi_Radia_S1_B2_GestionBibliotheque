package main.java.bibliotheque.utilitaire;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class InputValidator {

    public static boolean isValidString(String str) {
        return str != null && !str.trim().isEmpty() && str.matches("^[a-zA-Z0-9\\s+]+$");
    }

    public static boolean isValidAge(int age) {
        return age > 6;
    }

    public static boolean isPositiveNumber(int number) {
        return number > 0;
    }

    public static boolean isValidISBN(String isbn) {
        return isValidString(isbn) && isbn.matches("\\d{10}|\\d{13}");
    }

    public static boolean isValidDate(String dateStr) {
        return DateUtils.isValidDate(dateStr);
    }

    public static String generateNumeroDadhesion(String nom, int age, LocalDate dateAdhesion) {
        String formattedDate = dateAdhesion.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        return nom.toUpperCase() + age + formattedDate;
    }

    public static int lireChoix() {
         Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Erreur : veuillez entrer un nombre valide.");
            }
        }
    }
}

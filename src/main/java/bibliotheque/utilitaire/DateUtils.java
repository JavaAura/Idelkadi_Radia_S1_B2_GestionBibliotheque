package main.java.bibliotheque.utilitaire;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtils {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd"); // Assurez-vous que ce format correspond à vos besoins

    public static LocalDate parseDate(String dateStr) {
        try {
            return LocalDate.parse(dateStr, formatter);
        } catch (DateTimeParseException e) {
            System.err.println("Format de date invalide : " + dateStr);
            return null;
        }
    }

    public static String formatDate(LocalDate date) {
        if (date != null) {
            return date.format(formatter);
        } else {
            return null;
        }
    }

    public static boolean isValidDate(String dateStr) {
        try {
            LocalDate.parse(dateStr, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static String getCurrentDate() {
        return LocalDate.now().format(formatter);
    }
}
